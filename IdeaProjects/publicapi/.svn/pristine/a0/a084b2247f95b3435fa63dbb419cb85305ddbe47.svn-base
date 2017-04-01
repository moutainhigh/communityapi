package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ExpressPriceService{
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算网络运费</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 上午10:55:08</dd>
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param receiveAddressId 收件地址ID
	 * @param weight 重量
	 * @return 价格
	 * @since v1.0
	 */
	public String getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算包裹总价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午7:23:07</dd>
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param weightAndreceiveAddressIds 重量、收件地址ID      weight-addressId,weight-addressId
	 * @return String
	 * @since v1.0
	 */
	public String getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds);
}
