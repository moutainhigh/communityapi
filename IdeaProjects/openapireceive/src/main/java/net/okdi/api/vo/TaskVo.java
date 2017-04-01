package net.okdi.api.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TaskVo {
	
	private Long id; //主键ID 

    private Byte taskType; //任务类型 0:取件 1:派件 2:自取件 3：销售等非快递
	
	private Long taskId; //任务ID
    
    private Long coopCompId; //任务受理方站点
    
    private Long coopNetId; //任务受理方网络
    
    private String netName; // 任务受理方网络
    
    private String netCode;
    
    private Byte parEstimateCount; //包裹预估数

    private BigDecimal parEstimateWeight;  //包裹预估重量

    private Date appointTime; //预约取件时间

    private String appointDesc; //预约描述

    private Byte taskSource; //任务来源 1：好递网 2：站点自建 3：电商管家 4：好递个人端 5：好递接单王

    private Byte taskStatus; //任务状态 0：待处理 1：已分配 2：已完成 3：已取消
    
    private Byte taskIsEnd; //任务是否结束

    private Date taskEndTime; //任务结束时间

    private Long actorMemberId; //执行人员

    private String actorPhone; 

    private String contactName; //联系人姓名

    private String contactMobile; //联系人手机

    private String contactTel; //联系人电话

    private Long contactAddressId; //联系人地址ID

    private String contactAddress; //联系人详细地址

    private Long customerId; //客户ID

    private Long contactCasUserId; //联系人CAS_ID

    private Long contactCompId; //联系人公司ID

    private Date createTime; //创建时间

    private Long createUserId; //创建人ID

    private Byte taskFlag; //任务标志 0：正常 1：抢单

    private Date modifyTime; //更新时间

    private BigDecimal contactAddrLongitude; //联系人地址的经度信息

    private BigDecimal contactAddrLatitude;  //联系人地址的纬度信息
    
    private BigDecimal parEstimatePrice; //预估报价
    

	private Long fromCompId; //起始公司ID

    private Long fromMemberId; //起始人员ID

    private Long toCompId; //到达公司ID

    private Long toMemberId; //到达人员ID
    
    private Byte logTaskStatus; //0：指派 1：取消 2：拒绝 3：完结 10：未提货 11：已提货 12：拒收 13：签收 20：结束

    private Byte taskTransmitCause; //任务转单原因  1、客户取消发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来

    private Long operatorId; //操作人ID

    private Long operatorCompId; //操作公司ID

    private String taskProcessDesc;  //任务流水描述
    
    private Date logCreateTime; //日志创建时间
    
    private String operatorDesc; //操作人姓名
    
    
    private Long memberId; //处理人

    private String courierName;
    
    private Long compId; //处理站点

    private Byte disposalObject; //处理方类型 0：派送员 1：派送站点 2：客服 3：营业分部  4：站点
    
    private Byte showFlag; //显示标识 0：不显示 1：显示

    private Byte taskErrorFlag; //异常标识 0：正常 1：异常
    
    private String disposalDesc; //处理描述
    
    private Date modifiedTime; //更新时间

    private String thirdId; //第三方Id

    private String task_remark; //同意或拒绝原因

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getTaskType() {
		return taskType;
	}

	public void setTaskType(Byte taskType) {
		this.taskType = taskType;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

	public Long getFromCompId() {
		return fromCompId;
	}

	public void setFromCompId(Long fromCompId) {
		this.fromCompId = fromCompId;
	}

	public Long getFromMemberId() {
		return fromMemberId;
	}

	public void setFromMemberId(Long fromMemberId) {
		this.fromMemberId = fromMemberId;
	}

	public Long getToCompId() {
		return toCompId;
	}

	public void setToCompId(Long toCompId) {
		this.toCompId = toCompId;
	}

	public Long getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}

	public Byte getTaskTransmitCause() {
		return taskTransmitCause;
	}

	public void setTaskTransmitCause(Byte taskTransmitCause) {
		this.taskTransmitCause = taskTransmitCause;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getOperatorCompId() {
		return operatorCompId;
	}

	public void setOperatorCompId(Long operatorCompId) {
		this.operatorCompId = operatorCompId;
	}

	public String getTaskProcessDesc() {
		return taskProcessDesc;
	}

	public void setTaskProcessDesc(String taskProcessDesc) {
		this.taskProcessDesc = taskProcessDesc;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Byte getDisposalObject() {
		return disposalObject;
	}

	public void setDisposalObject(Byte disposalObject) {
		this.disposalObject = disposalObject;
	}

	public Byte getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Byte showFlag) {
		this.showFlag = showFlag;
	}

	public Byte getTaskErrorFlag() {
		return taskErrorFlag;
	}

	public void setTaskErrorFlag(Byte taskErrorFlag) {
		this.taskErrorFlag = taskErrorFlag;
	}

	public String getDisposalDesc() {
		return disposalDesc;
	}

	public void setDisposalDesc(String disposalDesc) {
		this.disposalDesc = disposalDesc;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Byte getLogTaskStatus() {
		return logTaskStatus;
	}

	public void setLogTaskStatus(Byte logTaskStatus) {
		this.logTaskStatus = logTaskStatus;
	}

	public Date getLogCreateTime() {
		return logCreateTime;
	}

	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
	}

	public String getOperatorDesc() {
		return operatorDesc;
	}

	public void setOperatorDesc(String operatorDesc) {
		this.operatorDesc = operatorDesc;
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

	public String getTask_remark() {
		return task_remark;
	}

	public void setTask_remark(String task_remark) {
		this.task_remark = task_remark;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	
	
}
