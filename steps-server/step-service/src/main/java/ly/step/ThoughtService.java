package ly.step;

import java.util.List;

/**
 * 与Thought相关的内部服务
 * 
 * @author Leon
 * 
 */
public interface ThoughtService {
    /**
     * 查找某个Thought， 按ID号查找
     * 
     * @param id
     * @return 如果返回null， 则意味着没有找到
     */
    public Thought findById(long id);

    /**
     * 查找用户的Thought列表， 也就是时间线。按时间倒序排列
     * 
     * @param userId
     *            用户的ID
     * @param sinceId
     *            开始查询的ID, 结果中的ID都会大于这个值。
     * @param maxId
     *            最多查询的ID, 结果中的ID都会小于这个值。
     * @param limit
     *            结果最多返回的内容
     * @return 查询结果，如果没有符合条件的记录。则会返回一个空的List对象
     */
    public List<Thought> findByUser(long userId, long sinceId, long maxId,
	    int limit);

    /**
     * 发送新的Thought
     * 
     * @param thought
     *            用户发送的Thought
     * @return thought的ID号
     */
    public long post(Thought thought);
}
