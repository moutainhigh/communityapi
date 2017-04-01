package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.core.base.BaseService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName MemberInfoService
 * @Description TODO
 * @author haifeng.he
 * @date 2014-10-24
 * @since jdk1.6
 */
public interface MemberInfoService extends BaseService<MemberInfo> {
	/**
	 * 
	 * @Method: doAddMemberToComp
	 * @Description: 网点资料注册的时候添加到本站点一条初始化的收派员记录
	 * @param memberId 人员id
	 * @param compId 网点id
	 * @return {"data":"","success":true}
	 * @
	 * @since jdk1.6
	 */
	public MemberInfo doAddMemberToComp(Long memberId, Long compId) ;

	/**
	 * 
	 * @Method: queryCountById
	 * @Description: 查询bas_employee_relation关系表中是否存在memberId与comId的记录(-1 后勤0
	 *               收派员1 站长)
	 * @param memberId网点id
	 * @param roleId角色id
	 * @param compId网点id
	 * @return
	 * @since jdk1.6
	 */
	public List<BasEmployeeRelation> queryCountById(Long memberId, Short roleId, Long compId);

	/**
	 * 
	 * @Method: queryMemberInfoFromUCenter
	 * @Description: 通过memberId去通行证查询人员的资料数据 
	 * @param memberId人员id
	 * @return
	 * @since jdk1.6
	 */
	public JSONObject queryMemberInfoFromUCenter(Long memberId) ;

	/**
	 * 
	 * @Method: doEditComp
	 * @Description: 修改人员所在站点 从旧站点修改到新站点
	 * @param compIdOld旧网点id
	 * @param compIdNew新网点id                                    
	 * @return {"data":"","success":true}
	 * @since jdk1.6
	 */
	public int doEditComp(Long compIdOld, Long compIdNew);

	/**
	 * 
	 * @Method: queryMemberResume
	 * @Description: 查询人员履历信息
	 * @param memberId人员id
	 * @return  {
			      "compAddress": "北京-海淀区-东小口镇|最最一", 
			      "compId": 13867330511306752, 
			      "compName": "最最一", 
			      "compTelephone": "010-11111111", 
			      "createTime": "2014-11-03", 
			      "employeeUserName": "通通通", 
			      "employee_user_id": 13954038301985792, 
			      "endTime": "2014-11-08", 
			      "netId": 2071, 
			      "netName": "微特派"
			    }
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> queryMemberResume(Long memberId);

	/**
	 * 
	 * @Method: addMemberInfo
	 * @Description: 提供给站点的添加收派员接口
	 * @param compId
	 *            网点id
	 * @param associatedNumber手机号
	 * @param memberName人员姓名
	 * @param roleId角色id
	 * @param areaColor片区颜色
	 * @param userId用户id
	 * @
	 * @since jdk1.6
	 */
	public void addMemberInfo(Long compId, String associatedNumber, String memberName, Short roleId, String areaColor,
			Long userId, Short memberSource) ;

	/**
	 * 
	 * @Method: validateTel
	 * @Description: 手机号注册验证(通过手机号去通行证查询 )
	 * @param associatedNumber手机号
	 * @return
	 * @since jdk1.6
	 */
	public JSONObject validateTel(String associatedNumber) ;

	/**
	 * 
	 * @Method: regTel
	 * @Description: 如果手机号在通行证没有注册那么用手机号调用接口注册生成memberId返回
	 * @param associatedNumber
	 *            手机号
	 * @param password密码
	 * @return
	 * @since jdk1.6
	 */
	public String regTel(String associatedNumber, String password,String memberName);

	/**
	 * 
	 * @Method: queryCompNameById
	 * @Description: 通过compId获取公司信息
	 * @param compId网点id
	 * @return
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> queryCompNameById(Long compId);

	/**
	 * 
	 * @Method: getMemberInfoById
	 * @Description: 点击姓名 反显右侧信息
	 * @param memberId人员id
	 * @return
	 * @since jdk1.6
	 */
	public MemberInfo getMemberInfoById(Long memberId);

