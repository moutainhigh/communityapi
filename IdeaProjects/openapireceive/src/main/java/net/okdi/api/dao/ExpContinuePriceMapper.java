package net.okdi.api.dao;

import java.util.List;

import net.okdi.api.entity.ExpContinuePrice;
import net.okdi.api.entity.ExpPriceAddress;
import net.okdi.core.base.BaseDao;

public interface ExpContinuePriceMapper  extends BaseDao{
	//根据首重id 批量删除续重信息
	public void batchDeleteByFirstPriceId(List<Object> firstPriceIdList);
	//批量保存价格续重信息
	public void batchSaveContinuePrice(List<ExpContinuePrice> expContinuePriceList);
}