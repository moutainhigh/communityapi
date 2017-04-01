/**  
 * @Project: openapi
 * @Title: BroadcastService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-1-14 上午11:15:21
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.util.List;

import net.okdi.api.entity.RobParcelRelation;
import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.vo.VO_BroadcastInfo;

/**
 * @author amssy
 * @version V1.0
 */
public interface BroadcastService {
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>第三方发布广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:34:20</dd>
	 * @param jsonString
	 * @return
	 * @since v1.0
	 */
	public String addBroadcast(String jsonString,Long memberId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>第三方发布广播运营数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:34:20</dd>
	 * @param jsonString
	 * @return
	 * @since v1.0
	 */
	public String addBroadcastOperation(String jsonString,Long memberId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>第三方发布广播运营数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:34:20</dd>
	 * @param jsonString
	 * @return
	 * @since v1.0
	 */
	public String addBroadcastOwnOperation(String jsonString);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:24:26</dd>
	 * @param broadcastInfo
	 * @param list
	 * @return
	 * @since v1.0
	 */
	public String addBroadcastBase(VO_BroadcastInfo broadcastInfo,List<RobParcelRelation>list);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加广播包裹关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:33:29</dd>
	 * @param list
	 * @return
	 * @since v1.0
	 */
	public String addBroadcastParcelRelation(List<RobParcelRelation>list);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>个人端创建广播</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午09:35:15</dd>
     * @param jsonString
     * @param memberId
     * @return
     * @since v1.0
     */
	public String addBroadcastOwn(String jsonString);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询周边站点和收派员并发起推送返回抢单列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:33:39</dd>
	 * @param lng
	 * @param lat
	 * @param broadcastId
	 * @param sendAddressName
	 * @return
	 * @since v1.0
	 */
    public List<RobQuotationInfo> getNearCompAndCourier(Double lng,Double lat,Long broadcastId,String sendAddressName);

    
}
