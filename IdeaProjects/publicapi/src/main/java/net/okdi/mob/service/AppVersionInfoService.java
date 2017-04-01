package net.okdi.mob.service;

public interface AppVersionInfoService {

	String queryAppVersionInfo(Short appType);
	String queryAppVersionInfoIos();
	String updateAppVersionInfo(Short appType);

}