	/**
	 * 
	 * @Method: doEditMemberInfo
	 * @Description: 修改右侧人员信息
	 * @param memberId人员id
	 * @param roleId角色id
	 * @param employeeWorkStatusFlag工作标志
	 * @param areaColor片区颜色
	 * @since jdk1.6
	 */
	public void doEditMemberInfo(Long compId,Long memberId, Short roleId, Short employeeWorkStatusFlag, String areaColor)
			;

	/**
	 * 
	 * @Method: deleteMemberInfo
	 * @Description: 站点添加的收派员的删除操作
	 * @param  memberId
	 * @return int
	 * @throws
	 */
	public void deleteMemberInfo(Long userId,Long memberId, Long compId, String memberName,String memberPhone) ;

	/**
	 * 
	 * @Method: checkTel
	 * @Description: 站点添加手机号的时候检查是否存在
	 * @param  associatedNumber
	 * @return int
	 * @throws
	 */
	public int checkTel(String associatedNumber, Long compId);

	/**
	 * 
	 * @Method: doAuditMember
	 * @Description: 手机端添加的人员，在站点进行审核操作(通过/拒绝)
	 * @param flag
	 * @param  compId
	 * @param memberId
	 * @return BasEmployeeAudit
	 * @throws
	 * @since jdk1.6
	 */
	public BasEmployeeRelation doAuditMember(Long logId,String memberPhone,Long userId,Short flag, Long compId, String memberName, Long memberId,
			String refuseDesc, String areaColor, Short roleId);

	/**
	 * 
	 * @Method: queryUserId
	 * @Description: 通过compId获取关系表中的memberId
	 * @param  compId
	 * @return BasEmployeeRelation
	 * @throws
	 * @since jdk1.6
	 */
	public Long queryMemberIdByCompId(Long compId);

	/**
	 * 
	 * @Method: getMemberInfoByCompId
	 * @Description: 查询站点下的营业分部和收派员或者营业分部下的收派员
	 * @param compId
	 * @return List<Map<String,Object>>
	 * @throws
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> getMemberInfoByCompId(Long parentId);
	
	/**
	 * 
	 * @Method: CompFlag
	 * @Description: 通过compId判断网点是站点还是营业分部
	 * @param compId
	 * @param  1006 站点 1050 营业分部
	 * @return int
	 * @throws
	 * @since jdk1.6
	 */
	public int CompFlag(Long compId);

	/**
	 * 
	 * @Method: queryUnAudit
	 * @Description: 人员列表中手机端来源的人员
	 * @param compId
	 * @return List<Map<String,Object>>
	 * @throws
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> queryUnAuditMemberInfo(Long compId);

	/**
	 * 
	 * @Method: queryMemberForComp
	 * @Description: 查询站点或营业分部下的人员列表
	 * @param compId 网点id
	 * @param roleId 角色id
	 * @return List<Map<String,Object>>
	 * @throws
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> queryMemberForComp(Long compId, Long roleId);
	/**
	 * 
	 * @Method: editMemberName 
	 * @Description: 修改人员姓名
	 *  @param compId 网点id
	 * @param memberId 人员id
	 * @param memberName 人员姓名
	 * @author haifeng.he
	 * @date 2014-11-10 上午11:58:03
	 * @since jdk1.6
	 */
	public void editMemberName(Long compId,Long memberId, String memberName);

	/**
	 * 
	 * @Method: getMemLocal
	 * @Description: 查找该站点下的收派员的经纬度
	 * @param compId
	 *            网点id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-3 下午7:05:24
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> getMemLocal(Long compId);

	/**
	 * 
	 * @Method: verifyIdentity
	 * @Description: 手机端的身份验证接口
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @param refuseDesc
	 *            拒绝原因
	 * @param flag
	 *            1通过 2拒绝
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-4 上午9:43:19
	 * @since jdk1.6
	 */
	public void verifyIdentity(Long compId,Long memberId, Short flag) ;

