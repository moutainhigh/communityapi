package net.okdi.apiV2.service;


public interface PlaintSiteService {

	public String savePlaintSiteInfo(Long memberId, Long compId, String compName,
			Short compType, String plaintPhone, String headPhone, String responsible, String idNum, String business, String netId,
			String businessLicenseImg, String shopImg);

	
}
