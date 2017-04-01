package net.okdi.api.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.api.dao.MobMemberLoginMapper;
import net.okdi.api.entity.MobMemberLogin;
import net.okdi.api.service.MobMemberLoginService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.ShopBroadcastInfoService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.Constant;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;

@Service("mobMemberLoginService")
public class MobMemberLoginServiceImpl extends BaseServiceImpl<MobMemberLogin> implements MobMemberLoginService {

	@Autowired
	private MobMemberLoginMapper mobMemberLoginMapper;

	@Autowired
	private EhcacheService ehcacheService;
	
	@Autowired
	NoticeHttpClient noticeHttpClient;
	
	@Autowired
	ShopBroadcastInfoService shopBroadcastInfoService;
	
	@Autowired
	SendNoticeService sendNoticeService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	public BaseDao getBaseDao() {
		return mobMemberLoginMapper;
	}

	@Override
	public List<MobMemberLogin> findByMobMemberLogin(MobMemberLogin mobMemberLogin) {
		String json = ehcacheService.get("mobPhoneCache", "login_ext_"+mobMemberLogin.getChannelNo()+'_'+mobMemberLogin.getChannelId(), String.class);
		List<MobMemberLogin> listArr  = null;
		if(!PubMethod.isEmpty(json)&&!"[]".equals(json)){
			listArr= JSON.parseArray(json,MobMemberLogin.class);
			return listArr;
		}
		listArr = mobMemberLoginMapper.findList(mobMemberLogin);
		ehcacheService.put("mobPhoneCache", "login_ext_"+mobMemberLogin.getChannelNo()+'_'+mobMemberLogin.getChannelId(), listArr);
		return listArr;
	}
	
	/**
	 * 更新用户登录信息
	 * @Method: updateMobMemberLogin 
	 * @param mobMemberLogin 
	 */
	@Override
	public void updateMobMemberLogin(MobMemberLogin mobMemberLogin) {
		ehcacheService.remove("mobPhoneCache","login_ext_"+mobMemberLogin.getChannelNo()+'_'+mobMemberLogin.getChannelId());
		mobMemberLoginMapper.update(mobMemberLogin);

	}

	/**
	 * 更新登录信息
	 * @Method: insertMobMemberLogin 
	 * @param mobMemberLogin 
	 */
	@Override
	public void insertMobMemberLogin(MobMemberLogin mobMemberLogin) {
		ehcacheService.remove("mobPhoneCache","login_ext_"+mobMemberLogin.getChannelNo()+'_'+mobMemberLogin.getChannelId());
		mobMemberLoginMapper.insert(mobMemberLogin);
	}

	/**
	 * 删除用户登录信息
	 * @Method: deleteMobMemberLogin 
	 * @param mobMemberLogin 
	 */
	@Override
	public void deleteMobMemberLogin(MobMemberLogin mobMemberLogin) {
		
		MobMemberLogin lastLogin = mobMemberLoginMapper.getLastLogin(mobMemberLogin);
		if (lastLogin != null) {
			String deviceToken = lastLogin.getDeviceToken();
			String deviceType = lastLogin.getDeviceType();
			String version = lastLogin.getVersion();
			String address = lastLogin.getAddress();
			saveOrUpdateMobMemberLogin(mobMemberLogin.getChannelNo(), 0L, deviceType, deviceToken, version, address);
		}
		
		//EhcacheUtil.getInstance().remove("phoneCache","'login_ext_'+#mobMemberLogin.channelNo+'_'+#mobMemberLogin.channelId");
		ehcacheService.remove("mobPhoneCache","login_ext_"+mobMemberLogin.getChannelNo()+'_'+mobMemberLogin.getChannelId());
		//System.out.println("删除缓存的memberId:"+mobMemberLogin.getChannelId());
		mobMemberLoginMapper.delete(mobMemberLogin);
		shopBroadcastInfoService.removeCurrentMemberCache(mobMemberLogin.getChannelId());
	}

