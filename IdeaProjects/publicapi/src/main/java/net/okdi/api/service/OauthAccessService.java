package net.okdi.api.service;

import net.okdi.api.entity.OauthAccessKey;

public interface OauthAccessService {

	public OauthAccessKey getEntityByKey(String publicKey);
	
}
