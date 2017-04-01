package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.okdi.api.entity.ExpCustomerContactsInfo;

@Repository
public interface ExpCustomerContactsInfoMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByCustomerId(Long customerId);

    int insert(ExpCustomerContactsInfo record);
    int insertContactsList(List<ExpCustomerContactsInfo> list);
    
    int insertSelective(ExpCustomerContactsInfo record);
    
    List<ExpCustomerContactsInfo> selectContactsByCustomerId(Long customerId);

    Integer selectCount(Map map);
    
    Integer getContactsCount(Map map);
    
    List<ExpCustomerContactsInfo> selectCustomers(Map map);
   
    
    int updateByPrimaryKeySelective(ExpCustomerContactsInfo record);

    int updateByPrimaryKey(ExpCustomerContactsInfo record);
}