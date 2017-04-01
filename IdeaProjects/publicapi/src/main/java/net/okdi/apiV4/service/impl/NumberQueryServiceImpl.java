package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;

import net.okdi.apiV3.controller.WalletNewController;
import net.okdi.apiV3.service.SmallBellService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.MessageInfoService;
import net.okdi.apiV4.service.NumberQueryService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.apiV4.service.WrongPriceService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;
import net.okdi.httpClient.TaskHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@SuppressWarnings("all")
@Service
public class NumberQueryServiceImpl implements NumberQueryService{
	
	@Autowired
	private SmallBellService smallBellService;
	@Autowired
	private WrongPriceService wrongPriceService;
	@Autowired
	private SendPackageService sendPackageService;
	@Autowired
	private MessageInfoService messageInfoService;
	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ReceivePackageService packageService;
	private static Logger logger = Logger.getLogger(NumberQueryServiceImpl.class);
	@Autowired
	private WalletNewService walletNewService;//注入service
	@Autowired
	private AssignPackageService assignPackageService;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Override
	public Map homepage(Long memberId) {
		Map map=new HashMap();
//		map=this.redisService.get("memberIdHomepage", memberId+"", Map.class);
		if(PubMethod.isEmpty(map)){
		map=new HashMap();
		Long smallBell=this.smallBellService.queryCount(memberId);
		//Map<String,String> pickupMap = this.pickup(memberId);
		//Long orderNum = Long.valueOf(pickupMap.get("orderNum"));
		//Long parcelNum = Long.valueOf(pickupMap.get("parcelNum"));
		Long wrong=this.wrongPriceService.queryWrong(memberId);//普通单发送失败的
		//Long noSign=this.wrongPriceService.queryCount(memberId);
		//Long aliWrong=this.wrongPriceService.queryAliWrong(memberId);//阿里异常发送失败的
		//String result=this.sendPackageService.querySendCountAll(memberId+"");
		String notice=this.messageInfoService.queryMessageTime();	//查询最新公告时间
		//Long robCount = this.smallBellService.queryRobCount(memberId);//抢单列表数量
		String noticeRecordNumRes=this.noticeRecordNum(memberId); //查询通知记录数量
		Long noticeRecordNum=this.getNum(noticeRecordNumRes);
		
		logger.info("查询用户所属公司的网点 或者代理点名称---------------");
		String name=this.queryNet(memberId);//代收点名称或者网点名称
		
		JSONObject jsonObject = JSONObject.parseObject(name);	
		String namek = jsonObject.getJSONObject("data").getString("name");
		String url = jsonObject.getJSONObject("data").getString("url");
		/*
		 * @Value("${net.info.pic.url}")
	        private String pictrueReadUrl;
		 * Long netId = jsonObject.getJSONObject("data").getLong("netId");
		String tempPath=pictrueReadUrl; //图片路径
		String url = tempPath+netId.toString()+".png";*/
		logger.info("查询用户所属公司的网点 -------或者代理点名称:"+namek+" 图像 ："+url);
		//map.put("name",jsonObject.get("data"));
		map.put("name",namek); //网点名称
		map.put("url",url);   //url 路径
		/*Long toBeTakencount=0l;
		Long sendTaskcount=0l;
		if(!PubMethod.isEmpty(result)){
			JSONObject json=JSONObject.parseObject(result);
			if(json.getBooleanValue("success")){
				String data=json.getString("data");
				JSONObject jsons=JSONObject.parseObject(data);
				toBeTakencount=jsons.getLong("toBeTakencount");
				sendTaskcount=jsons.getLong("sendTaskcount");
				map.put("toBeTakencount", toBeTakencount);//派件--待提
				map.put("sendTaskcount", sendTaskcount);//派件--待派
			}else{
				map.put("toBeTakencount", toBeTakencount);
				map.put("sendTaskcount", sendTaskcount);
			}
		}else{
			map.put("toBeTakencount", toBeTakencount);
			map.put("sendTaskcount", sendTaskcount);
		}*/
		if(!PubMethod.isEmpty(notice)){
			JSONObject json=JSONObject.parseObject(notice);
			if(json.getBooleanValue("success")){
				//Long noticeTime=json.getLong("data");
				if (!PubMethod.isEmpty(json.getLong("data"))) {
					map.put("notice",1l);//最新的公告时间 没有公告返回0已读,1是未读
				}else {
					map.put("notice",0l);
				}
				//map.put("notice",noticeTime);//最新的公告时间 没有公告返回0已读,1是未读
			}else{
				map.put("notice",0l);
			}
		}else{
			map.put("notice",0l);
		}
		/**
		 * 查询通讯费余额
		 */
		logger.info("查询通讯费余额的入口");
		String accountId = walletNewService.getAccountId(memberId);
		logger.info("查询通讯费----当前的账户是："+accountId);
		HashMap<String,Object> balance = walletNewService.getBalance(accountId);
		logger.info("通讯费余额："+balance.get("teleAvailableBalance"));
		map.put("available_balance",balance.get("teleAvailableBalance"));//通讯费余额 (有)
		/**
		 * 查询所挣得外快
		 */
		//* 查询外快的入口
		logger.info("查询外快的入口");
		String extraMoney = this.openApiHttpClient.getExtraMoney(memberId);
		map.put("extra",Double.parseDouble(extraMoney));//外快 (有)
		logger.info("查询外快返回的结果："+Double.parseDouble(extraMoney));
		map.put("wrong",wrong);//通知异常--发送失败  (已有)
		map.put("noticeRecordNum",noticeRecordNum);//通知记录数量  (已有)
		/**
		 * 揽收包裹数量
		 */
//		logger.info("查询揽收包裹的数量");
//		Long queryTakePackageCount = packageService.queryTakePackageCount(memberId);
//		logger.info("揽收包裹返回的数量:"+queryTakePackageCount);
		map.put("lanShou","0");//揽收包裹数量 (有)
		/**
		 * 投递包裹数量
		 */
//		logger.info("查询投递包裹数量");
		//Long sendRecords = sendPackageService.querySendRecords(memberId);
		//logger.info("投递返回的数量:"+sendRecords);
		map.put("delivery","0");//投递包裹数量 (有)
		map.put("smallBell",smallBell);//铃铛数量 (已有)
		/**
		 * 派件异常数量
		 */
//		logger.info("查询派件异常数量");
//		
		Long sendWrong= assignPackageService.queryAssignException(memberId);
		logger.info("派件异常返回的数量:"+sendWrong);
		map.put("sendWrong",sendWrong);//派件异常 (有)
		
		//map.put("orderNum",orderNum);//收派员的话 是 取件订单的数量, 代收点的话是已取包裹的数量
		//map.put("parcelNum",parcelNum);//收派员的话是 已取包裹的数量, 代收点的话是交寄订单的数量
		//map.put("aliWrong",aliWrong);//问题件--阿里大于
		//map.put("noSign",noSign);//问题件--异常未签收
		//map.put("robCount",robCount);//抢单(取件提醒)
//		this.redisService.put("memberIdHomepage", memberId+"", map);
		/*map.put("smallBell",0);
		map.put("orderNum",0);
		map.put("parcelNum",0);
		map.put("wrong",0);
		map.put("noSign",0);
		map.put("robCount",0);
		map.put("noticeRecordNum",0);*/
		logger.info("查询首页数量的最后返回的**NumberQueryServiceImpl*apiV4*homepage******map="+map);
		return map;
		}
		return map;
	}
	//查询代理点名称  网点i
	private String queryNet(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");	
		return openApiHttpClient.doPassSendStr("expressRegister/queryNet", map);
	}
	@Override
	public Map<String,String> pickup(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getOpenApiUrl()+"/receivePackage/queryTakePackCount",map);
		System.out.println("查询首页数量返回的result:========================: "+result);
		JSONObject parseObject = JSONObject.parseObject(result);
		map=new HashMap<String, String>();
		map.put("orderNum", "0");//默认给个0
		map.put("parcelNum", "0");//默认给个0
		if(parseObject.getBooleanValue("success")){
			if(!PubMethod.isEmpty(parseObject.get("data"))){
				JSONObject jsonObject = parseObject.getJSONObject("data");
				String orderNum = jsonObject.getString("orderNum");
				String parcelNum = jsonObject.getString("parcelNum");
				map.put("orderNum", orderNum);
				map.put("parcelNum", parcelNum);
			}
		}
		return map;
	}

	private Long getNum(String result){
		Long num=0l;
		JSONObject json=JSONObject.parseObject(result);
		if(json.getBooleanValue("success")){
			if(!PubMethod.isEmpty(json.get("data"))){
				num=json.getLong("data");
			}
		}
		return num;
	}
	@Override
	public String noticeRecordNum(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/mongoSms/findCount",map);
		return result;
	}
	
	
	@Override
	public Map noticeRecordSuccessNum(Long memberId) {
		Map<String,String> resmap=new HashMap<String, String>();
		resmap.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/mongoSms/noticeRecordSuccessNum",resmap);
		Map<String, Long> map=new HashMap();
		Long smsNum=0l;
		Long phoneNum=0l;
		Long chatNum=0l;
		if(!PubMethod.isEmpty(result)){
			JSONObject json=JSONObject.parseObject(result);
			if(json.getBooleanValue("success")){
				String data=json.getString("data");
				JSONObject jsons=JSONObject.parseObject(data);
				smsNum=jsons.getLong("smsNum");
				phoneNum=jsons.getLong("phoneNum");
				chatNum=jsons.getLong("chatNum");
				map.put("smsNum", smsNum);
				map.put("phoneNum", phoneNum);
				map.put("chatNum", chatNum);
			}else{
				map.put("smsNum", smsNum);
				map.put("phoneNum", phoneNum);
				map.put("chatNum", chatNum);
			}
		}else{
			map.put("smsNum", smsNum);
			map.put("phoneNum", phoneNum);
			map.put("chatNum", chatNum);
		}
		map.put("smsNum",smsNum);
		map.put("phoneNum",phoneNum);
		map.put("chatNum",chatNum);
		return map;
	}
	@Override
	public Map<String,Object> newHomepage(Long memberId) {
		Map map=new HashMap();
//		map=this.redisService.get("memberIdHomepage", memberId+"", Map.class);
		if(PubMethod.isEmpty(map)){
		map=new HashMap();
		Long smallBell=this.smallBellService.queryCount(memberId);
		Long wrong=this.wrongPriceService.queryWrong(memberId);//通知异常发送失败的
		String notice=this.messageInfoService.queryMessageTime();	//查询最新公告时间
		String noticeRecordNumRes=this.noticeRecordNum(memberId); //查询通知记录数量
		Long noticeRecordNum=this.getNum(noticeRecordNumRes);
		logger.info("查询用户所属公司的网点 或者代理点名称---------------");
		String name=this.queryNet(memberId);//代收点名称或者网点名称
		
		JSONObject jsonObject = JSONObject.parseObject(name);	
		String namek = jsonObject.getJSONObject("data").getString("name");
		String url = jsonObject.getJSONObject("data").getString("url");
		map.put("name",namek); //网点名称
		map.put("url",url);   //url 路径
		if(!PubMethod.isEmpty(notice)){
			JSONObject json=JSONObject.parseObject(notice);
			if(json.getBooleanValue("success")){
				//Long noticeTime=json.getLong("data");
				if (!PubMethod.isEmpty(json.getLong("data"))) {
					map.put("notice",1l);//最新的公告时间 没有公告返回0已读,1是未读
				}else {
					map.put("notice",0l);
				}
				//map.put("notice",noticeTime);//最新的公告时间 没有公告返回0已读,1是未读
			}else{
				map.put("notice",0l);
			}
		}else{
			map.put("notice",0l);
		}
		/**
		 * 查询通讯费余额
		 */
		logger.info("查询通讯费余额的入口");
		String accountId = walletNewService.getAccountId(memberId);
		logger.info("查询通讯费----当前的账户是："+accountId);
		HashMap<String,Object> balance = walletNewService.getBalance(accountId);
		logger.info("通讯费余额："+balance.get("teleAvailableBalance"));
		map.put("available_balance",balance.get("teleAvailableBalance"));//通讯费余额 (有)
		/**
		 * 查询所挣得外快
		 */
		//* 查询外快的入口
		logger.info("查询外快的入口");
		String extraMoney = this.openApiHttpClient.getExtraMoney(memberId);
		map.put("extra",Double.parseDouble(extraMoney));//外快 (有)
		logger.info("查询外快返回的结果："+Double.parseDouble(extraMoney));
		map.put("wrong",wrong);//通知异常--发送失败  (已有)
		map.put("noticeRecordNum",noticeRecordNum);//通知记录数量  (已有)
		/**
		 * 揽收包裹数量
		 */
		map.put("lanShou",0);//揽收包裹数量 (有)
		map.put("smallBell",smallBell);//铃铛数量 (已有)
		/**
		 * 派件异常数量
		 */
		logger.info("查询派件异常数量");
		
		Long sendWrong= assignPackageService.queryAssignException(memberId);
		logger.info("派件异常返回的数量:"+sendWrong);
		map.put("sendWrong",sendWrong);//派件异常 (有)
		return map;
	}
		return map;
	}
}
