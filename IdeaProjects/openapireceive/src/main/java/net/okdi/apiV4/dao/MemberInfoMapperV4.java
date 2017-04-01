package net.okdi.apiV4.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface MemberInfoMapperV4 {
	String findMemberPhoneByMemberId(Long memberId);

	Map<String, Object> queryMomberPhoneByMemberId(String memberId);

	String findNetNameByNetId(Long netId);

	Long findCompIdByMemberId(Long memberId);

	Long findNetIdByCompId(Long compId);
	
	Long findNetIdByComp(Long compId);

	String findMemberNameByMemberId(Long memberId);

	List<Map<String, Object>> querySiteMemberByMemberId(Long memberId);

	Map<String, Object> findLongOrLatiTudeByMemberId(Long memberId);

	List<Map<String, Object>> queryNearMemberByTude(@Param("topLat")Double topLat,
			@Param("bottomLat")Double bottomLat, @Param("leftLng")Double leftLng, @Param("rightLng")Double rightLng);

	Long findMemberIdByNameAndPhone(String newMemberPhone);

	String findMemberAddressByMemberId(Long memberId);

	List<Long> findNetIdByNetName(String netName);

	String findRoleByMemberId(Long memberId);


 }
