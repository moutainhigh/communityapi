package net.okdi.apiV4.entity;

import java.util.Date;

/**
 * 圈子成员
 * @ClassName: CommunityMember
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月15日 下午2:12:13
 * @version V1.0
 */
public class CommunityMember {
	
	/**
	 * 圈子id
	 */
	private Long communityId;

	/**
	 * 成员id
	 */
	private Long mid;
	
	/**
	 * 成员名字
	 */
	private String mname;
	
	/**
	 * 加入时间
	 */
	private Date joinTime;
	
	/**
	 * 最新发布的纯文本帖子内容
	 */
	private String latestContent;
	
	/**
	 * 1: 文字/图片/小视频/2: 文字+图片/ 3:文字+小视频
	 */
	private Integer cardType;
	
	/**
	 * 最新动态的发布时间
	 */
	private Date pubTime;

	public String getLatestContent() {
		return latestContent;
	}

	public void setLatestContent(String latestContent) {
		this.latestContent = latestContent;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}
	
	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Override
	public String toString() {
		return "CommunityMember [communityId=" + communityId + ", mid=" + mid + ", mname=" + mname + ", joinTime="
				+ joinTime + ", latestContent=" + latestContent + ", cardType=" + cardType + ", pubTime=" + pubTime
				+ "]";
	}
}
