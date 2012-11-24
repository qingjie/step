package ly.step;

/**
 * User 与 Thought 的关系
 * 
 * @author Leon
 * 
 */
public class UserToThought {

    public static class Builder {
	private long userId;
	private long thoughtId;

	private Builder() {
	}

	private Builder(UserToThought template) {
	    this.userId = template.userId;
	    this.thoughtId = template.thoughtId;
	}

	public UserToThought build() {
	    return new UserToThought(this);
	}

	public Builder setThoughtId(long thoughtId) {
	    this.thoughtId = thoughtId;
	    return this;
	}

	public Builder setUserId(long userId) {
	    this.userId = userId;
	    return this;
	}
    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private long userId;
    private long thoughtId;

    private UserToThought() {
    }

    private UserToThought(Builder builder) {
	this.userId = builder.userId;
	this.thoughtId = builder.thoughtId;
    }

    public long getThoughtId() {
	return thoughtId;
    }

    public long getUserId() {
	return userId;
    }

    public Builder toBuilder() {
	return new Builder(this);
    }
}
