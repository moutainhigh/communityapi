package net.okdi.mob.service;

public interface BankAccountService {

	/**
	 * 查询我的银行卡
	 * @param accountId 好递账户id
	 * @return {成功：{"success":true}失败：{"success":false,"errCode":"err.001","msg":"XXX"}}
	 */
	String findBankCark(String accountId);

	/**
	 * 删除银行卡
	 * @param accountId 好递账户id
	 * @param id 银行卡号id
	 * @return {成功：["success": true}失败：{"success":false,"errCode":"err.001","msg":"XXX"}	
	 */
	String deleteBankCark(String accountId, String id);
	
	/**
	 * 添加银行卡
	 * @param accountId 好递账户id
	 * @param bankName 银行名称
	 * @param memberName 持卡人姓名
	 * @param bankCard 银行卡号
	 * @param idNum 持卡人身份证号
	 * @param memberPhone 预留手机号
	 * @return {成功：["success": true}失败：{"success":false,"errCode":"err.001","msg":"XXX"}	
	 */
	String insertBankCark(String accountId, String bankName, String memberName,
			String bankCard, String idNum, String memberPhone);
}
