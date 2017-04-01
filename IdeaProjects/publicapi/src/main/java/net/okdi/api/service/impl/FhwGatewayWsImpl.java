/*
 * 文 件 名:  FhwGatewayWsImpl.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  文超
 * 创建时间:  2013-12-26 下午5:16:26  
 * 
 * 修改内容:  <修改内容>
 * 修改时间:  <修改时间>
 * 修改人:    <修改人>
 */
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.FhwGatewayWs;
import net.okdi.api.vo.DeliveryRange;
import net.okdi.api.vo.Parcel;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.CompInfoHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;


/**
 * <功能详细描述>
 * 
 * @author  文超
 * @version  [版本号, 2013-12-26 下午5:16:26 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("fhwGatewayWsImpl")
public class FhwGatewayWsImpl implements FhwGatewayWs {
	


	@Autowired
	private CompInfoHttpClient compInfoHttpClient;
	/**
	 * 
	 * 功能描述: 查询快递网络某级地址下覆盖超区地址信息
	 * 创建人:  翟士贺
	 * 创建时间：Dec 28, 2013 4:16:39 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 28, 2013 4:16:39 PM
	 * 修改备注：
	 * @param netId 快递网络ID，必填
	 * @param addressId 上级地址ID
	 * @return DeliveryRange
	 *
	 */
	@Override
	public DeliveryRange getCoverExceedAddress(Long netId,Long addressId){
		String result=this.compInfoHttpClient.getCoverExceedAddress(netId, addressId);
		if(!PubMethod.isEmpty(result)){
			Map<String,Object> temp=JSON.parseObject(result, Map.class);
			Map<String,List<Map>> map=(Map<String,List<Map>>)temp.get("data");
			DeliveryRange deliveryRange=new DeliveryRange(map.get("coverAddress"),map.get("exceedAddress"));
			return deliveryRange;
		}else{
			return null;
		}
	}
	/**
	 * 根据地址的经纬度获取网络下的站点信息
	 * @Method: getExpSites 
	 * @param longitude(double) 地址的经度，必填
	 * @param latitude(double) 地址的纬度，必填
	 * @param netId(Long) 网络ID，非必填
	 * @return 
	 * @see net.okdi.api.service.CompInfoService#getExpSites(double, double, java.lang.Long) 
	*/
	@Override
	public String getExpSites(double longitude, double latitude, Long netId) {
		if (PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)) {
			return PubMethod.paramsFailure();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String result=this.compInfoHttpClient.getExpSitesToEc(longitude, latitude, netId);
			if(PubMethod.isEmpty(result)){
				data.put("netList","");
				data.put("expCompList","");
				return PubMethod.jsonSuccess(data);
			}else{
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}


	/**
	 * 根据网络ID和取件省份ID获取网络报价（取件身份指的是收派员取件地址）
	 * @Method: getNetQuote 
	 * @param netId(Long) 网络ID，必填
	 * @param provinceId(Long) 取件省份ID，必填
	 * @return 
	 * @see net.okdi.api.service.CompInfoService#getNetQuote(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public String getNetQuote(Long netId, Long provinceId) {
		if (PubMethod.isEmpty(netId) || PubMethod.isEmpty(provinceId)) {
			return PubMethod.paramsFailure();
		}
		try {
			String result=this.compInfoHttpClient.getNetQuote(netId, provinceId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.jsonSuccess(null);
			}else{
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}

	/**
	 * 根据公司ID获取公司派送范围和不派送范围
	 * @Method: getExpSendRange 
	 * @param compId(Long) 公司ID，必填
	 * @return 
	 * @see net.okdi.api.service.CompInfoService#getExpSendRange(java.lang.Long) 
	*/
	@Override
	public String getExpSendRange(Long compId) {
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure();
		}
		Map<String, Object> allMap = new HashMap<String, Object>();
		try {
			String result = this.compInfoHttpClient.getExpSendRange(compId);
			if(PubMethod.isEmpty(result)){
				allMap.put("addressList", "");
				allMap.put("exceedareaList", "");
				return PubMethod.jsonSuccess(allMap);
			}else{
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}


	
	/**
	 * 
	 * 功能描述: 创建包裹
	 * 创建人:  翟士贺
	 * 创建时间：Dec 28, 2013 5:11:34 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 28, 2013 5:11:34 PM
	 * 修改备注：
	 *
	 */
	@Override
	public void createParcels(List<Parcel> parcels){
	}
	/**
	 * 
	 * 功能描述: 选快递是否超区
	 * 创建人:  翟士贺
	 * 创建时间：Dec 28, 2013 5:11:34 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 28, 2013 5:11:34 PM
	 * 修改备注：
	 * @param tradeList 订单信息
	 * @return Map<String,String>
	 *
	 */
	@Override
	public Map<String,String> getNoExceedExp(List<Map> tradeList){
		Map<String,String> map=new HashMap<String,String>();
		 for(Map<String,String> tradeMap:tradeList){
			 String tradeId=tradeMap.get("tradeId").toString();
			 String netIds=tradeMap.get("exp_Net_Ids");
			 if(PubMethod.isEmpty(netIds)){
				 netIds="999,1500,1501,1502,1503,1504";
			 }
			 if(netIds.length()!=0 && netIds.lastIndexOf(",")==netIds.length()-1){
				 netIds=netIds.substring(0,netIds.lastIndexOf(","));
			 }
			 if(netIds.indexOf(",")==0){
				 netIds=netIds.substring(1);
			 }
			 map.put(tradeId,netIds);
		 }
		 return map;
	}
	/**
	 * 
	 * 功能描述: 选快递查询评分
	 * 创建人:  翟士贺
	 * 创建时间：Dec 30, 2013 2:58:23 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 30, 2013 2:58:23 PM
	 * 修改备注：
	 * @param tradeList 订单信息
	 * @return Map<String,Map<String,String>>
	 *
	 */
	@Override
	 public Map<String,Map<String,String>> getExpByScore(List<Map> tradeList){
		 Map<String,Map<String,String>> tradeScore=new HashMap<String,Map<String,String>>();
		 for(Map<String,String> tradeMap:tradeList){
			 Map<String,String> netScore=new HashMap<String,String>();
			 String tradeId=tradeMap.get("tradeId");
			 String expNetIds=tradeMap.get("expNetIds");
			 if(expNetIds.lastIndexOf(",")==expNetIds.length()-1){
				 expNetIds=expNetIds.substring(0,expNetIds.lastIndexOf(","));
			 }
			 if(expNetIds.indexOf(",")==0){
				 expNetIds=expNetIds.substring(1);
			 }
			 for(String netId:expNetIds.split(",")){
				 if(!netScore.containsKey(netId)){
					 netScore.put(netId, "3,3");
				 }
			 }
			 tradeScore.put(tradeId, netScore);
		 }
		 return tradeScore;
	}
	/**
	 * 
	 * 功能描述: 选快递查询最近快递
	 * 创建人:  翟士贺
	 * 创建时间：Dec 28, 2013 5:12:24 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 28, 2013 5:12:24 PM
	 * 修改备注：
	 * @param tradeList 订单信息
	 * @param siteMap 快递站点信息
	 * @return Map<String,String>
	 *
	 */
	@Override
	public Map<String,String> getNearestExp(List<Map> tradeList,Map<String,Map<String,String>> siteMap){
		Map<String,String> siteDistance=new HashMap<String,String>();
		for(Map<String,String> tradeMap:tradeList){
			 String tradeId=tradeMap.get("tradeId");
			 String netSite=null;  
			 //不存在站点 返回一个快递网络
			 if(PubMethod.isEmpty(netSite)){
				 for(Map.Entry<String, String> entry:siteMap.get(tradeId).entrySet()){
						 netSite=entry.getKey()+"-"+entry.getValue();
						 break;
				 }
			 }
			 siteDistance.put(tradeId, netSite);
		}
		return siteDistance;
	}

	/**
	 * 
	 * 功能描述: 查询附近快递
	 * 创建人:  翟士贺
	 * 创建时间：Dec 31, 2013 3:20:33 PM
	 * 修改人：翟士贺
	 * 修改时间：Dec 31, 2013 3:20:33 PM
	 * 修改备注：
	 * @param pageNo 跳转页码，非必填，正整数，默认值1
	 * @param pageSize 分页大小，非必填，正整数，默认值1
	 * @param lng 地址经度，必填
	 * @param lat 地址纬度，必填
	 * @param netId 网络ID，非必填
	 * @param cityId 地址城市ID，非必填
	 * @param townId 地址乡镇ID，必填
	 * @param streetId 地址街道ID，必填
	 * @param distance 范围距离 km
	 * @return List<Map>
	 *
	 */
	@Override
	public DeliveryRange getNearComp(int pageNo,int pageSize,double lng,double lat,Long netId,Long cityId,Long townId,Long streetId,short distance){
		DeliveryRange deliveryRange=new DeliveryRange(new ArrayList<Map>(),0,0);
		return deliveryRange;
	}
	/**
	 * 根据手机号查询收派员信息 
	 * @param phone  手机号（不可为空）
	 * @param flag　　0 表示　拥有此手机号的手派员　１表示拥有此手机号的可用收派员(状态为　待审核（不包含二次待审核）＼审核通过　在职　可接单的)
	 * @return Map<String,Object>
	 *  key="compId" value=公司ID
	 *  key="compName"  value=公司名称
	 *  key="netId"   value=网络ID
	 *  key="netName" value=网络名称
	 *  key="coopNetIdStr" value=合作网络ID字符串 1,2,4
	 *  key="coopNetName"  value=合作网络名称 顺风,宅急送,天天
	 *  key="compAddress"  value="省市区镇+详细"
	 *  key="compTelPhone" value="公司座机"
	 *  key="compMobile"   value="公司手机号"
	 *  key="longitude"    value="经度"
	 *  key="latitude"     value="纬度"
	 *  key="registFlag"    value=收派员注册  0 未注册 1注册
	 *  key="compRegistFlag"   value=公司注册标识 0 未注册 1注册
	 *  key="deliverId"    value=收派员ID
	 *  key="deliverName"  value=收派员姓名
	 *  key="deliverStatus"  value=收派员状态 -1创建 0待审核 1审核通过 2审核不通过 4二次审核
	 *  key="jobFlag"      value=在职标识 1 在职 2离职
	 *  key="workStatusFlag"  value=工作状态　1在岗　2下班　3休假　4任务已满，暂不接单
		key="receivingFlag" value=是否可接单　0　不可接单　1可接单
	 *  
	 */
	@Override
	public List<Map> getDeliverInfo(String phone,Short flag) {
		try {
			List<Map> tempList=null;
			String result = this.compInfoHttpClient.queryMemberInfoForFhw(phone);
			if(!PubMethod.isEmpty(result)){
				Map<String,Object> temp=JSON.parseObject(result, Map.class);
				tempList=(List<Map>)temp.get("data");
			}
			return tempList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据站点ID查询站点下可用收派员
	 * @param compId
	 * @return
	 * @return List<Map<String,Object>>
	 *  key="compId" value=公司ID
	 *  key="compName"  value=公司名称
	 *  key="netId"   value=网络ID
	 *  key="netName" value=网络名称
	 *  key="compAddress"  value="省市区镇+详细"
	 *  key="compTelPhone" value="公司座机"
	 *  key="compMobile"   value="公司手机号"
	 *  key="longitude"    value="经度"
	 *  key="latitude"     value="纬度"
	 *  key="compRegistFlag"   value=注册标识 0 未注册 1注册
	 *  key="deliverId"    value=收派员ID
	 *  key="deliverName"  value=收派员姓名
	 *  key="deliverStatus"  value=收派员状态 -1创建 0待审核 1审核通过 2审核不通过 4二次审核
	 *  key="jobFlag"      value=在职标识 1 在职 2离职
	 *  key="workStatusFlag"  value=工作状态　1在岗　2下班　3休假　4任务已满，暂不接单
		key="receivingFlag" value=是否可接单　0　不可接单　1可接单
	 *  key="registFlag" value="收派员注册标识"0 未注册 1注册
	 *  key="deliverPhone" value=收派员电话
	 */
	@Override
	public List<Map> getCompDeliverList(Long compId) {
		return null;
	}
}
