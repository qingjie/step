package ly.step;

/**
 * 系统中的用户
 * 
 * @author Leon
 * 
 */
public class User {

    public static class Builder {
	private long id;
	private String name;

	private Builder() {
	}

	private Builder(User user) {
	    this.id = user.id;
	    this.name = user.name;
	}

	public User build() {
	    return new User(this);
	}

	public long getId() {
	    return id;
	}

	public String getName() {
	    return name;
	}

	public Builder setId(long id) {
	    this.id = id;
	    return this;
	}

	public Builder setName(String name) {
	    this.name = name;
	    return this;
	}
    }

    /**
     * 创建一个用户构造User对象的Builder对象
     * 
     * @return
     */
    public static Builder newBuilder() {
	return new Builder();
    }

    private long id;
    private String name;

    private User() {
    }

    private User(Builder builder) {
	this.id = builder.id;
	this.name = builder.name;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	else {
	    if (obj instanceof User) {
		return this.id == ((User) obj).id;
	    }
	    else {
		return false;
	    }
	}
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	return Long.valueOf(id).hashCode();
    }

    /**
     * 基于当前User对象创建一个新的Builder对象
     * 
     * @return
     */
    public Builder toBuilder() {
	return new Builder(this);
    }
}
