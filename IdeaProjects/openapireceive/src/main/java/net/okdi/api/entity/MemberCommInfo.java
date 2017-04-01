package net.okdi.api.entity;

import java.util.Date;

public class MemberCommInfo {
    private Long id;

    private Long memberId;
    
    private Long commTagId;

    private Short addressType;

    private String associatedNumber;

    private Date createTime;

    private Short verifFlag;

    private Short preferredComm;

    private Short delMark;

    private Short dataType;
    
    private Long compid;
    
    

    public Long getCommTagId() {
		return commTagId;
	}

	public void setCommTagId(Long commTagId) {
		this.commTagId = commTagId;
	}

	public Long getCompid() {
		return compid;
	}

	public void setCompid(Long compid) {
		this.compid = compid;
	}

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

    public Short getAddressType() {
        return addressType;
    }

    public void setAddressType(Short addressType) {
        this.addressType = addressType;
    }

    public String getAssociatedNumber() {
        return associatedNumber;
    }

    public void setAssociatedNumber(String associatedNumber) {
        this.associatedNumber = associatedNumber == null ? null : associatedNumber.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getVerifFlag() {
        return verifFlag;
    }

    public void setVerifFlag(Short verifFlag) {
        this.verifFlag = verifFlag;
    }

    public Short getPreferredComm() {
        return preferredComm;
    }

    public void setPreferredComm(Short preferredComm) {
        this.preferredComm = preferredComm;
    }

    public Short getDelMark() {
        return delMark;
    }

    public void setDelMark(Short delMark) {
        this.delMark = delMark;
    }

    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }
}