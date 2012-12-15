package ly.step.impl;

import java.util.List;

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
    @Autowired
    private ThoughtBroadcastQueue broadcastQueue;

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
	broadcastQueue.broadcast(thought);
	return persistenced.getId();
    }

}