	/**登录,更新或者删除用户信息
	 * @Method: saveOrUpdateMobMemberLogin 
	 * @param channelNo		应用名字
	 * @param memberId		个人memberId
	 * @param deviceType	设备类别
	 * @param deviceToken	设备token(其实是百度传回的token)
	 * @param version		版本号
	 * @param address 		地址
	 */
	@Override
	public void saveOrUpdateMobMemberLogin(String channelNo, Long memberId, String deviceType, String deviceToken, String version,
			String address) {
		
		MobMemberLogin mobFind = new MobMemberLogin();
		mobFind.setChannelNo(channelNo);
		mobFind.setChannelId(memberId);
		mobFind.setDeviceToken(deviceToken);
		mobFind.setPushType("ext");
		
		MobMemberLogin mobReplace = new MobMemberLogin();
		mobReplace.setId(IdWorker.getIdWorker().nextId());
		mobReplace.setChannelNo(channelNo);
		mobReplace.setChannelId(memberId);
		mobReplace.setDeviceType(deviceType);
		mobReplace.setDeviceToken(deviceToken);
		mobReplace.setVersion(version);
		mobReplace.setConnTime(System.currentTimeMillis());//需求改变了，只允许一台设备登录，所以这个connTime和ModitifyTime都相同，这个字段在原表中没有什么有意义的数据，注释掉
		mobReplace.setAddress(address);
		//mobReplace.setClientId(clientId);这个字段在原表中没有什么有意义的数据，注释掉
		//mobReplace.setReceiveNoticeFlag(receiveNoticeFlag);这个字段在原表中没有什么有意义的数据，注释掉
		mobReplace.setPushType("ext");
		//mobMemberLoginService.saveOrUpdateMobMemberLogin(mobFind,mobReplace);
		
		if (memberId != 0) {
			MobMemberLogin mml = new MobMemberLogin();
			mml.setChannelId(0L);
			mml.setChannelNo(channelNo);
			mml.setDeviceToken(deviceToken);
			mml.setPushType("ext");
			logger.info("memberId不为0, 删除是0的记录::" + deviceToken);
			mobMemberLoginMapper.delete(mml);
		}
		
		ehcacheService.remove("mobPhoneCache","login_ext_"+mobFind.getChannelNo()+'_'+mobFind.getChannelId());
		List<MobMemberLogin> lm = mobMemberLoginMapper.findList(mobFind);
		if (lm == null || lm.size() == 0) {
			mobReplace.setCreateTime(new Date());
			mobReplace.setModifyTime(new Date());
			mobMemberLoginMapper.insert(mobReplace);
		} else {
			mobReplace.setModifyTime(new Date());
			mobMemberLoginMapper.update(mobReplace);
		}
	}

	@SuppressWarnings("unused")
	private void sort(List<MobMemberLogin> lm) {
		Collections.sort(lm, new Comparator<MobMemberLogin>() {
			@Override
			public int compare(MobMemberLogin o1, MobMemberLogin o2) {
				Date mt1 = o1.getModifyTime() == null ? new Date() : o1.getModifyTime();
				Date mt2 = o2.getModifyTime() == null ? new Date() : o2.getModifyTime();
				return mt2.compareTo(mt1);
			}
		});
	}

	/**
	 * 是否该发短信（原来的意思是：验证是否该台手机登录）
	 * @Method: isOnlineMember 
	 * @param channelNo		应用类别
	 * @param memberId		个人用户
	 * @return true不发短信(在线) false发短信(不在线)
	 * @see net.okdi.api.service.MobMemberLoginService#isOnlineMember(java.lang.String, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public String isOnlineMember(String channelNo, Long memberId) {
		if(PubMethod.isEmpty(memberId)||PubMethod.isEmpty(channelNo)){
			return "channelNo或memberId不能为空";
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelNo(channelNo);
		mobMemberLogin.setChannelId(memberId);
		logger.debug("校验手机是否在线的传入参数：	channelNo:"+channelNo +"	memberId:"+memberId);
		List<MobMemberLogin> list = findByMobMemberLogin(mobMemberLogin);
		logger.debug("得到手机是否在线的结果：	list大小:"+list.size() );
		
		if(!PubMethod.isEmpty(list)){//登录状态
			MobMemberLogin mobLogin  = list.get(0);
			logger.debug("取第一条的memeberId：	手机的设备类型:"+mobLogin.getDeviceType() + "  手机的deviceToken"+mobLogin.getDeviceToken() );
			if ("android".equals(mobLogin.getDeviceType())) {
				Boolean socketPush = couldSocketPush(mobLogin.getChannelNo(), mobLogin.getChannelId());
				if (socketPush) {
					logger.debug("查询的推送标记："+socketPush +"  返回值：Constant.YESPUSH_NOSMS_ANDRIOD");
					return Constant.YESPUSH_NOSMS_ANDRIOD;
				} else {
					logger.debug("查询的推送标记："+socketPush +"  返回值：Constant.NOPUSH_YESSMS");
					return Constant.NOPUSH_YESSMS;
				}
			}else {
				logger.debug("查询的推送标记：  返回值：Constant.YESPUSH_NOSMS_APPLE");
				return Constant.YESPUSH_NOSMS_APPLE;
			}
		}
		return Constant.NOPUSH_NOSMS;
	}

	/**
	 * 该方法为最新增加的判断是否在线的功能，主要描述：接单王如果在线，直接判断socket是否在线
	 * 如果是个人端，判断是否是iphone，如果是，用三方推送（可推送）
	 * 如果是android,判断socket是否可以推送，如果可以，返回true
	 * 如果不可以推送，则发短信
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-28 下午2:58:54</dd>
	 * @param mobLogin
	 * @return
	 * @since v1.0
	 */
//	public boolean getMemberPush(MobMemberLogin mobLogin) {
//		if (mobLogin.getDeviceType().equals("android")) {
//			if (couldSocketPush(mobLogin.getChannelNo(), mobLogin.getChannelId())) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		return true;
//	}
//	
	
