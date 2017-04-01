package net.okdi.apiV4.controller;

import java.util.Date;

import net.okdi.apiV4.service.QueryTaskServiceV4;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/takeTask")
public class QueryTaskControllerV4 extends BaseController {

	@Autowired
	private QueryTaskServiceV4 takeTaskService;

	public static final Log logger = LogFactory.getLog(QueryTaskControllerV4.class);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>运营平台查询快递员取件管理列表之mongodb</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-4-29 下午2:26:36</dd>
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
	public String queryTaskList(Byte taskSource,String netName,Byte taskStatus, String contactAddress, String contactMobile,String actorPhone,
			Integer currentPage, Integer pageSize, Date startTime, Date endTime) {
		
		try{
			String msg = jsonSuccess(takeTaskService.queryTask(taskSource,netName,taskStatus,contactAddress,contactMobile,actorPhone,startTime,endTime,currentPage,pageSize));
			return msg;
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * @Description: 微信批量清除发件记录
	 * @param taskIds 任务id,多个用逗号分隔
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-17
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteWechatSendRecords", method = {RequestMethod.GET , RequestMethod.POST })
	public String deleteWechatSendRecords(String taskIds) {
		if(PubMethod.isEmpty(taskIds)){
			return paramsFailure("net.okdi.apiV4.controller.QueryTaskControllerV4.deleteWechatSendRecords.01", "taskIds不能为空!");
		}
		try{
			takeTaskService.deleteWechatSendRecords(taskIds);
			return jsonSuccess();
		}catch(RuntimeException re){
			re.printStackTrace();
			return jsonFailure(re);
		}
	}
}
