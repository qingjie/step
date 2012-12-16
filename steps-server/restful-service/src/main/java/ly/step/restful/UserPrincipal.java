package ly.step.restful;

import java.security.Principal;

import ly.step.User;

/**
 * 用户的身份
 * 
 * @author Leon
 * 
 */
public class UserPrincipal implements Principal {

    private final User user;

    public UserPrincipal(User user) {
	super();
	this.user = user;
    }

    @Override
    public String getName() {
	return user.getName();
    }

    public User getUser() {
	return user;
    }

}
