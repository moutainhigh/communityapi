package net.okdi.api.service;

import java.util.List;
import java.util.Map;

public interface NetDotInfoService {

	/**
	 * 	保存归属认证信息
	 */
	public void saveBelongingAuthentictaInfo(Long id,Long netId,String netName,Long compId,
			String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo);
	
	
	/**
	 * 		保存/修改 站点认证信息
	 */
	public void saveOrUpdateNetDotInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType, Short compStatus);
	
	
	/**
	 * 
	 * @Method: queryCompBasicInfo 
	 * @Description: 查询网点基础信息
	 * @param loginCompId 网点ID
	 */
	public Map<String, Object> queryNetDotBasicInfo(Long compId);
	
	
	/**
	 * @Method: queryCompBasicInfo 
	 * @Description: 查询网点认证信息
	 * @param loginCompId 网点ID
	 */
	public Map<String, Object> queryNetDotVerifyInfo(Long loginCompId);
	
	/**
	 * 	站长信息
	 */
	public List<Map<String,Object>> queryMemberForComp(Long compId, Long roleId);
	
	/**
	 * 	修改网店状态
	 */
	public void updateNetDotStatus(Long compId, Short compStatus, Long auditId);
	
}
