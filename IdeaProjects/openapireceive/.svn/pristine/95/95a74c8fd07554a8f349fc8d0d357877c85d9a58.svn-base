package net.okdi.apiV4.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 包裹关系表--包裹流转记录信息
 * @Project Name:springmvc 
 * @Package net.okdi.apiV4.entity  
 * @Title: ParParcelRelation.java 
 * @ClassName: ParParcelRelation <br/> 
 * @date: 2016-4-21 下午4:09:36 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Document(collection="parParcelRelation")
public class ParParcelRelation {
	
	private Long uid;
	private Long oldParId;//原来的包裹id
	private Long oldMemberId;//原来的快递员id
	private Long newParId;//新生成的包裹id
	private Long newMemberId;//新接单的快递员id
	private Short operationType;//操作类型 1：转代收 2：重派 3：转单
	@DateTimeFormat
	private Date createTime;
	
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getOldParId() {
		return oldParId;
	}
	public void setOldParId(Long oldParId) {
		this.oldParId = oldParId;
	}
	public Long getOldMemberId() {
		return oldMemberId;
	}
	public void setOldMemberId(Long oldMemberId) {
		this.oldMemberId = oldMemberId;
	}
	public Long getNewParId() {
		return newParId;
	}
	public void setNewParId(Long newParId) {
		this.newParId = newParId;
	}
	public Long getNewMemberId() {
		return newMemberId;
	}
	public void setNewMemberId(Long newMemberId) {
		this.newMemberId = newMemberId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Short getOperationType() {
		return operationType;
	}
	public void setOperationType(Short operationType) {
		this.operationType = operationType;
	}
	
	
	
}
