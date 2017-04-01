package net.okdi.apiV4.entity;

/**
 * 快递员基本信息实体类VO
 * @Project Name:springmvc 
 * @Package net.okdi.apiV4.entity  
 * @Title: MemberInfoVO.java 
 * @ClassName: MemberInfoVO <br/> 
 * @date: 2016-5-30 下午7:54:15 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
public class MemberInfoVO {

	private String memberId;
	private String memberName;
	private String memberPhone;
	private String roleId;
	private String netId;
	private String netName;
	private String compId;
	private String compName;
	private String compAddress;
	
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompAddress() {
		return compAddress;
	}
	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}
	
	
	
	
}
