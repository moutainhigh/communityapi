package net.okdi.apiV2.service;

import java.util.Map;

public interface PlaintSiteService {

	public String savePlaintSiteInfo(Long memberId, Long compId, String compName,
			Short compType, String plaintPhone,String headPhone, String responsible,  String idNum, String business, String netId,
			String businessLicenseImg, String shopImg);

	public Map<String,Object> queryPlaintSiteInfo(String currentPage,String pageSize,String startTime, String endTime,
			String plaintPhone, String status);

	public Map<String, Object> queryPlaintSiteInfoDetail(String id);

	public String agreedReplaceSite(String id, String compId, String memberId, String flag,
			String auditUser, String desc, String idNum, String responsible, String responsibleTelephone, String business);

	public String queryIsNotHaveBasEmployee(String compId, String memberId);

	
}
