package net.okdi.mob.service;

/**
 * @author amssy
 * @version V1.0
 */
public interface MemberInfoService {
	//**************************************************手机端***********************************************************************************/
		/**
		 * 
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询站点或营业分部下的人员列表</dd> <dt>
		 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
		 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
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
		 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.001 -compId不能为空！</dd>
		 *<dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.002 -roleId不能为空！</dd>
		 *<dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.003 -roleId不能为空！</dd>
		 * @since v1.0
		 */
		public String querySiteMemberList(Long compId, Long roleId);
		/**
		 * 
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询站点或营业分部下的待审核人员列表</dd> <dt>
		 * <span class="strong">作者:</span></dt><dd>杨凯</dd> <dt><span
		 * class="strong">时间:</span></dt><dd>2015-09-19 上午10:16:09</dd>
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
		 *<dt><span class="strong">异常:</span></dt> <dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.001 -compId不能为空！</dd>
		 *<dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.002 -roleId不能为空！</dd>
		 *<dd>net.okdi.api.controller.MemberInfoController.querySiteMemberList.003 -roleId不能为空！</dd>
		 * @since v1.0
		 */
		public String queryPendingAuditMemberList(Long compId, Long roleId);
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

		public String siteMemberToexamine(Long logId,String memberPhone, Long userId, Short flag, Long compId,
				String memberName, Long memberId, String refuseDesc, String areaColor, Short roleId);

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

		public String insertMemberInfo(Long compId, String associatedNumber, String memberName,
				Short roleId, String areaColor, Long userId, Short memberSource);
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
		public String queryMemberInfoById(Long memberId);
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
		public String updateMemberInfo(Long compId, Long memberId, Short roleId,
				Short employeeWorkStatusFlag, String areaColor,Long userId);
		
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
		
		public String deleteSiteMember(Long userId,Long memberId, Long compId, String memberName, String memberPhone);
		/**
		 * @api {post} /memberInfo/checkForExistence 检查人员是否重复或存在
		 * @apiPermission user
		 * @apiDescription 检查人员是否重复或存在kai.yang
		 * @apiparam {Long} compId 网点id
		 * @apiparam {String} associatedNumber 手机号
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/insertMemberInfo
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *  {"data":{"flag":2},"success":true} 
		 *  flag = 2  数据库中不存在这个手机号 flag = 1 本站点存在 flag = 5 非本站点存在
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		public String checkForExistence(Long compId, String associatedNumber);
}
