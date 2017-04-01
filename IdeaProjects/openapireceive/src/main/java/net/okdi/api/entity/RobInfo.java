package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RobInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.broadcast_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Long broadcastId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.broadcast_source
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Short broadcastSource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.parcel_total_count
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Integer parcelTotalCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.parcel_total_weight
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private BigDecimal parcelTotalWeight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.broadcast_remark
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private String broadcastRemark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.broadcast_status
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Short broadcastStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private String senderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_mobile
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private String senderMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_address_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Long senderAddressId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private String senderAddressName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_detail_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private String senderDetailAddressName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_longitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private BigDecimal senderLongitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.sender_latitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private BigDecimal senderLatitude;
    private Date appointTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.quotation_amount
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private BigDecimal quotationAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.quotation_comp_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Long quotationCompId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.quotation_member_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Long quotationMemberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.view_flag
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Short viewFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.create_user
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.create_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rob_broadcast_info.modity_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    private Date modityTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.broadcast_id
     *
     * @return the value of rob_broadcast_info.broadcast_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Long getBroadcastId() {
        return broadcastId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.broadcast_id
     *
     * @param broadcastId the value for rob_broadcast_info.broadcast_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setBroadcastId(Long broadcastId) {
        this.broadcastId = broadcastId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.broadcast_source
     *
     * @return the value of rob_broadcast_info.broadcast_source
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Short getBroadcastSource() {
        return broadcastSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.broadcast_source
     *
     * @param broadcastSource the value for rob_broadcast_info.broadcast_source
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setBroadcastSource(Short broadcastSource) {
        this.broadcastSource = broadcastSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.parcel_total_count
     *
     * @return the value of rob_broadcast_info.parcel_total_count
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Integer getParcelTotalCount() {
        return parcelTotalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.parcel_total_count
     *
     * @param parcelTotalCount the value for rob_broadcast_info.parcel_total_count
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setParcelTotalCount(Integer parcelTotalCount) {
        this.parcelTotalCount = parcelTotalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.parcel_total_weight
     *
     * @return the value of rob_broadcast_info.parcel_total_weight
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public BigDecimal getParcelTotalWeight() {
        return parcelTotalWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.parcel_total_weight
     *
     * @param parcelTotalWeight the value for rob_broadcast_info.parcel_total_weight
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setParcelTotalWeight(BigDecimal parcelTotalWeight) {
        this.parcelTotalWeight = parcelTotalWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.broadcast_remark
     *
     * @return the value of rob_broadcast_info.broadcast_remark
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public String getBroadcastRemark() {
        return broadcastRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.broadcast_remark
     *
     * @param broadcastRemark the value for rob_broadcast_info.broadcast_remark
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setBroadcastRemark(String broadcastRemark) {
        this.broadcastRemark = broadcastRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.broadcast_status
     *
     * @return the value of rob_broadcast_info.broadcast_status
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Short getBroadcastStatus() {
        return broadcastStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.broadcast_status
     *
     * @param broadcastStatus the value for rob_broadcast_info.broadcast_status
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setBroadcastStatus(Short broadcastStatus) {
        this.broadcastStatus = broadcastStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_name
     *
     * @return the value of rob_broadcast_info.sender_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_name
     *
     * @param senderName the value for rob_broadcast_info.sender_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_mobile
     *
     * @return the value of rob_broadcast_info.sender_mobile
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public String getSenderMobile() {
        return senderMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_mobile
     *
     * @param senderMobile the value for rob_broadcast_info.sender_mobile
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_address_id
     *
     * @return the value of rob_broadcast_info.sender_address_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Long getSenderAddressId() {
        return senderAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_address_id
     *
     * @param senderAddressId the value for rob_broadcast_info.sender_address_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderAddressId(Long senderAddressId) {
        this.senderAddressId = senderAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_address_name
     *
     * @return the value of rob_broadcast_info.sender_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public String getSenderAddressName() {
        return senderAddressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_address_name
     *
     * @param senderAddressName the value for rob_broadcast_info.sender_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderAddressName(String senderAddressName) {
        this.senderAddressName = senderAddressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_detail_address_name
     *
     * @return the value of rob_broadcast_info.sender_detail_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public String getSenderDetailAddressName() {
        return senderDetailAddressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_detail_address_name
     *
     * @param senderDetailAddressName the value for rob_broadcast_info.sender_detail_address_name
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderDetailAddressName(String senderDetailAddressName) {
        this.senderDetailAddressName = senderDetailAddressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_longitude
     *
     * @return the value of rob_broadcast_info.sender_longitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public BigDecimal getSenderLongitude() {
        return senderLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_longitude
     *
     * @param senderLongitude the value for rob_broadcast_info.sender_longitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderLongitude(BigDecimal senderLongitude) {
        this.senderLongitude = senderLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.sender_latitude
     *
     * @return the value of rob_broadcast_info.sender_latitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public BigDecimal getSenderLatitude() {
        return senderLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.sender_latitude
     *
     * @param senderLatitude the value for rob_broadcast_info.sender_latitude
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setSenderLatitude(BigDecimal senderLatitude) {
        this.senderLatitude = senderLatitude;
    }
    
    public Date getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.quotation_amount
     *
     * @return the value of rob_broadcast_info.quotation_amount
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public BigDecimal getQuotationAmount() {
        return quotationAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.quotation_amount
     *
     * @param quotationAmount the value for rob_broadcast_info.quotation_amount
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setQuotationAmount(BigDecimal quotationAmount) {
        this.quotationAmount = quotationAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.quotation_comp_id
     *
     * @return the value of rob_broadcast_info.quotation_comp_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Long getQuotationCompId() {
        return quotationCompId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.quotation_comp_id
     *
     * @param quotationCompId the value for rob_broadcast_info.quotation_comp_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setQuotationCompId(Long quotationCompId) {
        this.quotationCompId = quotationCompId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.quotation_member_id
     *
     * @return the value of rob_broadcast_info.quotation_member_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Long getQuotationMemberId() {
        return quotationMemberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.quotation_member_id
     *
     * @param quotationMemberId the value for rob_broadcast_info.quotation_member_id
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setQuotationMemberId(Long quotationMemberId) {
        this.quotationMemberId = quotationMemberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.view_flag
     *
     * @return the value of rob_broadcast_info.view_flag
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Short getViewFlag() {
        return viewFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.view_flag
     *
     * @param viewFlag the value for rob_broadcast_info.view_flag
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setViewFlag(Short viewFlag) {
        this.viewFlag = viewFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.create_user
     *
     * @return the value of rob_broadcast_info.create_user
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.create_user
     *
     * @param createUser the value for rob_broadcast_info.create_user
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.create_time
     *
     * @return the value of rob_broadcast_info.create_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.create_time
     *
     * @param createTime the value for rob_broadcast_info.create_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rob_broadcast_info.modity_time
     *
     * @return the value of rob_broadcast_info.modity_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public Date getModityTime() {
        return modityTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rob_broadcast_info.modity_time
     *
     * @param modityTime the value for rob_broadcast_info.modity_time
     *
     * @mbggenerated Wed Jun 17 13:04:56 CST 2015
     */
    public void setModityTime(Date modityTime) {
        this.modityTime = modityTime;
    }
}