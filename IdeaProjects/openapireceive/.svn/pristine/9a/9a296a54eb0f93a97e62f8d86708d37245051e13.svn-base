package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.DicAddress;

public interface DicAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(DicAddress record);

    int insertSelective(DicAddress record);

    DicAddress selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(DicAddress record);

    int updateByPrimaryKey(DicAddress record);
    
    List<Map<String,Object>> DicAddressByparseAddr(Map<String,Object> parameterMap);
    
    DicAddressaid getObjectByPrimaryKey(Long addressId);

	List<DicAddress> queryProvinceInfo();
}