package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasExpressPrice;
import net.okdi.api.vo.VO_ExpressPrice;
import net.okdi.core.base.BaseDao;

public interface BasExpressPriceMapper  extends BaseDao{
	/**
	 * @Description: 根据网络ID和省份ID获取网络报价
	 * @author feng.wang
	 * @param netId
	 *            网络ID
	 * @param provinceId
	 *            收件地址省份ID
	 * @return
	 */
	public List<VO_ExpressPrice> getNetQuote(Map<String, Object> params);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询所有网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午4:27:27</dd>
	 * @return List<BasExpressPrice>
	 * @since v1.0
	 */
	public List<BasExpressPrice> queryExpressPrice();
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络地址查询网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午5:42:47</dd>
	 * @param params
	 * @return BasExpressPrice
	 * @since v1.0
	 */
	public BasExpressPrice queryExpressPriceByNetAndAddress(Map<String, Object> params);
}