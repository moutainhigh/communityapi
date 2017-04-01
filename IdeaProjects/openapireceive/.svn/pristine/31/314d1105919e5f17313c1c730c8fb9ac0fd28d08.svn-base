package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.ParTaskInfo;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;

public interface TakeTaskService {
	
	/**
	 * 
	 * @Project: openapi
	 * @Title: TaskService.java
	 * @Package net.okdi.task.service
	 * @Description: 通过手机号查询联系人
	 * @author xpf
	 * @date 2014-10-24 下午1:16:52
	 * @Copyright: 2014 All rights reserved.
	 * @since jdk1.6
	 * @version V1.0
	 */
	public Map<String, Object> loadContacts(String mobile, Long compId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过地址查询收派员或营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 下午2:02:36</dd>
	 * @param compId 站点id
	 * @param addrLongitude 经度
	 * @param addrLatitude 纬度
	 * @return {"data":{"compId":123456789,"memberId":123456789,"compName":站点,"memberId":人名},"success":true}
	 * @since v1.0
	 */
	public Map<String, Object> getCompOrEmployee(Long compId, BigDecimal addrLongitude, BigDecimal addrLatitude);
	
	
	/**
	 * 
	 * @Method: createByExp 
	 * @Description: 站点创建任务
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 联系人姓名
	 * @param contactMobile 联系人手机
	 * @param contactTel 联系人电话
	 * @param contactAddressId 联系人地址ID
	 * @param contactAddress 联系人详细地址
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 联系人地址的经度信息
	 * @param contactAddrLatitude 联系人地址的纬度信息
	 * @param taskSource 任务来源 1：好递网 2：站点自建 3：电商管家 4：好递个人端 5：好递接单王
	 * @param taskType 任务类型 0:取件 1:派件 2:自取件 3：销售等非快递
	 * @param customerType 客户类型 0:电商 1:企业 2:零散
	 * @param saveFlag 保存客户信息
	 * @param parEstimateWeight 包裹重量
	 * @param parEstimateCount 包裹数量
	 * @param taskFlag 0正常1抢单
	 * @return
	 * @throws Exception
	 * @since jdk1.6
	 */
	public Map<String, Object> create(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, 
			String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, 
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude, Byte taskSource, Byte taskType, Short customerType, 
			String saveFlag, BigDecimal parEstimateWeight, Byte parEstimateCount, BigDecimal parEstimatePrice, Byte taskFlag, Long broadcastId, String noticeType) throws Exception;
	
	/**
	 * 
	 * @Method: queryTaskNoPage 
	 * @Description: 查询收派员所有取件任务
	 * @param memberId 收派员id
	 * @param status 0全部 1未完成 2已完成
	 * @param taskType 2自取件
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午1:18:03
	 * @since jdk1.6
	 */
	public Map<String, Object> queryTaskNoPage(Long memberId, Integer status, Byte taskType);
	
	/**
	 * 
	 * @Method: queryTask 
	 * @Description: 分页查询任务
	 * @param senderName 发件人
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param senderPhone 发件人手机
	 * @param spacetime 持续时间
	 * @param memberId 收派员id
	 * @param compId 营业分部id
	 * @param operatorCompId 操作人公司id
	 * @param pageNum 页码
	 * @param querystatus 0待处理 1已分配 2已完成3已取消
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午1:18:51
	 * @since jdk1.6
	 */
	public Map<String, Object> queryTask(String senderName, String startTime, String endTime, String senderPhone,
			String spacetime, Long memberId, Long compId, Long operatorCompId, Page page, Byte querystatus);
	
	/**
	 * 
	 * @Method: distribute 
	 * @Description: 任务分配
	 * @param id id 可传递多个id值,用','隔开
	 * @param formCompId 分配方公司id
	 * @param fromMemberId 分配方人员id
	 * @param toCompId 接收方公司id
	 * @param toMemberId 接收方人员id
	 * @param currentMemberId 当前登录人id
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午1:21:25
	 * @since jdk1.6
	 */
	public String distribute(String id, Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long currentMemberId);
	
	/**
	 * 
	 * @Method: cancel 
	 * @Description: 取消任务
	 * @param taskId 任务id
	 * @param memberId 收派员id
	 * @param compId 站点id
	 * @param taskTransmitCause 任务转单原因  1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
	 * @param disposalDesc 任务流水描述
	 * @param status 1收派员 2营业分部 3站点
	 * @param source 来源 0其他来源  1客户来源
	 * @return
	 * @author xpf
	 * @date 2014-10-29 下午6:21:56
	 * @since jdk1.6
	 */
	public String cancel(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc, int status, int source);
	
	/**
	 * 
	 * @Method: queryTaskDetail 
	 * @Description: 查询任务详情
	 * @param id id
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午1:23:37
	 * @since jdk1.6
	 */
	public Map<String,Object> queryTaskDetail(Long id);
	
	/**
	 * 
	 * @Method: finishTask 
	 * @Description: 完成任务
	 * @param id 任务记录id
	 * @param memberId 操作人id
	 * @param compId 操作人公司id
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午2:04:10
	 * @since jdk1.6
	 */
	public String finishTask(Long id, Long memberId, Long compId, Long TaskId);
	
	/**
	 * 
	 * @Method: queryOkdiTask 
	 * @Description: 好递网发件记录查询
	 * @param memberId
	 * @return
	 * @author xpf
	 * @date 2014-11-3 下午2:12:46
	 * @since jdk1.6
	 */
	public Page queryOkdiTask(Long memberId, Page page, Byte takeSource); 
	
	/**
	 * 
	 * @Method: queryTaskUnfinished 
	 * @Description: 查询营业分部或收派员是否有未完成任务
	 * @return 1有未完成任务 0无未完成
	 * @author xpf
	 * @date 2014-11-6 上午9:36:46
	 * @since jdk1.6
	 */
	public int queryTaskUnFinished(Long compId, Long memberId);
	
	/**
	 * 
	 * @Method: getMemberInfoByCompId 
	 * @Description: 查询站点下收派员或营业分部
	 * @param compId
	 * @return
	 * @author xpf
	 * @date 2014-11-6 下午1:54:47
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> getMemberInfoByCompId(Long parentId);
	
	/**
	 * 
	 * @Method: queryOkdiTaskDetail 
	 * @Description: 好递网查询详情
	 * @return
	 * @author xpf
	 * @date 2014-11-11 上午11:17:28
	 * @since jdk1.6
	 */
	public Map<String,Object> queryOkdiTaskDetail(Long taskId);
	
	/**
	 * 
	 * @Method: queryTaskCount 
	 * @Description: 查询取件任务数量
	 * @param compId
	 * @return
	 * @author xpf
	 * @date 2014-11-11 上午11:39:43
	 * @since jdk1.6
	 */
	public Map<String,Object> queryTaskUnTakeCount(Long compId, Byte status);
	
	/**
	 * 
	 * @Method: updateContacts 
	 * @Description: 接单王更新客户数据
	 * @param taskId
	 * @param contactName
	 * @param contactMobile
	 * @param contactTel
	 * @param contactAddressId
	 * @param contactAddress
	 * @param customerId
	 * @return
	 * @author xpf
	 * @date 2014-11-12 上午9:53:22
	 * @since jdk1.6
	 */
	public String updateContacts(Long taskId, String contactName, String contactMobile, String contactTel, Long contactAddressId, String contactAddress, Long customerId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新取件任务信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-26 下午6:06:03</dd>
	 * @param taskId
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String updateTaskInfoForOkdi(Long taskId, Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>任务id查询任务信息对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-21 下午1:32:48</dd>
	 * @param key 任务id
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	public ParTaskInfo cacheTaskInfo(String key);
	/**
	 * 取件记录列表
	 * @param memberId
	 * @param date
	 * @return
	 */
	public List<Map<String,Object>>  takeTaskRecordList(Long memberId,String date);
    /**
     * 取件记录详情
     * @param taskId
     * @return
     */
    public Map<String,Object> takeTaskDetail(Long taskId);

    public Map<String,Object> queryTakeById(Long taskId);
    
    public Map<String,Object> createTaskIgomo(Long fromCompId, Long coopNetId,String appointDesc,Long actorMemberId,String contactName,String contactMobile
			,String contactAddress,BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,Long memberId,
			String parcelStr,Byte taskFlag, Integer howFast, Byte parEstimateCount,Long assignNetId);


	void removeTaskIgomoCache(Long fromCompId, Long actorMemberId);


	Map queryWechatTask(String memberId, Page page, Byte takeSource,String netName,String actorMemberName,String actorPhone)
			throws ServiceException;
/**
 * 
 * @Description: 查询取件列表
 * @param taskSource
 * @param netName
 * @param taskStatus
 * @param contactAddress
 * @param contactMobile
 * @param actorPhone
 * @param currentPage
 * @param pageSize
 * @param startTime
 * @param endTime
 * @param page 
 * @return Object
 * @throws
 * @author jianxin.ma
 * @date 2016-4-5
 */

	public Page queryTask(String taskSource, String netName,String taskStatus, String contactAddress, String contactMobile,
			String actorPhone,String startTime, String endTime, Integer currentPage, Integer pageSize);


	public BasNetInfo getNetInfoByNetId(Long netId);


	void wechatRob(Long memberId, String netName, String netId, String phone, String mname, Long taskId);



	public void cancelWechatOrder(Long taskId);


	void updateTaskStatus(Long taskId, int status);
}
