/**  
 * @Project: openapi
 * @Title: CourierServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-1 下午03:23:31
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.BasOnLineMemberMapper;
import net.okdi.api.dao.CourierMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.apiV1.dao.BasEmployeeAuditMapperV1;
import net.okdi.apiV4.dao.MemberInfoMapperV4;
import net.okdi.core.common.Geohash;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.ElectronicFenceUtil;
import net.okdi.core.util.PubMethod;

/**
 * @ClassName CourierServiceImpl
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-11-1
 * @since jdk1.6
 */
@SuppressWarnings("unchecked")
@Service
public class CourierServiceImpl implements CourierService {
	private static double EARTH_RADIUS = 6378.137 * 1000;
	
	@Autowired
	private CourierMapper courierMapper;
	
	@Autowired
	BasOnLineMemberMapper basOnLineMemberMapper;
	
	@Autowired
	EhcacheService ehcacheService;
	
	@Autowired
	BasCompInfoMapper compInfoMapper;
	
	@Autowired
	BasNetInfoMapper netInfoMapper;
	
	@Autowired
	MemberInfoService memberInfoService;
	
	@Autowired
	private BasEmployeeAuditMapperV1 basEmployeeAuditMapper;
	
	@Autowired
	private MemberInfoMapperV4 memberInfoMapperV4;
	
	@Value("${save.courier.head}")
	String readPath;
	
	private static final Logger logger = LoggerFactory.getLogger(CourierServiceImpl.class);
	
