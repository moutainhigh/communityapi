package net.okdi.apiV2.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//不分地区收费
@Document(collection = "newUserRegisterActivity")
public class NewUserRegisterActivity {

	@Id
	private Long id; //活动编号
	
	private Short activityType;//活动类型
	
	private String activityName;//活动名称
	
	private Short activityStatus;//活动状态
	
	
	private Date activityStartTime;//活动开始时间
	
	
	private Date activityEndTime;//活动结束时间
	
	
	private String activityIntroduction;//活动简介

	
	private Double rewardPrice;//奖励金额
	
	

	public Double getRewardPrice() {
		return rewardPrice;
	}


	public void setRewardPrice(Double rewardPrice) {
		this.rewardPrice = rewardPrice;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Short getActivityType() {
		return activityType;
	}


	public void setActivityType(Short activityType) {
		this.activityType = activityType;
	}


	public String getActivityName() {
		return activityName;
	}


	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public Short getActivityStatus() {
		return activityStatus;
	}


	public void setActivityStatus(Short activityStatus) {
		this.activityStatus = activityStatus;
	}


	public Date getActivityStartTime() {
		return activityStartTime;
	}


	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}


	public Date getActivityEndTime() {
		return activityEndTime;
	}


	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}


	public String getActivityIntroduction() {
		return activityIntroduction;
	}


	public void setActivityIntroduction(String activityIntroduction) {
		this.activityIntroduction = activityIntroduction;
	}
	
}
