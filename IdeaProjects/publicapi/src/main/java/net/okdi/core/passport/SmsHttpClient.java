package net.okdi.core.passport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.SmsSendResult;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class SmsHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool;

	private static Logger logger = Logger.getLogger(SmsHttpClient.class);

	private final static String OUT_PUT = "json";

	private String smsServiceHttpUrl;

	public SmsHttpClient() {
	}

	public SmsHttpClient(String smsServiceHttpUrl) {
		this.smsServiceHttpUrl = smsServiceHttpUrl;
	}

	public String doSmsSend(String signCode, String channelId, String usePhone,
			String content, String extraCommonParam) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("signCode", signCode);
		map.put("channelId", channelId);
		map.put("usePhone", usePhone);
		map.put("content", content);
		map.put("channelNo", "0");
		map.put("extraCommonParam", extraCommonParam);
		// String url = constPool.getSmsServiceHttpUrl()+methodUrl;
		// logger.info(url);
		String response = Post(smsServiceHttpUrl, map);
		if (response == null || "".equals(response))
			return null;
		// logger.info(response);
		return response;
	}

	public String doSmsSend_More(String channelNO, String channelId,
			String userPhone, String content, String extraCommonParam,
			String SendUrl, String compId) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo", "03");
		map.put("channelId", "0");
		map.put("userPhone", userPhone);
		map.put("text", content.substring(1, content.length() - 1));
		map.put("extraCommonParam", compId);
		String response = Post(constPool.getSmsServiceHttpUrl() + SendUrl, map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String doSmsSend_HJZX(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag, String pathUrl,
			String msgId,Short isNum,String pickupTime, String pickupAddr, String compName) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("pathUrl", pathUrl);
		map.put("msgId", msgId);
		map.put("isNum", isNum+"");
		map.put("pickupTime", pickupTime);
		map.put("pickupAddr", pickupAddr);
		map.put("compName", compName);
		String response = "";
		if(flag == 5){//微信优先,但传过去的flag = 1
			map.put("flag", String.valueOf(1));
			System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"mongoSms/sendKjcxYXBatch");
			response = Post(constPool.getSmsHttpUrl()
					+ "mongoSms/sendKjcxYXBatch", map);
		}else if(flag == 1 || flag == 4){//仅短信或者聊天
			map.put("flag", String.valueOf(flag));
			System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"sendChatAndSms/sendOnlySms");
			response = Post(constPool.getSmsHttpUrl()
					+ "sendChatAndSms/sendOnlySms", map);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	public String newDoSmsSend_HJZX(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,
			String msgId,Short isNum,String pickupTime, String pickupAddr, String compName, String version, String system) throws ServiceException {
		/**sms 微信优先
		 * String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,String msgId,Short isNum, String pickupTime, String pickupAddr, 
			String compName, String version
		 */
		/**sms  仅短信
		 * String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,String msgId,Short isNum, String pickupTime, String pickupAddr, String compName, String version
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("msgId", msgId);
		map.put("isNum", isNum+"");
		map.put("pickupTime", pickupTime);
		map.put("pickupAddr", pickupAddr);
		map.put("compName", compName);
		map.put("version", version);
		map.put("system", system);
		String response = "";
		try {
			if(flag == 5){//微信优先,但传过去的flag = 1
				map.put("flag", String.valueOf(1));
				System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"newMongoSms/newSendKjcxYXBatch");
				response = Post(constPool.getSmsHttpUrl()
						+ "newMongoSms/newSendKjcxYXBatch", map);
			}else if(flag == 1){//仅短信
				map.put("flag", String.valueOf(flag));
				System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newSendOnlySms");
				response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/newSendOnlySms", map);
			}else if(flag == 4){//聊天
				map.put("flag", String.valueOf(flag));
				System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newSendSmsChat");
				response = Post(constPool.getSmsHttpUrl()
						+ "newSendChatAndSms/newSendSmsChat", map);
			}
		} catch (Exception e) {
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "发送失败");
			errorMap.put("flag", "");
			return JSON.toJSONString(errorMap);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	public String doAliSendSms(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag, String pathUrl,
			String msgId,Short isNum,String pickupTime, String pickupAddr, String compName, String versionCode) throws ServiceException {
		//老版的阿里短信聊天
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("pathUrl", pathUrl);
		map.put("msgId", msgId);
		map.put("isNum", isNum+"");
		map.put("pickupTime", pickupTime);
		map.put("pickupAddr", pickupAddr);
		map.put("compName", compName);
		map.put("versionCode", versionCode);
		String response = "";
		if(flag == 9 || flag == 4){//仅短信或者聊天
			map.put("flag", flag);
			System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"sendChatAndSms/doAliSendSms");
			response = Post(constPool.getSmsHttpUrl()+ "sendChatAndSms/doAliSendSms", map);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	public String newDoAliSendSms(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag, String pathUrl,
			String msgId,Short isNum,String pickupTime, String pickupAddr, String compName, String versionCode, String system) throws ServiceException {
		//阿里大于发送走这个接口 首页
		/**
		 * String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,String msgId,Short isNum, String pickupTime, 
			String pickupAddr, String compName, String versionCode
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		//map.put("pathUrl", pathUrl);
		map.put("msgId", msgId);
		map.put("isNum", isNum+"");
		map.put("pickupTime", pickupTime);
		map.put("pickupAddr", pickupAddr);
		map.put("compName", compName);
		map.put("version", versionCode);
		map.put("system", system);
		String response = "";
		if(flag == 9){//仅短信
			map.put("flag", flag);
			System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newDoAliSendSms");
			response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/newDoAliSendSms", map);
		}else if(flag == 4){//聊天
			map.put("flag", flag);
			System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newDoAliSendSms");
			response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/newSendAliSmsChat", map);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String querySmsSend_HJZX(Long memberId, String queryTime,
			Integer pageSize, Integer pageNo, Short status, Short type,
			String receiverPhone) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("queryTime", queryTime);
		map.put("pageSize", String.valueOf(pageSize));
		map.put("pageNo", String.valueOf(pageNo));
		map.put("status", status == null ? null : String.valueOf(status));
		map.put("type", type == null ? null : String.valueOf(type));
		map.put("receiverPhone", receiverPhone);
		String response = Post(constPool.getSmsHttpUrl() + "mongoSms/find", map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String querySmsSend_HJZX2(Long memberId, String queryTime,
			Integer pageSize, Integer pageNo, Short status, Short type,
			String receiverPhone, String sendContent, String number, String billNum,String name) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("queryTime", queryTime);
		map.put("pageSize", String.valueOf(pageSize));
		map.put("pageNo", String.valueOf(pageNo));
		map.put("status", status == null ? null : String.valueOf(status));
		map.put("type", type == null ? null : String.valueOf(type));
		map.put("receiverPhone", receiverPhone);
		map.put("sendContent", sendContent);
		map.put("number", number);
		map.put("billNum", billNum);
		map.put("name", name);
		//String response = Post("http://localhost:8081/sms/mongoSms/findMassnotificationrecord", map);
		String response = Post(constPool.getSmsHttpUrl() + "mongoSms/findMassnotificationrecord", map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String querySmsChat(String firstMsgId, int pageNo, int pageSize)
			throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageNo + "");
		map.put("pageSize", pageSize + "");
		map.put("firstMsgId", firstMsgId);
		String response = Post(constPool.getSmsHttpUrl()
				+ "mongoCallBack/querySmsChat", map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String doSmsSendMoreByFree(String content, String SendUrl,
			String userPhone) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo", "02");
		map.put("channelId", "0");
		map.put("userPhone", userPhone);
		map.put("text", content.substring(1, content.length() - 1));
		map.put("extraCommonParam", "");
		String response = Post(constPool.getSmsServiceHttpUrl() + SendUrl, map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}

	public String customerGateway(Map parMap, String url) {
		String response = Post(constPool.getSmsServiceHttpUrl() + url, parMap);
		if (response == null || "".equals(response))
			return null;
		// logger.info(response);
		return response;
	}

	/**
	 * 这个方法保留，但是目前不启用，使用会报错，需要修改 短信发送，多条 创 建 人: 赵云 2013-9-17 下午2:40:12
	 * 
	 * @param channelNo
	 *            渠道标识
	 * @param channelId
	 *            渠道ID
	 * @param text
	 *            短信发送内容，格式如：手机号1|短信内容^手机号2|短信内容2^.....
	 * @param extraCommonParam
	 *            用于记录系统后台替商家或合作客户A发送短信场景业务中的商家或者客户端A的ID（可以为空）
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Object> doSmsSendMore(String baseUrl, String channelNo,
			Long channelId, String text, String extraCommonParam)
			throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo", channelNo);
		map.put("channelId", String.valueOf(channelId));
		map.put("text", text);
		map.put("extraCommonParam", extraCommonParam);
		String methodName = "doSmsSendMore";

		/* msg_service_http_url参数配置systemConfig.properties文件中 */
		String url = baseUrl + methodName;// ConstPool.getSystemValue("msg_service_http_url")
											// + methodName;
		// String url="http://192.168.35.38:8080/express/webservice/"+
		// methodName;
		logger.info(url);
		String response = Post(url, map);
		if (response == null)
			return null;
		logger.info(response);
		/******* 返回值类型转换*******begin ****/
		if (response.indexOf("errSubCode") >= 0) {
			ServiceException e = JSON.parseObject(response,
					ServiceException.class);
			throw new ServiceException(e.getErrCode(), e.getErrSubCode(),
					e.getMessage());
		}
		Map<String, Object> result = JSON.parseObject(response, Map.class);
		/******* end*******begin ****/
		return getSmsSendMoreResult(result);
	}

	private Map<String, Object> getSmsSendMoreResult(Map<String, Object> result) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmsSendResult> ssrs = new ArrayList<SmsSendResult>();
		if (PubMethod.isEmpty(result))
			return null;
		map.put("status", result.get("status"));
		List list = (List) result.get("result");
		for (int i = 0; i < list.size(); i++) {
			Map mapList = (Map) list.get(i);
			SmsSendResult ssr = new SmsSendResult();
			ssr.setMessageNum(Integer.parseInt(mapList.get("messageNum")
					.toString()));
			ssr.setMsg(mapList.get("msg").toString());
			ssr.setOid(mapList.get("oid").toString());
			ssr.setPhone(mapList.get("phone").toString());
			ssr.setPushtime(mapList.get("pushtime").toString());
			ssr.setSpnumber(mapList.get("spnumber").toString());
			ssr.setStatus(mapList.get("status").toString());
			ssrs.add(ssr);
		}
		map.put("result", ssrs);
		return map;
	}

	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		map.put("_type", OUT_PUT);
		return map;
	}

	/**
	 * <功能详细描述> 创 建 人: 文超 创建时间: Apr 2, 2013 11:45:24 AM
	 * 
	 * @param args
	 * @see [类、类#方法、类#成员]
	 */
	public static void main(String[] args) {
		SmsHttpClient client = new SmsHttpClient(
				"http://sms.okdit.net/sms/smsGateway/doSmsSend_sjgr");
		String content = "11234";
		String s = client.doSmsSend("01", "0", "13261658330", content, null);
		System.out.println(s);
	}

	public String sendSmsAndPhone(String accountId,String channelNo, String content,
			Long memberId, String memberPhone, Short flag,String phoneAndWaybillNum,String smsAndWaybillNum) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("content", content);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("flag", String.valueOf(flag));
		map.put("phoneAndWaybillNum", phoneAndWaybillNum);
		map.put("smsAndWaybillNum", smsAndWaybillNum);
		System.out.println("---调用短信路径："+constPool.getSmsHttpUrl()+"sendSmsAndPhone/send");
		String response = Post(constPool.getSmsHttpUrl()
				+ "sendSmsAndPhone/send", map);
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	
	
}
