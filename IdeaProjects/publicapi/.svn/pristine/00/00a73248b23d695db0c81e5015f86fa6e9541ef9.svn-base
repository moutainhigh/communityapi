package net.okdi.apiV3.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

public interface WalletNewService {

	String checkAccountPwdIsExist(String accountId);

	String validatePassword(String accountId, Long memberId, String password) throws Base64DecodingException;

	String setWalletPassword(String accountId, String password);

	HashMap<String, Object> getBalance(String accountId);

	String sendVerifyCodeCreateAccount(String mobile, Long busineType,String validTimes,String version);

	String validVerifyCodeCreateAccount(String mobile, Long busineType,String verifyCode);

	String findBankCard(String accountId);

	String getBankInfoList();

	String insertBankCard(String accountId, String bankName,String memberName, String bankCard, String idNum, Long memberPhone,Long bankId);

	String deleteBankCard(Long bankCardId, String accountId);

	String withdraw(String accountId, String money, String bankCardNum,String bankName, String remark);

	HashMap<String, Object> isHaveBankOrWechat(String accountId);

	String checkWithdrawLimit(String accountId);

	HashMap<String, Object> rechargeByBalance(String accountId, Double money);

	String rechargeByWeChat(String accountId, Double money);

	HashMap<String, Object> cashTradeList(String accountId, String startDate,
			String endDate);

	HashMap<String, Object> queryRewardMoney(Double money);

	HashMap<String, Object> orderDetail(String accountId, String tid);
	public String getAccountId(Long memberId);

	String cancelOrder(String tid, String accountId);

	String repayment(String tid, String isJSAPI, String openid);

	Map queryInviteRecord(String memberId);

	Map queryRechargeRebateAct();

	String rechargeMoneyToInvite(String accountId,String memberId, String rechargeMoney,String type);
	
	String rechargeMoneyToInvite(String accountId,String memberId, String rechargeMoney,String type, String tradeNum);

	void insertRechargeRebateInfo(String rechargeType, String memberId,
			String accountId, String rechargeMoney, String rebateMoney,
			String isSuccess, String remark,String isBalanceNotMore);

	void insertRechargeRebateInfo(String rechargeType, String memberId,
			String accountId, String rechargeMoney, String rebateMoney,
			String isSuccess, String remark,String isBalanceNotMore, String tradeNum);

	
	List<HashMap<String, Object>> queryRebateRecord(String accountId, String page, String rows);

	HashMap<String, Object> queryCount(String orderId);

	HashMap<String, Object> queryCountAll(String orderId);

	String queryActivityList();

	String createScanCodeOrder(Double tradeTotalAmount, Long accountId,
			Long memberId);

	String queryExpressFeeRecord(String accountId,String currentPage,String pageSize) throws ParseException;

	JSONObject oneTradeRebateLog(String tradeNum, String memberId);
}
