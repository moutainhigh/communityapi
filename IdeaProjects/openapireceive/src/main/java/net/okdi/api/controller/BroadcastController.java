/**  
 * @Project: openapi
 * @Title: BroadcastController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2015-1-17 上午09:59:54
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.okdi.api.service.BroadcastListService;
import net.okdi.api.service.BroadcastService;
import net.okdi.api.vo.VO_broadcastCreateTask;
import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/broadcast")
public class BroadcastController extends BaseController{
	//任务来源
	final Byte task_source_ec = 3; //电商管家
	final Byte task_source_app_personal = 4; //好递个人端
	
	
	@Autowired
	private BroadcastService broadcastService;
	@Autowired
	private BroadcastListService broadcastListService;
    
	
	@RequestMapping("addBroadcastOwn")
	@ResponseBody
    public String addBroadcastOwn(String jsonString){
    	try {
    		return broadcastService.addBroadcastOwn(jsonString);	
		} catch (Exception e) {
		return this.jsonFailure(e);
		}
    	
    }
	@RequestMapping("addBroadcastOwnOperation")
	@ResponseBody
    public String addBroadcastOwnOperation(String jsonString){
    	try {
    		return broadcastService.addBroadcastOwnOperation(jsonString);	
		} catch (Exception e) {
		return this.jsonFailure(e);
		}
    	
    }

	@RequestMapping("addBroadcast")
	@ResponseBody
    public String addBroadcast(String jsonString,Long memberId){
    	try {
    		return broadcastService.addBroadcast(jsonString,memberId);	
		} catch (Exception e) {
		return this.jsonFailure(e);
		}
    }
	
	@RequestMapping("addBroadcastOperation")
	@ResponseBody
    public String addBroadcastOperation(String jsonString,Long memberId){
    	try {
    		return broadcastService.addBroadcastOperation(jsonString,memberId);	
		} catch (Exception e) {
		return this.jsonFailure(e);
		}
    }
	

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午10:01:41</dd>
	 * @param loginMemberId 登录人id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryBroadcastList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public String queryBroadcastList(Long loginMemberId){
    	try {
    		return jsonSuccess(broadcastListService.queryBroadcastList(loginMemberId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
    }
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播详情(显示包裹列表，抢单列表)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午10:06:23</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryBroadcastDetail", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public String queryBroadcastDetail(Long broadcastId){
    	try {
    		return jsonSuccess(broadcastListService.doQueryBroadcastDetail(broadcastId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
    }
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午10:09:20</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/cancelBroadcast", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String cancelBroadcast(Long broadcastId){
		try {
    		return jsonSuccess(broadcastListService.cancelBroadcast(broadcastId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播超时</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午10:10:43</dd>
	 * @param broadcastId 广播超时
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/timeoutBroadcast", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String timeoutBroadcast(Long broadcastId){
		try {
    		return jsonSuccess(broadcastListService.timeoutBroadcast(broadcastId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商抢单创建取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:42:19</dd>
	 * @param broadcastParam json格式参数
	 * @return
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.BroadcastController.createTaskEC.001 - 创建预约失败</dd>
	 * <dd>openapi.BroadcastListServiceImpl.createTakeTask.001 - 请选择要创建预约的广播数据</dd>
	 * <dd>openapi.BroadcastListServiceImpl.createTakeTask.002 - 请选择要创建预约的报价数据</dd>
	 * <dd>openapi.BroadcastListServiceImpl.createTakeTask.003 - 创建取件任务失败</dd>
	 * <dd>openapi.BroadcastListServiceImpl.createTakeTask.004 - 广播不存在，创建预约失败</dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/createTask/ec", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String createTaskEC(String broadcastParam){
		try {
			VO_broadcastCreateTask taskVO = null;
			if(!PubMethod.isEmpty(broadcastParam)) {
				taskVO = JSON.parseObject(broadcastParam, VO_broadcastCreateTask.class);
			}
			if(!PubMethod.isEmpty(taskVO)) {
				return jsonSuccess(broadcastListService.createTakeTask(taskVO.getBroadcastId(), taskVO.getQuotationId(), taskVO.getAppointTime(), taskVO.getContactTel(), task_source_ec, (byte) 1, null));
			} else {
				throw new ServiceException("openapi.BroadcastController.createTaskEC.001", "创建预约失败");
			}
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端抢单创建取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:45:16</dd>
	 * @param broadcastId 广播id 必须
	 * @param quotateId  报价id 必须
	 * @param appointTime 预约取件时间
	 * @param contactTel 发件人座机电话
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/createTask/personal", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String createTaskPersonal(Long broadcastId, Long quotateId, String appointTime, String contactTel, String senderPhone){
		try {
			return jsonSuccess(broadcastListService.createTakeTask(broadcastId, quotateId, appointTime, contactTel, task_source_app_personal, (byte) 2, senderPhone));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除广播(取消或超时状态下可删除)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:50:29</dd>
	 * @param broadcastId 广播id
	 * @param loginMemberId 登录人id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/deleteBroadcast", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteBroadcast(Long broadcastId, Long loginMemberId){
		try {
			return jsonSuccess(broadcastListService.deleteBroadcast(broadcastId, loginMemberId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:51:50</dd>
	 * @param taskId 任务id
	 * @param loginMemberId 登录人id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/deleteTakeTask", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteTakeTask(Long taskId, Long loginMemberId){
		try {
			return jsonSuccess(broadcastListService.deleteTakeTask(taskId, loginMemberId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询任务详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午3:56:05</dd>
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryTaskDetail", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String queryTaskDetail(Long taskId){
		try {
			return jsonSuccess(broadcastListService.queryTaskDetail(taskId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午6:56:18</dd>
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件人电话
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/scanParcel", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String ScanParcel(Long id, Long taskId, String expWaybillNum, String phone){
		try {
			return jsonSuccess(broadcastListService.scanParcel(id, taskId, expWaybillNum, phone));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端确认交寄创建包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:25:14</dd>
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件电话
	 * @param addresseeAddressId 收件地址id
	 * @param addresseeAddress 收件地址
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/broadcastCreateParcelPersonal", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String broadcastCreateParcelPersonal(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress){
		try {
			return jsonSuccess(broadcastListService.broadcastCreateParcel(taskId, expWaybillNum, phone, addresseeAddressId, addresseeAddress, (short) 2));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商确认交寄创建包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:25:14</dd>
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件电话
	 * @param addresseeAddressId 收件地址id
	 * @param addresseeAddress 收件地址
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/broadcastCreateParcelEC", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String broadcastCreateParcelEC(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress){
		try {
			return jsonSuccess(broadcastListService.broadcastCreateParcel(taskId, expWaybillNum, phone, addresseeAddressId, addresseeAddress, (short) 1));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:38:45</dd>
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/deleteParcel", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteParcel(Long id, Long taskId){
		try {
			return jsonSuccess(broadcastListService.deleteParcel(id, taskId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播重新发起</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午5:21:52</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/broadcastRestart", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String broadcastRestart(Long broadcastId){
		try {
			return jsonSuccess(broadcastListService.broadcastRestart(broadcastId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取件任务重启发起广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午5:23:30</dd>
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/broadcastRestartForTask", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String broadcastRestartForTask(Long taskId){
		try {
			return jsonSuccess(broadcastListService.broadcastRestartForTask(taskId));
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
}