package net.okdi.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.PromoAppDownLogMapper;
import net.okdi.api.dao.PromoRecordMapper;
import net.okdi.api.dao.WxShareLogMapper;
import net.okdi.api.dao.WxSignLogMapper;
import net.okdi.api.entity.PromoAppDownLog;
import net.okdi.api.entity.PromoRecord;
import net.okdi.api.entity.WxShareLog;
import net.okdi.api.entity.WxSignLog;
import net.okdi.api.service.PromoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.mail.SimpleMail;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class PromoServiceImpl extends BaseServiceImpl<String> implements PromoService {

	@Autowired
	PromoRecordMapper promoRecordMapper;
	@Autowired
	PromoAppDownLogMapper promoAppDownLogMapper;
	@Autowired
	NoticeHttpClient noticeHttpClient;
	@Autowired
	SimpleMail simpleMail;
	@Autowired
	SendNoticeService sendNoticeService;
	
	@Autowired
	WxShareLogMapper wxShareLogMapper;
	@Autowired
	WxSignLogMapper wxSignLogMapper;
	
	@Value("${promo.url}")
	private String promoUrl;
	
	@Value("${shortlink_http_url}")
	private String shortLinkUrl;

	@Value("${longlink_http_url}")
	private String longLinkUrl;
	
	@Value("${sms_url}")
	private String smsUrl;
	
	@Value("${promoshop.url}")
	private String promoShopUrl;
	@Value("${promolife.url}")
	private String promoLifeUrl;
	@Value("${promoexpress.url}")
	private String promoExpressUrl;
	
	@Override
	public BaseDao getBaseDao() {
		return null;
	}

	@Override
	public String parseResult(String info) {
		return info;
	}
	
	static DesEncrypt de = new DesEncrypt();
	
	@Override
	public Map<String,Object> insertPromoRecord(String promoSrect,PromoRecord pr) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			Map<String,String> map = deCodeUrlParam(promoSrect);
			pr.setMemberId(Long.valueOf(map.get("memberId")));
			pr.setMemberSrc(Short.valueOf(map.get("memberSrc")));
			pr.setProductType(Short.valueOf(map.get("productType")));
			resultMap.put("productType", Short.valueOf(map.get("productType")));
		}catch(Exception e){
			e.printStackTrace();
			return resultMap;
		}
		
		Long id = IdWorker.getIdWorker().nextId();
		pr.setId(id);
		promoRecordMapper.insert(pr);
		return resultMap;
	}

	@Override
	public void insertPromoAppDownLog(String promoSrect,PromoAppDownLog padl) {
		try{
			Map<String,String> map = deCodeUrlParam(promoSrect);
			padl.setProductType(Short.valueOf(map.get("productType")));
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		Long id = IdWorker.getIdWorker().nextId();
		padl.setId(id);
		promoAppDownLogMapper.insert(padl);
	}

	@Override
	public String getLongUrl(Long memberId, Short memberSrc, Short productType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId + "");
		map.put("memberSrc", memberSrc + "");
		map.put("productType", productType + "");

		String url = getBasicUrl(map, true);
		return url;
	}

	
	private String getBasicUrl(Map<String, String> map, Boolean needDes) {
		String url = buildGetParams(map);
		if (needDes) {
			DesEncrypt de = new DesEncrypt();
			url = de.convertPwd(url, "ENC");
		}
		if(map.get("productType").equals("1")){
			return promoShopUrl + "?" + url;
		}
		if(map.get("productType").equals("2")){
			return promoLifeUrl + "?" + url;
		}else{
			return promoExpressUrl + "?" + url;
		}
		
	}

	private String buildGetParams(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			PubMethod.concat(sb, key + "=" + value, "&");
		}
		String paramStr = sb.toString();
		System.out.println(paramStr);
		return paramStr;
	}

	@Override
	public String getShortUrl(Long memberId, Short memberSrc, Short productType) {
		String longUrl = getLongUrl(memberId,memberSrc,productType);
		//String shortUrl = getShortUrl(longUrl);
		//return shortUrl;//取消掉短链接，现在改为都用长连接
		return longUrl;
	}
	
	public String getShortUrl(String url) {
		String params ="";
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", url);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;
	}

	@Override
	public String sendSms(String channelNo,Long channelId,String mobile, String content,String expMobile,String memberId) {
		String[] mobArrs = mobile.split(",");
		for(String mob:mobArrs){
			//String usePhone = mob;
			String extraCommonParam = ",";
			if(mob.matches("1\\d{10}")){
				noticeHttpClient.doSmsSendMore(channelNo, channelId, mob, content, extraCommonParam,expMobile);
			}
		}
		try{
			Short sms_source = Short.valueOf("3");
			Short phone_type = Short.valueOf("3");
			sendNoticeService.sendSmsAuditOnKnownMemberId(expMobile, mobile, sms_source, phone_type,(short)1,null,Long.valueOf(memberId));
		}catch(Exception e){
			//e.printStackTrace();
		}
		return "true";
	}

	@Override
	public String sendEMail(String toAddr, String title, String content) {
		String[] emailArrs = toAddr.split(",");
		for(String email:emailArrs){
			simpleMail.sendText(title, content, email);
		}
		return "true";
	}
	
	private static Map<String,String> deCodeUrlParam(String promoSrect){
		String requestStr = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			requestStr = de.convertPwd(promoSrect, "");
			String[] requestParams = requestStr.split("&");
			for(int i = 0 ;i < requestParams.length;i++){
				String[] paramPair = requestParams[i].split("=");
				map.put(paramPair[0], paramPair[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	@Override
	public void insertWxShareLog(Long memberId,Long sharedMemberId) {
		WxShareLog record = new WxShareLog();
		record.setMemberId(memberId);
		record.setSharedMemberId(sharedMemberId);
		record.setCreateTime(new Date());
		wxShareLogMapper.insertWxShareLog(record);
	}
	@Override
	public List<WxShareLog> findByShareMId(Long sharedMemberId) {
		return wxShareLogMapper.findByShareMId(sharedMemberId);
	}

	@Override
	public Map<String,Object> insertWxSignLog(Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(queryTodaySign(memberId)){
			map.put("sign", "hasSign");
		}else{
			WxSignLog wsl = new WxSignLog();
			wsl.setId(IdWorker.getIdWorker().nextId());
			wsl.setCreateTime(new Date());
			wsl.setMemberId(memberId);
			wxSignLogMapper.insertWxSignLog(wsl);
			map.put("sign", "success");
		}
		return map;
	}

	@Override
	public Boolean queryTodaySign(Long memberId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("now", new Date());
		List<WxSignLog> querySign = wxSignLogMapper.queryTodaySign(map);
		if(querySign.size()>0)
			return true;
		return false;
	}

	public static void main(String[] args) {
		Map m = deCodeUrlParam("buk/gQXlTzMGjiZDAKw3pU5PYXprTHE1S1j3ZXjQDBh0rKRICNfUoooyk9h0ViEN");
		System.out.println(m);
	}
}
