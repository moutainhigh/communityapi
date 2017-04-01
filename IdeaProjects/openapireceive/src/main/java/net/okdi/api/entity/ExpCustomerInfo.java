package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ExpCustomerInfo {
    private Long id;

	private Long compId;

	private Short customerType;//0：客户、1：快递员、2：电商、3：社区小店、4：公司前台、5：收发室、6：小区物业

	private Short cooperativeState;

	private String customerName;

	private Byte gender;
	
	private String customerNameSpell;

	private String customerPhone;

	private Long townId;

	private String townName;


	private String detailedAddress;
	private BigDecimal longitude;

	private BigDecimal latitude;

	private Long erpCustomerId;

	private Short deleteFlag;

	private Date createTime;

	private Date updateTime;

	private Long discountGroupId;

	private BigDecimal agencyFee;

	private Short settleType;

	private Long casMemberId;
	
	private String iphoneTwo;
	private String compAddress;
	private String iphoneThree;
	private String addressTwo;
	private String addressThress;
	private Long memberId;

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