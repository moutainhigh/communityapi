package net.okdi.api.entity;

import java.util.Date;

public class BasEmployeeAudit {
    private Long id;

    private Long memberId;

    private Long auditComp;

    private Short applicationRoleType;

    private Short auditOpinion;

    private String auditDesc;

    private Long auditUser;

    private Date auditTime;

    private Short auditItem;

    private Short auditRejectReason;
    
    private String applicationDesc;
    
    private Date applicationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAuditComp() {
        return auditComp;
    }

    public void setAuditComp(Long auditComp) {
        this.auditComp = auditComp;
    }

    public Short getApplicationRoleType() {
        return applicationRoleType;
    }

    public void setApplicationRoleType(Short applicationRoleType) {
        this.applicationRoleType = applicationRoleType;
    }

    public Short getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(Short auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc == null ? null : auditDesc.trim();
    }

    public Long getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Short getAuditItem() {
        return auditItem;
    }

    public void setAuditItem(Short auditItem) {
        this.auditItem = auditItem;
    }

    public Short getAuditRejectReason() {
        return auditRejectReason;
    }

    public void setAuditRejectReason(Short auditRejectReason) {
        this.auditRejectReason = auditRejectReason;
    }

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
    
}