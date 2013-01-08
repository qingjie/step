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
     * 为用户分配新的 Access Token
     * 
     * @param userId
     *            用户的ID
     * @return 新的 Access Token
     */
    public AccessToken allocateAccessToken(long userId);

    /**
     * 验证用户身份
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return 用户的Entity, 如果验证成功了
     * @throws AuthenticationException
     *             验证失败
     */
    public User auth(String username, String password)
	    throws AuthenticationException;

    /**
     * 通过票据的 access token 来查找用户
     * 
     * @param accessToken
     *            票据的code
     * @return access token 对应的用户
     * @throws AuthenticationException
     *             Access Code 是无效的。可能是 Access Code 不存在或者已经过期了
     */
    public User findByAccessToken(String accessToken)
	    throws AuthenticationException;

    /**
     * 通过ID查找用户
     * 
     * @param id
     *            ， 用户的ID
     * @return 如果返回null， 则意味着没有找到
     */
    public User findById(long id);

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
     * 注册用户
     * 
     * @param user
     *            用户的Entity
     * @param password
     *            密码
     * @return 注册成功后的用户
     */
    public User register(User user, String password)
	    throws UsernameAlreadyRegisteredException;

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
