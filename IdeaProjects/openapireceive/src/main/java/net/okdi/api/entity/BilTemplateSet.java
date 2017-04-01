package net.okdi.api.entity;

public class BilTemplateSet {
	private Long id;
	private	String templateType;//'模板类型 00:短信平台短信模板,01:收派员系统的短信模板'
	private	Long compId;//'公司ID'
	private	Long memberId;//'收派员ID'
	private	Long accountId;//短信账户ID
	private	String templateContent;//短信内容
	private	String templateSign;//短信签名
	private	Long createUser;//创建人
	private	String createTime;//创建时间
	private	Short deleteMark;//删除标识 0:删除,1:正常'
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
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
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getTemplateSign() {
		return templateSign;
	}
	public void setTemplateSign(String templateSign) {
		this.templateSign = templateSign;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Short getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Short deleteMark) {
		this.deleteMark = deleteMark;
	}
}
