package net.okdi.httpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

/**
 * @author feng.wang
 * @version V1.0
 */
public class SmsHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool;

	Logger logger = Logger.getLogger(SmsHttpClient.class);
	/**
	 * 叫快递发送短信
	 * @param usePhone 目的手机号
	 * @since v1.0
	 */
	public String callExp(String mobile) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		String methodName = "sms/callExp";
		String url = constPool.getOpenApiUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	public String createTask(String phoneAndNum, Long memberId) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneAndNum", phoneAndNum);
		map.put("memberId", memberId+"");
		String methodName = "task/createTask";
		String url = constPool.getOpenApiUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	
	public String doSmsSendTask(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,
			String msgId, String pickupAddr,String pickupTime, String compName) throws ServiceException {
		//老版的阿里派件群发通知
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("flag", String.valueOf(flag));
		map.put("msgId", msgId);
		//, String pickupAddr,String pickupTime, String compName
		map.put("pickupAddr", pickupAddr);
		map.put("pickupTime", msgId);
		map.put("compName", compName);
		String response = null;
		if(flag==1||flag==4){
			response = Post(constPool.getSmsHttpUrl()+ "taskMassNotice/sendSms", map);
		}else if(flag == 5){
			map.put("flag", "1");
			map.put("pickupTime", "");
			map.put("pickupAddr", "");
			map.put("compName", "");
			//, String pickupTime, String pickupAddr, String compName
			response = Post(constPool.getSmsHttpUrl()+ "taskMassNotice/sendWeiSms", map);
		
		}else if(flag == 9){
			response = Post(constPool.getSmsHttpUrl()+ "taskMassNotice/sendAliTaskSms", map);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	public String newDoSmsSendTask(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,
			String msgId, String pickupAddr,String pickupTime, String compName, String version, String system) throws ServiceException {
		//普通单的延时发送( 微信优先和仅短信 )
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("flag", String.valueOf(flag));
		map.put("msgId", msgId);
		//, String pickupAddr,String pickupTime, String compName
		map.put("pickupAddr", pickupAddr);
		map.put("pickupTime", msgId);
		map.put("compName", compName);
		map.put("version", version);
		map.put("system", system);
		String response = null;
		if(flag == 5){//微信优先,但传过去的flag = 1
			map.put("flag", String.valueOf(1));
			System.out.println("---调用微信优先路径："+constPool.getSmsHttpUrl()+"newMongoSms/newSendKjcxYXBatch");
			response = Post(constPool.getSmsHttpUrl()
					+ "newMongoSms/newSendKjcxYXBatch", map);
		}else if(flag == 1){//仅短信
			map.put("flag", String.valueOf(flag));
			System.out.println("---调用仅短信路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newSendOnlySms");
			response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/newSendOnlySms", map);
		}else if(flag == 9){//仅短信
			map.put("flag", String.valueOf(flag));
			System.out.println("---调用阿里大于路径："+constPool.getSmsHttpUrl()+"newSendChatAndSms/newDoAliSendSms");
			response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/newDoAliSendSms", map);
		}
		if (response == null || "".equals(response))
			return null;
		return response;
	}
	/*public String doSmsSendTask(String accountId,String channelNo, String phoneAndContent,
			Long memberId, String memberPhone, Short flag,
			String msgId, String pickupAddr,String pickupTime, String compName, String version) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", "07");
		map.put("phoneAndContent", phoneAndContent);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone", memberPhone);
		map.put("flag", String.valueOf(flag));
		map.put("msgId", msgId);
		//, String pickupAddr,String pickupTime, String compName
		map.put("pickupAddr", pickupAddr);
		map.put("pickupTime", msgId);
		map.put("compName", compName);
		map.put("version", version);
		String response = null;
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
	*/
	
	public String queryFirstMsgIdByParcelId(String parcelId, Short flag){
		logger.info("根据parcelI: "+parcelId+"查询短信记录是否存在flag: "+flag);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parcelId", parcelId);
		map.put("flag", flag);
		String response = Post(constPool.getSmsHttpUrl()+ "newSendChatAndSms/queryFirstMsgIdByParcelId", map);
		return response;
	}
}
