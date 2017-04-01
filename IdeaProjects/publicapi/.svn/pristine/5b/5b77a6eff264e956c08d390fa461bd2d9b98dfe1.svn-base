/**  
 * @Project: publicapi
 * @Title: ThirdParty.java
 * @Package net.okdi.mob.controller
 * @author amssy
 * @date 2015-1-20 下午04:32:43
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.okdi.core.base.BaseController;
import net.okdi.core.common.handleMessage.MessageSenderRabbit;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.BroadcastService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("okdiTask")
public class ThirdPartyController extends BaseController{
	@Autowired
	BroadcastService broadcastService;
    @Autowired
    MessageSenderRabbit messageSenderRabbit;
    @Autowired
    ConstPool constPool; 
    Logger logger = Logger.getLogger(this.getClass());
	@RequestMapping("addBroadcast")
	@ResponseBody
	public String addBroadcast(String jsondata,HttpServletRequest request){
		logger.debug("电商运营数据单个接口 ============ addBroadcast  参数  : "+jsondata);
		try {
			String result = broadcastService.addBroadcast(jsondata,Long.parseLong(request.getAttribute("auth_memberId").toString()));	
			return result;
		} catch (Exception e) {
            return jsonFailure(e);
		}
		
	}
	@RequestMapping("addBroadcastOperation")
	@ResponseBody
	public String addBroadcastOperation(String jsondata,HttpServletRequest request){
		logger.debug("电商运营数据批量接口 ============== addBroadcastOperation  参数   ：   "+jsondata);
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("jsondata", jsondata);
		map.put("memberId", request.getAttribute("auth_memberId").toString());
		String a = JSON.toJSONString(map);
		messageSenderRabbit.setRoutingKey(constPool.getRabbitmqHousekeeperRoutingKey());
		messageSenderRabbit.sendDataToQueue(a);
		return jsonSuccess(null);
	}
    

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商抢单创建取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:42:19</dd>
	 * @param broadcastParam json格式参数
	 * @return
	 * @since v1.0
	 */
	@RequestMapping("/createTask/ec")
	@ResponseBody
	public String createTaskEC(String jsondata){
		try {
			String result = broadcastService.createTaskEC(jsondata);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午4:09:09</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/cancelBroadcast/ec", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String cancelBroadcast(Long broadcastId){
		try {
			String result = broadcastService.cancelBroadcast(broadcastId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
}