package net.okdi.apiV4.service.impl;

import net.okdi.apiV4.service.DeliveryService;
import net.okdi.core.passport.OpenApiHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 包名: net.okdi.apiV4.service.impl
 * 创建人 : Elong
 * 时间: 05/02/2017 5:37 PM
 * 描述 :
 */
@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Autowired
    private OpenApiHttpClient openApiHttpClient;

    //查询发货列表
    @Override
    public String queryDeliveryList(String memberId, Long netId, Long compId) {
        Map<String,Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("netId", netId);
        map.put("compId", compId);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/queryDeliveryList", map);
    }

    @Override
    public String queryNotPrintList(String memberId, Long netId, Long compId) {
        Map<String,Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("netId", netId);
        map.put("compId", compId);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/queryNotPrintList", map);
    }

    //确认发货
    @Override
    public String confirmDelivery(String uids,String deliveryAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("uids", uids);
        params.put("deliveryAddress", deliveryAddress);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/confirmDelivery", params);
    }
    //查询发货详情
	@Override
	public String queryDeliveryDetail(String uid) {
		 Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/queryDeliveryDetail", params);
	}
	//根据二维码获取包裹列表
	@Override
	public String getParListByQRCode(String uids) {
	 Map<String, Object> params = new HashMap<>();
        params.put("uids", uids);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/getParListByQRCode", params);
    }
	//确认收货
	@Override
	public String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index) {
		Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("uids", uids);
        params.put("deliveryAddress", deliveryAddress);
        params.put("index", index);
        return openApiHttpClient.doPassTakeStrParcel("deliveryPackage/confirmTakeDelivery", params);
    }

}
