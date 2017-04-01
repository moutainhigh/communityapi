package net.okdi.apiV2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;

@Service
public class SmsSendServiceImpl implements SmsSendService{
	
	public static final Log logger = LogFactory.getLog(SmsSendServiceImpl.class);
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private Map<String ,String> map = new HashMap<String,String>();

	private Map<String ,Object> maps = new HashMap<String,Object>();
	
	private static Integer ONEDAY = 1500; //每人每天最多可以发多少短信

	private static Integer ONEHOUR = 500; //每人每小时最多可以发多少短信

	private static Integer NOTPASS = 10; //每人每小时最多可以发多少短信
	
//	private static Integer MAX_SEND_MEMBER_COUNT = 1000; //每人每天可以给多少个人发短信
	
	@Override
	public Map memberSendNumQueryOneDay(Long memberId,Integer num){
		
//		GroupBy groupBy = GroupBy.key("receiverPhone").initialDocument("{count:0}").reduceFunction("function(doc, result){result.count+=1}");
//		Criteria criteria = Criteria.where("createTime").gte(getStartDate()).lte(getEndDate()).and("memberId").is(memberId);
//		GroupByResults<Map> result = mongoTemplate.group(criteria,"sms_log", groupBy, Map.class);
//		BasicDBList list = (BasicDBList) result.getRawResults().get("retval");
//		int count = PubMethod.isEmpty(list)?0:list.size();
//		if(count>=MAX_SEND_MEMBER_COUNT){
//			maps=new HashMap<String, Object>();
//			maps.put("success",false);
//			maps.put("cause", "每日最多给"+MAX_SEND_MEMBER_COUNT+"个用户发通知，您已超限，请明日再发送");
//			return maps;
//		}
		
		Date date=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd"); //时间定义到天就行
		String today=sim.format(date);		//当前是哪一天
		map=this.redisService.get("memberSendNumQueryOne",memberId+"", Map.class); //先从缓存取数据
		if(PubMethod.isEmpty(map)){		//没有缓存,添加
			logger.info("用户："+memberId+",群发通知每天上限"+this.ONEDAY+"条,客户今日已发"+0+"条,客户本次提交"+num+"条!");
			map = new HashMap<String,String>();
			map.put("time",today);
			map.put("num",num+"");  //因为短信一次的发送数量是有限制的，所以我们不用担心这个num大于我的的预设值，直接存就好了
			this.redisService.put("memberSendNumQueryOne", memberId+"", map);	
			maps=new HashMap<String, Object>();
			maps.put("success",true);
			return maps;
		}else{  //有缓存，取数据
			String lastOneDate=map.get("time");
			Integer lastOneNum=Integer.valueOf(map.get("num"));
			logger.info("用户："+memberId+",群发通知每天上限"+this.ONEDAY+"条,客户今日已发"+lastOneNum+"条,客户本次提交"+num+"条!");
			if(lastOneDate.equals(today)){//时间是今天
				if((num+lastOneNum)>this.ONEDAY){ //今天发短信数超过预设值
					logger.info("用户："+memberId+",群发通知每天上限"+this.ONEDAY+"条,客户今日已发"+lastOneNum+"条,客户本次提交"+num+"条,超出上限!");
					maps=new HashMap<String, Object>();
					maps.put("success",false);
					maps.put("cause", "每日群发通知数量最多为"+this.ONEDAY+"条，您今日已达上限，请明日继续发送");
					return maps;
				}else{//今天发短信数没有超过预设值
					map.put("num",num+lastOneNum+"");
					this.redisService.put("memberSendNumQueryOne",memberId+"", map);
					maps=new HashMap<String, Object>();
					maps.put("success",true);
					return maps;
				}
			}else{// 时间不是今天
				map.put("time", today);
				map.put("num",num+"");
				this.redisService.put("memberSendNumQueryOne", memberId+"", map);
				maps=new HashMap<String, Object>();
				maps.put("success",true);
				return maps;
			}
		}
	}
	
