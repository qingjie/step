package ly.step.impl;

import java.util.List;

public interface UserToThoughtDao {

    /**
     * 保存一个 User 到 Thought 的订阅关系
     * 
     * @param build
     */
    void save(long userId, long thoughtId);

    /**
     * 读取用户的时间线
     * 
     * @param userId
     * @param sinceId
     * @param maxId
     * @param limit
     * @return
     */
    List<Long> findByUserId(long userId, long sinceId, long maxId, int limit);

}
