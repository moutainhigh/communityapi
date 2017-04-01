package net.okdi.api.entity;

import java.util.Date;

public class ParSmsAudit {
	  private Long id;
	  
	  private String receiverMobile;
	  
	  private String memberMobile;
	  
	  private Long memberId;
	  
	  private Short smsSource;
	  
	  private Short phoneType;
	  
	  private Date  createTime;
	  
	  private Short sendSuccess; 

	  private String  sendVersion;
	  
	  
	public Short getSendSuccess() {
		return sendSuccess;
	}

	public void setSendSuccess(Short sendSuccess) {
		this.sendSuccess = sendSuccess;
	}

	public String getSendVersion() {
		return sendVersion;
	}

	public void setSendVersion(String sendVersion) {
		this.sendVersion = sendVersion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Short getSmsSource() {
		return smsSource;
	}

	public void setSmsSource(Short smsSource) {
		this.smsSource = smsSource;
	}

	public Short getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Short phoneType) {
		this.phoneType = phoneType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}