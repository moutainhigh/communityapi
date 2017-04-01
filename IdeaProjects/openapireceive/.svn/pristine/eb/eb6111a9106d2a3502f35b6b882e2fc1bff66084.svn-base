package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.vo.TaskVo;
import net.okdi.api.vo.VO_queryTask;
import net.okdi.core.common.page.Page;


public interface TaskService {
	
	/**
	 * 
	 * @Method: loadContacts 
	 * @Description: 手机号获取客户信息
	 * @param mobile
	 * @return
	 * @since jdk1.6
	 */
	public String loadContacts(String mobile);
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 通过地址查询收派员或营业分部
	 * @author xpf
	 * @date 2014-10-24 下午1:16:52
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 */
	public String getCompOrEmployee(BigDecimal addrLongitude, BigDecimal addrLatitude);
	
	/**
	 * 
	 * @Method: create 
	 * @Description: 创建任务，包括取件任务、派件任务
	 * @param task
	 * @return
	 * @throws Exception
	 * @since jdk1.6
	 */
	public Long create(TaskVo task) throws Exception;
	
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 查询任务
	 * @author xpf
	 * @date 2014-10-24 下午2:19:03
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 * @param status 0未处理任务 1取件任务 2取消任务
	 */
	public List<Map<String,Object>> page(VO_queryTask queryParam, Page page, int status);
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 分配任务
	 * @author xpf
	 * @date 2014-10-24 下午2:51:16
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 */
	public String taskDistribution(Long taskId, Long compId, Long memberId);
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 取消任务
	 * @author xpf
	 * @date 2014-10-24 下午2:54:45
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 * @param taskCancelType 取消状态 1，2(拒绝) 3-5(取消)
	 */
	public String cancelTask(Long taskId, Byte taskCancelType, String remark);
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 查看任务详情
	 * @author xpf
	 * @date 2014-10-24 下午3:01:14
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 */
	public String queryTaskDetail(Long taskId);
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 完成任务
	 * @author xpf
	 * @date 2014-10-24 下午3:05:25
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 */
	public String finishTask();
	
	
	
}
