package net.okdi.apiV4.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="expCustomerInfo")
public class ExpCustomerInfo {
    private Long id;//'主键ID',

	private Long compId;//'站点ID',

	private Short customerType;//0：客户、1：快递员、2：电商、3：社区小店、4：公司前台、5：收发室、6：小区物业

	private Short cooperativeState;//'合作状态 0:已合作,1:未合作',

	private String customerName;//'客户名称',

	private Byte gender;//'客户性别 (1:男 2:女 null:未知)',
	
	private String customerNameSpell;//'客户名称拼音',

	private String customerPhone;//'客户电话',

	private Long townId;//'乡镇id',

	private String townName;//'乡镇名称：省-市-区县-乡镇',


	private String detailedAddress;//详细地址',
	private BigDecimal longitude;//'经度',

	private BigDecimal latitude;//'纬度',

	private Long erpCustomerId;//'客户ERP主键id',

	private Short deleteFlag;//'删除标识 0未删除 1已删除',

	private Date createTime;//创建时间',

	private Date updateTime;//'更新时间',

	private Long discountGroupId;//'优惠组id',

	private BigDecimal agencyFee;//'代收款费率',

	private Short settleType;//'结算方式 1、月结 2、到付 3、现结',

	private Long casMemberId;//'客户对应CAS_ID',
	
	private String iphoneTwo;//'第二个手机号',
	private String compAddress;//'公司地址',
	private String iphoneThree;//'第三个手机号',
	private String addressTwo;//'第二个地址',
	private String addressThress;//'第三个地址',
	private Long memberId;//'收派员的memberId',

	private Byte isOkdit;//是否是好递快递员 0否 1 是
	
	
	public Byte getIsOkdit() {
		return isOkdit;
	}

	public void setIsOkdit(Byte isOkdit) {
		this.isOkdit = isOkdit;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public String getIphoneTwo() {
		return iphoneTwo;
	}

	public void setIphoneTwo(String iphoneTwo) {
		this.iphoneTwo = iphoneTwo;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}

	public String getIphoneThree() {
		return iphoneThree;
	}

	public void setIphoneThree(String iphoneThree) {
		this.iphoneThree = iphoneThree;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getAddressThress() {
		return addressThress;
	}

	public void setAddressThress(String addressThress) {
		this.addressThress = addressThress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Short getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	public Short getCooperativeState() {
		return cooperativeState;
	}

	public void setCooperativeState(Short cooperativeState) {
		this.cooperativeState = cooperativeState;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNameSpell() {
		return customerNameSpell;
	}

	public void setCustomerNameSpell(String customerNameSpell) {
		this.customerNameSpell = customerNameSpell;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public Long getErpCustomerId() {
		return erpCustomerId;
	}

	public void setErpCustomerId(Long erpCustomerId) {
		this.erpCustomerId = erpCustomerId;
	}

	public Short getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Long getDiscountGroupId() {
		return discountGroupId;
	}

	public void setDiscountGroupId(Long discountGroupId) {
		this.discountGroupId = discountGroupId;
	}

	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public Short getSettleType() {
		return settleType;
	}

	public void setSettleType(Short settleType) {
		this.settleType = settleType;
	}

	public Long getCasMemberId() {
		return casMemberId;
	}

	public void setCasMemberId(Long casMemberId) {
		this.casMemberId = casMemberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

}