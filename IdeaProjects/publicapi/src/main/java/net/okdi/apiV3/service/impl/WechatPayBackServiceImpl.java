package net.okdi.apiV3.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import net.okdi.apiV3.service.WechatPayBackService;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.AbstractHttpClient;

@Service
public class WechatPayBackServiceImpl extends AbstractHttpClient implements WechatPayBackService {

	@Value("${okdiplatform_url}")
    private String okdiplatformUrl; //运营平台url
	
	public static final Log logger = LogFactory.getLog(WechatPayBackServiceImpl.class);
	
	@Override
	public HashMap<String, Object> getActivityRule(Double money){
		Double rewardMoney = 0.00;
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用运营平台接口
		String methodName="/rechargeActity/getActivityList";
		logger.info("okdiplatform 查询正在进行中的活动规则接口: url ="+okdiplatformUrl+" methodName="+methodName+" map="+null);
		String result = this.Post(okdiplatformUrl+methodName, null);
		logger.info("请求运营平台返回结果："+result);
		if (PubMethod.isEmpty(result)) {
			logger.info("运营平台没有查询到正在进行中的充值奖励活动！请核对数据库！");
			map.put("isHaveActivity", "0");
		}else {
			Map<String,Object> resultMap = JSONObject.parseObject(result);
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			map.put("activityId", dataMap.get("activityId"));
			if (!"".equals(dataMap.get("ruleList")) && dataMap.get("ruleList")!=null) {
				List<Map<String,Object>> mapList = (List<Map<String, Object>>) dataMap.get("ruleList");
				//如果有奖励规则
				if(mapList!=null && mapList.size()>0){
					for(Map<String,Object> mapOBJ :mapList){
						Double startMoney = Double.parseDouble(mapOBJ.get("startMoney").toString());
						Double endMoney = Double.parseDouble(mapOBJ.get("endMoney").toString());
						Double reward = Double.parseDouble(mapOBJ.get("rewardMoney").toString());
						//输入金额在活动规则范围之内
						if (money<=endMoney && money>=startMoney ) {
							rewardMoney = reward;
							map.put("isHaveActivity", "1");
							map.put("rewardMoney", rewardMoney);
							logger.info("充值金额为："+money+";充值活动奖励金额为："+rewardMoney);
							break;
						}else {
							logger.info("输入的金额没有在活动规则范围之内，所以没有充值奖励金额！");
							map.put("isHaveActivity", "0");
						}
					}
				}else{
					logger.info("运营平台没有充值奖励活动规则，请核对数据库！");
					map.put("isHaveActivity", "0");
				}
			}else {
				map.put("isHaveActivity", "0");
			}
		}
		return map;
	}
}
