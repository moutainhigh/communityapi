package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV3.service.impl.WechatPayBackServiceImpl;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WechatHttpClient extends AbstractHttpClient{

	@Value("${pay_url}")
	private String payUrl; //新版财务url

	public static final Log logger = LogFactory.getLog(WechatHttpClient.class);
	
	public String paycallback(String tradeNum,String payStatus,String attach,String sign,String mchId,String transactionId,String outTradeNo) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("payStatus", payStatus);
		map.put("attach", attach);
		map.put("sign", sign);
		map.put("mchId", mchId);
		map.put("transactionId", transactionId);
		map.put("out_trade_no", outTradeNo);
		String methodName = "ws/recharge/invoke_payment_callback";
		String url = payUrl + methodName;
		this.buildParams(map);
		return Post(url, map);
	}
	
	
	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		return map;
	}


	public String rechargeMoney(String accountId, double money,String activityId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("outAccountId", "100003");//100003 通信运营账户
		paramMap.put("type", "3");//1账户余额 2购物卡3通讯账户
		paramMap.put("accountType", "100001");//100001:个人、100003:店铺
		paramMap.put("activityId", activityId);
		paramMap.put("title", "平台赠送-充值");
		paramMap.put("remark", "平台赠送-充值");
		paramMap.put("money", String.valueOf(money));
		paramMap.put("inAccountId", accountId);
		paramMap.put("platformId", "100002");
		paramMap.put("outerTradeType", "10017");//平台赠送-充值
		String methodName = "ws/trade/rebate";
		String url = payUrl + methodName;
		this.buildParams(paramMap);
		return Post(url, paramMap);
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
			throw new ServiceException("财务异常，查MemberId失败");
		}else{
			Map resMap = (Map) res.get("data");
			memberId = (String) resMap.get("member_id");
		}
		logger.info("外部系统通过accountId查询账户memberId："+memberId);
		return memberId;
	}
	
}
