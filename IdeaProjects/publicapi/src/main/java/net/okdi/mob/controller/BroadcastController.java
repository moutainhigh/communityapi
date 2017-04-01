/**  
 * 发抢单
 * @Project: publicapi
 * @Title: BroadcastController.java
 * @Package net.okdi.mob.controller
 * @author amssy
 * @date 2015-1-16 下午01:54:06
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.controller;

import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.BroadcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人端发抢单等
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/broadcast")
public class BroadcastController {

	@Autowired
	BroadcastService broadcastService;
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端发抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-20 下午12:52:30</dd>
	 * @param jsonString
	 	{
			"parcelValues": //包裹信息参数
			 [
			        {   "addresseeAddressId": "123123123",
			            "addresseeAddress": "北京市-海淀区|田村路43号",//收件地址
			            "chareWeightForsender": "0.5",                     //包裹重量
			            "addresseeName":"测试",//收件人姓名
			            "addresseeMobile":"13201254896"//收件人电话
			        },
			        {   "addresseeAddressId": "123123123",
			            "addresseeAddress": "北京市-海淀区|田村路43号"//收件地址,
			            "chareWeightForsender": "0.5",                  //包裹重量
			            "addresseeName":"测试",//收件人姓名
			            "addresseeMobile":"13201254896"//收件人电话
			        }
			],
			"senderAddressName": "北京市-海淀区|田村43号",                    //发件地址名
			"senderAddressId": "100808",                            //发件地址ID
			    "broadcastRemark": "备注",                             //备注
			    "loginMemberId": "123123123",                           //登陆人ID          
			    "senderLatitude": "123.123",                            //发件人精度
			    "senderLongitude": "123.123",                           //发件人纬度
			    "senderMobile": "13111111111",                       //发件人电话
			    "senderName": "张三",                                   //发件人姓名
			    "totalCount": "5",                                        //包裹总数量
			    "totalWeight": "1"                                        //包裹总重量
		}
		
		注意： 除senderName外均必填
	 * @return	{"data":{"id":"123123123"},"success":true}
	 * @since v1.0
	 */
	@RequestMapping("addBroadcastOwn")
	@ResponseBody
	public String addBroadcastOwn (String jsonString){
		try {
			String result = broadcastService.addBroadcastOwn(jsonString);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>运营数据，只是运营数据，手机端不调用</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-20 下午12:52:30</dd>
	 * @param jsonString
	 	{
			"parcelValues": //包裹信息参数
			 [
			        {   "addresseeAddressId": "123123123",
			            "addresseeAddress": "北京市-海淀区|田村路43号",//收件地址
			            "chareWeightForsender": "0.5"                     //包裹重量
			        },
			        {   "addresseeAddressId": "123123123",
			            "addresseeAddress": "北京市-海淀区|田村路43号"//收件地址,
			            "chareWeightForsender": "0.5"                    //包裹重量
			        }
			],
			"senderAddressName": "北京市-海淀区|田村43号",                    //发件地址名
			"senderAddressId": "100808",                            //发件地址ID
			    "broadcastRemark": "备注",                             //备注
			    "loginMemberId": "123123123",                           //登陆人ID          
			    "senderLatitude": "123.123",                            //发件人精度
			    "senderLongitude": "123.123",                           //发件人纬度
			    "senderMobile": "13111111111",                       //发件人电话
			    "senderName": "张三",                                   //发件人姓名
			    "totalCount": "5",                                        //包裹总数量
			    "totalWeight": "1"                                        //包裹总重量  
		}
		
		注意： 除senderName外均必填
	 * @return	{"data":{"id":"123123123"},"success":true}
	 * @since v1.0
	 */
	@RequestMapping("addBroadcastOwnOperation")
	@ResponseBody
	public String addBroadcastOwnOperation (String jsonString){
		try {
			String result = broadcastService.addBroadcastOwnOperation(jsonString);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午2:18:31</dd>
	 * @param loginMemberId 登录人id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryBroadcastList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String queryBroadcastList(Long loginMemberId){
		
		try {
			String result = broadcastService.queryBroadcastList(loginMemberId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询广播详情(显示包裹列表，抢单列表)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午3:55:50</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryBroadcastDetail", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String queryBroadcastDetail(Long broadcastId){
		
		try {
			String result = broadcastService.queryBroadcastDetail(broadcastId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午4:09:09</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/cancelBroadcast", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String cancelBroadcast(Long broadcastId){
		try {
			String result = broadcastService.cancelBroadcast(broadcastId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播超时</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午4:10:03</dd>
	 * @param broadcastId 广播id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/timeoutBroadcast", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String timeoutBroadcast(Long broadcastId){
		try {
			String result = broadcastService.timeoutBroadcast(broadcastId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}

	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商抢单创建取件任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午5:42:19</dd>
	 * @param broadcastParam json格式参数
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/createTask/ec", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String createTaskEC(String broadcastParam){
		try {
			String result = broadcastService.createTaskEC(broadcastParam);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
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
	 * @param senderPhone 发件人手机号
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/createTask/personal", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String createTaskPersonal(Long broadcastId, Long quotateId, String appointTime, String contactTel, String senderPhone){
		try {
			String result = broadcastService.createTaskPersonal(broadcastId, quotateId, appointTime, contactTel, senderPhone);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
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
			String result = broadcastService.deleteBroadcast(broadcastId, loginMemberId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
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
			String result = broadcastService.deleteTakeTask(taskId, loginMemberId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>确认待交寄列表</dd>
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
			String result = broadcastService.queryTaskDetail(taskId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
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
	public String scanParcel(Long id, Long taskId, String expWaybillNum, String phone){
		try {
			String result = broadcastService.scanParcel(id, taskId, expWaybillNum, phone);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端确认交寄创建包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:56:05</dd>
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
	public String broadcastCreateParcelPersonal(Long taskId, String expWaybillNum, String phone,
			Long addresseeAddressId, String addresseeAddress){
		try {
			String result = broadcastService.broadcastCreateParcelPersonal(taskId, expWaybillNum, phone,
					addresseeAddressId, addresseeAddress);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商确认交寄创建包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:56:05</dd>
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
		String result = broadcastService.broadcastCreateParcelEC(taskId, expWaybillNum, phone, addresseeAddressId, addresseeAddress);
		return result;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午3:08:34</dd>
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/deleteParcel", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteParcel(Long id, Long taskId){
		String result = broadcastService.deleteParcel(id, taskId);
		return result;
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
			String result = broadcastService.broadcastRestart(broadcastId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
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
			String result = broadcastService.broadcastRestartForTask(taskId);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
}