	@Override
	public Map memberSendNumQueryOneHour(Long memberId,Integer num){
		Date date=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH"); //时间定义到小时就行
		String now=sim.format(date);		//当前是哪个小时
		map=this.redisService.get("memberSendNumQueryOneHOUR",memberId+"", Map.class); //先从缓存取数据
		if(PubMethod.isEmpty(map)){		//没有缓存,添加
			logger.info("用户："+memberId+",群发通知每小时上限"+this.ONEHOUR+"条,客户今日已发"+0+"条,客户本次提交"+num+"条!");
			map = new HashMap<String,String>();
			map.put("time",now);
			map.put("num",num+"");  //因为短信一次的发送数量是有限制的，所以我们不用担心这个num大于我的的预设值，直接存就好了
			this.redisService.put("memberSendNumQueryOneHOUR", memberId+"", map);	
			maps=new HashMap<String, Object>();
			maps.put("success",true);
			return maps;
		}else{  //有缓存，取数据
			String lastOneDate=map.get("time");
			Integer lastOneNum=Integer.valueOf(map.get("num"));
			logger.info("用户："+memberId+",群发通知每天上限"+this.ONEHOUR+"条,客户今日已发"+lastOneNum+"条,客户本次提交"+num+"条!");
			if(lastOneDate.equals(now)){//时间是同一个小时
				if((num+lastOneNum)>this.ONEHOUR){ //当前小时发短信数超过预设值
					logger.info("用户："+memberId+",群发通知每小时上限"+this.ONEHOUR+"条,客户当前小时已发"+lastOneNum+"条,客户本次提交"+num+"条,超出上限!");
					maps=new HashMap<String, Object>();
					maps.put("success",false);
					maps.put("cause", "群发通知，1小时内发送数量不可超过"+this.ONEHOUR+"条！");
					return maps;
				}else{//当前小时发短信数没有超过预设值
					map.put("num",num+lastOneNum+"");
					this.redisService.put("memberSendNumQueryOneHOUR",memberId+"", map);
					maps=new HashMap<String, Object>();
					maps.put("success",true);
					return maps;
				}
			}else{// 时间不是本小时
				map.put("time", now);
				map.put("num",num+"");
				this.redisService.put("memberSendNumQueryOneHOUR", memberId+"", map);
				maps=new HashMap<String, Object>();
				maps.put("success",true);
				return maps;
			}
		}
	}
	
	
	public static Date getStartDate() {
		  Calendar c1 = new GregorianCalendar();
		    c1.set(Calendar.HOUR_OF_DAY, 0);
		    c1.set(Calendar.MINUTE, 0);
		    c1.set(Calendar.SECOND, 0);
		    System.out.println(c1.getTime());
		return c1.getTime();
	}
	public static Date getEndDate() {
	    Calendar c2 = new GregorianCalendar();
	    c2.set(Calendar.HOUR_OF_DAY, 23);
	    c2.set(Calendar.MINUTE, 59);
	    c2.set(Calendar.SECOND, 59);
	    System.out.println(c2.getTime());
		return c2.getTime();
	}
	
	@Override
	public Map identityNotPass(Long memberId,Integer num){
		
//		GroupBy groupBy = GroupBy.key("receiverPhone").initialDocument("{count:0}").reduceFunction("function(doc, result){result.count+=1}");
//		Criteria criteria = Criteria.where("createTime").gte(getStartDate()).lte(getEndDate()).and("memberId").is(memberId);
//		GroupByResults<Map> result = mongoTemplate.group(criteria,"sms_log", groupBy, Map.class);
//		BasicDBList list = (BasicDBList) result.getRawResults().get("retval");
//		int count = PubMethod.isEmpty(list)?0:list.size();
//		if(count>=MAX_SEND_MEMBER_COUNT){
//			maps=new HashMap<String, Object>();
//			maps.put("success",false);
//			maps.put("cause", "每日最多给"+MAX_SEND_MEMBER_COUNT+"个用户发通知，您已超限，请明日再发送");
//			return maps;	
//		}
		
		Date date=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd"); //时间定义到天就行
		String today=sim.format(date);		//当前是哪一天
		map=this.redisService.get("memberSendNumQueryOne",memberId+"", Map.class); //先从缓存取数据
		if(PubMethod.isEmpty(map)){		//没有缓存,添加
			logger.info("用户："+memberId+",群发通知每天上限"+this.NOTPASS+"条,客户今日已发"+0+"条,客户本次提交"+num+"条!");
			map = new HashMap<String,String>();
			map.put("time",today);
			map.put("num",num+"");  //因为短信一次的发送数量是有限制的，所以我们不用担心这个num大于我的的预设值，直接存就好了
			this.redisService.put("memberSendNumQueryOne", memberId+"", map);	
			maps=new HashMap<String, Object>();
			maps.put("success",true);
			return maps;
		}else{  //有缓存，取数据
			String lastOneDate=map.get("time");
			Integer lastOneNum=Integer.valueOf(map.get("num"));
			logger.info("用户："+memberId+",群发通知每天上限"+this.NOTPASS+"条,客户今日已发"+lastOneNum+"条,客户本次提交"+num+"条!");
			if(lastOneDate.equals(today)){//时间是今天
				if((num+lastOneNum)>this.NOTPASS){ //今天发短信数超过预设值
					logger.info("用户："+memberId+",群发通知每天上限"+this.NOTPASS+"条,客户今日已发"+lastOneNum+"条,客户本次提交"+num+"条,超出上限!");
					maps=new HashMap<String, Object>();
					maps.put("success",false);
					maps.put("cause", "每日群发通知数量最多为"+this.NOTPASS+"条，您今日已达上限，请明日继续发送");
					return maps;
				}else{//今天发短信数没有超过预设值
					map.put("num",num+lastOneNum+"");
					this.redisService.put("memberSendNumQueryOne",memberId+"", map);
					maps=new HashMap<String, Object>();
					maps.put("success",true);
					return maps;
				}
			}else{// 时间不是今天
				map.put("time", today);
				map.put("num",num+"");
				this.redisService.put("memberSendNumQueryOne", memberId+"", map);
				maps=new HashMap<String, Object>();
				maps.put("success",true);
				return maps;
			}
		}
	}
}
