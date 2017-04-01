/**  
 * @Project: openapi
 * @Title: BroadcastServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-1-14 上午11:53:25
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.RobBroadcastInfoMapper;
import net.okdi.api.dao.RobParcelRelationMapper;
import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.api.entity.RobParcelRelation;
import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.service.BroadcastService;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.RobInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.vo.VO_BroadcastInfo;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author amssy
 * @version V1.0
 */
@SuppressWarnings("unchecked")
@Service
public class BroadcastServiceImpl implements BroadcastService{
    @Autowired
	private EhcacheService ehcacheService; //缓存
	@Autowired
	private RobBroadcastInfoMapper broadcastMapper;//广播
    @Autowired
    private ParcelInfoService parcelInfoService;//包裹
    @Autowired
    private RobParcelRelationMapper robParcelRelationMapper;//抢单包裹关系
    @Autowired
    private RobInfoService robInfoService;//报价
    @Autowired
    private CourierService courierService; //附近站点收派员
    @Autowired
    private SendNoticeService sendNoticeService;//推送
	@Autowired
	private MemberInfoService memberInfoService;//人员
	private static List<String> list = new ArrayList<String>();
	static {
		list.add("北京市");
		list.add("天津市");
		list.add("河北省");
		list.add("山西省");
		list.add("内蒙古自治区");
		list.add("辽宁省");
		list.add("吉林省");
		list.add("黑龙江省");
		list.add("上海市");
		list.add("江苏省");
		list.add("浙江省");
		list.add("安徽省");
		list.add("福建省");
		list.add("江西省");
		list.add("山东省");
		list.add("河南省");
		list.add("湖北省");
		list.add("湖南省");
		list.add("广东省");
		list.add("广西壮族自治区");
		list.add("海南省");
		list.add("重庆市");
		list.add("四川省");
		list.add("贵州省");
		list.add("云南省");
		list.add("西藏自治区");
		list.add("陕西省");
		list.add("甘肃省");
		list.add("青海省");
		list.add("宁夏回族自治区");
		list.add("新疆维吾尔自治区");
		list.add("台湾省");
		list.add("香港特别行政区");
		list.add("澳门特别行政区");
		list.add("海外");
		
	}
    /**
	 * @Method: addBroadcast 
	 * @param broadcastInfo
	 * @return 
	 * @see net.okdi.api.service.BroadcastService#addBroadcast(net.okdi.api.vo.VO_BroadcastInfo) 
	*/
	@Override
	public String addBroadcast(String jsonString,Long memberId) {
		List<RobParcelRelation>list = new ArrayList<RobParcelRelation>();
		VO_BroadcastInfo broadcastInfo = getBroadcastInfo(jsonString, list, (short)2, memberId);
		addBroadcastBase(broadcastInfo, list);
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("id", broadcastInfo.getId());
		return jsonSuccess(map);
	}
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>封装broadcastVo|addBroadcastOwn|addBroadcast</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:22:55</dd>
     * @param jsonValue
     * @param list
     * @param source
     * @param memberId
     * @return
     * @since v1.0
     */
	private VO_BroadcastInfo getBroadcastInfo(String jsonValue,List<RobParcelRelation>list,short source,Long memberId){
		 JSONObject obj =  JSON.parseObject(jsonValue);
		 List <VO_ParcelInfoAndAddressInfo>parcelList = JSON.parseArray(String.valueOf(obj.get("parcelValues")), VO_ParcelInfoAndAddressInfo.class);
		 Long broadcastId = IdWorker.getIdWorker().nextId(); 
		 StringBuffer sb = new StringBuffer();
		 if(parcelList!=null){
			 Long parcelId = null;
		 for (int i = 0;i<parcelList.size();i++){
			 RobParcelRelation robParcelRelation = new RobParcelRelation();
			 parcelId = IdWorker.getIdWorker().nextId();
			 parcelList.get(i).setId(parcelId);
			 robParcelRelation.setId(IdWorker.getIdWorker().nextId());
			 robParcelRelation.setRobId(broadcastId);
			 robParcelRelation.setParcelId(parcelId);
			 list.add(robParcelRelation);
			 if(i == 0){
				 sb.append(this.subbStringProvience(parcelList.get(i).getAddresseeAddress()));
			 }
			 if(i>0&&i<3){
				 sb.append("、"+this.subbStringProvience(parcelList.get(i).getAddresseeAddress()));
			 }
		 }
		 }
		 VO_BroadcastInfo broadcastInfoVO = new VO_BroadcastInfo();
		   broadcastInfoVO.setBroadcastRemark(String.valueOf(obj.get("broadcastRemark")));
		   broadcastInfoVO.setBroadcastStatus((short)1);
		   broadcastInfoVO.setBroadcastType(source);
		   broadcastInfoVO.setId(broadcastId);
		   broadcastInfoVO.setLoginMemberId(PubMethod.isEmpty(memberId)?Long.parseLong(String.valueOf(obj.get("loginMemberId"))):memberId);
		   broadcastInfoVO.setParcelValues(parcelList);
		   broadcastInfoVO.setSenderAddressId(Long.parseLong(obj.getString("senderAddressId")==null?"0":obj.getString("senderAddressId")));
		   broadcastInfoVO.setSenderAddressName(obj.getString("senderAddressName"));
		   broadcastInfoVO.setSenderLatitude(Double.parseDouble(obj.getString("senderLatitude")));
		   broadcastInfoVO.setSenderLongitude(Double.parseDouble(obj.getString("senderLongitude")));
		   broadcastInfoVO.setSenderMobile(obj.getString("senderMobile"));
		   broadcastInfoVO.setTotalCount(Integer.parseInt(obj.getString("totalCount")));
		   broadcastInfoVO.setTotalWeight(Double.parseDouble(obj.getString("totalWeight")));
		   broadcastInfoVO.setAddresseeAddress(sb.toString());
		   broadcastInfoVO.setSenderName(obj.getString("senderName"));
		   return broadcastInfoVO;
	}
	@Override
	public String addBroadcastBase(VO_BroadcastInfo broadcastInfo,List<RobParcelRelation>list) {
		List parcelList = broadcastInfo.getParcelValues();
		RobBroadcastInfo broadcast =  broadcastInfo.changToRobBroadcast();
		if (parcelList!=null) {
			broadcast.setTotalCount(parcelList.size());
		}
		broadcastMapper.insert(broadcast);
		parcelInfoService.addbatchSaveParcelInfo(parcelList);
		this.addBroadcastParcelRelation(list);
		List quotationlist = getNearCompAndCourier(broadcastInfo.getSenderLongitude(), broadcastInfo.getSenderLatitude(), list.get(0).getRobId(),broadcastInfo.getSenderAddressName());
		if(quotationlist.size()>0){
		robInfoService.addbatchRobQuotationInfo(quotationlist);
		}
		ehcacheService.remove("robBroadcastCache", String.valueOf(broadcast.getLoginMemberId()));
		ehcacheService.removeAll("robQuotationInfoCache");
		return null;
	}
	/**
	 * @Method: addBroadcastParcelRelation  批量添加包裹预约关系表
	 * @param list 
	 * @return  
	 * @see net.okdi.api.service.BroadcastService#addBroadcastParcelRelation(java.util.List)
	 */
	@Override
	public String addBroadcastParcelRelation(List<RobParcelRelation> list) {
        robParcelRelationMapper.addBroadcastParcelRelation(list);
		return jsonSuccess(null);
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询附近站点收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:38:46</dd>
	 * @param lng
	 * @param lat
	 * @param broadcastId
	 * @return
	 * @since v1.0
	 */
	public List<RobQuotationInfo> getNearCompAndCourier(Double lng,Double lat,Long broadcastId,String sendAddressName){
		Map<String,Object> map  = courierService.queryNearComp(lng, lat, null, null, 0.0, null,20l);
		List<Map<String,Object>> couriserList = courierService.queryNearMemberRob(lng, lat, null, null, null, 0, 20);
		List<Map> compList = (List<Map>) map.get("expCompList");
		 List<RobQuotationInfo> result = fixRobQuotation(compList, couriserList, broadcastId,sendAddressName);
		return result;
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>组装站点收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:37:25</dd>
	 * @param compList
	 * @param courierList
	 * @param broadcastId
	 * @return
	 * @since v1.0
	 */
	private List<RobQuotationInfo> fixRobQuotation(List compList,List courierList,Long broadcastId,String sendAddressName){
		List <RobQuotationInfo> result = new ArrayList<RobQuotationInfo>();
		for (int i =0;i<compList.size();i++){
			Map map = (Map)compList.get(i);
		        RobQuotationInfo rqi = new RobQuotationInfo();
		        rqi.setCompId(Long.parseLong(map.get("compId").toString()));
		        rqi.setCreateTime(new Date());
		        rqi.setId(IdWorker.getIdWorker().nextId());
		        //rqi.setIsNew((short)0);
                rqi.setRobId(broadcastId); 
                rqi.setRobStatus((short)2);
                rqi.setQuotationType((short)1);
                rqi.setSuccessFlag((short)2);
                rqi.setDistance((long)(Double.parseDouble(map.get("distance").toString())*1000));
                sendNoticeToComp(rqi.getCompId(), map, broadcastId, sendAddressName);
                result.add(rqi);
		}
		for(int i = 0;i<courierList.size();i++){
			Map map = (Map)courierList.get(i);
		        RobQuotationInfo rqi = new RobQuotationInfo();
		        rqi.setCompId(Long.parseLong(map.get("compId").toString()));
		        rqi.setCreateTime(new Date());
		        rqi.setMemberId(Long.parseLong(map.get("memberId").toString()));
		        rqi.setId(IdWorker.getIdWorker().nextId());
		        //rqi.setIsNew((short)0);
	            rqi.setRobId(broadcastId); 
	            rqi.setQuotationType((short)2);
	            rqi.setRobStatus((short)2);
	            rqi.setDistance((long)(Double.parseDouble(map.get("distance").toString())*1000));
	            rqi.setSuccessFlag((short)2);
	            sendNoticeService.broadNoticeToExp(broadcastId, rqi.getMemberId(), rqi.getDistance()/1000+"", sendAddressName, 1);
	            result.add(rqi);
		}
		return result;
	}
	
    private String jsonSuccess(Object map) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != map) {
			allMap.put("data", map);
		}
		return JSON.toJSONString(allMap,BaseController.s);
	}
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>个人端创建广播</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:35:15</dd>
     * @param jsonString
     * @param memberId
     * @return
     * @since v1.0
     */
	@Override
	public String addBroadcastOwn(String jsonString) {
		List<RobParcelRelation>list = new ArrayList<RobParcelRelation>();
		VO_BroadcastInfo broadcastInfo = getBroadcastInfo(jsonString, list, (short)1, null);
		addBroadcastBase(broadcastInfo, list);
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("id", broadcastInfo.getId());
		return jsonSuccess(map);
	}

