package ly.step.impl;

import java.util.List;

import ly.step.Thought;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 这个Queue的抽象实现会在消息 enqueue 的时候就会把需要广播到的列表准备好
 * 
 * @author Leon
 * 
 */
public abstract class AbstractPreparedThoughtBroadcastQueue implements
        ThoughtBroadcastQueue {
    private static final Logger logger = LoggerFactory
	    .getLogger(AbstractPreparedThoughtBroadcastQueue.class);

    @Autowired
    private UserRelationDao userRelationDao;
    @Autowired
    private PreparedThoughtBroadcastTaskDao preparedBroadcastTaskDao;

    @Override
    public void broadcast(Thought thought) {
	List<Long> friends = userRelationDao.findFriendsByUserId(thought
	        .getAuthorId());
	logger.debug("Broadcasting. ThoughtId:{}, RecipientsSize:{}",
	        thought.getId(), friends.size());
	PreparedThoughtBroadcastTask preparedBroadcastTask = preparedBroadcastTaskDao
	        .save(PreparedThoughtBroadcastTask.newBuilder()
	                .setRecipientList(friends)
	                .setThoughtId(thought.getId())
	                .build());

	logger.info("Broadcast prepared. ID:{}", preparedBroadcastTask.getId());
	this.enqueue(preparedBroadcastTask);
	logger.debug("Broadcast task enqueue. ID:{}",
	        preparedBroadcastTask.getId());
    }

    /**
     * 将任务放入Queue中
     * 
     * @param preparedBroadcastTask
     *            要放入队列中的Task
     */
    abstract protected void enqueue(
	    PreparedThoughtBroadcastTask preparedBroadcastTask);

}
