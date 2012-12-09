package ly.step.impl;

import ly.step.User;

public interface UserDao {

    /**
     * 按ID读取
     * 
     * @param id
     * @return
     */
    User findById(long id);
}
