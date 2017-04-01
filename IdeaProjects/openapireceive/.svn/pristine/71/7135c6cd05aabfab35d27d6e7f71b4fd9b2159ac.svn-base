package net.okdi.apiV2.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.dao.TelephoneRewardMapper;
import net.okdi.apiV2.entity.NewUserRegisterActivity;
import net.okdi.apiV2.entity.RegGetRewardRelation;
import net.okdi.apiV2.entity.TeleActivityRules;
import net.okdi.apiV2.entity.TelephoneRelation;
import net.okdi.apiV2.entity.TelephoneReward;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.AbstractHttpClient;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class TelephoneRewardServiceImpl extends AbstractHttpClient implements TelephoneRewardService {
	public static final Log logger = LogFactory.getLog(QueryInvitedStatusAndStationServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	//财务系统的url
	@Value("${pay_url}")
	private String payUrl;
	//短信系统的url
	@Value("${sms_server}")
	private String SMServer;
	//成功
	private final static String  RESULT_SUCCESS = "001";
	//失败
	private final static String  RESULT_FAILE = "002";
	//代表当天内已经存在的活动参数
	private final static String  RESULT_EXIST = "003";
	
	@Autowired
	public TelephoneRewardMapper telephoneRewardMapper;
	/**
	 * 登录领奖励
	 */
	@Override
	public String loginReceiveRewardNum(String phone) {
		logger.info("每天登录领奖励======================= loginReceiveRewardNum==================");
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String actytime = dateFormat.format(date);
			Query query = new Query();
			Short actStatus = 1;//已经开始的活动(当天)
			query.addCriteria(Criteria.where("actStatus").is(actStatus));//进行中的活动
			//query.addCriteria(Criteria.where("dateTime").is(actytime));//活动当天的时间 yyyyMMdd
			//query.addCriteria(Criteria.where("actEndTime").gte(date));//活动结束时间大于当前时间
			//query.addCriteria(Criteria.where("actStartTime").lte(date));//活动开始时间小于当前时间
			TelephoneReward reward = mongoTemplate.findOne(query, TelephoneReward.class);//查询奖励活动
			if(PubMethod.isEmpty(reward)){
				return RESULT_FAILE;//不存在奖励活动
			}
			logger.info("每天登录领奖励======================= 查询奖励活动 ==================");
			long id = reward.getId();//活动编号
			query = new Query();
			query.addCriteria(Criteria.where("actId").is(id));
			TeleActivityRules activityRules = mongoTemplate.findOne(query, TeleActivityRules.class);
			Integer rewartNum = activityRules.getRewardNum();//奖励数量
			Double rewardPrice = activityRules.getRewardPrice();//奖励价格
			//查询当天是否已经领过(在关系表中查看)
			logger.info("每天登录领奖励===============查询奖励规则============奖励数量 rewartNum: "+rewartNum+" 奖励规则: "+rewardPrice);
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("tid").is(id));
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("dateTime").is(actytime));
			//query.addCriteria(Criteria.where("actStartTime").lte(actytime));//当前时间小于活动结束时间
			TelephoneRelation relation = mongoTemplate.findOne(query, TelephoneRelation.class);
			if (PubMethod.isEmpty(relation)) {
				logger.info("每天登录领奖励===============查询该人第一次登录插入一条============奖励数量: "+rewartNum );
				TelephoneRelation telephoneRelation = new TelephoneRelation();
				telephoneRelation.setId(IdWorker.getIdWorker().nextId());
				telephoneRelation.setTid(id);
				telephoneRelation.setPhone(phone);
				telephoneRelation.setRewardNum(rewartNum);
				telephoneRelation.setRewardPrice(rewardPrice);//奖励的价格
				Double totalPrice = rewardPrice * rewartNum;
				telephoneRelation.setRewardTotalPrice(totalPrice);//奖励的总金额
				telephoneRelation.setDateTime(actytime);
				telephoneRelation.setStatus((short)0);//0代表为领用
				mongoTemplate.insert(telephoneRelation);
			}
			return RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT_FAILE;
		}
	}

	/**
	 * 获取活动规则的详情(获取奖励数量,奖励价格)
	 */
	@Override
	public TelephoneRelation queryRewardNumDetail(String phone) {
		Query query = new Query();
		//query.addCriteria(Criteria.where("phone").is(phone));
		//根据当前时间查询今天的奖励数量
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String actytime = dateFormat.format(date);
		Short actStatus = 1;//已经开始的活动(当天)
		query.addCriteria(Criteria.where("actStatus").is(actStatus));//目前要在进行中的活动
		//query.addCriteria(Criteria.where("dateTime").is(actytime));
		TelephoneReward telephoneReward = mongoTemplate.findOne(query, TelephoneReward.class);
		TelephoneRelation relation = null;
		if(!PubMethod.isEmpty(telephoneReward)){
			long actId = telephoneReward.getId();//主键id
			query = new Query();
			query.addCriteria(Criteria.where("tid").is(actId));
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("dateTime").is(actytime));//查询每天是否有领奖励数量
			relation = mongoTemplate.findOne(query, TelephoneRelation.class);
			if(PubMethod.isEmpty(relation)){
				relation = new TelephoneRelation();
				relation.setId(0);
				relation.setTid(0l);
				relation.setPhone("0");
				relation.setRewardNum(0);
				relation.setRewardPrice(0d);
				relation.setRewardTotalPrice(0d);
			}
		}else{
			//没活动返回也是实体类,但都是0
			relation = new TelephoneRelation();
			relation.setId(0);
			relation.setTid(0l);
			relation.setPhone("0");
			relation.setRewardNum(0);
			relation.setRewardPrice(0d);
			relation.setRewardTotalPrice(0d);
		}
		return relation;
		
	}

	/**
	 * 减去使用奖励的数量
	 */
	@Override
	public String calculateMinusRewardNumber(String phone, String id, String num) {
		
		try {
			Integer nn = Integer.valueOf(num);
			Query query = new Query();
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("tid").is(Long.valueOf(id)));
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String actytime = dateFormat.format(date);
			query.addCriteria(Criteria.where("dateTime").is(actytime));//根据当天时间查询当天使用的奖励数量
			TelephoneRelation relation = mongoTemplate.findOne(query, TelephoneRelation.class);
			Double rewardTotalPrice = relation.getRewardTotalPrice();//奖励的总金额
			Double rewardPrice = relation.getRewardPrice();//奖励价格
			Integer rewardNum = relation.getRewardNum();//奖励数量
			Update update = new Update();
			update.set("rewardNum", rewardNum - nn);//剩余多少奖励的数量
			Double usePrice = nn * rewardPrice;//使用了多少奖励的金额
			Double remai = rewardTotalPrice - usePrice;//剩余多少奖励金额
			update.set("rewardTotalPrice", remai);//奖励总金额剩余多少
			mongoTemplate.updateFirst(query, update, TelephoneRelation.class);
			return RESULT_SUCCESS;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return RESULT_FAILE;
		}
	}

	//根据memberid查询 accountId
	public String getAccountId(String memberId) {
		String accountId = "";
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
		return accountId;
	}

	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//查询通讯账户可用金额,和冻结金额
	public Map<String,Object> getTelecomBook(String accountId){
		Map<String,Object> map = new HashMap<String,Object>();
		//String accountId = getAccountId(memberId);//调用财务返回accountId
		map.put("accountId", accountId);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//查询通信账户余额
		String url2 = payUrl+"ws/telecom/getTelecomBook";
		String result2 = this.Post(url2, map);
		Map<String,Object> obj2 = JSONObject.parseObject(result2);
		if("true".equals(String.valueOf(obj2.get("success")))) {
			Map<String,Object> banlanceMap2 = (Map<String, Object>) obj2.get("data");
			resultMap.put("telecomBalance", banlanceMap2.get("available_balance"));//可用金额
			//resultMap.put("freezeBalance", banlanceMap2.get("freeze_balance"));//冻结金额
		}
		logger.info("请求publicapi返回封装后的数据Map通信账户余额数据："+result2);
		return resultMap;
	}	
	
	//根据通讯金额计算可用多少条和免费多少条
	public Map<String,Object> getSMSOrPhoneNum(String accountId, String phone){
		
		//查询可用余额
		Map<String, Object> telecomBook = getTelecomBook(accountId);
		Double telecomBalance = Double.parseDouble(telecomBook.get("telecomBalance") +"");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//Map<String,Object> map = getTelecomBook(memberId);
		//1.使用可用金额计算还可以使用多少条
		//Double telecomBalance = Double.parseDouble(map.get("telecomBalance")+"");//telecomBalance
		Map<String, Object> salePriceMap = getSMSOrPhoneSalePrice();
		Double smsSalePrice = Double.parseDouble( salePriceMap.get("smsSalePrice")+"");//短信销售价
		Double phoneSalePrice = Double.parseDouble( salePriceMap.get("phoneSalePrice")+"");//电话销售价
		resultMap.put("telecomBalance", telecomBalance);//可用余额
		if(0 == smsSalePrice && 0 == phoneSalePrice){//判断,短信,电话销售价都是0,弹窗不显示
			resultMap.put("lenSms", "no");//no 标识为了弹窗不显示
			resultMap.put("lenPhone", "no");//no 标识为了弹窗不显示
		}else {
			//判断短信的销售价
			if(0 != smsSalePrice){//短信销售价不为0
				int lenSms = (int) (telecomBalance / smsSalePrice);//可用短信多少条
				resultMap.put("lenSms", lenSms+"");//可发短信多少条
			}else{//短信销售价为0
				resultMap.put("lenSms", "no");//no 标识为了弹窗不显示
			}
			//判断电话的销售价
			if(0 != phoneSalePrice){//短信销售价不为0
				int lenPhone = (int) (telecomBalance / phoneSalePrice);//可用电话多少个
				resultMap.put("lenPhone", lenPhone+"");//群呼通知多少条
			}else{//短信销售价为0
				resultMap.put("lenPhone", "no");//no 标识为了弹窗不显示
			}
		}
		//2.使用电话 查询出奖励数量
		Query query = new Query();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String actytime = dateFormat.format(date);
		Short actStatus = 1;//已经开始的活动(当天)
		query.addCriteria(Criteria.where("actStatus").is(actStatus));//目前要在进行中的活动
		TelephoneReward telephoneReward = mongoTemplate.findOne(query, TelephoneReward.class);
		TelephoneRelation relation = null;
		if(!PubMethod.isEmpty(telephoneReward)){
			//查询关系表中该用户的优惠数量
			long id = telephoneReward.getId();
			query = new Query();
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("tid").is(Long.valueOf(id)));
			query.addCriteria(Criteria.where("dateTime").is(actytime));
			relation = mongoTemplate.findOne(query, TelephoneRelation.class);
			if(!PubMethod.isEmpty(relation)){
				Integer rewardNum = relation.getRewardNum();//奖励数量
				if(0 == rewardNum){//没有免费的条数了
					resultMap.put("preferentialNum", "no");//优惠数量为0了,则弹窗不显示
				}else {
					resultMap.put("preferentialNum", rewardNum+"");//优惠数量
				}
			}else{
				resultMap.put("preferentialNum", "no");//优惠数量为0了,则弹窗不显示
			}
			
		}else{
			resultMap.put("preferentialNum", "no");//没有活动优惠数量为0
		}
