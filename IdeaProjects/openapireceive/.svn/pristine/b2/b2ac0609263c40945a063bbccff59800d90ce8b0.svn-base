package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.PromoAppDownLog;
import net.okdi.api.entity.PromoRecord;
import net.okdi.api.entity.WxShareLog;

public interface PromoService {
	Map<String,Object>  insertPromoRecord(String promoMsg, PromoRecord pr);
	void insertPromoAppDownLog(String promoMsg,PromoAppDownLog padl);
	String getLongUrl(Long memberId, Short memberSrc, Short productType);
	String getShortUrl(Long memberId, Short memberSrc, Short productType);
	String sendSms(String channelNo,Long channelId,String mobile, String content,String expMobile,String memberId);
	String sendEMail(String toAddr,String title,  String content);
	
	void insertWxShareLog(Long memberId,Long sharedMemberId);
	List<WxShareLog> findByShareMId(Long sharedMemberId);
	
	Map<String,Object> insertWxSignLog(Long memberId);
	Boolean queryTodaySign(Long memberId);
}
