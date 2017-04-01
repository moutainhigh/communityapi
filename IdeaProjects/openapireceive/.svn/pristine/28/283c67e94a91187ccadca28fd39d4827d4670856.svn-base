package net.okdi.apiV4.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="parTaskInfo")
public class ParTaskInfo {
    private Long uid;
    
    private Byte taskType; //任务类型 0:取件 1:派件 2:自取件 3：销售等非快递

    private Long coopCompId; // 任务受理方站点

    private Long coopNetId;//任务受理方网络

    private Byte parEstimateCount; //包裹预估数

    private BigDecimal parEstimateWeight; //包裹预估重量
    @DateTimeFormat
    private Date appointTime;//预约取件时间

    private String appointDesc;//预约描述

    private Byte taskSource;//任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王,6:微信端

    private Byte taskStatus;//任务状态 0待处理，1已分配，2已完成，3已取消，4微信叫快递-快递员拒绝接单 10已删除

    private Byte taskIsEnd;//任务是否结束  1:结束
    @DateTimeFormat
    private Date taskEndTime;//任务结束时间

    private Long actorMemberId;//执行人员ID 取件员id

    private String actorPhone;//执行人电话

    private String contactName;//联系人姓名
 
    private String contactMobile;//联系人手机

    private String contactTel;//联系人电话

    private Long contactAddressId;//联系人地址id

    private String contactAddress;//详细地址

    private Long customerId;//客户id/发件人id

    private Long contactCasUserId;//联系人CAS_ID

    private Long contactCompId; //联系人公司ID
    @DateTimeFormat
    private Date createTime;	//创建时间

    private Long createUserId; //创建人ID

    private Byte taskFlag; // 0：正常,1：抢单  
    @DateTimeFormat
    private Date modifyTime; //最后修改时间

    private BigDecimal contactAddrLongitude; //联系人地址的经度信息

    private BigDecimal contactAddrLatitude; //联系人地址的纬度信息
    
    private BigDecimal parEstimatePrice; //预估报价
    
    private Long orderNum;//订单号--
    
    private Short payStatus;//付款状态：初始化为0-未确定，10 现金支付       20 微信支付完成并且财务系统同步完成    21 微信支付完成并且财务系统同步失败       22 微信支付失败同步财务成功    23 微信支付失败同步财务失败   

    private String thirdId; //第三方id
    
    private String payNum;//第三方账单ID
    
    private BigDecimal amount;//金额
    
    private String taskRemark;//同意或拒绝的原因描述'
    
    private Byte isRemove;//是否删除 0否 1是
    
    private String mobilePhone;//----片键---2016年5月26日10:07:10


    //添加日期2017年02月05日16:43:17 by zj 新原型中在订单列表中增加了是否标记, 如果是空或者为0 都是为标记, 1:的才是已标记
    private Short tagFalg;// 标记标示 0:未标记, 1:已标记
    //添加日期2017年02月05日16:43:17 by zj 新原型中在订单列表中增加了标记内容
    private String comment;//  标记内容    如：易碎 ， 易爆



    //取消原因
    private String cancelReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
    public Short getTagFalg() {
        return tagFalg;
    }

    public void setTagFalg(Short tagFalg) {
        this.tagFalg = tagFalg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Byte isRemove) {
		this.isRemove = isRemove;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Byte getTaskType() {
        return taskType;
    }

    public void setTaskType(Byte taskType) {
        this.taskType = taskType;
    }

    public Long getCoopCompId() {
        return coopCompId;
    }

    public void setCoopCompId(Long coopCompId) {
        this.coopCompId = coopCompId;
    }

    public Long getCoopNetId() {
        return coopNetId;
    }

    public void setCoopNetId(Long coopNetId) {
        this.coopNetId = coopNetId;
    }

    public Byte getParEstimateCount() {
        return parEstimateCount;
    }

    public void setParEstimateCount(Byte parEstimateCount) {
        this.parEstimateCount = parEstimateCount;
    }

    public BigDecimal getParEstimateWeight() {
        return parEstimateWeight;
    }

    public void setParEstimateWeight(BigDecimal parEstimateWeight) {
        this.parEstimateWeight = parEstimateWeight;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointDesc() {
        return appointDesc;
    }

    public void setAppointDesc(String appointDesc) {
        this.appointDesc = appointDesc;
    }

    public Byte getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(Byte taskSource) {
        this.taskSource = taskSource;
    }

    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Byte getTaskIsEnd() {
        return taskIsEnd;
    }

    public void setTaskIsEnd(Byte taskIsEnd) {
        this.taskIsEnd = taskIsEnd;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Long getActorMemberId() {
        return actorMemberId;
    }

    public void setActorMemberId(Long actorMemberId) {
        this.actorMemberId = actorMemberId;
    }

    public String getActorPhone() {
        return actorPhone;
    }

    public void setActorPhone(String actorPhone) {
        this.actorPhone = actorPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Long getContactAddressId() {
        return contactAddressId;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getContactCasUserId() {
        return contactCasUserId;
    }

    public void setContactCasUserId(Long contactCasUserId) {
        this.contactCasUserId = contactCasUserId;
    }

    public Long getContactCompId() {
        return contactCompId;
    }

    public void setContactCompId(Long contactCompId) {
        this.contactCompId = contactCompId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Byte getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Byte taskFlag) {
        this.taskFlag = taskFlag;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public BigDecimal getContactAddrLongitude() {
        return contactAddrLongitude;
    }

    public void setContactAddrLongitude(BigDecimal contactAddrLongitude) {
        this.contactAddrLongitude = contactAddrLongitude;
    }

    public BigDecimal getContactAddrLatitude() {
        return contactAddrLatitude;
    }

    public void setContactAddrLatitude(BigDecimal contactAddrLatitude) {
        this.contactAddrLatitude = contactAddrLatitude;
    }
    
    public BigDecimal getParEstimatePrice() {
		return parEstimatePrice;
	}

	public void setParEstimatePrice(BigDecimal parEstimatePrice) {
		this.parEstimatePrice = parEstimatePrice;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Short getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Short payStatus) {
		this.payStatus = payStatus;
	}


	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getTaskRemark() {
		return taskRemark;
	}

	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
	
}