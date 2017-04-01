package net.okdi.api.entity;

import java.util.Date;

public class ExpCompAuditLog {
    private Long id;

    private Long compId;

    private Long parentCompId;

    private Byte status;

    private String refuseDesc;

    private Byte compDeleteMark;

    private Byte parentCompDeleteMark;

    private Long createCompId;

    private Long createUserId;

    private Date createTime;
    
    private String compName;
    
    private String responsible;
    
    private String compMobile;
    
    private String compTypeNum;
    
    
    
    
    
    
    
    

    public String getCompTypeNum() {
		return compTypeNum;
	}

	public void setCompTypeNum(String compTypeNum) {
		this.compTypeNum = compTypeNum;
	}

	public String getCompMobile() {
		return compMobile;
	}

	public void setCompMobile(String compMobile) {
		this.compMobile = compMobile;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
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

    public Long getParentCompId() {
        return parentCompId;
    }

    public void setParentCompId(Long parentCompId) {
        this.parentCompId = parentCompId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRefuseDesc() {
        return refuseDesc;
    }

    public void setRefuseDesc(String refuseDesc) {
        this.refuseDesc = refuseDesc == null ? null : refuseDesc.trim();
    }

    public Byte getCompDeleteMark() {
        return compDeleteMark;
    }

    public void setCompDeleteMark(Byte compDeleteMark) {
        this.compDeleteMark = compDeleteMark;
    }

    public Byte getParentCompDeleteMark() {
        return parentCompDeleteMark;
    }

    public void setParentCompDeleteMark(Byte parentCompDeleteMark) {
        this.parentCompDeleteMark = parentCompDeleteMark;
    }

    public Long getCreateCompId() {
        return createCompId;
    }

    public void setCreateCompId(Long createCompId) {
        this.createCompId = createCompId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}