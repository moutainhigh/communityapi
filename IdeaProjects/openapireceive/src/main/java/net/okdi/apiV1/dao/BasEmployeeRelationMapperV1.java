package net.okdi.apiV1.dao;

import net.okdi.api.entity.BasEmployeeRelation;
import java.util.Map;
import org.apache.ibatis.annotations.Param;


public interface BasEmployeeRelationMapperV1 {

	void saveRelation(BasEmployeeRelation relation);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入公司于站長的关系表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public int insertBasEmployeeRelation(@Param("map")Map<String,Object>map);	
	
}