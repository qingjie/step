package ly.step;

import java.util.Date;

/**
 * 评论
 * 
 * @author Leon
 * 
 */
public class Comment {
    public static class Builder {
	private long id;
	private long ThoughtId;
	private String text;
	private Date createdAt;
	private long authorId;

	private Builder() {
	}

	private Builder(Comment template) {
	    this.id = template.id;
	    this.ThoughtId = template.ThoughtId;
	    this.text = template.text;
	    this.createdAt = template.createdAt;
	    this.authorId = template.authorId;
	}

	public Comment build() {
	    return new Comment(this);
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

	public long getThoughtId() {
	    return ThoughtId;
	}

	public Builder setAuthor(long authorId) {
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

	public Builder setThoughtId(long ThoughtId) {
	    this.ThoughtId = ThoughtId;
	    return this;
	}

    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private long id;
    private long ThoughtId;
    private String text;
    private Date createdAt;
    private long authorId;

    private Comment() {
    }

    private Comment(Builder builder) {
	this.id = builder.id;
	this.ThoughtId = builder.ThoughtId;
	this.text = builder.text;
	this.createdAt = builder.createdAt;
	this.authorId = builder.authorId;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	else {
	    if (obj instanceof Comment) {
		return this.id == ((Comment) obj).id;
	    }
	    else {
		return false;
	    }
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

    public long getThoughtId() {
	return ThoughtId;
    }

    @Override
    public int hashCode() {
	return Long.valueOf(this.id).hashCode();
    }

    public Builder toBuilder() {
	return new Builder(this);
    }
}
