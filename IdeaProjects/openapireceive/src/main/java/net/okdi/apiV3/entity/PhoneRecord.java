package net.okdi.apiV3.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "PhoneRecord")
public class PhoneRecord {

	@Id
	private String id;//主键
	private String sendPhone; //发送者电话
	private String receivePhone; //接收者电话
	 @DateTimeFormat
	private Date startTime; //时间
	private String flag; //标识 1：本机电话  2：两端呼
	private String duration ;//持续时间
	private String fee ;//费用
	
	
	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getReceivePhone() {
		return receivePhone;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
    

}
