package ly.step.impl;

/**
 * 用户不存在！
 * 
 * @author Leon
 * 
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3965816425952863053L;

    private final String username;

    public UserNotFoundException(String username) {
	super();
	this.username = username;
    }

    @Override
    public String getMessage() {
	return username + " not found.";
    }

    public String getUsername() {
	return username;
    }

}
