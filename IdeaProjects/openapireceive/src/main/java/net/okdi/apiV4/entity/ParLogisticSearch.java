package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="parLogisticSearch")
public class ParLogisticSearch implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private String channelNo;//渠道编号 01 好递个人端   02 好递接单王
	private Long channelId;//渠道ID  也就是用户ID
	private Long netId;//快递公司ID
	private String netName;/**额外增加网络名称**/
	private String netCode;//快递100快递公司代码
	private String expWaybillNum;//运单号
	private String expType;//快递类型 0:发快递  1:收快递
	private String traceStatus;//签收类型  0未签收  1已签收
	@DateTimeFormat
	private Date createdTime;
	@DateTimeFormat
	private Date modifiedTime;
	private String traceDetail;//物流明细信息
	private Long appointId;//包裹id
	private String recMobile;//收件人电话
	private String systemMark;//系统标识 0个人端  1 电商管家
	private Short pushMark;//是否需要推送的标识
	
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public Long getNetId() {
		return netId;
	}
	public void setNetId(Long netId) {
		this.netId = netId;
	}
	public String getNetCode() {
		return netCode;
	}
	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}
	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
	}
	public String getExpType() {
		return expType;
	}
	public void setExpType(String expType) {
		this.expType = expType;
	}
	public String getTraceStatus() {
		return traceStatus;
	}
	public void setTraceStatus(String traceStatus) {
		this.traceStatus = traceStatus;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getTraceDetail() {
		return traceDetail;
	}
	public void setTraceDetail(String traceDetail) {
		this.traceDetail = traceDetail;
	}
	public Long getAppointId() {
		return appointId;
	}
	public void setAppointId(Long appointId) {
		this.appointId = appointId;
	}
	public String getRecMobile() {
		return recMobile;
	}
	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}
	public String getSystemMark() {
		return systemMark;
	}
	public void setSystemMark(String systemMark) {
		this.systemMark = systemMark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public Short getPushMark() {
		return pushMark;
	}
	public void setPushMark(Short pushMark) {
		this.pushMark = pushMark;
	}
	
}
