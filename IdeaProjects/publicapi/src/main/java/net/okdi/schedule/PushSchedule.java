package net.okdi.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.schedule.entity.ParLogisticSearch;
import net.okdi.schedule.service.ParLogisticSearchService;

import org.springframework.beans.factory.annotation.Autowired;

public class PushSchedule {
	
	@Autowired
	ParLogisticSearchService parLogisticSearchService;

	/**
	 * 处理步骤：
	 * 1.首先从openapi取得1000条(openapi控制)需要推送的数据，需要传递字段包括：
	 * 		netId,netCode,expWaybillNum,memberId
	 * 	 在传递数据的时候只传这一些，这能减少传输的数据量
	 * 2.对于这1000条数据进行推送
	 * 3.更新这些推送数据的推送标记
	 * 		该逻辑值需要传递会该条记录的id就可以了，使用最简的数据传输，使用id,分割即可，可以加上多个“，”，增加容错性
	 * 4.回归到第一步执行，直到第一步取到的数据为空
	 */
	
	public void startPush(){
		System.out.println("这是推送定时。。。。PushSchedule");
		List<ParLogisticSearch> lsPar= parLogisticSearchService.findUnPushed();
		int count = 20;//保证最多处理20次，这个是测试版
		while(!PubMethod.isEmpty(lsPar) && lsPar.size() > 0){
			if(count<0)
				return;
			count --;
			StringBuffer sbStr = new StringBuffer();
			for(ParLogisticSearch par:lsPar){
				push(par);
				sbStr.append( par.getId() + ",");
			}
			parLogisticSearchService.updatePushData(sbStr.toString());
			lsPar= parLogisticSearchService.findUnPushed();
		}
	}

	private void push(ParLogisticSearch p) {
		StringBuffer sb = new StringBuffer();
		sb.append(p.getNetId() + "||");
		sb.append(p.getNetCode() + "||");
		sb.append(p.getExpWaybillNum() + "||");
		//sb.append(p.getExpType());
		sb.append("118");
		Map pushMap = new HashMap<String, String>();
		pushMap.put("channelNo", "02");
		pushMap.put("memberId", p.getChannelId());
		pushMap.put("type", "118");
		pushMap.put("title", "消息提醒");
		pushMap.put("content", "您关注的物流动态有变化");
		pushMap.put("extraParam", sb.toString());
		pushMap.put("msgType", "message");
		pushMap.put("pushType", "fourHourPush");
		parLogisticSearchService.pushExtMsg(pushMap);
	}
}
