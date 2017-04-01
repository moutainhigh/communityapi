/**  
 * @Project: public_api
 * @Title: TaskServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2014-11-17 下午3:36:59
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.service.TaskService;
import net.okdi.core.common.handleMessage.MessageSenderRabbit;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.TaskHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;


@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskHttpClient taskHttpClient;
	@Autowired
	MessageSenderRabbit messageSenderRabbit;
	@Autowired
	private ConstPool constPool;
	@Override
	public String queryTaskUnTakeCount(Long compId) {
		return taskHttpClient.queryTaskUnTakeCount(compId);
	}
	
	@Override
	public String cancelTaskErp(String taskId) {
		return taskHttpClient.cancelTask(taskId);
	}
	
	
}
