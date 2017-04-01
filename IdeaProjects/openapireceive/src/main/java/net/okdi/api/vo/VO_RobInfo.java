package net.okdi.api.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class VO_RobInfo {

	private List<Map<String,Object>> parcelData;//包裹信息
	
	private Short broadcastType;//广播来源1:个人端2电商管家
	
	private Long broadcastId;//广播id
	
	private Long quotationId;//报价id
	
	private Long quotation;//报价金额
	
	private Long countdown;//倒计时 
	
	private Long takeMemberId;//指定的取件员id
	
	private Long loginMemberId;//当前登录人的id
	
	private String takeMemberName;//指定的取件员姓名
	
	private Long distance;//距离
	
	private Long senderAddressId;//发件地址id
	
	private String senderAddressName;//发件地址
	
	private String senderDetailAddressName;//发件详细地址
	
    private Integer totalCount;//总数量

    private Double totalWeight;//总重量
	
    private Date createTime;//创建时间
    
    private String broadcastRemark;//广播备注
    
    private Short broadcastStatus;//抢单状态 1 已抢 代表已报价 2未抢 


    
	public Short getBroadcastType() {
		return broadcastType;
	}

	public void setBroadcastType(Short broadcastType) {
		this.broadcastType = broadcastType;
	}

	public Long getLoginMemberId() {
		return loginMemberId;
	}

	public void setLoginMemberId(Long loginMemberId) {
		this.loginMemberId = loginMemberId;
	}

	public String getTakeMemberName() {
		return takeMemberName;
	}

	public void setTakeMemberName(String takeMemberName) {
		this.takeMemberName = takeMemberName;
	}

	public Short getBroadcastStatus() {
		return broadcastStatus;
	}

	public void setBroadcastStatus(Short broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	public Long getSenderAddressId() {
		return senderAddressId;
	}

	public void setSenderAddressId(Long senderAddressId) {
		this.senderAddressId = senderAddressId;
	}

	public String getSenderAddressName() {
		return senderAddressName;
	}

	public void setSenderAddressName(String senderAddressName) {
		this.senderAddressName = senderAddressName;
	}

	public String getSenderDetailAddressName() {
		return senderDetailAddressName;
	}

	public void setSenderDetailAddressName(String senderDetailAddressName) {
		this.senderDetailAddressName = senderDetailAddressName;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBroadcastRemark() {
		return broadcastRemark;
	}

	public void setBroadcastRemark(String broadcastRemark) {
		this.broadcastRemark = broadcastRemark;
	}

	public Long getBroadcastId() {
		return broadcastId;
	}

	public void setBroadcastId(Long broadcastId) {
		this.broadcastId = broadcastId;
	}

	public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}

	public List<Map<String, Object>> getParcelData() {
		return parcelData;
	}

	public void setParcelData(List<Map<String, Object>> parcelData) {
		this.parcelData = parcelData;
	}

	public Long getQuotation() {
		return quotation;
	}

	public void setQuotation(Long quotation) {
		this.quotation = quotation;
	}

	public Long getCountdown() {
		return countdown;
	}

	public void setCountdown(Long countdown) {
		this.countdown = countdown;
	}

	public Long getTakeMemberId() {
		return takeMemberId;
	}

	public void setTakeMemberId(Long takeMemberId) {
		this.takeMemberId = takeMemberId;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
