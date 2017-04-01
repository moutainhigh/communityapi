package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.MemberAddressInfo;
import net.okdi.api.entity.MemberAddressInfoExample;

import org.apache.ibatis.annotations.Param;

public interface MemberAddressInfoMapper {
    int countByExample(MemberAddressInfoExample example);

    int deleteByExample(MemberAddressInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberAddressInfo record);

    int insertSelective(MemberAddressInfo record);

    List<MemberAddressInfo> selectByExample(MemberAddressInfoExample example);

    MemberAddressInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemberAddressInfo record, @Param("example") MemberAddressInfoExample example);

    int updateByExample(@Param("record") MemberAddressInfo record, @Param("example") MemberAddressInfoExample example);

    int updateByPrimaryKeySelective(MemberAddressInfo record);

    int updateByPrimaryKey(MemberAddressInfo record);

	List<Map<String, Object>> getMemLocal(Long compId);
}