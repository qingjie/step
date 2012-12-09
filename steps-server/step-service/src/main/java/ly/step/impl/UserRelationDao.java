package ly.step.impl;

import java.util.Date;
import java.util.List;

/**
 * 用户关系的数据访问对象
 * 
 * @author Dante
 * 
 */
public interface UserRelationDao {

    /**
     * 保存一个关系
     * 
     * @param build
     */
    void save(long friendA, long friendB, Date createdAt);

    /**
     * 删除一个关系
     * 
     * @param friendA
     * @param friendB
     */
    void remove(long friendA, long friendB);

    /**
     * 查找指定用户的朋友列表
     * 
     * @param userId
     *            用户的ID
     * @return 指定用户的朋友们的ID。
     */
    List<Long> findFriendsByUserId(long userId);
}