package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="packageReport")
public class PackageReport {
	
	private Long memberId;//	快递员id	
	@DateTimeFormat
	private Date createTime; //单位为天   必填项   年月日
	private Long receiveCount;//	 揽收数   同一天的累加
	private Short signType;// 签收类型   对应 signResult 签收类型   14：个人签收- 15：他人签收  3：转代收点- 16系统签收
	private Long sendCount;//	 投递数	同一天同一 签收类型
	private Long compId; //所属站点    必填项
	private Long netId; //所属网络
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Short getSignType() {
		return signType;
	}
	public void setSignType(Short signType) {
		this.signType = signType;
	}
	public Long getSendCount() {
		return sendCount;
	}
	public void setSendCount(Long sendCount) {
		this.sendCount = sendCount;
	}
	public Long getCompId() {
		return compId;
	}
	public void setCompId(Long compId) {
		this.compId = compId;
	}
	public Long getNetId() {
		return netId;
	}
	public void setNetId(Long netId) {
		this.netId = netId;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PackageReport{");
		sb.append("memberId=").append(memberId);
		sb.append(", createTime=").append(createTime);
		sb.append(", receiveCount=").append(receiveCount);
		sb.append(", signType=").append(signType);
		sb.append(", sendCount=").append(sendCount);
		sb.append(", compId=").append(compId);
		sb.append('}');
		return sb.toString();
	}
	
}