    private String subbStringProvience(String addressName){
    	String result = null; 
    	for (int i = 0;i<list.size();i++){
    		if(addressName.contains(list.get(i).toString())){
    			result = list.get(i).toString();
    		}
    	}
    	if(result == null){
    		return "";
    	}else{
    		return result;
    	}
    }
    
    private void sendNoticeToComp(Long compId,Map map,Long broadcastId,String sendAddressName){
        List <Map<String,Object>>list = memberInfoService.getMemberInfoByCompId(compId);
    	for (int i = 0; i < list.size(); i++) {
    		if("-1".equals(list.get(i).get("roleId").toString())||"1".equals(list.get(i).get("roleId").toString())||"2".equals(list.get(i).get("roleId").toString())){
    		 sendNoticeService.broadNoticeToSite(broadcastId, Long.parseLong(list.get(i).get("memberId").toString()), map.get("distance").toString(), sendAddressName, 1);	
    		}
    		}
    }
//    private String subbStringProvience(Long addressId){
//    	if(PubMethod.isEmpty(addressId)){return "";}
//    	String shortId = (addressId+"").substring(0,2);
//    	
//    	return map.get(shortId)==null?"":map.get(shortId).toString();
//    }
	@Override
	public String addBroadcastOperation(String jsonString, Long memberId) {
		List<RobParcelRelation>list = new ArrayList<RobParcelRelation>();
		VO_BroadcastInfo broadcastInfo = getBroadcastInfo(jsonString, list, (short)4, memberId);
		addBroadcastBase(broadcastInfo, list);
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("id", broadcastInfo.getId());
		return jsonSuccess(map);
	}
	@Override
	public String addBroadcastOwnOperation(String jsonString) {
		List<RobParcelRelation>list = new ArrayList<RobParcelRelation>();
		VO_BroadcastInfo broadcastInfo = getBroadcastInfo(jsonString, list, (short)3, null);
		addBroadcastBase(broadcastInfo, list);
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("id", broadcastInfo.getId());
		return jsonSuccess(map);
	}

}
