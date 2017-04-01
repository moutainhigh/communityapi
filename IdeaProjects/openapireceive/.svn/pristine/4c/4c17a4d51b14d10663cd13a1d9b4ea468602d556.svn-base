package net.okdi.apiV4.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * <p>Title: 微信绑定手机号</p>
 * <p>Description: </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-7-15
 */
@Document(collection = "wechatBound")
public class WechatBound {

	private Long memberId;
	private String mobile;//绑定手机号
	private String openId;//公众号openId
	private Date createTime;//绑定时间
	private String inviterPhone;//邀请人手机号
	private String addressName;//地址信息
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getInviterPhone() {
		return inviterPhone;
	}
	public void setInviterPhone(String inviterPhone) {
		this.inviterPhone = inviterPhone;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
}
