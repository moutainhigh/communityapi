package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.mob.service.WalletService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WalletServiceImpl extends AbstractHttpClient implements WalletService {
	
	
    @Autowired
    private ConstPool constPool;
    
    public static final Log logger = LogFactory.getLog(WalletServiceImpl.class);
	@Override
	public String setWalletPassword(String accountCard, String password) {
		String url = constPool.getWalletUrl();
		String methodName="createAcountPassword.aspx";
		Map <String,String>map = new HashMap<String,String>();
		map.put("accountCard", accountCard);
		map.put("password", password);
		String result = this.Post(url+methodName, map);
		return result;
	}
	
	
	@Override
	public String accountCardExist(String accountCard) throws Exception {
		if(PubMethod.isEmpty(accountCard)) {
			throw new ServiceException("publicapi.WalletServiceImpl.accountCardExist.001", "无账户号！");
		}
		String url = constPool.getWalletUrl();
		String methodName="checkAcountCardIsExist.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountCard", accountCard);
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String createAccount(String nick, String userPhone, String catId,
			Long accountCard, Long memberId) {
		if(PubMethod.isEmpty(catId)) {
			throw new ServiceException("publicapi.WalletServiceImpl.createAccount.001", "请选择账户类型！");
		}
		if(PubMethod.isEmpty(accountCard)) {
			throw new ServiceException("publicapi.WalletServiceImpl.createAccount.002", "无账户号！");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("publicapi.WalletServiceImpl.createAccount.003", "memberId不可为空！");
		}
		if(PubMethod.isEmpty(nick)) {
			throw new ServiceException("publicapi.WalletServiceImpl.createAccount.004", "nick不可为空！");
		}
		if(PubMethod.isEmpty(userPhone)) {
			throw new ServiceException("publicapi.WalletServiceImpl.createAccount.005", "userPhone不可为空！");
		}
		String url = constPool.getWalletUrl();
		String methodName="createAccountAndAccountBook.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("nick", nick);
		map.put("userPhone", userPhone);
		map.put("catId", catId);
		map.put("accountCard", String.valueOf(accountCard));
		map.put("memberId", String.valueOf(memberId));
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String cashTradeDetail(String accountCard, String created) {
		if(PubMethod.isEmpty(accountCard)) {
			throw new ServiceException("publicapi.WalletServiceImpl.cashTradeDetail.001", "无账户号！");
		}
		String url = constPool.getWalletUrl();
		String methodName="queryCashTradeDetail.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountCard", accountCard);
		map.put("date", created);
		String result = this.Post(url+methodName, map);
		return result;
	}

	
	@Override
	public String getBalance(Long accountCard, Long memberId, String catId,
			String startTime, String endTime) {
		if(PubMethod.isEmpty(catId)) {
			throw new ServiceException("publicapi.WalletServiceImpl.getBalance.001", "请选择账户类型！");
		}
		if(PubMethod.isEmpty(accountCard)) {
			throw new ServiceException("publicapi.WalletServiceImpl.getBalance.002", "无账户号！");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("publicapi.WalletServiceImpl.getBalance.003", "memberId不可为空！");
		}
		String url = constPool.getWalletUrl();
		String methodName="getBalance.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("catId", catId);
		map.put("accountCard", String.valueOf(accountCard));
		map.put("memberId", String.valueOf(memberId));
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String validatePassword(String accountCard, Long memberId,
			String password) {
		String url = constPool.getWalletUrl();
		String methodName="checkPasswordByAccountCard.aspx";
		Map <String,String>map = new HashMap<String,String>();
		map.put("accountCard", accountCard);
		map.put("memberId", memberId == null?"":memberId+"");
		map.put("password", password);
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String sendVerifyCode(String mobile, Long busineType,
			String validTimes) {
		if(PubMethod.isEmpty(mobile)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请输入手机号！");
		}
		if(PubMethod.isEmpty(busineType)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请选择业务类型！");
		}
		if(PubMethod.isEmpty(validTimes)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请选择验证每天发送次数！");
		}
		String url = constPool.getPassPortUrl();
		String methodName="service/sendVerifyCode";
		Map <String,String>map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("busineType", String.valueOf(busineType));
		map.put("validTimes", validTimes);
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String validVerifyCode(String mobile, Long busineType,
			String verifyCode) {
		if(PubMethod.isEmpty(mobile)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请输入手机号！");
		}
		if(PubMethod.isEmpty(busineType)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请选择业务类型！");
		}
		if(PubMethod.isEmpty(verifyCode)) {
			throw new ServiceException("publicapi.WalletServiceImpl.sendVerifyCode.001", "请输入验证码！");
		}
		String url = constPool.getPassPortUrl();
		String methodName="service/validVerifyCode";
		Map <String,String>map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("busineType", String.valueOf(busineType));
		map.put("verifyCode", verifyCode);
		String result = this.Post(url+methodName, map);
		return result;
	}


	@Override
	public String checkAccountPwdIsExist(Long memberId) {
		String url = constPool.getWalletUrl();
		String methodName="checkAccountPwdIsExist.aspx";
		Map <String,String>map = new HashMap<String,String>();
		map.put("accountCard", memberId+"");
		String result = this.Post(url+methodName, map);
		return result;
	}
	/**
	 * @Method: getBankInfoList 
	 * @return 银行卡类型 credit:信誉卡 deposit:储蓄卡
	 * @see net.okdi.o2o.api.service.WalletService#getBankInfoList() 
	*/
	@Override
	public String getBankInfoList(String cardType) {
		String url = constPool.getWalletUrl();
		String methodName="getBankInfoList.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("cardType", cardType);
		logger.info("okdilifeapi  getBankInfoList  查询所有银行卡 : url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("okdilifeapi  getBankInfoList  查询所有银行卡 : result ="+result);
		return result;
//		if(!PubMethod.isEmpty(result)){
//			Map<String, Object> resultMap =  JSON.parseObject(result);
//			if(!PubMethod.isEmpty(resultMap.get("RESULT")) && "success".equals(resultMap.get("RESULT").toString()) && !PubMethod.isEmpty(resultMap.get("DATA"))){
//				List<Map> returnList = new ArrayList<Map>();
////				List<Map> resultList = JSON.parseArray(,Map.class);
//				String StrMap = resultMap.get("DATA").toString();
//				if(!"[]".equals(StrMap)){
//					Map ListMap =  JSON.parseObject(StrMap);
//					 List<Map> resultList = (List)ListMap.get("LIST2");
//					 for (Map reMap : resultList) {
//						 String readBankPicPath = constPool.getBankPictureReadPath()+reMap.get("id")+".png";
//						 reMap.put("readBankPicPath", readBankPicPath);
//						 returnList.add(reMap);
//					 }
//					 resultMap.put("DATA",returnList);
//					 return JSON.toJSONString(resultMap);
//				}
//				throw new ServiceException("电商管家获得数据List1为空");
//			}
//			return result;
//		}
//		throw new ServiceException("电商管家获得数据为空");
	}


	@Override
	public String getWithdrawLimit(String accountCard, String memberId, String catId, String startTime, String endTime) {
		String url = constPool.getWalletUrl();
		String methodName="getWithdrawLimit.aspx";
		Map <String,String>map = new HashMap<String,String>();
		map.put("accountCard", accountCard);
		map.put("memberId", memberId);
		map.put("catId", catId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		logger.info("okdilifeapi校验短信验证码: url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("okdilifeapi校验短信验证码: result ="+result);
		return result;
	}

	// TODO 1
	@Override
	public String getOKdiLifeCashTradeByType(String accountCard,
			int currentPage, int type) {
		String url = constPool.getWalletUrl();
		String methodName = "getOKdiLifeCashTradeByType";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountCard", accountCard);
		map.put("currentPage", String.valueOf(currentPage));
		map.put("type", String.valueOf(type));
		logger.info("okdilifeapi按账户号和交易类型查询好递生活账单: url =" + url + " methodName=" + methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("okdilifeapi按账户号和交易类型查询好递生活账单返回值: result =" + result);
		return result;
	}

	// TODO 2
	@Override
	public String getOKdiLifeCashTrade(String accountCard, int currentPage) {
		String url = constPool.getWalletUrl();
		String methodName = "getOKdiLifeCashTrade";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountCard", accountCard);
		map.put("currentPage", String.valueOf(currentPage));
		logger.info("okdilifeapi按账户号查询好递生活账单: url =" + url + " methodName=" + methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("okdilifeapi按账户号查询好递生活账单返回值: result =" + result);
		return result;
	}

	// TODO 3
	@Override
	public String getTradeType(String catId) {
		String url = constPool.getWalletUrl();
		String methodName = "getTradeType";
		Map<String, String> map = new HashMap<String, String>();
		map.put("catId", catId);
		logger.info("okdilifeapi查询交易类型: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("okdilifeapi查询交易类型: result =" + result);
		return result;
	}
	
	
	
}