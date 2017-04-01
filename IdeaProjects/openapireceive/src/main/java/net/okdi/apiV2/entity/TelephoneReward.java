package net.okdi.apiV2.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//不分地区收费
@Document(collection = "telephoneReward")
public class TelephoneReward {

	//基本信息
	@Id
	private long id;//主键,活动编号
	
	private String actName;//活动名称
	
	private Short actType;//活动类型
	
	private Short actStatus;//活动状态 1:未开始, 2:进行中, 3:已结束
	
	private Date actStartTime;//活动开始时间
	
	private Date actEndTime;//活动结束时间
	
	private Short actScope;//活动范围 1:所有快递员, 2:导入快递员
	
	private String actIntroduction;//活动简介
	
	private String dateTime;//该时间用于查询当天活动 格式: yyyyMMdd
	
	
	
	public Short getActType() {
		return actType;
	}

	public void setActType(Short actType) {
		this.actType = actType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Short getActStatus() {
		return actStatus;
	}

	public void setActStatus(Short actStatus) {
		this.actStatus = actStatus;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public Short getActScope() {
		return actScope;
	}

	public void setActScope(Short actScope) {
		this.actScope = actScope;
	}

	public String getActIntroduction() {
		return actIntroduction;
	}

	public void setActIntroduction(String actIntroduction) {
		this.actIntroduction = actIntroduction;
	}

	
}
