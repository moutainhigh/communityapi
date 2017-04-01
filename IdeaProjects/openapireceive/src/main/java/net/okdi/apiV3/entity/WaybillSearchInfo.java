package net.okdi.apiV3.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "waybillSearchInfo")
public class WaybillSearchInfo {

	@Id
	private Long id;//主键
	@DateTimeFormat
	private Date createTime;//创建时间
	@DateTimeFormat
	private Date modifyTime;//创建时间
	
	private String waybillNo;//运单号
	
	private String netId;//网络ID
	
	private String  netCode;
	
	private String netName;// 网络名称
	
	private Long memberId;//查询人ID

	private Integer month;
	
	private Integer isCurrMonth;

	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getIsCurrMonth() {
		return isCurrMonth;
	}

	public void setIsCurrMonth(Integer isCurrMonth) {
		this.isCurrMonth = isCurrMonth;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}
	


}
