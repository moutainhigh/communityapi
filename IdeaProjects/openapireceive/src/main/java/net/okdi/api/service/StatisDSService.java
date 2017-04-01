package net.okdi.api.service;

public interface StatisDSService {
	void addDsVisitCount(String shortkey, String time, String shopId);
	void addDsDownCount(String shortkey,String productType,String userAgent,String appType);
}
