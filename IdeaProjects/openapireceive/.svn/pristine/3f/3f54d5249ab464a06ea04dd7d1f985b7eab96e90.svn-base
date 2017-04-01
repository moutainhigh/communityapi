package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.LoginInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.core.base.BaseDao;
import net.okdi.core.common.page.Page;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoMapper extends BaseDao{
	Long findMemberIdByMemberPhone(String memberPhone);
	
    /**
	 * Description（方法描述）:收派员查询列表 
	 * Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ
	 * Receive parameters(接收参数):
	 * Return parameters(返回参数):
	 */
	List<MemberInfo> queryMemberInfo(Long compId,Page page);
	/**
	 * Description（方法描述）:添加memberInfo表
	 * Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ
	 * Receive parameters(接收参数):
	 * Return parameters(返回参数):
	 */
	void insertMemberInfo(Map<String, Object> map);

	/**
	 * Description（方法描述）:点击姓名 反显右侧信息
	 * Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ
	 * Receive parameters(接收参数):
	 * Return parameters(返回参数):
	 */
	MemberInfo getMemberInfoById(Long memberId);

	int delMemberInfo(Long memberId);
	/**
	 * Description（方法描述）:添加收派员到指定网点
	 * Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ
	 * Receive parameters(接收参数):
	 * Return parameters(返回参数):
	 */
	void doAddMember(Map<String, Object> map);
	/**
	 * 
	 * @Method: queryUnAudit 
	 * @Description: 人员列表中手机端来源的人员
	 * @param @param compId
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws 
	 * @since jdk1.6
	 */
	List<Map<String, Object>> queryUnAuditMemberInfo(Long compId);
	/**
	 * 
	 * @Method: queryCompNameById 
	 * @Description: 通过网点id查询网点名称
	 * @param compId 网点id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:08:36
	 * @since jdk1.6
	 */
	List<Map<String, Object>> queryCompNameById(Long compId);
	/**
	 * 
	 * @Method: queryIsOne 
	 * @Description: 查询是否唯一
	 * @param associatedNumber 手机号
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:08:53
	 * @since jdk1.6
	 */
	int queryIsOne(String associatedNumber);
	/**
	 * 
	 * @Method: editMemberInfo 
	 * @Description: 修改人员信息
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:09:08
	 * @since jdk1.6
	 */
	void editMemberInfo(Map<String, Object> map);
	/**
	 * 
	 * @Method: queryCount 
	 * @Description: 查询总数
	 * @param memberId 人员id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:09:24
	 * @since jdk1.6
	 */
	int queryCount(Long memberId);
	/**
	 * 
	 * @Method: upMemberInfo 
	 * @Description: 更新人员信息
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:09:37
	 * @since jdk1.6
	 */
	void upMemberInfo(Map<String, Object> map);
	/**
	 * 
	 * @Method: queryRegTime 
	 * @Description: 查询注册时间
	 * @param memberId 人员id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:09:45
	 * @since jdk1.6
	 */
	List<Map<String,Object>> queryRegTime(Long memberId);
	/**
	 * 
	 * @Method: editMemberName 
	 * @Description: 修改人员姓名
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-11 上午9:09:57
	 * @since jdk1.6
	 */
	void editMemberName(Map<String, Object> map);
	/**
	 * 
	 * @Method: verifyIdentity 
	 * @Description: TODO
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-4 上午10:14:05
	 * @since jdk1.6
	 */
	int verifyIdentity(Map<String, Object> map);
	List<MemberInfo> findMemberInfoByCompId(Long compId);
	void saveMemberInfo(VO_MemberInfo vo);
	int updateMemberInfo(VO_MemberInfo vo);
	/**
	 * 
	 * @Method: queryEmployeeCache 
	 * @Description: 查询人员列表
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
	 * @date 2014-11-10 下午8:02:30
	 * @since jdk1.6
	 */
	List<VO_MemberInfo> queryEmployeeCache(Long compId);
	/**
	 * @Method: quertDefulatAddress 
	 * @Description: 好递网发快递查询默认联系人
	 * @param map
	 * @return map
	 * @author mengnan.zhang
	 * @date 2014-11-7 下午02:31:29
	 * @since jdk1.6
	 */
    public Map <String,Object>quertDefulatAddress(Map <String,Object>map);
	List<Map<String, Object>> getMemberAddress(Long memberId);
	List<Map<String, Object>> getAuditInfo(Long memberId);
	Map<String, Object> getValidationStatus(Long memberId);
	Map<String, Object> getMasterPhone(Long compId);
	List<Map<String, Object>> getBranchInfo(Map<String,Object> map);
	Map<String, Object> getRelationInfo(Long memberId);
	List<Map<String, Object>> loadMemberInfo(Long compId);
	int doUpdateMemberInfo(VO_MemberInfo vo);
	
	
	//获取公司登录信息
	LoginInfo getLoginInfoByMemberId(Long memberId);
	List<Map<String, Object>> queryMemberInfoByMemberPhone(String memberPhone);
	void updateName(VO_MemberInfo vo);
	
	
	Map<String, Object> queryMemberNameByMemberId(Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>手机号查询人员对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-24 下午4:39:50</dd>
	 * @param memberPhone 手机号
	 * @return
	 * @since v1.0
	 */
	MemberInfo queryMemberByMemberPhone(String memberPhone);
	void updateSource(Long memberId);
	
	Map<String,Object> getInfoByMemberId(Long memberId);
	Map<String, Object> getCompInfoByMemberId(Long memberId);
	List<Map<String, Object>> findMemberAll(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,
			@Param("memberName") String memberName,@Param("memberPhone") String memberPhone, 
			@Param("netId") String netId, @Param("roleType") Short roleType,@Param("compType") String compType,
			 @Param("compName") String compName, @Param("opinion") Short opinion,
			 @Param("beginTime") String beginTime,@Param("endTime") String endTime);
	int getCountTotalPending(@Param("memberName") String memberName,@Param("memberPhone") String memberPhone, 
			@Param("netId") String netId, @Param("roleType") Short roleType,@Param("compType") String compType,
			 @Param("compName") String compName, @Param("opinion") Short opinion,
			 @Param("beginTime") String beginTime,@Param("endTime") String endTime);
	int getCountTotalAll(@Param("memberName") String memberName,@Param("memberPhone") String memberPhone, 
			@Param("netId") String netId, @Param("roleType") Short roleType,@Param("compType") String compType,
			 @Param("compName") String compName, @Param("opinion") Short opinion,
			 @Param("beginTime") String beginTime,@Param("endTime") String endTime);
	int getallMemberNum();
	int getUnPassNum();
	
	List<Map<String, Object>> findMemberPinding(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,
			@Param("memberName") String memberName,@Param("memberPhone") String memberPhone, 
			@Param("netId") String netId, @Param("roleType") Short roleType,@Param("compType") String compType,
			 @Param("compName") String compName, @Param("opinion") Short opinion,
			 @Param("beginTime") String beginTime,@Param("endTime") String endTime);
	
	/**
	 * 	修改审核状态
	 * @param param
	 */
	public void updateVerifFlag(Map<String,Object> param);
	
	
	public Short queryVerifFlag(Long memberId);
	
	/**
	 * 
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
	public List<Map<String,Object>> queryCourierAuditList(Map<String,Object> map);
	/**
	 * 
	 * @Method: queryCourierAuditList
	 * @Description: 查询收派员审核列表总数
	 * @param  associatedNumber
	 * @param memberName 收派员姓名
	 * @param phone 手机号
	 * @param startTime 注册起始时间
	 * @param endTime 注册结束时间
	 * @param auditType 审核类型  0.待审核 1.通过 2.拒绝
	 * @throws
	 */
	public Integer queryCourierAuditCount(Map<String,Object> map);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询待审核人员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-07 上午10:44:04</dd>
	 * @param memberId 用户id
	 * @since v1.0
	 */
	public Map<String,Object> queryCourierAuditInfo(@Param("memberId")Long memberId);
	public List<String> queryMemberByPhone(@Param("memberPhone")String memberPhone) ;
	public void deleteResumeByMemberId(@Param("memberId")Long memberId) ;
	public void deleteAuditByMemberId(@Param("memberId")Long memberId) ;
	public void deleteRelationalByMemberId(@Param("memberId")Long memberId) ;
	public void deleteInfoByMemberId(@Param("memberId")Long memberId) ;
}