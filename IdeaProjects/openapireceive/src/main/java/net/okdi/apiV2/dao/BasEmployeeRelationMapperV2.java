package net.okdi.apiV2.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface BasEmployeeRelationMapperV2 {

	public int insertBasEmployeeRelation(@Param("map")Map<String,Object>map);
}