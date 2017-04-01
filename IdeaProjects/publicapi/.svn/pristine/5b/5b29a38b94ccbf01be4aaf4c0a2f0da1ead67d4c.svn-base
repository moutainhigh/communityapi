package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.ShortLinkHttpClient;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.CommonService;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.SendMsgToCustomerService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@Service("sendMsgToCustomerService")
public class SendMsgToCustomerServiceImpl implements SendMsgToCustomerService{

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SmsHttpClient smsHttpClient;
	
	@Autowired
	private ConstPool constPool; 
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ExpGatewayService expGatewayService;
	
	Logger logger = Logger.getLogger(SendMsgToCustomerServiceImpl.class);
	//private ShortLinkHttpClient shortLinkHttpClient = new ShortLinkHttpClient();
	/**
	 * 此逻辑实现功能：
	 * 1.上传声音文件
	 * 2.生成短链接
	 * 3.发送短信 
	 * @return 
	 */
	@Override
	public String smsAndUpSound(String phone, String compId,String memberPhone, Long memberId, MultipartFile[] myfiles,Short isWaybillNum,Short isIdentifier) {
		logger.info("phone:	"+phone.trim());
		logger.info("compId: "+compId);
		logger.info("memberPhone: "+memberPhone);
		logger.info("memberId: "+memberId);
		logger.info("isWaybillNum: "+isWaybillNum);
		logger.info("isIdentifier: "+isIdentifier);
		//1.上传声音文件
		String soundPath = commonService.uploadSound(memberId, myfiles);
		if(PubMethod.isEmpty(soundPath)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("success", false);
			return JSON.toJSONString(map);
		}
		//2.生成短链接
		Map<String,String> map = new HashMap<String,String>();
		map.put("sound", soundPath.split("[*][*][*]")[0]);
		String url = getOkdiShortLink(map);
		//3.发送短信
		String content ="";
		if(PubMethod.isEmpty(isWaybillNum) || (0 != isWaybillNum && 1 != isWaybillNum)){
			isWaybillNum=1;
		}
		if(PubMethod.isEmpty(isIdentifier) || (0 != isIdentifier && 1 != isIdentifier)){
			isIdentifier=1;
		}
		if (isWaybillNum == 0 && isIdentifier == 0) {
			content = "快递员发来一条留言，点击收听: " + url + " #运单号##编号#";
		} else if (isWaybillNum == 1 && isIdentifier == 1) {
			content = "快递员发来一条留言，点击收听: " + url;
		} else if (isWaybillNum ==0 && isIdentifier == 1) {
			content = "快递员发来一条留言，点击收听: " + url + " #运单号#";
		} else if (isWaybillNum == 1 && isIdentifier ==0) {
			content = "快递员发来一条留言，点击收听: " + url + " #编号#";
		}else{
			 content = "快递员发来一条留言，点击收听: " + url;
		}
		phone=phone.replaceAll(",", "|");
//		String result =  expGatewayService.sendSms(content, phone, memberId, null, null,true,false,soundPath.split("[*][*][*]")[1],null);
		return null;
		/*String result = sendSms(content,phone.trim(),memberPhone.trim(),memberId,compId.trim());
		return result;*/
	}
	
