package net.okdi.apiV1.service;

import java.util.Map;

public interface IAttributionService {
	/**
	 * 归属信息-店长（已认证）
	 * 归属信息-店员（已认证）
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param memberId
	 * @return
	 */
	public String findShopowner(String memberId,String roleId);

	 
}
