package net.okdi.api.entity;

import java.util.Date;

public class MemberCollectExpMember {
    private Long id;

    private Long createUserId;

    private String expMemberName;

    private String expMemberPhone;

    private Long expMemberAddressId;

    private String expMemberDetaileDisplay;

    private String expMemberDetailedAddress;

    private String netId;

    private Date createTime;

    private Long compId;

    private String remark;

    private Long casMemberId;

    private String netName;
    
    private String imgUrl;
    
    private String compName;
    
    private Double distance;
    
    
    public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getExpMemberName() {
        return expMemberName;
    }

    public void setExpMemberName(String expMemberName) {
        this.expMemberName = expMemberName == null ? null : expMemberName.trim();
    }

    public String getExpMemberPhone() {
        return expMemberPhone;
    }

    public void setExpMemberPhone(String expMemberPhone) {
        this.expMemberPhone = expMemberPhone == null ? null : expMemberPhone.trim();
    }

    public Long getExpMemberAddressId() {
        return expMemberAddressId;
    }

    public void setExpMemberAddressId(Long expMemberAddressId) {
        this.expMemberAddressId = expMemberAddressId;
    }

    public String getExpMemberDetaileDisplay() {
        return expMemberDetaileDisplay;
    }

    public void setExpMemberDetaileDisplay(String expMemberDetaileDisplay) {
        this.expMemberDetaileDisplay = expMemberDetaileDisplay == null ? null : expMemberDetaileDisplay.trim();
    }

    public String getExpMemberDetailedAddress() {
        return expMemberDetailedAddress;
    }

    public void setExpMemberDetailedAddress(String expMemberDetailedAddress) {
        this.expMemberDetailedAddress = expMemberDetailedAddress == null ? null : expMemberDetailedAddress.trim();
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId == null ? null : netId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCasMemberId() {
        return casMemberId;
    }

    public void setCasMemberId(Long casMemberId) {
        this.casMemberId = casMemberId;
    }

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
    
}