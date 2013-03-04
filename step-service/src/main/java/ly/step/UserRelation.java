package ly.step;

import java.util.Date;

/**
 * 用户的关系
 * 
 * @author Dante
 * 
 */
public class UserRelation {

    public static class Builder {
	private long friendA;
	private long friendB;
	private Date createdAt;

	private Builder() {
	}

	public UserRelation build() {
	    return new UserRelation(this);
	}

	private Builder(UserRelation userRelation) {
	    this.friendA = userRelation.friendA;
	    this.friendB = userRelation.friendB;
	    this.createdAt = userRelation.createdAt;
	}

	public long getFriendA() {
	    return friendA;
	}

	public Builder setFriendA(long userId) {
	    this.friendA = userId;
	    return this;
	}

	public long getFriendB() {
	    return friendB;
	}

	public Builder setFriendB(long userId) {
	    this.friendB = userId;
	    return this;
	}

	public Date getCreatedAt() {
	    return createdAt;
	}

	public Builder setCreatedAt(Date createdAt) {
	    this.createdAt = createdAt;
	    return this;
	}

    }

    private final long friendA;
    private final long friendB;
    private final Date createdAt;

    public static Builder newBuilder() {
	return new Builder();
    }

    public Builder toBuilder() {
	return new Builder(this);
    }

    private UserRelation(Builder builder) {
	this.friendA = builder.friendA;
	this.friendB = builder.friendB;
	this.createdAt = builder.createdAt;
    }

    public long getFriendA() {
	return friendA;
    }

    public long getFriendB() {
	return friendB;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

}
