package net.okdi.api.entity;

import java.util.Date;

public class NoticeCenter {
    private Long id;

    private Long compId;

    private Long createCompId;

    private Long compAuditLogId;

    private Long memberAuditLogId;

    private Short menuType;

    private String noticeTitle;

    private String noticeContent;

    private Short isRead;

    private Short noticeType;

    private Date createTime;

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

    public Long getCreateCompId() {
        return createCompId;
    }

    public void setCreateCompId(Long createCompId) {
        this.createCompId = createCompId;
    }

    public Long getCompAuditLogId() {
        return compAuditLogId;
    }

    public void setCompAuditLogId(Long compAuditLogId) {
        this.compAuditLogId = compAuditLogId;
    }

    public Long getMemberAuditLogId() {
        return memberAuditLogId;
    }

    public void setMemberAuditLogId(Long memberAuditLogId) {
        this.memberAuditLogId = memberAuditLogId;
    }

    public Short getMenuType() {
        return menuType;
    }

    public void setMenuType(Short menuType) {
        this.menuType = menuType;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public Short getIsRead() {
        return isRead;
    }

    public void setIsRead(Short isRead) {
        this.isRead = isRead;
    }

    public Short getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Short noticeType) {
        this.noticeType = noticeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}