	/**
	 * @Method: queryNearMember 
	 * @Description: TODO
	 * @param lng
	 * @param lat
	 * @param townId
	 * @param streetId
	 * @param netId
	 * @param sortFlag
	 * @return 
	 * @see net.okdi.api.service.CourierService#queryNearMember(double, double, java.lang.Long, java.lang.Long, java.lang.Long, int) 
	 * @since jdk1.6
	*/
	@Override
	public List <Map<String,Object>> queryNearMember(Double lng,Double lat,Long townId,Long streetId,Long netId,Integer sortFlag,Integer howFast) {
	//	this.saveOnLineMember(1500l, "D速物流", 5981l, "广西北海公司", 123l, "张梦楠", "13161340623", 116.257164, 39.930728, "");
		int dis=howFast;//设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(lat)));
		dlng = dlng * 180.0 / Math.PI; // 弧度转换成角度
		double dlat = Math.abs(dis * 1000 / EARTH_RADIUS);
		dlat = dlat * 180.0 / Math.PI; // 弧度转换成角度
		double bottomLat = lat - dlat;
		double topLat = lat + dlat;
		double leftLng = lng - dlng;
		double rightLng = lng + dlng;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bottomLat", bottomLat);
		params.put("topLat", topLat);
		params.put("leftLng", leftLng);
		params.put("rightLng", rightLng);
		params.put("townId", townId);
		params.put("streetId", streetId);
		params.put("netId", netId);
		params.put("sortFlag", sortFlag);
		List<Map> memberList = ehcacheService.getAll("onLineMember", Map.class);// this.courierMapper.queryNearMember(params);
		List<Map<String, Object>> newMemberList = new ArrayList<Map<String, Object>>();
		if (memberList == null || memberList.size() == 0) {
			return newMemberList;
		}
		for (int i = 0; i < memberList.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) memberList.get(i);
			if ((new Date().getTime() - Long.parseLong(map.get("createTime").toString())) > 1860000) {
				continue;
			}
			double distance = this.getDistance(lat, lng, Double.parseDouble(map.get("lat").toString()),
					Double.parseDouble(map.get("lng").toString())) / 1000;
			if (distance <= dis) {
				boolean kmFlag = distance * 1000 - 1000 > 0;
				map.put("distance", distance);
				map.put("distanceNew", kmFlag ? distance : distance * 1000);
				map.put("distanceNumber", kmFlag ? distance : distance * 1000);// 兼容ios错误
				if (kmFlag) {
					map.put("unit", "km");
					map.put("distanceUnit", "km");// 兼容ios错误
				} else {
					map.put("unit", "m");
					map.put("distanceUnit", "m");// 兼容ios错误
				}
				String compId = map.get("compId").toString();
				BasCompInfo compInfo = this.ehcacheService.get("compCache", compId, BasCompInfo.class);
				if (compInfo == null) {// 缓存里面没有从数据库里面读取公司信息
					compInfo = this.compInfoMapper.findById(Long.parseLong(compId));
				}
				map.put("compTel", compInfo.getCompTel() == null ? " " : compInfo.getCompTel());
				map.put("headImg", readPath + "/" + map.get("memberId") + ".jpg");
				map.put("approveFlag", map.get("flag") == null ? "0" : "1");
				Map<String, Object> memberMap = memberInfoService
						.getValidationStatus(Long.parseLong(map.get("memberId").toString()));

				if (memberMap != null && "1".equals(String.valueOf(memberMap.get("relationFlag")))
						&& "1".equals(String.valueOf(memberMap.get("veriFlag")))) {
					map.put("veriFlag", 1);
				} else {
					continue;
				}
				newMemberList.add(map);
			}
		}
		if (sortFlag == 0) {
			Collections.sort(newMemberList, new ComparatorList());
		} else if (sortFlag == 1) {
			Collections.sort(newMemberList, Collections.reverseOrder(new ComparatorList()));
		}
		return newMemberList;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> queryNearMemberForWechat(Double lng, Double lat, Integer sortFlag, Integer howFast,Long assignNetId, String roleType) {
		int dis = howFast;// 设置5公里范围内
		String hash = Geohash.encode(lat, lng);
		String[] hashExpands = Geohash.getGeoHashExpand(hash.substring(0, 5));
		List<Map> memberList = new ArrayList<>();
		for (String hashExp : hashExpands) {
			String geoHashStr = hashExp.substring(0, 5);
			List<Map> mems = ehcacheService.getAll("onLineMember.geohash:" + geoHashStr, Map.class);
			if (mems == null) {
				continue;
			}
			memberList.addAll(mems);
		}
		
		//List<Map> memberList = ehcacheService.getAll("onLineMember", Map.class);// this.courierMapper.queryNearMember(params);
		List<Map<String, Object>> newMemberList = new ArrayList<Map<String, Object>>();
		if (memberList == null || memberList.size() == 0) {
			return newMemberList;
		}
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i) == null ) {
				continue;
			}
			Map<String, Object> map = (Map<String, Object>) memberList.get(i);
			if ((new Date().getTime() - Long.parseLong(map.get("createTime").toString())) > 1860000) {
				continue;
			}
			double courierLat = Double.parseDouble(map.get("lat").toString());
			double courierLng = Double.parseDouble(map.get("lng").toString());
			double distance = this.getDistance(lat, lng, courierLat, courierLng) / 1000;
			if (distance <= dis) {
				boolean kmFlag = distance * 1000 - 1000 > 0;
				map.put("distance", distance);
				map.put("distanceNew", kmFlag ? distance : distance * 1000);
				map.put("distanceNumber", kmFlag ? distance : distance * 1000);// 兼容ios错误
				map.put("distanceM", distance * 1000);// 显示米
				if (kmFlag) {
					map.put("unit", "km");
					map.put("distanceUnit", "km");// 兼容ios错误
				} else {
					map.put("unit", "m");
					map.put("distanceUnit", "m");// 兼容ios错误
				}
				map.put("headImg", readPath + "/" + map.get("memberId") + ".jpg");
				newMemberList.add(map);
			}
		}
		if (sortFlag == 0) {
			Collections.sort(newMemberList, new ComparatorList());
		} else if (sortFlag == 1) {
			Collections.sort(newMemberList, Collections.reverseOrder(new ComparatorList()));
		}
		if(PubMethod.isEmpty(assignNetId)){
			return newMemberList;
		}else{
			List<Map<String, Object>> netMemberList = new ArrayList<Map<String, Object>>();
				//遍历附近快递员,筛选出对应快递公司的快递员
				for (Map<String, Object> map : newMemberList) {
					/*String role = map.get("roleType")+"";
					if("2".equals(roleType)){
						//代收点过来查询不让查到代收点2,3的角色
						if(!"2".equals(role) && !"3".equals(role)){
							String netId = String.valueOf(map.get("netId"));
							if(netId.equals(String.valueOf(assignNetId))){
								netMemberList.add(map);
							}
						}
					}else{
						//微信叫快递的
						String netId = String.valueOf(map.get("netId"));
						if(netId.equals(String.valueOf(assignNetId))){
							netMemberList.add(map);
						}
				}*/
					String netId = String.valueOf(map.get("netId"));
					if(netId.equals(String.valueOf(assignNetId))){
						netMemberList.add(map);
					}
			}
			return netMemberList;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorList implements Comparator {
		public int compare(Object arg0, Object arg1) {
			int flag;
			Map map0 = (Map) arg0;
			Map map1 = (Map) arg1;
			double distance0 = Double.parseDouble(map0.get("distance").toString());
			double distance1 = Double.parseDouble(map1.get("distance").toString());
			if (distance0 > distance1)
				flag = -1;
			else if (distance0 < distance1)
				flag = 1;
			else
				flag = 0;
			return flag;
		}
	}
	
	private  double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	public  double getDistance(double lat1, double lng1, double lat2,double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * @Method: saveOnLineMember 
	 * @Description: TODO
	 * @param netId
	 * @param netName
	 * @param compId
	 * @param compName
	 * @param memberId
	 * @param memberName
	 * @param memberMobile
	 * @param lng
	 * @param lat
	 * @param memo 
	 * @see net.okdi.api.service.CourierService#saveOnLineMember(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double, java.lang.Double, java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public void saveOnLineMember(Long netId, String netName, Long compId, String compName, Long memberId,
			String memberName, String memberMobile, Double lng, Double lat, String memo) {
		
		MemberInfo memberInfo = memberInfoService.getMemberInfoById(memberId);
		String phone = memberInfo.getMemberPhone();
		String name = memberInfo.getMemberName();
		
		memberName = memberName == null ? "" : memberName;
		if (!memberName.equals(name)) {
			logger.debug("前端传的名字不一样了::memberName => " + memberName + "; name => " + name); 
		}
		
		memberMobile = memberMobile == null ? "" : memberMobile;
		if (!memberMobile.equals(phone)) {
			logger.debug("前端传的手机号不一样了::memberMobile => " + memberMobile + "; phone => " + phone); 
		}
		boolean veriFlag = getVeriFlag(memberId); 
		if (!veriFlag) {
			logger.info("{}没有进行实名认证::{}", veriFlag);
			return;
		}
		
		String role = memberInfoMapperV4.findRoleByMemberId(memberId);
		if("2".equals(role) || "3".equals(role)){
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", IdWorker.getIdWorker().nextId());
		map.put("netId", netId);
		map.put("netName", netName);
		map.put("compId", compId);
		map.put("compName", compName);
		map.put("memberId", memberId);
		map.put("memberName", name);
		map.put("memberMobile", phone);
		map.put("lng", lng);
		map.put("lat", lat);
		map.put("memo", memo);
		map.put("roleType", role);
		basOnLineMemberMapper.insert(map);
		
		map.put("createTime", new Date().getTime());
		ehcacheService.removeAll("nearCompAndPersionCache");
		
		
		String hash = Geohash.encode(lat, lng);
		String geoHash = hash.substring(0, 5);
		Map<String, Object> oldHashMap = ehcacheService.get("onLineMember.geohashlist", memberId + "", Map.class);
		String oldHash = oldHashMap == null ? "" : String.valueOf(oldHashMap.get("geoHash"));
		logger.info("新GeoHash::{}, redis读到的上一次GeoHash::{}, {}", geoHash, oldHash, geoHash.equals(oldHash));
		if (!geoHash.equals(oldHash)) {
			logger.info("删除redis中{}旧的GeoHash::{}", memberId + "", oldHash);
			ehcacheService.remove("onLineMember.geohash:" + oldHash, memberId + "");
		}
		ehcacheService.put("onLineMember.geohash:" + geoHash, memberId + "", map);
		Map<String, Object> geoHashMap = new HashMap<>();
		geoHashMap.put("geoHash", geoHash);
		ehcacheService.put("onLineMember.geohashlist", memberId + "", geoHashMap);
	}
	
	private boolean getVeriFlag(Long memberId) {
		BasEmployeeAudit bea = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,1L);
		if (null == bea || (Short) bea.getAuditOpinion() == 2 || -1 == bea.getAuditOpinion() || 0 == bea.getAuditOpinion() ) { //1通过  2未通过
			return false;
		}
		return true;
	}

	/**
	 * @Method: autoSuggest 
	 * @Description: TODO
	 * @param compId
	 * @param lat
	 * @param lng
	 * @return 
	 * @see net.okdi.api.service.CourierService#autoSuggest(java.lang.Long, java.lang.Double, java.lang.Double) 
	 * @since jdk1.6
	*/
	@Override
	public List<Map> autoSuggest(Long compId, Double lat,
			Double lng) {
		String key = compId+"_"+lat+"_"+lng; 
		String result = this.ehcacheService.get("autoSuggest", key+"", String.class);
		if(!PubMethod.isEmpty(result)){
			List <Map>resultList = JSON.parseArray(result,Map.class);
		    return resultList;
		}else{
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("compId", compId);
		map.put("lat", lat);
		map.put("lng", lng);
		List<Map> list = courierMapper.autoSuggest(map);
		Iterator<Map> it = list.iterator();
		List<Map> tempList = new ArrayList<Map>();
		while(it.hasNext()){
			Map data = it.next();
			if(ElectronicFenceUtil.pointInPolygon(lng, lat, data.get("lngs").toString(),data.get("lats").toString() )){
				tempList.add(data);
				break;
			};
		}
		String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
		if(PubMethod.isEmpty(keys)){
			ehcacheService.put("autoSuggestKey", compId+"", key);
		}else{
			ehcacheService.put("autoSuggestKey", compId+"", keys+","+key);
		}
		ehcacheService.put("autoSuggest", key, JSON.toJSONString(tempList));
		return tempList;
		}
	}
	
	private void removeAll(String cacheName,String keys){
		if(keys!=null){
		String [] allKey = keys.split(",");
	    for (int i = 0;i<allKey.length;i++){
	    	ehcacheService.remove(cacheName, allKey[i]);
	    }
		}
	}

	/**
	 * @Method: updateOnLineMember 
	 * @Description: TODO
	 * @param id
	 * @param lng
	 * @param lat 
	 * @see net.okdi.api.service.CourierService#updateOnLineMember(java.lang.Long, java.lang.Double, java.lang.Double) 
	 * @since jdk1.6
	*/
	@Override
	public void updateOnLineMember(Long memberId, Double lng, Double lat) {
    Map <String,Object>map = new HashMap<String,Object>();
    map.put("memberId",memberId );
    map.put("lng",lng );
    map.put("lat", lat);
	courierMapper.updateOnLineMemember(map);
	}

	/**
	 * @Method: deleteOnLineMember 
	 * @Description: TODO
	 * @param memberId 
	 * @see net.okdi.api.service.CourierService#deleteOnLineMember(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public void deleteOnLineMember(Long memberId) {
		ehcacheService.remove("onLineMember", memberId+"");
    Map <String,Object>map = new HashMap <String,Object>();
    map.put("memberId", memberId);
    courierMapper.deleteOnLineMember(map);
		
	}

	/**
	 * @Method: queryNearComp 
	 * @Description: TODO
	 * @param lng
	 * @param lat
	 * @param recTownId
	 * @return 
	 * @see net.okdi.api.service.CourierService#queryNearComp(java.lang.Double, java.lang.Double, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public  Map<String,Object>  queryNearComp(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId) {
       List <Map<String,Object>> list = getCompList(lng, lat,sendProvince,recProvince,netId);
       
       List <Map<String,Object>> netList = getCompList(lng, lat,sendProvince,recProvince,null);
       List<Map<String,Object>> tempListA = new ArrayList<Map<String,Object>>();
       List<Map<String,Object>> tempListB = new ArrayList<Map<String,Object>>();
       Iterator<Map<String,Object>> it = list.iterator();
       Iterator<Map<String,Object>> netIt = netList.iterator();
       Map <String,String>netMap = new HashMap<String,String>();
       while(it.hasNext()){
    	  Map <String,Object>map = it.next();
    	  Long compId = Long.parseLong(map.get("compId").toString());
    	  String compIds = map.get("compId").toString();
    	  BasCompInfo	compInfo = this.ehcacheService.get("compCache", compIds, BasCompInfo.class);
    	  if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
  			compInfo = this.compInfoMapper.findById(compId);
  		 }
  		  if(compInfo == null){
  			  it.remove();
  			  continue;
  		  }
  		if(!("1".equals(compInfo.getCompStatus().toString()))){
			  it.remove();
			  continue;
		  }
  		  BasNetInfo netInfo = this.ehcacheService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
		  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
			netInfo = this.netInfoMapper.findById(compInfo.getBelongToNetId());
	     }
		  map.put("compAddressInfo",compInfo.getCompAddress()==null?"":compInfo.getCompAddress().replace("|", " "));
          map.put("compMobile", compInfo.getCompMobile()==null?" ":compInfo.getCompMobile());
          map.put("compName", compInfo.getCompName()==null?" ":compInfo.getCompName());
    	  map.put("compPhone", compInfo.getCompTelephone()==null?" ":compInfo.getCompTelephone());
    	  map.put("firstFreight", map.get("price") == null? "-":map.get("price").toString());
          map.put("continueFreight", map.get("price") == null?"-":Long.parseLong(map.get("price").toString())/2);
          map.put("firstWeight", "1.00");
          map.put("netName", netInfo.getNetName()==null?" ":netInfo.getNetName());
          map.put("netPhone", netInfo.getTelephone()==null?"":netInfo.getTelephone());  
          map.put("totalPrice", map.get("price")==null?"-":getPrice(Long.parseLong(map.get("price").toString()), weight));  
          map.put("userPhone", map.get("responsibleTelephone"));
          map.put("userName", map.get("responsible"));
          map.put("userMobile", " ");
          map.put("distance", this.getDistance(lat, lng,compInfo.getLatitude()==null?999:compInfo.getLongitude().doubleValue() , compInfo.getLongitude()==null?999:compInfo.getLongitude().doubleValue())/1000); 
          map.put("netImage", "http://www.okdi.net/nfs_data/comp/"+netInfo.getNetId()+".png");
          map.put("cooperationLogo", !compInfo.getCompTypeNum().equals("1003")?"1":"0" );
         if(map.get("cooperationLogo").toString().equals("1")){
        	 tempListA.add(map);
         } else{
        	 tempListB.add(map);
         }
       }
       while(netIt.hasNext()){
    	   Map <String,Object>map = netIt.next();
     	   BasNetInfo netInfo = this.ehcacheService.get("netCache",map.get("netId").toString(), BasNetInfo.class);
     	  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
  			netInfo = this.netInfoMapper.findById(Long.parseLong(map.get("netId").toString()));
  	     }
     	   if(netInfo == null)continue;
     	   netMap.put(netInfo.getNetId()+"", netInfo.getNetName());
       }
       Collections.sort(tempListA, new Comparator<Object>() {  
           public int compare(Object a, Object b) {
        	   double one = Double.parseDouble(((Map<String,Object>)a).get("distance").toString());  
        	   double two = Double.parseDouble(((Map<String,Object>)b).get("distance").toString());   
             return (int) (one-two)  ;   
           }  
        });
       Collections.sort(tempListB, new Comparator<Object>() {  
           public int compare(Object a, Object b) {
        	   double one = Double.parseDouble(((Map<String,Object>)a).get("distance").toString());  
        	   double two = Double.parseDouble(((Map<String,Object>)b).get("distance").toString());   
             return (int) (one-two)  ;   
           }  
        });
       list.clear();
       list.addAll(tempListA);
       list.addAll(tempListB);
       for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
       {
           for (int j = list.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
           {
        	   Map map = list.get(i);
        	   Map map2 = list.get(j);
               if ( map.get("compId").equals(map2.get("compId")))
               {
                   list.remove(j);
               }
           }
       }
       Map <String,Object>recmap = new HashMap<String,Object>();
       recmap.put("compNets", netMap);
       recmap.put("expCompList", list);
       return recmap;
       
	}
    
	private List<Map<String,Object>> getCompList(Double lng, Double lat,Long takeProvinceId,Long sendProvinceId,Long netId){
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("lat", lat);
		map.put("lng", lng);
		map.put("takeProvinceId", takeProvinceId);
		map.put("sendProvinceId", sendProvinceId);
		map.put("netId", netId);
		List<Map<String,Object>> list = courierMapper.queryNearComp(map);
		List<Map<String,Object>> compList = new ArrayList<Map<String,Object>>();
		Iterator<Map<String, Object>> it = list.iterator();
		while(it.hasNext()){
			Map <String,Object>data = it.next();
			if(ElectronicFenceUtil.pointInPolygon(lng, lat, data.get("lngs").toString(), data.get("lats").toString())){
				compList.add(data);
			};
		}
		return compList;
		
	}
    public String getPrice(Long fristPrice,Double weight){
        StringBuffer sb = new StringBuffer("");
    	if(weight>1){
        if(weight%1 == 0){
    		sb.append((fristPrice/2)*(weight-1)+fristPrice);
    	} else{
    		sb.append(fristPrice+fristPrice/2*(Math.ceil(weight)-1));
    	}
    	}else{
    		sb.append(fristPrice);
    	}
    	return sb.toString();
    }
    
    private String jsonSuccess(Object map) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != map) {
			allMap.put("data", map);
		}
		return JSON.toJSONString(allMap);
	}

	/**
	 * @Method: queryNearCompAndMember 
	 * @Description: TODO
	 * @param lng
	 * @param lat
	 * @param recProvince
	 * @param sendProvince
	 * @param weight
	 * @param netId
	 * @return 
	 * @see net.okdi.api.service.CourierService#queryNearCompAndMember(java.lang.Double, java.lang.Double, java.lang.Long, java.lang.Long, java.lang.Double, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String queryNearCompAndMember(Double lng, Double lat,
			Long recProvince, Long sendProvince, Double weight, Long netId,Long sendTownId,Long streetId) {
	    	String compKey = "comp_"+lng+"_"+lat+"_"+recProvince+"_"+sendProvince+"_"+weight+"_"+netId+"_"+sendTownId+"_"+streetId;
	    	String personKey = "person_"+lng+"_"+lat+"_"+recProvince+"_"+sendProvince+"_"+weight+"_"+netId+"_"+sendTownId+"_"+streetId;
		    String compResult = ehcacheService.get("nearCompAndPersionCache", compKey,String.class);
		    String personResult = ehcacheService.get("nearCompAndPersionCache", personKey,String.class);
		    if(!PubMethod.isEmpty(compResult)){
		    	Map <String,Object>map = (Map<String, Object>) JSONObject.parse(compResult);
		    	Map <String,Object> compNets =  (Map<String, Object>) JSON.parse(map.get("compNets").toString());
		    	List<Map> expCompList = JSON.parseArray(map.get("expCompList").toString(),Map.class);
		    	map.put("compNets",compNets);
		    	map.put("expCompList",expCompList );
		    	if(!PubMethod.isEmpty(personResult) &&!"[]".equals(personResult)){
		    		map.put("memberList",JSON.parseArray(personResult,Map.class));
		    	}
		    	else{
		    		map.put("memberList", this.queryNearMember(lng, lat, sendTownId, streetId, netId, 1, 5));
		    	}
		    	return jsonSuccess(map);
		    }else{
		    	 Map<String,Object> map = this.queryNearComp(lng, lat, recProvince, sendProvince, weight, netId,5l);
		    	 ehcacheService.put("nearCompAndPersionCache", compKey, JSON.toJSONString(map));
		    	 if(!PubMethod.isEmpty(personResult)){
		    		map.put("memberList", personResult);
			    	}
			    	else{
			    		List<Map<String,Object>>list = this.queryNearMember(lng, lat, sendTownId, streetId, netId, 1, 5);
			    	map.put("memberList",list );
			    	ehcacheService.put("nearCompAndPersionCache", personKey, JSON.toJSONString(list));
			    	}
		    	 System.out.println(jsonSuccess(compResult));
		    	 return jsonSuccess(map);
		    }
//		    Map<String,Object> map = this.queryNearComp(lng, lat, recProvince, sendProvince, weight, netId);
//		    map.put("memberList",  this.queryNearMember(lng, lat, sendTownId, streetId, netId, 1, 5));                   
//	        ehcacheService.put("nearCompAndPersionCache", key, jsonSuccess(map));
//	        return jsonSuccess(map);
	}

	/**
	 * @Method: queryNearComp 
	 * @param lng
	 * @param lat
	 * @param recProvince
	 * @param sendProvince
	 * @param weight
	 * @param netId
	 * @param distance
	 * @return 
	 * @see net.okdi.api.service.CourierService#queryNearComp(java.lang.Double, java.lang.Double, java.lang.Long, java.lang.Long, java.lang.Double, java.lang.Long, java.lang.Long) 
	*/
	@Override
	public Map<String, Object> queryNearComp(Double lng, Double lat,
			Long recProvince, Long sendProvince, Double weight, Long netId,
			Long distance) {
		    List <Map<String,Object>> list = getCompList(lng, lat,sendProvince,recProvince,netId);
	       
	       List <Map<String,Object>> netList = getCompList(lng, lat,sendProvince,recProvince,null);
	       List<Map<String,Object>> tempListA = new ArrayList<Map<String,Object>>();
	       List<Map<String,Object>> tempListB = new ArrayList<Map<String,Object>>();
	       Iterator<Map<String,Object>> it = list.iterator();
	       Iterator<Map<String,Object>> netIt = netList.iterator();
	       Map <String,String>netMap = new HashMap<String,String>();
	       while(it.hasNext()){
	    	  Map <String,Object>map = it.next();
	    	  Long compId = Long.parseLong(map.get("compId").toString());
	    	  String compIds = map.get("compId").toString();
	    	  BasCompInfo	compInfo = this.ehcacheService.get("compCache", compIds, BasCompInfo.class);
	    	  if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
	  			compInfo = this.compInfoMapper.findById(compId);
	  		 }
	  		  if(compInfo == null){
	  			  it.remove();
	  			  continue;
	  		  }
	  		if(!("1".equals(compInfo.getCompStatus().toString()))){
				  it.remove();
				  continue;
			  }
	  		  BasNetInfo netInfo = this.ehcacheService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
			  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
				netInfo = this.netInfoMapper.findById(compInfo.getBelongToNetId());
		     }
			  double distance2 = this.getDistance(lat, lng,compInfo.getLatitude()==null?999:compInfo.getLatitude().doubleValue() , compInfo.getLongitude()==null?999:compInfo.getLongitude().doubleValue());
			  boolean distanceFlag = distance2 -1000>0;
			  map.put("compAddressInfo",compInfo.getCompAddress()==null?"":compInfo.getCompAddress().replace("|", " "));
	          map.put("compMobile", compInfo.getCompMobile()==null?" ":compInfo.getCompMobile());
	          map.put("compName", compInfo.getCompName()==null?" ":compInfo.getCompName());
	    	  map.put("compPhone", compInfo.getCompTelephone()==null?" ":compInfo.getCompTelephone());
	    	  map.put("firstFreight", map.get("price") == null? "-":map.get("price").toString());
	          map.put("continueFreight", map.get("price") == null?"-":Long.parseLong(map.get("price").toString())/2);
	          map.put("firstWeight", "1.00");
	          map.put("netName", netInfo.getNetName()==null?" ":netInfo.getNetName());
	          map.put("netPhone", netInfo.getTelephone()==null?"":netInfo.getTelephone());  
	          map.put("totalPrice", map.get("price")==null?"-":getPrice(Long.parseLong(map.get("price").toString()), weight));  
	          map.put("userPhone", map.get("responsibleTelephone"));
	          map.put("userName", map.get("responsible"));
	          map.put("userMobile", " ");
	          map.put("distance", distance2/1000);
	          if (distanceFlag) {
	        	  map.put("distanceNew",distance2/1000 );
	        	  map.put("unit", "km");
			}else{
				  map.put("distanceNew", distance2);
				  map.put("unit", "m");
			}
	          map.put("netImage", "http://www.okdi.net/nfs_data/comp/"+netInfo.getNetId()+".png");
	          map.put("cooperationLogo", !compInfo.getCompTypeNum().equals("1003")?"1":"0" );
	         if(map.get("cooperationLogo").toString().equals("1")){
	        	 tempListA.add(map);
	         } else{
	        	 tempListB.add(map);
	         }
	       }
	       while(netIt.hasNext()){
	    	   Map <String,Object>map = netIt.next();
	     	   BasNetInfo netInfo = this.ehcacheService.get("netCache",map.get("netId").toString(), BasNetInfo.class);
	     	  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
	  			netInfo = this.netInfoMapper.findById(Long.parseLong(map.get("netId").toString()));
	  	     }
	     	   if(netInfo == null)continue;
	     	   netMap.put(netInfo.getNetId()+"", netInfo.getNetName());
	       }
	       Collections.sort(tempListA, new Comparator<Object>() {  
	           public int compare(Object a, Object b) {
	        	   double one = Double.parseDouble(((Map<String,Object>)a).get("distance").toString())*1000;  
	        	   double two = Double.parseDouble(((Map<String,Object>)b).get("distance").toString())*1000;   
	             return (int) (one-two)  ;   
	           }  
	        });
	       Collections.sort(tempListB, new Comparator<Object>() {  
	           public int compare(Object a, Object b) {
	        	   double one = Double.parseDouble(((Map<String,Object>)a).get("distance").toString())*1000;  
	        	   double two = Double.parseDouble(((Map<String,Object>)b).get("distance").toString())*1000;   
	             return (int) (one-two)  ;   
	           }  
	        });
	       list.clear();
	       list.addAll(tempListA);
	       list.addAll(tempListB);
	       for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
	       {
	           for (int j = list.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
	           {
	        	   Map map = list.get(i);
	        	   Map map2 = list.get(j);
	               if ( map.get("compId").equals(map2.get("compId")))
	               {
	                   list.remove(j);
	               }
	           }
	       }
	       List <Map<String,Object>> list2 = new ArrayList<Map<String,Object>>(); 
           for (int i = 0; i < list.size(); i++){
        	   if(list.get(i).get("compId").toString().equals("123791997190144")){
        		   System.out.println(11);
        	   }
	    	   if(((Double.parseDouble(list.get(i).get("distance").toString())))<distance){
	    		   list2.add(list.get(i));
	    	   }
	       }
	       Map <String,Object>recmap = new HashMap<String,Object>();
	       recmap.put("compNets", netMap);
	       recmap.put("expCompList", list2);
	       return recmap;
	}
