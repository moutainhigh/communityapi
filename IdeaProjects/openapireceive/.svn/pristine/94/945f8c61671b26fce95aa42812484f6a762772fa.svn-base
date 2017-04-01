package net.okdi.apiV3.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WechatAddress")
public class WechatAddress {

	@Id
	private Long id;//主键
	
	private Date createTime;//创建时间
	
	private Long memberId;//创建人ID
	
	private String senderName;//发件人name

	private String senderPhone;//发件人电话
	
	private Long  addresseeTownId;//发件人省市区/县Id
	
	private String addresseeTownName;// 发件人省市区/县名称
	

	private String addresseeAddress;//发件人详细地址
	
	private Short defaultMark;//是否默认	0 默认  1 非默认
	
	private Short flag;//地址类型 1:收件地址 2:发件地址 

	
	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public Long getAddresseeTownId() {
		return addresseeTownId;
	}

	public void setAddresseeTownId(Long addresseeTownId) {
		this.addresseeTownId = addresseeTownId;
	}

	public String getAddresseeTownName() {
		return addresseeTownName;
	}

	public void setAddresseeTownName(String addresseeTownName) {
		this.addresseeTownName = addresseeTownName;
	}

	public String getAddresseeAddress() {
		return addresseeAddress;
	}

	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress;
	}

	public Short getDefaultMark() {
		return defaultMark;
	}

	public void setDefaultMark(Short defaultMark) {
		this.defaultMark = defaultMark;
	}

	
}
