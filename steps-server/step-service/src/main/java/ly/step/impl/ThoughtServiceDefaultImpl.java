package ly.step.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import ly.step.Thought;
import ly.step.ThoughtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThoughtServiceDefaultImpl implements ThoughtService {

    @Autowired
    private ThoughtDao thoughtDao;
    @Autowired
    private UserToThoughtDao userToThoughtDAO;
    @Autowired
    private UserRelationDao userRelationDao;

    // Well，we have an unbounded queue.
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
	    1,
	    10,
	    1,
	    TimeUnit.MINUTES,
	    new LinkedBlockingQueue<Runnable>());

    private void broadcast(final Thought thought) {
	// Next time, we may introduce a messaging service :-)
	threadPoolExecutor.execute(new Runnable() {

	    @Override
	    public void run() {
		List<Long> friendList = userRelationDao
		        .findFriendsByUserId(thought
		                .getAuthorId());
		for (Long friend : friendList) {
		    userToThoughtDAO.save(
			    friend,
			    thought.getId());
		}
	    }
	});
    }

    @Override
    public Thought findById(long id) {
	return thoughtDao.findById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.ThoughtService#findByUser(long, long, long, int)
     */
    @Override
    public List<Long> findByUser(long userId, long sinceId, long maxId,
	    int limit) {
	return thoughtDao.findByUser(userId, sinceId, maxId, limit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.ThoughtService#findInTimeline(long, long, long, int)
     */
    @Override
    public List<Long> findInTimeline(long userId, long sinceId, long maxId,
	    int limit) {
	return userToThoughtDAO.findByUserId(userId, sinceId, maxId, limit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.ThoughtService#post(ly.step.Thought)
     */
    @Override
    public long post(Thought thought) {
	// 保存
	Thought persistenced = thoughtDao.save(thought);
	userToThoughtDAO.save(thought.getAuthorId(), thought.getId());
	// 启动广播
	broadcast(thought);
	return persistenced.getId();
    }

    /**
     * Graceful shutdown this service, deallocate resource if needed.
     */
    @PreDestroy
    public void shutdown() {
	threadPoolExecutor.shutdown();
    }

}