	/**
	 * 
	 * @Method: verifyRelation
	 * @Description: 客服归属验证接口
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id 0 收派员 -1 后勤 -2 站长
	 * @param flag 1通过2拒绝
	 * @author haifeng.he
	 * @date 2014-11-4 上午10:39:29
	 * @since jdk1.6
	 */
	public void verifyRelation(Long memberId, Long compId, Short roleId,Short flag);
	/**
	 * 
	 * @Method: memberInfoToComp 
	 * @Description: 手机端注册接口
	 * @param memberId 人员id
	 * @param compId 网点id
	 * @param memberName 人员姓名
	 * @param roleId 角色id
	 * @param memberPhone 人员电话
	 * @
	 * @author haifeng.he
	 * @date 2014-11-5 下午12:41:54
	 * @since jdk1.6
	 */
	public void memberInfoToComp(Long memberId, Long compId, String memberName,Short roleId,String memberPhone,String applicationDesc);
	/**
	 * 
	 * @Method: SynDataFromUcenter 
	 * @Description: 通行证同步数据到api
	 * @param memberId 人员id
	 * @param memberName 人员姓名
	 * @param gender 0男1女
	 * @param birthday 生日
	 * @param idNum 身份证
	 * @param memberPhone 人员手机号
	 * @param memberAddressId 地址id
	 * @param memberDetaileDisplay 地址文字描述
	 * @param memberDetailedAddress 详细地址
	 * @
	 * @author haifeng.he
	 * @date 2014-11-5 下午7:28:16
	 * @since jdk1.6
	 */
	public void SynDataFromUcenter(Long memberId, String memberName,String idNum,
			String memberPhone, Long memberAddressId, String memberDetaileDisplay, String memberDetailedAddress,Short resource);
	/**
	 * 
	 * @Method: queryEmployeeCache 
	 * @Description: 从api缓存中查询人员列表
	 * @param compId 网点id
	 * @return  {
			      "areaColor": "#c2c2c2", 
			      "compId": 13867330511306752, 
			      "employeeWorkStatusFlag": 1, 
			      "id_num": "", 
			      "memberId": 13867278975369216, 
			      "memberName": "管理员", 
			      "memberPhone": "13177770001", 
			      "memberSource": 1, 
			      "roleId": 1
			    }
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:01:57
	 * @since jdk1.6
	 */
	public List<VO_MemberInfo> queryEmployeeCache(Long compId);
	/**
	 * 
	 * @Method: removeMemberInfo 
	 * @Description: 拒绝之后的人员删除操作
	 * @param logId 日志id
	 * @author haifeng.he
	 * @date 2014-11-6 下午4:14:02
	 * @since jdk1.6
	 */
	public void removeMemberInfo(Long compId,Long logId);
	/**
	 * 
	 * @Method: getMemberAddress 
	 * @Description: 查询指定人员的地址位置
	 * @param memberId 人员id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-10 下午2:20:11
	 * @since jdk1.6
	 */
	public List<Map<String,Object>> getMemberAddress(Long memberId);

	
	public List<Map<String,Object>> getAuditInfo(Long memberId);
	
