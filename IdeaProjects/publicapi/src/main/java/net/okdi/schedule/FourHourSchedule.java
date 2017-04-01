/**  
 * @Project: mob
 * @Title: FourHourSchedule.java
 * @Package net.okdi.schedule
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-12 下午8:27:48
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ApplicationContextUtil;
import net.okdi.core.util.service.ConstPool;
import net.okdi.logistics.QueryKuaiDiClient;
import net.okdi.schedule.entity.ParLogisticSearch;
import net.okdi.schedule.service.ParLogisticSearchService;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FourHourSchedule {

	private static Integer PERLENGTH = 1000;

	@Autowired
	ParLogisticSearchService parLogisticSearchService;

	private volatile Integer currentNum = 0;
	private volatile Integer total = 0;
	private volatile List<ParLogisticSearch> batchList = new ArrayList<ParLogisticSearch>();

	ExecutorService esPool = Executors.newSingleThreadExecutor();
	
	//定时推送任务:显示只有1步，处理加入我收的我发的
	@SuppressWarnings(value = "rawtypes")
	public void scheduleTask() {
		total = 0;
		currentNum = 0;
		System.out.println("定时任务开启： scheduleTask");
		List resultList = parLogisticSearchService.findIdListPerFourHour();
		if (resultList == null || resultList.size() == 0)
			return;
		total = resultList.size();
		int num = 0;
		do {
			List uptList = new ArrayList();
			Integer end = (num + 1) * PERLENGTH < resultList.size() ? (num + 1) * PERLENGTH : resultList.size();
 			for (int i = num * PERLENGTH; i < end; i++) {
				uptList.add(resultList.get(i));
			}
			//System.arraycopy(resultList, num*PERLENGTH, uptList, 0, PERLENGTH);
			Map resultMap = parLogisticSearchService.findUnfinishParList(uptList);
			if (resultMap == null || resultMap.get("data") == null || "[]".equals(String.valueOf(resultMap.get("data"))))
				return;
			List<ParLogisticSearch> list = JSON.parseArray(resultMap.get("data").toString(), ParLogisticSearch.class);
			for (ParLogisticSearch p : list) {
				try {
					// if("3".equals(p.getTraceStatus())||"1".equals(p.getSystemMark())){continue;}
					if (!PubMethod.isEmpty(p.getExpWaybillNum())) {
						// pushSingleMsg(p, list.size());
						PushSing ps = new PushSing(p);
						//ps.start();
						esPool.execute(ps);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			num++;
		} while (num < resultList.size() / PERLENGTH);
	}

	class PushSing extends Thread {
		private ParLogisticSearch p = null;

		public PushSing(ParLogisticSearch p) {
			ConstPool constPool = (ConstPool) ApplicationContextUtil.getBean("constPool");
			this.p = p;
		}

		@Override
		public void run() {
			//执行的流程：当前的处理的数加1，如果当前页码数超过或者等于总数
			currentNum++;
			if (batchList.size() > 100 || currentNum >= total) {
				parLogisticSearchService.batchUpdate(batchList);
				batchList = new ArrayList<ParLogisticSearch>();
				if(currentNum >= total)
					currentNum=0;
			}
			//System.out.println(new Date() + " 业务单号：  " + p.getExpWaybillNum());
			pushSingleMsg(p);
		}
	}

	public void pushSingleMsg(ParLogisticSearch p) {
		// 以下为了查看推送日志
		StringBuffer logStrb = new StringBuffer("开始处理包裹【 " + p.getExpWaybillNum() + " 】信息。。。\n");
		try {
			// 获取最后一条物流信息
			Map map = new HashMap();
			map.put("type", p.getNetCode());
			map.put("postid", p.getExpWaybillNum());
			//batchList.add(p);
			QueryKuaiDiClient ac = new QueryKuaiDiClient();
			//BasNetinfoService basNetinfoService= (BasNetinfoService)(ApplicationContextUtil.getBean("basNetinfoService")) ;
			// Thread t1 = new Thread();
			// System.out.println("AppointId:"+p.getAppointId());
			String netName = "";
			//BasNetinfo basNetinfo = basNetinfoService.findBasNetinfo(p.getNetId());
			//netName = basNetinfo == null ? "" : basNetinfo.getNetName();
			String text = "";
			String status = "";
			String city = "";
			text = ac.getResult(map);
			if (text == null)
				return;
			status = JsonToObject.getParamFromJsonString(text, "status");
			//System.out.println(text);
			logStrb.append("从快递100中查询到的状态 status: " + status);
			if (status != null && status.equals("200")) {
				String state = JsonToObject.getParamFromJsonString(text, "state");
				String oldTime = getNewestTime(p.getTraceDetail());
				String newTime = getNewestTime(text);
				System.out.println("老时间："+oldTime +"      新时间："+newTime);
				if (p.getTraceStatus() == null)
					p.setTraceStatus("-1");
				if ((p.getTraceDetail() != null && !oldTime.equals(newTime)) || !p.getTraceStatus().equals(state)) {
					p.setTraceDetail(text);
					// p.setCharLength(text.length());
					p.setModifiedTime(PubMethod.dateToTimestamp(new Date()));
					p.setTraceStatus(state);
					p.setPushMark(Short.valueOf("1"));
					// 接收的状态和发送的状态不一致
					logStrb.append("\n包裹更新表内容开始");
					batchList.add(p);
					
					//这里不再进行推送操作
//					StringBuffer sb = new StringBuffer();
//					sb.append(p.getNetId() + "||");
//					sb.append(p.getNetCode() + "||");
//					sb.append(p.getExpWaybillNum() + "||");
//					//sb.append(p.getExpType());
//					sb.append("118");
//					Map pushMap = new HashMap<String, String>();
//					pushMap.put("channelNo", "02");
//					pushMap.put("memberId", p.getChannelId());
//					pushMap.put("type", "118");
//					pushMap.put("title", "消息提醒");
//					pushMap.put("content", "您关注的物流动态有变化");
//					pushMap.put("extraParam", sb.toString());
//					pushMap.put("msgType", "message");
//					pushMap.put("pushType", "fourHourPush");
//					//pushHttpClient.doPassSendObj("pushMsg/sendExtMsg", pushMap);
//					parLogisticSearchService.pushExtMsg(pushMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getNewestTime(String str) {
		try {
			if (PubMethod.isEmpty(str))
				return "";
			JSONObject obj = JSONObject.parseObject(str);
			JSONArray jaData = JSONArray.parseArray(obj.get("data").toString());
			JSONObject jfinal = null ;
			for (int i = 0; i < jaData.size(); i++) {
				if(jfinal == null){
					jfinal = jaData.getJSONObject(i);
				}else  if (jfinal.getString("time").compareTo(jaData.getJSONObject(i).getString("time")) < 0) {
					jfinal = jaData.getJSONObject(i);
				}
			}

			return jfinal.getString("time");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}

class JsonToObject {
	public static String getParamFromJsonString(String text, String paramName) {
		if (text == null || text.equals(""))
			return "";
		JSONObject obj = JSONArray.parseObject(text);
		return obj.getString(paramName);
	}
}