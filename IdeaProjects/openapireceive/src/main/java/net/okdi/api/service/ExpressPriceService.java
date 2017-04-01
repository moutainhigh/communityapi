package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasExpressPrice;
import net.okdi.api.vo.VO_ExpressPrice;
import net.okdi.core.base.BaseService;

public interface ExpressPriceService extends BaseService<BasExpressPrice> {
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
	public BigDecimal getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算包裹总价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午7:23:07</dd>
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param weightAndreceiveAddressIds 重量、收件地址ID      weight-addressId,weight-addressId
	 * @return Map<String,BigDecimal>
	 * @since v1.0
	 */
	public Map<String,BigDecimal> getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>包裹收款/结算</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午4:38:14</dd>
	 * @param parcelIds 包裹ID
	 * @param totalCodAmount 代收货款金额
	 * @param totalFreight 实收运费
	 * @param memberId 创建人 
	 * @param type 类型 0取件 1派件
	 * @since v1.0
	 */
	public void settleAccounts(String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,short type);
}
