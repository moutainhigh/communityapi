package net.okdi.api.entity;

import java.util.Date;

public class SmsInterceptionLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception_log.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception_log.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Long memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception_log.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private String sendPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception_log.send_content
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private String sendContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_interception_log.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception_log.id
     *
     * @return the value of sms_interception_log.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception_log.id
     *
     * @param id the value for sms_interception_log.id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception_log.member_id
     *
     * @return the value of sms_interception_log.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception_log.member_id
     *
     * @param memberId the value for sms_interception_log.member_id
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception_log.send_phone
     *
     * @return the value of sms_interception_log.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public String getSendPhone() {
        return sendPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception_log.send_phone
     *
     * @param sendPhone the value for sms_interception_log.send_phone
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception_log.send_content
     *
     * @return the value of sms_interception_log.send_content
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public String getSendContent() {
        return sendContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception_log.send_content
     *
     * @param sendContent the value for sms_interception_log.send_content
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_interception_log.create_time
     *
     * @return the value of sms_interception_log.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_interception_log.create_time
     *
     * @param createTime the value for sms_interception_log.create_time
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}