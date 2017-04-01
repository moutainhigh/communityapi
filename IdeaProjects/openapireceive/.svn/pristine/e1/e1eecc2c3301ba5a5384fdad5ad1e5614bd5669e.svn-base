package net.okdi.api.entity;


import java.sql.Timestamp;

import net.okdi.core.util.PubMethod;


/**
 * BasVerifyRecored entity. @author MyEclipse Persistence Tools
 */

public class BasVerifyRecored implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private Long memberId;
	private String verifyType;
	private String verifyAccount;
	private String verifyCode;
	private String status;
	private String content;
	private Timestamp modifyTime;
	private Timestamp createTime;
	private String busineType;

	// Constructors

	/** default constructor */
	public BasVerifyRecored() {
	}

	public BasVerifyRecored(Long memberId, String verifyAccount, String verifyCode, String content, String busineType) {
		this.memberId = memberId;
		this.verifyType = "0";
		this.verifyAccount = verifyAccount;
		this.verifyCode = verifyCode;
		this.status = "0";
		this.content = content;
		this.busineType = busineType;
		this.modifyTime = PubMethod.getSysTimestamp();
		this.createTime = PubMethod.getSysTimestamp();
	}

	/** full constructor */
	public BasVerifyRecored(Long id, Long memberId, String verifyType, String verifyAccount, String verifyCode, String status, String content, Timestamp modifyTime,
			Timestamp createTime) {
		this.id = id;
		this.memberId = memberId;
		this.verifyType = verifyType;
		this.verifyAccount = verifyAccount;
		this.verifyCode = verifyCode;
		this.status = status;
		this.content = content;
		this.modifyTime = modifyTime;
		this.createTime = createTime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getVerifyType() {
		return this.verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getVerifyAccount() {
		return this.verifyAccount;
	}

	public void setVerifyAccount(String verifyAccount) {
		this.verifyAccount = verifyAccount;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getBusineType() {
		return busineType;
	}

	public void setBusineType(String busineType) {
		this.busineType = busineType;
	}

}
