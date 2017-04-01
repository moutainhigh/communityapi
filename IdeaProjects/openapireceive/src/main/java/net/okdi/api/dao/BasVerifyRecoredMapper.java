package net.okdi.api.dao;


import net.okdi.core.base.BaseDao;


public interface BasVerifyRecoredMapper extends BaseDao {
	
	public int getSmsTotalOfExp(String mob);
	
}