package net.okdi.apiV4.dao;

import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.MemberInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface AttentionMapper {
	/**
	 * 
	 * @Description: 根据用户id查询用户名
	 * @param fromMemberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-23
	 */
	String findNameById(Long fromMemberId);
	/**
	 * 
	 * @Description: 根据compId查询站点成员列表(不包括站长)
	 * @param compId
	 * @return List<MemberInfo>
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-24
	 */
	List<MemberInfo> queryEmployeeByCompId(Long compId);
	/**
	 * 
	 * @Description: 根据memberId查询所属站点信息
	 * @param actualSendMember
	 * @return Map<String,Object>
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-27
	 */
	Map<String, Object> finCompInfoByMemberId(Long actualSendMember);

}
