package net.okdi.apiV4.entity;

import java.util.Date;
import java.util.List;

public class Card {

	private Long cardId;//帖子id
	private Long createorMemberId;//创建人memberId
	private String content;//内容
	private String type;//1单独的文字+单独图+单独小视频    2  文字+图片组合   3文字+小视频组合；
	private Integer readCount;//阅读数
	private List<String> imageUrl;//图片url
	private Date createTime;
	private String status;
	private String videoUrl;//视频url
	private Integer upCount;//点赞数
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public List<String> getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Integer getUpCount() {
		return upCount;
	}
	public void setUpCount(Integer upCount) {
		this.upCount = upCount;
	}
	public Long getCreateorMemberId() {
		return createorMemberId;
	}
	public void setCreateorMemberId(Long createorMemberId) {
		this.createorMemberId = createorMemberId;
	} 
	
	
}
