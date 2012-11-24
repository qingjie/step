package ly.step;

import java.util.Date;

/**
 * 一个Tweet， 一个帖子， 人生旅途中的一个想法
 * 
 * @author Leon
 * 
 */
public class Thought {
    public static class Builder {
	private long id;
	private String text;
	private long authorId;
	private Date createdAt;

	private Builder() {
	}

	private Builder(Thought thought) {
	    this.id = thought.id;
	    this.text = thought.text;
	    this.authorId = thought.authorId;
	    this.createdAt = thought.createdAt;
	}

	public Thought build() {
	    return new Thought(this);
	}

	public long getAuthorId() {
	    return authorId;
	}

	public Date getCreatedAt() {
	    return createdAt;
	}

	public long getId() {
	    return id;
	}

	public String getText() {
	    return text;
	}

	public Builder setAuthorId(long authorId) {
	    this.authorId = authorId;
	    return this;
	}

	public Builder setCreatedAt(Date createdAt) {
	    this.createdAt = createdAt;
	    return this;
	}

	public Builder setId(long id) {
	    this.id = id;
	    return this;
	}

	public Builder setText(String text) {
	    this.text = text;
	    return this;
	}

    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private long id;
    private String text;
    private long authorId;
    private Date createdAt;

    private Thought() {
    }

    private Thought(Builder builder) {
	this.id = builder.id;
	this.text = builder.text;
	this.authorId = builder.authorId;
	this.createdAt = builder.createdAt;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Thought) {
	    return id == ((Thought) obj).id;
	}
	else {
	    return false;
	}
    }

    public long getAuthorId() {
	return authorId;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public long getId() {
	return id;
    }

    public String getText() {
	return text;
    }

    @Override
    public int hashCode() {
	return Long.valueOf(id).hashCode();
    }

    public Builder toBuilder() {
	return new Builder(this);
    }

}
