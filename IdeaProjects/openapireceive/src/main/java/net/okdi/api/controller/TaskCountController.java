/**  
 * @Project: openapi
 * @Title: TaskCountController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2015-3-3 下午02:33:52
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import java.util.Date;

import net.okdi.api.service.QueryTaskService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/taskCountController")
public class TaskCountController extends BaseController{
	@Autowired
     private QueryTaskService queryTaskService;
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询指定收派员取派件任务数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-2 下午04:46:50</dd>
	 * @param netId 网络ID
	 * @param compId 站点或营业分部ID
	 * @param memberId 收派员ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间 
	 * @param page 分页参数
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("queryTask")
	public String queryTask(Long netId,Long compId,Long memberId,Long startTime,Long endTime,Page page){
		try {
		return queryTaskService.queryTask(netId, compId, memberId, new Date(startTime),new Date(endTime), page);	
		} catch (Exception e) {
		return jsonFailure(e);
		}
	}
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>查询一个收派员取派件任务详细信息</dd>
     * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-3-2 下午04:46:56</dd>
     * @param memberId 收派员ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return
     * @since v1.0
     */
	@ResponseBody
	@RequestMapping("queryTaskDetail")
	public String queryTaskDetail(Long memberId,Long startTime,Long endTime,Page page){
		try {
			return queryTaskService.queryTaskDetail(memberId, new Date(startTime),new Date(endTime), page);	
			} catch (Exception e) {
			return jsonFailure(e);
			}
	}


}
