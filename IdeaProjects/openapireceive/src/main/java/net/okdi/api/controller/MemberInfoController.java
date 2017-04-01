package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendTaskService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.api.vo.VO_SiteMemberInfo;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 人员信息管理接口模块
 * 
 * @author haifeng.he
 * @version V1.0
 */
@Controller
@RequestMapping("/memberInfo")
public class MemberInfoController extends BaseController {
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	SendTaskService sendTaskService;
	public static final Log logger = LogFactory.getLog(MemberInfoController.class);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点系统人员查询列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午1:19:04</dd>
	 * @param logMemberId
	 *            当前登录用户的memberId
	 * @param compId
	 *            网点id
	 * @return { "areaColor": "#c2c2c2", "compId": 13867330511306752,
	 *         "employeeWorkStatusFlag": 1, 工作状态 "id_num": "230121198203215698",
	 *         "memberId":13867278975369216, "memberName": "管理员", "memberPhone":
	 *         "13177770001", "memberSource": 1, "roleId": 1 } <dd>areaColor -
	 *         片区颜色</dd> <dd>compId - 网点id</dd> <dd>employeeWorkStatusFlag -
	 *         工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单</dd> <dd>id_num - 身份证号</dd> <dd>
	 *         memberId - 人员id</dd> <dd>memberName - 人员姓名</dd> <dd>memberPhone -
	 *         人员手机号</dd> <dd>memberSource - 人员来源 0:手机端1:站点</dd> <dd>roleId -
	 *         角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryMemberInfo.001 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberInfo(Long logMemberId, Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberInfo.001", "compId不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<VO_MemberInfo> list = ehcacheService.get("employeeCache", compId.toString(),
				ArrayList.class);
		if (!PubMethod.isEmpty(list)) {
			map.put("list", list);
			map.put("logMemberId", logMemberId);
			return jsonSuccess(map);
		} else {
			map.put("compId", compId);
			list = this.memberInfoService.queryEmployeeCache(compId);
			map.put("list", list);
			map.put("logMemberId", logMemberId);
			ehcacheService.put("employeeCache", compId.toString(), list);
			return jsonSuccess(map);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询bas_employee_relation关系表中是否存在memberId与comId的记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午1:21:41</dd>
	 * @param memberId
	 *            人员id
	 * @param roleId
	 *            角色id
	 * @param compId
	 *            网点id
	 * @return {"data":[{"compId":13752423284211712, -- 网点id
	 *         "memberId":3111394703898098, --人员id "roleId":1}], --角色id -1: 后勤0
	 *         : 收派员1 : 大站长 -2：小站长 "success":true}
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryCountById.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.queryCountById.002 -compId不能为空！</dd>
	 *<dt></span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryCountById.003 -memberId不能为空！</dd>
	 *<dt></span></dt> <dd>net.okdi.api.controller.MemberInfoController.doAddMemberToComp.002 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCountById", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCountById(Long memberId, Short roleId, Long compId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryCountById.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryCountById.002", "compId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryCountById.003", "roleId不能为空！");
		}
		try {
			List<BasEmployeeRelation> list = this.memberInfoService.queryCountById(memberId,
					roleId, compId);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员履历信息</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:03:26</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @return { "compAddress": "北京-海淀区-东小口镇|最最一", 网点地址 "compId":
	 *         13867330511306752, 网点id "compName": "最最一", 网点名字 "compTelephone":
	 *         "010-11111111", 网点联系电话 "createTime": "2014-11-03", 注册时间
	 *         "employeeUserName": "通通通", "employee_user_id": 13954038301985792,
	 *         人员id "endTime": "2014-11-08", 离职时间 "netId": 2071, 网络id "netName":
	 *         "微特派" 网络名称 }
	 *<dd>net.okdi.api.controller.MemberInfoController.queryMemberResume.001 -memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMemberResume", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberResume(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberResume.001", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.queryMemberResume(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加人员接口</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:06:54</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @param associatedNumber
	 *            手机号
	 * @param memberName
	 *            人员姓名
	 * @param roleId
	 *            注册角色id
	 * @param areaColor
	 *            片区颜色
	 * @param userId
	 *            审核人
	 * @param memberSource
	 *            来源 0手机端1站点
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.001 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.002 -associatedNumber不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.003 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.004 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.005 -userId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.006 -memberSource不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.007 -memberSource参数错误！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String addMemberInfo(Long compId, String associatedNumber, String memberName,
			Short roleId, String areaColor, Long userId, Short memberSource) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.001", "compId不能为空！");
		}
		if(PubMethod.isEmpty(associatedNumber)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.002", "associatedNumber不能为空！");
		}
		if(PubMethod.isEmpty(memberName)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.003", "memberName不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.004", "roleId不能为空！");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.005", "userId不能为空！");
		}
		if(PubMethod.isEmpty(memberSource)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.006", "memberSource不能为空！");
		}
		if(memberSource !=0 &&memberSource !=1){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.007", "memberSource参数错误！");
		}
		if(roleId==1 || roleId==2){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.008", "roleId参数错误！");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if(PubMethod.isEmpty(this.memberInfoService.queryCountById(userId, null, compId))){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.009", "用户权限错误！");
			}
			if(roleId == -2){
				roleId = 0 ;
			}
			this.memberInfoService.addMemberInfo(compId, associatedNumber, memberName, roleId,
					areaColor, userId, memberSource);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改人员信息</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:10:04</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @param roleId
	 *            角色id
	 * @param employeeWorkStatusFlag
	 *            工作状态标识
	 * @param areaColor
	 *            片区颜色
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doEditMemberInfo.001-
	 *         修改人员归属信息异常</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doEditMemberInfo.002-
	 *         工作状态传入参数错误</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doEditMemberInfo.003-
	 *         角色标识传入参数错误</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doEditMemberInfo.003-
	 *         颜色色值传入参数错误</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.001 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.002 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.003 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.004 -employeeWorkStatusFlag参数错误！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.005 -areaColor参数错误！</dd>	 
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/doEditMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String doEditMemberInfo(Long compId, Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doEditMemberInfo.001", "compId不能为空！");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doEditMemberInfo.002", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doEditMemberInfo.003", "roleId不能为空！");
		}
		if(PubMethod.isEmpty(employeeWorkStatusFlag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doEditMemberInfo.004", "employeeWorkStatusFlag不能为空！");
		}
		if(PubMethod.isEmpty(areaColor)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doEditMemberInfo.005", "areaColor不能为空！");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			this.memberInfoService.doEditMemberInfo(compId, memberId, roleId,
					employeeWorkStatusFlag, areaColor);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加人员的时候检查手机号是否已存在</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:10:39</dd>
	 * 
	 * @param associatedNumber
	 *            手机号
	 * @param compId
	 *            网点id
	 * @return {"data":2,"success":true} <dd>flag = 2  数据库中不存在这个手机号 flag = 1 本站点存在 flag = 5 非本站点存在</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.checkTel.001 -associatedNumber不能为空！</dd>	
	 *<dd>net.okdi.api.controller.MemberInfoController.checkTel.002 -compId不能为空！</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkTel", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkTel(String associatedNumber, Long compId) {
		if(PubMethod.isEmpty(associatedNumber)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.checkTel.001", "associatedNumber不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.checkTel.002", "compId不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int flag = this.memberInfoService.checkTel(associatedNumber, compId);
			map.put("flag", flag);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>提供给取件任务中的查询营业分部和人员接口</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:11:27</dd>
	 * 
	 * @param parentId
	 *            网点id
	 * @return { "areaColor": "#c2c2c2", --片区颜色 "compTypeNum": "0", --公司类型 --
	 *         1002-发货商家1060：单位客户1000 网络1003 网络直营站点1006 加盟公司1008 加盟公司站点1030
	 *         快递代理点1050 营业分部 "memberId": 13867278975369216, --人员id
	 *         "memberName": "管理员", --人员姓名 "parentId": 13867330511306752, --父id
	 *         "phone": "13177770001", --手机号 "roleId": 1 --角色id -1: 后勤0 : 收派员1 :
	 *         大站长 -2：小站长 }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getMemberInfoByCompId.001 -parentId不能为空！</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfoByCompId", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMemberInfoByCompId(Long parentId) {
		if(PubMethod.isEmpty(parentId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getMemberInfoByCompId.001", "parentId不能为空！");
		}
		try {
			List<Map<String, Object>> list = this.memberInfoService.getMemberInfoByCompId(parentId);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>手机端添加的人员，在站点进行审核操作(通过/拒绝)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:12:02</dd>
	 * 
	 * @param logId
	 *            日志表id
	 * @param userId
	 *            登陆人员的memberId
	 * @param flag
	 *            标志 1通过2拒绝
	 * @param compId
	 *            网点id
	 * @param memberName
	 *            人员姓名
	 * @param memberId
	 *            人员id
	 * @param refuseDesc
	 *            拒绝原因
	 * @param areaColor
	 *            片区颜色
	 * @param roleId
	 *            角色id
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dt><span class="strong">异常:</span></dt> <dd>
	 *         openapi.MemberInfoServiceImpl.doAuditMember.001 - 更新状态异常</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.doAuditMember.002 - 插入归属关系异常</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doAuditMember.003- 插入履历异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.doAuditMember.001 -logId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.002 -userId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.003 -flag不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.004 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.005 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.006 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.007 -areaColor不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.008 -roleId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/doAuditMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String doAuditMember(Long logId,String memberPhone, Long userId, Short flag, Long compId,
			String memberName, Long memberId, String refuseDesc, String areaColor, Short roleId) {
		if(PubMethod.isEmpty(logId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.001", "logId不能为空！");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.002", "userId不能为空！");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.003", "flag不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.004", "compId不能为空！");
		}
		if(PubMethod.isEmpty(memberName)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.005", "memberName不能为空！");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.006", "memberId不能为空！");
		}
/*		if(PubMethod.isEmpty(areaColor)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.008", "areaColor不能为空！");
		}*/
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.009", "roleId不能为空！");
		}
		try {
			this.memberInfoService.doAuditMember(logId, memberPhone,userId, flag, compId, memberName, memberId,
					refuseDesc, areaColor, roleId);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点添加的收派员的删除操作(包括手机端的离职接口 )</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:13:34</dd>
	 * 
	 * @param userId
	 *            操作人id
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            站点id
	 * @param memberName
	 *            人员姓名
	 * @param memberPhone
	 *            人员手机号
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.deleteMemberInfo.001 - 删除人员关系异常
	 *         </dd> <dd>openapi.MemberInfoServiceImpl.deleteMemberInfo.002 -
	 *         插入履历关系异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.002 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.003 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.004 -memberPhone不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteMemberInfo(Long userId,Long memberId, Long compId, String memberName, String memberPhone) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.002", "compId不能为空！");
		}
//		if(PubMethod.isEmpty(memberName)){
//			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.003", "memberName不能为空！");
//		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.004", "memberPhone不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int isFinish = this.takeTaskService.queryTaskUnFinished(null, memberId);
			if (isFinish == 0) {
				List<Map<String,Object>> list = this.parcelInfoService.queryParcelListBySendMemberId(memberId,1,10);
				if(!PubMethod.isEmpty(list)){
					map.put("isSendFinish", "1");
					return jsonSuccess(map);
				}
				String a  = this.sendTaskService.querySendTaskList(memberId);
				JSONObject obj = JSON.parseObject(a);
				JSONObject jbj = obj.getJSONObject("data");
				if(jbj.getJSONArray("resultlist").size()>0){
					map.put("isTaskFinish", "1");
					return jsonSuccess(map);
				}
				this.memberInfoService.deleteMemberInfo(userId,memberId, compId, memberName, memberPhone);
				map.put("isFinish", isFinish);
				return jsonSuccess(map);
			} else {
				map.put("isFinish", isFinish);
				return jsonSuccess(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询站点或营业分部下的人员列表</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:16:09</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id 1050营业分部1006站点
	 * @return { "data": [ { "areaColor": "#c2c2c2", --片区颜色 "compId":
	 *         13867330511306752, --网点id "compName": "最营业一", --网点名称
	 *         "compTypeNum": "1050", ----公司类型 -- 1002-发货商家1060：单位客户1000 网络1003
	 *         网络直营站点1006 加盟公司1008 加盟公司站点1030 快递代理点1050 营业分部 "memberId":
	 *         13867399378632704 --人员id } ], "success": true }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryMemberForComp.001 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.queryMemberForComp.002 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.queryMemberForComp.003 -roleId不能为空！</dd>
	 * @since v1.0
	 */
	@Deprecated 
	@ResponseBody
	@RequestMapping(value = "/queryMemberForComp", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberForComp(Long compId, Long roleId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberForComp.001", "compId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberForComp.002", "roleId不能为空！");
		}
		if(roleId !=1006 && roleId !=1050){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberForComp.003", "roleId参数异常！");
		}
		try {
			return jsonSuccess(this.memberInfoService.queryMemberForComp(compId, roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员详细信息</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:16:30</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @return { "data": { "areaColor": "#c2c2c2", --片区颜色
	 *         "employeeWorkStatusFlag": 1, --工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单
	 *         "memberId": 13954038301985792, --人员id "memberName": "离职", --人员姓名
	 *         "memberPhone": "13177770045", --人员手机号 "memberSource": 1, --人员来源
	 *         "roleId": 1 --角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长 }, "success": true
	 *         }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getMemberInfoById.001 -memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfoById", method = { RequestMethod.POST, RequestMethod.GET })
	public String getMemberInfoById(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getMemberInfoById.001", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.getMemberInfoById(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>客服身份验证接口</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:16:49</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @param flag
	 *            1通过 2拒绝
	 * @return {"data":"","success":true} <dd>
	 *         openapi.MemberInfoServiceImpl.verifyIdentity.001 - 手机端的身份验证接口异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.verifyIdentity.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.verifyIdentity.002 -flag不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.verifyIdentity.003 -审核标识异常！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyIdentity", method = { RequestMethod.POST, RequestMethod.GET })
	public String verifyIdentity(Long compId,Long memberId, Short flag) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyIdentity.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyIdentity.002", "flag不能为空！");
		}
		if(flag !=1 && flag !=2){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyIdentity.003", "审核标识异常！");
		}
		try {
			this.memberInfoService.verifyIdentity(compId,memberId, flag);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>客服归属验证接口</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:17:08</dd>
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id 0 收派员 -1 后勤 -2 站长
	 * @param flag
	 *            1通过2拒绝
	 * @return {"data":"","success":true}
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.verifyRelation.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.verifyRelation.002 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.verifyRelation.003 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.verifyRelation.004 -flag不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyRelation", method = { RequestMethod.POST, RequestMethod.GET })
	public String verifyRelation(Long memberId, Long compId, Short roleId, Short flag) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyRelation.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyRelation.002", "compId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyRelation.003", "roleId不能为空！");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.verifyRelation.004", "flag不能为空！");
		}
		try {
			this.memberInfoService.verifyRelation(memberId, compId, roleId, flag);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>手机端注册接口</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:18:41</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @param memberName
	 *            人员姓名
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id 0 收派员 -1 后勤 -2 站长
	 * @param applicationDesc
	 *            5张单号
	 * @param flag
	 *            1：第一步调用归属验证 2：第二部调用身份验证
	 * @param memberPhone
	 *            手机号
	 * @param idNum
	 *            身份证
	 * @param memberSourceFlag
	 *            人员来源 0手机1站点
	 * @return {"data":"","success":true} <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.001 -
	 *         手机端注册memberId不能为空</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.002 -
	 *         手机端注册compId不能为空</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.003 -
	 *         手机端注册roleId不能为空</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.004 -
	 *         手机端注册flag不能为空</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.005 -
	 *         手机端注册memberName不能为空</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.mobileRegistration.006 -
	 *         手机端注册applicationDesc不能为空</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.002 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.003 -memberPhone不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.004 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.005 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.006 -flag不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.mobileRegistration.007 -memberSourceFlag不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/mobileRegistration", method = { RequestMethod.POST, RequestMethod.GET })
	public String mobileRegistration(Long memberId, String memberName, String memberPhone,
			String idNum, Long compId, Short roleId, String applicationDesc, Short flag,String memberSourceFlag,String stationPhone) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.001", "memberId不能为空！");
		}
//		if(PubMethod.isEmpty(memberName)){
//			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.002", "memberName不能为空！");
//		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.003", "memberPhone不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.004", "compId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.005", "roleId不能为空！");
		}
//		if(roleId==1){
//			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.008", "roleId不能为1！");
//		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.006", "flag不能为空！");
		}
		if(PubMethod.isEmpty(memberSourceFlag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.mobileRegistration.007", "memberSourceFlag不能为空！");
		}
		try {
			this.memberInfoService.mobileRegistration(memberId, memberName, memberPhone, idNum,
					compId, roleId, applicationDesc, flag , memberSourceFlag, stationPhone);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通行证同步数据到api</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:19:22</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @param memberName
	 *            人员姓名
	 * @param idNum
	 *            身份证
	 * @param memberPhone
	 *            人员手机号
	 * @param memberAddressId
	 *            地址id
	 * @param memberDetaileDisplay
	 *            地址文字描述
	 * @param memberDetailedAddress
	 *            详细地址
	 * @return {"data":"","success":true} <dd>
	 *         openapi.MemberInfoServiceImpl.SynDataFromUcenter.001 -
	 *         通行证同步数据更新操作异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.SynDataFromUcenter.001 -memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/SynchronousData", method = { RequestMethod.POST, RequestMethod.GET })
	public String SynDataFromUcenter(Long memberId, String memberName, String idNum,
			String memberPhone, Long memberAddressId, String memberDetaileDisplay,
			String memberDetailedAddress,Short resource) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.SynDataFromUcenter.001", "memberId不能为空！");
		}
		try {
			this.memberInfoService.SynDataFromUcenter(memberId, memberName, idNum, memberPhone,
					memberAddressId, memberDetaileDisplay, memberDetailedAddress,resource);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>拒绝之后的人员删除操作</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:20:20</dd>
	 * 
	 * @param logId
	 *            日志表id
	 * @param compId
	 *           网点d
	 * @return {"data":"","success":true} <dd>
	 *         openapi.MemberInfoServiceImpl.removeMemberInfo.001 -
	 *         拒绝之后的人员删除操作异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.removeMemberInfo.001 -logId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/removeMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeMemberInfo(Long compId,Long logId) {
		if(PubMethod.isEmpty(logId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.removeMemberInfo.001", "logId不能为空！");
		}
		try {
			this.memberInfoService.removeMemberInfo(compId,logId);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过memberId查询审核信息</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:21:48</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @return { "data": [ { "appTime": 1415070026000, --审核时间 "compId": 1, -网点id
	 *         "id": 13976544168378372, "memebrId": 1811399874664854, --人员id
	 *         "roleId": 1 --角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长 } ], "success":
	 *         true }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getAuditInfo.001 -memberId不能为空！</dd>
	 * @since v1.0 roleId 角色 -1: 后勤 0 : 收派员 1 : 站长
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuditInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String getAuditInfo(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getAuditInfo.001", "memberId不能为空！");
		}
		List<Map<String, Object>> list = this.memberInfoService.getAuditInfo(memberId);
		return jsonSuccess(list);
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员审核状态</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:22:17</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @return {"data":{ "memberId":781398355116084, --人员id "relationFlag":0,
	 *         --归属验证标识 0 待审核 1通过 2拒绝""已离职 "veriFlag":0}, --身份验证标识 0 待审核 1通过 2拒绝
	 *         "success":true}
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getValidationStatus.001 -memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getValidationStatus", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getValidationStatus(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getValidationStatus.001", "memberId不能为空！");
		}
		return jsonSuccess(this.memberInfoService.getValidationStatus(memberId));
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询站长信息</dd> <dt><span
	 * class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-19 上午11:27:19</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @return {"data":"","success":true}
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getMasterPhone.001 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMasterPhone", method = { RequestMethod.POST, RequestMethod.GET })
	public String getMasterPhone(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getMasterPhone.001", "compId不能为空！");
		}
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = this.memberInfoService.queryMemberForComp(compId, null);
		for (Map<String, Object> map : list) {
			if (map.get("roleId").toString().equals("1")) {
				list1.add(map);
			}
		}
		return jsonSuccess(list1);
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询该站点下的手机端来源的收派员</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>haifeng.he</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2014-11-22 下午4:06:53</dd>
	 * 
	 * @param compId
	 * @return { "data": [ { "auditItem": 2, "auditOpinion": 0, --审核状态 "compId":
	 *         14365879010852864, --网点id "createTime": 1416643003000, --创建时间
	 *         "employeeWorkStatusFlag": "1", --工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单
	 *         "logId": 14388890593920000, --人员日志id "memberId": 20142014, --人员id
	 *         "memberName": "手机端测试yiyi", --人员姓名 "memberPhone": "13123456789",
	 *         --人员手机号 "memberSource": "0", --来源 0 手机端 1站点 "roleId": 0 --角色id
	 *         -1: 后勤0 : 收派员1 : 大站长 -2：小站长 } ], "success": true }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.loadMemberInfo.001 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/loadMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String loadMemberInfo(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.loadMemberInfo.001", "compId不能为空！");
		}
		try {
			List<Map<String, Object>> list = this.memberInfoService.loadMemberInfo(compId);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>提供给电商管家的接口(通过手机号查询已注册且可接单的收派员信息)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-1 上午11:16:07</dd>
	 * @param memberPhone 人员手机号 
	 * @return
	 * {
		  "data": [
		    {
		      "compId": 14452493785505792, 网点id
		      "compName": "申通爱我", 网点名称
		      "deliverId": 14452471149894656, 人员id 
		      "deliverName": "吕帅", 人员姓名
		      "deliverPhone": "13520932902", 人员手机号 
		      "netId": 2071, 网络id
		      "netName": "微特派",  网络名称
		      "receivingFlag": 1,  可否接单 1 可以 0 不可以
		      "registFlag": "1"  是否注册 1注册 0未注册
		    }
		  ], 
		  "success": true
		}
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryMemberInfoForFhw.001 -memberPhone不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMemberInfoForFhw", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberInfoForFhw(String memberPhone){
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryMemberInfoForFhw.001", "memberPhone不能为空！");
		}
		try {
			List<Map<String,Object>> list = this.memberInfoService.queryMemberInfoForFhw(memberPhone);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询在线收派员坐标</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-30 上午10:13:40</dd>
	 * @param compId
	 * @return {
		    "data": [
		        {
		            "latitude": 116.11,
		            "longitude": 116.11,
		            "memberId": 10086,
		            "memberName": "测试履历"
		        }
		    ],
		    "success": true
		}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemLocation", method = { RequestMethod.POST, RequestMethod.GET })	
	public String queryMemberCoordinate(Long compId){
		try {
			//System.out.println("wwwwwwwwwwwwwwww"+jsonSuccess(this.memberInfoService.queryMemberCoordinate(compId)));
			return jsonSuccess(this.memberInfoService.queryMemberCoordinate(compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getMemberId", method = { RequestMethod.POST, RequestMethod.GET })	
	public String getMemberId(String memberPhone){
		try {
			return jsonSuccess(this.memberInfoService.getMemberId(memberPhone));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	/**
	 * 通过ID查找收派员明细信息
	 * @param memberPhone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMemberById", method = { RequestMethod.POST, RequestMethod.GET })	
	public String findMemberById(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("MemberInfoController.findMemberById.001","memberId不能为空");
		}
		try {
			return jsonSuccess(this.memberInfoService.findMemberById(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 查找需待审核(全部人员的）的信息			
	 * @param memberPhone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMemberAll", method = { RequestMethod.POST, RequestMethod.GET })	
	public String findMemberAll(Integer pageNum,Integer pageSize,String memberName,String memberPhone,
			String netId,Short roleType,String compType,String compName,
			Short opinion,String beginTime,String endTime,String status){

		try {
			return jsonSuccess(this.memberInfoService.findMemberAll(pageNum, pageSize, memberName, 
					memberPhone, netId, roleType, compType, compName,
					opinion, beginTime, endTime,status));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value ="/verifyRelationForOperate", method = { RequestMethod.POST })
	public String verifyRelationForOperate(String phone){
		try {
			this.memberInfoService.verifyRelationForOperate(phone);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
//**************************************************手机端***********************************************************************************/
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点系统人员查询列表</dd>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * @param logMemberId
	 *            当前登录用户的memberId
	 * @param compId
	 *            网点id
	 * @return { "areaColor": "#c2c2c2", "compId": 13867330511306752,
	 *         "employeeWorkStatusFlag": 1, 工作状态 "id_num": "230121198203215698",
	 *         "memberId":13867278975369216, "memberName": "管理员", "memberPhone":
	 *         "13177770001", "memberSource": 1, "roleId": 1 } <dd>areaColor -
	 *         片区颜色</dd> <dd>compId - 网点id</dd> <dd>employeeWorkStatusFlag -
	 *         工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单</dd> <dd>id_num - 身份证号</dd> <dd>
	 *         memberId - 人员id</dd> <dd>memberName - 人员姓名</dd> <dd>memberPhone -
	 *         人员手机号</dd> <dd>memberSource - 人员来源 0:手机端1:站点</dd> <dd>roleId -
	 *         角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryMemberInfo.001 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/querySiteMemberList", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySiteMemberList(Long logMemberId, Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.querySiteMemberList.001", "compId不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//ehcacheService.removeAll("employeeCache");
		List<VO_MemberInfo> list = ehcacheService.get("employeeCache", compId.toString(),ArrayList.class);
		List<VO_SiteMemberInfo> resultList = new ArrayList<VO_SiteMemberInfo>();
		List<Map<String, Object>> list2 = this.memberInfoService.loadMemberInfo(compId);
		try {
		if (!PubMethod.isEmpty(list)) {
			String str=JSONObject.toJSONString(list);
			JSONArray ja=JSONArray.parseArray(str);
			for(int i=0;i<ja.size();i++){
				JSONObject json = (JSONObject) ja.get(i);				
				VO_SiteMemberInfo vosm=new VO_SiteMemberInfo();
				vosm.setAuditOpinion("1");
				vosm.setCompId(compId);
				vosm.setCreateTime(json.getDate("createTime"));
				vosm.setEmployeeWorkStatusFlag((short)1);
				vosm.setMemberId(json.getLong("memberId"));
				vosm.setMemberName(json.getString("memberName"));
				vosm.setMemberPhone(json.get("memberPhone").toString());
				vosm.setMemberSource(json.getShort("memberSource"));
				vosm.setRoleId(json.getShort("roleId"));
				vosm.setMemberDetaileDisplay(String.valueOf(constPool.getHeadImgPath()+json.getLong("memberId")+".jpg"));
				resultList.add(vosm);
			}
		}else{
			list = this.memberInfoService.queryEmployeeCache(compId);
			System.out.println(list.size());
			ehcacheService.put("employeeCache", compId.toString(), list);
			for(VO_MemberInfo vom:list){
				VO_SiteMemberInfo vosm=new VO_SiteMemberInfo();
				vosm.setAuditOpinion("1");
				vosm.setCompId(compId);
				vosm.setCreateTime(vom.getCreateTime());
				vosm.setEmployeeWorkStatusFlag((short)1);
				vosm.setMemberId(vom.getMemberId());
				vosm.setMemberName(vom.getMemberName());
				vosm.setMemberPhone(vom.getMemberPhone());
				vosm.setMemberSource(vom.getMemberSource());
				vosm.setRoleId(vom.getRoleId());
				vosm.setMemberDetaileDisplay(String.valueOf(constPool.getHeadImgPath()+vom.getMemberId()+".jpg"));
				resultList.add(vosm);
			}
		}
		map.put("memberList",resultList);//memberList
		map.put("auditingMemberNum",list2.size());
		return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点待审核人员查询列表</dd>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * @param logMemberId
	 *            当前登录用户的memberId
	 * @param compId
	 *            网点id
	 * @return { "areaColor": "#c2c2c2", "compId": 13867330511306752,
	 *         "employeeWorkStatusFlag": 1, 工作状态 "id_num": "230121198203215698",
	 *         "memberId":13867278975369216, "memberName": "管理员", "memberPhone":
	 *         "13177770001", "memberSource": 1, "roleId": 1 } <dd>areaColor -
	 *         片区颜色</dd> <dd>compId - 网点id</dd> <dd>employeeWorkStatusFlag -
	 *         工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单</dd> <dd>id_num - 身份证号</dd> <dd>
	 *         memberId - 人员id</dd> <dd>memberName - 人员姓名</dd> <dd>memberPhone -
	 *         人员手机号</dd> <dd>memberSource - 人员来源 0:手机端1:站点</dd> <dd>roleId -
	 *         角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.queryMemberInfo.001 -compId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPendingAuditMemberList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPendingAuditMemberList(Long logMemberId, Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryPendingAuditMemberList.001", "compId不能为空！");
		}
		List<VO_SiteMemberInfo> resultList = new ArrayList<VO_SiteMemberInfo>();
		List<Map<String, Object>> list = this.memberInfoService.loadMemberInfo(compId);
		try {
			for(Map<String, Object> vom:list){
				String str=JSONObject.toJSONString(vom);
				JSONObject json=JSONObject.parseObject(str);
				VO_SiteMemberInfo vosm=new VO_SiteMemberInfo();
				vosm.setAuditOpinion("0");
				vosm.setCompId(compId);
				vosm.setCreateTime(json.getDate("createTime"));
				vosm.setEmployeeWorkStatusFlag(json.getShort("employeeWorkStatusFlag"));
				vosm.setMemberId(json.getLong("memberId"));
				vosm.setMemberName(json.getString("memberName"));
				vosm.setMemberPhone(json.get("memberPhone").toString());
				vosm.setMemberSource(json.getShort("memberSource"));
				vosm.setRoleId(json.getShort("roleId"));
				vosm.setLogId(json.getLong("logId"));
				vosm.setMemberDetaileDisplay(String.valueOf(constPool.getHeadImgPath()+json.getLong("memberId")+".jpg"));
				resultList.add(vosm);
			}
			return jsonSuccess(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>手机端添加的人员，在站点进行审核操作(通过/拒绝)</dd>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param logId
	 *            日志表id
	 * @param userId
	 *            登陆人员的memberId
	 * @param flag
	 *            标志 1通过2拒绝
	 * @param compId
	 *            网点id
	 * @param memberName
	 *            人员姓名
	 * @param memberId
	 *            人员id
	 * @param refuseDesc
	 *            拒绝原因
	 * @param areaColor
	 *            片区颜色
	 * @param roleId
	 *            角色id
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dt><span class="strong">异常:</span></dt> <dd>
	 *         openapi.MemberInfoServiceImpl.doAuditMember.001 - 更新状态异常</dd> <dd>
	 *         openapi.MemberInfoServiceImpl.doAuditMember.002 - 插入归属关系异常</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.doAuditMember.003- 插入履历异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.doAuditMember.001 -logId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.002 -userId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.003 -flag不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.004 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.005 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.006 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.007 -areaColor不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.doAuditMember.008 -roleId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/siteMemberToexamine", method = { RequestMethod.POST, RequestMethod.GET })
	public String siteMemberToexamine(Long logId,String memberPhone, Long userId, Short flag, Long compId,
			String memberName, Long memberId, String refuseDesc, String areaColor, Short roleId) {
		if(PubMethod.isEmpty(logId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.001", "logId不能为空！");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.002", "userId不能为空！");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.003", "flag不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.004", "compId不能为空！");
		}
		if(PubMethod.isEmpty(memberName)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.005", "memberName不能为空！");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.006", "memberId不能为空！");
		}
/*		if(PubMethod.isEmpty(areaColor)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.008", "areaColor不能为空！");
		}*/
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.siteMemberToexamine.009", "roleId不能为空！");
		}
		try {
			this.memberInfoService.doAuditMember(logId, memberPhone,userId, flag, compId, memberName, memberId,
					refuseDesc, areaColor, roleId);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加人员接口</dd> <dt><span
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @param associatedNumber
	 *            手机号
	 * @param memberName
	 *            人员姓名
	 * @param roleId
	 *            注册角色id
	 * @param areaColor
	 *            片区颜色
	 * @param userId
	 *            审核人
	 * @param memberSource
	 *            来源 0手机端1站点
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.001 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.002 -associatedNumber不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.003 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.004 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.005 -userId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.006 -memberSource不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.addMemberInfo.007 -memberSource参数错误！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertMemberInfo(Long compId, String associatedNumber, String memberName,
			Short roleId, String areaColor, Long userId, Short memberSource) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.001", "compId不能为空！");
		}
		if(PubMethod.isEmpty(associatedNumber)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.002", "associatedNumber不能为空！");
		}
		if(PubMethod.isEmpty(memberName)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.003", "memberName不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.004", "roleId不能为空！");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.005", "userId不能为空！");
		}
		if(PubMethod.isEmpty(memberSource)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.006", "memberSource不能为空！");
		}
		if(memberSource !=0 &&memberSource !=1){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.insertMemberInfo.007", "memberSource参数错误！");
		}
		if(roleId==1 || roleId==2){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.008", "roleId参数错误！");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			this.memberInfoService.addMemberInfo(compId, associatedNumber, memberName, roleId,
					areaColor, userId, memberSource);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员详细信息</dd> <dt><span
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param memberId
	 *            人员id
	 * @return { "data": { "areaColor": "#c2c2c2", --片区颜色
	 *         "employeeWorkStatusFlag": 1, --工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单
	 *         "memberId": 13954038301985792, --人员id "memberName": "离职", --人员姓名
	 *         "memberPhone": "13177770045", --人员手机号 "memberSource": 1, --人员来源
	 *         "roleId": 1 --角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长 }, "success": true
	 *         }
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.getMemberInfoById.001 -memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMemberInfoById", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberInfoById(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getMemberInfoById.001", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.getMemberInfoById(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改人员信息</dd> <dt><span
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @param roleId
	 *            角色id
	 * @param employeeWorkStatusFlag
	 *            工作状态标识
	 * @param areaColor
	 *            片区颜色
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.updateMemberInfo.001-
	 *         修改人员归属信息异常</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.updateMemberInfo.002-
	 *         工作状态传入参数错误</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.updateMemberInfo.003-
	 *         角色标识传入参数错误</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.updateMemberInfo.003-
	 *         颜色色值传入参数错误</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.doEditMemberInfo.001 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.updateMemberInfo.002 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.updateMemberInfo.003 -roleId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.updateMemberInfo.004 -employeeWorkStatusFlag参数错误！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.updateMemberInfo.005 -areaColor参数错误！</dd>	 
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateMemberInfo(Long compId, Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor,Long userId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.001", "compId不能为空！");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.002", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.003", "roleId不能为空！");
		}
		if(PubMethod.isEmpty(employeeWorkStatusFlag)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.004", "employeeWorkStatusFlag不能为空！");
		}
		if(PubMethod.isEmpty(areaColor)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.005", "areaColor不能为空！");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.006", "userId不能为空！");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			this.memberInfoService.updateMemberInfo(compId, memberId, roleId,
					employeeWorkStatusFlag, areaColor , userId);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点添加的收派员的删除操作(包括手机端的离职接口 )</dd>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param userId
	 *            操作人id
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            站点id
	 * @param memberName
	 *            人员姓名
	 * @param memberPhone
	 *            人员手机号
	 * @return {"data":"","success":true} <dd>success - 返回标识 true:成功 flase:失败</dd>
	 *         <dd>openapi.MemberInfoServiceImpl.deleteMemberInfo.001 - 删除人员关系异常
	 *         </dd> <dd>openapi.MemberInfoServiceImpl.deleteMemberInfo.002 -
	 *         插入履历关系异常</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.001 -memberId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.002 -compId不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.003 -memberName不能为空！</dd>
	 *<dd>net.okdi.api.controller.MemberInfoController.deleteMemberInfo.004 -memberPhone不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSiteMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteSiteMember(Long userId,Long memberId, Long compId, String memberName, String memberPhone) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteSiteMember.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteSiteMember.002", "compId不能为空！");
		}
//		if(PubMethod.isEmpty(memberName)){
//			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.003", "memberName不能为空！");
//		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteSiteMember.004", "memberPhone不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		ehcacheService.removeAll("employeeCache");
		try {
			int isFinish = this.takeTaskService.queryTaskUnFinished(null, memberId);
			if (isFinish == 0) {
				List<Map<String,Object>> list = this.parcelInfoService.queryParcelListBySendMemberId(memberId,1,10);
				if(!PubMethod.isEmpty(list)){
					map.put("isFinish",0);
					map.put("isSendFinish", 1);
					map.put("isTaskFinish", 1);
					return jsonSuccess(map);
				}
				String a  = this.sendTaskService.querySendTaskList(memberId);
				JSONObject obj = JSON.parseObject(a);
				JSONObject jbj = obj.getJSONObject("data");
				if(jbj.getJSONArray("resultlist").size()>0){
					map.put("isFinish",0);
					map.put("isSendFinish", 0);
					map.put("isTaskFinish", 1);
					return jsonSuccess(map);
				}
				this.memberInfoService.deleteMemberInfo(userId,memberId, compId, memberName, memberPhone);
				map.put("isSendFinish", 0);
				map.put("isTaskFinish", 0);
				map.put("isFinish", isFinish);
				return jsonSuccess(map);
			} else {
				map.put("isFinish", isFinish);
				return jsonSuccess(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加人员的时候检查手机号是否已存在</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * 
	 * @param associatedNumber
	 *            手机号
	 * @param compId
	 *            网点id
	 * @return {"data":2,"success":true} <dd>flag = 2  数据库中不存在这个手机号 flag = 1 本站点存在 flag = 5 非本站点存在</dd>
	 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.checkTel.001 -associatedNumber不能为空！</dd>	
	 *<dd>net.okdi.api.controller.MemberInfoController.checkTel.002 -compId不能为空！</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkForExistence", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkForExistence(String associatedNumber, Long compId) {
		if(PubMethod.isEmpty(associatedNumber)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.checkForExistence.001", "associatedNumber不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.checkForExistence.002", "compId不能为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int flag = this.memberInfoService.checkTel(associatedNumber, compId);
			map.put("flag", flag);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 	修改审核状态	chunyang.tan
	 * @param memberId	用户Id
	 * @param auditOpinion	审核结果
	 * @param auditRejectReason	审核拒绝原因
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAuditOpinion", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAuditOpinion(Long memberId,Short auditOpinion,Short auditRejectReason){
		if(PubMethod.isEmpty(memberId))
		{
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateAuditOpinion.001", "memberId不能为空！");
		}
		if(PubMethod.isEmpty(auditOpinion)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.updateAuditOpinion.002", "auditOpinion不能为空！");
		}
		try{
			memberInfoService.updateAuditOpinion(memberId,auditOpinion,auditRejectReason);
			return jsonSuccess();
		}catch(Exception e)
		{
			return jsonFailure();
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断是否是收派员</dd> <dt>
	 * <span class="strong">作者:</span></dt><dd>aijun.han</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-10-07 上午11:10:09</dd>
	 * @param memberPhonbe
	 *            手机号
	 * @return {"data":true,"success":true}
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/ifCourier", method = { RequestMethod.POST, RequestMethod.GET })
	public String ifCourier(String memberPhone){
		try{
			if(PubMethod.isEmpty(memberPhone)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.ifHas.001", "memberPhone不能为空！");
			}
			boolean flag=this.memberInfoService.ifCourier(memberPhone);
			return jsonSuccess(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询收派员审核列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-07 上午10:44:04</dd>
	 * @param memberName 收派员姓名
	 * @param phone 手机号
	 * @param startTime 注册起始时间
	 * @param endTime 注册结束时间
	 * @param auditType 审核类型 0.待审核 1.通过 2.拒绝
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @since v1.0
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryCourierAuditList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCourierAuditList(String memberName, String phone, String startTime, String endTime, Short auditType,Integer pageNo,Integer pageSize) {
		if(PubMethod.isEmpty(auditType)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryCourierAuditList.001", "auditType不能为空,审核类型不能为空！");
		}
		if(PubMethod.isEmpty(pageNo)){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize=20;
		}
		try {
			return jsonSuccess(this.memberInfoService.queryCourierAuditList(memberName, phone,startTime,endTime,auditType,pageNo,pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询待审核人员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-07 上午10:44:04</dd>
	 * @param memberId 用户id
	 * @since v1.0
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryCourierAuditInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCourierAuditInfo(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryCourierAuditInfo.001", "memberId不能为空,审核类型不能为空！");
		}

		try {
			return jsonSuccess(this.memberInfoService.queryCourierAuditInfo(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除方法</dd>
	 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
	 * @param memberPhone
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMemberByPhone", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteMemberByPhone(String memberPhone) {
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberByPhone.001", "memberPhone不能为空！");
		}
		try{
				return jsonSuccess(this.memberInfoService.queryMemberByPhone(memberPhone));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String getUserInfo(Long memberPhone) {
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getUserInfo", "memberPhone不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.getUserInfo(memberPhone));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/clearNormalRoleInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String clearNormalRoleInfo(Long memberId, Long compId,String memberName, String memberPhone, Short roleId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.clearNormalRoleInfo", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.clearNormalRoleInfo(memberId, compId, memberName, memberPhone,roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/clearStationRoleInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String clearStationRoleInfo(Long oldMemberId, Long newMemberId, Long compId, Short roleId,String memberName, String memberPhone) {
		if(PubMethod.isEmpty(oldMemberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.clearStationRoleInfo", "oldMemberId不能为空！");
		}
		if(PubMethod.isEmpty(newMemberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.clearStationRoleInfo", "newMemberId不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.clearStationRoleInfo", "compId不能为空！");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.clearStationRoleInfo", "roleId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.clearStationRoleInfo(oldMemberId, newMemberId, compId, roleId, memberName,  memberPhone));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryNormalRoles", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNormalRoles(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.queryStationRoles", "compId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.queryNormalRoles(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/validTaskHandler", method = { RequestMethod.POST, RequestMethod.GET })
	public String validTaskHandler(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.validTaskHandler", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.validTaskHandler(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/validPlaintInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String validPlaintInfo(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.validPlaintInfo", "memberId不能为空！");
		}
		try {
			return jsonSuccess(this.memberInfoService.validPlaintInfo(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
