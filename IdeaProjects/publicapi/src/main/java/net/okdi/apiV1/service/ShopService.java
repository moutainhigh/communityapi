package net.okdi.apiV1.service;

import java.util.List;

/**
 * @author 作者 :xingwei.zhang
 * @version 创建时间：2016-11-22 下午6:21:40
 * 类说明
 */
public interface ShopService {
	/**
	 * 取派范围
	 * @return
	 */
	public String fetchRanges(String memberId,String fetchRangeData);
	/**
	 * 取派时间
	 * @param endTime 
	 * @param startTime 
	 * @return
	 */
	public String takeTime(String memberId,String startTime, String endTime);
	/**
	 * 取件价格
	 * @param fetchPrice 
	 * @param region 
	 * @param memberId 
	 * @param photoUrl 
	 * @param netName 
	 * @return
	 */
	public String pickupPrice(String memberId, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl);
	/**
	 * 服务说明
	 * @param serviceDescription 
	 * @param memberId 
	 * @return
	 */
	public String serviceExplain(String memberId, String serviceDescription);
	/**
	 * 查询店铺信息
	 * @param memberId
	 * @return
	 */
	public String queryShopInfo(String memberId);
	/**
	 * 查询取件价格
	 * @param memberId
	 * @return
	 */
	public String queryPickupPrice(String memberId);
	/**
	 * 服务快递
	 * @param memberId
	 * @return
	 */
	public String serviceExpress(String memberId, String serviceExpDate);
	/**
	 * 查询服务快递
	 * @param memberId
	 * @return
	 */
	public String queryServiceExpress(String memberId);
	/**
	 * 查询已填写
	 * @param memberId
	 * @return
	 */
	public String queryIsAdd(String memberId);
	/**
	 * 查询取件范围
	 * @param memberId
	 * @return
	 */
	public String queryFetchRanges(String memberId);
	/**
	 * 删除取件范围
	 * @param memberId
	 * @return
	 */
	public String deleteFetchRange(String Id);
	/**
	 * 更新取件价格
	 * @param id
	 * @param region
	 * @param fetchPrice
	 * @param netName
	 * @param photoUrl
	 * @return
	 */
	public String updateRegion(String Id, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl);
	/**
	 * 删除取件价格
	 * @param id
	 * @return
	 */
	public String deleteRegion(String Id);
	/**
	 * 好店分享
	 * @param memberId
	 * @return
	 */
	public String storeShare(String memberId);

}
