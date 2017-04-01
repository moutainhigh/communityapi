package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.api.service.BroadcastListService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/task")
public class TakeTaskController extends BaseController {
	//任务来源
	final Byte task_source_okdi = 1; //好递网
	final Byte task_source_exp = 2; //站点自建
	final Byte task_source_ec = 3; //电商管家
	final Byte task_source_app_personal = 4; //好递个人端
	final Byte task_source_app_exp = 5; //好递接单王
	
	//任务类型
	final Byte task_type_take = 0; //取件
	final Byte task_type_send = 1; //派件
	final Byte task_type_take_and_send = 2; //自取件
	final Byte task_type_sale = 3; //销售等非快递
	
	//任务状态
	final Byte task_status_untake = 0; //待处理
	final Byte task_status_distribute = 1; //已分配
	final Byte task_status_finish = 2; //已完成
	final Byte task_status_cancel = 3; //已取消
	
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private BroadcastListService broadcastListService;
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过手机号查询客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午2:58:29</dd>
	 * @param mobile 手机号
	 * @param compId 站点id
	 * @return {"data":{"customerType":2,"contactsPhone":"110","customerName":"王老五","addrIdLev5":10064572,"addrIdLev4":104585,"detailedAddress":"","memberId":12345,"okdiCustomerId":123,"memberName":收派员},"success":true}
	 *customerType 客户类型 0电商 1企业 2零散   	contactsPhone联系电话   customerName联系人姓名   addrIdLev5五级地址  addrIdLev4四级地址  detailedAddress详细地址	memberId人员ID    okdiCustomerId好递客户ID	memberName收派员姓名
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.loadContacts.001 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.loadContacts.002 - 无发件人信息</dd>
	 * <dd>openapi.TakeTaskServiceImpl.loadContacts.003 - 当前登陆人无站点信息</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/loadContacts", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadContacts(String mobile, Long compId) {
		try {
			return jsonSuccess(takeTaskService.loadContacts(mobile, compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午2:59:22</dd>
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 发件人姓名
	 * @param contactMobile 发件人手机
	 * @param contactTel 发件人电话
	 * @param contactAddressId 发件人地址ID
	 * @param contactAddress 发件人详细地址  北京-海淀区 田村路
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 发件人地址的经度信息
	 * @param contactAddrLatitude 发件人地址的纬度信息
	 * @param customerType 客户类型 0电商 1企业 2零散
	 * @param saveFlag 是否保存客户  1保存
	 * @return {"success":true, "taskId":123456789}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	 * <dd>openapi.TakeTaskController.create.001 - 请选择分配的营业分部或收派员</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/create/exp", method = { RequestMethod.GET , RequestMethod.POST })
	public String exp(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude, Short customerType, String saveFlag) {
		try {
			if(PubMethod.isEmpty(toCompId) && PubMethod.isEmpty(toMemberId)) {
				throw new ServiceException("openapi.TakeTaskController.create.001", "请选择分配的营业分部或收派员");
			}
			return jsonSuccess(takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile,
					contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_exp, task_type_take, customerType, saveFlag, null, null, null, (byte) 0, null, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:08:25</dd>
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 发件人姓名
	 * @param contactMobile 发件人手机
	 * @param contactTel 发件人电话
	 * @param contactAddressId 发件人地址ID
	 * @param contactAddress 发件人详细地址
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 发件人地址的经度信息
	 * @param contactAddrLatitude 发件人地址的纬度信息
	 * @param parEstimateWeight 包裹重量
	 * @return {"success":true, "taskId":123456789}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/create/okdi", method = {RequestMethod.GET , RequestMethod.POST })
	public String okdi(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude, BigDecimal parEstimateWeight) {
		try {
			return jsonSuccess(takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile,
					contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_okdi, task_type_take, null, null, parEstimateWeight, (byte) 1, null, (byte) 0, null, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:12:56</dd>
	 * @param fromCompId 任务受理方站点  n
	 * @param fromMemberId 任务受理人员  n
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员  n
	 * @param coopNetId 任务受理方网络  n
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员  n
	 * @param contactName 发件人姓名  n
	 * @param contactMobile 发件人手机  n
	 * @param contactTel 发件人电话
	 * @param contactAddressId 发件人地址ID  n
	 * @param contactAddress 发件人详细地址  n 省市区县+空格+详细地址
	 * @param customerId 客户ID  n
	 * @param createUserId 创建人ID  n
	 * @param contactAddrLongitude 发件人地址的经度信息  n
	 * @param contactAddrLatitude 发件人地址的纬度信息  n
	 * @param parEstimateWeight 包裹重量  n
	 * @param parEstimateCount 包裹数量
	 * @param broadcastId 广播id
	 * @return {"success":true, "data":{"taskId":123456789}}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/create/personal", method = {RequestMethod.GET , RequestMethod.POST })
	public String personal(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude, BigDecimal parEstimateWeight, Byte parEstimateCount, Long broadcastId) {
		try {
			if(PubMethod.isEmpty(parEstimateCount)) {
				parEstimateCount = (byte) 1;
			}
			Map<String, Object> map = takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile,
					contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_app_personal, task_type_take, null, null, parEstimateWeight, parEstimateCount, null, (byte) 0, broadcastId, "");
			if(map != null && map.get("taskId") != null && !"".equals(map.get("taskId").toString())) {
				broadcastListService.finishBroadcastByCreateTask(Long.parseLong(map.get("taskId").toString()), broadcastId,toMemberId);
			}
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:14:00</dd>
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 发件人姓名
	 * @param contactMobile 发件人手机
	 * @param contactTel 发件人电话
	 * @param contactAddressId 发件人地址ID
	 * @param contactAddress 发件人详细地址
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 发件人地址的经度信息
	 * @param contactAddrLatitude 发件人地址的纬度信息
	 * @return {"success":true, "taskId":123456789}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/create/order", method = {RequestMethod.GET , RequestMethod.POST })
	public String order(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude) {
		try {
			return jsonSuccess(takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile,
					contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_app_exp, task_type_take_and_send, null, null, null, null, null, (byte) 0, null, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商管家创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:14:52</dd>
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 发件人姓名
	 * @param contactMobile 发件人手机
	 * @param contactTel 发件人电话
	 * @param contactAddressId 发件人地址ID
	 * @param contactAddress 发件人详细地址
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 发件人地址的经度信息
	 * @param contactAddrLatitude 发件人地址的纬度信息
	 * @return {"success":true, "taskId":123456789}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/create/ec", method = { RequestMethod.GET , RequestMethod.POST })
	public String commerce(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude, BigDecimal parEstimateWeight, Byte parEstimateCount, Long broadcastId) {
		try {
			return jsonSuccess(takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile,
					contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_ec, task_type_take, null, null, parEstimateWeight, parEstimateCount, null, (byte) 0, broadcastId, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>分配营业分部或收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:15:41</dd>
	 * @param id 任务记录id
	 * @param formCompId 分配方公司id
	 * @param fromMemberId 分配方人员id
	 * @param toCompId 接收方营业分部id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.001 - XXX任务已完成无法分配！</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.002 - 任务分配失败</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.003 - 请选择分配取件员或营业分部</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.004 - 请选择要分配的任务</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/distribute/department", method = {RequestMethod.GET , RequestMethod.POST })
	public String toDepartment(String id, Long formCompId, Long fromMemberId, Long toCompId) {
		try {
			return jsonSuccess(takeTaskService.distribute(id, formCompId, fromMemberId, toCompId, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>分配收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:16:42</dd>
	 * @param id 任务记录id
	 * @param formCompId 分配方公司id
	 * @param fromMemberId 分配方人员id
	 * @param toMemberId 接收方人员id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.001 - XXX任务已完成无法分配！</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.002 - 任务分配失败</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.003 - 请选择分配取件员或营业分部</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.004 - 请选择要分配的任务</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/distribute/member", method = {RequestMethod.GET , RequestMethod.POST })
	public String toMember(String id, Long formCompId, Long fromMemberId, Long toMemberId) {
		try {
			return jsonSuccess(takeTaskService.distribute(id, formCompId, fromMemberId, null, toMemberId, null));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员指派收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:17:30</dd>
	 * @param id 任务记录id
	 * @param fromMemberId 分配方人员id
	 * @param toMemberId 接收方人员id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.001 - XXX任务已完成无法分配！</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.002 - 任务分配失败</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.003 - 请选择分配取件员或营业分部</dd>
	 * <dd>openapi.TakeTaskServiceImpl.distributeTask.004 - 请选择要分配的任务</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/distribute/memToMem", method = {RequestMethod.GET , RequestMethod.POST })
	public String memberToMember(String id, Long fromMemberId, Long toMemberId, Long currentMemberId) {
		try {
			return jsonSuccess(takeTaskService.distribute(id, null, fromMemberId, null, toMemberId, currentMemberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员取消</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:18:22</dd>
	 * @param taskId 任务id
	 * @param memberId 操作人id
	 * @param taskTransmitCause 任务转单原因  1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
	 * @param disposalDesc 取消备注
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.001 - 请选择取消的任务</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancelTask.002 - 任务已经取消不可重复取消</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.004 - 无取消操作人</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.005 - 请选择取消原因</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.006 - 请填写其他原因的备注</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.007 - 任务已经完成不可取消</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel/member", method = {RequestMethod.GET , RequestMethod.POST })
	public String cancelMember(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc) {
		//收派员
		try{
			return jsonSuccess(takeTaskService.cancel(taskId, memberId, taskTransmitCause, disposalDesc, 1, 0));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部取消</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:20:52</dd>
	 * @param taskId 任务id
	 * @param memberId 操作人id
	 * @param taskTransmitCause 任务转单原因  1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
	 * @param disposalDesc 取消备注
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.001 - 请选择取消的任务</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancelTask.002 - 任务已经取消不可重复取消</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.004 - 无取消操作人</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.005 - 请选择取消原因</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.006 - 请填写其他原因的备注</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.007 - 任务已经完成不可取消</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel/department", method = {RequestMethod.GET , RequestMethod.POST })
	public String cancelDepart(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc) {
		//营业分部
		try{
			return jsonSuccess(takeTaskService.cancel(taskId, memberId, taskTransmitCause, disposalDesc, 2, 0));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点取消</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:22:01</dd>
	 * @param taskId 任务id
	 * @param memberId 操作人id
	 * @param taskTransmitCause 任务转单原因  1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
	 * @param disposalDesc 取消备注
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.001 - 请选择取消的任务</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancelTask.002 - 任务已经取消不可重复取消</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.004 - 无取消操作人</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.005 - 请选择取消原因</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.006 - 请填写其他原因的备注</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.007 - 任务已经完成不可取消</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel/exp", method = {RequestMethod.GET , RequestMethod.POST })
	public String cancelExp(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc) {
		//站点
		try{
			return jsonSuccess(takeTaskService.cancel(taskId, memberId, taskTransmitCause, disposalDesc, 3, 0));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员查询所有取件任务(不分页)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:22:45</dd>
	 * @param memberId 收派员id
	 * @param status 0全部 1未完成 2已完成
	 * @param taskType 2自取件
	 * @return {"data":{"resultlist":[{"appointDesc":"","contactAddress":"北京-海淀区 田村路","contactMobile":"13800138000","contactName":"张三","createTime":1415774210000,"memberId":3111394703898098,"taskId":14161141757117440}]},"success":true}
	 *appointDesc 取件备注   contactAddress详细地址 contactMobile联系人手机  contactName联系人姓名  memberId处理人id taskid任务ID createTime创建时间
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/tasknopage", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryTaskByMemNoPage(Long memberId, Integer status, Byte taskType) {		
		//查询收派员未处理任务
		try{
			return jsonSuccess(takeTaskService.queryTaskNoPage(memberId, status, taskType));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询待处理任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:26:36</dd>
	 * @param senderName 发件人
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @param senderPhone 发件人手机号
	 * @param spacetime 持续时间 h1 h2 h2m
	 * @param operatorCompId 操作人公司id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示记录的条数
	 * @return {"data":{"page":{"currentPage":1,"hasFirst":false,"hasLast":false,"hasNext":false,"hasPre":false,
	 * "items":[{"appointDesc":"","compId":13999092876705792,"compName":"营业分部1号站","contactAddress":"北京-昌平区 西环南路44号","contactMobile":"13599999996","contactName":"发到发到v","createTime":1415602435000,"disposalType":0,"id":"14162005477818368","spacetime":"2天38分","taskErrorFlag":0,"taskId":"14116112032858112"}],"offset":0,"pageCount":1,"pageSize":10,"total":1}},"success":true}
	 * appointDesc 预约描述     compId处理站点ID  compName处理站点名字  contactAddress详细地址 contactMobile联系人手机   contactName联系人姓名   disposalType处理类型 0：待处理,1：已分配,2：已完成,3：已取消      taskErrorFlag异常标识 0：正常,1：异常 createTime创建时间 spacetime时间 taskId任务ID
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTask.001 - 当前操作人无所属站点</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/untake", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryTaskUnTake(String senderName, String startTime, String endTime, String senderPhone,
			String spacetime, Long operatorCompId, Integer currentPage, Integer pageSize) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			//0待处理任务
			String msg = jsonSuccess(takeTaskService.queryTask(senderName, startTime, endTime, senderPhone, spacetime, null, null, operatorCompId, page, task_status_untake));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:33:17</dd>
	 * @param senderName 发件人
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @param senderPhone 发件人手机号
	 * @param spacetime 持续时间 h1 h2 h2m
	 * @param taskStatus -1：全部 1：未完成 2：已完成
	 * @param memberId 收派员id
	 * @param compId 营业分部id
	 * @param operatorCompId 操作人公司id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示记录的条数
	 * @return {"data":{"page":{"currentPage":1,"hasFirst":false,"hasLast":false,"hasNext":false,"hasPre":false,
	 * "items":[{"appointDesc":"dcx xcv xc","appointTime":1415683800000,"compId":13999092876705792,"compName":"营业分部1号站","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"13599999999","contactName":"xcvcvxcv","createTime":1415601887000,"disposalType":1,"id":"14115968386596864","memberName":"营业部","memberPhone":"13177700001","spacetime":"2天52分","taskErrorFlag":0,"taskId":"14115968279904256"}],"offset":0,"pageCount":1,"pageSize":10,"total":4}},"success":true}
	 * appointDesc预约描述  appointTime预约时间 compId公司id compName公司名称	contactAddress详细地址 contactMobile联系电话	contactName姓名	createTime创建时间 disposalType处理类型 0：待处理,1：已分配,2：已完成,3：已取消
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTask.001 - 当前操作人无所属站点</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/taketask", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTakeTask(String senderName, String startTime, String endTime, String senderPhone, 
			String spacetime, Byte taskStatus, Long memberId, Long compId, Long operatorCompId, Integer currentPage, Integer pageSize) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			return jsonSuccess(takeTaskService.queryTask(senderName, startTime, endTime, senderPhone, spacetime, memberId, compId, operatorCompId, page, taskStatus));
		}catch(Exception re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取消任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:38:30</dd>
	 * @param senderName 发件人
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @param senderPhone 发件人手机号
	 * @param operatorCompId 操作人公司id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示记录的条数
	 * @return {"data":{"page":{"currentPage":1,"hasFirst":false,"hasLast":false,"hasNext":false,"hasPre":false,
	 * "items":[{"appointDesc":"dcx xcv xc","appointTime":1415683800000,"cancelTime":1415777975000,"compId":13999092876705792,"compName":"营业分部1号站","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"13599999999","contactName":"xcvcvxcv","createTime":1415601887000,"disposalDesc":"123","id":"14115968386596864","memberName":"营业部","spacetime":"2天54分","taskId":"14115968279904256","taskProcessDesc":"123","taskTransmitCause":3}],"offset":0,"pageCount":1,"pageSize":10,"total":1}},"success":true}
	 * appointDesc预约描述  appointTime预约时间	cancelTime取消时间 compId公司ID  compName公司名称  contactAddress详细地址   contactMobile联系电话 contactName收派员姓名  createTime创建时间  disposalDesc处理详情 memberName名称  spacetime间隔时间 taskId任务ID  taskProcessDesc任务进程详情  taskTransmitCause任务转单原因 1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTask.001 - 当前操作人无所属站点</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/refusetask", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryRefuseTask(String senderName, String startTime, String endTime, String senderPhone,
			 Long operatorCompId, Integer currentPage, Integer pageSize) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			//3取消任务
			Map<String,Object> map=takeTaskService.queryTask(senderName, startTime, endTime, senderPhone, null, null, null, operatorCompId, page, task_status_cancel);
			return jsonSuccess(map);
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询任务明细</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:40:00</dd>
	 * @param id 任务记录id
	 * @return {"data":{"resultlist":[{"compName":"营业分部1号站","createTime":1415601887000,"memberName":"营业部","taskProcessDesc":"网点指定任务给：营业部"},{"compName":"营业分部1号站","createTime":1415777975000,"memberName":"营业部","taskProcessDesc":"任务已被取消：123"}],"taskinfo":{"actorMemberName":"营业部","actorMemberPhone":"13177700001","appointDesc":"dcx xcv xc","appointTime":1415683800000,"compId":13999092876705792,"contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactAddressId":11000211,"contactMobile":"13599999999","contactName":"xcvcvxcv","contactTel":"","coopCompName":"营业分部1号站","createTime":1415601887000,"taskFlag":0,"taskId":14115968279904256,"taskIsEnd":0,"taskSource":2,"taskStatus":3,"taskType":0}},"success":true}
	 *compName公司名称	createTime创建时间  memberName名称 taskProcessDesc任务进展详情
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTaskDetail.001 - 查询任务信息失败</dd>
	 * <dd>openapi.TakeTaskServiceImpl.queryTaskDetail.002 - 请选择需要查询的任务记录</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/taskdetail", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTaskDetail(Long id) {
		try{
			String msg = jsonSuccess(takeTaskService.queryTaskDetail(id));
			return msg;
			//return jsonSuccess(takeTaskService.queryTaskDetail(id));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>任务完结</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:40:41</dd>
	 * @param id 任务记录id
	 * @param memberId 操作人id
	 * @param compId 操作网点id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.finishTask.001 - XXX任务已完结无法再次完结</dd>
	 * <dd>openapi.TakeTaskServiceImpl.finishTask.002 - 请选择要完成的任务记录</dd>
	 * <dd>openapi.TakeTaskServiceImpl.finishTask.003 - 当前操作人无所属站点</dd>
	 * <dd>openapi.TakeTaskServiceImpl.finishTask.004 - 当前操作人不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/finish/task", method = { RequestMethod.GET,RequestMethod.POST })
	public String finishTask(Long id, Long memberId, Long compId,Long takeTaskId) {
		try{
			return jsonSuccess(takeTaskService.finishTask(id, memberId, compId, takeTaskId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端完成任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-24 上午11:26:14</dd>
	 * @param taskId  任务id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/finish/taskPersonal", method = { RequestMethod.GET,RequestMethod.POST })
	public String finishTaskPersonal(Long taskId) {
		try{
			return jsonSuccess(takeTaskService.finishTask(null, null, null, taskId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网发件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:41:27</dd>
	 * @param memberId 登录人Id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示数量
	 * @return {"data":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,
	 * "items":[{"appointTime":"","compId":13867330511306752,"compName":"最最一","compPhone":"010-11111111","compType":"已认证","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"15684754555","contactName":"llllllllllll","memberName":"测来源","netName":"微特派","taskId":14071724909789184,"taskStatus":1}],"offset":0,"pageCount":3,"pageSize":10,"total":21},"success":true}
	 * appointTime指派时间 compId公司id  compName公司名称  compPhone公司电话   compType公司类型 contactAddress详细地址contactMobile联系电话contactName收派员姓名
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryOkdiTask.001 - 当前操作人不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/okditask", method = {RequestMethod.GET,RequestMethod.POST })
	public String queryOkdiTask(Long memberId, Integer currentPage, Integer pageSize) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			String msg = jsonSuccess(takeTaskService.queryOkdiTask(memberId, page, (byte) 1));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端发件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-20 下午5:37:10</dd>
	 * @param memberId 登录人Id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示数量
	 * @return {"data":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,"items":[{"appointTime":"","compId":13867330511306752,"compName":"最最一","compPhone":"010-11111111","compType":"已认证","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"15684754555","contactName":"llllllllllll","memberName":"测来源","netName":"微特派","taskId":14071724909789184,"taskStatus":1}],"offset":0,"pageCount":3,"pageSize":10,"total":21},"success":true}
	 *appointTime指派时间 compId公司id  compName公司名称  compPhone公司电话   compType公司类型 contactAddress详细地址contactMobile联系电话contactName收派员姓名
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryOkdiTask.001 - 当前操作人不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/personaltask", method = {RequestMethod.GET,RequestMethod.POST })
	public String queryPersonalTask(Long memberId, Integer currentPage, Integer pageSize) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			String msg = jsonSuccess(takeTaskService.queryOkdiTask(memberId, page, (byte) 4));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信端发件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-3-28 下午5:20:10</dd>
	 * @param memberId 登录人Id
	 * @param currentPage 当前页
	 * @param pageSize 每页显示数量
	 * @return {"data":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,"items":[{"appointTime":"","compId":13867330511306752,"compName":"最最一","compPhone":"010-11111111","compType":"已认证","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"15684754555","contactName":"llllllllllll","memberName":"测来源","netName":"微特派","taskId":14071724909789184,"taskStatus":1}],"offset":0,"pageCount":3,"pageSize":10,"total":21},"success":true}
	 *appointTime指派时间 compId公司id  compName公司名称  compPhone公司电话   compType公司类型 contactAddress详细地址contactMobile联系电话contactName收派员姓名
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryOkdiTask.001 - 当前操作人不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/query/querysendRecordList", method = {RequestMethod.GET,RequestMethod.POST })
	public String querysendRecordList(String memberId, Integer currentPage, Integer pageSize,String netName,String actorMemberName,String actorPhone) {
		try{
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			String msg = jsonSuccess(takeTaskService.queryWechatTask(memberId, page, (byte) 6,netName,actorMemberName,actorPhone));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询收派员或营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:42:59</dd>
	 * @param parentId 站点id
	 * @return {"data":[{"areaColor":"#c2c2c2","compTypeNum":"0","memberId":13998903514106880,"memberName":"营业部","parentId":13999092876705792,"phone":"13177700001","roleId":1}],"success":true}
	 * areaColor片区颜色	roleId 角色 1:收派员,2:后勤,3:站长
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.getMemberInfoByCompId.001 - 当前操作人所属站点不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfoByCompId", method = { RequestMethod.GET,RequestMethod.POST })
	public String getMemberInfoByCompId(Long parentId) {
		try{
			return jsonSuccess(takeTaskService.getMemberInfoByCompId(parentId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网任务明细查询</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:45:13</dd>
	 * @param taskId 任务id
	 * @return {"data":{"taskinfo":{"actorMemberName":"营业部","actorMemberPhone":"13177700001","appointDesc":"","appointTime":1415433600000,"compId":13999092876705792,"compPhone":"13177700001","compType":"已认证","contactAddress":"北京-海淀区 田村路118号","contactAddressId":11000206,"contactMobile":"13800138000","contactName":"aiai","contactTel":"","coopCompName":"营业分部1号站","createTime":1415433429000,"netName":"微特派","taskFlag":0,"taskId":14071808172490752,"taskIsEnd":1,"taskSource":2,"taskStatus":2,"taskType":0}},"success":true}
	 * actorMemberName取件员姓名  actorMemberPhone取件员电话 appointDesc委派详情 appointTime委派时间compId公司ID  compPhone公司电话	 compType公司分类代码 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1050:营业分部 contactAddress详细地址 contactAddressId详细地址ID   contactMobile联系电话	contactName联系姓名 contactTel联系电话 coopCompName任务受理方名称任务标志   taskFlag  0：正常,1：抢单 taskId任务ID  taskIsEnd任务是否结束     taskSOURCE任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryOkdiTaskDetail.001 - 查询任务信息失败</dd>
	 * <dd>openapi.TakeTaskServiceImpl.queryOkdiTaskDetail.002 - 请选择要查询任务信息</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/okdiTaskDetail", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryOkdiTaskDetail(Long taskId) {
		try{
			return jsonSuccess(takeTaskService.queryOkdiTaskDetail(taskId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:46:59</dd>
	 * @param compId 站点id
	 * @return {"data":{"num":5},"success":true}
	 * num数量
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTaskUnTakeCount.001 - 当前操作人所属站点不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTaskUnTakeCount", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTaskUnTakeCount(Long compId) {
		try{
			return jsonSuccess(takeTaskService.queryTaskUnTakeCount(compId, task_status_untake));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王更新客户数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:48:24</dd>
	 * @param taskId 任务id
	 * @param contactName 发件人姓名
	 * @param contactMobile 发件人手机
	 * @param contactTel 发件人座机
	 * @param contactAddressId 发件人地址id
	 * @param contactAddress 地址详情 (北京-海淀区 田村路)
	 * @param customerId 客户id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.updateContacts.001 - 请选择要更新的任务信息</dd>
	 * <dd>openapi.TakeTaskServiceImpl.updateContacts.002 - 请输入发件人姓名</dd>
	 * <dd>openapi.TakeTaskServiceImpl.updateContacts.003 - 请输入发件人手机号</dd>
	 * <dd>openapi.TakeTaskServiceImpl.updateContacts.004 - 请选择发件地址</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateContacts", method = { RequestMethod.GET,RequestMethod.POST })
	public String updateContacts(Long taskId, String contactName, String contactMobile, String contactTel, Long contactAddressId, String contactAddress, Long customerId) {
		try{
			return jsonSuccess(takeTaskService.updateContacts(taskId, contactName, contactMobile, contactTel, contactAddressId, contactAddress, customerId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网客户取消任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:43:21</dd>
	 * @param taskId 任务id
	 * @param memberId 客户id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.001 - 请选择取消的任务</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancelTask.002 - 任务已经取消不可重复取消</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.004 - 无取消操作人</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.005 - 请选择取消原因</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.006 - 请填写其他原因的备注</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.007 - 任务已经完成不可取消</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelTaskOkdi", method = { RequestMethod.GET,RequestMethod.POST })
	public String cancelTaskOkdi(String taskId, Long memberId) {
		try{
			return jsonSuccess(takeTaskService.cancel(taskId, memberId, (byte) 1, "", -1, 1));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商客户取消任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:43:21</dd>
	 * @param taskId 任务id
	 * @param memberId 客户id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.001 - 请选择取消的任务</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancelTask.002 - 任务已经取消不可重复取消</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.004 - 无取消操作人</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.005 - 请选择取消原因</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.006 - 请填写其他原因的备注</dd>
	 * <dd>openapi.TakeTaskServiceImpl.cancel.007 - 任务已经完成不可取消</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelTaskAmssy", method = { RequestMethod.GET,RequestMethod.POST })
	public String cancelTaskAmssy(String taskId) {
		try{
			return jsonSuccess(takeTaskService.cancel(taskId, -1l, (byte) 1, "", -1, 1));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过地址查询收派员或营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 下午2:24:46</dd>
	 * @param compId 站点id
	 * @param addrLongitude 经度
	 * @param addrLatitude 纬度
	 * @return {"data":{"compId":123456789,"memberId":123456789,"compName":站点,"memberName":人名,"compType":1050},"success":true}
	 * compId公司Id  memberId人ID   compName公司名称	memberName人名   compType公司分类代码 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1050:营业分部
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.getCompOrEmployee.001 - 当前操作人所属站点不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompOrEmployeeByAddr", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryCompOrEmployeeByAddr(Long compId, BigDecimal addrLongitude, BigDecimal addrLatitude) {
		try{
			return jsonSuccess(takeTaskService.getCompOrEmployee(compId, addrLongitude, addrLatitude));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员或营业分部是否可以解除关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-26 下午5:39:01</dd>
	 * @param compId 营业分部id
	 * @param memberId 收派员id
	 * @return 0可以解除，1不可解除
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTaskUnFinished", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryTaskUnFinished(Long compId, Long memberId) {
		try{
			int result = takeTaskService.queryTaskUnFinished(compId, memberId);
			return String.valueOf(result);
		}catch(RuntimeException re){
			re.printStackTrace();
			return "1";
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新取件任务信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-26 下午5:57:51</dd>
	 * @param taskId 任务id
	 * @param memberId 创建人id
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.updateTaskInfoForOkdi.001 - 请选择要更新的任务信息</dd>
	 * <dd>openapi.TakeTaskServiceImpl.updateTaskInfoForOkdi.002 - 当前操作人不存在</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTaskInfoForOkdi", method = { RequestMethod.GET,RequestMethod.POST })
	public String updateTaskInfoForOkdi(Long taskId, Long memberId) {
		try{
			return jsonSuccess(takeTaskService.updateTaskInfoForOkdi(taskId, memberId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}

	
	
	
	
	
//	 * @param fromCompId 任务受理方站点
//	 * @param fromMemberId 任务受理人员
//	 * @param toMemberId 收派员
//	 * @param coopNetId 任务受理方网络
//	 * @param appointDesc 取件备注
//	 * @param actorMemberId 执行人员
//	 * @param contactName 发件人姓名
//	 * @param contactMobile 发件人手机
//	 * @param contactTel 发件人电话
//	 * @param contactAddress 发件人详细地址
//	 * @param createUserId 创建人ID --这个参数没有用到
//	 * @param contactAddrLongitude 发件人地址的经度信息
//	 * @param contactAddrLatitude 发件人地址的纬度信息
//	 * @param openId 微信号
//	 * @return {"success":true, "taskId":123456789}
//	 * <dt><span class="strong">异常:</span></dt>
//	 * <dd>openapi.TakeTaskServiceImpl.create.001 - 请输入发件人姓名</dd>
//	 * <dd>openapi.TakeTaskServiceImpl.create.002 - 请输入发件人手机号</dd>
//	 * <dd>openapi.TakeTaskServiceImpl.create.003 - 请选择发件地址</dd>
	
	@ResponseBody
	@RequestMapping(value = "/createTaskIgomo", method = { RequestMethod.GET,RequestMethod.POST })
	public String createTaskIgomo(Long fromCompId, Long coopNetId,String appointDesc,Long actorMemberId,String contactName,String contactMobile
			,String contactAddress,BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,
			Long memberId,String parcelStr,Byte taskFlag,Integer howFast,Byte parEstimateCount,Long assignNetId) {
		if(PubMethod.isEmpty(parEstimateCount)){
			parEstimateCount=1;
		}
		try{
			return jsonSuccess(takeTaskService.createTaskIgomo(fromCompId, coopNetId, appointDesc, actorMemberId, 
					contactName, contactMobile, contactAddress, contactAddrLongitude, contactAddrLatitude,actorPhone,
					openId,taskSource,memberId,parcelStr,taskFlag,howFast,parEstimateCount,assignNetId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}

	/**
	 * 微信取消叫快递---马建鑫
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelWechatOrder", method = { RequestMethod.GET,RequestMethod.POST })
	public String cancelWechatOrder(Long taskId){
		try{
			takeTaskService.cancelWechatOrder(taskId);
			return jsonSuccess();
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询快递员取件管理列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-5 下午2:26:36</dd>
	 * @param taskSource 任务来源
	 * @param netName 快递网络
	 * @param taskStatus 任务状态
	 * @param contactAddress 寄件人地区
	 * @param contactMobile 寄件人手机
	 * @param actorPhone 快递员手机号
	 * @param currentPage 当前页
	 * @param pageSize 每页显示记录的条数
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return {"data":{"page":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,"items":
	 * [{"DATE_FORMAT(pti":{"create_time,'%Y-%m-%d %H:%i:%s')":"2015-01-07 18:23:17"},"actor_phone":"13552751530",
	 * "comp_name":"田村3","contact_address":"北京海淀区 田村路43号","contact_mobile":"18311293752","contact_name":"王小二",
	 * "member_name":"管理员","net_name":"顺丰速运","task_source":6,"task_status":11}],"offset":0,"pageCount":59135,"pageSize":1,"rows":[],"total":59135}},"success":true}
	 * 返回值:
	 * 来源 			task_source	
	 * 寄件人手机号 	contact_mobile	
	 * 寄件人姓名		contact_name
	 * 寄件人所在地区	contact_address
	 * 寄件人详细地址  contact_address	
	 * 快递员姓名 	 member_name
	 * 快递员手机号 	actor_phone	
	 * 快递网络  		net_name
	 * 快递站点(连表)	comp_name	
	 * 下单时间 		create_time
	 * 状态 			task_status
	 * 
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.TakeTaskServiceImpl.queryTask.001 - 当前操作人无所属站点</dd>
	 * @since v1.0
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/query/taskList", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryTaskList(String taskSource,String netName,String taskStatus, String contactAddress, String contactMobile,String actorPhone,
			Integer currentPage, Integer pageSize, String startTime, String endTime) {
		
		try{
			
			String msg = jsonSuccess(takeTaskService.queryTask(taskSource,netName,taskStatus,contactAddress,contactMobile,actorPhone,startTime,endTime,currentPage,pageSize));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/net/id", method = {RequestMethod.GET , RequestMethod.POST })
	public String getNetInfoByNetId(Long netId) {
		try {
			return jsonSuccess(takeTaskService.getNetInfoByNetId(netId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/rob", method = {RequestMethod.GET , RequestMethod.POST })
	public String rob(Long memberId, String netName, String netId, String phone, String mname, Long taskId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(netName)) {
			return paramsFailure("002", "netName不能为空");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("003", "netId不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return paramsFailure("004", "phone不能为空");
		}
		if (PubMethod.isEmpty(mname)) {
			return paramsFailure("005", "mname不能为空");
		}
		if (PubMethod.isEmpty(taskId)) {
			return paramsFailure("006", "taskId不能为空");
		}
		try {
			takeTaskService.wechatRob(memberId, netName, netId, phone, mname, taskId);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
}





