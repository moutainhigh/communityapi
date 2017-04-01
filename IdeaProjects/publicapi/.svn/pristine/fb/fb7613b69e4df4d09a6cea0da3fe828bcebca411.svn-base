/**  
 * @Project: public_api
 * @Title: TaskHttpClient.java
 * @Package net.okdi.httpClient
 * @author amssy
 * @date 2014-11-17 下午5:23:26
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;

public class TaskHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool; 

	public String queryTaskUnTakeCount(Long compId) {
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure();
		}
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("compId", compId == null ? "" : compId.toString());
			String url = constPool.getOpenApiUrl() + "task/queryTaskUnTakeCount";
			String response = Post(url, map);
			return response;
		} catch (RuntimeException re) {
			return PubMethod.jsonFailure();
		}
	}
	
	public String cancelTask(String taskId) {
          Map<String,String> map = new HashMap<>();
          map.put("taskId",taskId);
          String url = constPool.getOpenApiUrl() + "task/cancelTaskAmssy";
		  String response = Post(url, map);
		  return response;
	}
	
	public String countUserNumber(Integer countType) {
		if (PubMethod.isEmpty(countType)) {
			return PubMethod.paramsFailure();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("countType", String.valueOf(countType));
		String url = constPool.getOpenApiUrl() + "task/queryTaskUnTakeCount";
		return Post(url, map);
	}
	

}
