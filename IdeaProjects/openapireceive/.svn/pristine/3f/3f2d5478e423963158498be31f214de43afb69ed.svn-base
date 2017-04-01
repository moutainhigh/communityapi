/**  
 * @Project: openapi
 * @Title: CountNumServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-4-28 下午6:32:11
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.service.CountNumService;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class CountNumServiceImpl implements CountNumService {
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private NoticeHttpClient noticeHttpClient;
	
	/**
	 * 统计站点、收派员、好递超市、好递生活注册数量
	 * @param countType 1：统计总数 2：当前月注册总数 3：当前月每日注册总数
	 * @return
	 */
	@Override
	public Object getRegisterNum(Integer countType) {
		/**统计总数**/
		if(countType==1){
			return getTotalNum(countType);
		}
		/**当前月注册总数**/
		if(countType==2){
			return getCurrentMonthNum(countType);
		}
		/**当前月每日注册总数**/
		if(countType==3){
			return getMonthOfDaysNum(countType);
		}
		return "countType is error";
	}
	
	/**
	 * 统计总数
	 * @param countType 1：统计总数 
	 * @return
	 */
	private Map<String,Object> getTotalNum(Integer countType){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		//站点
		int stationTotalNumber=this.basCompInfoMapper.getTotalNum();
		dataMap.put("stationTotalNumber", stationTotalNumber);
		//收派员
		int deliveryTotalNumber=this.basEmployeeRelationMapper.getTotalNum();
		dataMap.put("deliveryTotalNumber", deliveryTotalNumber);
		//好递生活
		int personalTotalNumber=0;
		String result=this.noticeHttpClient.getLifeAndShopNum(countType);
		String personData=String.valueOf(JSON.parseObject(result).get("personalTotalNumber"));
		if(!PubMethod.isEmpty(personData)&&!"null".equals(personData)){
			personalTotalNumber=Integer.valueOf(personData);
		}
		dataMap.put("personalTotalNumber", personalTotalNumber);
		//好递超市
		int shopTotalNumber=0;
		String data=this.noticeHttpClient.getShopNum(countType);
		String shopData=String.valueOf(JSON.parseObject(data).get("shopTotalNumber"));
		if(!PubMethod.isEmpty(shopData)&&!"null".equals(shopData)){
			shopTotalNumber=Integer.valueOf(shopData);
		}
		dataMap.put("shopTotalNumber", shopTotalNumber);
		return dataMap;
	}
	
	/**
	 * 当前月注册总数
	 * @param countType 2：当前月注册总数 
	 * @return
	 */
	private Map<String,Object> getCurrentMonthNum(Integer countType){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		//站点
		int stationTotalNumber=this.basCompInfoMapper.getCurrentMonthNum();
		dataMap.put("stationTotalNumber", stationTotalNumber);
		//收派员
		int deliveryTotalNumber=this.basEmployeeRelationMapper.getCurrentMonthNum();
		dataMap.put("deliveryTotalNumber", deliveryTotalNumber);
		//好递生活
		int personalTotalNumber=0;
		String result=this.noticeHttpClient.getLifeAndShopNum(countType);
		String personData=String.valueOf(JSON.parseObject(result).get("personalTotalNumber"));
		if(!PubMethod.isEmpty(personData)&&!"null".equals(personData)){
			personalTotalNumber=Integer.valueOf(personData);
		}
		dataMap.put("personalTotalNumber", personalTotalNumber);
		//好递超市
		int shopTotalNumber=0;
		String data=this.noticeHttpClient.getShopNum(countType);
		String shopData=String.valueOf(JSON.parseObject(data).get("shopTotalNumber"));
		if(!PubMethod.isEmpty(shopData)&&!"null".equals(shopData)){
			shopTotalNumber=Integer.valueOf(shopData);
		}
		dataMap.put("shopTotalNumber", shopTotalNumber);
		dataMap.put("time", DateUtil.getNowTime("yyyy-M"));
		return dataMap;
	}
	
	/**
	 * 当前月每日注册总数
	 * @param countType 3：当前月每日注册总数
	 * @return
	 */
	private List<Map> getMonthOfDaysNum(Integer countType){
		//站点
		List<Map<String, Object>> stationList=this.basCompInfoMapper.getMonthOfDaysNum();
		//收派员
		List<Map<String, Object>> deliverList=this.basEmployeeRelationMapper.getMonthOfDaysNum();
		//好递生活
		List<Map> personalList=new ArrayList<Map>();
		String result=this.noticeHttpClient.getLifeAndShopNum(countType);
		if(!PubMethod.isEmpty(result)&&!"null".equals(result)){
			personalList=JSON.parseArray(JSON.parseObject(result).get("personalTotalNumber").toString(),java.util.Map.class);
		}
		//好递超市
		List<Map> shopList=new ArrayList<Map>();
		String data=this.noticeHttpClient.getShopNum(countType);
		if(!PubMethod.isEmpty(data)&&!"null".equals(result)){
			shopList=JSON.parseArray(JSON.parseObject(data).get("shopTotalNumber").toString(),java.util.Map.class);
		}
		return this.monthAssembleData(stationList, deliverList, shopList, personalList);
	}
	
	/**
	 * 组装每月每日注册数JSON
	 * @param stationList 站点注册LIST
	 * @param deliverList 收派员注册LIST
	 * @param shopList 好递超市注册LIST
	 * @param personalList 好递生活注册LIST
	 * @return
	 */
	private List<Map> monthAssembleData(List<Map<String, Object>> stationList,
			List<Map<String, Object>> deliverList,List<Map> shopList,List<Map> personalList) {
		//当前月份
		String currentMonth=DateUtil.getNowTime("yyyy-M");
		List<Map> templist = new ArrayList<Map>();
		Map<String, Object> map = null;
		int days;
		//添加数据
		for(int i = 1; i <= Calendar.getInstance().get(Calendar.DATE); i++) {
			map = new HashMap<String, Object>();
			//添加站点注册数量
			if(stationList!=null&&stationList.size()>0) {
				for(Map<String,Object> m : stationList) {
					days = (Integer) m.get("days");
					if(i==days){
						map.put("stationTotalNumber", m.get("total"));
						break;
					}else{
						map.put("stationTotalNumber", 0);
					}
				}
			}else{
				map.put("stationTotalNumber", 0);
			}
			//添加收派员注册数量
			if(deliverList!=null&&deliverList.size()>0) {
				for(Map<String,Object> m : deliverList) {
					days = (Integer) m.get("days");
					if(i==days){
						map.put("deliveryTotalNumber", m.get("total"));
						break;
					}else{
						map.put("deliveryTotalNumber", 0);
					}
				}
			}else{
				map.put("deliveryTotalNumber", 0);
			}
			//添加好递超市注册数量
			if(shopList!=null&&shopList.size()>0) {
				for(Map m : shopList) {
					days = (Integer) m.get("days");
					if(i==days){
						map.put("shopTotalNumber", m.get("total"));
						break;
					}else{
						map.put("shopTotalNumber", 0);
					}
				}
			}else{
				map.put("shopTotalNumber", 0);
			}
			//添加好递生活注册数量
			if(personalList!=null&&personalList.size()>0) {
				for(Map m : personalList) {
					days = (Integer) m.get("days");
					if(i==days){
						map.put("personalTotalNumber", m.get("total"));
						break;
					}else{
						map.put("personalTotalNumber", 0);
					}
				}
			}else{
				map.put("personalTotalNumber", 0);
			}
			map.put("time", currentMonth+"-"+i);
			templist.add(map);
		}
		return templist;
	}
	
}