package net.okdi.apiV1.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasCompbusiness;

import org.apache.ibatis.annotations.Param;


public interface BasCompBusinessMapperV1 {
	
	public BasCompbusiness findById(Long id);
	
	public  int insert(BasCompbusiness basCompbusiness) ;
	
	public  int insertMessage(BasCompbusiness basCompbusiness) ;
	
	public  int insertBusiness(@Param("map")Map<String,Object>map) ;
	
	public void deleteBusinessMessage(Long compId);
	/**
	 * @author jingguoqiang
	 */
	public String queryBusinesslicense(Long compId);
	/**
	 * @author jingguoqiang
	 */
	public String queryComplicense(Long compId);
	
	/**
	 * @author jingguoqiang
	 */
	public void updateBusinesslicense(@Param("compMess")String compMess,@Param("compId")Long compId);
	/**
	 * @author jingguoqiang
	 */
	public void updateComplicense(@Param("compMess")String compMess,@Param("compId")Long compId);
	/**
	 * @author jingguoqiang
	 */
	public String queryPhoneByBusinesslicense(String license);
	/**
	 * @author jingguoqiang
	 */
	public String queryPhoneByCompLicense(String license);
	
	public String queryCompId(Long compId);
	
}