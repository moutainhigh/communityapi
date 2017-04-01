package net.okdi.api.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="parTaskDisposalRecord")
public class ParTaskDisposalRecord {
	@Id
	private String _id;
    private Long id;

    private Long taskId;

    private Byte disposalType;

    private Long memberId;

    private Long compId;

    private Byte disposalObject;

    private Byte showFlag;

    private Byte taskErrorFlag;

    private String disposalDesc;
    @DateTimeFormat
    private Date createTime;
    @DateTimeFormat
    private Date modifiedTime;

    
    public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Byte getDisposalType() {
        return disposalType;
    }

    public void setDisposalType(Byte disposalType) {
        this.disposalType = disposalType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}