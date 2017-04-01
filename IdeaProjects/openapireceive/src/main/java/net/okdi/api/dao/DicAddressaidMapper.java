package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.vo.VO_NetWork;
import net.okdi.core.base.BaseDao;

public interface DicAddressaidMapper extends BaseDao {

	public DicAddressaid selectByPrimaryKey(Long addressId);

	public List<Object> ifTwx(String cityId);

	public String getCityNameByProvince(String cityId);

	public String getCityNameBySzs(String cityId);

	public String getCityNameByCity(String cityId);

	public List<VO_NetWork> getAreaListByProvinceId(Map<String, Object> map);

	public List<VO_NetWork> getAreaListByCountyId(Map<String, Object> map);

	public List<VO_NetWork> getAreaListByCityId(Map<String, Object> map);

	/**
	 * 
	 * @Method: queryRelevantAddressList 
	 * @Description: 地址联想控件，查询5级地址下的6/7级地址
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:49:51
	 * @since jdk1.6
	 */
	public List<Map<String, Object>> queryRelevantAddressList(Map<String, Object> map);

	/**
	 * @Description 根据地址ID获取地址所在省份ID
	 * @author feng.wang
	 * @date 2014-11-6
	 * @since jdk1.6
	 */
	public Long getProvinceId(Long addressId);
	
	
	/**
	 * @Description 根据地址ID获取上级地址ID
	 * @author feng.wang
	 * @date 2014-11-6
	 * @since jdk1.6
	 */
	public Long getParentId(Long addressId);
	
	/**
	 * 
	 * @Description: 查询5级以上的地址信息
	 * @author feng.wang
	 * @date 2014-11-28 下午13:15:42
	 * @return
	 */
	public List<DicAddressaid> getAddressList();

	public DicAddressaid queryDicAddressaid(String addressName);

	public List<DicAddressaid> queryProvinceInfo();
}