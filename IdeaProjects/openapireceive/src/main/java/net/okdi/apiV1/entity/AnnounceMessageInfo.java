package net.okdi.apiV1.entity;

import java.io.Serializable;
import java.util.Date;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AnnounceMessageInfo")
public class AnnounceMessageInfo implements Serializable {

	
	private long id;  //主键id
	
	private Short announceType;//公告类型
	
	private String title; //标题
	
	private String content; //发送内容
	
	private Short pushWay;//发送方式
	
	private String creator;//创建人

	private Short status;//状态
	
	private Date  createTime; //记录创建时间
	
	private Date modifiedTime;//修改时间
	

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Short getAnnounceType() {
		return announceType;
	}

	public void setAnnounceType(Short announceType) {
		this.announceType = announceType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
