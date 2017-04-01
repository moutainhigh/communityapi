package net.okdi.apiV4.service;

import java.util.Date;

import java.util.List;
import java.util.Map;


import net.okdi.core.common.page.Page;

public interface ReceiveOrderFromWebsiteService {

	
	public String  queryTakePackListFromWebsite(String userCode, String orgCode,String memberId,String netId);

	public String saveParcelFromGT(String parcels, String memberId,String netId);

	public String QueryParcelFromGT(long parseLong, long netId, int parseInt, int parseInt2);

	public String confirmTakeParcelGT(String uids, String memberId, String netId, String terminalId, String versionId);

	public String huntParcelGT(String mobile, long memberId, long netId, int currentPage, int pageSize, String flag);
}
