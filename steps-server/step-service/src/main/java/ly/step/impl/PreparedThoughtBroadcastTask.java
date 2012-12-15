package ly.step.impl;

import java.util.Collections;
import java.util.List;

/**
 * 一个广播任务
 * 
 * @author Leon
 * 
 */
public class PreparedThoughtBroadcastTask {

    public static class Builder {
	private long id;
	private List<Long> recipientList;
	private long thoughtId;

	private Builder() {
	}

	private Builder(PreparedThoughtBroadcastTask preparedBroadcastTask) {
	    this.recipientList = preparedBroadcastTask.recipientList;
	    this.thoughtId = preparedBroadcastTask.thoughtId;
	    this.id = preparedBroadcastTask.id;
	}

	public PreparedThoughtBroadcastTask build() {
	    return new PreparedThoughtBroadcastTask(this);
	}

	public long getId() {
	    return id;
	}

	public List<Long> getRecipientList() {
	    return recipientList;
	}

	public long getThoughtId() {
	    return thoughtId;
	}

	public Builder setId(long id) {
	    this.id = id;
	    return this;
	}

	public Builder setRecipientList(List<Long> recipients) {
	    this.recipientList = Collections.unmodifiableList(recipients);
	    return this;
	}

	public Builder setThoughtId(long thoughtId) {
	    this.thoughtId = thoughtId;
	    return this;
	}
    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private final long id;
    private final List<Long> recipientList;
    private final long thoughtId;

    private PreparedThoughtBroadcastTask(Builder builder) {
	this.recipientList = builder.recipientList;
	this.thoughtId = builder.thoughtId;
	this.id = builder.id;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof PreparedThoughtBroadcastTask && this.id != 0) {
	    return this.id == ((PreparedThoughtBroadcastTask) obj).id;
	}
	return super.equals(obj);
    }

    public long getId() {
	return id;
    }

    public List<Long> getRecipientList() {
	return recipientList;
    }

    public long getThoughtId() {
	return thoughtId;
    }

    @Override
    public int hashCode() {
	if (this.id != 0) {
	    return Long.valueOf(id).hashCode();
	}
	else {
	    return super.hashCode();
	}
    }

    public Builder toBuilder() {
	return new Builder(this);
    }

}
