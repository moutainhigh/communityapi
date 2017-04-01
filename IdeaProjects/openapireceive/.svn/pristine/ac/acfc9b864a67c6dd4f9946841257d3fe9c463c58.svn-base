package net.okdi.core.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.entity.TaskRemind;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.amssy.common.util.primarykey.IdWorker;

/**
 * @description: copy from okdiweb
 * @author feng.wang
 * @date 2014-9-5
 * @version: 1.0.0
 */
public class NoticeHttpClient extends AbstractHttpClient<String> {

	private static Logger logger = Logger.getLogger(NoticeHttpClient.class);

	private final static String OUT_PUT = "json";
	
	@Value("${ucenter.url}")
	private String ucenterUrl;
	
	@Value("${o2opub.url}")
	private String o2opubUrl;
	
	@Value("${validateCenterUrl}")
	private String validateCenterUrl;

	@Value("${sms_url}")
	private String URL;
	
	@Value("${sms_zt_url}")
	private String smsZtUrl;

	@Value("${mob_push_url}")
	private String mobPushUrl;

	@Value("${pc_push_url}")
	// http://192.168.31.204:8080/pushSocket/
	private String pcPushUrl;

	@Value("${shortlink_http_url}")
	private String shortLinkUrl;

	@Value("${longlink_http_url}")
	private String longLinkUrl;

	@Value("${parcel_okdi_url}")
	private String parcelOkdiUrl;

	@Value("${parcel_personal_url}")
	private String parcelPersonnalUrl;
	@Value("${promo.okdi.express.url}")
	private String promoOkdiExpressUrl;
	/**
	 * 个人端手机客户端下载跳转地址
	 */
	@Value("${personal_jump_url}")
	private String personalJumpUrl;
    @Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 接单王手机客户端下载跳转地址
	 */
	@Value("${exp_jump_url}")
	private String expJumpUrl;
	@Value("${callBackErp}")
	private String erpUrl;
	@Value("${sms_zt_url}")
	private String ztURL;
	@Value("${weichat_push_url}")
	private String wechatPushUrl;
	@Value("${openApiParcelUrl}")
	private String openApiParcelUrl;
	
