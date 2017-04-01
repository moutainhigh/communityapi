package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * <p>Title:排行榜按周统计取派件数 </p>
 * <p>Description: </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-7-1
 */
@Document(collection="parWeekCount")
public class ParWeekCount {

	private Long memberId;//快递员Id
	
	private Long provinceId;//快递员所属省份Id(默认查询省内排行用)
	
	private String strWeek;//周 --格式:2016-01 (-后面的值取这一天是这一年的第几周)
	
	private Date createTime;//创建时间
	
	private Date modifyTime;//修改时间
	
	private Integer takeCount;//取件总数
	
	private Integer sendCount;//派件总数

	public String getStrWeek() {
		return strWeek;
	}

	public void setStrWeek(String strWeek) {
		this.strWeek = strWeek;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
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
