package ly.step;

import com.google.common.hash.Hashing;

public class UserCredential {

    public static class Builder {

	private String username;
	private String passwordHash;
	private long userId;

	private Builder() {
	}

	private Builder(UserCredential credential) {
	    this.passwordHash = credential.passwordHash;
	    this.userId = credential.userId;
	    this.username = credential.username;
	}

	public UserCredential build() {
	    return new UserCredential(this);
	}

	public String getPasswordHash() {
	    return passwordHash;
	}

	public long getUserId() {
	    return userId;
	}

	public String getUsername() {
	    return username;
	}

	public Builder setPassword(String password) {
	    this.passwordHash = toHash(password);
	    return this;
	}

	public Builder setPasswordHash(String passwordHash) {
	    this.passwordHash = passwordHash;
	    return this;
	}

	public Builder setUserId(long userId) {
	    this.userId = userId;
	    return this;
	}

	public Builder setUsername(String username) {
	    this.username = username;
	    return this;
	}

    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private static String toHash(String password) {
	return Hashing.sha1().hashString(password).toString();
    }

    private final String username;

    private final String passwordHash;

    private final long userId;

    private UserCredential(Builder builder) {
	this.passwordHash = builder.passwordHash;
	this.userId = builder.userId;
	this.username = builder.username;
    }

    public boolean checkPassword(String password) {
	return this.passwordHash.equals(toHash(password));
    }

    public String getPasswordHash() {
	return passwordHash;
    }

    public long getUserId() {
	return userId;
    }

    public String getUsername() {
	return username;
    }

    public Builder toBuilder() {
	return new Builder(this);
    }

}