	@Override
	public String sendVoiceSms(String phone, String compId,String memberPhone, Long memberId, MultipartFile[] myfiles,Short isWaybillNum,Short isIdentifier) {
		logger.info("phone:	"+phone.trim());
		logger.info("compId: "+compId);
		logger.info("memberPhone: "+memberPhone);
		logger.info("memberId: "+memberId);
		logger.info("isWaybillNum: "+isWaybillNum);
		logger.info("isIdentifier: "+isIdentifier);
		//1.上传声音文件
		String soundPath = commonService.uploadSound(memberId, myfiles);
		if(PubMethod.isEmpty(soundPath)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("success", false);
			return JSON.toJSONString(map);
		}
		//2.生成短链接
		Map<String,String> map = new HashMap<String,String>();
		map.put("sound", soundPath.split("[*][*][*]")[0]);
		String url = getOkdiShortLink(map);
		//3.发送短信
		String content ="";
		if(PubMethod.isEmpty(isWaybillNum) || (0 != isWaybillNum && 1 != isWaybillNum)){
			isWaybillNum=1;
		}
		if(PubMethod.isEmpty(isIdentifier) || (0 != isIdentifier && 1 != isIdentifier)){
			isIdentifier=1;
		}
		if (isWaybillNum == 0 && isIdentifier == 0) {
			content = "快递员发来一条留言，点击收听: " + url + " #运单号##编号#";
		} else if (isWaybillNum == 1 && isIdentifier == 1) {
			content = "快递员发来一条留言，点击收听: " + url;
		} else if (isWaybillNum ==0 && isIdentifier == 1) {
			content = "快递员发来一条留言，点击收听: " + url + " #运单号#";
		} else if (isWaybillNum == 1 && isIdentifier ==0) {
			content = "快递员发来一条留言，点击收听: " + url + " #编号#";
		}else{
			 content = "快递员发来一条留言，点击收听: " + url;
		}
//		String result =  expGatewayService.sendSms(content, phone, memberId, null, null,false,false,soundPath.split("[*][*][*]")[1],null);
		return null;
		/*String result = sendSms(content,phone.trim(),memberPhone.trim(),memberId,compId.trim());
		return result;*/
	}
	
	@Override
	public String delSound(Integer day) {
		return commonService.delSound(day);
	}
	
	public String sendSms(String content,String phone, String memberPhone, Long memberId, String compId) {
		String channelNO = "03";
		String channelId = compId;
		if (PubMethod.isEmpty(compId)) {
			compId = "33620";
		}
		String[] phoneArrs = phone.split(",");
		StringBuilder sdb = new StringBuilder("a");
		for(String p:phoneArrs){
			sdb.append(p).append("|").append(content).append("^");
		}
		String ResultConten = sdb.toString();//为了兼容
		String doSmsSend = smsHttpClient.doSmsSend_More(channelNO, channelId, memberPhone, ResultConten, null,
				"smsGateway/doSmsSendMore_LXHL", null);//领先互联
		if (PubMethod.isEmpty(doSmsSend)) {
			return "{\"success\":false}";
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("content", ResultConten.substring(1, ResultConten.length()));
		paraMap.put("memberId", String.valueOf(memberId));
		paraMap.put("memberMobile", memberPhone);
		paraMap.put("compId", compId);
		return openApiHttpClient.doPassSendStr("smsSendRecord/saveSmsSendRecordBatch", paraMap);
	}
	
	
	private String getOkdiShortLink(Map<String,String> map){
		
		ShortLinkHttpClient linkClient = new ShortLinkHttpClient();	
		String soundPlayUrl = constPool.getSoundPlayUrl();//访问网站页面
		String shortLinkUrl = constPool.getShortlinkHttpUrl();//获取短链接KEY
		String longLinkUrl = constPool.getLonglinkHttpUrl();//短网址调转到长网址
		
		String params = map2UrlParams(map);
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", soundPlayUrl + "?" + params);
		String shortKey = linkClient.doPost(shortLinkUrl, mapLink);
		
		return longLinkUrl + shortKey;
	}
	
	private static String map2UrlParams(Map<String,String> map){
		Iterator<String> its = map.keySet().iterator();
		StringBuilder sbd = new StringBuilder();
		while(its.hasNext()){
			String key = its.next();
			String value=map.get(key);
			sbd.append(key);
			sbd.append("=");
			sbd.append(value);
			sbd.append("&");
		}
		return sbd.substring(0, sbd.length()-1);
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "11");
		map.put("b", "22");
		map.put("cc", "333");
		String str = map2UrlParams(map);
		System.out.println(str);
	}
}
