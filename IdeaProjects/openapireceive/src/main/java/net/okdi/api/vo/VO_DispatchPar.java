package net.okdi.api.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VO_DispatchPar {
	private Long id;
	private String expWaybillNum; //包裹运单号		par_parcelinfo
	private String actualSendMember;//实际派件人ID	par_parcelinfo
	private String memberName;//派件员姓名				member_info
	private BigDecimal freight; //应收运费			par_parcelinfo
	private BigDecimal codAmount; //代收货款金额	par_parcelinfo
	//private String signMember; //签收人			par_parcelinfo
	
	private String addresseeName; //签收人			par_parceladdress
	private String addresseeMobile;//收件人电话    	par_parceladdress
	private String addresseeAddress;//收件地址		par_parceladdress
	
	private Short parcelStatus;//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'	par_parcelinfo
	
	private Long sendTaskId;//派件任务id			par_parcelinfo
	
	private Short tackingStatus;//0:在途,未签收 1:已签收',
	
	private String errorMessage; //错误原因，与包裹挂钩
	
	private Date createTime;//包裹录入时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
	}

	public String getActualSendMember() {
		return actualSendMember;
	}

	public void setActualSendMember(String actualSendMember) {
		this.actualSendMember = actualSendMember;
	}


	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getAddresseeMobile() {
		return addresseeMobile;
	}
	public void setAddresseeMobile(String addresseeMobile) {
		this.addresseeMobile = addresseeMobile;
	}
	public String getAddresseeAddress() {
		return addresseeAddress;
	}

	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress;
	}

	public Short getParcelStatus() {
		return parcelStatus;
	}

	public void setParcelStatus(Short parcelStatus) {
		this.parcelStatus = parcelStatus;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Long getSendTaskId() {
		return sendTaskId;
	}
	public void setSendTaskId(Long sendTaskId) {
		this.sendTaskId = sendTaskId;
	}
	public Short getTackingStatus() {
		return tackingStatus;
	}
	public void setTackingStatus(Short tackingStatus) {
		this.tackingStatus = tackingStatus;
	}
	public String getAddresseeName() {
		return addresseeName;
	}
	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
