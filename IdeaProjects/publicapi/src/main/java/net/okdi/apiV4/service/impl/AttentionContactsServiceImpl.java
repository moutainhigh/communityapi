package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV3.service.impl.WalletNewServiceImpl;
import net.okdi.apiV4.service.AttentionContactsService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
@Service
public class AttentionContactsServiceImpl implements AttentionContactsService {

	@Autowired
    private ConstPool constPool;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	public static final Log logger = LogFactory.getLog(AttentionContactsServiceImpl.class);
	
	/**
	 * 查询附近快递员20条数据
	 */
	@Override
	public String queryNearMemberForAttention(Double lng, Double lat,
			Integer howFast,String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lng",lng);
		map.put("lat",lat);
		map.put("howFast",howFast);
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStrToCallCourier("Courier/queryNearMemberForAttention/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.AttentionContactsServiceImpl.queryNearMemberForAttention.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 通讯录好友列表--关注联系人使用
	 */
	@Override
	public String queryContactsBookList(Long memberId, String jsonData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("jsonData",jsonData);
		String response = openApiHttpClient.doPassSendStrToCommunity("attentionContacts/queryContactsBookList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.AttentionContactsServiceImpl.queryContactsBookList.001","数据请求异常");
		}
		return response;
	}

}
