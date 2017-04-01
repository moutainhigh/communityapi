package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RobQuotationInfo {
    private Long id;

    private Long robId;

    private Long compId;

    private Long memberId;

    private Short robStatus;

    private Short successFlag;

    private BigDecimal quotationAmt;

    private Short quotationType;

    private Short isNew;

    private Date createTime;
    
    private Long distance;
    
    

    public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRobId() {
        return robId;
    }

    public void setRobId(Long robId) {
        this.robId = robId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Short getRobStatus() {
        return robStatus;
    }

    public void setRobStatus(Short robStatus) {
        this.robStatus = robStatus;
    }

    public Short getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Short successFlag) {
        this.successFlag = successFlag;
    }

    public BigDecimal getQuotationAmt() {
        return quotationAmt;
    }

    public void setQuotationAmt(BigDecimal quotationAmt) {
        this.quotationAmt = quotationAmt;
    }

    public Short getQuotationType() {
        return quotationType;
    }

    public void setQuotationType(Short quotationType) {
        this.quotationType = quotationType;
    }

    public Short getIsNew() {
        return isNew;
    }

    public void setIsNew(Short isNew) {
        this.isNew = isNew;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}