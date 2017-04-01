package net.okdi.apiV4.entity;

import java.util.Date;

public class PromoRecord {
    private Long id;

    private Long memberId;

    private Short memberSrc;

    private Short promoType;

    private Short productType;

    private Short promoOper;

    private Long promoRegMemberId;

    private String userAgent;

    private Date createTime;

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

    public Short getMemberSrc() {
        return memberSrc;
    }

    public void setMemberSrc(Short memberSrc) {
        this.memberSrc = memberSrc;
    }

    public Short getPromoType() {
        return promoType;
    }

    public void setPromoType(Short promoType) {
        this.promoType = promoType;
    }

    public Short getProductType() {
        return productType;
    }

    public void setProductType(Short productType) {
        this.productType = productType;
    }

    public Short getPromoOper() {
        return promoOper;
    }

    public void setPromoOper(Short promoOper) {
        this.promoOper = promoOper;
    }

    public Long getPromoRegMemberId() {
        return promoRegMemberId;
    }

    public void setPromoRegMemberId(Long promoRegMemberId) {
        this.promoRegMemberId = promoRegMemberId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}