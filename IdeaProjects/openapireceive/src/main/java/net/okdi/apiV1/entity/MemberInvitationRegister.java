package net.okdi.apiV1.entity;

import java.util.Date;

public class MemberInvitationRegister {
    private Long id;

    private Long fromMemberId;

    private String fromMemberPhone;

    private Short fromMemberRoleId;

    private String toMemberPhone;

    private Short toMemberRoleId;

    private Long toMemberNetId;

    private Date createTime;

    private Date modityTime;
    
    private Short isRegistered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public String getFromMemberPhone() {
        return fromMemberPhone;
    }

    public void setFromMemberPhone(String fromMemberPhone) {
        this.fromMemberPhone = fromMemberPhone == null ? null : fromMemberPhone.trim();
    }

    public Short getFromMemberRoleId() {
        return fromMemberRoleId;
    }

    public void setFromMemberRoleId(Short fromMemberRoleId) {
        this.fromMemberRoleId = fromMemberRoleId;
    }

    public String getToMemberPhone() {
        return toMemberPhone;
    }

    public void setToMemberPhone(String toMemberPhone) {
        this.toMemberPhone = toMemberPhone == null ? null : toMemberPhone.trim();
    }

    public Short getToMemberRoleId() {
        return toMemberRoleId;
    }

    public void setToMemberRoleId(Short toMemberRoleId) {
        this.toMemberRoleId = toMemberRoleId;
    }

    public Long getToMemberNetId() {
        return toMemberNetId;
    }

    public void setToMemberNetId(Long toMemberNetId) {
        this.toMemberNetId = toMemberNetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModityTime() {
        return modityTime;
    }

    public void setModityTime(Date modityTime) {
        this.modityTime = modityTime;
    }

	public Short getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(Short isRegistered) {
		this.isRegistered = isRegistered;
	}
    
    
}