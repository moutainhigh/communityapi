package net.okdi.apiV1.entity;

import java.io.Serializable;
import java.util.Date;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pushMessageInfo")
public class PushMessageInfo implements Serializable {

	
	private long Id;  //主键id
	
	private String title; //标题
	
	private String content; //发送内容
	
	private Short pushWay;//发送方式
	
	private Short extraParam;//触发页面
	
	private Date sendTime; //发送时间
	
	private Date  createTime; //记录创建时间
	
	private Date  modifyTime;//记录修改时间
	
	private long sendNum;//发送数量
	
	private long reciveNum;//接收数量
	
	private long openNum;//打开数量
	
	private String platform;//平台
	
	private Short useType;//对象类型
	
	


	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Short getUseType() {
		return useType;
	}

	public void setUseType(Short useType) {
		this.useType = useType;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getSendNum() {
		return sendNum;
	}

	public void setSendNum(long sendNum) {
		this.sendNum = sendNum;
	}

	public long getReciveNum() {
		return reciveNum;
	}

	public void setReciveNum(long reciveNum) {
		this.reciveNum = reciveNum;
	}

	public long getOpenNum() {
		return openNum;
	}

	public void setOpenNum(long openNum) {
		this.openNum = openNum;
	}

	public void setOpenNum(Integer openNum) {
		this.openNum = openNum;
	}
	
}
