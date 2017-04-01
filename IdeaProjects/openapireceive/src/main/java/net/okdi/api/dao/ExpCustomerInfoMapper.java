package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.okdi.api.entity.ExpCustomerInfo;

@Repository
public interface ExpCustomerInfoMapper {
	
	Integer selectCount(Map map);
	List<ExpCustomerInfo> selectByContactName(Map map);
	
	Integer getContactsCount(Map map);
	List<Long> selectCustomers(Map map);
	
	ExpCustomerInfo selectByKey(Long id);
	
    int deleteByPrimaryKey(Long id);

    int insert(ExpCustomerInfo record);

    int insertSelective(ExpCustomerInfo record);

    ExpCustomerInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpCustomerInfo record);

    int updateByPrimaryKey(ExpCustomerInfo record);
	List<ExpCustomerInfo> queryByErpIdandCompId(Map map);
	void clearDiscountGroupIdByCompId(Map map);
	int insertV4(ExpCustomerInfo record);
	List<ExpCustomerInfo> selectCustomer(Long compId);
	void editCustomer(ExpCustomerInfo record);
	void addCustomerType(Map map);
	void deleteCustomerType(Map<String, String> map);
	List<ExpCustomerInfo> selectCustomersByMobile(Map map);
}