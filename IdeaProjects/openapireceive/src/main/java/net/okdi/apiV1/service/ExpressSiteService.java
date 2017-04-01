package net.okdi.apiV1.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.apiV1.entity.AnnounceMessageInfo;
import net.okdi.apiV1.entity.PushMessageToSomeOne;

public interface ExpressSiteService {
	
	public BasCompInfo saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Double longitude,Double latitude, String county,String address);
	
	
	public Map<String, Object> queryJoinState(String member_phone);
	
	public void applyJoin(String member_id,String audit_comp,String application_role_type,String audit_item);
	
	/*public void applyJoinNoSign(String member_id,String belongToNetId,String application_role_type,
			 String toMemberPhone);*/
    
		/*public String invite(String fromMemberId, String fromMemberPhone,
				  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId);*/
	
	public Map submitCompVerifyInfo(Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
				String frontImg,String holdImg , String reverseImg, Long memberId)	;
	
	public String submitBasCompInfo(Long loginCompId, String compName, String belongToNetId, String county, 
			Double longitude, Double latitude,String address,String responsibleTelephone,String member_id) ;
	
	public void auditSite(Long compId, String compStatus);
	
	
	public Map updateCompStatus(Long compId,  String compMess, String compType) ;
	public void  updateCompStatusToRefuse(Long compId, String refuseDesc);
	
	public String queryCompName(Long compId) ;
	
	public Map<String, Object> queryCompBasicInfo(Long compId);
	
	public void saveEmployeeaudit(String memberId,String compId,String roleType) ;
	
	public void sendTS(String title,String message,String extraParam,String phones, String pushWay,String platform,String useType,String userType,String startTime1,String endTime1) ;
	
	public Map queryPhones(String phone,Integer currentPage,Integer pageSize);
	
	public List exportPhones(String phone) ;


	public List<AnnounceMessageInfo> queryMyMessage(String phone);




	public void deleleMyAllMessage(String memberPhone);


	public void deleleMyOneMessage(Long pushId);


	public void readOneMessage(Long pushId);


	public void sendAnnounceTS(String announceType, String title,String content, String creator, String idkey, String pushWay);
	
	
}
