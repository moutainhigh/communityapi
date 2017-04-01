package net.okdi.api.entity;

import java.util.Date;

public class WxShareLog {
    private Long memberId;

    private Long sharedMemberId;

    private Date createTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSharedMemberId() {
        return sharedMemberId;
    }

    public void setSharedMemberId(Long sharedMemberId) {
        this.sharedMemberId = sharedMemberId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}