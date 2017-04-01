package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="taskRemind")
public class TaskRemind {
	
	private Long uid;	//主键id
	
	private Long memberId; //收派员id
	
	private String memberPhone; //收派员电话号码
	
	private Long taskId; //取件任务id
	
	private String senderPhone; //发件人电话号码
	
	private String senderAddress; //发件人地址
	
	private Short remindStatus; //提醒状态 0.未回复 1.客户回复 2.回复已读
	
	@DateTimeFormat
	private Date createTime;	//创建时间
	
	@DateTimeFormat
	private Date updateTime;	//更新时间
	
	private Short taskStatus;  //取件状态  0.待确认 1.同意  2.拒绝

	private Short isRemove;  //是否删除  0.未删除 1.删除

	private Short isRead;  //是否删除  0.未读1.已读

	private Byte taskSource;//任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王,6:微信端

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public Short getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Short remindStatus) {
		this.remindStatus = remindStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Short getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Short taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Short getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Short isRemove) {
		this.isRemove = isRemove;
	}

	public Byte getTaskSource() {
		return taskSource;
	}

	public void setTaskSource(Byte taskSource) {
		this.taskSource = taskSource;
	}

	public Short getIsRead() {
		return isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}
	
	
}