	public static void main(String[] args){
		//boolean b = couldSocketPush("0",1L);
		//System.out.println(b);
	}
	
	
	public Boolean couldSocketPush(String channelNo,Long memberId){
		String result = noticeHttpClient.valSocketOnline(channelNo,String.valueOf(memberId));
		//String result = "{\"online\":true,\"success\":true}";
		try{
			//{"online":true,"success":true}
			JSONObject jso = JSON.parseObject(result);
			return new Boolean(jso.get("online").toString());
		}catch(Exception e){
			return false;
		}
	}
	
	/** 该方法暂时弃用，以后看下是否需要再加上
	 * @Method: valOnePhoneLogin 
	 * @Description: 验证是否手机登录，验证逻辑：必须能找到相应的数据才返回true
	 * @param mobMemberLogin
	 * @return true/false
	 * @see net.okdi.push.service.MobMemberLoginService#valOnePhoneLogin(net.okdi.push.entity.MobMemberLogin) 
	 * @since jdk1.6
	*/
	//@Cacheable(value = "mobPhoneCache", key = "'login_ext_'+#channelNo+'_'+#memberId")
	@Override
	public boolean valOnePhoneLogin(String channelNo, Long memberId, String deviceToken) {
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelNo(channelNo);
		mobMemberLogin.setChannelId(memberId);
		mobMemberLogin.setDeviceToken(deviceToken);
		//List<MobMemberLogin> list = mobMemberLoginMapper.findList(mobMemberLogin);
		List<MobMemberLogin> list = findByMobMemberLogin(mobMemberLogin);
		if(list!=null&&list.size()>0)
			return true;
		return false;
	}

	/**
	 * 三方推送
	 * @Method: pushExt 
	 * @param channelNo	应用类型 	01接单王  02个人端
	 * @param memberId	个人memberId
	 * @param type		消息应用类型
	 * @param title		消息标题
	 * @param content	消息内容
	 * @param extraParam 	需要传递的额外的参数
	 * @param msgType "message":消息,"notice":通知   (目前为了兼容以前的，定义不传默认为消息) 
	 * @see net.okdi.api.service.MobMemberLoginService#pushExt(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public void pushExt(String pushType,String channelNo, String memberId, String type, String title, String content, String extraParam,String msgType) {
		if(!memberId.matches("[\\d,]+")){
			return;
		}
		logger.debug("进入pushExt方法，开始推送");
		if(Constant.YESPUSH_NOSMS_ANDRIOD.equals(pushType)){
			logger.debug("开始调用socket推送，"+"pushType条件为 Constant.YESPUSH_NOSMS_ANDRIOD："+Constant.YESPUSH_NOSMS_ANDRIOD);
			noticeHttpClient.pushSocketPC(channelNo,memberId, type, title, "", content, extraParam, 0);
		}
		if("fourHourPush".equals(pushType)){
			logger.debug("四小时推送：fourHourPush 开始调用socket推送");
			noticeHttpClient.pushSocketPC(channelNo,memberId, type, title, "", content, extraParam, 0);
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelNo(channelNo);
		String[] members = memberId.split(",");
		for(String member:members){
			if(member.matches("\\d+")){
				mobMemberLogin.setChannelId(Long.parseLong(memberId));
				List<MobMemberLogin> list = mobMemberLoginMapper.findList(mobMemberLogin);
				if(list==null||list.size()==0)
					return;
				for(MobMemberLogin myMember:list){
					logger.debug("三方推送："+myMember.getDeviceType()+"  :"+myMember.getDeviceToken());
					noticeHttpClient.pushExtMsg(myMember.getChannelNo(),myMember.getDeviceType(), myMember.getDeviceToken(), type, title, content,
							extraParam,msgType);
				}
			}
		}
	}
	/**
	 * 消息推送--专门用于推广 
	 */
	public void pushExtForSendTS(String pushType,String channelNo, String memberId, String type, String title, String content, String extraParam,String msgType,String deviceType,String deviceToken) {
		if(!memberId.matches("[\\d,]+")){
			return;
		}
		logger.debug("进入pushExt方法，开始推送");
		if(Constant.YESPUSH_NOSMS_ANDRIOD.equals(pushType)){
			logger.debug("开始调用socket推送，"+"pushType条件为 Constant.YESPUSH_NOSMS_ANDRIOD："+Constant.YESPUSH_NOSMS_ANDRIOD);
			
			noticeHttpClient.pushSocketPC(channelNo,memberId, type, title, "", content, extraParam, 0);
		}
		else{
			logger.debug("开始调用苹果推送，"+"设备token："+deviceToken);
			noticeHttpClient.pushExtMsg(channelNo,deviceType, deviceToken, type, title, content,
					extraParam,msgType);
		}
					
			
		
	
	}

