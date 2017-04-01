package net.okdi.api.dao;

import java.util.Map;

import net.okdi.core.base.BaseDao;

/**
 * 
 * @ClassName BasCompbusinessMapper
 * @Description 网点经营信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface BasCompbusinessMapper extends BaseDao{
	/**
	 * 
	 * @Method: updateTelephone 
	 * @Description: 更新负责人电话
	 * @param paras
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:44:07
	 * @since jdk1.6
	 */
	public void updateTelephone(Map<String, Object> paras);
}