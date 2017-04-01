package net.okdi.schedule.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.schedule.entity.ParLogisticSearch;
import net.okdi.schedule.service.ParLogisticSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("parLogisticSearchService")
public class ParLogisticSearchServiceImpl implements ParLogisticSearchService{
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	public ParLogisticSearch findParLogisticSearch(Long netId,String expWaybillNum){
		ParLogisticSearch ps = new ParLogisticSearch();
		ps.setNetId(netId);
		ps.setExpWaybillNum(expWaybillNum);
		return null;
	}
	/**
	 * private static final long serialVersionUID = 7000615940755255428L;

	private int currentPage = 1; // 当前页数

	private int pageSize = 10; // 每页显示记录的条数

	private int total; // 总的记录条数

	private int pageCount; // 总的页数

	private int offset; // 开始位置，从0开始

	private boolean hasFirst;// 是否有首页

	private boolean hasPre;// 是否有前一页

	private boolean hasNext;// 是否有下一页

	private boolean hasLast;// 是否有最后一页
	private List items = new ArrayList();
	 */
	@Override
	public Map findUnfinishParList(List listIds) {
		Map map = new HashMap();
		map.put("ids", listIds.toString().replace("[", "").replace("]", ""));
		Map resultMap = openApiHttpClient.doPassSendObj("logisticSearch/findParLogistic", map);
		//resultMap.get("item");
		return resultMap;
	}


	/**
	 * @Method: uptParList 
	 * @Description: TODO
	 * @param parLogisticSearch 
	*/
	@Override
	public void uptParList(ParLogisticSearch lsParLogisticSearch) {
		Map map = new HashMap();
		map.put("data", JSON.toJSONString(lsParLogisticSearch));
		Map resultMap = openApiHttpClient.doPassSendObj("logisticSearch/update", map);
	}
	/**
	 * @Method: uptParList 
	 * @Description: TODO
	 * @param parLogisticSearch 
	 */
	@Override
	public void batchUpdate(List<ParLogisticSearch> lsParLogisticSearch) {
		Map map = new HashMap();
		map.put("jsonData", JSON.toJSONString(lsParLogisticSearch));
		Map resultMap = openApiHttpClient.doPassSendObj("logisticSearch/batchUpdate", map);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List findIdListPerFourHour() {
		Map map = new HashMap();
		Map resultMap = openApiHttpClient.doPassSendObj("logisticSearch/findIdListPerFourHour", map);
		if(resultMap == null||resultMap.get("data")==null)
			return null;
		String[] strArrs = resultMap.get("data").toString().replaceAll("[\\[\\]\\{\\}]", "").split(",");
		return Arrays.asList(strArrs);
	}
	@Override
	public void pushExtMsg(Map map) {
		openApiHttpClient.doPassSendObj("mobPush/pushExt", map);
	}
	@Override
	public List<ParLogisticSearch> findUnPushed() {
		Map map = new HashMap();
		//{"data":[{"appointId":"","channelId":133187036823552,"channelNo":"","createdTime":"","expType":"","expWaybillNum":"718786513041","id":133187454156800,"modifiedTime":"","netCode":"zhongtong","netId":1502,"netName":"","pushMark":"","recMobile":"","systemMark":"","traceDetail":"","traceStatus":""},{"appointId":"","channelId":132886997286912,"channelNo":"","createdTime":"","expType":"","expWaybillNum":"718786513041","id":133246356865024,"modifiedTime":"","netCode":"zhongtong","netId":1502,"netName":"","pushMark":"","recMobile":"","systemMark":"","traceDetail":"","traceStatus":""},{"appointId":"","channelId":132886997286912,"channelNo":"","createdTime":"","expType":"","expWaybillNum":"1700151875813","id":133249989132288,"modifiedTime":"","netCode":"yunda","netId":1504,"netName":"","pushMark":"","recMobile":"","systemMark":"","traceDetail":"","traceStatus":""}],"success":true}
		Map m = openApiHttpClient.doPassSendObj("logisticSearch/findUnPushed", map);
		if(PubMethod.isEmpty(m)?true:PubMethod.isEmpty(m.get("data"))){
			return null;
		} else {
			if("[]".equals(m.get("data").toString()))
				return null;
		}

		String str = m.get("data").toString();
		List<ParLogisticSearch> lsPar = getListFromStr(str,ParLogisticSearch.class);
		return lsPar;
	}
	
	private  static <T> List<T> getListFromStr(String str,Class<T> T){
		try{
			List<T> ls = new ArrayList<T>();
			JSONArray jsa = JSON.parseArray(str);
			for(int i = 0;i<jsa.size();i++){
				JSONObject jso = jsa.getJSONObject(i);
				T t = jso.toJavaObject(jso, T);
				ls.add(t);
			}
			return ls;
		}catch(Exception e){
			return null;
		}
	}
	
//	public static void main(String[] args) {
//		String str ="[{\"appointId\":\"\",\"channelId\":133187036823552,\"channelNo\":\"\",\"createdTime\":\"\",\"expType\":\"\",\"expWaybillNum\":\"718786513041\",\"id\":133187454156800,\"modifiedTime\":\"\",\"netCode\":\"zhongtong\",\"netId\":1502,\"netName\":\"\",\"pushMark\":\"\",\"recMobile\":\"\",\"systemMark\":\"\",\"traceDetail\":\"\",\"traceStatus\":\"\"},{\"appointId\":\"\",\"channelId\":132886997286912,\"channelNo\":\"\",\"createdTime\":\"\",\"expType\":\"\",\"expWaybillNum\":\"718786513041\",\"id\":133246356865024,\"modifiedTime\":\"\",\"netCode\":\"zhongtong\",\"netId\":1502,\"netName\":\"\",\"pushMark\":\"\",\"recMobile\":\"\",\"systemMark\":\"\",\"traceDetail\":\"\",\"traceStatus\":\"\"},{\"appointId\":\"\",\"channelId\":132886997286912,\"channelNo\":\"\",\"createdTime\":\"\",\"expType\":\"\",\"expWaybillNum\":\"1700151875813\",\"id\":133249989132288,\"modifiedTime\":\"\",\"netCode\":\"yunda\",\"netId\":1504,\"netName\":\"\",\"pushMark\":\"\",\"recMobile\":\"\",\"systemMark\":\"\",\"traceDetail\":\"\",\"traceStatus\":\"\"}]";
//		 List<ParLogisticSearch>  lpar = getListFromStr(str,ParLogisticSearch.class);
//		 for(ParLogisticSearch par : lpar){
//			 System.out.println(par.getId());
//		 }
//	}
	
	@Override
	public void updatePushData(String ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		openApiHttpClient.doPassSendStr("logisticSearch/updatePushData", map);
	}
	
}
