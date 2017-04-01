/*
 * 文 件 名:  FhwGatewayWs.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  文超
 * 创建时间:  2013-12-26 下午5:13:58  
 * 
 * 修改内容:  <修改内容>
 * 修改时间:  <修改时间>
 * 修改人:    <修改人>
 */
package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.okdi.api.adapter.MapAdapter;
import net.okdi.api.adapter.MapMapAdapter;
import net.okdi.api.vo.DeliveryRange;
import net.okdi.api.vo.Parcel;




/**
 * 电商管家相关接口
 * @author shihe.zhai
 * @version V1.0
 */
@WebService(serviceName="FhwGatewayWs",targetNamespace="http://abc.com")
public interface FhwGatewayWs {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询快递网络某级地址下覆盖超区地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-20 下午1:02:35</dd>
	 * @param netId 快递网络ID，必填
	 * @param addressId 上级地址ID
	 * @return DeliveryRange
	 * @since v1.0
	 */
	@WebMethod
	public DeliveryRange getCoverExceedAddress(@WebParam(name="netId")Long netId,@WebParam(name="addressId")Long addressId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>创建包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午2:59:11</dd>
	 * @param parcels 包裹列表信息
	 * @since v1.0
	 */
	@WebMethod
	public void createParcels(@WebParam(name="parcels")List<Parcel> parcels);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>选快递是否超区</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午2:59:49</dd>
	 * @param tradeList 订单信息
	 * @return Map<String,String>
	 * @since v1.0
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)  
	public Map<String,String> getNoExceedExp(@WebParam(name="tradeList")@XmlJavaTypeAdapter(MapAdapter.class)List<Map> tradeList);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>选快递查询评分</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午3:00:19</dd>
	 * @param tradeList 订单信息
	 * @return Map<String,Map<String,String>>
	 * @since v1.0
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapMapAdapter.class)  
	 public Map<String,Map<String,String>> getExpByScore(@WebParam(name="tradeList")@XmlJavaTypeAdapter(MapAdapter.class)List<Map> tradeList);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>选快递查询最近快递</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午3:00:42</dd>
	 * @param tradeList 订单信息
	 * @param siteMap 快递站点信息
	 * @return Map<String,String>
	 * @since v1.0
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)  
	public Map<String,String> getNearestExp(@WebParam(name="tradeList")@XmlJavaTypeAdapter(MapAdapter.class)List<Map> tradeList,@WebParam(name="siteMap")@XmlJavaTypeAdapter(MapMapAdapter.class)Map<String,Map<String,String>> siteMap);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询附近快递</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午3:01:45</dd>
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
	 * @since v1.0
	 */
	@WebMethod
	public DeliveryRange getNearComp(@WebParam(name="pageNo")int pageNo,@WebParam(name="pageSize")int pageSize,@WebParam(name="lng")double lng,@WebParam(name="lat")double lat,@WebParam(name="netId")Long netId,@WebParam(name="cityId")Long cityId,@WebParam(name="townId")Long townId,@WebParam(name="streetId")Long streetId,@WebParam(name="distance")short distance);	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据手机号查询收派员信息 </dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午3:02:17</dd>
	 * @param phone  手机号（不可为空）
	 * @param flag　　0 表示　拥有此手机号的手派员　１表示拥有此手机号的可用收派员(状态为　待审核（不包含二次待审核）＼审核通过　在职　可接单的)
	 * @return Map<String,Object>
	 *  key="compId" value=公司ID
	 *  key="compName"  value=公司名称
	 *  key="netId"   value=网络ID
	 *  key="netName" value=网络名称
	 *  key="registFlag"    value=收派员注册  0 未注册 1注册
	 *  key="deliverId"    value=收派员ID
	 *  key="deliverName"  value=收派员姓名
	 *  key="deliverPhone"  value=收派员手机号 
	 *  key="receivingFlag" value=是否可接单　0　不可接单　1可接单
	 * @since v1.0
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)  
	public List<Map> getDeliverInfo(@WebParam(name="phone")String phone,@WebParam(name="flag")Short flag);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据站点ID查询站点下可用收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 下午3:03:18</dd>
	 * @param compId 网点信息
	 * @return List<Map<String,Object>>
	 *  key="compId" value=公司ID
	 *  key="compName"  value=公司名称
	 *  key="netId"   value=网络ID
	 *  key="netName" value=网络名称
	 *  key="deliverId"    value=收派员ID
	 *  key="deliverName"  value=收派员姓名
		key="receivingFlag" value=是否可接单　0　不可接单　1可接单
	 *  key="registFlag" value="收派员注册标识"0 未注册 1注册
	 *  key="deliverPhone" value=收派员电话
	 * @since v1.0
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)  
	public List<Map> getCompDeliverList(@WebParam(name="compId")Long compId);
	
	


	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据地址的经纬度获取网络下的站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:27:54</dd>
	 * @param longitude (double) 地址的经度，必填
	 * @param latitude (double) 地址的纬度，必填
	 * @param netId (Long) 网络ID，非必填
	 * @return {"success": true, "data": { "netList": [ { "id": "1504", "name": "韵达快递", "total": 7 }, { "id": "1524", "name": "UC优速快递", "total": 5 } ],"expCompList": [ { "netId": "公司所在网络ID 1524", "netName": "公司所在网络名称 圆通速递", "netTel": "公司所在网络电话 400-188-8888", "compId": 115244, "compName": "公司名称  北京朝阳区高碑店公司", "compAddress": "公司地址信息 北京市朝阳区高碑店乡小郊亭如家公寓", "compTelephone": "公司电话 15156568953", "compMobile": "公司经理电话 13255552222"}]}}
	 * @since v1.0
	*/
	@WebMethod
	public String getExpSites(@WebParam(name = "longitude") double longitude, @WebParam(name = "latitude") double latitude, @WebParam(name = "netId") Long netId);

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络ID和取件省份ID获取网络报价（取件身份指的是收派员取件地址）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:28:01</dd>
	 * @param netId (Long) 网络ID，必填
	 * @param provinceId (Long) 取件省份ID，必填
	 * @return {"success": true, "data": { "netPriceList": [ { "continueWeight": 5, "firstFreight": 10, "province": "甘肃省" }, { "continueWeight": 61, "firstFreight": 122, "province": "宁夏回族自治区" }]}}
	 * @since v1.0
	*/
	@WebMethod
	public String getNetQuote(@WebParam(name = "netId") Long netId, @WebParam(name = "provinceId") Long provinceId);

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司派送范围和不派送范围</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-17 下午03:28:05</dd>
	 * @param compId (Long) 公司ID，必填
	 * @return {"success": true, "data": { "addressList": [ "回龙观西大街北店时代广场", "朱辛庄319号", "北农路华北电力大学", "龙禧三街骊龙园", "三合庄村马家地", "欧德宝汽车城" ], "exceedareaList": [ "回龙观西大街北店时代广场", "朱辛庄319号", "北农路华北电力大学", "龙禧三街骊龙园", "三合庄村马家地", "欧德宝汽车城" ]}}
	 * @since v1.0
	*/
	@WebMethod
	public String getExpSendRange(@WebParam(name = "compId") Long compId);


}
