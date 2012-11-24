package ly.step;

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
}
