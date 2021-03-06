package ly.step.impl;

import java.util.List;

import ly.step.Thought;
import ly.step.ThoughtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThoughtServiceDefaultImpl implements ThoughtService {

    /**
     * 单次广播的最大数量， 嗯先支持这些吧。
     */
    private static final int MAX_BROADCAST = 15;

    private static final Logger logger = LoggerFactory
	    .getLogger(ThoughtServiceDefaultImpl.class);

    @Autowired
    private ThoughtDao thoughtDao;
    @Autowired
    private UserToThoughtDao userToThoughtDao;
    @Autowired
    private FriendDao userRelationDao;

    private void broadcast(final Thought thought) {
	List<Long> friendList = userRelationDao
	        .findFriendsByUserId(thought
	                .getAuthorId());
	logger.info("Will broadcast thought {} to {} friends: {}.",
	        new Object[] { thought.getId(), friendList.size(), friendList });
	if (friendList.size() > 15) {
	    logger.warn("There {} in a batch for thought {}!",
		    friendList.size(), thought.getId());
	}
	if (friendList.size() > MAX_BROADCAST) {
	    friendList = friendList.subList(0, MAX_BROADCAST);
	}
	for (Long friend : friendList) {
	    userToThoughtDao.save(friend, thought.getId());
	}
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
	return userToThoughtDao.findByUserId(userId, sinceId, maxId, limit);
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
	userToThoughtDao.save(thought.getAuthorId(), persistenced.getId());
	// 启动广播
	broadcast(persistenced);
	return persistenced.getId();
    }
}
