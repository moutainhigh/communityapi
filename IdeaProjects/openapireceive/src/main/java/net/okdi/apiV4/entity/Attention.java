package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * <p>Title: 关注实体</p>
 * <p>Description: 关注实体类</p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-5-26
 */
@Document(collection="attention")
public class Attention {

	private Long attentionId;//关注Id
	private Long fromMemberId;//关注者memberId
	private Long toMemberId;//被关注者memberId
	private  Short isRead;//1：未读；2：已读
	private Short isAttention;//是否关注对方(toMemberId是否关注fromMemberId) 0 否 1 是
	private Date createTime;//创建时间
	
	public Short getIsAttention() {
		return isAttention;
	}
	public void setIsAttention(Short isAttention) {
		this.isAttention = isAttention;
	}
	public Long getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
	}
	public Long getFromMemberId() {
		return fromMemberId;
	}
	public void setFromMemberId(Long fromMemberId) {
		this.fromMemberId = fromMemberId;
	}
	public Long getToMemberId() {
		return toMemberId;
	}
	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Short getIsRead() {
		return isRead;
	}
	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}
	
}
