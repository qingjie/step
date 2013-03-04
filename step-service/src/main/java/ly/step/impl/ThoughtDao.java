package ly.step.impl;

import java.util.List;

import ly.step.Thought;

public interface ThoughtDao {

    /**
     * 按ID查找
     * 
     * @param id
     * @return
     */
    Thought findById(long id);

    /**
     * 查找用户发布的Thought列表。按时间倒序排列
     * 
     * @param userId
     *            用户的ID
     * @param sinceId
     *            开始查询的ID, 结果中的ID都会大于这个值。
     * @param maxId
     *            最多查询的ID, 结果中的ID都会小于这个值。
     * @param limit
     *            结果最多返回的内容
     * @return 符合条件的Thought的ID号。如果没有符合条件的记录。则会返回一个空的List对象
     */
    List<Long> findByUser(long userId, long sinceId, long maxId, int limit);

    /**
     * 保存一个Thought
     * 
     * @param thought
     * @return 保存后的Thought, ID被更新了
     */
    Thought save(Thought thought);
}
