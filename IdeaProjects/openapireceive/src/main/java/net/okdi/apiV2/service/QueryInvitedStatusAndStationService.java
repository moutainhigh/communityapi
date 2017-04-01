package net.okdi.apiV2.service;

import java.util.Map;

public interface QueryInvitedStatusAndStationService {

	Map<String, Object> queryInvitedStatusAndStation(String memberId);

	boolean ifIsCourier(String memberId);

	boolean ifOwnershipAudit(String memberId);

	int ifIsAssistantOrManager(String memberId);

	String findCompName(String memberId);

}
