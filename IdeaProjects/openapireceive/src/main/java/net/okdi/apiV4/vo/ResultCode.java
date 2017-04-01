package net.okdi.apiV4.vo;

/**
 * 响应码
 * @ClassName: ResutCode
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月15日 下午1:29:20
 * @version V1.0
 */
public interface ResultCode {

	static interface Success {
		
		String OK = "001";
		
		String FAIL = "002";
		
		String FAIL_MSG = "系统繁忙  请稍后再试";
		
	}
	
	static interface MemberInfo {
		
		/**
		 * memberId不存在
		 */
		String NO_SUCH_MEMB = "101";
		
		String NO_SUCH_MEMB_MSG = "未注册的用户";
		
		/**
		 * 置顶联系人时, 联系人不存在
		 */
		String NO_SUCH_CONTAST = "102";
		
		String NO_SUCH_CONTAST_MSG = "对方不存在";
		
		
	}
	
	static interface Community {
		
		/**
		 * 圈子不存在
		 */
		String COMMUNITY_NOT_EXIST = "201";
		
		String COMMUNITY_NOT_EXIST_MSG = "圈子去哪儿了";
		
		String NOT_MASTER = "102";
		
		String NOT_MASTER_CHANGE = "103";
		
		/**
		 * 只有群主可以设置公告
		 */
		String NOT_MASTER_SETNOTICE = "只有群主可以设置公告";
		
		/**
		 * 只有群主可以转让管理权
		 */
		String NOT_MASTER_CHANGEMASTER = "只有群主可以转让管理权";
	}
	
	static interface JoinCommunity {
		
		/**
		 * 不能重复加圈子
		 */
		String HAS_IN = "301";
		
		String HAS_IN_MSG = "we`re aleady 伐木累";

		/**
		 * 已申请过
		 */
		String REQUESTED = "302";
		
		String REQUESTED_MSG = "申请过了";
		
		/**
		 * 没有加圈请求
		 */
		String NO_SUCH_REQUEST = "303";
		
		String NO_SUCH_REQUEST_MSG = "木有找到加圈申请";
		
	}
}








