package ly.step.impl;

import ly.step.User;
import ly.step.UsernameAlreadyRegisteredException;

public interface UserDao {

    /**
     * 注册新用户
     * 
     * @param user
     * @return
     */
    User create(User user) throws UsernameAlreadyRegisteredException;

    /**
     * 按ID读取
     * 
     * @param id
     * @return
     */
    User findById(long id);
}
