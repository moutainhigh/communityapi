package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.DicAddressaid;
import net.okdi.core.base.BaseService;
/**
 * 
 * @ClassName AddressService
 * @Description TODO
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface AddressService extends BaseService<DicAddressaid> {
	/**
	 * 
	 * @Method: queryRelevantAddressList 
	 * @Description: 联想地址六七级地址
	 * @param townId 乡镇（五级）地址ID
	 * @param keyword 地址关键字
	 * @param count 显示数量
	 * @return Json
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:24:21
	 * @since jdk1.6
	 */
	public String queryRelevantAddressList(Long townId,String keyword,Integer count);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询快递网络某级地址下覆盖超区地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-20 上午11:50:20</dd>
	 * @param netId 快递网络ID，必填
	 * @param addressId 上级地址ID
	 * @return Map<String,List<Map>>
	 * @since v1.0
	 */
	public Map<String,List<Map>> getCoverExceedAddress(Long netId,Long addressId);
	
	
	
}
