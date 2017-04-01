package net.okdi.apiV4.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.apiV4.entity.BasCompInfo;
import net.okdi.apiV4.entity.MemberInfoVO;

import org.apache.ibatis.annotations.Param;


public interface BasEmployeeAuditMapperV4 {

	MemberInfoVO queryMemberInfoByZhaohu(@Param("memberId")String memberId);

	List<Map<String, Object>> queryAllNetNameAndNetId();

	HashMap<String, Object> queryProvinceId(Long memberId);

	BasCompInfo getDaiByCompId(String compId);
	
	/**查询basnetinfo中授权状态
	 * @param netId
	 * @return
	 */
	BasNetInfo querystatusfrombasnet(Long netId);
}