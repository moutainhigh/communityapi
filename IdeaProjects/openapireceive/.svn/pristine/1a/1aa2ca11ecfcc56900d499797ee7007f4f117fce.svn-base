package net.okdi.apiV4.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="accSrecpayvouchers")
public class AccSrecpayvouchers {
	
    private Long uid;

    private Short paymentWay;          //付款方式 1:银收;2:现收;3:银付;4:现付',                          
                                                                                         
    private Short voucherFlag;         //凭证标识 0：收款凭证;1：付款凭证',                                
                                                                                           
    private Long objectId;             //付款人/客户ID',                                          
                                                                                          
    private Long objectCasId;          //付款人注册ID',                                           
                                                                                           
    private Long objectCompId;         //付款公司ID',                                            
                                                                                          
    private Short objectCompType;      //'对方公司类型 1000-网络;1002-发货商;2000-站点派送员',               
                                                                                           
    private Integer billQuantity;      //'对账单数量',                                            
                                                                                          
    private Long recePeopleId;         //'收款人ID',                                            
                                                                                        
    private String recePeopleName;     //'收款人姓名',                                            
                                                                                           
    private Long compId;               //'收款公司ID',                                           
                                                                                          
    private Long createUserId;         //'创建人ID',                                            
                                                                                         
    private Date createTime;           //'创建时间',                                             
                                                                                           
    private BigDecimal totalCodAmount; //合计代收货款金额',                                         
                                                                                         
    private BigDecimal totalFreight;   //'合计运费',                                             
                                                                                         
    private BigDecimal totalAmount;    //'合计金额',                                             
                                                                                          
    private BigDecimal actualAmount;   //'实收实付金额',                                           
                                                                                          
    private BigDecimal balance;        //'余额',                                               
                                                                                          
    private Short voucherStatus;       //'单证状态 0：未支付;1：部分完成;2：支付完成',                         
                                                                                          
    private String remark;             //'备注',                                               
  
    
    
    public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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