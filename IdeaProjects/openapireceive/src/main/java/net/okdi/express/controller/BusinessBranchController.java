package net.okdi.express.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.service.BusinessBranchService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/businessBranch")
public class BusinessBranchController extends BaseController{
	@Autowired
	private BusinessBranchService businessBranchService;
	@Autowired
	private TakeTaskService takeTaskService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过营业分部名称和营业分部站长手机号查询出营业分部信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:18:40</dd>
	 * @param parentCompId  --站点compId
	 * @param netId  --网络id
	 * @param memberPhone  --营业分部站长手机号
	 * @param compName  --营业分部名称
	 * @return
		{
		    "data": {
		        "address": "第一个营业分部地址",  --地址
		        "compAddress": "北京-海淀区-海淀镇|第一个营业分部地址",  --详细地址
		        "compId": 129403986427904,  --营业分部compid
		        "compName": "一一营业分部",  --营业分部名称
		        "compTypeNum": "1050",  --营业分部类型
		        "responsible": "贺海峰",  --负责人
		        "responsibleTelephone": "13111111112"  -- 负责人手机号
		    },
		    "success": true
		}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByParentCompId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByParentCompId(Long parentCompId,Long netId,String memberPhone,String compName){
		try {
			return jsonSuccess(this.businessBranchService.queryCompInfoByParentCompId(compName, memberPhone, parentCompId,netId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过站点名称和站长手机号查询出营业分部信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午4:06:33</dd>
	 * @param compId  --营业分部compId
	 * @param netId  --网络id
	 * @param memberPhone  --站长手机号
	 * @param compName  --站点名称
	 * @return
		{
		    "data": {
		        "address": "第一个站点地址",
		        "compAddress": "北京-海淀区-东小口镇|第一个站点地址",
		        "compName": "一一站点",
		        "compTypeNum": "1006",
		        "parentCompId": 129403046903808,
		        "responsible": "第一个站点的贺海峰",
		        "responsibleTelephone": "13122223333"
		    },
		    "success": true
		}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByCompId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByCompId(Long compId,Long netId,String memberPhone,String compName){
		try {
			return jsonSuccess(this.businessBranchService.queryCompInfoByCompId(compName, memberPhone, compId,netId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询为空给该手机号发送邀请短信</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-9 下午1:12:39</dd>
	 * @param memberPhone   --手机号
	 * @param compName  --当前登录的站点或营业分部名称
	 * @param compId  --当前登录的站点或营业分部comPid
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/sendInvitationMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendInvitationMessage(String compTypeNum,String memberPhone,String compName,Long compId,Long logId){
		try {
			this.businessBranchService.sendInvitationMessage(compTypeNum,memberPhone, compName, compId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点关联营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:30:12</dd>
	 * @param userId
	 * @param parentCompId
	 * @param compId
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createCompRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String createCompRelation(Long userId,Long parentCompId,Long compId,Long netId){
		try {
			this.businessBranchService.createCompRelation(userId,parentCompId,compId,netId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部关联站点</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:30:40</dd>
	 * @param userId
	 * @param parentCompId
	 * @param compId
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addCompRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String addCompRelation(Long logId,Long userId,Long parentCompId,Long compId,Long netId){
		try {
			this.businessBranchService.addCompRelation(logId,userId,parentCompId,compId,netId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部申请关联弹框之前需要判断该营业分部是否已经被某站点加为下属营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-12 下午1:50:49</dd>
	 * @param compId  --营业分部id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/isThrough", method = { RequestMethod.POST, RequestMethod.GET })
	public String isThrough(Long compId){
		try {
			return jsonSuccess(this.businessBranchService.isThrough(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点管理营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:30:51</dd>
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryConsumingBranch", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryConsumingBranch(Long parentCompId){
		try {
			return jsonSuccess(this.businessBranchService.queryConsumingBranch(parentCompId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点待审核营业分部列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:31:12</dd>
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryUnConsumingBranch", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryUnConsumingBranch(Long parentCompId){
		try {
			return jsonSuccess(this.businessBranchService.queryUnConsumingBranch(parentCompId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点审核营业分部</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:31:26</dd>
	 * @param logId
	 * @param flag
	 * @param compId
	 * @param parentCompId
	 * @param netId
	 * @param userId
	 * @param refuseDesc
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/auditBranch", method = { RequestMethod.POST, RequestMethod.GET })
	public String  auditBranch(Long logId,Short flag,Long compId,Long parentCompId,Long netId,Long userId,String refuseDesc){
	
		try {
			this.businessBranchService.auditBranch(logId,flag,compId,parentCompId,netId,userId,refuseDesc);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点解除关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:31:39</dd>
	 * @param relationId
	 * @param compId
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/relieveRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String relieveRelation(Long relationId,Long compId,Long parentCompId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			int  flag =takeTaskService.queryTaskUnFinished(compId,null);
			if(flag ==0){
				this.businessBranchService.relieveRelation(relationId,compId,parentCompId);
				map.put("isFinish", 0);
				return jsonSuccess(map);
			}else{
				map.put("isFinish", 1);
				return jsonSuccess(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部更改关联</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:31:55</dd>
	 * @param logId
	 * @param compId
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCompRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateCompRelation(Long logId,Long compId,Long parentCompId){
		try {
			this.businessBranchService.updateCompRelation(compId,parentCompId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部解除关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:32:09</dd>
	 * @param logId
	 * @param compId
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCompRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteCompRelation(Long logId,Long compId,Long parentCompId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			int  flag =takeTaskService.queryTaskUnFinished(compId,null);
			if(flag ==0){
				this.businessBranchService.deleteCompRelation(logId,compId,parentCompId);
				map.put("isFinish", 0);
				return jsonSuccess(map);
			}else{
				map.put("isFinish", 1);
				return jsonSuccess(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改片区颜色</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午4:56:06</dd>
	 * @param relationId
	 * @param areaColor
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/editAreaColor", method = { RequestMethod.POST, RequestMethod.GET })
	public String editAreaColor(Long relationId,String areaColor,Long parentCompId){
		try {
			this.businessBranchService.editAreaColor(relationId,areaColor,parentCompId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部查询关联的上级站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午6:32:31</dd>
	 * @param compId
	 * @return
			{
	    "data": {
	        "compId": 129403986427904,
	        "compName": "一一站点",
	        "parentCompId": 129403046903808,
	        "status": 1
	    },
	    "success": true
	}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRelationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRelationInfo(Long compId){
		try {
			return jsonSuccess(this.businessBranchService.queryRelationInfo(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
}