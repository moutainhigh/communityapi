package net.okdi.apiV4.entity;

import java.util.Date;
import java.util.List;

public class Vo_Card {
	private Long cardId;
	private Long createorMemberId;
	private String content;
	private String type;//1.文字，2图片 3视频
	private Integer readCount;
	private List<String> imageUrl;
	private Date createTime;
	private String status;
	private String videoUrl;
	private Integer upCount;
	private String baseUrl;
	
	private List<CardComment> comments;
	
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
	public List<CardComment> getComments() {
		return comments;
	}
	public void setComments(List<CardComment> comments) {
		this.comments = comments;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
