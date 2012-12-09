package ly.step;

import java.util.List;

/**
 * 与用户身份相关的内部服务
 * 
 * @author Leon
 * 
 */
public interface UserService {
    /**
     * 通过ID查找用户
     * 
     * @param id
     *            ， 用户的ID
     * @return 如果返回null， 则意味着没有找到
     */
    public User findById(long id);

    /**
     * 通过票据的code来查找用户
     * 
     * @param code
     *            票据的code
     * @return 如果返回null， 则意味着没有找到
     */
    public User findByTicketCode(String code);

    /**
     * 查找用户的朋友们
     * 
     * @param userId
     *            用户的ID
     * @return 用户的朋友关系列表。按关系创建时间倒序排列。最新的在最前面。
     */
    public List<Long> findFriend(long userId);

    /**
     * 两个用户缔结好友关系
     * 
     * @param friendA
     *            好友关系中的 A
     * @param friendB
     *            好友关系中的 B
     */
    public void makeFriend(long friendA, long friendB);

    /**
     * A 和 B 不再是好友了
     * 
     * @param friendA
     *            要分开的A
     * @param friendB
     *            要分开的B
     */
    public void separate(long friendA, long friendB);
}
