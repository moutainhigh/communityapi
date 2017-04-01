package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompimage;
import net.okdi.api.entity.ExpPriceAddress;
import net.okdi.core.base.BaseDao;

public interface ExpPriceAddressMapper  extends BaseDao {
	//根据首重ID批量删除
	public void batchDeleteByFirstPriceId(List<Object> firstPriceIdList);
	//根据主键ID批量删除
	public void batchDeleteById(List<Object> idList);
	//获取存在报价的子地址
	public List<Map<String,Object>> getChildAddress(Map<String, Object> paras);
	//根据首重ID获取存在地址的首重ID
	public List<Long> getFirstPriceIdByFirstPriceId(List<Object> firstPriceIdList);
	//批量保存报价地址信息
	public void batchSavePriceAddress(List<ExpPriceAddress> expPriceAddressList);
}