package ly.step.impl;

import ly.step.UserCredential;

/**
 * 用户凭证的数据访问对象
 * 
 * @author Leon
 * 
 */
public interface UserCredentialDao {
    /**
     * 按用户名查找用户的凭据
     * 
     * @param username
     *            用户名
     * @return 用户的凭据。如果找到了
     */
    public UserCredential findByUsername(String username);

    /**
     * 保存用户的凭据
     * 
     * @param build
     */
    public void save(UserCredential userCrendential);
}
