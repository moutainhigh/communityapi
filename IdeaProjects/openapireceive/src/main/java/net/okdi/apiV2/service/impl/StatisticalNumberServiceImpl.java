package net.okdi.apiV2.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.okdi.apiV2.dao.BasCompInfoMapperV2;
import net.okdi.apiV2.service.StatisticalNumberService;
import net.okdi.core.sms.AbstractHttpClient;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class StatisticalNumberServiceImpl extends AbstractHttpClient implements StatisticalNumberService {

	
	@Autowired
	private BasCompInfoMapperV2 basCompInfoMapperV2;
	
	@Value("${sms_server}")
	private String SMServer;
	
	@Override
	public List<Map<String,Object>> statisticalNumber() {
		
		//1. 查询bas_cominfo 循环得出 省份, 城市, 注册站点总数, 最近一月注册站点数, 
		try {
			List<Map<String,Object>> list1 = basCompInfoMapperV2.queryAllBasCompInfo();
			//注册站点总数, 省份, 城市
			Map<String,List> mapList = new HashMap<String,List>();
			//List<String> listCompId = new ArrayList<String>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// comp_id, comp_address, create_time
			//存储最近一月注册站点数
			Map<String,Integer> map4 = new HashMap<String,Integer>();
			//存储收派员总数
			Map<String,Object> map5 = new HashMap<String,Object>();
			//最近一月注册收派数
			Map<String,Object> map7 = new HashMap<String,Object>();
			//最近7天注册收派数
			Map<String,Object> map8 = new HashMap<String,Object>();
			
			//最近一月注册站点数
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			Date date1 = calendar.getTime();//上个月的今天
			Date date2 = new Date();//今天
			
			for(Map<String,Object> map : list1){
				String compId = map.get("comp_id")+"";
				String compAddress = map.get("comp_address")+"";
				String createTime = map.get("create_time")+"";
				Date date3 = dateFormat.parse(createTime);
				//省份
				//String[] addressArr = compAddress.split("-");
				String province = compAddress.substring(0, 2);
				/*int length = addressArr.length;
				//String province = "null";
				String city = "null";
				if(length > 2){
					province = addressArr[0];//省份
					city = addressArr[1];
				}if(length < 2){
					province = addressArr[0];//省份
					city = "null";
				}*/
				String provin_city = province;
				//省份, 城市, 注册站点总数
				List list2 = mapList.get(provin_city);//根据省份得到下面的compId
				if(PubMethod.isEmpty(list2)){
					list2 = new ArrayList<String>();
					list2.add(compId);
					mapList.put(provin_city, list2);
				}else {
					list2.add(compId);
					mapList.put(provin_city, list2);
				}
				list2 = null;
				//最近一月注册站点数
				if(date3.after(date1) && date3.before(date2)){
					Integer len = map4.get(provin_city);
					if(len == null){
						map4.put(provin_city, 0);
					}else {
						map4.put(provin_city, len++);
					}
				}
			}
			//存储所有收派员的memberId
			Map<String,List<String>> map9 = new HashMap<String,List<String>>();
			Set<String> keySet = mapList.keySet();
			//最近七天注册收派数
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			Date date3 = calendar.getTime();//7天前的今天
			Date date4 = new Date();//今天
			String startMonth1 = dateFormat.format(date3);
			String endMonth1 = dateFormat.format(date4);
			for(String key : keySet){
				//收派员总数
				List list = mapList.get(key);//获取到所有的站点 compId
				//根据compId查询出所有的收派员 和对应的 memberId
				List<String> list3 = basCompInfoMapperV2.queryCourierMemberId(list);
				map5.put(key, list3.size());//城市,省份下面的所有收派员
				//存储所有收派员的memberId
				map9.put(key, list3);
				
				//最近一月注册收派数
				//查询bas_employeeAudit表中的注册收派员数
				String startMonth = dateFormat.format(date1);
				String endMonth = dateFormat.format(date2);
				List<String> list4 = basCompInfoMapperV2.queryCourierMemberIdByTime(list, startMonth, endMonth);
				map7.put(key, list4.size());
				//最近7天注册收派数
				//查询bas_employeeAudit表中的注册收派员数
				List<String> list5 = basCompInfoMapperV2.queryCourierMemberIdByTime(list, startMonth1, endMonth1);
				map8.put(key, list5.size());
			}
			
			
			//最近一天发短信数,时间
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date date5 = calendar.getTime();//1天前的今天
			Date date6 = new Date();//今天
			//最近一月发短信数, //最近七天发短信数, //最近一天发短信数
			Map<String,Object> map10 = new HashMap<String,Object>();
			Map<String,Object> map11 = new HashMap<String,Object>();
			Map<String,Object> map12 = new HashMap<String,Object>();
			Map<String,Object> map = null;
			//最近七天发短信数,时间
			String startDay2 = dateFormat.format(date3);
			String endDay2 = dateFormat.format(date4);
			//最近一天发短信数,时间
			String startDay3 = dateFormat.format(date5);
			String endDay3 = dateFormat.format(date6);
			Set<String> keySet9 = mapList.keySet();
			for (String key : keySet9) {
				//获取所有的memberId
				List<String> listMemberId = map9.get(key);
				String startMonth2 = dateFormat.format(date1);
				String endMonth2 = dateFormat.format(date2);
				for(String memberId : listMemberId){
					map = new HashMap<String,Object>();
					//最近一月发短信数
					map.put("memberId", memberId);
					map.put("startTime", startMonth2);
					map.put("endTime", endMonth2);
					String result1 = this.Post(SMServer+"querySmsNum/byMemberId", map);
					String num1 =JSON.parseObject(result1).getString("data");
					//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>result1: "+result1);
					map10.put(key, num1);
					//最近七天发短信数
					map = new HashMap<String,Object>();
					map.put("memberId", memberId);
					map.put("startTime", startDay2);
					map.put("endTime", endDay2);
					String result2 = this.Post(SMServer+"querySmsNum/byMemberId", map);
					//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>result2: "+result2);
					String num2 =JSON.parseObject(result2).getString("data");
					map11.put(key, num2);
					
					//最近一天发短信数
					map = new HashMap<String,Object>();
					map.put("memberId", memberId);
					map.put("startTime", startDay3);
					map.put("endTime", endDay3);
					String result3 = this.Post(SMServer+"querySmsNum/byMemberId", map);
					System.out.println(">>>>>>>>result1, result2, result3: "+result1+" ========result2 "+result2+" ========result3 "+result3);
					String num3 =JSON.parseObject(result3).getString("data");
					map12.put(key, num3);
				}
			}
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			//存储最终数据的map
			Map<String,Object> map13 = null;
			//循环数据放入listMap 中
			Set<String> keySet2 = mapList.keySet();
			
			for (String key : keySet2) {
				map13 = new HashMap<String,Object>();
				// key : 省份-城市
				String provice = key.split("-")[0];
				//String city = key.split("-")[1];
				map13.put("provice", provice);
				//map13.put("city", city);
				// 注册站点总数 keySet2.size();
				List list = mapList.get(key);
				map13.put("siteTotal", list.size());
				// 收派员总数 map5.get(key)
				map13.put("acceptTotal", map5.get(key));
				// 最近一月注册站点数 map4.get(key)
				map13.put("oneMonthSite", map4.get(key));
				// 最近一月注册收派数 map7.get(key)
				map13.put("oneMonthAccept", map7.get(key));
				// 最近 7天注册站点数 map8.get(key)
				map13.put("sevenDaySite", map8.get(key));
				// 最近一月发短信数 map10.get(key);
				map13.put("oneMonthSms", map10.get(key));
				// 最近 7天发短信数 map11.get(key);
				map13.put("sevenDaySms", map11.get(key));
				// 最近 1天发短信数 map12.get(key);
				map13.put("oneDaySms", map12.get(key));
				listMap.add(map13);
				map13 = null;
			}
			return listMap;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

}
