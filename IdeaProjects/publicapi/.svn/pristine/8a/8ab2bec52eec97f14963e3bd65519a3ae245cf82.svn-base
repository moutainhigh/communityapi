/**  
 * @Project: public_api
 * @Title: TaskTaskController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2014-11-18 上午11:04:21
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import net.okdi.api.service.TaskService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.handleMessage.MessageSenderRabbit;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pengfei.xia
 * @version V1.0
 */
@Controller
@RequestMapping("/task")
public class TaskTakeController extends BaseController {
	@Autowired
	private TaskService taskService;
	
	@ResponseBody
	@RequestMapping(value="/taskUnTakeCount", method = { RequestMethod.GET , RequestMethod.POST })
	public String taskUnTakeCount(Long compId) {
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure();
		}
		try {
			return taskService.queryTaskUnTakeCount(compId);
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/cancelTaskAmssy", method = { RequestMethod.GET , RequestMethod.POST })
	public String cancelTaskAmssy(Long taskId) {
		if (taskId==null||taskId == 0) {
			return PubMethod.paramsFailure();
		}
		try {
			return taskService.cancelTaskErp(taskId+"");
		} catch (Exception e) {
			e.printStackTrace();
		    return PubMethod.jsonFailure();
		}
	
	}
	
	
	
}