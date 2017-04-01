package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.NetLowestPrice;
import net.okdi.api.entity.NetLowestPriceExample;
import org.apache.ibatis.annotations.Param;

public interface NetLowestPriceMapper {
    int countByExample(NetLowestPriceExample example);

    int deleteByExample(NetLowestPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NetLowestPrice record);

    int insertSelective(NetLowestPrice record);

    List<NetLowestPrice> selectByExample(NetLowestPriceExample example);

    NetLowestPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NetLowestPrice record, @Param("example") NetLowestPriceExample example);

    int updateByExample(@Param("record") NetLowestPrice record, @Param("example") NetLowestPriceExample example);

    int updateByPrimaryKeySelective(NetLowestPrice record);

    int updateByPrimaryKey(NetLowestPrice record);

	NetLowestPrice queryLowestPrice(Map<String,Object> map);
}