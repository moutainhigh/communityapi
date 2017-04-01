package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.api.service.ExpressPriceService;
import net.okdi.httpClient.ExpressPriceHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 网络信息
 * @author 翟士贺
 * @date 2014-10-18下午3:13:31
 */
@Service
public class ExpressPriceServiceImpl implements ExpressPriceService {
	@Autowired
	private ExpressPriceHttpClient expressPriceHttpClient;
	/**
	 * 计算网络运费
	 * @Method: getExpressPrice 
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param receiveAddressId 收件地址ID
	 * @param weight 重量
	 * @return 价格
	 * @see net.okdi.api.service.ExpressPriceService#getExpressPrice(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	@Override
	public String getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight){
		return this.expressPriceHttpClient.getExpressPrice(netId, sendAddressId, receiveAddressId, weight);
	}
	/**
	 * 计算包裹总价格
	 * @Method: getExpressTotalPrice 
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param weightAndreceiveAddressIds 重量、收件地址ID      weight-addressId,weight-addressId
	 * @return String
	 * @see net.okdi.api.service.ExpressPriceService#getExpressTotalPrice(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	public String getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds){
		return this.expressPriceHttpClient.getExpressTotalPrice(netId, sendAddressId, weightAndreceiveAddressIds);
	}
}