	/** 
	 * 发送短信
	 * @param channelNo 签名代码
	 * @param channelId 渠道ID
	 * @param usePhone 目的手机号
	 * @param content 短信内容
	 * @param extraCommonParam 额外参数
	 * @since v1.0
	*/
	public String doSmsSend(String channelNo, Long channelId, String usePhone, String content, String extraCommonParam) throws ServiceException {
//		Map<String, String> map = new HashMap<String, String>();
//		// map.put("signCode", "01");
//		map.put("channelNo", channelNo);
//		map.put("channelId", paramsToStr(channelId));
//		map.put("usePhone", usePhone);
//		map.put("content", content);
//		map.put("extraCommonParam", extraCommonParam);
//		// String methodName = "smsGateway/doSmsSend_zd";
//		//String methodName = "smsGateway/doSmsSend";
//		String methodName = "smsGateway/doSmsSend_LXHL";
//		String url = URL + methodName;
//		this.buildParams(map);
//		String response = Post(url, map);
//		logger.info("调用短信接口返回值：" + response);
//		return response;
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo", "03");
		map.put("content", content);
		map.put("type", "2");
		map.put("mobile", usePhone);
		String methodName = "xycSms/sendSms";
		// String methodName = "smsGateway/doSmsSend_LXHL";
		String url = ztURL + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	/** 
	 * 发送短信
	 * @param usePhone 目的手机号
	 * @param content 短信内容
	 * @since v1.0
	*/
	public String doSmsSendMore(String channelNo, Long channelId, String userPhone, String content, String extraCommonParam,String memberPhone) throws ServiceException {
		
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("channelNo","02");
		map.put("channelId", "0");
		map.put("userPhone", userPhone);
		map.put("text",userPhone+"|"+content);
		map.put("extraCommonParam", "");
		
		String methodName = "smsGateway/doSmsSendMore_LXHL";
		String url = URL + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		logger.info("调用短信发送多条接口返回值：" + response);
		return response;
	}
	
	/**
	 * PC端推送接口（socket推送）
	 * 
	 * @Method: pushSocketPC
	 * @Description:
	 * @param memberId
	 *            (非空)个人用户memeberId，支持批量的memberId,使用","分隔，例如：621411635383583,
	 *            621411635383582
	 * @param type
	 *            消息类型 (非空) 目前定义为>1000的业务类型
	 * @param title
	 *            传送的标题
	 * @param subtitle
	 *            副标题
	 * @param content
	 *            传送的内容(非空)
	 * @param extraParam
	 *            额外的参数，一般定义为aa||bb||cc的形式，具体由手机前端确定
	 * @return 只是表示调用成功 {"pushflag":"success"}
	 */
	public String pushSocketPC(String channelNo,String memberId, String type, String title, String subtitle, String content, String extraParam, int taskNum) {
		if (PubMethod.isEmpty(memberId)) {
			return null;
		}
		if (PubMethod.isEmpty(channelNo)) {
			channelNo="05";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo",channelNo);
		map.put("memberId", memberId);
		map.put("type", type);
		map.put("title", title);
		map.put("subtitle", subtitle);
		map.put("content", content);
		map.put("extraParam", extraParam);
		map.put("taskNum", String.valueOf(taskNum));
		map.put("id", String.valueOf(IdWorker.getIdWorker().nextId()));
		String methodName = "/pushMsg/sendSocketMsg";
		String url = pcPushUrl + methodName;
		logger.debug("socket推送传送数据："+map);
		logger.debug("调用PC推送接口地址URL=:" + url + ",任务数量=:" + String.valueOf(taskNum) + ",标题=：" + title + ",副标题=：" + subtitle + ",推送内容=：" + content);
		this.buildParams(map);
		String response = Post(url, map);
		return response;

	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证是否可以推送socket</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-28 下午2:06:04</dd>
	 * @param channelNo
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String valSocketOnline(String channelNo,String memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("channelNo", channelNo);
		String methodName = "/pushMsg/isOnline";
		String url = pcPushUrl + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;

	}
	
	public String getPersonalShortUrl(String expMemberLon, String expMemberLat, String expMemberMob) {
		if(PubMethod.isEmpty(expMemberLon)||PubMethod.isEmpty(expMemberLat)||PubMethod.isEmpty(expMemberMob)){
			return null;
		}
		String params = "";
		params = "sendLon=" + expMemberLon + "" + "&sendLat=" + expMemberLat + "" + "&sendMob=" + expMemberMob;
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", parcelPersonnalUrl + "?" + params);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;

	}

	public String getOkdiShortUrl(String sendAddress, String sendMobile) {
		if(PubMethod.isEmpty(sendAddress)||PubMethod.isEmpty(sendMobile)){
			return null;
		}
		String params = "sendAddress=" + PubMethod.escape(sendAddress) + "" + "&sendMob=" + sendMobile;
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", parcelOkdiUrl + "?" + params);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;

	}
	public String getPromoOkdiExpressUrl() {
		String params = "";
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", promoOkdiExpressUrl + "?" + params);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;

	}
	public String getPersonalLoadtUrl() {
		String params ="";
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", personalJumpUrl + "?" + params);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;

	}
	
	public String getExpLoadtUrl() {
		String params ="";
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", expJumpUrl + "?" + params);
		String shortKey = Post(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;

	}

	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		map.put("_type", OUT_PUT);
		return map;
	}

	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return info;
	}

	public String pushExtMsg(String channelNo, String deviceType, String deviceToken, String type, String title, String content, String extraParam, String msgType) {
		if (hasNull(channelNo, deviceType, deviceToken)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelNo", channelNo);
		map.put("deviceType", deviceType);
		map.put("deviceToken", deviceToken);
		map.put("type", type);
		map.put("title", title);
		map.put("content", content);
		map.put("extraParam", extraParam);
		map.put("msgType", msgType);
		String methodName = "/pushMsg/sendExtMsg";
		String url = mobPushUrl + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}
	
	public String initBadge(String deviceToken) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceToken", deviceToken);
		String methodName = "/pushMsg/initBadge";
		String url = mobPushUrl + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}
	
	public String getLifeAndShopNum(Integer countType){
		Map<String, String> map = new HashMap<String, String>();
		map.put("countType", countType.toString());
		String methodName = "/service/getLifeAndShopNum";
		String url = ucenterUrl + methodName;
		this.buildParams(map);
		return Post(url, map);
	}
	
	/**
	 * 根据类型获取好递生活和好递超市注册用户的总数
	 * @param countType 1：统计总数 2：当前月注册总数 3：当前月每日注册总数
	 * @return
	 */
	public String getShopNum(Integer countType){
		Map<String, String> map = new HashMap<String, String>();
		map.put("countType", countType.toString());
		String methodName = "/shopInfo/getShopNum";
		String url = o2opubUrl + methodName;
		this.buildParams(map);
		return Post(url, map);
	}


	private boolean hasNull(Object... objs) {
		for (Object obj : objs) {
			if (PubMethod.isEmpty(obj)) {
				return true;
			}
		}
		return false;
	}

	public String ifReg(String mobile) {
		if (PubMethod.isEmpty(mobile)) {
			return null;
		}
		//validateCenterUrl=http://ucenter.okdit.net/ucenter/service/validate
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		String ucenterUrl =validateCenterUrl;
		this.buildParams(map);
		String response = Post(ucenterUrl, map);
		return response;

	}
	
	/** 
	 * 同步运营平台推广数据
	 * @param productType 产品类型 1.商铺  2.好递生活用户  3.快递员
	 * @param promoRegMemberId被推广人的memberId
	 * @param recommendNode操作状态2：登录，3：审核 ，4：完善
	 * @param flag是否注册了站点/店铺   0未注册 1已注册
	 * @return
	 * @since v1.0
	*/
	public String updateIsPromoFlag(Short productType,Long promoRegMemberId,Short recommendNode,Short flag){
		Map<String, String> map = new HashMap<String, String>();
		map.put("productType", productType==null?"":String.valueOf(productType));
		map.put("promoRegMemberId", promoRegMemberId==null?"":promoRegMemberId.toString());
		map.put("recommendNode", recommendNode==null?"":String.valueOf(recommendNode));
		map.put("flag", flag==null?"":String.valueOf(flag));
		String methodName = "/promo/updateIsPromoFlag";
		String url = ucenterUrl + methodName;
		return Post(url, map);
	}
	
	public String memberInvitation(String memberPhone,String memberName,String netName,String siteName,Long netId,Long siteId,String shopId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberPhone", memberPhone);
		map.put("memberName",memberName);
		map.put("netName", netName);
		map.put("siteName", siteName);
		map.put("netId", String.valueOf(netId));
		map.put("siteId", String.valueOf(siteId));
		map.put("shopId", shopId);
		String methodName = "regCourierConsumeSetExpress.aspx";
		String url = erpUrl + methodName;
		return Post(url, map);
	}
	
	public String updatePromo(Map<String,String> ucenterParam){
		String methodName = "/promoExpress/updatePromo";
		String url = ucenterUrl + methodName;
		return Post(url, ucenterParam);
	}
	
	/**
	 *	修改user认证状态 
	 * @param ucenterParam
	 * @return
	 */
	public String updateUserIdentityStatus(Map<String,String> ucenterParam){
		String methodName = "/service/updateIdentityStatus";
		String url = ucenterUrl + methodName;
		return Post(url, ucenterParam);
	}
	
	/**
	 *	查询邀请关系
	 * @param ucenterParam
	 * @return
	 */
	public String queryPromo(Map<String,String> ucenterParam){
		String methodName = "/promoExpress/findPromoRel";
		String url = ucenterUrl + methodName;
		return Post(url, ucenterParam);
	}
	
	
//	 * memberwx    爱购猫微信号(手机号)
//	 * sendContent 发送内容
//	 * memberId    收派员Id
//	 * memberPhone 收派员电话
//	 * smsSource   信息类型{1.派送员 2.客户}
//	 * taskId      取件任务  
//	 * */
	
	public void saveMsg(String senderPhone, String senderAddress,
			Long memberId, String memberPhone,Long taskId) {
		TaskRemind taskRemind=new TaskRemind();
		Date now=new Date();
		taskRemind.setUid(IdWorker.getIdWorker().nextId());
		taskRemind.setMemberId(memberId);
		taskRemind.setMemberPhone(memberPhone);
		taskRemind.setTaskId(taskId);
		taskRemind.setSenderPhone(senderPhone);
		taskRemind.setSenderAddress(senderAddress);
		taskRemind.setRemindStatus((short)0);
		taskRemind.setCreateTime(now);
		taskRemind.setUpdateTime(now);
		taskRemind.setTaskStatus((short)0);
		taskRemind.setIsRemove((short)0);
		taskRemind.setIsRead((short)0);
		mongoTemplate.insert(taskRemind);
//		Map<String,String>  parMap = new HashMap<String,String>();
//		parMap.put("memberwx", memberwx);
//		parMap.put("sendContent", sendContent);
//		parMap.put("memberId", String.valueOf(memberId));
//		parMap.put("memberPhone", memberPhone);
//		parMap.put("smsSource", "2");
//		parMap.put("taskId", String.valueOf(taskId));
//		String methodName = "mongoSms/saveMsgLoger";
//		String url = smsZtUrl + methodName;
//		return  Post(url, parMap);
//		return "";
	}
	
	
	public static void main(String[] args) {
		NoticeHttpClient client = new NoticeHttpClient();
		/* 短信的单条发送 */
		// client.doSmsSend("07", 0L, "18911302131", "测试", null);
		// String result=client.pushSocketPC("2021401770962002", "110", "王锋测试",
		// "王锋测试", "11111111");
		// String result=client.getOkdiShortLink("北京市海淀区田村路43号", "顺风",
		// "116.221542", "39.938716",117149584900097L,
		// "http://www.okdi.net/nfs_data/comp/102.png");
		// System.out.println(result);
		
		//doSmsSendMore
		//mobile:13216666666, content:请速到185666666666来取快递expMobile:18556663333 memberId:142639045615616
		client.URL = "http://192.168.35.38:8880/customerWeb/webservice/";
		String result = client.doSmsSendMore("02", 0L, "13216666666", "请速到185666666666来取快递", "", "18556663333");
		System.out.println(result);
	}
	
	/**
	 *	给微信端推送取消的原因pushCancelReason
	 * @param ucenterParam
	 * @return
	 */
	public String pushCancelReason(Map<String,String> param){
		String methodName = "weichat/pushCancelOrderTemplate";
		String url = wechatPushUrl + methodName;
		logger.info("发货中==========================给微信端推送取消的原因:url:"+url);
		 String result = Post(url, param);
		 logger.info("发货中============================给微信端推送取消的结果:"+result);
		 return result;
	}
	
	
	/**
	 *	快递员转给代收点
	 * @param ucenterParam
	 * @return
	 */
	public String courierToAgent(Map<String,String> param){
		String methodName = "sendPackageReport/insertSendForms";
		String url = openApiParcelUrl + methodName;
		logger.info("发货中===============快递员转给代收点:url:"+url);
		 String result = Post(url, param);
		 logger.info("发货中===============快递员转给代收点:"+result);
		 return result;
	}
	
	

	
	
	

}
