package net.okdi.apiV3.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import net.okdi.apiV3.service.WalletNewService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.Base64;
import net.okdi.core.util.DateUtils;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

@Service
public class WalletNewServiceImpl extends AbstractHttpClient implements WalletNewService {
	
	@Autowired
    private ConstPool constPool;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
    @Value("${pay_url}")
	private String payUrl; //新版财务url
    @Value("${okdiplatform_url}")
    private String okdiplatformUrl; //运营平台url
    @Value("${passPortUrl}")
    private String ucenterUrl; //Ucenter url
	public static final Log logger = LogFactory.getLog(WalletNewServiceImpl.class);
	
	/**
	 * outer_trade_type:财务系统业务类型
	 * 
	 *  10010 群发通知-仅短信
		10011 群发通知-电话优先
		10012 群发通知-仅电话
		10013 拨打电话
		10014 充值-余额充值到通讯费
		10015 充值-微信充值到通讯费
		10016 平台赠送-注册
		10017 平台赠送-充值
		10018 平台赠送-邀请
		10019 平台赠送-新手体验金
		1002  提现
		1006  返利
	 */
	
	
	/**
	 * 验证是否设置密码
	 */
	@Override
	public String checkAccountPwdIsExist(String accountId) {
		String url = payUrl;
		String methodName="ws/account/password/check";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		logger.info("publicapi检查账户是否设置密码: url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("publicapi检查账户是否设置密码返回值: result ="+result);
		JSONObject res = JSON.parseObject(result);
		Map<String, Object> maps=new HashMap<String, Object>();
		if(res.get("success").toString().equals("true")){
			maps.put("RESULT","success");
			Map o=(Map) res.get("data");
			Map<String,String> map2=new HashMap<String, String>();
			map2.put("flag",o.get("isSetPassword").toString());
			maps.put("DATA", map2);
		}else{
			maps.put("RESULT", "false");
			
			Map<String,String> map2=new HashMap<String, String>();
			map2.put("MSG",res.get("error_msg").toString());
			maps.put("DATA", map2);
		}
		logger.info("请求publicapi返回封装后的数据Map："+maps);
		return JSONArray.toJSONString(maps);
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.service.impl.WalletNewServiceImpl.java.getAccountId 
	 * @Description: TODO(使用外部系统memberId查询账户Id) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-1-22
	 * @auth zhaohu
	 */
	public String getAccountId(Long memberId) {
		/*String accountId = "";
		String url = payUrl;
		String methodName = "ws/account/get/memberId";
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		logger.info("publicapi 使用外部系统memberId查询账户接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 使用外部系统memberId查询账户接口: result =" + result);
		JSONObject res = JSON.parseObject(result);
		if(res.get("success").toString().equals("false")){
			throw new ServiceException("财务异常，查询账户Id失败");
		}else{
			Map resMap = (Map) res.get("data");
			accountId = (String) resMap.get("account_id");
		}
		logger.info("外部系统通过memberId查询账户accountId："+accountId);
		return accountId;*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId+"");
		String accountId = openApiHttpClient.doPassSendStr("expressRegister/getAccountIdByMemberId", map);
		return accountId;
	}
	
	
	/**
	 * 验证钱包旧密码
	 * @throws Base64DecodingException 
	 */
	@Override
	public String validatePassword(String accountId, Long memberId,String password) throws Base64DecodingException {
		try {
			password=new String(Base64.decode(password.getBytes()));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		String url = payUrl;
		String methodName = "ws/account/password/check/correct";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("password", password);
		map.put("_uid", memberId.toString());
		logger.info("publicapi 验证钱包旧密码接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 验证钱包旧密码接口返回结果: result =" + result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.validatePassword.002","请求财务数据异常！");
		}
//		String success=String.valueOf(JSON.parseObject(result).get("success"));
//		if(!"true".equals(success)){
//			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.validatePassword.001","请求财务数据异常！");
//		}
		return result;
	}

	/**
	 * 设置钱包密码
	 */
	@Override
	public String setWalletPassword(String accountId, String password) {
		//先查询是否设置过密码 设置过去修改 没有设置过才去设置  新版财务是2个接口
		System.out.println("传过来的设置密码======="+password);
		String p1 = "";
		try {
			p1 = password;
			password=new String(Base64.decode(password.getBytes()));
			if(this.isMessyCode(password)){
				password=p1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = payUrl+"/ws/account/password/check";//是否设置密码
		Map <String,String>map = new HashMap<String,String>();
		map.put("accountId", accountId);
		String result = this.Post(url, map);
		String success=String.valueOf(JSON.parseObject(result).get("success"));
		if(!"true".equals(success)){
			throw new ServiceException("0", "同步失败！");
		}
		
		Map<String,Object> mapR =(Map<String, Object>) JSON.parseObject(result).get("data");
		
		String isSetPassword = mapR.get("isSetPassword").toString();
		if("0".equals(isSetPassword)){//没有设置密码
			logger.info("此账户没有设置密码，需创建密码！！！");
			String url1=payUrl+"ws/account/createpwd";
			Map <String,String>map1 = new HashMap<String,String>();
			map1.put("accountId", accountId);
			map1.put("password", password);
			String result1 = this.Post(url1, map1);
			if(PubMethod.isEmpty(result1)){
				throw new ServiceException("同步异常！");
			}
			String success1=String.valueOf(JSON.parseObject(result1).get("success"));
			if(!"true".equals(success1)){
				throw new ServiceException("同步失败！");
			}
			Map<String,Object> mapRe  = new HashMap<String,Object>();
			mapRe.put("RESULT", "success");
			logger.info("请求publicapi返回封装后的数据Map："+mapRe);
			return JSON.toJSONString(mapRe);
		}else{//设置密码 去修改密码
			logger.info("此账户已经设置了密码，需修改密码！！！");
			String url2=payUrl+"/ws/account/updatepwd";
			Map <String,String>map2 = new HashMap<String,String>();
			map2.put("accountId", accountId);
			map2.put("newPassword", password);
			String result2 = this.Post(url2, map2);
			if(PubMethod.isEmpty(result2)){
				throw new ServiceException("同步异常！");
			}
			String success2=String.valueOf(JSON.parseObject(result2).get("success"));
			if(!"true".equals(success2)){
				throw new ServiceException("同步失败！");
			}
			Map<String,Object> mapRe  = new HashMap<String,Object>();
			mapRe.put("RESULT", "success");
			logger.info("请求publicapi返回封装后的数据Map："+mapRe);
			return JSON.toJSONString(mapRe);
		}
	}
	
	/**
	 * 是否是正常文字  true乱码   false 正常
	 * */
	 public boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }
	 public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	 
	/**
	 * Base64加密/解密
	 */
	public static void main2(String[] args) throws Base64DecodingException {
		String encode = Base64.encode("123456".getBytes());//加密
		System.out.println(encode);
		String decode = new String(Base64.decode("MTIzNDU2".getBytes()));//解密
		System.out.println(decode);
	}
	
	/**
	 * 查询余额
	 */
	@Override
	public HashMap<String, Object> getBalance(String accountId) {
		DecimalFormat df = new DecimalFormat("0.00");
		HashMap<String, Object> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		//查询余额
		String url = payUrl+"/ws/account/balance/get";
		String result = this.Post(url, map);
		Map<String,Object> obj = JSONObject.parseObject(result);
		if("true".equals(String.valueOf(obj.get("success")))) {
			Map<String,Object> banlanceMap = (Map<String, Object>) obj.get("data");
			resultMap.put("cashAvailableBalance", banlanceMap.get("available_balance"));//现金账户可用余额
		}
		logger.info("请求publicapi返回封装后的数据Map余额数据111："+result);
		//查询通信账户余额
		String url2 = payUrl+"/ws/telecom/getTelecomBook";
		String result2 = this.Post(url2, map);
		logger.info("请求publicapi返回封装后的数据Map余额数据222："+result2);
		Map<String,Object> obj2 = JSONObject.parseObject(result2);
		if("true".equals(String.valueOf(obj2.get("success")))) {
			Map<String,Object> banlanceMap2 = (Map<String, Object>) obj2.get("data");
			resultMap.put("teleBalance", df.format(Double.parseDouble(banlanceMap2.get("balance").toString())));//通信账户余额
			resultMap.put("teleAvailableBalance", df.format(Double.parseDouble(banlanceMap2.get("available_balance").toString())));//通信账户可用余额
			resultMap.put("freezeBalance", df.format(Double.parseDouble(banlanceMap2.get("freeze_balance").toString())));//通信账户冻结/预扣金额
		}
		logger.info("请求publicapi返回封装后的数据Map通信账户余额数据333："+resultMap);
		return resultMap;
	}
	
	/**
	 * 发送验证码
	 */
	@Override
	public String sendVerifyCodeCreateAccount(String mobile, Long busineType,String validTimes,String version) {
		if(PubMethod.isEmpty(mobile)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.sendVerifyCodeCreateAccount.001", "请输入手机号！");
		}
		if(PubMethod.isEmpty(busineType)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.sendVerifyCodeCreateAccount.002", "请选择业务类型！");
		}
		if(PubMethod.isEmpty(validTimes)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.sendVerifyCodeCreateAccount.003", "请选择验证每天发送次数！");
		}
		String url = constPool.getPassPortUrl();
		String methodName="service/sendVerifyCode";
		Map <String,String>map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("busineType", String.valueOf(busineType));
		map.put("validTimes", "true");
		map.put("version", version);
		logger.info("publicapi 发送短信验证码: url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("publicapi 发送短信验证码: result ="+result);
		return result;
	}
	/**
	 * 校验验证码
	 */
	@Override
	public String validVerifyCodeCreateAccount(String mobile, Long busineType,String verifyCode) {
		if(PubMethod.isEmpty(mobile)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.validVerifyCodeCreateAccount.001", "请输入手机号！");
		}
		if(PubMethod.isEmpty(busineType)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.validVerifyCodeCreateAccount.002", "请选择业务类型！");
		}
		if(PubMethod.isEmpty(verifyCode)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.validVerifyCodeCreateAccount.003", "请输入验证码！");
		}
		String url = constPool.getPassPortUrl();
		String methodName="service/validVerifyCode";
		Map <String,String>map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("busineType", String.valueOf(busineType));
		map.put("verifyCode", verifyCode);
		logger.info("publicapi 校验短信验证码: url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("publicapi 校验短信验证码: result ="+result);
		return result;
	}
	/**
	 * 查询我的银行卡列表
	 */
	@Override
	public String findBankCard(String accountId) {
		if(PubMethod.isEmpty(accountId)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.findBankCard.001", "accountId不能为空！");
		}
		String methodName="/ws/account/bankcard/list";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		logger.info("publicapi 查询银行卡列表接口: url ="+payUrl+" methodName="+methodName+" map="+map);
		String result = this.Post(payUrl+methodName, map);
		logger.info("publicapi 查询银行卡列表接口返回结果: result ="+result);
		Map<String,Object> resMap = new HashMap<String,Object>();
		if(PubMethod.isEmpty(result)) {
			resMap.put("RESULT", "error");
			Map<String,Object> reMap = new HashMap<String,Object>();
			reMap.put("MSG", "连接新财务系统失败");
			resMap.put("DATA", reMap);
			return  JSON.toJSONString(resMap); 
		}
		String string = JSON.parseObject(result).get("success").toString();
		if("false".equals(string)){
			resMap.put("RESULT", "error");
			Map<String,Object> reMap = new HashMap<String,Object>();
			reMap.put("MSG", JSON.parseObject(result).get("error_msg"));
			resMap.put("DATA", reMap);
			return  JSON.toJSONString(resMap);
		}
		List jsonArray = JSON.parseObject(result).getJSONArray("data");
		List<Map> resList = new ArrayList<Map>();
		if(!PubMethod.isEmpty(jsonArray)){
			for (Object object : jsonArray) {
				Map<String,String>  rmap = new HashMap<String,String>();
				Map<String,String> rm  = (Map) object;
				rmap.put("id", rm.get("id"));
				rmap.put("cardNum", rm.get("card_num"));
				rmap.put("cardOwnerName", rm.get("card_owner_name"));
				rmap.put("bankId", rm.get("bank_id"));
				rmap.put("bankName", rm.get("bank_name"));
				rmap.put("bindPhone", rm.get("bind_phone"));
				rmap.put("status", rm.get("status"));
				rmap.put("isDefault", rm.get("is_default"));
				rmap.put("picUrl", constPool.getBankImgReadPath()+constPool.getBankPicUrl()+String.valueOf(rm.get("bank_id"))+".png");
				resList.add(rmap);
			}
		} else {
			resMap.put("RESULT", "success");
			resMap.put("DATA", new ArrayList<Object>());
			return  JSON.toJSONString(resMap); 
		}
		resMap.put("RESULT", "success");
		resMap.put("DATA", resList);
		logger.info("请求publicapi返回封装后的数据Map："+resMap);
		return  JSON.toJSONString(resMap); 
	}
	/**
	 * 查询所有支持的银行卡列表
	 */
	@Override
	public String getBankInfoList() {
		Map<String,String> map = new HashMap<String, String>();
		String url = payUrl+"/ws/account/channel";
		String result = this.Post(url, map);
		logger.info("查询所有支持的银行卡列表请求财务返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.getBankInfoList.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 添加银行卡
	 */
	@Override
	public String insertBankCard(String accountId, String bankName,String memberName, String bankCard, String idNum, Long memberPhone,
			Long bankId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("cardNum", bankCard);
		map.put("cardOwnerName", memberName);
		map.put("bankId", String.valueOf(bankId));
		map.put("bankName", bankName);
		map.put("bindPhone", String.valueOf(memberPhone));
		map.put("bankProvinceName", "");
		map.put("bankProvinceId", "");
		map.put("bankCityName", "");
		map.put("bankCityId", "");
		map.put("bankBranchName", "");
		map.put("isDefault", "0");
		map.put("idCardnum", idNum);
		String url = payUrl+"/ws/account/bankcard/add";
		String result = this.Post(url, map);
		logger.info("请求财务返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.insertBankCark.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 删除银行卡
	 */
	@Override
	public String deleteBankCard(Long bankCardId, String accountId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("bankCardId", String.valueOf(bankCardId));
		String url = payUrl+"/ws/account/bankcard/delete";
		String result = this.Post(url, map);
		logger.info("请求财务返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.deleteBankCard.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 提现申请
	 */
	@Override
	public String withdraw(String accountId, String money,String bankCardNum, String bankName, String remark) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("totalAmount", money);
		map.put("title", "提现-"+bankName);
		map.put("remark", remark);
		map.put("platformId", "100002");//平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
		map.put("outerTradeType", "1002");//1005返利 1002提现
		map.put("bankCardNum", bankCardNum);
		String url = payUrl+"/ws/withdraw/create/cardnum";
		String result = this.Post(url, map);
		logger.info("请求财务返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.withdraw.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 查询该账户是否绑定银行卡或微信账户
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> isHaveBankOrWechat(String accountId) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String methodName="/ws/account/bankcard/list";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		logger.info("publicapi 查询银行卡列表是否存在: url ="+payUrl+" methodName="+methodName+" map="+map);
		String result = this.Post(payUrl+methodName, map);
		logger.info("请求财务返回结果："+result);
		List<Map<String,Object>> list = (List<Map<String, Object>>) JSON.parseObject(result).get("data");
		logger.info("银行卡列表size："+list.size());
		if(list != null && list.size()>0){
			resultMap.put("isHaveBankFlag", "1");
		}else {
			resultMap.put("isHaveBankFlag", "0");
		}
		logger.info("publicapi返回手机端数据："+resultMap);
		return resultMap;
	}
	/**
	 * 查询提现额度
	 */
	@Override
	public String checkWithdrawLimit(String accountId) {
		Map<String, String> map = new HashMap<>();
		map.put("accountId", accountId);
		String url = payUrl+"/ws/withdraw/check";
		String result = this.Post(url, map);
		logger.info("请求财务返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("请求财务数据异常！");
		}
		String success=String.valueOf(JSON.parseObject(result).get("success"));
		Map<String,Object> resultMap = (Map<String, Object>) JSON.parseObject(result).get("data");
		HashMap<String,Object> m = new HashMap<String, Object>();
		HashMap<String,Object> results = new HashMap<String, Object>();
		m.put("perWithDrawMin", String.valueOf(resultMap.get("per_withdraw_min")));
		m.put("perWithDrawMax", String.valueOf(resultMap.get("per_withdraw_max")));
		m.put("perDayMax", String.valueOf(resultMap.get("per_day_max")));
		m.put("perWeekMax", String.valueOf(resultMap.get("per_week_max")));
		m.put("perDayTimes", String.valueOf(resultMap.get("per_day_times")));
		m.put("perWeekTimes", String.valueOf(resultMap.get("per_week_times")));
		m.put("accountId", String.valueOf(resultMap.get("account_id")));
		m.put("dayAmount", String.valueOf(resultMap.get("day_amount")));
		m.put("weekAmount", String.valueOf(resultMap.get("week_amount")));
		m.put("monthAmount", String.valueOf(resultMap.get("month_amount")));
		m.put("dayTimes", String.valueOf(resultMap.get("day_times")));
		m.put("weekTimes", String.valueOf(resultMap.get("week_times")));
		m.put("monthTimes", String.valueOf(resultMap.get("month_times")));
		
		String errorMsg=String.valueOf(JSON.parseObject(result).get("error_msg"));
		String errorCode=String.valueOf(JSON.parseObject(result).get("error_code"));
		//1:余额不足;2:每日最多提现 %d 次;3:每周最多提现 %d 次;4:每日提现不能超过 %.2f 元;5:每周提现不能超过 %.2f 元;
		if("err.cash.202".equals(errorCode)){
			errorCode = "1";
		}else if ("err.trade.withdraw.519".equals(errorCode)) {
			errorCode = "2";
		}else if ("err.trade.withdraw.520".equals(errorCode)) {
			errorCode = "3";
		}else if ("err.trade.withdraw.521".equals(errorCode)) {
			errorCode = "4";
		}else if ("err.trade.withdraw.522".equals(errorCode)) {
			errorCode = "5";
		}
		
		if("true".equals(success)){
			results.put("success", "true");
			results.put("data", m);
		}else if ("false".equals(success)) {
			results.put("success", "false");
			results.put("errorMsg", errorMsg);
			results.put("errorCode", errorCode);
			results.put("data", m);
		}
		
		logger.info("publicapi返回手机端数据："+m);
		return JSON.toJSONString(results);
	}
	/**
	 * 查询交易明细
	 */
	@Override
	public HashMap<String, Object> cashTradeList(String accountId,String startDate, String endDate) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String url = payUrl;
		String methodName="/ws/trade/query_trade";
		
		Map<String, String> map = new HashMap<String, String>();
		//获取当月开始和结束时间
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
        String start = null;
        String end = null;
        c.set(Calendar.DATE, 1);
        start = shortSdf.format(c.getTime());
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        end = shortSdf.format(c.getTime());
        if (!PubMethod.isEmpty(startDate)) {
        	map.put("startTime", startDate);
		}else {
			map.put("startTime", start);
		}
        if (!PubMethod.isEmpty(endDate)) {
        	map.put("endTime", endDate);
        }else {
        	map.put("endTime", end);
        }
		map.put("accountId", accountId);
		logger.info("publicapi查询明细: url ="+url+" methodName="+methodName+" map="+map);
		String result = this.Post(url+methodName, map);
		logger.info("publicapi查询明细返回值: result ="+result);
		
		Double mount = 0.0;
		List<Map<String,Object>> recordList = new ArrayList<Map<String,Object>>();
		Map<String,Object> resMap = JSON.parseObject(result);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) resMap.get("data");
		if (resMap.get("success").toString().equals("true")) {
			if (dataList!=null && dataList.size()>0) {
				for (Map<String,Object> mapObj : dataList) {
					Map<String, Object> detail = new HashMap<String, Object>();
					detail.put("picUrl", "");
					//待支付/待付款
					String status = (String) mapObj.get("trade_status");
					if(status.equals("0")||status.equals("160")||status.equals("200")||status.equals("600")){
						status = "2";
					}//交易成功
					if(status.equals("30")||status.equals("180")||status.equals("220")||status.equals("410")||status.equals("610")){
						status = "1";
					}//交易失败
					if(status.equals("40")||status.equals("190")||status.equals("230")){
						status = "3";
					}
					detail.put("status", status);//1交易成功2待支付/代付款3交易失败
					detail.put("tradeNum", mapObj.get("third_trade_num"));//第三方支付订单号
					detail.put("channel", mapObj.get("third_channel"));//第三方资金渠道
					detail.put("bankName", "");
					detail.put("outcome_user_name", "");
					String type = (String) mapObj.get("trade_cat");//0购物 ，1提现，2充值，3通信，4返利，5转账
					if(type.equals("1")){
						type = "2";
					}
					if(type.equals("5")){
						type = "3";
					}
					if(type.equals("2")){
						type = "4";
					}
					if(type.equals("0")){
						type = "1";
					}
					detail.put("type", type);//				 1：付款 2：提现 3：转账 4：充值
					String amount = mapObj.get("total_amount").toString();
					mount += Double.valueOf(amount);
					detail.put("outcome", mapObj.get("total_amount"));
					detail.put("income", mapObj.get("total_amount"));
					detail.put("title", mapObj.get("title"));
					detail.put("income_user_name", mapObj.get("seller_account_id"));
					detail.put("created", mapObj.get("created"));
					detail.put("outcome_cat_name", mapObj.get("buyer_account_id"));
					detail.put("income_cat_name", "");
					detail.put("tid",  mapObj.get("tid"));
					
					recordList.add(detail);
				}
				resultMap.put("incomeTotal",mount);
				resultMap.put("outcomeTotal",mount);
				resultMap.put("DATA", recordList);
			}
		}else {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.cashTradeDetail.001","请求财务数据异常！");
		}
		return resultMap;
	}
	
	/**
	 * 余额充值到通信账户
	 */
	@Override
	public HashMap<String, Object> rechargeByBalance(String accountId,Double money) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		//获取账户余额
		HashMap<String,Object> balanceMap = this.getBalanceByAccountId(accountId);
		Double balance = Double.parseDouble(String.valueOf(balanceMap.get("available_balance")));
		if(balance < money){
			logger.info("账户余额为："+balance+"；充值金额为："+money+"；余额不足！");
			resultMap.put("result", "false");
		}else{
			logger.info("账户余额为："+balance+"；充值金额为："+money+"；可以充值！");
			String memberId = this.getMemberIdByAccountId(accountId);
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("accountId", accountId);
			//平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004) 
			paramMap.put("platform_id", String.valueOf(100002));
			//获取充值奖励金额
			HashMap<String, Object> rewardMap = getActivityRule(money);
			String activityId = rewardMap.get("activityId").toString();//活动ID
			//没有活动或者没有规则
			if ("0".equals(rewardMap.get("isHaveActivity"))) {
				paramMap.put("title", "充值-余额充值到通讯费");
				paramMap.put("remark", "充值-余额充值到通讯费");
				paramMap.put("money", String.valueOf(money));
				paramMap.put("outer_trade_type", String.valueOf(10014));//充值-余额充值到通讯费
				//type --- 1:A账户余额充值到A账户购物卡 2：A账户余额充值到A账户通讯账户 3：(店铺)余额充值到中间分享账户 4:中间分享账户充值到(个人)余额 5：好递运营充值到通讯账户
				paramMap.put("type", String.valueOf(2));
				//调用财务系统充值接口
				String methodName="/ws/recharge/transfer_recharge";
				logger.info("publicapi 账户余额充值到A账户通讯账户接口: url ="+payUrl+" methodName="+methodName+" map="+paramMap);
				String result = this.Post(payUrl+methodName, paramMap);
				logger.info("请求财务返回结果："+result);
				JSONObject object = JSONObject.parseObject(result);
				//请求成功
				if ("true".equals(object.get("success").toString())) {
					resultMap.put("result", "true");
				}else {
					resultMap.put("result", "false");
				}
			//有活动规则
			}else if("1".equals(rewardMap.get("isHaveActivity"))) {
				for (int i = 0; i < 2; i++) {
					//正常余额充值到通讯费
					if(i == 0){
						paramMap.put("title", "充值-余额充值到通讯费");
						paramMap.put("remark", "充值-余额充值到通讯费");
						paramMap.put("money", String.valueOf(money));
						//type --- 1:A账户余额充值到A账户购物卡 2：A账户余额充值到A账户通讯账户 3：(店铺)余额充值到中间分享账户 4:中间分享账户充值到(个人)余额 5：好递运营充值到通讯账户
						paramMap.put("type", String.valueOf(2));
						paramMap.put("outer_trade_type", String.valueOf(10014));//充值-余额充值到通讯费
						//调用财务系统充值接口
						String methodName="/ws/recharge/transfer_recharge";
						logger.info("publicapi 账户余额充值到A账户通讯账户接口: url ="+payUrl+" methodName="+methodName+" map="+paramMap);
						String result = this.Post(payUrl+methodName, paramMap);
						logger.info("请求财务返回结果："+result);
						JSONObject object = JSONObject.parseObject(result);
						//请求成功
						if ("true".equals(object.get("success").toString())) {
							resultMap.put("result", "true");
						}else {
							resultMap.put("result", "false");
							break;
						}
					//充值活动奖励金额调用返利接口
					}else if (i == 1) {
						//查询财务系统通讯账户余额
						String telecomBook = this.queryTelecomBook();
						//财务系统通讯账户余额不足的时候不返利
						if(Double.parseDouble(telecomBook) >= Double.parseDouble(rewardMap.get("rewardMoney").toString())){
							paramMap.put("outAccountId", "100003");//100003 通信运营账户
							paramMap.put("type", "3");//1账户余额 2购物卡3通讯账户
							paramMap.put("accountType", "100001");//100001:个人、100003:店铺
							paramMap.put("activityId", activityId);
							paramMap.put("title", "平台赠送-充值");
							paramMap.put("remark", "平台赠送-充值");
							paramMap.put("money", String.valueOf(rewardMap.get("rewardMoney")));
							paramMap.put("inAccountId", accountId);
							paramMap.put("platformId", "100002");
							paramMap.put("outerTradeType", "10017");//平台赠送-充值
							//调用财务系统返利接口
							String methodName="/ws/trade/rebate";
							logger.info("publicapi 充值奖励活动返利接口: url ="+payUrl+" methodName="+methodName+" map="+paramMap);
							String result = this.Post(payUrl+methodName, paramMap);
							logger.info("请求财务返回结果："+result); 
							//赠送奖励成功推送
//							String memberId = this.getMemberIdByAccountId(accountId);
							if ("true".equals(JSON.parseObject(result).getString("success"))) {
								//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
								this.insertRechargeRebateInfo("1", memberId, accountId, String.valueOf(money), 
										PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "1","现金充值到通讯账户返利成功-个人充值返利","0");
								if(!PubMethod.isEmpty(memberId)){
									Map<String, String> smsMap = new HashMap<String, String>();
									smsMap.put("money",rewardMap.get("rewardMoney").toString());
									smsMap.put("memberId",memberId);
									openApiHttpClient.doPassSendStr("expressRegister/sendSmsByRecharge/", smsMap);
								}else {
									logger.info("没有查到此人的memberId");
								}
							}else {
								//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
								this.insertRechargeRebateInfo("1", memberId, accountId, String.valueOf(money), PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "0","现金充值到通讯账户返利失败-个人充值返利","0");
								logger.info("现金充值到通讯账户充值返利请求失败！记录已经保存，请查看rechargeRebateInfo表！accountId="+accountId);
							}
						}else{
							//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
							this.insertRechargeRebateInfo("1", "", accountId, String.valueOf(money), PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "0","现金充值到通讯账户返利失败-个人充值返利，好递账户余额不足！","1");
							logger.info("现金充值到通讯账户充值返利请求失败！原因：好递账户余额不足未返利！记录已经保存，请查看rechargeRebateInfo表！accountId="+accountId);
						}
					}
				}
			}
			//返利给邀请者--好友充值返利奖励  2016年7月18日15:03:27- by zhaohu  type: 1现金充值到通讯账户 2微信充值 
			String result = this.rechargeMoneyToInvite(accountId, memberId, String.valueOf(money),"1");
			logger.info("现金充值到通讯账户，好友充值返利奖励，请求结果result="+result);
		}
		return resultMap;
	}
	
	/**
	 * 查询账户余额
	 */
	public HashMap<String,Object> getBalanceByAccountId(String accountId) {
		Map<String, String> map = new HashMap<String, String>();
		HashMap<String, Object> balanceMap = new HashMap<String, Object>();
		map.put("accountId", accountId);
		String url = payUrl+"/ws/account/balance/get";
		String result = this.Post(url, map);
		if (PubMethod.isEmpty(result)) {
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.getBalance.001","请求财务数据异常！");
		}
		logger.info("请求publicapi返回封装后的数据Map："+result);
		Map<String,Object> resMap = JSON.parseObject(result);
		if (resMap.get("success").toString().equals("true")) {
			Map<String,Object> resultMap = (Map<String, Object>) resMap.get("data");
			Object balance1 = resultMap.get("balance");//余额
			Object balance2 = resultMap.get("available_balance");//可用余额
			balanceMap.put("balance", balance1);
			balanceMap.put("available_balance", balance2);
		}
		return balanceMap;
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.service.impl.WalletNewServiceImpl.java.getActivityRule 
	 * @Description: TODO(调用运营平台获取正在进行中的充值奖励活动规则) 
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2016-2-26
	 * @auth zhaohu
	 */
	public HashMap<String, Object> getActivityRule(Double money){
		Double rewardMoney = 0.00;
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用运营平台接口
		String methodName="/rechargeActity/getActivityList";
		logger.info("okdiplatform 查询正在进行中的活动规则接口: url ="+okdiplatformUrl+" methodName="+methodName+" map="+null);
		String result = this.Post(okdiplatformUrl+methodName, null);
		logger.info("请求运营平台返回结果："+result);
		if (PubMethod.isEmpty(result)) {
			logger.info("运营平台没有查询到正在进行中的充值奖励活动！请核对数据库！");
			map.put("isHaveActivity", "0");
		}else {
			Map<String,Object> resultMap = JSONObject.parseObject(result);
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			map.put("activityId", dataMap.get("activityId"));
			if (!"".equals(dataMap.get("ruleList")) && dataMap.get("ruleList")!=null) {
				List<Map<String,Object>> mapList = (List<Map<String, Object>>) dataMap.get("ruleList");
				//如果有奖励规则
				if(mapList!=null && mapList.size()>0){
					for(Map<String,Object> mapOBJ :mapList){
						Double startMoney = Double.parseDouble(mapOBJ.get("startMoney").toString());
						Double endMoney = Double.parseDouble(mapOBJ.get("endMoney").toString());
						Double reward = Double.parseDouble(mapOBJ.get("rewardMoney").toString());
						//输入金额在活动规则范围之内
						if (money<=endMoney && money>=startMoney ) {
							rewardMoney = reward;
							map.put("isHaveActivity", "1");
							map.put("rewardMoney", rewardMoney);
							logger.info("充值金额为："+money+";充值活动奖励金额为："+rewardMoney);
							break;
						}else {
							logger.info("输入的金额没有在活动规则范围之内，所以没有充值奖励金额！");
							map.put("isHaveActivity", "0");
						}
					}
				}else{
					logger.info("运营平台没有充值奖励活动规则，请核对数据库！");
					map.put("isHaveActivity", "0");
				}
			}else {
				map.put("isHaveActivity", "0");
			}
		}
		return map;
	}

	/**
	 * 微信充值到通信账户
	 */
	@Override
	public String rechargeByWeChat(String accountId,Double money) {
		HashMap<String, String> paramMapWX = new HashMap<String, String>();
		paramMapWX.put("accountId", accountId);
		paramMapWX.put("payType", "1");//0:公账号支付 1:APP支付
		paramMapWX.put("tradeType", "2");//0:微信充值到余额 1：微信充值到购物卡 2：微信充值到通信账户
		paramMapWX.put("title", "充值-微信充值到通讯费");
		paramMapWX.put("remark", "充值-微信充值到通讯费");
		paramMapWX.put("platform_id", "100002");//平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004) 不能为空
		paramMapWX.put("outer_trade_type", "10015");//10015 充值-微信充值到通讯费
		paramMapWX.put("money", String.valueOf(money));
		//调用财务系统充值接口--微信充值
		String methodName="/ws/recharge/weixin_pay";
		logger.info("publicapi 微信充值到通信账户接口: url ="+payUrl+" methodName="+methodName+" map="+paramMapWX);
		String result = this.Post(payUrl+methodName, paramMapWX);
		logger.info("微信充值请求财务系统返回结果："+result);
		return result;
	}
	/**
	 * 根据输入金额查询充值奖励金额
	 */
	@Override
	public HashMap<String, Object> queryRewardMoney(Double money) {
		Double rewardMoney = 0.00;
		Double reMoney = 0.00;
		HashMap<String, Object> map = new HashMap<String, Object>();
		//查询通讯账户余额
		String telecomBook = this.queryTelecomBook();
		if(!PubMethod.isEmpty(money) && money > 0){
			//查询活动规则奖励
			HashMap<String, Object> activityRule = this.getActivityRule(money);
			if("1".equals(activityRule.get("isHaveActivity"))){
				reMoney = Double.parseDouble(activityRule.get("rewardMoney").toString());
			}
		}
		//调用运营平台接口
		String methodName="/rechargeActity/getActivityList";
		logger.info("okdiplatform 查询正在进行中的活动规则接口: url ="+okdiplatformUrl+" methodName="+methodName+" map="+null);
		String result = this.Post(okdiplatformUrl+methodName, null);
		logger.info("请求运营平台返回结果："+result);
		if (PubMethod.isEmpty(result)) {
			logger.info("运营平台没有查询到正在进行中的充值奖励活动！请核对数据库！");
		}else {
			if(!PubMethod.isEmpty(money) && money > 0 && reMoney <= Double.parseDouble(telecomBook)){
				Map<String,Object> resultMap = JSONObject.parseObject(result);
				Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
				if (!"".equals(dataMap.get("ruleList")) && dataMap.get("ruleList")!=null) {
					List<Map<String,Object>> mapList = (List<Map<String, Object>>) dataMap.get("ruleList");
					//如果有奖励规则
					if(mapList!=null && mapList.size()>0){
						for(Map<String,Object> mapOBJ :mapList){
							Double startMoney = Double.parseDouble(mapOBJ.get("startMoney").toString());
							Double endMoney = Double.parseDouble(mapOBJ.get("endMoney").toString());
							Double reward = Double.parseDouble(mapOBJ.get("rewardMoney").toString());
							//输入金额在活动规则范围之内
							if (money<=endMoney && money>=startMoney ) {
								rewardMoney = reward;
								logger.info("充值金额为："+money+";充值活动奖励金额为："+rewardMoney);
								break;
							}else {
								logger.info("输入的金额没有在活动规则范围之内，所以没有充值奖励金额！");
							}
						}
					}else{
						logger.info("运营平台没有充值奖励活动规则，请核对数据库！");
					}
				}else {
					logger.info("运营平台没有充值奖励活动规则，请核对数据库！");
				}
			}
		}
		map.put("rewardMoney",rewardMoney);
		return map;
	}

	/**
	 * 账单详情
	 */
	@Override
	public HashMap<String, Object> orderDetail(String accountId, String tid) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("accountId", accountId);
		paramMap.put("tid", tid);
		//调用财务系统账单详情接口
		String methodName="/ws/trade/query";
		logger.info("publicapi 查询账单详情接口: url ="+payUrl+" methodName="+methodName+" map="+paramMap);
		String result = this.Post(payUrl+methodName, paramMap);
		logger.info("请求财务系统返回结果："+result);
		Map<String, Object>  jsonMap = (Map<String, Object>) JSONObject.parse(result);
		Map<String, Object>  json = (Map<String, Object>) jsonMap.get("data");
		if (jsonMap.get("success").equals("false")) {
			throw new ServiceException(json.get("error_msg").toString());
		}else{
			String tidOBJ = json.get("tid").toString();
			String outer_tid = json.get("outer_tid").toString();
			String created = json.get("created").toString();
			String outer_trade_type = json.get("outer_trade_type").toString();
			String tradeStatus = json.get("trade_status").toString();
			String amount = json.get("total_amount").toString();
			DecimalFormat df = new DecimalFormat("0.00");
			String total_amount = df.format(Double.parseDouble(amount));
			String trade_cat = json.get("trade_cat").toString();
			
			resultMap.put("tid", tidOBJ);//交易号
			resultMap.put("outer_tid", outer_tid);//订单号
			resultMap.put("created", created);//交易时间
			resultMap.put("outer_trade_type", outer_trade_type);//交易类别
			/**
			 *  10010 群发通知-仅短信
				10011 群发通知-电话优先
				10012 群发通知-仅电话
				10013 拨打电话
				10014 充值-余额充值到通讯费
				10015 充值-微信充值到通讯费
				10016 平台赠送-注册
				10017 平台赠送-充值
				10018 平台赠送-邀请
				10019 平台赠送-新手体验金
				10020 平台赠送-好友充值返利
				10021 平台赠送－关注好递用户（通信费账户）
				10023 平台赠送－关注好递用户（现金余额账户）
				10022 群发通知-短信+群呼
				1002  提现
				1006  平台赠送-邀请爱购猫用户
				10025 扫单通知-短信
				10026 微信充值到余额
				10027 微信充值到金币
			 */
			//交易说明  交易状态
			if("10010".equals(outer_trade_type)){//群发通知-仅短信
				HashMap<String,Object> queryCount = this.queryCount(outer_tid);
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "群发通知-仅短信");
				resultMap.put("tradeMoney", "-"+total_amount);
				resultMap.put("smsOrPhoneCount", queryCount.get("smsOrPhoneCount"));
				resultMap.put("weChatCount", queryCount.get("weChatCount"));
			}else if ("10011".equals(outer_trade_type)) {
				HashMap<String,Object> queryCountAll = this.queryCountAll(outer_tid);
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "群发通知-群呼优先");
				resultMap.put("tradeMoney", "-"+total_amount);
				resultMap.put("phoneCount", queryCountAll.get("phoneCount"));
				resultMap.put("noticeCount", queryCountAll.get("noticeCount"));
				resultMap.put("weChatCount", queryCountAll.get("weChatCount"));
			}else if ("10012".equals(outer_trade_type)) {
				HashMap<String,Object> queryCount = this.queryCount(outer_tid);
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "群发通知-仅群呼");
				resultMap.put("tradeMoney", "-"+total_amount);
				resultMap.put("smsOrPhoneCount", queryCount.get("smsOrPhoneCount"));
				resultMap.put("weChatCount", queryCount.get("weChatCount"));
			}else if ("10013".equals(outer_trade_type)) {
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "拨打电话");
				resultMap.put("tradeMoney", "-"+total_amount);
			}else if ("10014".equals(outer_trade_type)) {
				if(tradeStatus.equals("200")||tradeStatus.equals("210")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("220")){
					resultMap.put("tradeStatus", "交易成功");
				}else if(tradeStatus.equals("230")) {
					resultMap.put("tradeStatus", "交易失败");
				}else if(tradeStatus.equals("240")) {
					resultMap.put("tradeStatus", "已取消");
				}else if(tradeStatus.equals("250")) {
					resultMap.put("tradeStatus", "交易关闭");
				}
				resultMap.put("tradeType", "通信账户充值");
				resultMap.put("payType", "余额支付");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10015".equals(outer_trade_type)) {
				if(tradeStatus.equals("200")||tradeStatus.equals("210")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("220")){
					resultMap.put("tradeStatus", "交易成功");
				}else if(tradeStatus.equals("230")) {
					resultMap.put("tradeStatus", "交易失败");
				}else if(tradeStatus.equals("240")) {
					resultMap.put("tradeStatus", "已取消");
				}else if(tradeStatus.equals("250")) {
					resultMap.put("tradeStatus", "交易关闭");
				}
				resultMap.put("tradeType", "通信账户充值");
				resultMap.put("payType", "微信支付");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10016".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-注册");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10017".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-充值");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10018".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-邀请");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10019".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-新手体验金");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10020".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-好友充值返利");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10021".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-关注好递（通信费）");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10023".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-关注好递（现金）");
				resultMap.put("tradeMoney", "+"+total_amount);
			}else if ("10022".equals(outer_trade_type)) {
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "群发通知-短信+群呼");
				resultMap.put("tradeMoney", "-"+total_amount);
				resultMap.put("phoneCount", this.queryCountAll(outer_tid).get("phoneCount"));
				resultMap.put("noticeCount", this.queryCountAll(outer_tid).get("noticeCount"));
			}else if ("1002".equals(outer_trade_type)) {
				if(tradeStatus.equals("180")){
					resultMap.put("tradeStatus", "交易成功");
				}else if(tradeStatus.equals("120") || tradeStatus.equals("150") || tradeStatus.equals("190")){
					resultMap.put("tradeStatus", "交易失败");
				}else {
					resultMap.put("tradeStatus", "进行中");
				}
				String bankName = json.get("channel_name").toString();
				String bankCard = json.get("bank_card_num").toString();
				bankCard = bankCard.substring(bankCard.length()-4, bankCard.length());
				resultMap.put("tradeType", "提现-"+bankName+"("+bankCard+")");
				resultMap.put("tradeMoney", "-"+total_amount);
				//2016年5月12日17:21:42 by hu.zhao  增加收款-寄件----快递费
			}else if ("200001".equals(outer_trade_type)) {
				if(tradeStatus.equals("0")||tradeStatus.equals("10")||tradeStatus.equals("20")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("30")){
					resultMap.put("tradeStatus", "交易成功");
				}else if(tradeStatus.equals("40")) {
					resultMap.put("tradeStatus", "交易失败");
				}else if(tradeStatus.equals("50")) {
					resultMap.put("tradeStatus", "已取消");
				}else if(tradeStatus.equals("51")) {
					resultMap.put("tradeStatus", "交易关闭");
				}
				resultMap.put("tradeType", "快递费");
				String seller_account_id = json.get("seller_account_id").toString();
				String buyer_account_id = json.get("buyer_account_id").toString();
				if(seller_account_id.equals(accountId)){
					resultMap.put("tradeMoney", "+"+total_amount);
				}else if (buyer_account_id.equals(accountId)) {
					resultMap.put("tradeMoney", "-"+total_amount);
				}
			}else if ("1006".equals(outer_trade_type)) {
				if (tradeStatus.equals("410")) {
					resultMap.put("tradeStatus", "交易成功");
				}else {
					resultMap.put("tradeStatus", "交易失败");
				}
				resultMap.put("tradeType", "平台赠送-邀请爱购猫用户");
				resultMap.put("tradeMoney", "+"+total_amount);
			}if("10025".equals(outer_trade_type)){//群发通知-仅短信
				HashMap<String,Object> queryCount = this.queryCount(outer_tid);
				if(tradeStatus.equals("700")){
					resultMap.put("tradeStatus", "进行中");
				}else if(tradeStatus.equals("710")){
					resultMap.put("tradeStatus", "交易成功");
				}
				resultMap.put("tradeType", "扫单通知-短信");
				resultMap.put("tradeMoney", "-"+total_amount);
				resultMap.put("smsOrPhoneCount", queryCount.get("smsOrPhoneCount"));
				resultMap.put("weChatCount", queryCount.get("weChatCount"));
			}
		}
		return resultMap;
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.service.impl.WalletNewServiceImpl.java.queryCount 
	 * @Description: TODO(请求open查询订单下短信和电话数量) 
	 * @param @param orderId
	 * @param @return   
	 * @return HashMap<String,Object>  返回值类型
	 * @throws 
	 * @date 2016-3-3
	 * @auth zhaohu
	 */
	@Override
	public HashMap<String, Object> queryCount(String orderId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("noticeId", orderId);
		String methodName="/sendPackage/querySmsOrPhoneCount";
		logger.info("请求openapi 查询电话/短信数量: url ="+constPool.getOpenApiUrl()+" methodName="+methodName+" map="+paramMap);
		String result = this.Post(constPool.getOpenApiUrl()+methodName, paramMap);
		logger.info("请求openapi返回结果："+result);
		JSONObject json = JSONObject.parseObject(result);
		if ("false".equals(json.get("success"))) {
			throw new ServiceException("请求openapi查询电话/短信数量接口出错！");
		}else {
			Map<String,Object> map = (Map<String, Object>) json.get("data");
			resultMap.put("smsOrPhoneCount", map.get("smsOrPhoneCount"));
			resultMap.put("weChatCount", map.get("weChatCount"));
		}
		return resultMap;
	}
	@Override
	public HashMap<String, Object> queryCountAll(String orderId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("noticeId", orderId);
		String methodName="/sendPackage/querySmsAndPhoneCount";
		logger.info("openapi 查询电话/短信数量: url ="+constPool.getOpenApiUrl()+" methodName="+methodName+" map="+paramMap);
		String result = this.Post(constPool.getOpenApiUrl()+methodName, paramMap);
		logger.info("请求openapi返回结果："+result);
		JSONObject json = JSONObject.parseObject(result);
		if ("false".equals(json.get("success"))) {
			throw new ServiceException("请求openapi查询电话/短信数量接口出错！");
		}else {
			Map<String,Object> map = (Map<String, Object>) json.get("data");
			resultMap.put("phoneCount", map.get("phoneCount"));
			resultMap.put("noticeCount", map.get("noticeCount"));
			resultMap.put("weChatCount", map.get("weChatCount"));
		}
		return resultMap;
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.service.impl.WalletNewServiceImpl.java.queryTelecomBook 
	 * @Description: TODO(查询财务系统100003通信账户余额) 
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-3-17
	 * @auth zhaohu
	 */
	public String queryTelecomBook(){
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accountId", "100003");
		//调用运营平台接口
		String methodName="ws/telecom/getTelecomBook";
		logger.info("publicapi 查询通信账户余额: url ="+payUrl+" methodName="+methodName+" map="+paramMap);
		String result = this.Post(payUrl+methodName, paramMap);
		logger.info("请求财务系统返回结果："+result);
		JSONObject json = JSONObject.parseObject(result);
		if ("false".equals(json.get("success"))) {
			throw new ServiceException("请求财务系统查询通信账户余额出错！");
		}else {
			Map<String,Object> map = (Map<String, Object>) json.get("data");
			return map.get("available_balance").toString();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV3.service.impl.WalletNewServiceImpl.java.getMemberIdByAccountId 
	 * @Description: TODO(通过accountId获取memberId) 
	 * @param @param accountId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-3-28
	 * @auth zhaohu
	 */
	public String getMemberIdByAccountId(String accountId) {
		String memberId = "";
		String url = payUrl;
		String methodName = "ws/account/get";
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		logger.info("publicapi 使用外部系统accountId查询账户接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 使用外部系统accountId查询账户接口: result =" + result);
		JSONObject res = JSON.parseObject(result);
		if(res.get("success").toString().equals("false")){
			throw new ServiceException("财务异常，查询MemberId失败");
		}else{
			Map resMap = (Map) res.get("data");
			memberId = (String) resMap.get("member_id");
		}
		logger.info("外部系统通过accountId查询账户memberId："+memberId);
		return memberId;
	}

	/**
	 * 微信支付取消支付订单
	 */
	@Override
	public String cancelOrder(String tid, String accountId) {
		String url = payUrl;
		String methodName = "ws/recharge/cancel_recharge";
		Map<String, String> map = new HashMap<String, String>();
		map.put("tid", tid);
		map.put("accountId", accountId);
		logger.info("publicapi 取消支付订单接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 取消支付订单接口返回结果: result =" + result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.cancelOrder.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 继续支付
	 */
	@Override
	public String repayment(String tid, String isJSAPI, String openid) {
		String url = payUrl;
		String methodName = "ws/recharge/repeat_recharge";
		Map<String, String> map = new HashMap<String, String>();
		map.put("tid", tid);
		map.put("isJSAPI", isJSAPI);
		map.put("openid", openid);
		logger.info("publicapi 继续支付订单接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 继续支付订单接口返回结果: result =" + result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV3.service.impl.WalletNewServiceImpl.repayment.001","请求财务数据异常！");
		}
		return result;
	}
	/**
	 * 查询快递员姓名
	 */
	public Map queryMemberName(String memberId) {
		Map resultMap = new HashMap<>();
		String url = constPool.getOpenApiUrl();
		String methodName = "expressRegister/queryMemberName";
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		logger.info("publicapi 查询快递员姓名接口--: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 查询快递员姓名接口 返回结果: result =" + result);
		if(!PubMethod.isEmpty(result)){
			JSONObject obj = JSONObject.parseObject(result);
			if("true".equals(String.valueOf(obj.get("success")))){
				Map reMap = (Map) obj.get("data");
				return reMap;
			}else {
				return resultMap;//createTime  rate百分比  timeLength天
			}
		}else {
			logger.info("publicapi result 失败！！！！！！！！");
			return resultMap;
		}
	}
	/**
	 * 请求ucenter查询是否有--已经向运营平台同步数据的邀请记录？是否是被邀请注册的
	 */
	@Override
	public Map queryInviteRecord(String memberId) {
		Map resultMap = new HashMap<>();
		String url = ucenterUrl;
		String methodName = "promoExpress/queryInviteRecord";
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		logger.info("publicapi 调用ucenter接口--查询是否有已经向运营平台同步数据的邀请记录: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 调用ucenter接口--查询是否有已经向运营平台同步数据的邀请记录 返回结果: result =" + result);
		if(!PubMethod.isEmpty(result)){
			JSONObject obj = JSONObject.parseObject(result);
			if("true".equals(String.valueOf(obj.get("success")))){
				Map reMap = (Map) obj.get("data");
				return reMap;
			}else {
				return resultMap;//promoRegMemberId	promoRegPhone	inviteMemberId	createTime
			}
		}else {
			logger.info("publicapi publicapi 调用ucenter接口--查询是否有已经向运营平台同步数据的邀请记录接口 失败！！！！！！！！");
			return resultMap;
		}
	}
	/**
	 * 请求okdiplatform查询好友充值返利活动
	 */
	@Override
	public Map queryRechargeRebateAct() {
		Map resultMap = new HashMap<>();
		String url = okdiplatformUrl;
		String methodName = "rechargeRebateSets/getLastRechargeRebateSets";
		Map<String, String> map = new HashMap<String, String>();
		logger.info("publicapi 调用运营平台查询好友充值返利活动接口--: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 调用运营平台查询好友充值返利活动接口 返回结果: result =" + result);
		if(!PubMethod.isEmpty(result)){
			JSONObject obj = JSONObject.parseObject(result);
			if("true".equals(String.valueOf(obj.get("success")))){
				Map reMap = (Map) obj.get("data");
				return reMap;
			}else {
				return resultMap;//createTime  rate百分比  timeLength天
			}
		}else {
			logger.info("publicapi 调用运营平台查询好友充值返利活动接口 失败！！！！！！！！");
			return resultMap;
		}
	}
	/**
	 * 好友充值返利
	 */
	public String rechargeMoney(String accountId, double rechargeMoney,double rebateMoney,String activityId,String memberName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		JSONObject object = new JSONObject();
		object.put("memberName", memberName);
		object.put("rechargeMoney", rechargeMoney);
		paramMap.put("outAccountId", "100003");//100003 通信运营账户
		paramMap.put("type", "3");//1账户余额 2购物卡3通讯账户
		paramMap.put("accountType", "100001");//100001:个人、100003:店铺
		paramMap.put("activityId", activityId);
		paramMap.put("title", "平台赠送-好友充值返利");
		paramMap.put("remark", object.toString());//存放充值人的姓名和充值金额
		paramMap.put("money", String.valueOf(rebateMoney));
		paramMap.put("inAccountId", accountId);
		paramMap.put("platformId", "100002");
		paramMap.put("outerTradeType", "10020");//10020 平台赠送-好友充值返利
		String methodName = "ws/trade/rebate";
		String url = payUrl + methodName;
		this.buildParams(paramMap);
		return Post(url, paramMap);
	}
	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		return map;
	}
	
	@Override
	public String rechargeMoneyToInvite(String accountId,String memberId,String rechargeMoney,String type) {
		return rechargeMoneyToInvite(accountId, memberId, rechargeMoney, type, "");
	}
	
	/**
	 * 好友充值赠送奖励--type: 1现金充值到通讯账户 2微信充值
	 */
	@Override
	public String rechargeMoneyToInvite(String accountId,String memberId,String rechargeMoney,String type, String tradeNum) {
		String result = "";
		//1.通过账户id获取memberId
		if(!PubMethod.isEmpty(memberId)){
			//2.通过memberId获取是否有邀请记录--是否是被邀请注册的
			Map inviteRecord =  this.queryInviteRecord(memberId);
			if(!PubMethod.isEmpty(inviteRecord)){
				//3.查询是否有好友充值奖励活动
				Map activity =  this.queryRechargeRebateAct();
				if(!PubMethod.isEmpty(activity)){
					String inviteMemberId = inviteRecord.get("inviteMemberId").toString();//邀请者id
					Long createTime = Long.parseLong(inviteRecord.get("createTime").toString());//邀请时间
					Double rate = Double.parseDouble(activity.get("rate").toString()) / 100;//奖励百分比
					Long timeLength = Long.parseLong(activity.get("timeLength").toString());//有效期
					String activityId = activity.get("id").toString();//活动id
					//4.邀请时间距离当前时间 在有效期内，给予返利；否则不予返利
					Long start = System.currentTimeMillis() - createTime;
					Long end = (24*60*60*1000)*timeLength;
					if(start <= end){
						Map memberMap = this.queryMemberName(memberId);
						String memberName = "";
						if(!PubMethod.isEmpty(memberMap)){
							memberName = PubMethod.isEmpty(memberMap.get("memberName")) ? "" : memberMap.get("memberName").toString();
						}
						Double moneyR = rate*Double.parseDouble(rechargeMoney);
						DecimalFormat  df   = new DecimalFormat("0.00"); 
						String rebateMoney = df.format(moneyR);
						//5.查询邀请者财务账户id并返利
						String inviteAccountId = this.getAccountId(Long.parseLong(inviteMemberId));
						//好友充值返利
						result = this.rechargeMoney(inviteAccountId, Double.parseDouble(rechargeMoney), Double.parseDouble(rebateMoney), activityId, memberName);
						if ("true".equals(JSON.parseObject(result).getString("success"))) {
							//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
							//type: 1现金充值到通讯账户 2微信充值
							//充值类型 1现金充值到通讯费--个人返利 	2微信充值到通讯费--充值个人返利	3微信充值到通讯费--充值好友返利    4现金充值到通讯费--充值好友返利
							if("1".equals(type)){
								this.insertRechargeRebateInfo("4", inviteMemberId, inviteAccountId, rechargeMoney, rebateMoney, "1","现金充值到通讯费-好友充值奖励，受益于被邀请者accountId="+accountId+"，memberId="+memberId+"，成功！","0");
							}else if ("2".equals(type)) {
								this.insertRechargeRebateInfo("3", inviteMemberId, inviteAccountId, rechargeMoney, rebateMoney, "1","微信充值到通讯费-好友充值奖励，受益于被邀请者accountId="+accountId+"，memberId="+memberId+"，成功！","0", tradeNum);
							}
						}else {
							//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
							if("1".equals(type)){
								logger.info("现金充值到通讯费，请求财务返利接口数据异常--好友充值奖励失败！");
								this.insertRechargeRebateInfo("4", inviteMemberId, inviteAccountId, rechargeMoney, rebateMoney, "0","现金充值到通讯费-好友充值奖励，受益于被邀请者accountId="+accountId+"，memberId="+memberId+"，失败！","0");
							}else if ("2".equals(type)) {
								logger.info("微信充值到通讯费，请求财务返利接口数据异常--好友充值奖励失败！");
								this.insertRechargeRebateInfo("3", inviteMemberId, inviteAccountId, rechargeMoney, rebateMoney, "0","微信充值到通讯费-好友充值奖励，受益于被邀请者accountId="+accountId+"，memberId="+memberId+"，失败！","0", tradeNum);
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 插入充值返利记录信息
	 */
	@Override
	public void insertRechargeRebateInfo(String rechargeType,String memberId,String accountId,String rechargeMoney,
			String rebateMoney,String isSuccess,String remark,String isBalanceNotMore) {
		String url = constPool.getOpenApiUrl();
		String methodName = "rechargeRebateInfo/insertRechargeRebateInfo";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rechargeType", rechargeType);
		map.put("memberId", memberId);
		map.put("accountId", accountId);
		map.put("rechargeMoney", rechargeMoney);
		map.put("rebateMoney", rebateMoney);
		map.put("isSuccess", isSuccess);
		map.put("remark", remark);
		map.put("isBalanceNotMore", isBalanceNotMore);
		map.put("tradeNum", "");
		logger.info("publicapi 调用openapi插入充值返利信息接口--: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 调用openapi插入充值返利信息接口返回结果: result =" + result);
	}

	@Override
	public void insertRechargeRebateInfo(String rechargeType,String memberId,String accountId,String rechargeMoney,
			String rebateMoney,String isSuccess,String remark,String isBalanceNotMore, String tradeNum) {
		String url = constPool.getOpenApiUrl();
		String methodName = "rechargeRebateInfo/insertRechargeRebateInfo";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rechargeType", rechargeType);
		map.put("memberId", memberId);
		map.put("accountId", accountId);
		map.put("rechargeMoney", rechargeMoney);
		map.put("rebateMoney", rebateMoney);
		map.put("isSuccess", isSuccess);
		map.put("remark", remark);
		map.put("isBalanceNotMore", isBalanceNotMore);
		map.put("tradeNum", tradeNum);
		logger.info("publicapi 调用openapi插入充值返利信息接口--: url =" + url + " methodName="	+ methodName + " map=" + map);
		String result = this.Post(url + methodName, map);
		logger.info("publicapi 调用openapi插入充值返利信息接口返回结果: result =" + result);
	}

	@Override
	public JSONObject oneTradeRebateLog(String tradeNum, String memberId) {
		int retries = 1;
		String result;
		JSONObject obj = new JSONObject();
		while (retries > 0) {
			result = doSendHasRebateReq(tradeNum, memberId);
			if (result == null) {
				logger.error("是否返利接口第一次调用失败, 重试retries: " + retries);
				continue;
			}
			retries--;
			JSONObject json = JSON.parseObject(result);
			Boolean success = json.getBoolean("success");
			if (success) {
				return JSON.parseObject(json.getString("data"), JSONObject.class);
			}
		}
		return obj;
	}
	
	private String doSendHasRebateReq(String tradeNum, String memberId) {
		Map<String, Object> param = new HashMap<>();
		param.put("tradeNum", tradeNum);
		param.put("memberId", memberId);
		String result = Post(constPool.getOpenApiUrl() + "rechargeRebateInfo/rebatelog", param);
		logger.info("查询是否返利返回值: " + result + " 参数: " + param);
		return result;
	}
	
	/**
	 * 查询好友充值奖励记录
	 */
	@Override
	public List<HashMap<String, Object>> queryRebateRecord(String accountId,
			String page, String rows) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat df = new DecimalFormat("0.00");
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		String url = payUrl;
		String methodName="ws/trade/query_trade_page";
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("accountId", accountId);
		paramMap.put("outerTradeType", "10018,10020,10021,10023,1006");//账单类型
		paramMap.put("page", page);
		paramMap.put("rows", rows);
		paramMap.put("platformId","100002"); //平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
		String result = this.Post(url+methodName, paramMap);
		if(!PubMethod.isEmpty(result)){
			JSONObject jsonObj = JSONObject.parseObject(result);
			if("true".equals(jsonObj.get("success").toString())){
				JSONArray jsonArr = JSONArray.parseArray(JSONObject.parseObject(jsonObj.getString("data")).getString("items"));
				List<Map> list = JSONArray.parseArray(jsonArr.toString(),Map.class);
				if(list!=null && list.size()>0){
					for(Map map : list){
						HashMap<String, Object> resultMap = new HashMap<>();
						String created = map.get("created").toString();
						String outerTradeType = map.get("outer_trade_type").toString();
						String tid = map.get("tid").toString();
						String rebateMoney = map.get("total_amount").toString();
						String remark = map.get("remark").toString();
						Map remarkMap = new HashMap<String, Object>();
						if(!PubMethod.isEmpty(remark)){
							if(!remark.contains("邀请人手机号") && !remark.contains("直接返现")){//为了兼容旧数据
								JSONObject obj = JSONObject.parseObject(remark);
								remarkMap = (Map)obj;//强转map
							}
						}
						//邀请快递员10018  {"memberName":"赵虎","memberPhone":"15011232453"}
						//邀请个人10021	{"memberName":"赵虎","memberPhone":"15011232453"}
						//好友充值10020	{"memberName":"赵虎","rechargeMoney":25}
						String memberName = "" ;
						String memberPhone = "" ;
						String rechargeMoney = "" ;
						if("10018".equals(outerTradeType) || "10021".equals(outerTradeType) || "10023".equals(outerTradeType)|| "1006".equals(outerTradeType)){
							memberName = PubMethod.isEmpty(remarkMap.get("memberName")) ? "" :remarkMap.get("memberName").toString();
							memberPhone = PubMethod.isEmpty(remarkMap.get("memberPhone")) ? "" :remarkMap.get("memberPhone").toString();
						}else if("10020".equals(outerTradeType)){
							memberName = PubMethod.isEmpty(remarkMap.get("memberName")) ? "" :remarkMap.get("memberName").toString();
							rechargeMoney = PubMethod.isEmpty(remarkMap.get("rechargeMoney")) ? "0.00" : df.format(remarkMap.get("rechargeMoney"));
						}
						Date parseDate = null;
						try {
							parseDate = sdf2.parse(created);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						resultMap.put("createTime",sdf.format(parseDate));
						resultMap.put("tid",tid);
						resultMap.put("rebateMoney",rebateMoney);
						resultMap.put("memberName",memberName);
						resultMap.put("memberPhone",memberPhone);
						resultMap.put("rechargeMoney",rechargeMoney);
						resultMap.put("outerTradeType",outerTradeType);
						resultList.add(resultMap);
					}
				}
			}
		}
		return resultList;
	}
	/**
	 * 查询充值活动规则
	 * @return
	 */
	@Override
	public String queryActivityList(){
		//调用运营平台接口
		String methodName="/rechargeActity/getActivityList";
		logger.info("okdiplatform 查询正在进行中的活动规则接口: url ="+okdiplatformUrl+" methodName="+methodName+" map="+null);
		String result = this.Post(okdiplatformUrl+methodName, null);
		logger.info("请求运营平台返回结果："+result);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("请求运营平台项目出错！","net.okdi.apiV3.service.impl.WalletNewServiceImpl.queryActivityList");
		}
		return result;
	}
	/**
	 * 扫码收款生成微信二维码订单
	 */
	@Override
	public String createScanCodeOrder(Double tradeTotalAmount, Long accountId,
			Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tradeTotalAmount",tradeTotalAmount);
		map.put("accountId",accountId);
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("sendPackage/createScanCodeOrder/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.createScanCodeOrder.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查询收款记录
	 */
	@Override
	public String queryExpressFeeRecord(String accountId,String currentPage,String pageSize) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> todayMap = new HashMap<String, Object>();
		String reMap = "";
		String url = payUrl;
		String methodName="ws/trade/query_trade_page";
		map.put("outerTradeType","200001");
		map.put("accountId", accountId);
		map.put("tradeStatus", "30");//交易成功
		map.put("page", currentPage);
		map.put("rows", pageSize);
		map.put("platformId","100002"); //平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
		String result = this.Post(url+methodName, map);
		String items="";
		JSONObject jsonResult=JSONObject.parseObject(result);
		if(jsonResult.getBooleanValue("success")){
			items=JSONObject.parseObject(jsonResult.getString("data")).getString("items");
		}else{
			throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.006", "查询账单列表有误！");
		}
		JSONArray jsona=JSONArray.parseArray(items);
		List<Map> list = JSON.parseArray(jsona.toString(),Map.class);
		String time1="";
		String time2="";
		List<String> listMonth=new ArrayList<String>();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		DecimalFormat df = new DecimalFormat("#0.00");
		List<Map> ls=new ArrayList<Map>();
		if(list.size()>0){
			int tradeCount = 0;//今天交易笔数
			double tradeMoney = 0.00;//今天交易额
			for(Map mp:list){
				Map rmp = new HashMap<>();
				String money=mp.get("total_amount").toString();
				String createTime=mp.get("created").toString();
				String payways=mp.get("pay_ways").toString();
				Date parseCreateTime = sim.parse(createTime);
				String formatTime = sdf.format(parseCreateTime);
				rmp.put("createTime", formatTime);
				rmp.put("money", df.format(Double.parseDouble(money)));
				List<Map> parseArray = JSONArray.parseArray(payways,Map.class);//支付方式集合 type 0 余额 1优惠券 2 微信 3购物卡
				rmp.put("payways", parseArray.get(0).get("type"));
				time2=createTime.substring(0,10);//
				boolean today = DateUtils.isToday(sim.parse(createTime));
				if(today){
					tradeCount++;
					tradeMoney += Double.parseDouble(money);
				}
				//是同一天的放到一起，非同一天的新增一行数据
				if(!time2.equals(time1)){
					listMonth.add(today?"今天":time2);
					time1=time2;
					ls=new ArrayList<Map>();
					ls.add(rmp);
					map2.put(today?"今天":time2,ls);
				}else{
					ls.add(rmp);
				}
			}
			
			todayMap.put("tradeCount", tradeCount);
			todayMap.put("tradeMoney", df.format(tradeMoney));
		}
		
		ArrayList<Entry<String, Object>> listMap = new ArrayList<>(map2.entrySet());
		Collections.sort(listMap, new Comparator<Map.Entry<String, Object>>() {

			@Override
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		
		reMap="{\"items\":"+JSON.toJSONString(map2)+",\"itemsName\":"+JSON.toJSONString(listMonth)+",\"todayCount\":"+JSON.toJSONString(todayMap)+",\"success\":true}";
//		reMap="{\"items\":" + JSONObject.toJSONString(map2,SerializerFeature.SortField, SerializerFeature.WriteMapNullValue) + ",\"itemsName\":" + 
//							  JSONObject.toJSONString(listMonth, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue) + ",\"todayCount\":" + 
//							  JSONObject.toJSONString(todayMap, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue) + ",\"success\":true}";
		return reMap;
	}
	
	
	
	
}

