package net.okdi.apiV2.dao;

import java.util.Map;

import net.okdi.api.entity.BasCompbusiness;


public interface BasCompBusinessMapperV2 {

	
	public  int insertMessage(BasCompbusiness basCompbusiness) ;
	
	
	public void deleteBusinessMessage(Long compId);


	public Map<String, Object> queryCompTypeAndAdressByCompId(String compId);
	
}