package net.okdi.apiV2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasCompimage;
import net.okdi.core.base.BaseDao;

public interface BasCompimageMapperV2 extends BaseDao{

	
	public void saveCompImageBatch(List<BasCompimage> compimageList);
	
	public void deleteImageByCompId(@Param("compId")Long compId);

	public List<HashMap<String, Object>> querySiteImage(String memberId);
}