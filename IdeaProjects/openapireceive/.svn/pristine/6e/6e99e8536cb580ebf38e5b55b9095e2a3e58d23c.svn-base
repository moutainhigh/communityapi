/**  
 * @Project: openapi
 * @Title: QueryTaskService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-3-2 下午04:23:06
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.util.Date;

import net.okdi.core.common.page.Page;

/**
 * @author amssy
 * @version V1.0
 */
public interface QueryTaskService {
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
	public String queryTask(Long netId,Long compId,Long memberId,Date startTime,Date endTime,Page page);
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
	public String queryTaskDetail(Long memberId,Date startTime,Date endTime,Page page);
}
