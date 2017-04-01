package net.okdi.apiV2.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompimage;

import org.apache.ibatis.annotations.Param;

public interface BasPlaintInfoMapperV2 {

	//插入申诉人信息
	public int savePlaintSiteInfo(@Param("map")Map<String, Object> map);

	//查询虚拟站点
	public Map<String, Object> queryVirtualSite(@Param("netId")String netId);

	//插入到审核表中
	public void saveBasEmployeeAudit(@Param("map")Map<String, Object> auditMap);

	//保存照片
	public void saveCompImageBatch(List<BasCompimage> compimageList);

	//删除本人原来的上传照片
	public void deleteImageByPlaintId(@Param("id")long id);
	
	//查询申请记录
	public List<Map<String, Object>> queryPlaintSiteInfo(@Param("map")Map<String, Object> map);

	//根据申诉者手机号查询该站点下的站长手机号
	public Map<String,Object> querySitePhoneByPlaintId(@Param("id")Long id);

	//查询所有的申诉者的手机号
	public List<Long> queryPlaintPhone(@Param("map")Map<String,Object> map);

	//查询申诉人详情
	public Map<String, Object> queryPlaintSiteInfoDetail(@Param("id")String id);

	//查询三张照片的名字
	public List<Map<String,Object>> queryBasCompImageInfo(@Param("id")String id);

	//根据memberId查询审核人的名字
	public String queryMemberNameByMemberId(@Param("auditUser")String auditUser);

	//查询记录的总条数
	public Long queryPlaintSiteInfoCount(@Param("map")Map<String, Object> map);

	//更新申请人的信息
	public void updateBasPlaintInfoById(@Param("id")String id, @Param("flag")String flag,
			@Param("auditUser")Long auditUser, @Param("desc")String desc, @Param("date")Date date);

	//更改原来的站长为收派员
	public void updateBasEmployeeAuditByCompId(@Param("compId")String compId);

	//更改原来的站长为收派员
	public void basEmployeeRelationByCompId(@Param("compId")String compId);

	//插入到bas_employee_relation 关系表中建立关系
	public void saveEmployeeRelation(@Param("map")Map<String, Object> emap);

	//根据memberId删除bas_employeeaudit虚拟信息
	public void deleteBasEmployeeAuditByMemberId(@Param("compId")Long compId, @Param("memberId")String memberId);

	//根据plaintId更改信息
	public void updateBasCompimageById(@Param("plaintId")String id,@Param("compId")String compId);

	public void updateBasCompimageByCompId(@Param("compId")String compId);

	//修改member_info 表中该申诉人的身份证
	public void updateMemberInfoByMemberId(@Param("memberId")String memberId, @Param("idNum")String idNum);

	//更改bas_compbusiness表中负责人的信息
	public void updateBasCompbusiness(@Param("compId")String compId, @Param("responsible")String responsible,
			@Param("responsibleTelephone")String responsibleTelephone, @Param("idNum")String idNum, @Param("business")String business);

	//根据真实的compId查询该网络下的虚拟的compId
	public Long queryVirtualCompIdByCompId(@Param("compId")String compId);

	//根据id查询bas_planitInfo表中的 compName
	public Map<String,Object> queryCompNameById(@Param("id")String id);

	//查询虚拟站点下是否还有这个收派员的记录
	public Map<String,Object> queryIsNotHave(@Param("memberId")String memberId, @Param("virCompId")Long virCompId);

	/*//根据真实的compId查询原站长的memberid
	public Map<String, Object> queryMemberIdByCompId(@Param("compId")String compId);

	//根据memeberid查询原站长的手机号
	public Map<String, Object> queryOldMemberPhoneByCompId(@Param("oldMemberId")String oldMemberId);
	 */
	//根据compId查询原站长的memberId和memberPhone
	public Map<String, Object> queryOldMemberIdAndMemberPhoneByCompId(@Param("compId")String compId);

	public Map<String, Object> queryIsNotBasCompBussiness(@Param("compId")String compId);

	public void saveBasCompBussiness(@Param("compId")String compId, @Param("responsible")String responsible,
			@Param("responsibleTelephone")String responsibleTelephone, @Param("idNum")String idNum, @Param("business")String business);

	public Map<String, Object> queryWebmasterISNotDeparture(@Param("compId")String compId);

	public void updateBasCompinfoStatusByCompId(@Param("compId")String compId);

	

}
