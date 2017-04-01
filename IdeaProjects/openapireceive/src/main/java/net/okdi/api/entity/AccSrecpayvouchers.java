package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccSrecpayvouchers {
    private Long id;

    private Short paymentWay;

    private Short voucherFlag;

    private Long objectId;

    private Long objectCasId;

    private Long objectCompId;

    private Short objectCompType;

    private Integer billQuantity;

    private Long recePeopleId;

    private String recePeopleName;

    private Long compId;

    private Long createUserId;

    private Date createTime;

    private BigDecimal totalCodAmount;

    private BigDecimal totalFreight;

    private BigDecimal totalAmount;

    private BigDecimal actualAmount;

    private BigDecimal balance;

    private Short voucherStatus;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Short paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Short getVoucherFlag() {
        return voucherFlag;
    }

    public void setVoucherFlag(Short voucherFlag) {
        this.voucherFlag = voucherFlag;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getObjectCasId() {
        return objectCasId;
    }

    public void setObjectCasId(Long objectCasId) {
        this.objectCasId = objectCasId;
    }

    public Long getObjectCompId() {
        return objectCompId;
    }

    public void setObjectCompId(Long objectCompId) {
        this.objectCompId = objectCompId;
    }

    public Short getObjectCompType() {
        return objectCompType;
    }

    public void setObjectCompType(Short objectCompType) {
        this.objectCompType = objectCompType;
    }

    public Integer getBillQuantity() {
        return billQuantity;
    }

    public void setBillQuantity(Integer billQuantity) {
        this.billQuantity = billQuantity;
    }

    public Long getRecePeopleId() {
        return recePeopleId;
    }

    public void setRecePeopleId(Long recePeopleId) {
        this.recePeopleId = recePeopleId;
    }

    public String getRecePeopleName() {
        return recePeopleName;
    }

    public void setRecePeopleName(String recePeopleName) {
        this.recePeopleName = recePeopleName == null ? null : recePeopleName.trim();
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
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

    public BigDecimal getTotalCodAmount() {
        return totalCodAmount;
    }

    public void setTotalCodAmount(BigDecimal totalCodAmount) {
        this.totalCodAmount = totalCodAmount;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Short getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(Short voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}