package net.okdi.apiV1.service;

public interface ExpressSiteService {
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Double longitude, Double latitude, String county,String address);
	
	public String queryCompVerifyInfo(Long compId);
	
	
	public String queryJoinState(String member_phone);
	
	public String applyJoin(String member_id,String audit_comp,String application_role_type,String audit_item);
	
	public String applyJoinNoSign(String member_id,String belongToNetId,String application_role_type,
			 String toMemberPhone);
    
	public String invite( Long fromMemberId, String fromMemberPhone,
			  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId);
	
	public String submitCompVerifyInfo(String loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
				String frontImg,String businessLicenseImg, String shopImg,Long memberId)	;
	
	public String auditSite(String compId, String compStatus);
	
	public String submitBasCompInfo(Long loginCompId, String compName, String belongToNetId, String county, 
			Double longitude, Double latitude,String address,String responsibleTelephone,String member_id) ;

	public String queryMyMessage(String phone);

	public String queryMyMessage(Long memberId);

	public String deleleMyAllMessage(String memberPhone);

	public String deleleMyOneMessage(Long pushId);

	public String readOneMessage(Long pushId);

	public String queryMessageDetails(Long id, String memberId);

	public String queryMessageNewest(String memberId);

	public String addNoticeState(String Id, String memberId);

}
