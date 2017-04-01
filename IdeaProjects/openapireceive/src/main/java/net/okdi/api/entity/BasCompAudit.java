package net.okdi.api.entity;

import java.util.Date;

public class BasCompAudit {
    private Long id;

    private Long compId;

    private Short auditOpinion;

    private String auditDesc;

    private Long auditUser;

    private Date auditTime;

    private Long auditComp;

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

    public Long getAuditComp() {
        return auditComp;
    }

    public void setAuditComp(Long auditComp) {
        this.auditComp = auditComp;
    }
}