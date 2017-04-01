package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV4.service.IMService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

@Service
public class IMServiceImpl extends AbstractHttpClient<String> implements IMService {

	private @Autowired ConstPool constPool;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressCommunityServiceImpl.class);
	
	@Override
	public String allocate(Long mid) {
		Map<String, Object> params = new HashMap<>();
		params.put("receiver", mid);
		String methodName = "message/allocate";
		String url = constPool.getImUrl() + methodName;
		String post = Post(url, params);
		LOGGER.debug("分配长连接地址返回值 :: mid => " + mid + " :: 地址 => " + post);
		return post;
	}
	
	@Override
	public String offlineMsg(Long mid) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", mid);
		String methodName = "message/offline";
		String url = constPool.getImUrl() + methodName;
		String post = Post(url, params);
		LOGGER.debug("拉取离线消息返回值 :: mid => " + mid + " :: 地址 => " + post);
		return post;
	}
	
}






















