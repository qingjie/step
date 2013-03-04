package ly.step;

/**
 * 用户名已经注册过了
 * 
 * @author Leon
 * 
 */
public class UsernameAlreadyRegisteredException extends
        RuntimeException {
    private static final long serialVersionUID = -8761483644435983522L;
    private final String username;

    public UsernameAlreadyRegisteredException(String username) {
        super();
        this.username = username;
    }

    @Override
    public String getMessage() {
        return username + " is already registered.";
    }

    public String getUsername() {
        return username;
    }

}