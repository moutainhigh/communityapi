package net.okdi.api.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.api.entity.PromoAppDownLog;
import net.okdi.api.entity.PromoRecord;
import net.okdi.api.service.PromoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping("/promo")
public class PromoController extends BaseController {

	@Autowired
	PromoService promoService;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入推广后的注册或登录日志</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 上午10:52:12</dd>
	 * @param promoSrect	推广人的长连接密钥
	 * @param promoOper		推广所作的操作	1.注册 2.登录
	 * @param promoRegMemberId	推广注册人的memberId
	 * @param userAgent		浏览器的型号版本
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertPromoRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertPromoRecord(String promoSrect, Short promoOper,
			Long promoRegMemberId, String userAgent) {
		if(PubMethod.isEmpty(promoSrect)){
			return paramsFailure(this.getClass().getName()+"insertPromoRecord.001", "推广的promoSrect 不能为空");
		}
		try {
			PromoRecord pr = new PromoRecord();
			//pr.setMemberId(memberId);
			//pr.setMemberSrc(memberSrc);
			//pr.setProductType(productType);
			pr.setPromoOper(promoOper);
			pr.setPromoRegMemberId(promoRegMemberId);
			pr.setUserAgent(userAgent);
			pr.setCreateTime(new Date());
			Map<String, Object> map = promoService.insertPromoRecord(promoSrect,pr);
			return jsonSuccess(map);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入推广下载app的点击数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 上午11:20:27</dd>
	 * @param promoSrect	推广人的长连接密钥
	 * @param promoRegMemberId	推广注册人的memberId
	 * @param productType		推广产品类型	1.接单王 2.个人端 3.网站 4.站点 5其它
	 * @param appType			下载的app类型 1:android 2:iphone 3:PC(暂无)
	 * @param userAgent			浏览器的型号版本
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertPromoAppDownLog", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertPromoAppDownLog(String promoSrect,Long promoRegMemberId, Short productType, Short appType, 
				String userAgent) {
		if(PubMethod.isEmpty(promoRegMemberId)){
			return paramsFailure(this.getClass().getName()+"insertPromoAppDownLog.001", "推广人的memberId 不能为空");
		}
		if(PubMethod.isEmpty(appType)){
			return paramsFailure(this.getClass().getName()+"insertPromoAppDownLog.002", "appType类型 不能为空");
		}
		if(PubMethod.isEmpty(productType)){
			return paramsFailure(this.getClass().getName()+"insertPromoAppDownLog.003", "推广产品类型 不能为空");
		}
		if(PubMethod.isEmpty(promoSrect)){
			return paramsFailure(this.getClass().getName()+"insertPromoAppDownLog.004", "推广promoSrect不能为空");
		}
		try {
			PromoAppDownLog padl = new PromoAppDownLog();
			padl.setPromoRegMemberId(promoRegMemberId);
			padl.setProductType(productType);
			padl.setAppType(appType);
			padl.setUserAgent(userAgent);
			padl.setCreateTime(new Date());
			promoService.insertPromoAppDownLog(promoSrect,padl);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午1:32:36</dd>
	 * @param memberId		推广人的memberId(可以是客服系统，也可以使普通注册用户)
	 * @param memberSrc		推广人来源  1.客服系统	2.普通注册用户
	 * @param productType	推广产品类型	1.接单王 2.个人端 3.网站 4.站点 5其它
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value="/getLongUrl")
	public String getLongUrl(Long memberId,Short memberSrc,Short productType){
		try {
			String result = promoService.getLongUrl(memberId,memberSrc,productType);
			return jsonSuccess(result);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午1:32:36</dd>
	 * @param memberId		推广人的memberId(可以是客服系统，也可以使普通注册用户)
	 * @param memberSrc		推广app的来源  	1.商铺  2.好递生活用户  3.快递员
	 * @param productType	推广产品类型	1.邀请注册线下商铺  2.邀请好递生活用户  3.邀请快递员
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value="/getShortUrl")
	public String getShortUrl(Long memberId,Short memberSrc,Short productType){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure(this.getClass().getName()+"getShortUrl.001", "推广人的memberId 不能为空");
		}
		if(PubMethod.isEmpty(memberSrc)){
			return paramsFailure(this.getClass().getName()+"getShortUrl.002", "推广人来源memberSrc 不能为空");
		}
		if(PubMethod.isEmpty(productType)){
			return paramsFailure(this.getClass().getName()+"getShortUrl.003", "推广产品类型productType 不能为空");
		}
		try{
			String result = promoService.getShortUrl(memberId,memberSrc,productType);
			return jsonSuccess(result);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>发送短信</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午3:23:20</dd>
	 * @param mobile	电话号码，支持多个，用“,”分割，注意：使用英文的","
	 * @param content	推广短信的内容(目前<140个字)
	 * @param expMobile 收派员的手机号
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value="/sendSms")
	public String sendSms(String mobile,String content,String expMobile,String memberId){
		//System.out.println(content);
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure(this.getClass().getName()+"sendSms.001", "推广人的memberId 不能为空");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure(this.getClass().getName()+"sendSms.002", "推广短信的内容 不能为空");
		}
		if(content.length()>140){
			return paramsFailure(this.getClass().getName()+"sendSms.003", "推广短信的内容 不能长于140");
		}
		try{
			String result = promoService.sendSms("02",0L,mobile,content,expMobile,memberId);
			return jsonSuccess(result);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>发送email</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午3:57:00</dd>
	 * @param emailAddr	发送的email地址
	 * @param title		邮件发送主题
	 * @param content	内容
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value="/sendEMail")
	public String sendEMail(String emailAddr,String title,String content){
		if(PubMethod.isEmpty(emailAddr)){
			return paramsFailure(this.getClass().getName()+"sendEMail.001", "邮件发送地址 不能为空");
		}
		if(PubMethod.isEmpty(title)){
			return paramsFailure(this.getClass().getName()+"sendEMail.001", "邮件发送主题 不能为空");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure(this.getClass().getName()+"sendEMail.002", "发送内容 不能为空");
		}
		try{
			String result = promoService.sendEMail(emailAddr,title,content);
			return jsonSuccess(result);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
