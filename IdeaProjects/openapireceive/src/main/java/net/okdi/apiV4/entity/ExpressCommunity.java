package net.okdi.apiV4.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.okdi.apiV4.vo.CommunityStatusCode.CommunityIden;
import net.okdi.apiV4.vo.CommunityStatusCode.CommunityStat;

/**
 * 快递圈子
 * @ClassName: ExpressCommunity
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月15日 上午11:16:51
 * @version V1.0
 */
public class ExpressCommunity implements Serializable{

	private static final long serialVersionUID = 86123975709004107L;

	private Long id;
	
	private String communityName;
	
	/**
	 * 圈子介绍
	 */
	private String communityIntroduce;
	
	/**
	 * 圈子所在省份
	 */
	private String communityProvince;
	
	/**
	 * 圈子区/镇
	 */
	private String communityTown;

	/**
	 * 圈子地址
	 */
	private String communityAddress;
	
	/**
	 * 经纬度数组
	 */
	private Double[] loc;

	/**
	 * 圈子公告
	 */
	private String communityNotice;
	
	/**
	 * 创建人id
	 */
	private Long creator;
	
	/**
	 * 管理员
	 */
	
	private CommunityMaster master;
	
	/**
	 * 圈子成员(在单独的集合中, 不以内嵌形式)
	 */
	private List<CommunityMember> members;
	
	/**
	 * 圈子成员数量
	 */
	private Integer memberNum;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态: 0:默认  1:已解散	逻辑删除  2:禁用  
	 */
	private Integer stat;
	
	/**
	 * 圈子标识  0: 系统默认创建(如全国34个省市的圈子) 1:用户创建的圈子
	 */
	private Integer iden;
	
	/**
	 * 管理员
	 * @ClassName: CommunityMaster
	 * @Description: TODO
	 * @author hang.yu
	 * @date 2016年4月18日 上午10:20:02
	 * @version V1.0
	 */
	public class CommunityMaster {
		
		private Long mid;
		
		private String mname;
		
		private String latestContent;
		
		private Integer cardType;
		
		private Date updateTime;

		public String getMname() {
			return mname;
		}

		public void setMname(String mname) {
			this.mname = mname;
		}

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

		public Long getMid() {
			return mid;
		}

		public void setMid(Long mid) {
			this.mid = mid;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCommunityIntroduce() {
		return communityIntroduce;
	}

	public void setCommunityIntroduce(String communityIntroduce) {
		this.communityIntroduce = communityIntroduce;
	}

	public String getCommunityProvince() {
		return communityProvince;
	}

	public void setCommunityProvince(String communityProvince) {
		this.communityProvince = communityProvince;
	}

	public String getCommunityTown() {
		return communityTown;
	}

	public void setCommunityTown(String communityTown) {
		this.communityTown = communityTown;
	}

	public String getCommunityAddress() {
		return communityAddress;
	}

	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	public String getCommunityNotice() {
		return communityNotice;
	}

	public void setCommunityNotice(String communityNotice) {
		this.communityNotice = communityNotice;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public List<CommunityMember> getMembers() {
		return members;
	}

	public void setCommunityMembers(List<CommunityMember> members) {
		this.members = members;
	}

	public CommunityMaster getMaster() {
		return master;
	}

	public void setMaster(CommunityMaster master) {
		this.master = master;
	}

	public void setMembers(List<CommunityMember> members) {
		this.members = members;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStat() {
		return stat;
	}

	public void setStat(Integer stat) {
		this.stat = stat;
	}
	
	public void setStat(CommunityStat stat) {
		this.stat = stat.toStat();
	}

	public Integer getIden() {
		return iden;
	}

	public void setIden(Integer iden) {
		this.iden = iden;
	}
	
	public void setIden(CommunityIden iden) {
		this.iden = iden.toIden();
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}
	
	
}
















