package net.okdi.mob.service;

public interface OkdiAccountInfoService {

	/**
	 * 查询是否开通好递账户
	 * @param accountType 账户类型	0个人，1店铺
	 * @param memberId	用户id
	 * @param shopId 店铺id
	 * @return {"data":{"isHaveOkdiAccount": 0是否开通好递账户"okdiAccount": "" 好递账户},"success": true}
	 */
	String isHaveOkdiAccount(String accountType, String memberId, String shopId);

	/**
	 * 开通好递账户
	 * @param accountType 账户类型	0个人，1店铺
	 * @param memberId	用户id
	 * @param shopId 店铺id
	 * @param accountCard 如果是个人传手机号 如果是店铺传输入的那个账户
	 * @param userPhone	手机号
	 * @param nick 昵称
	 * @return {"data":{"createOkdiAccountFlag":1 开通成功"okdiAccount": "13999999999"  开通成功的账户},"success": true}
	 */
	String createOkdiAccount(String accountType, String memberId, String shopId,String accountCard, String userPhone, String nick);

	/**
	 * 查询该账户是否绑定银行卡或微信账户
	 * @param accountId 返回的账户id
	 * @param flag	0是否绑定银行卡，1是否绑定微信账号
	 * @return {"data":{"haveWechatInfo": [{"weChatCard":"4444444" 微信号}],"isHaveWechatFlag": 1  是否绑定微信号 0 未绑定 1已绑定},"success": true}
	 */
	String isHaveBankOrWechat(String accountId, String flag);

	/**
	 * 创建微信账号
	 * @param accountId 账户的主键id
	 * @param wechatCard	微信账号
	 * @return {"data":{"accountId":148120441004032,"createdTime":1432524134116,"deleteMark":0"id": 148416271548416,"updateTime": "","wechatCard":"hkaslhfash@163.com"},"success":true}
	 */
	String createOkdiWechatInfo(String accountId, String wechatCard);

	/**
	 * 删除微信账号
	 * @param id 查询微信账户时返回的主键id
	 * @return {"success":true 删除成功}
	 */
	String deleteOkdiWechatInfo(String id);

	/**
	 * 查询微信账号
	 * @param accountType 账户类型	0个人，1店铺
	 * @param memberId	用户id
	 * @param shopId 店铺id
	 * @return {"data":[{"weChatCard": "4444444"}],"success":true}
	 */
	String queryOkdiWechatInfo(String accountType, String memberId, String shopId);
}
