package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MemberAddressInfo {
    private Long id;

    private Long memberId;

    private Long addressTagId;

    private Long detailedAddresssId;

    private String detailedAddresss;

    private String zipCode;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Short shippingDef;

    private Short getDef;

    private Short returnDef;

    private Short invoiceDef;

    private Date createTime;

    private Short delMark;

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

    public Long getAddressTagId() {
        return addressTagId;
    }

    public void setAddressTagId(Long addressTagId) {
        this.addressTagId = addressTagId;
    }

    public Long getDetailedAddresssId() {
        return detailedAddresssId;
    }

    public void setDetailedAddresssId(Long detailedAddresssId) {
        this.detailedAddresssId = detailedAddresssId;
    }

    public String getDetailedAddresss() {
        return detailedAddresss;
    }

    public void setDetailedAddresss(String detailedAddresss) {
        this.detailedAddresss = detailedAddresss == null ? null : detailedAddresss.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Short getShippingDef() {
        return shippingDef;
    }

    public void setShippingDef(Short shippingDef) {
        this.shippingDef = shippingDef;
    }

    public Short getGetDef() {
        return getDef;
    }

    public void setGetDef(Short getDef) {
        this.getDef = getDef;
    }

    public Short getReturnDef() {
        return returnDef;
    }

    public void setReturnDef(Short returnDef) {
        this.returnDef = returnDef;
    }

    public Short getInvoiceDef() {
        return invoiceDef;
    }

    public void setInvoiceDef(Short invoiceDef) {
        this.invoiceDef = invoiceDef;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getDelMark() {
        return delMark;
    }

    public void setDelMark(Short delMark) {
        this.delMark = delMark;
    }
}