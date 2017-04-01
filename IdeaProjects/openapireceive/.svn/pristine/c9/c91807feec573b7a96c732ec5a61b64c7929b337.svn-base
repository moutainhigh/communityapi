package net.okdi.apiV2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//不分地区收费
@Document(collection = "telephoneRelation")
public class TelephoneRelation {

	@Id
	private long id;//主键
	
	private Long tid;//关联id
	
	private String phone;//登录用户手机号
	
	private Integer rewardNum;//奖励数量
	
	private Double rewardTotalPrice;// 奖励的总金额
	
	private Double rewardPrice;//奖励的价格

	private String dateTime;//用于查询是否领用过奖励标识, 格式:yyyyMMdd
	
	private Short status;// 是否已经领用过 0:未领用, 1:已领用
	
	
	
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Double getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(Double rewardPrice) {
		this.rewardPrice = rewardPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}

	public Double getRewardTotalPrice() {
		return rewardTotalPrice;
	}

	public void setRewardTotalPrice(Double rewardTotalPrice) {
		this.rewardTotalPrice = rewardTotalPrice;
	}

	
	
	
}
