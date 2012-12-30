package ly.step.impl;

/**
 * 不存在的 access token
 * 
 * @author Leon
 * 
 */
public class InvalidAccessTokenException extends RuntimeException {
    public static class AccessTokenExpiredException extends
	    InvalidAccessTokenException {
	private static final long serialVersionUID = 5112008177055145415L;

	public AccessTokenExpiredException(String accessToken) {
	    super(accessToken);
	}

    }

    public static class AccessTokenNotFoundException extends
	    InvalidAccessTokenException {
	private static final long serialVersionUID = 109320204120097057L;

	public AccessTokenNotFoundException(String accessToken) {
	    super(accessToken);
	}

    }

    private static final long serialVersionUID = 5624067735303879823L;

    private final String accessToken;

    public InvalidAccessTokenException(String accessToken) {
	super();
	this.accessToken = accessToken;
    }

    public String getAccessToken() {
	return accessToken;
    }

}