	public Map<String,Object> findMemberInfoFromAudit(Long memberId);
	/**
	 * 
	 * <h6>方法描述:</h6>
	 * <p>具体描述信息 </p>
	 * @param memberId
	 * @return {"data":{"memberId":781398355116084,"relationFlag":1,"veriFlag":0},"success":true}
	 *
	 */
	public Map<String,Object> getValidationStatus(Long memberId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询站长电话</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-19 上午11:28:29</dd>
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	public Map<String,Object> getMasterPhone(Long compId) ;

	public List<Map<String, Object>> loadMemberInfo(Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-26 上午10:52:01</dd>
	 * @param memberId
	 * @param compId
	 * @param roleId
	 * @param applicationDesc
	 * @param flag
	 * @since v1.0
	 */
	public void mobileRegistration(Long memberId, String memberName,String memberPhone,String idNum,Long compId, Short roleId,
			String applicationDesc, Short flag,String memberSourceFlag,String stationPhone);

	public List<Map<String, Object>> queryMemberInfoForFhw(String memberPhone);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点添加通行证已注册人员需要更新通行证</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-26 上午11:20:39</dd>
	 * @param memberId
	 * @param memberName
	 * @since v1.0
	 */
	public void updateUcenter(Long memberId,String memberName);

	public List<Map<String,Object>> queryMemberCoordinate(Long compId);

	public Long getMemberId(String memberPhone);
	
	
	public void editCompName(Long compId,String compName);

	public Map<String,Object> findMemberById(Long memberId);

	public Map<String,Object> findMemberAll(Integer pageNum, Integer pageSize,
			String memberName, String memberPhone, String netId, Short roleType,
			String compType, String compName, Short opinion, String beginTime,
			String endTime,String status);
	public void verifyRelationForOperate(String phone);
	//**************************************************手机端***********************************************************************************/
	
	/**
	 * 
	 * @Method: queryMemberForComp
	 * @Description: 查询站点或营业分部下的人员列表
	 * @param compId 网点id
	 * @param roleId 角色id
	 * @return List<Map<String,Object>>
	 * @throws
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> querySiteMemberList(Long compId, Long roleId);
	
	/**
	 * 
	 * @Method: updateMemberInfo
	 * @Description: 修改右侧人员信息
	 * @param memberId人员id
	 * @param roleId角色id
	 * @param employeeWorkStatusFlag工作标志
	 * @param areaColor片区颜色
	 * @since jdk1.6
	 */
	public void updateMemberInfo(Long compId,Long memberId, Short roleId, Short employeeWorkStatusFlag, String areaColor,Long userId);
	
	
	public void updateAuditOpinion(Long memberId,Short auditOpinion,Short auditRejectReason);
	
	/**
	 * 	查询身份验证标识
	 * @param memberId
	 */
	public Short queryVerifFlag(Long memberId);
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 根据memberPhone查memberId
	 * @data 2015-10-7 下午12:48:12
	 * @param memberPhone
	 * @return memberId
	 */
	public Long getMemberIdByMemberPhone(String memberPhone);
	/**
	 * 
	 * @Method queryBasEmployeeAudit
	 * @author AiJun.Han
	 * @Description  判断等于memberId的是否存在
	 * @data 2015-10-7 上午10:57:38
	 * @param membeId 用户id
	 * @since jdk1.6
	 */
	public Long queryBasEmployeeAudit(long memberId);
	/**
	 * 
	 * @Method ifCourier
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-7 上午11:39:18
	 * @return true 收派员存在  false 收派员不存在
	 * @param memberPhone
	 */
	public boolean ifCourier(String memberPhone);
	
	public Long queryBasEmployeeRelation(long memberId);
	/**
	 * @Method: queryCourierAuditList
	 * @Description: 查询收派员审核列表
	 * @param  associatedNumber
	 * @param memberName 收派员姓名
	 * @param phone 手机号
	 * @param startTime 注册起始时间
	 * @param endTime 注册结束时间
	 * @param auditType 审核类型  0.待审核 1.通过 2.拒绝
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @throws
	 */
	public Map<String,Object> queryCourierAuditList(String memberName, String phone, String startTime, String endTime, 
			Short auditType,Integer pageNo,Integer pageSize);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询待审核人员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-07 上午10:44:04</dd>
	 * @param memberId 用户id
	 * @since v1.0
	 */
	public Map<String,Object> queryCourierAuditInfo(Long memberId);
	
	/**
	 * 
	 * @Method: deleteMemberInfo
	 * @Description: 删除人员
	 * @param  memberId
	 * @return int
	 * @throws
	 */
	public List<String> queryMemberByPhone(String memberPhone);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>用户信息查询</dd>
	 * @param memberPhone 用户手机号
	 * @since v1.0
	 */
	public Map<String,Object> getUserInfo(Long memberPhone);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>非站长用户清除角色</dd>
	 * @param memberId 用户ID
	 * @param compId   所属站点ID
	 * @since v1.0
	 */
	public String clearNormalRoleInfo(Long memberId, Long compId,String memberName, String memberPhone, Short roleId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>站长用户清除角色</dd>
	 * @param memberId 用户ID
	 * @param compId   所属站点ID
	 * @since v1.0
	 */
	public String clearStationRoleInfo(Long oldMemberId, Long newMemberId, Long compId, Short roleId,String memberName, String memberPhone);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点用户查询（不含站长/店长）</dd>
	 * @param compId 站点ID
	 * @since v1.0
	 */
	public List<Map<String,Object>> queryNormalRoles(Long compId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询是否有3天内未完成的抢单取件任务</dd>
	 * @param memberId 用户ID
	 * @since v1.0
	 */
	public Map<String,Object> validTaskHandler(Long memberId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>站长-查询是否申诉</dd>
	 * @param memberId 用户ID
	 * @since v1.0
	 */
	public Map<String,Object> validPlaintInfo(Long memberId);
}
