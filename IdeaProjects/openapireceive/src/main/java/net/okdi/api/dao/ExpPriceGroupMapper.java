package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpPriceGroup;
import net.okdi.core.base.BaseDao;

public interface ExpPriceGroupMapper extends BaseDao{
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午11:46:53</dd>
	 * @param paras groupId优惠组id  compId 网点ID
	 * @since v1.0
	 */
	public void deletePriceGroup(Map<String, Object> paras);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午11:48:40</dd>
	 * @param paras groupId优惠组ID compId网点ID  groupName优惠组名称  discountPercentage 打折百分比
	 * @since v1.0
	 */
	public void updatePriceGroup(Map<String, Object> paras);
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
	 * <dt><span class="strong">方法描述:</span></dt><dd>优惠组重名验证</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午7:06:28</dd>
	 * @param paras
	 * @return
	 * @since v1.0
	 */
	public List<Map<String,Object>> getSameNamePriceGroup(Map<String, Object> paras);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取默认组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午12:50:18</dd>
	 * @return
	 * @since v1.0
	 */
	public ExpPriceGroup getDefaultPriceGroup();
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新优惠组折扣信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午7:09:31</dd>
	 * @param paras
	 * @since v1.0
	 */
	public void updateDiscountPercentage(Map<String, Object> paras);
}