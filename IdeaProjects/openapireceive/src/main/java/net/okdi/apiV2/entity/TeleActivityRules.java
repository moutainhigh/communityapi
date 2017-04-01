package net.okdi.apiV2.entity;

import org.springframework.data.mongodb.core.mapping.Document;

//不分地区收费
@Document(collection = "teleActivityRules")
public class TeleActivityRules {

	//活动规则
	private Long actId;//活动编号
	
	private String rewardName;//奖励名称
	
	private Short rewardNode;//奖励节点 1:每天, 2:每周, 3:每月, 4:一次性奖励
	 
	private Double rewardPrice;//奖励价格
	
	private Integer rewardNum;//奖励数量
	
	private String accountWay;//到帐方式 1:时间, 2:登录

	private String accountTime;//到帐时间
	
	
	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Short getRewardNode() {
		return rewardNode;
	}

	public void setRewardNode(Short rewardNode) {
		this.rewardNode = rewardNode;
	}

	public Double getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(Double rewardPrice) {
		this.rewardPrice = rewardPrice;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getAccountWay() {
		return accountWay;
	}

	public void setAccountWay(String accountWay) {
		this.accountWay = accountWay;
	}

}
