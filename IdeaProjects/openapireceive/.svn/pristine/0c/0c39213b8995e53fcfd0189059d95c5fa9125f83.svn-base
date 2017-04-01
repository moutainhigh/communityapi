package net.okdi.apiV4.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="basCustomerInfo")
public class BasCustomerInfo {
	
	private Long uid;
	/**
	 * 客户姓名
	 */
	private String userName;
	/**
	 * 客户手机号
	 */
	private String mobilePhone;
	/**
	 * 客户memberId
	 */
	private Long memberId;
	/**
	 * 客户最新地址
	 */
	private String recentAddress;
	/**
	 * 客户地址列表（多个）
	 */
	private List<String> addressList = new ArrayList<String>();
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getRecentAddress() {
		return recentAddress;
	}
	public void setRecentAddress(String recentAddress) {
		this.recentAddress = recentAddress;
	}
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
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
	
	
	
}
