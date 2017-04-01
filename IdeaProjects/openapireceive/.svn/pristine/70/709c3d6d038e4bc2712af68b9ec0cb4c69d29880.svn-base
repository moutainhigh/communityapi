/**  
 * @Project: openapi
 * @Title: BroadcastListService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-1-13 下午5:31:44
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author amssy
 * @version V1.0
 */
public interface BroadcastListService {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-13 下午5:37:30</dd>
	 * @param memberId 登录人id
	 * @return
	 * @since v1.0 OK
	 */
	public List<Map<String,Object>> queryBroadcastList(Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播详情(显示包裹列表，抢单列表)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午7:58:37</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0 OK
	 */
	public Map<String, Object> doQueryBroadcastDetail(Long broadcastId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:05:11</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0 OK
	 */
	public String cancelBroadcast(Long broadcastId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播超时</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:06:45</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0 OK
	 */
	public String timeoutBroadcast(Long broadcastId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播重新发起</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:40:13</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	public String broadcastRestart(Long broadcastId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>选中站点或收派员创建取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 上午10:51:42</dd>
	 * @param broadcastId 广播id
	 * @param quotationId 报价id
	 * @param fromCompId  任务受理方站点  n 选中收派员时为收派员所在站点id  选中站点时为站点id
	 * @param fromMemberId  任务受理人员
	 * @param toCompId  营业分部
	 * @param toMemberId  收派员  选中收派员时填写
	 * @param coopNetId  任务受理方网络  n
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param actorMemberId 执行人员 
	 * @param contactTel 发件人电话
	 * @param taskSource 取件任务创建类型  个人端和电商
	 * @param taskFlag 0正常1抢单
	 * @return
	 * @throws Exception
	 * @since v1.0 OK
	 */
	public Map<String, Object> createTakeTask(Long broadcastId, Long quotationId, String appointTime, String contactTel, Byte taskSource, Byte taskFlag, String senderPhone) throws Exception;
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除已取消或已超时的广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午9:45:45</dd>
	 * @param broadcastId 广播id
	 * @param memberId 当前登录人id
	 * @return
	 * @since v1.0 OK
	 */
	public String deleteBroadcast(Long broadcastId, Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午4:17:01</dd>
	 * @param taskId 任务id
	 * @param memberId 登录人id
	 * @return
	 * @since v1.0
	 */
	public String deleteTakeTask(Long taskId, Long memberId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询任务详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午10:58:16</dd>
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	public Map<String, Object> queryTaskDetail(Long taskId); 
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午4:29:50</dd>
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件人电话
	 * @return
	 * @since v1.0
	 */
	public String scanParcel(Long id, Long taskId, String expWaybillNum, String phone);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单取件任务创建包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午7:06:35</dd>
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件人电话
	 * @param addresseeAddressId 收件地址id
	 * @param addresseeAddress 收件地址文字
	 * @param phone sendType 发件人类型 1:发货商家,2:好递个人
	 * @return
	 * @since v1.0
	 */
	public Map<String, Object> broadcastCreateParcel(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress, Short sendType);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>确认交寄删除包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午1:57:05</dd>
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	public String deleteParcel(Long id, Long taskId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>预约重新发起</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang </dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午02:50:37</dd>
	 * @param taskId
	 * @return
	 * @since v1.0
	 */
	 public String broadcastRestartForTask(Long taskId);
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>抢单指定站点或收派员时修改广播状态</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午4:42:56</dd>
	  * @param taskId
	  * @param broadcastId
	  * @return
	  * @since v1.0
	  */
	 public String finishBroadcastByCreateTask(Long taskId, Long broadcastId,Long memberId);
     /**
      * <dt><span class="strong">方法描述:</span></dt><dd>给已经报价的收派员发失败推送</dd>
      * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
      * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 下午8:08:10</dd>
      * @param broadcastId
      * @param qId
      * @since v1.0
      */
	 public void sendMessageToFailedCouriser(Long broadcastId,Long qId);
}
