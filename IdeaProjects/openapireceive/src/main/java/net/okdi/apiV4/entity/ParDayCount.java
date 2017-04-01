package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * <p>Title:排行榜按日统计取派件数 </p>
 * <p>Description: </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-7-1
 */
@Document(collection="parDayCount")
public class ParDayCount {

	private Long memberId;//快递员Id
	
	private Long provinceId;//快递员所属省份Id(默认查询省内排行用)
	
	private String strDay;//天-- 格式:2016-01-02
	
	private Date createTime;//创建时间
	
	private Date modifyTime;//修改时间
	
	private Integer takeCount;//取件总数
	
	private Integer sendCount;//派件总数
	
	public String getStrDay() {
		return strDay;
	}

	public void setStrDay(String strDay) {
		this.strDay = strDay;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTakeCount() {
		return takeCount;
	}

	public void setTakeCount(Integer takeCount) {
		this.takeCount = takeCount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	
}
