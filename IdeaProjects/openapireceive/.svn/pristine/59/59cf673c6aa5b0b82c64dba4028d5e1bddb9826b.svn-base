package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpFirstPrice;
import net.okdi.core.base.BaseDao;

public interface ExpFirstPriceMapper  extends BaseDao{
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据优惠组ID获取报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午9:46:02</dd>
	 * @param paras
	 * @return List<Map>
	 * @since v1.0
	 */
	public List<Map> getPriceListByGroupId(Map<String, Object> paras);
	//根据优惠组ID查询首重ID
	public List<Long> getIdByGroupId(Map<String, Object> paras);
	//根据首重ID批量删除首重数据
	public void batchDeleteById(List<Object> idList);
	//批量保存首重信息
	public void batchSaveFristPrice(List<ExpFirstPrice> expFirstPriceList);
}