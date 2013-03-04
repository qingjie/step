package ly.step;

import java.util.Date;

/**
 * 描述了一个设备上的客户端
 * 
 * @author Leon
 * 
 */
public class Device {
    public static final class Builder {

	private String deviceId;
	private String meta;
	private Date registeredAt;

	private Builder() {
	}

	private Builder(Device device) {
	    this.deviceId = device.deviceId;
	    this.meta = device.meta;
	    this.registeredAt = device.registeredAt;
	}

	public Device build() {
	    return new Device(this);
	}

	public String getDeviceId() {
	    return deviceId;
	}

	public String getMeta() {
	    return meta;
	}

	public Date getRegisteredAt() {
	    return registeredAt;
	}

	public Builder setDeviceId(String deviceId) {
	    this.deviceId = deviceId;
	    return this;
	}

	public Builder setMeta(String meta) {
	    this.meta = meta;
	    return this;
	}

	public Builder setRegisteredAt(Date registeredAt) {
	    this.registeredAt = registeredAt;
	    return this;
	}

    }

    public static Builder newBuilder() {
	return new Builder();
    }

    private final String deviceId;
    private final String meta;

    private final Date registeredAt;

    public Device(Builder builder) {
	this.deviceId = builder.deviceId;
	this.meta = builder.meta;
	this.registeredAt = builder.registeredAt;
    }

    public String getDeviceId() {
	return deviceId;
    }

    public String getMeta() {
	return meta;
    }

    public Date getRegisteredAt() {
	return registeredAt;
    }

    public Builder toBuilder() {
	return new Builder(this);
    }

}