/*		if(!PubMethod.isEmpty(telephoneReward)){
			//查询关系表中该用户的优惠数量
			long id = telephoneReward.getId();
			query = new Query();
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("tid").is(Long.valueOf(id)));
			query.addCriteria(Criteria.where("dateTime").is(actytime));
			relation = mongoTemplate.findOne(query, TelephoneRelation.class);
			if(!PubMethod.isEmpty(relation)){
				Integer rewardNum = relation.getRewardNum();//奖励数量
					if(0 == rewardNum){//没有免费的条数了
					resultMap.put("preferentialNum", "no");//优惠数量为0了,则弹窗不显示
				}else {
				resultMap.put("preferentialNum", rewardNum+"");//优惠数量
//				}
			}else{
				resultMap.put("preferentialNum", "0");//优惠数量为0了,则弹窗不显示
			}
			
		}else{
			resultMap.put("preferentialNum", "0");//没有活动优惠数量为0
		}
*/		Map map=new HashMap(); 
		map.put("type","1");
		String url = SMServer+"smsPrefixSuffix/query";
		String result=this.Post(url, map);
		Map<String,Object> obj = JSONObject.parseObject(result);
		if("true".equals(String.valueOf(obj.get("success")))) {
			Integer len = String.valueOf(obj.get("data")).length();
			resultMap.put("one",70-len);
			resultMap.put("two",134-len);
			resultMap.put("three",201-len);
			resultMap.put("contentNum",1);
		}else{
			Integer len=13;//默认一个数
			resultMap.put("one",70-len);
			resultMap.put("two",134-len);
			resultMap.put("three",201-len);
			resultMap.put("contentNum",1);
		}
		return resultMap;
	}
	
	//请求SMS短信服务器获取电话短信销售价
	public Map<String,Object> getSMSOrPhoneSalePrice(){
		//smsFeesManage/queryFeesManageByAccount?account=okdiceshi //助通短信销售价 返回的是json格式
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,String> map = new HashMap<String,String>();
		//map.put("noticeType", "1");
		//SMServer
		//查询通信账户余额
/*		String url2 = SMServer+"smsFeesManage/querySalePriceManageByNoticeType";
		String result2 = this.Post(url2, map);
		Map<String,Object> obj2 = JSONObject.parseObject(result2);
		if("true".equals(String.valueOf(obj2.get("success")))) {
			List<Map<String,Object>> list = (List<Map<String, Object>>) obj2.get("data");
			for(Map<String,Object> map1 : list){
				//System.out.println(">>>>>>>>>>>>>>>>>>>: "+map1.get("salePrice"));
				String noticeType = (String) map1.get("noticeType");
				if("1".endsWith(noticeType)){//短信
					resultMap.put("smsSalePrice", map1.get("salePrice"));//短信销售价
				}else if("2".endsWith(noticeType)){//电话
					resultMap.put("phoneSalePrice", map1.get("salePrice"));//电话销售价
				}
			}
			//resultMap.put("smsSalePrice", banlanceMap2.get("sale_price"));//短信销售价
		}*/
		String url2 = SMServer+"smsSalesPrice/query";
		String result2 = this.Post(url2, map);
		Map<String,Object> obj2 = JSONObject.parseObject(result2);
		if("true".equals(String.valueOf(obj2.get("success")))) {
			String data=obj2.get("data").toString();
			JSONObject jsons = JSONObject.parseObject(data);
			resultMap.put("smsSalePrice", jsons.get("sms"));// 短信销售价
			resultMap.put("phoneSalePrice", jsons.get("voice"));// 电话销售价
		}
		return resultMap;
	}

	//新用户注册领奖励, 
	//return 003:奖励活动不存在, 002:奖励已经领取过
	public String registerGetReward(String memberId, String accountId) {
		//0.查询新用户奖励活动
		Query query = new Query();
		Short activityStatus = 1;
		query.addCriteria(Criteria.where("activityStatus").is(activityStatus));
		NewUserRegisterActivity userRegisterActivity = mongoTemplate.findOne(query, NewUserRegisterActivity.class);
		if(PubMethod.isEmpty(userRegisterActivity)){
			return RESULT_EXIST;//新用户奖励活动不存在
		}
		Double rewardPrice = userRegisterActivity.getRewardPrice();//奖励金额
		Short activityType = userRegisterActivity.getActivityType();
		//领取之前判断一下是否已经领取过
		query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("activityType").is(activityType));
		List<RegGetRewardRelation> list = mongoTemplate.find(query, RegGetRewardRelation.class);
		if(PubMethod.isEmpty(list)){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateTime = dateFormat.format(new Date());
			//1.插入关系, memberId和奖励金额
			RegGetRewardRelation rewardRelation = new RegGetRewardRelation();
			rewardRelation.setId(IdWorker.getIdWorker().nextId());
			rewardRelation.setMemberId(memberId);
			rewardRelation.setRewardPrice(rewardPrice);
			rewardRelation.setCreateTime(new Date());
			rewardRelation.setActivityType(activityType);//活动类型, 为了以后添加新的活动用户注册有问题
			rewardRelation.setDateTime(dateTime);
			rewardRelation.setStatus((short)0);
			mongoTemplate.insert(rewardRelation);
			
			//2.调用财务,把奖励金额放到通讯账户下面
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("outAccountId", 100003);
			map.put("inAccountId", Long.valueOf(accountId));
			map.put("money", BigDecimal.valueOf(rewardPrice));
			map.put("type", (short)3);
			map.put("accountType", 100001);
			map.put("activityId", userRegisterActivity.getId());
			map.put("remark", "平台赠送--新手体验金");
			map.put("title", "平台赠送--新手体验金");
			map.put("platformId", Long.valueOf(100002));
			map.put("outerTradeType", "10019");//交易类型 10019 平台赠送-新手体验金
			String url = payUrl +"ws/trade/rebate";
			String result = this.Post(url, map);
			return result;
		}
		return RESULT_FAILE;//代表新注册用户已经领用过奖励金额
	}

	@Override
	public Map getStrategy(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	//查询该用户当天是否已经领取过奖励
	@Override
	public String queryIsGetReward(String phone) {
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String actytime = dateFormat.format(date);
		Query query = new Query();
		Short actStatus = 1;//已经开始的活动(当天)
		query.addCriteria(Criteria.where("actStatus").is(actStatus));//进行中的活动
		TelephoneReward reward = mongoTemplate.findOne(query, TelephoneReward.class);//查询奖励活动
		if(PubMethod.isEmpty(reward)){
			return RESULT_EXIST;//不存在奖励活动
		}
		long id = reward.getId();//活动编号
		//查询当天是否已经领过(在关系表中查看)
		query = null;
		query = new Query();
		query.addCriteria(Criteria.where("tid").is(id));
		query.addCriteria(Criteria.where("phone").is(phone));
		query.addCriteria(Criteria.where("dateTime").is(actytime));
		query.addCriteria(Criteria.where("status").is((short)1));
		//query.addCriteria(Criteria.where("actStartTime").lte(actytime));//当前时间小于活动结束时间
		TelephoneRelation relation = mongoTemplate.findOne(query, TelephoneRelation.class);
		if(!PubMethod.isEmpty(relation)){
			return RESULT_SUCCESS;//返回001 代表已经领取过
		}else{
			return RESULT_FAILE; //返回002 代表还未领取过
		}
	}

	@Override
	public String queryIsGetRegister(String memberId) {
		//0.查询新用户奖励活动
		try {
			Query query = new Query();
			Short activityStatus = 1;
			query.addCriteria(Criteria.where("activityStatus").is(activityStatus));
			NewUserRegisterActivity userRegisterActivity = mongoTemplate.findOne(query, NewUserRegisterActivity.class);
			if(PubMethod.isEmpty(userRegisterActivity)){
				return RESULT_EXIST;//新用户奖励活动不存在
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Short activityType = userRegisterActivity.getActivityType();
			Date activityStartTime = userRegisterActivity.getActivityStartTime();//活动开始时间
			Date activityEndTime = userRegisterActivity.getActivityEndTime();//活动结束时间
			//判断该memberId的注册时间 create_time
			String createTime = telephoneRewardMapper.queryCreateTimeByMemberId(memberId);
			Date creatTime = dateFormat.parse(createTime);
			if(creatTime.after(activityStartTime) && creatTime.before(activityEndTime)){
				//领取之前判断一下是否已经领取过
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				//String dateTime = dateFormat.format(new Date());
				query = new Query();
				query.addCriteria(Criteria.where("memberId").is(memberId));
				query.addCriteria(Criteria.where("activityType").is(activityType));
				query.addCriteria(Criteria.where("status").is((short)1));
				RegGetRewardRelation relation = mongoTemplate.findOne(query, RegGetRewardRelation.class);
				//2016-04-01
				if(!PubMethod.isEmpty(relation)){
					return RESULT_SUCCESS;//已经领取过
				}else{
					return RESULT_FAILE;//还未领取过
				}
			}else {
				return RESULT_SUCCESS;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RESULT_SUCCESS;
	}

	@Override
	public String queryPhoneByMemberId(String memberId) {
		
		String result = telephoneRewardMapper.queryPhoneByMemberId(memberId);
		return result;
	}

	/*@Override
	public String updateLoginAndRegStatus(String phone, String memberId) {
		//通知奖励活动修改为已领取状态 1
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String actytime = dateFormat.format(date);
			Query query = new Query();
			Short actStatus = 1;//已经开始的活动(当天)
			query.addCriteria(Criteria.where("actStatus").is(actStatus));//进行中的活动
			TelephoneReward reward = mongoTemplate.findOne(query, TelephoneReward.class);//查询奖励活动
			if(PubMethod.isEmpty(reward)){
				return RESULT_EXIST;//不存在奖励活动
			}
			long id = reward.getId();//活动编号
			//查询当天是否已经领过(在关系表中查看)
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("tid").is(id));
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("dateTime").is(actytime));
			query.addCriteria(Criteria.where("status").is((short)0));
			Update update = new Update();
			update.set("status", (short)1);
			mongoTemplate.updateFirst(query, update, TelephoneRelation.class);
			
			//新手体验金状态修改为已领取 状态为 1
			Query query1 = new Query();
			Short activityStatus = 1;
			query1.addCriteria(Criteria.where("activityStatus").is(activityStatus));
			NewUserRegisterActivity userRegisterActivity = mongoTemplate.findOne(query1, NewUserRegisterActivity.class);
			if(PubMethod.isEmpty(userRegisterActivity)){
				return RESULT_EXIST;//新用户奖励活动不存在
			}
			Short activityType = userRegisterActivity.getActivityType();
			//领取之前判断一下是否已经领取过
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			//String dateTime = dateFormat.format(new Date());
			query1 = new Query();
			query1.addCriteria(Criteria.where("memberId").is(memberId));
			query1.addCriteria(Criteria.where("activityType").is(activityType));
			query1.addCriteria(Criteria.where("status").is((short)0));
			update = new Update();
			update.set("status", (short)1);
			mongoTemplate.updateFirst(query1, update, RegGetRewardRelation.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESULT_FAILE;
		}
		return RESULT_SUCCESS;
	}
*/
	//修改登录领奖励的状态
	@Override
	public String updateLoginStatus(String phone) {
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String actytime = dateFormat.format(date);
			Query query = new Query();
			Short actStatus = 1;//已经开始的活动(当天)
			query.addCriteria(Criteria.where("actStatus").is(actStatus));//进行中的活动
			TelephoneReward reward = mongoTemplate.findOne(query, TelephoneReward.class);//查询奖励活动
			if(PubMethod.isEmpty(reward)){
				return RESULT_EXIST;//不存在奖励活动
			}
			long id = reward.getId();//活动编号
			//查询当天是否已经领过(在关系表中查看)
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("tid").is(id));
			query.addCriteria(Criteria.where("phone").is(phone));
			query.addCriteria(Criteria.where("dateTime").is(actytime));
			query.addCriteria(Criteria.where("status").is((short)0));
			Update update = new Update();
			update.set("status", (short)1);
			mongoTemplate.updateFirst(query, update, TelephoneRelation.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESULT_FAILE;
		}
		return RESULT_SUCCESS;
	}

	//修改注册领奖励的状态
	@Override
	public String updateRegisterStatus(String memberId) {

		//新手体验金状态修改为已领取 状态为 1
		try {
			Query query = new Query();
			Short activityStatus = 1;
			query.addCriteria(Criteria.where("activityStatus").is(activityStatus));
			NewUserRegisterActivity userRegisterActivity = mongoTemplate.findOne(query, NewUserRegisterActivity.class);
			if(PubMethod.isEmpty(userRegisterActivity)){
				return RESULT_EXIST;//新用户奖励活动不存在
			}
			Short activityType = userRegisterActivity.getActivityType();
			//领取之前判断一下是否已经领取过
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			//String dateTime = dateFormat.format(new Date());
			query = new Query();
			query.addCriteria(Criteria.where("memberId").is(memberId));
			query.addCriteria(Criteria.where("activityType").is(activityType));
			query.addCriteria(Criteria.where("status").is((short)0));
			Update update = new Update();
			update.set("status", (short)1);
			mongoTemplate.updateFirst(query, update, RegGetRewardRelation.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESULT_FAILE;
		}
		return RESULT_SUCCESS;
	}

	/*@Override
	public Map getStrategy(String memberId) {
		// TODO Auto-generated method stub
		Map map = telephoneRewardMapper.getStrategy(memberId);
		return map;
	}*/
	
	
}
