package ly.step.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ly.step.AccessToken;
import ly.step.AuthenticationException;
import ly.step.User;
import ly.step.UserCredential;
import ly.step.UserService;
import ly.step.UsernameAlreadyRegisteredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceDefaultImpl implements UserService {

    private static final Logger logger = LoggerFactory
	    .getLogger(UserServiceDefaultImpl.class);
    // access token 的默认过期时间是 7 天
    public static final int ACCESS_TOKEN_DEFAULT_EXPIRE_IN_SECONDS = 60 * 60 * 24 * 7;

    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private AccessTokenDao accessTokenDao;
    @Autowired
    private UserCredentialDao userCredentialDao;

    @Override
    public AccessToken allocateAccessToken(long userId) {
	String token = UUID.randomUUID().toString().replaceAll("-", "");
	AccessToken accessToken = accessTokenDao.create(AccessToken
	        .newBuilder()
	        .setAccessToken(token)
	        .setCreatedAt(new Date())
	        .setUserId(userId)
	        .setExpiredInSecond(ACCESS_TOKEN_DEFAULT_EXPIRE_IN_SECONDS)
	        .build());
	return accessToken;
    }

    @Override
    public User auth(String username, String password)
	    throws AuthenticationException {
	UserCredential userCredential = userCredentialDao
	        .findByUsername(username);
	if (userCredential == null || !userCredential.checkPassword(password)) {
	    throw new AuthenticationException();
	}
	else {
	    User user = userDao.findById(userCredential.getUserId());
	    if (user == null) {
		throw new AuthenticationException();
	    }
	    else {
		return user;
	    }
	}
    }

    @Override
    public User findByAccessToken(String accessToken)
	    throws AuthenticationException {
	AccessToken ticket = accessTokenDao.findByAccessToken(accessToken);
	if (ticket == null || ticket.isExpired()) {
	    throw new AuthenticationException();
	}
	else {
	    User user = userDao.findById(ticket.getUserId());
	    if (user == null) {
		throw new AuthenticationException();
	    }
	    else {
		return user;
	    }
	}
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
	User result = userDao.findById(id);
	if (result == null) {
	    throw new UserNotFoundException("@[id=" + id + "]");
	}
	else {
	    return result;
	}
    }

    @Override
    public List<Long> findFriend(long userId) {
	return friendDao.findFriendsByUserId(userId);
    }

    @Override
    public void makeFriend(long friendA, long friendB) {
	friendDao.save(friendA, friendB, new Date());
    }

    @Override
    @Transactional
    public User register(User user, String password)
	    throws UsernameAlreadyRegisteredException {
	logger.info("User register: {}", user.getName());
	User result = userDao.create(user);
	userCredentialDao
	        .save(UserCredential
	                .newBuilder()
	                .setUserId(result.getId())
	                .setUsername(user.getName())
	                .setPassword(password)
	                .build());
	return result;
    }

    @Override
    public void separate(long friendA, long friendB) {
	friendDao.remove(friendA, friendB);
    }

}
