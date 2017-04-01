package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.vo.VO_ParParcelInfo;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.core.base.BaseDao;

public interface ParParceladdressMapper extends BaseDao{

	/**
	 *   增加包裹地址信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午6:18:21</dd>
	 * @param parceladdress
	 * @return
	 * @since v1.0
	 */
	int insertParceladdress(ParParceladdress parceladdress);
	
	/**
	 * 修改包裹地址信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午6:18:58</dd>
	 * @param parceladdress
	 * @return
	 * @since v1.0
	 */
	int updateParceladdress(ParParceladdress parceladdress);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新包裹地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午5:55:20</dd>
	 * @param parceladdress
	 * @return
	 * @since v1.0
	 */
	int updateParceladdressSelective(ParParceladdress parceladdress);
	
	/**
	 * 删除包裹地址信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午6:19:15</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	int deleteParceladdress(Long id);
	
	/**
	 * 查询包裹信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-6 下午12:59:40</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	ParParceladdress findParceladdress(Long id);

	void addbatchSaveParcelAddressInfo(List<VO_ParcelInfoAndAddressInfo> list);
	
	
	public List<Long> queryParcelIdsBySendUserId(Long memberId);
}