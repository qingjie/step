package ly.step.impl;

import ly.step.AccessToken;
import ly.step.impl.InvalidAccessTokenException.AccessTokenNotFoundException;

/**
 * 票据的数据访问对象
 * 
 * @author Leon
 * 
 */
public interface AccessTokenDao {

    /**
     * 创建一个新的Ticket
     * 
     * @param accessToken
     * @return 新的Ticket对象
     */
    AccessToken create(AccessToken accessToken);

    /**
     * 根据 access Token 查找具体的 AccessToken 对象
     * 
     * @param accessToken
     *            access token 的 String
     * @return access Token 的具体对象，如果找到的话
     * @throws AccessTokenNotFoundException
     *             没有找到哇！
     */
    AccessToken findByAccessToken(String accessToken)
	    throws InvalidAccessTokenException.AccessTokenNotFoundException;
}
