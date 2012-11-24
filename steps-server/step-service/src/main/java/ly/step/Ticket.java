package ly.step;

import java.util.Date;

/**
 * 用户的身份凭证
 * 
 * @author Leon
 * 
 */
public class Ticket {
    public static class Builder {
	private String code;
	private long userId;
	private Date createdAt;

	private Builder() {
	}

	private Builder(Ticket template) {
	    this.code = template.code;
	    this.userId = template.userId;
	    this.createdAt = template.createdAt;
	}

	public Ticket build() {
	    return new Ticket(this);
	}

	public String getCode() {
	    return code;
	}

	public Date getCreatedAt() {
	    return createdAt;
	}

	public long getUserId() {
	    return userId;
	}

	public Builder setCode(String code) {
	    this.code = code;
	    return this;
	}

	public Builder setCreatedAt(Date createdAt) {
	    this.createdAt = createdAt;
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

    private String code;
    private long userId;
    private Date createdAt;

    private Ticket() {
    }

    private Ticket(Builder builder) {
	this.code = builder.code;
	this.userId = builder.userId;
	this.createdAt = builder.createdAt;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}
	else {
	    if (obj instanceof Ticket) {
		if (this.code != null) {
		    return this.code.equals(((Ticket) obj).code);
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

    public String getCode() {
	return code;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public long getUserId() {
	return userId;
    }

    @Override
    public int hashCode() {
	if (this.code == null) {
	    return super.hashCode();
	}
	else {
	    return this.code.hashCode();
	}

    }

    public Builder toBuilder() {
	return new Builder(this);
    }
}
