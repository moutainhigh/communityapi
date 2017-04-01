package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpPriceGroup;
import net.okdi.api.vo.VO_BasNetInfo;
import net.okdi.api.vo.VO_ExpressPrice;
import net.okdi.core.base.BaseService;

/**
 * 优惠组信息
 * @author shihe.zhai
 * @version V1.0
 */
public interface PriceGroupService extends BaseService<ExpPriceGroup> {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>新增优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午1:02:14</dd>
	 * @param expPriceGroup
	 * @since v1.0
	 */
	public ExpPriceGroup savePriceGroup(String groupName,Double discountPercentage,Long compId,Long netId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午1:03:45</dd>
	 * @param groupId 优惠组id 
	 * @param compId compId 网点ID
	 * @since v1.0
	 */
	public void deletePriceGroup(Long groupId,Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午1:04:12</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID 
	 * @param groupName 优惠组名称
	 * @param discountPercentage 打折百分比
	 * @since v1.0
	 */
	public void updatePriceGroup(Long groupId,Long compId,String groupName,Double discountPercentage);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取网点优惠组列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午11:49:47</dd>
	 * @param compId 网点id
	 * @return List<Map<String,Object>>
	 * @since v1.0
	 */
	public List<Map<String,Object>> getPriceGroupList(Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>是否存在重名优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午9:30:21</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param groupName 优惠组名称
	 * @return boolean true 存在  false 不存在
	 * @since v1.0
	 */
	public boolean isExistSameNamePriceGroup(Long groupId,Long compId,String groupName);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据ID获取优惠组详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午8:07:14</dd>
	 * @param groupId 优惠组ID
	 * @return ExpPriceGroup
	 * @since v1.0
	 */
	public ExpPriceGroup getPriceGroupById(Long groupId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据优惠组ID获取报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午9:35:05</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @return List
	 * @since v1.0
	 */
	public List<Map> getPriceListByGroupId(Long groupId,Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断是否存在网络公开报价优惠组价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午9:59:11</dd>
	 * @param compId 网点ID
	 * @return boolean true存在 false 不存在
	 * @since v1.0
	 */
	public boolean isExistNetDefaultPrice(Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd> 查询网络公开报价系想你</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:08:53</dd>
	 * @param compId 网点ID
	 * @return Map<String,Object>
	 * @since v1.0
	 */
	public Map<String,Object> getNetOpenPrice(Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:14:09</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @param firstFreight 首重价格
	 * @param firstWeight 首重重量
	 * @param continueInfo 续重信息
	 * @param citys 到达城市
	 * @since v1.0
	 */
	public void savePriceInfo(Long groupId,Long compId,Long netId,BigDecimal firstFreight,BigDecimal firstWeight,String continueInfo,String citys);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>导入网络公开报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:33:49</dd>
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @since v1.0
	 */
	public void importNetPrice(Long compId,Long netId);
}
