package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
@Document(collection="parTaskProcess")
public class ParTaskProcess {
    private Long uid;

    private Long taskId;

    private Long fromCompId;

    private Long fromMemberId;

    private Long toCompId;

    private Long toMemberId;
    @DateTimeFormat
    private Date createTime;

    private Byte taskStatus;

    private Byte taskTransmitCause;

    private Long operatorId;

    private Long operatorCompId;

    private String taskProcessDesc;
    
    private String operatorDesc;

    

    public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
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

	public String getOperatorDesc() {
		return operatorDesc;
	}

	public void setOperatorDesc(String operatorDesc) {
		this.operatorDesc = operatorDesc;
	}
}