package net.okdi.apiV4.entity;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 注册用户  对应MySQL member_info表
 * @ClassName: ExpressMemberInfo
 * @Description: TODO
 * @author hang.yu
 * @date 2016年5月20日 下午2:46:43
 * @version V1.0
 */
public class ExpressMemberInfo {

	/**
	 * 用户id  memberId
	 */
	private Long id;
	
	/**
	 * 用户名
	 */
	private String mname;
	
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 加入的圈子
	 */
	private Set<Long> joinCommuns;
	
	/**
	 * 置顶的圈子/个人聊天
	 */
	private Map<Long, TopCommunity> topCommuns;
	
	/**
	 * 消息免打扰的群/个人聊天
	 */
	private Set<Long> interrupt;
	
	/**
	 * 清除聊天记录
	 */
	private Map<Long, TruncLog> truncLog;
	
	/**
	 * 创建时间
	 */
	private Date ts;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Map<Long, TruncLog> getTruncLog() {
		return truncLog;
	}

	public void setTruncLog(Map<Long, TruncLog> truncLog) {
		this.truncLog = truncLog;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Set<Long> getJoinCommuns() {
		return joinCommuns;
	}

	public void setJoinCommuns(Set<Long> joinCommuns) {
		this.joinCommuns = joinCommuns;
	}

	public Map<Long, TopCommunity> getTopCommuns() {
		return topCommuns;
	}

	public void setTopCommuns(Map<Long, TopCommunity> topCommuns) {
		this.topCommuns = topCommuns;
	}

	public Set<Long> getInterrupt() {
		return interrupt;
	}

	public void setInterrupt(Set<Long> interrupt) {
		this.interrupt = interrupt;
	}



	/**
	 * @Description: 置顶的圈子  置顶的个人聊天
	 * @author hang.yu
	 * @date 2016年5月21日 下午1:25:55
	 * @version V1.0
	 */
	public class TopCommunity {

		/**
		 * 圈子id 或者memberId
		 */
		private Long cid;
		
		/**
		 * 操作时间
		 */
		private Date ts;

		public Long getCid() {
			return cid;
		}

		public void setCid(Long cid) {
			this.cid = cid;
		}

		public Date getTs() {
			return ts;
		}

		public void setTs(Date ts) {
			this.ts = ts;
		}

		public TopCommunity() {
		}
		
		public TopCommunity(Long cid, Date ts) {
			this.cid = cid;
			this.ts = ts;
		}
		
		@Override
		public int hashCode() {
			return getCid().hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			TopCommunity tl = (TopCommunity) obj;
			return getCid().longValue() == tl.getCid().longValue();
		}
		
	}
	

	public class TruncLog {
		
		/**
		 * 圈子id 单聊时对方id
		 */
		private Long cid;
		
		/**
		 * 清除聊天记录的时间, 清除记录只是记录下操作时间
		 */
		private Date ts;

		public TruncLog() {
		}
		
		public TruncLog(Long cid, Date ts) {
			this.cid = cid;
			this.ts = ts;
		}

		public Long getCid() {
			return cid;
		}

		public void setCid(Long cid) {
			this.cid = cid;
		}

		public Date getTs() {
			return ts;
		}

		public void setTs(Date ts) {
			this.ts = ts;
		}
		
		@Override
		public int hashCode() {
			return getId().hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			TruncLog tl = (TruncLog) obj;
			return id.longValue() == tl.getCid().longValue();
		}
	}
	
}