//	public static void main(String[] args) {
//	System.out.println( DistanceUtil.getDistance(34.367127, 107.284911, 38.044638, 114.509506));;
//	}

	/**
	 * @Method: queryNearMemberRob 
	 * @param lng
	 * @param lat
	 * @param townId
	 * @param streetId
	 * @param netId
	 * @param sortFlag
	 * @param howFast
	 * @return 
	 * @see net.okdi.api.service.CourierService#queryNearMemberRob(double, double, java.lang.Long, java.lang.Long, java.lang.Long, int, int) 
	*/
	@Override
	public List<Map<String, Object>> queryNearMemberRob(double lng, double lat,
			Long townId, Long streetId, Long netId, int sortFlag, int howFast) {
			//	this.saveOnLineMember(1500l, "D速物流", 5981l, "广西北海公司", 123l, "张梦楠", "13161340623", 116.257164, 39.930728, "");
				int dis=howFast;//设置5公里范围内
				double EARTH_RADIUS = 6378.137 * 1000;
				double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(lat)));
				dlng = dlng*180.0/Math.PI;        //弧度转换成角度
				double dlat = Math.abs(dis*1000 / EARTH_RADIUS);
				dlat = dlat*180.0/Math.PI;     //弧度转换成角度
				double bottomLat=lat - dlat;
				double topLat=lat + dlat;
				double leftLng=lng - dlng;
				double rightLng=lng + dlng;
				Map <String,Object>params = new HashMap<String,Object>();
				params.put("bottomLat", bottomLat);
				params.put("topLat", topLat);
				params.put("leftLng", leftLng);
				params.put("rightLng", rightLng);
				params.put("townId", townId);
				params.put("streetId", streetId);
				params.put("netId", netId);
				params.put("sortFlag", sortFlag);
				List <Map>memberList= ehcacheService.getAll("onLineMember", Map.class);//this.courierMapper.queryNearMember(params);
				List <Map<String,Object>>newMemberList=new ArrayList<Map<String,Object>>();
				if(memberList==null||memberList.size()==0){
					return newMemberList;
				}
				for(int i=0;i<memberList.size();i++){
					Map <String,Object>map=(Map<String,Object>)memberList.get(i);
					if((new Date().getTime()-Long.parseLong(map.get("createTime").toString()))>1860000){continue;}
					double distance=this.getDistance(lat, lng, Double.parseDouble(map.get("lat").toString()), Double.parseDouble(map.get("lng").toString()))/1000;
					if(distance<=dis){
						map.put("distance", distance);
						String compId = map.get("compId").toString();
							BasCompInfo	compInfo = this.ehcacheService.get("compCache", compId, BasCompInfo.class);
				  		  if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
				  			compInfo = this.compInfoMapper.findById(Long.parseLong(compId));
				  		 }
							map.put("compTel", (compInfo ==null || compInfo.getCompTel()==null)?" ":compInfo.getCompTel());
						    map.put("headImg", readPath+"/"+map.get("memberId")+".jpg");
							map.put("approveFlag", map.get("flag")==null?"0":"1");
							//验证归属认证
//							Map<String,Object> memberMap = memberInfoService.getValidationStatus(Long.parseLong(map.get("memberId").toString()));
//							
//							if(memberMap!=null&&"1".equals(String.valueOf(memberMap.get("relationFlag")))){
//								map.put("veriFlag", 1);
//							}
//							else{
//								continue;
//							}
							map.put("veriFlag", 1);
							newMemberList.add(map);
					}
				}
				if(sortFlag==0){
				Collections.sort(newMemberList, new ComparatorList());
				}else if(sortFlag==1){
					Collections.sort(newMemberList,Collections.reverseOrder(new ComparatorList()));
				}
				return newMemberList;
	}
	
	public String getAllOnlineMember(){
		List <Map>memberList= ehcacheService.getAll("onLineMember", Map.class);
			return jsonSuccess(memberList);
	}

}
