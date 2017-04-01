package net.okdi.apiV4.vo;


public class VO_SiteMemberInfo {
	

	private Long memberId;//快递员id
	
	private String memberName;//快递员姓名
	
	private Short roleId;//'角色 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员',
	
	private String headImgPath;//头像地址
	
	private String memberPhone;
	
	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getHeadImgPath() {
		return headImgPath;
	}

	public void setHeadImgPath(String headImgPath) {
		this.headImgPath = headImgPath;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Short getRoleId() {
		return roleId;
	}

	public void setRoleId(Short roleId) {
		this.roleId = roleId;
	}

	
}
