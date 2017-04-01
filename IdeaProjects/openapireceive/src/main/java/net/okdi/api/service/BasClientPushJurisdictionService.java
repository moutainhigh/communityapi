package net.okdi.api.service;

import net.okdi.api.entity.BasClientPushJurisdiction;

public interface BasClientPushJurisdictionService {
	
	BasClientPushJurisdiction findByBasClientPushJurisdiction(Long memberId);
	void saveOrupdateBasClientPushJurisdiction(BasClientPushJurisdiction basClientPushJurisdiction);
}
