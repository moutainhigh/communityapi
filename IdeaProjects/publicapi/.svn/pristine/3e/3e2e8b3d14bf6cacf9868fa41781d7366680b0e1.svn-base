package net.okdi.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.okdi.api.dao.OauthAccessKeyMapper;
import net.okdi.api.entity.OauthAccessKey;
import net.okdi.api.service.EhcacheService;
import net.okdi.api.service.OauthAccessService;
import net.okdi.core.util.PubMethod;

@Service
public class OauthAccessServiceImpl implements OauthAccessService {

	@Autowired
	private OauthAccessKeyMapper oauthAccessKeyMapper;

	@Autowired
	private EhcacheService ehcacheService;

	@Override
	public OauthAccessKey getEntityByKey(String publicKey) {
		OauthAccessKey entity = this.ehcacheService.get("oauthKey", publicKey, OauthAccessKey.class);
		if (PubMethod.isEmpty(entity)) {
			entity = this.oauthAccessKeyMapper.getEntityByKey(publicKey);
			this.ehcacheService.put("oauthKey", publicKey, JSON.toJSONString(entity));
		}
		return entity;
	}

}
