package net.okdi.apiV4.vo;

/**
 * 圈子相关的各种状态码, 统一在这里定义
 * @Description: TODO
 * @author hang.yu
 * @date 2016年5月21日 上午11:44:19
 * @version V1.0
 */
public interface CommunityStatusCode {

	public enum CommunityStat {
		
		/**
		 * 0: 圈子默认状态
		 */
		DEFAULT(0), 
		
		/**
		 * 1: 圈子已被删除, 逻辑删除
		 */
		LOGIC_DEL(1), 
		
		/**
		 * 2: 圈子被禁用
		 */
		DISABLE(2);
		
		private int status;
		
		CommunityStat(int status) {
			this.status = status;
		}
		
		public int toStat() {
			return status;
		}
		
	}
	
	/**
	 * 圈子标识
	 * @ClassName: CommunityIden
	 * @Description: TODO
	 * @author hang.yu
	 * @date 2016年5月21日 上午11:59:46
	 * @version V1.0
	 */
	public enum CommunityIden {

		/**
		 * 0: 系统默认创建(如全国34个省市的圈子)
		 */
		SYS(0),
		
		/**
		 * 1:用户创建的圈子
		 */
		USER(1);
		
		private int iden;
		
		CommunityIden(int iden) {
			this.iden = iden;
		}
		
		public int toIden() {
			return iden;
		}
	}
	
}



















