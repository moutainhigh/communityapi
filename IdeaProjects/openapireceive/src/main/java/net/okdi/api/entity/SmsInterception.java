package net.okdi.api.entity;

import java.util.Date;

public class SmsInterception {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Long memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private String sendPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.wrong_number
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Integer wrongNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.disable_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Date disableTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.disable_type
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Integer disableType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception.update_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.id
     *
     * @return the value of sms_interception.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.id
     *
     * @param id the value for sms_interception.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.member_id
     *
     * @return the value of sms_interception.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.member_id
     *
     * @param memberId the value for sms_interception.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.send_phone
     *
     * @return the value of sms_interception.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public String getSendPhone() {
        return sendPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.send_phone
     *
     * @param sendPhone the value for sms_interception.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.wrong_number
     *
     * @return the value of sms_interception.wrong_number
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Integer getWrongNumber() {
        return wrongNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.wrong_number
     *
     * @param wrongNumber the value for sms_interception.wrong_number
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setWrongNumber(Integer wrongNumber) {
        this.wrongNumber = wrongNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.disable_time
     *
     * @return the value of sms_interception.disable_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Date getDisableTime() {
        return disableTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.disable_time
     *
     * @param disableTime the value for sms_interception.disable_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setDisableTime(Date disableTime) {
        this.disableTime = disableTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.disable_type
     *
     * @return the value of sms_interception.disable_type
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Integer getDisableType() {
        return disableType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.disable_type
     *
     * @param disableType the value for sms_interception.disable_type
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setDisableType(Integer disableType) {
        this.disableType = disableType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.create_time
     *
     * @return the value of sms_interception.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.create_time
     *
     * @param createTime the value for sms_interception.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception.update_time
     *
     * @return the value of sms_interception.update_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception.update_time
     *
     * @param updateTime the value for sms_interception.update_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}