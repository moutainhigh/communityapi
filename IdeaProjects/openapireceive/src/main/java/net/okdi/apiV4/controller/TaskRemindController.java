package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("taskRemindController")
@RequestMapping("taskRemind")
public class TaskRemindController extends BaseController{

	@Autowired
	private TaskRemindService taskRemindService;
	
	@ResponseBody
	@RequestMapping(value = "/queryTaskRemind", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTaskRemind(Long memberId,Integer pageNo,Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.TaskRemindController.queryTaskRemind", "memberId不能为空");
		}
		if(PubMethod.isEmpty(pageNo)){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize=20;
		}
		try{
			Map map=new HashMap();
			map.put("smsList", this.taskRemindService.queryTaskRemind(memberId,pageNo,pageSize));
			map.put("pageNo", pageNo);
			map.put("pageSize", pageSize);
			return jsonSuccess2(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryTaskRob", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTaskRob(Long memberId,Integer pageNo,Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.TaskRemindController.queryTaskRob", "memberId不能为空");
		}
		if(PubMethod.isEmpty(pageNo)){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize=20;
		}
		try{
			Map map=new HashMap();
			map.put("smsList", this.taskRemindService.queryTaskRob(memberId,pageNo,pageSize));
			map.put("pageNo", pageNo);
			map.put("pageSize", pageSize);
			return jsonSuccess2(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.jsonFailure(e);
		}
	}
	/**
	 * msgId    爱购猫微信号(手机号)
	 * memberId    收派员Id
	 * */
	@ResponseBody
	@RequestMapping("removeMsgLoger")
	public String removeMsgLoger(Long msgId, Long memberId) {
		try {
			if (PubMethod.isEmpty(msgId)) {
				return paramsFailure("001", "msgId不能为空");
			}
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure("002", "memberId不能为空");
			}
			this.taskRemindService.removeMsgLoger(msgId,memberId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * 这个是更改短信状态为已读
	 * @param msgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("unReply")
	public String unRead(String msgId,Long memberId){
		if(PubMethod.isEmpty(msgId)){
			return paramsFailure("net.okdi.apiV4.controller.TaskRemindController.unRead", "msgId参数不能为空");
		}
/*		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.TaskRemindController.memberId", "memberId参数不能为空");
		}*/
		String result="";
		try {
			result=this.taskRemindService.unRead(msgId,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
		return jsonSuccess(result);
	}
	/**
	 * 这个是更改短信状态为已读
	 * @param msgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("unReadCount")
	public String unReadCount(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.TaskRemindController.unReadCount.memberId", "用户id不能为空");
		}
		try {
			return jsonSuccess(this.taskRemindService.unReadCount(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