	/**
	 * @Method: parseResult 
	 * @Description: TODO
	 * @param info
	 * @return 
	 * @see net.okdi.core.sms.AbstractHttpClient#parseResult(java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String parseResult(String info) {
		return info;
	}

	public void initBadge(String deviceToken){
		noticeHttpClient.initBadge(deviceToken);
	}

	/**
	 * 判断配送员是否在线（原来的意思是：验证是否该台手机登录）
	 * @Method: isOnlineMember 
	 * @param channelNo		应用类别
	 * @param memberId		个人用户
	 */
	@Override
	public String isDeliveryOnlineMember(String channelNo, Long memberId) {
		if(PubMethod.isEmpty(memberId)||PubMethod.isEmpty(channelNo)){
			return "channelNo或memberId不能为空";
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelNo(channelNo);
		mobMemberLogin.setChannelId(memberId);
		logger.debug("校验手机是否在线的传入参数：	channelNo:"+channelNo +"	memberId:"+memberId);
		List<MobMemberLogin> list = findByMobMemberLogin(mobMemberLogin);
		logger.debug("得到手机是否在线的结果：	list大小:"+list.size() );
		
		if(!PubMethod.isEmpty(list)){//登录状态
			if(list.size()>1){
				logger.error("根据channelNo,memberId : "+channelNo +" " + memberId+"\t竟然取出多条");
			}
			MobMemberLogin mobLogin  = list.get(0);
			logger.debug("取第一条的memeberId：	手机的设备类型:"+mobLogin.getDeviceType() + "  手机的deviceToken"+mobLogin.getDeviceToken() );
			if ("android".equals(mobLogin.getDeviceType())) {
				Boolean socketPush = couldSocketPush(mobLogin.getChannelNo(), mobLogin.getChannelId());
				if (socketPush) {
					logger.debug("查询的推送标记："+socketPush +"  返回值：Constant.DELIVERY_ANDRIOD_LOG_ONLINE");
					return Constant.DELIVERY_ANDRIOD_LOG_ONLINE;
				} else {
					logger.debug("查询的推送标记："+socketPush +"  返回值：Constant.DELIVERY_ANDRIOD_ONLYLOG");
					return Constant.DELIVERY_ANDRIOD_ONLYLOG;
				}
			}else {
				logger.debug("查询的推送标记：  返回值：Constant.DELIVERY_IOS_LOG");
				return Constant.DELIVERY_IOS_LOG;
			}
		}
		return Constant.DELIVERY_NO_LOG;
	}

	/**
	 * 	客户推送
	 */
	@Override
	public void customerReply(Long memberId,String mob) {
		// TODO Auto-generated method stub
		sendNoticeService.customerReply(memberId,mob);
	}

	/**
	 * 	取件推送
	 */
	@Override
	public void taskPush(Long memberId, String mob) {
		// TODO Auto-generated method stub
		sendNoticeService.taskPush(memberId, mob);
	}
	
	@Override
	public void robPush(Long memberId, String mob, String extMsg) {
		// TODO Auto-generated method stub
		sendNoticeService.robPush(memberId, mob, extMsg);
	}
}







