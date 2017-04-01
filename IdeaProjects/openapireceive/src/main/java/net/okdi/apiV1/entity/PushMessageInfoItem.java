package net.okdi.apiV1.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pushMessageInfoItem")
public class PushMessageInfoItem implements Serializable{

	private long batchId; //此BATCH_ID 为PushMessageInfo表中的主键id
	
	private String pushPhone; //推送的手机号
	
	private Short sendType;//发送方式
	
	private Short  reciveType;//接收类型
	
	private Short  openType;//打开类型
	
	private Date createTime;//创建时间
	
	private Date modifyTime;//修改时间
	
	
	
	
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public String getPushPhone() {
		return pushPhone;
	}
	public void setPushPhone(String pushPhone) {
		this.pushPhone = pushPhone;
	}
	
	
	public Short getSendType() {
		return sendType;
	}
	public void setSendType(Short sendType) {
		this.sendType = sendType;
	}
	public Short getReciveType() {
		return reciveType;
	}
	public void setReciveType(Short reciveType) {
		this.reciveType = reciveType;
	}
	public Short getOpenType() {
		return openType;
	}
	public void setOpenType(Short openType) {
		this.openType = openType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
