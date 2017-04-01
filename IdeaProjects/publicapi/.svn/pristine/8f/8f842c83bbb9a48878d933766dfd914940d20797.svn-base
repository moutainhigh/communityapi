package net.okdi.mob.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface NetDotService {

	
	public String saveBelongingAuthentictaInfo(Long id,Long netId,String netName,Long compId,
			 String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo);
	
	
	public String saveOrUpdateNetDotInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType, Short compStatus);
	
	public String queryNetDotInfo(Long compId);
	
	public String updateNetDotStatus(Long compId, Short compStatus, Long auditId);
	
//	public String record(Long memberId, String memberPhone, String channelNo, String regip, String deviceType,
//			String deviceToken);
	
	public Map<String,Object> uploadImage(MultipartFile myfile);
	
	public String validNetIsChanged();
}
