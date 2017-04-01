package net.okdi.api.entity;

public class ExpCustomerMemberRelation {
	private Long compId;
	private Long customerId;
	private Long expMemberId;
	private Short sort;
    private String expMemberName;
	
	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}
	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getExpMemberId() {
		return expMemberId;
	}

	public void setExpMemberId(Long expMemberId) {
		this.expMemberId = expMemberId;
	}

	public String getExpMemberName() {
		return expMemberName;
	}

	public void setExpMemberName(String expMemberName) {
		this.expMemberName = expMemberName;
	}
	
}