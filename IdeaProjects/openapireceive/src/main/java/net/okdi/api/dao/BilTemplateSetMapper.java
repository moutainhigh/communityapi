package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.okdi.api.entity.BilTemplateSet;
import net.okdi.core.base.BaseDao;
      
public interface BilTemplateSetMapper extends BaseDao{

	public int updateByPrimaryKeySelective(BilTemplateSet bl);

	public List<BilTemplateSet> list(Map<String, Object> paras);

	public int deleteByMap(Map<String, Object> paras);

}
