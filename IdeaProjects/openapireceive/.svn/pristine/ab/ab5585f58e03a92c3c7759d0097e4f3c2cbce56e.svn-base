
package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="parLogisticTrace")
public class ParLogisticTrace implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long casMemberId;
	private Long netId;//网路ID
	private String expWaybillNum;//快递单号
	private Long parId;//包裹的id
	private String traceStatus;//0未签收   1签收
	private String traceDetail;//物流明细信息
	@DateTimeFormat
	private Date modifyTime;//最后查询时间
	private String sendNoticeFlag;//0不推送  1推送
	private String clientTraceStatu;//和签收状态一样
	private String clientNetNum;//物流名称   申通
	
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCasMemberId() {
		return casMemberId;
	}
	public void setCasMemberId(Long casMemberId) {
		this.casMemberId = casMemberId;
	}
	public Long getNetId() {
		return netId;
	}
	public void setNetId(Long netId) {
		this.netId = netId;
	}
	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
	}
	public Long getParId() {
		return parId;
	}
	public void setParId(Long parId) {
		this.parId = parId;
	}
	public String getTraceStatus() {
		return traceStatus;
	}
	public void setTraceStatus(String traceStatus) {
		this.traceStatus = traceStatus;
	}
	public String getTraceDetail() {
		return traceDetail;
	}
	public void setTraceDetail(String traceDetail) {
		this.traceDetail = traceDetail;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getSendNoticeFlag() {
		return sendNoticeFlag;
	}
	public void setSendNoticeFlag(String sendNoticeFlag) {
		this.sendNoticeFlag = sendNoticeFlag;
	}

	public String getClientTraceStatu() {
		return clientTraceStatu;
	}
	public void setClientTraceStatu(String clientTraceStatu) {
		this.clientTraceStatu = clientTraceStatu;
	}
	public String getClientNetNum() {
		return clientNetNum;
	}
	public void setClientNetNum(String clientNetNum) {
		this.clientNetNum = clientNetNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
