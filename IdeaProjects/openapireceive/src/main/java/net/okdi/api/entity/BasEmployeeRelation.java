package net.okdi.api.entity;

public class BasEmployeeRelation {
    private Long id;

    private Long memberId;

    private Long compId;

    private Short roleId;

    private String areaColor;

    private Short employeeWorkStatusFlag;

    private Short reviewTaskReceivingFlag;

    private Long margin;

    private Long creditLimit;
    
    private String memberName;
    
    

    public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public String getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(String areaColor) {
        this.areaColor = areaColor == null ? null : areaColor.trim();
    }

    public Short getEmployeeWorkStatusFlag() {
        return employeeWorkStatusFlag;
    }

    public void setEmployeeWorkStatusFlag(Short employeeWorkStatusFlag) {
        this.employeeWorkStatusFlag = employeeWorkStatusFlag;
    }

    public Short getReviewTaskReceivingFlag() {
        return reviewTaskReceivingFlag;
    }

    public void setReviewTaskReceivingFlag(Short reviewTaskReceivingFlag) {
        this.reviewTaskReceivingFlag = reviewTaskReceivingFlag;
    }

    public Long getMargin() {
        return margin;
    }

    public void setMargin(Long margin) {
        this.margin = margin;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }
}