package net.okdi.apiV4.entity;

import java.io.Serializable;

/**
 * 申请加圈
 * @ClassName: JoinCommunityRequest
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月15日 下午2:58:47
 * @version V1.0
 */
public class JoinCommunityRequest implements Serializable{

	private static final long serialVersionUID = 2931519453798396949L;

	public static final transient int CHANGE_MASTER = 2;
	
	/**
	 * 圈子id
	 */
	private Long communityId;
	
	/**
	 * 圈子名字
	 */
	private String commnuity;
	
	/**
	 * 圈子管理员
	 */
	private Long commMaster;
	
	/**
	 * 申请人
	 */
	private Long proposer;
	
	/**
	 * 申请人姓名
	 */
	private String proposerName;
	
	/**
	 * 申请时间
	 */
	private Long proposeTime;
	
	/**
	 * 状态  0：默认(申请中) 1: 同意  2: 管理权转让后
	 */
	private Integer status;
	
	public JoinCommunityRequest(Long communityId, String commnuity, Long commMaster) {
		this.communityId = communityId;
		this.commnuity = commnuity;
		this.commMaster = commMaster;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public String getCommnuity() {
		return commnuity;
	}

	public void setCommnuity(String commnuity) {
		this.commnuity = commnuity;
	}

	public Long getCommMaster() {
		return commMaster;
	}

	public void setCommMaster(Long commMaster) {
		this.commMaster = commMaster;
	}

	public Long getProposer() {
		return proposer;
	}

	public void setProposer(Long proposer) {
		this.proposer = proposer;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public Long getProposeTime() {
		return proposeTime;
	}

	public void setProposeTime(Long proposeTime) {
		this.proposeTime = proposeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}













