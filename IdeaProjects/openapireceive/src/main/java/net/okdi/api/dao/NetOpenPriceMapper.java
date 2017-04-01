package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.core.base.BaseDao;

public interface NetOpenPriceMapper extends BaseDao{
   public List<Map<String,Object>> getNetOpenPrice(Long formAddressId);
}