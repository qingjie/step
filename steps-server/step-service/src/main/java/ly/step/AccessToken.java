package ly.step;

import java.util.Date;

/**
 * 用户的身份凭证
 * 
 * @author Leon
 * 
 */
public class AccessToken {

    public static class Builder {
	private String accessToken;
	private long userId;
	private Date createdAt;
	private int expiredInSecond;

	private Builder() {
	}

	private Builder(AccessToken template) {
	    this.accessToken = template.accessToken;
	    this.userId = template.userId;
	    this.createdAt = template.createdAt;
	    this.expiredInSecond = template.expiredInSecond;
	}

	public AccessToken build() {
	    return new AccessToken(this);
	}

	public String getAccessToken() {
	    return accessToken;
	}

	public Date getCreatedAt() {
	    return createdAt;
	}

	public int getExpiredInSecond() {
	    return expiredInSecond;
	}

	public long getUserId() {
	    return userId;
	}

	public Builder setAccessToken(String accessToken) {
	    this.accessToken = accessToken;
	    return this;
	}

	public Builder setCreatedAt(Date createdAt) {
	    this.createdAt = createdAt;
	    return this;
	}

	public Builder setExpiredInSecond(int expiredInSecond) {
	    this.expiredInSecond = expiredInSecond;
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

    private final String accessToken;
    private final long userId;
    private final Date createdAt;
    public final int expiredInSecond;

    private AccessToken(Builder builder) {
	this.accessToken = builder.accessToken;
	this.userId = builder.userId;
	this.createdAt = builder.createdAt;
	this.expiredInSecond = builder.expiredInSecond;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}
	else {
	    if (obj instanceof AccessToken) {
		if (this.accessToken != null) {
		    return this.accessToken
			    .equals(((AccessToken) obj).accessToken);
		}
		else {
		    return super.equals(obj);
		}
	    }
	    else {
		return false;
	    }
	}
    }

    public String getAccessToken() {
	return accessToken;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public int getExpiredInSecond() {
	return expiredInSecond;
    }

    public long getUserId() {
	return userId;
    }

    @Override
    public int hashCode() {
	if (this.accessToken == null) {
	    return super.hashCode();
	}
	else {
	    return this.accessToken.hashCode();
	}

    }

    /**
     * 计算这个Access Token是否过期了
     * 
     * @return 这个Access Token是否过期了。如果这个Access Token没有 Created At属性，那么就永久不过期
     */
    public boolean isExpired() {
	if (this.getCreatedAt() != null) {
	    Date now = new Date();
	    Date expired = new Date(this.getCreatedAt().getTime()
		    + this.expiredInSecond * 1000);
	    return !now.before(expired);
	}
	else {
	    return false;
	}
    }

    public Builder toBuilder() {
	return new Builder(this);
    }
}
