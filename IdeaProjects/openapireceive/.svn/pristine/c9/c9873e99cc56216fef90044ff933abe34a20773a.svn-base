package net.okdi.apiV1.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.core.base.BaseDao;

public interface BasNetInfoMapperV1 extends BaseDao{
	/**
	 * 
	 * @Description: 查询网络信息(未删除 且 审核通过的)
	 * @author 翟士贺
	 * @date 2014-10-20上午9:11:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfo();
	/**
	 * 
	 * @Description: 查询网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfoAll();
	/**
	 * 
	 * @Description: 修改网络信息
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public int updateNetInfo(Map<String,Object> map);
	/**
	 * @Description: jingguoqiang
	 */
	public BasNetInfo findById(Long netId);
}