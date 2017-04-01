package net.okdi.apiV2.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//不分地区收费
@Document(collection = "regGetRewardRelation")
public class RegGetRewardRelation {

	@Id
	private Long id;
	
	private String memberId;//人员的memberId
	
	private Double rewardPrice;//奖励金额

	private Date createTime;//创建时间O
	
	private Short activityType; //活动状态
	
	private String dateTime;//用于查询yyyyMMdd
	
	private Short status;//标志是否已经领用;//0 未领用, 1:已领用
	
	

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Short getActivityType() {
		return activityType;
	}

	public void setActivityType(Short activityType) {
		this.activityType = activityType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Double getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(Double rewardPrice) {
		this.rewardPrice = rewardPrice;
	}
	
}
