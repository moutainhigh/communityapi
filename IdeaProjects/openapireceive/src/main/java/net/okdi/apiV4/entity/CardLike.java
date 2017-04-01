package net.okdi.apiV4.entity;

import java.util.Date;
/**
 * 帖子点赞表
 * @author amssy
 *
 */
public class CardLike {

	private Long cardLikeId;//帖子点赞id
	private Long cardId;//帖子id
	private Date createTime;//创建时间
	private Long memberId;//点赞人id
	public Long getCardLikeId() {
		return cardLikeId;
	}
	public void setCardLikeId(Long cardLikeId) {
		this.cardLikeId = cardLikeId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
}
