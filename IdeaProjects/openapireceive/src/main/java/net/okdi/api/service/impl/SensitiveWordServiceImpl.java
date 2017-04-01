package net.okdi.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.okdi.api.dao.SensitiveWordMapper;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.SensitiveWord;
import net.okdi.api.entity.SmsInterception;
import net.okdi.api.entity.SmsInterceptionLog;
import net.okdi.api.service.SensitiveWordService;
import net.okdi.api.service.SmsInterceptionLogService;
import net.okdi.api.service.SmsInterceptionService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;


@Service
public class SensitiveWordServiceImpl extends BaseServiceImpl<MemberInfo> implements SensitiveWordService{

	@Autowired
	private SensitiveWordMapper sensitiveWordMapper;
	@Autowired
	private SmsInterceptionService smsInterceptionService;	
	@Autowired
	private SmsInterceptionLogService smsInterceptionLogService;	
	@Autowired
	EhcacheService ehcacheService;
	@Autowired
	RedisService redisService;
	@Autowired
	private ConstPool constPool;
	
	Logger logger = Logger.getLogger(ShopBroadcastInfoServiceImpl.class);
	private static int stipulateTime=24*60*60*1000; //规定时间内
	private static int disableNum=3; //允许错误的总次数
	private static int twentyFour=24*60*60*1000; //禁用24小时
	private static int seventyTwo=3*24*60*60*1000; //禁用三天
	@Override
	public Map<String, Object> queryBlackList() {
		Map<String, Object> map = null; 
		map = this.ehcacheService.get("SensitiveWordCache", "blackList",Map.class);
		if (PubMethod.isEmpty(map)) {
			map=new HashMap<String, Object>();
			Set<String> set = new HashSet<String>();
			List<String> list = sensitiveWordMapper.selectByType("0");// 0代表只查黑名单
			for (String o : list) {
				set.add(o);
			}
			map.put("data", set);
			this.ehcacheService.put("SensitiveWordCache", "blackList", map);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryWhiteList() {
		Map<String, Object> map = null;
		map = this.ehcacheService.get("SensitiveWordCache", "whiteList",Map.class);
		if (PubMethod.isEmpty(map)) {
			map=new HashMap<String, Object>();
			Set<String> set = new HashSet<String>();
			List<String> list = sensitiveWordMapper.selectByType("1");// 1代表只查黑名单
			for (String o : list) {
				set.add(o);
			}
			map.put("data", set);
			this.ehcacheService.put("SensitiveWordCache", "whiteList", map);
		}
		return map;
	}

	@Override
	public Map<String, Object> insertSensitiveWord(String sensitiveWord,String type) {
		if(PubMethod.isEmpty(sensitiveWord)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.insertSensitiveWord.001","插入敏感字异常，sensitiveWord参数非空异常");			
		}
		if(PubMethod.isEmpty(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.insertSensitiveWord.002","插入敏感字类型异常，type参数非空异常");			
		}
		if(!"0".equals(type) && !"1".equals(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.insertSensitiveWord.003","插入敏感字类型异常，type参数只能为\"0\":黑名单或\"1\"白名单");			
		}
		Map<String, Object> map = new HashMap<String,Object>();
		//先判断是否重复添加
		Integer there=sensitiveWordMapper.selectBySensitiveWordAndType(sensitiveWord);
		if(there>0){
			map.put("success","false");
			map.put("message","字段已存在，请勿重新添加");
		}else{
			SensitiveWord sens=new SensitiveWord();
			sens.setId(IdWorker.getIdWorker().nextId());
			sens.setSensitiveWord(sensitiveWord);
			sens.setState("0");
			sens.setType(type);
			sensitiveWordMapper.insertSensitiveWord(sens);
			map.put("success","true");
			map.put("message","添加成功");
			if("0".equals(type)){
				this.ehcacheService.remove("SensitiveWordCache","blackList");				
			}
			if("1".equals(type)){
				this.ehcacheService.remove("SensitiveWordCache","whiteList");				
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteSensitiveWord(String sensitiveWord,String type) {
		if(PubMethod.isEmpty(sensitiveWord)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.deleteSensitiveWord.001","插入敏感字异常，sensitiveWord参数非空异常");			
		}
		if(PubMethod.isEmpty(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.deleteSensitiveWord.002","插入敏感字类型异常，type参数非空异常");			
		}
		if(!"0".equals(type) && !"1".equals(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.deleteSensitiveWord.003","插入敏感字类型异常，type参数只能为\"0\":黑名单或\"1\"白名单");			
		}
		Map<String, Object> map = new HashMap<String,Object>();
		int i=sensitiveWordMapper.deleteSensitiveWord(sensitiveWord,type);
		map.put("i",i);
		map.put("success","true");
		map.put("message","删除成功");
		if("0".equals(type)){
			this.ehcacheService.remove("SensitiveWordCache","blackList");				
		}
		if("1".equals(type)){
			this.ehcacheService.remove("SensitiveWordCache","whiteList");				
		}
		return map;
	}

	@Override
	public Map<String, Object> likeSensitiveWord(String sensitiveWord,String type) {
		if(PubMethod.isEmpty(sensitiveWord)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.likeSensitiveWord.001","插入敏感字异常，sensitiveWord参数非空异常");			
		}
		if(PubMethod.isEmpty(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.likeSensitiveWord.002","插入敏感字类型异常，type参数非空异常");			
		}
		if(!"0".equals(type) && !"1".equals(type)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.likeSensitiveWord.003","插入敏感字类型异常，type参数只能为\"0\":黑名单或\"1\"白名单");			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		list=sensitiveWordMapper.likeSensitiveWord(sensitiveWord,type);
		map.put("success","true");
		map.put("message","查询成功");
		//num 查询结果的数量
		if(list.size()>0){
			map.put("num",list.size());
			map.put("data",list);			
		}else{
			map.put("num",0);
		}
		return map;
	}
	
	@Override
	public String addWrongNumber(Long memberId,String phone,String sendContent) {
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.addWrongNumber.001","记录用户id异常，memeberId参数非空异常");			
		}
		if(PubMethod.isEmpty(phone)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.addWrongNumber.002","查询发送号码phone异常，phone参数非空异常");			
		}
		if(PubMethod.isEmpty(sendContent)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.addWrongNumber.003","查询发送内容sendContent异常，sendContent参数非空异常");			
		}
		//进到这里说明短信内容判定为有问题，那么先插入数据库一条记录记录一下
		SmsInterceptionLog smsIntLog=new SmsInterceptionLog();
		Long id=IdWorker.getIdWorker().nextId();
		smsIntLog.setId(id);
		smsIntLog.setMemberId(memberId);
		smsIntLog.setSendPhone(phone);
		smsIntLog.setSendContent(sendContent);
		Date today=new Date(); //当前时间
		smsIntLog.setCreateTime(today);
		this.smsInterceptionLogService.insert(smsIntLog);
		Map map=this.redisService.get("addWrongNumber",memberId.toString(),Map.class);
		if(PubMethod.isEmpty(map)){
			map=new HashMap();
			map.put("errorNum",1);
			map.put("errorTime",today);
			map.put("disableType",1);//正常
			this.redisService.put("addWrongNumber",memberId.toString(),map);
		}else{
			int errorNum=Integer.parseInt(map.get("errorNum").toString()); //获取上次的错误次数
			Long errorTime=(Long)map.get("errorTime");//获取上次发送短信出错的时间
			if(today.getTime()-errorTime>stipulateTime){
				errorNum=0;
			}
			map.put("errorNum",errorNum+1);
			if(errorNum+1==disableNum){
				String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(today);
				map.put("errorTime",today);
				map.put("disableType",2);//禁用24小时
				Map<String,String> maps=new HashMap<String,String>();
				maps.put("time",dateStr);
				maps.put("memberId",memberId.toString());
				String url=constPool.getCustomerUrl()+"/smsLogAction!insertSmsOperateLog.aspx";
//				String url = "http://192.168.53.63:8080/okdicustomer/smsLogAction!insertSmsOperateLog.aspx";
				String result=this.Post(url, maps);
				this.smsInterceptionService.updateErrorType(memberId,2);
			}
			this.redisService.put("addWrongNumber",memberId.toString(),map);
		}
		int count=this.smsInterceptionService.selectByPrimaryKey(memberId);//查询用户记录是否存在
		if(count==0){ 	//不存在就创建
			SmsInterception record=new SmsInterception();
			record.setId(IdWorker.getIdWorker().nextId()); //主键id
			record.setMemberId(memberId); //发送人id
			record.setSendPhone(phone); //发送人号码
			record.setWrongNumber(1); //累计错误次数，初始化为0
			record.setDisableType(1);    //禁用类型，默认是24小时
			record.setCreateTime(today); //创建时间
			record.setUpdateTime(today); //最后一次更新时间
			this.smsInterceptionService.insert(record);
		}else{
			this.smsInterceptionService.updateByPrimaryKey(memberId); //更新数据库的错误次数，再更新禁用时间
		}
		this.redisService.remove("smsInterception",memberId.toString());
		return "{\"success\":true}";
	}
	
	

	@Override
	public String queryWrongNumber(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.queryWrongNumber.001","查询用户id异常，memeberId参数非空异常");			
		}
		Map map=this.redisService.get("addWrongNumber",memberId.toString(),Map.class);
		if(!PubMethod.isEmpty(map)){
			Date today = new Date();
			int errorType=Integer.parseInt(map.get("disableType").toString()); //获取错误类型
			Long errorTime=(Long)map.get("errorTime");//获取上次发送短信出错的时间
			if(errorType==2){ 
				if(today.getTime()-errorTime>twentyFour){
					this.redisService.remove("addWrongNumber",memberId.toString());
					this.smsInterceptionService.updateErrorType(memberId, 1);
				}else{
					throw new ServiceException("openapi.SensitiveWordServiceImpl.queryWrongNumber.002","通知内容已经连续三次不符合发送要求， 免费短信功能被禁用24小时！");
				}
			}else if(errorType==3){
				if(today.getTime()-errorTime>seventyTwo){
					this.redisService.remove("addWrongNumber",memberId.toString());
					this.smsInterceptionService.updateErrorType(memberId, 1);
				}else{
					throw new ServiceException("openapi.SensitiveWordServiceImpl.queryWrongNumber.003","由于您近日多次违规操作，免费短信功能被禁用3天！");
				}
			}else if(errorType==4){
				throw new ServiceException("openapi.SensitiveWordServiceImpl.queryWrongNumber.004","由于您近日多次违规操作，免费短信功能被禁用");
			}
		}
		return "{\"success\":true}";
	}
	@Override
	public void removeWrongNumber(String memberId) {
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.SensitiveWordServiceImpl.removeWrongNumber.001","查询用户id异常，memeberId参数非空异常");			
		}
		this.redisService.remove("addWrongNumber",memberId);
		}

	@Override
	public BaseDao getBaseDao() {
		return null;
	}

	@Override
	public String parseResult(String info) {
		return null;
	}
}
