package net.okdi.api.dao;

import net.okdi.api.entity.EnterpriseCooperationSite;

public interface EnterpriseCooperationSiteMapper {
    int deleteByPrimaryKey(Long enterpriseShopId);

    int insert(EnterpriseCooperationSite record);

    int insertSelective(EnterpriseCooperationSite record);

    EnterpriseCooperationSite selectByPrimaryKey(Long enterpriseShopId);

    int updateByPrimaryKeySelective(EnterpriseCooperationSite record);

    int updateByPrimaryKey(EnterpriseCooperationSite record);
}