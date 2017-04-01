package net.okdi.apiV1.service;

import java.util.Map;

public interface LoginService {
	Map<String, Object> login(String channelNo,String userName,String password,String deviceType,String deviceToken,String version,String address,Integer source);

	String longResult(String  userName,String password,Integer source);


}
