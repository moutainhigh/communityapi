package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.dao.ShopBroadcastInfoMapper;
import net.okdi.api.dao.ShopBroadcastPushRelationMapper;
import net.okdi.api.dao.ShopTradeItemMapper;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ShopBroadcastInfo;
import net.okdi.api.entity.ShopBroadcastPushRelation;
import net.okdi.api.entity.ShopTradeItem;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.ShopBroadcastInfoService;
import net.okdi.api.service.ShopMemberPushSetService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ShopBroadcastInfoServiceImpl extends BaseServiceImpl implements ShopBroadcastInfoService {

	@Autowired
	ShopBroadcastInfoMapper shopBroadcastInfoMapper;

	@Autowired
	EhcacheService ehcacheService;

	@Autowired
	SendNoticeService sendNoticeService;

	@Autowired
	ShopMemberPushSetService shopMemberPushSetService;

	@Autowired
	private ShopTradeItemMapper shopTradeItemMapper;
	
	@Autowired
	private ShopBroadcastPushRelationMapper shopBroadcastPushRelationMapper; 
	@Autowired
	private MemberInfoMapper memberInfoMapper;//人员
	@Autowired
	private ConstPool constPool;
	Logger logger = Logger.getLogger(ShopBroadcastInfoServiceImpl.class);

	/**
	 * 抢送货(进行中,以完成)
	 */
	@Override
	public Map<String, Object> queryTakeDeliveryStation(
			Short queryStatus, Long memberId,Integer pageNo,Integer pageSize) {
		Integer total = 0;
		List<Map> list = ehcacheService.get("shopBraodcastListCache", queryStatus+ "-" + memberId, List.class);
		if(PubMethod.isEmpty(list)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("queryStatus", queryStatus);
			params.put("memberId", memberId);
			list = shopBroadcastInfoMapper.queryTakeDeliveryStation(params);
			ehcacheService.put("shopBraodcastListCache", queryStatus + "-"
					+ memberId, list);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(PubMethod.isEmpty(pageNo) || pageNo<1){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize) || pageSize<1){
			pageSize=10;
		}
		Page page = PageUtil.getPageData(pageNo, pageSize, list);
		dataMap.put("list", page.getItems());
		//total = shopBroadcastInfoMapper.selectCount(params);
		dataMap.put("totalCount", list.size());
		dataMap.put("pageNo", pageNo);
		dataMap.put("pageSize", pageSize);
		dataMap.put("pageCount", page.getPageCount());
		return dataMap;
	}

	/**
	 * 抢单
	 */
	@Override
	public Map<String, Object> querySingleDetails(Long broadcastId,
			Long memberId) {
		List list = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("broadcastId", broadcastId);
		params.put("memberId", memberId);
		list = shopBroadcastInfoMapper.querySingleDetails(params);
		dataMap.put("list", list);
		return dataMap;
	}

	/**
	 * 代取件
	 */
	@Override
	public Map<String, Object> queryShopBroadcastInfoById(Long broadcastId) {
		List list = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("broadcastId", broadcastId);
		list = shopBroadcastInfoMapper.queryShopBroadcastInfoById(params);
		dataMap.put("list", list);
		return dataMap;
	}

	/**
	 * 抢
	 */
	@Override
	public void rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude) {
		Map<String,Object> shopBroadcastInfoMap = this.queryOrderDetails(broadcastId,null,null);
		double distance = DistanceUtil.getDistance(addresseeLatitude.doubleValue(),addresseeLongitude.doubleValue()
				,Double.valueOf(shopBroadcastInfoMap.get("senderLatitude").toString()) , 
				Double.valueOf(shopBroadcastInfoMap.get("senderLongitude").toString()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String now = format.format(new Date());
		this.cacheAllMemberId(broadcastId);
		ehcacheService.remove("shopBraodcastListCache","1-" + memberId);
		ehcacheService.remove("shopBroadcastCountCache", memberId+"|"+now);
		ehcacheService.remove("shopBroadcastDetailCache", broadcastId.toString());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("broadcastId", broadcastId);
		map.put("memberId", memberId);
		map.put("singleTime", new Date());
		
		map.put("memberDistance", distance);
		int count = shopBroadcastInfoMapper.rob(map);
		if(count !=1){
			shopBroadcastInfoMap = this.queryOrderDetails(broadcastId,null,null);
			if(Short.valueOf(shopBroadcastInfoMap.get("broadcastStatus").toString())==1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.rob.001", "此订单已被其他送货员抢走啦！");
			}else if(Short.valueOf(shopBroadcastInfoMap.get("broadcastStatus").toString())==4){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.rob.002", "该送货任务已被取消啦！");
			}else{
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.rob.003", "抢单失败！");
			}	
		}else{
			this.cacheAllMemberId(broadcastId);
			ehcacheService.remove("shopBraodcastListCache","1-" + memberId);
			ehcacheService.remove("shopBroadcastCountCache", memberId+"|"+now);
			ehcacheService.remove("shopBroadcastDetailCache", broadcastId.toString());
		}
		MemberInfo memberInfo = memberInfoMapper.findById(memberId);
		Map<String,String> param = new HashMap<String,String>();
		param.put("broadcastId", broadcastId.toString());
		param.put("memberId", memberId.toString());
		param.put("memberName", memberInfo.getMemberName());
		param.put("deliveryPhone", memberInfo.getMemberPhone());
		String url = constPool.getO2opubUrl() + "shopCallExpress/updateToComplete";
		String response = Post(url, param);
		Map<String,Object> result = JSON.parseObject(response);
		if(PubMethod.isEmpty(result) || !"true".equals(result.get("success").toString())){
			throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.rob.004", "抢单，回调o2o异常");
		}
	}

	/**
	 * 确认取件
	 */
	@Override
	public void take(Long broadcastId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("broadcastId", broadcastId);
		params.put("takeTime", new Date());
		Map<String,Object> shopBroadcastInfoMap = this.queryOrderDetails(broadcastId,null,null);
		ehcacheService.remove("shopBraodcastListCache","1-" + shopBroadcastInfoMap.get("memberId"));
		ehcacheService.remove("shopBroadcastDetailCache",broadcastId.toString());
		int count= shopBroadcastInfoMapper.take(params);
		if(count !=1){
			ehcacheService.remove("shopBroadcastDetailCache",broadcastId.toString());
			shopBroadcastInfoMap = this.queryOrderDetails(broadcastId,null,null);
			if(Short.valueOf(shopBroadcastInfoMap.get("broadcastStatus").toString())==4){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.take.001", "该送货任务已被取消啦！");
			}else{
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.take.002", "操作失败！");
			}
		}else{
			System.out.println("清除进行中列表："+shopBroadcastInfoMap.get("memberId"));
			ehcacheService.remove("shopBraodcastListCache","1-" + shopBroadcastInfoMap.get("memberId"));
			ehcacheService.remove("shopBroadcastDetailCache",broadcastId.toString());
		}
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("broadcastId", broadcastId.toString());
		String url = constPool.getO2opubUrl() + "shopCallExpress/updateToDelivery";
		String response = Post(url, param);
		Map<String,Object> result = JSON.parseObject(response);
		if(PubMethod.isEmpty(result) || !"true".equals(result.get("success").toString())){
			throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.take.001", "确认取件，回调o2o异常");
		}
	}

	/**
	 * 根据 memberId 查询
	 */
	@Override
	public ShopBroadcastInfo findByMemberId(Long memberId) {
		// ShopBroadcastInfo shopBroadcastInfo =
		// ehcacheService.get("smsTempleAuditCache", memberId.toString(),
		// ShopBroadcastInfo.class);
		// if(PubMethod.isEmpty(shopBroadcastInfo)){
		// shopBroadcastInfo=this.shopBroadcastInfoMapper.findByMemberId(memberId);
		// this.ehcacheService.put("smsTempleAuditCache",
		// String.valueOf(memberId), shopBroadcastInfo);
		// }
		return null;
	}
	
	/**
	 * 		查询抢送货列表	
	 * @param memberId
	 * 			当前登录人id
	 * @param sendDeliveryLongitude
	 * 			送单人经度
	 * @param sendDeliveryLatitude
	 * 			送单人纬度
	 * @return map
	 */
	@Override
	public Map<String,Object> selectGrabDeliveryByMemberId(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude,Integer pageNo,Integer pageSize) {
		

		Map<String,Object> map = new HashMap<String,Object>();
		List<Map> grabDeliveryList = null;
		
		
		map = this.ehcacheService.get("shopBraodcastListCache", "0-"+memberId, Map.class);
		
		
		if(PubMethod.isEmpty(map)){
			//查待送货列表
			map = new HashMap<String,Object>();
			grabDeliveryList = shopBroadcastInfoMapper.selectGrabDeliveryByMemberId(memberId);
			//查当前收派员所能看到的待送货的总数
			Integer count = shopBroadcastInfoMapper.selectGrabDeliveryCount(memberId);
			map.put("count", count);
			map.put("grabDeliveryList", grabDeliveryList);
			for(int i=0;i<grabDeliveryList.size();i++){
				Date date = (Date)grabDeliveryList.get(i).get("serviceTime");
				grabDeliveryList.get(i).put("serviceTime", date.getTime());
			}
			this.ehcacheService.put("shopBraodcastListCache", "0-"+memberId, map);
		}else{
			grabDeliveryList = JSON.parseArray(map.get("grabDeliveryList").toString(), Map.class);
		}
		
		
		if(PubMethod.isEmpty(pageNo) || pageNo<1){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize) || pageSize<1){
			pageSize=10;
		}
		Page page = PageUtil.getPageData(pageNo, pageSize, grabDeliveryList);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("pageCount", page.getPageCount());
		grabDeliveryList = page.getItems();
		for(int i=0;i<grabDeliveryList.size();i++){
			//遍历取发件人地址经度
			Double senderLongitude = Double.valueOf(grabDeliveryList.get(i).get("senderLongitude")+"");
			
			//遍历取发件人地址纬度
			Double senderLatitude = Double.valueOf(grabDeliveryList.get(i).get("senderLatitude")+"");
			
			//遍历发货人到收货人距离
			Double tradeDistance = Double.valueOf(grabDeliveryList.get(i).get("tradeDistance")+"");
			
			//计算接单人到发件人的距离
			Double distance = DistanceUtil.getDistance(sendDeliveryLatitude.doubleValue(),sendDeliveryLongitude.doubleValue(),senderLatitude,senderLongitude);
			
			//放入map中
			grabDeliveryList.get(i).put("memberDistance", new DecimalFormat("##0.0").format(distance/1000)+"km");
			grabDeliveryList.get(i).put("tradeDistance", new DecimalFormat("##0.0").format(tradeDistance/1000)+"km");
		}
			map.put("grabDeliveryList", grabDeliveryList);
			return map;
	}

	/**
	 * 	确定送达接口实现
	 */
	@Override
	public void configDelivery(Long broadcastId, Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			memberId = shopBroadcastInfoMapper.queryMemberIdByBroadCastId(broadcastId);
		}
		
		//清理状态为1,2的缓存
		this.ehcacheService.remove("shopBraodcastListCache", "1-"+memberId);
		this.ehcacheService.remove("shopBraodcastListCache", "2-"+memberId);
		this.ehcacheService.remove("shopBroadcastDetailCache", broadcastId+"");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(new Date());
		int count = shopBroadcastInfoMapper.updateConfigDelivery(broadcastId,memberId,currentTime);
		if(count != 1){
			throw new ServiceException("openapi.GrabDeliveryServiceImpl.configDelivery.001", "修改确定送达状态，count执行sql异常");
		}else{
			this.ehcacheService.remove("shopBraodcastListCache", "1-"+memberId);
			this.ehcacheService.remove("shopBraodcastListCache", "2-"+memberId);
			this.ehcacheService.remove("shopBroadcastDetailCache", broadcastId+"");
		}
	}

	/**
	 * 	取消广播
	 */
	@Override
	public void cancelBroadcast(Long broadcastId) {
		SimpleDateFormat todayTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String fixedTime = todayTime.format(new Date());
		this.ehcacheService.remove("shopBroadcastDetailCache", broadcastId+"");
		
		ShopBroadcastInfo shopBroadcastInfo=this.shopBroadcastInfoMapper.selectByPrimaryKey(broadcastId);
		if(PubMethod.isEmpty(shopBroadcastInfo)){
			throw new ServiceException("openapi.GrabDeliveryServiceImpl.cancelBroadcast.001", "修改取消广播状态，shopBroadcastInfo为空");
		}
		if(shopBroadcastInfo.getBroadcastStatus() == 1 || shopBroadcastInfo.getBroadcastStatus() == 2){
			this.ehcacheService.remove("shopBraodcastListCache", "1-"+shopBroadcastInfo.getMemberId());
			this.ehcacheService.remove("shopBraodcastListCache", "2-"+shopBroadcastInfo.getMemberId());
			this.ehcacheService.remove("shopBroadcastCountCache", shopBroadcastInfo.getMemberId()+"|"+fixedTime);
		}else{
			this.cacheAllMemberId(broadcastId);
		}
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(new Date());
		int count = shopBroadcastInfoMapper.cancelBroadcast(broadcastId,currentTime);
		if(count != 1){
			throw new ServiceException("openapi.GrabDeliveryServiceImpl.cancelBroadcast.001", "修改取消广播状态，count执行sql异常");
		}else{
			this.ehcacheService.remove("shopBroadcastDetailCache", broadcastId+"");
			if(shopBroadcastInfo.getBroadcastStatus() == 1 || shopBroadcastInfo.getBroadcastStatus() == 2){
				this.ehcacheService.remove("shopBraodcastListCache", "1-"+shopBroadcastInfo.getMemberId());
				this.ehcacheService.remove("shopBraodcastListCache", "2-"+shopBroadcastInfo.getMemberId());
				this.ehcacheService.remove("shopBroadcastCountCache", shopBroadcastInfo.getMemberId()+"|"+fixedTime);
			}else{
				this.cacheAllMemberId(broadcastId);
			}
		}
		
		//推送
		try{
			if(!PubMethod.isEmpty(shopBroadcastInfo.getMemberId())){
				sendNoticeService.cancelNoticeToApp(broadcastId, shopBroadcastInfo.getMemberId(), shopBroadcastInfo.getShopName(), shopBroadcastInfo.getTradeNum()+"");
			}
		}catch(Exception e){
			
		}
	}

	/**
	 * 	统计订单数
	 */
	@Override
	public Map<String, Object> statisticalNumber(Long memberId) {
		Map<String,Object> result = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String currentTime = df.format(new Date());
		result = this.ehcacheService.get("shopBroadcastCountCache", memberId+"|"+currentTime, Map.class);
		if(PubMethod.isEmpty(result)){
			Integer OrderStatisticsNumber = shopBroadcastInfoMapper.orderStatistics(memberId);
			result = new HashMap<String,Object>();
			Map<String,Object> saveParam = new HashMap<String,Object>();
			saveParam.put("memberId", memberId);
			saveParam.put("currentTime", currentTime);
			Integer todayOrderNumber = shopBroadcastInfoMapper.todayOrder(saveParam);
			result.put("OrderStatisticsNumber", OrderStatisticsNumber);
			result.put("todayOrderNumber", todayOrderNumber);
			this.ehcacheService.put("shopBroadcastCountCache", memberId+"|"+currentTime, result);
		}
		
		return result;
	}
	
	/**
	 * 	清理根据广播Id 查出来的所有memberId状态为0的
	 * @param broadcastId
	 * 		广播id
	 */
	public void cacheAllMemberId(Long broadcastId){
		List<Long> listShopBroadcastInfo = shopBroadcastInfoMapper.selectMemberIdByBroadcastId(broadcastId);
		for(int i=0;i<listShopBroadcastInfo.size();i++){
			this.ehcacheService.remove("shopBraodcastListCache", "0-"+listShopBroadcastInfo.get(i));
		}
	}

	/**
	 * 	加缓存
	 */
	@Override
	public void currentMemberCacheInfor(Long memberId,
			BigDecimal sendDeliveryLongitude, BigDecimal sendDeliveryLatitude) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", memberId);
		param.put("sendDeliveryLongitude", sendDeliveryLongitude);
		param.put("sendDeliveryLatitude", sendDeliveryLatitude);
		param.put("currentTime", df.format(new Date()));
		
		this.ehcacheService.put("shopMemberOnlineCache", memberId+"", param);
	}
	
	/**
	 * 	清缓存
	 */
	@Override
	public void removeCurrentMemberCache(Long memberId){
		this.ehcacheService.remove("shopMemberOnlineCache", memberId+"");
	}

	/**
	 * 	查附近五公里，30分钟之内的所有送派员
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public List<Long> queryNearMemberList(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude) throws ParseException {
 		int dis=5;//设置5公里范围内
		
		List<Map> memberList = this.ehcacheService.getAll("shopMemberOnlineCache",Map.class);
		//List<Map<String,Object>>  listShopBroadcastInfo = shopBroadcastInfoMapper.selectAllShopBroadcastInfo();
		List<Long> memberIdList=new ArrayList<Long>();
		//取方圆5公里的数据
		if(!PubMethod.isEmpty(memberList) && memberList.size()!=0){
			for(int i=0;i<memberList.size();i++){
				Map<String,Object> compMap=(Map<String, Object>) memberList.get(i);
				Date createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(compMap.get("currentTime").toString());
				if(System.currentTimeMillis()-createTime.getTime()>30*60*1000){
					continue;
				}
				double distance = DistanceUtil.getDistance(sendDeliveryLatitude.doubleValue(),
														   sendDeliveryLongitude.doubleValue(),
														   Double.parseDouble(compMap.get("sendDeliveryLatitude").toString()),
														   Double.parseDouble(compMap.get("sendDeliveryLongitude").toString()))/1000;
				if(distance<=dis){
					memberIdList.add(Long.valueOf(compMap.get("memberId")+""));
				}
			}
		}
		return memberIdList;
	}
	
	
	/**
	 * 		查看附近送派员总数
	 * @param sendDeliveryLongitude
	 * 			送派员当前所在位置 经度
	 * @param sendDeliveryLatitude
	 * 			送派员当前所在位置 纬度
	 * @return
	 */
	@Override
	public int queryNearMemberCount(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude) throws ParseException{
		return this.queryNearMemberList(sendDeliveryLongitude, sendDeliveryLatitude).size();
	}
	@Override
	public String saveOrderInfo(String senderName, String senderPhone,
			Long senderTownId, String senderTownName, String senderAddress,
			BigDecimal senderLongitude, BigDecimal senderLatitude,
			String addresseeName, String addresseePhone,
			Long addresseeTownId, String addresseeTownName,
			String addresseeAddress, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, BigDecimal tradeDistance, String shopName,String shopPhone,
			String serviceTime, String tradeNum, short productNum,
			BigDecimal collectionMoney, BigDecimal freightMoney, String json) {
			
			if(PubMethod.isEmpty(senderName)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.001","插入订单异常，senderName参数非空异常");			
			}
			if(PubMethod.isEmpty(senderPhone)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.002","插入订单异常，senderPhone参数非空异常");			
			}
			if(PubMethod.isEmpty(senderTownId)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.003","插入订单异常，senderTownId参数非空异常");			
			}
			if(PubMethod.isEmpty(senderTownName)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.004","插入订单异常，senderTownName参数非空异常");			
			}
			if(PubMethod.isEmpty(senderAddress)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.005","插入订单异常，senderAddress参数非空异常");			
			}
			if(PubMethod.isEmpty(senderLongitude) || senderLongitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.006","插入订单异常，senderLongitude参数非空异常");			
			}
			if(PubMethod.isEmpty(senderLatitude) || senderLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.007","插入订单异常，senderLatitude参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeName)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.008","插入订单异常，addresseeName参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseePhone)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.009","插入订单异常，addresseePhone参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeTownId)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.010","插入订单异常，addresseeTownId参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeTownName)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.011","插入订单异常，addresseeTownName参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeAddress)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.012","插入订单异常，addresseeAddress参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeLongitude) || addresseeLongitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.013","插入订单异常，addresseeLongitude参数非空异常");			
			}
			if(PubMethod.isEmpty(addresseeLatitude) || addresseeLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.014","插入订单异常，addresseeLatitude参数非空异常");			
			}
			if(PubMethod.isEmpty(tradeDistance) || tradeDistance.compareTo(new BigDecimal(0))== -1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.015","插入订单异常，tradeDistance参数非空异常");			
			}
			if(PubMethod.isEmpty(shopName)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.016","插入订单异常，shopName参数非空异常");			
			}
			if(PubMethod.isEmpty(serviceTime)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.017","插入订单异常，serviceTime参数非空异常");			
			}
			if(PubMethod.isEmpty(tradeNum)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.018","插入订单异常，tradeNum参数非空异常");			
			}
			if(productNum<1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.019","插入订单异常，productNum参数非空异常");			
			}
			if(PubMethod.isEmpty(collectionMoney) || collectionMoney.compareTo(new BigDecimal(0))== -1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.020","插入订单异常，collectionMoney参数非空异常");			
			}
			if(PubMethod.isEmpty(freightMoney) || freightMoney.compareTo(new BigDecimal(0))== -1){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.021","插入订单异常，freightMoney参数非空异常");			
			}
			if(PubMethod.isEmpty(shopPhone)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.022","插入订单异常，shopPhone参数非空异常");			
			}
			ShopBroadcastInfo shop=new ShopBroadcastInfo();
			shop.setBroadcastId(IdWorker.getIdWorker().nextId());
			//查询附近收派员
			List<Long> couriserList = new ArrayList<Long>();
			try {
				couriserList = queryNearMemberList(senderLongitude, senderLatitude);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.023","查询附近收派员异常");	
			}
			List<Long> memberIdList = new ArrayList<Long>();
			List<ShopBroadcastPushRelation> shopBroadcastPushRelationList = new ArrayList<ShopBroadcastPushRelation>();
			for(Long memberId : couriserList){
				short result=this.shopMemberPushSetService.queryShopMemberPushSetById(memberId);
				if(result==1){
				continue;	
				}
				try{
					ShopBroadcastPushRelation shopBroadcastPushRelation=new ShopBroadcastPushRelation();
					shopBroadcastPushRelation.setRelationId(IdWorker.getIdWorker().nextId());
					shopBroadcastPushRelation.setMemberId(memberId);
					shopBroadcastPushRelation.setBroadcastId(shop.getBroadcastId());
					shopBroadcastPushRelationList.add(shopBroadcastPushRelation);
					memberIdList.add(memberId);
					}catch(Exception e){
						continue;
					}
				this.ehcacheService.remove("shopBraodcastListCache", "0-"+memberId);
			}
			Long radioBroadcastId=null; //广播id

			shop.setSenderName(senderName);
			shop.setSenderPhone(senderPhone);
			shop.setSenderTownId(Long.valueOf(senderTownId));
			shop.setSenderTownName(senderTownName);
			shop.setSenderAddress(senderAddress);
			shop.setSenderLongitude(senderLongitude);
			shop.setSenderLatitude(senderLatitude);
			shop.setAddresseeName(addresseeName);
			shop.setAddresseePhone(addresseePhone);
			shop.setAddresseeTownId(Long.valueOf(addresseeTownId));
			shop.setAddresseeTownName(addresseeTownName);
			shop.setAddresseeAddress(addresseeAddress);
			shop.setAddresseeLongitude(addresseeLongitude);
			shop.setAddresseeLatitude(addresseeLatitude);
			shop.setTradeDistance(tradeDistance);
			shop.setShopName(shopName);
			shop.setShopPhone(shopPhone);
			shop.setTotalAmount(collectionMoney.add(freightMoney));
			short a=0;//广播状态 0待接单、1已接单（待取件）、2已取件（派送中）、3已完成、4已取消   初始化为0
			shop.setBroadcastStatus(a);
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date serTime;
				serTime = sim.parse(serviceTime);
				shop.setServiceTime(serTime);
			} catch (ParseException e) {
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.024","插入订单异常，serviceTime参数转换异常");
			}
			shop.setTradeNum(tradeNum);
			shop.setProductNum(productNum);
			shop.setCollectionMoney(collectionMoney);
			shop.setFreightMoney(freightMoney);
			try {
				this.shopBroadcastInfoMapper.insert(shop); //插入订单详情到shop_broadcast_info表
			} catch (Exception e) {
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.025","插入订单异常，插入订单详情异常");
			}
			radioBroadcastId = shop.getBroadcastId();
			
			List<ShopTradeItem> list=new ArrayList<ShopTradeItem>();
			if(json!=null&&!"".equals(json)){
				JSONObject fromObject=JSONObject.parseObject(json);
				JSONArray jsonArray = fromObject.getJSONArray("data");
				List<Map> list1 = JSON.parseArray(jsonArray.toString(),Map.class);
		        for(Map map:list1){
		        	ShopTradeItem shopTradeItem=new ShopTradeItem();
		           	shopTradeItem.setId(IdWorker.getIdWorker().nextId());
		    		if(PubMethod.isEmpty(map.get("productName").toString())){
						throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.026","插入商品详情异常，productName参数非空异常");			
					}
		           	shopTradeItem.setProductName(map.get("productName").toString());
		    		if(PubMethod.isEmpty(map.get("productNum"))){
						throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.027","插入商品详情异常，productNum参数非空异常");			
					}
					if(Integer.parseInt(String.valueOf(map.get("productNum")))<1){
						throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.028","插入商品详情异常，productNum参数数量不能小于1");			
					}
		           	shopTradeItem.setProductNum(Integer.valueOf(map.get("productNum").toString()));
		           	shopTradeItem.setBroadcastId(radioBroadcastId);
		           	list.add(shopTradeItem);
		           	}  
				}
			try {
				this.shopTradeItemMapper.insertList(list);//插入商品详情到shop_trade_item表
			} catch (Exception e) {
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.029","插入订单异常，插入商品信息异常");
			}
				
		try {
			if(shopBroadcastPushRelationList.size()>0){
				this.shopBroadcastPushRelationMapper.insertList(shopBroadcastPushRelationList);//插入快递员详情到shop_broadcast_push_relation表，如果没有找到快递员就不插
				}
		} catch (Exception e) {
			throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.saveOrderInfo.030","插入订单异常，插入快递员信息异常");
		}
		for(Long memberId:memberIdList){
			try {
				//执行推送操作
				this.ehcacheService.remove("shopBraodcastListCache", "0-"+memberId);
				this.sendNoticeService.broadNoticeToApp(radioBroadcastId, memberId);
			} catch (Exception e) {
				continue;
			}
		}
		return radioBroadcastId+"";
	}

	@Override
	public Map<String,Object> queryOrderDetails(Long broadcastId,Double lat,Double lng) {
		Map map=new HashMap();
		if(PubMethod.isEmpty(broadcastId)){
			throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.queryOrderInfo.001","查询订单异常，broadcastId参数非空异常");			
		}
		map = this.ehcacheService.get("shopBroadcastDetailCache", broadcastId.toString(), Map.class);
		if(PubMethod.isEmpty(map)){
			map=this.shopBroadcastInfoMapper.selectOrderDetailById(broadcastId);//根绝广播id查询订单详情
			if(PubMethod.isEmpty(map)){
				throw new ServiceException("openapi.ShopBroadcastInfoServiceImpl.queryOrderInfo.002","查询订单异常，broadcastId参数不存在");
			}
			List<Map> list=this.shopTradeItemMapper.findShopTradeById(broadcastId);//根据广播id查询商品信息
			map.put("shopTradeItem",list);
			this.ehcacheService.put("shopBroadcastDetailCache", broadcastId.toString(), map);
		}
		Double distance = 0.00;
		if(lat != null && lng != null){
			if(PubMethod.isEmpty(map.get("memberDistance"))){
				//查询快递员到发货地的距离
				distance=DistanceUtil.getDistance(lat,lng,((BigDecimal)map.get("senderLatitude")).doubleValue(),((BigDecimal)map.get("senderLongitude")).doubleValue());
				distance=distance/1000;
			}else{
				distance=Double.parseDouble(map.get("memberDistance").toString())/1000;
			}
		}
		
		BigDecimal collectionMoney = new BigDecimal(map.get("collectionMoney").toString());
		collectionMoney = collectionMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalAmount = new BigDecimal(map.get("totalAmount").toString());
		totalAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("collectionMoney",collectionMoney);
		map.put("totalAmount",totalAmount);
		BigDecimal memberDistance = new BigDecimal(distance);
		memberDistance = memberDistance.setScale(1, BigDecimal.ROUND_HALF_UP);
		BigDecimal tradeDistance = new BigDecimal(Double.parseDouble(map.get("tradeDistance").toString())/1000);
		tradeDistance = tradeDistance.setScale(1, BigDecimal.ROUND_HALF_UP);
		map.put("memberDistance",memberDistance+"km");
		map.put("tradeDistance", tradeDistance+"km");
		return map;
	}

	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

}
