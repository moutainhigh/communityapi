package net.okdi.apiV4.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Vo_ParTaskInfo {
    private Long taskId;
    
    private BigDecimal parEstimateWeight; //包裹预估重量

    private String appointDesc;//预约描述

    private Byte taskSource;//任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王,6:微信端

    private Byte taskStatus;//任务状态 0待处理，1已分配，2已完成，3已取消，10已删除

    private String taskEndTime;//任务结束时间

    private String actorPhone;//执行人电话

    private String contactName;//联系人姓名
 
    private String contactMobile;//联系人手机

    private String contactAddress;//详细地址
    
    private String createTime;	//创建时间

    private String modifyTime; //最后修改时间
    
    private Byte parEstimateCount; //包裹预估数
    
    private String taskRemark;//同意或拒绝的原因描述'
    
    private Date appointTime;//预约取件时间
    
    private String memberName;
    
    private String netName;
    
    private String compName;
    

    public Byte getParEstimateCount() {
		return parEstimateCount;
	}

	public void setParEstimateCount(Byte parEstimateCount) {
		this.parEstimateCount = parEstimateCount;
	}

	public String getTaskRemark() {
		return taskRemark;
	}

	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	public Date getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}


    public BigDecimal getParEstimateWeight() {
        return parEstimateWeight;
    }

    public void setParEstimateWeight(BigDecimal parEstimateWeight) {
        this.parEstimateWeight = parEstimateWeight;
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

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

	public String getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	
}