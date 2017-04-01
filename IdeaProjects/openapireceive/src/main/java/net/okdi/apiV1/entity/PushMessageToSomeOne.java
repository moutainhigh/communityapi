package net.okdi.apiV1.entity;

import java.io.Serializable;
import java.util.Date;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pushMessageToSomeOne")
public class PushMessageToSomeOne implements Serializable {

	
	private Long pushId;  //推送id
	
	private String title; //标题
	
	private String content; //发送内容
	
	private Short pushWay;//发送方式
	
	private Short extraParam;//触发页面
	
	private Long createTime;//创建时间
	
	private Long toMemberId;//推送给谁
	
	private String memberPhone;//推送给哪个手机号
	
	private Short isRead;//该条消息是否已读  0未读  1已读

	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Short getPushWay() {
		return pushWay;
	}

	public void setPushWay(Short pushWay) {
		this.pushWay = pushWay;
	}

	public Short getExtraParam() {
		return extraParam;
	}

	public void setExtraParam(Short extraParam) {
		this.extraParam = extraParam;
	}



	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Short getIsRead() {
		return isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}

	
	
}
