package net.okdi.mob.entity;

import java.io.Serializable;
import java.util.Date;

public class MobMemberLogin implements Serializable {
	private static final long serialVersionUID = -8761265997434746462L;

	private Long id;
	private String channelNo;
	private Long channelId;
	private String deviceType;
	private String deviceToken;
	private String version;
	private Long connTime;
	private String address;
	private Date modifyTime;
	private Date createTime;
	private String clientId;
	private String receiveNoticeFlag; // 客户端设置是否接收通知消息，1：接收，0：不接收'
	private String pushType; // 'ext：第三方推送，socket:socket推送'
	
	//为了前台传入参数方便
	private String mobile;//电话号码

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getConnTime() {
		return connTime;
	}

	public void setConnTime(Long connTime) {
		this.connTime = connTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getReceiveNoticeFlag() {
		return receiveNoticeFlag;
	}

	public void setReceiveNoticeFlag(String receiveNoticeFlag) {
		this.receiveNoticeFlag = receiveNoticeFlag;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}