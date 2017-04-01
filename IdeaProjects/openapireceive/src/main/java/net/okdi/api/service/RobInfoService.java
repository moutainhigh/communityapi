package net.okdi.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.entity.VoiceSet;
import net.okdi.api.vo.VO_RobInfo;
import net.okdi.core.base.BaseService;

/**
 * 抢单信息openapi接口
 * @author haifeng.he
 * @version V1.0
 */
public interface RobInfoService extends BaseService<RobBroadcastInfo> {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:01:20</dd>
	 * @param compId											--站点id
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id （手机端必传）
	 * @return
				{
				    "data": [
				        {
				            "broadcastId": 125227047215104,		--抢单广播id
				            "broadcastRemark": "备注",			--抢单广播备注
				            "broadcastStatus": 2,				--抢单广播状态 1代表已报价 2代表待响应
				            "broadcastType": 1,					--抢单广播来源1:个人端2电商管家 
				            "createTime": 1421646050000,		--抢单广播创建时间(毫秒数)
				            "distance": 5198,					--距离
				            "loginMemberId": 2051401181502004,	--发布抢单广播的客户id
				            "parcelData": [						--包裹信息
				                {
				                    "parcelAddress": "北京市-海淀区-田村路43号",	--包裹收件地址
				                    "parcelAddressId": 123123123,				--包裹收件地址id
				                    "parcelId": 125227047215105,				--包裹id
				                    "parcelWeight": 0.5							--包裹重量
				                },
				                {
				                    "parcelAddress": "北京市-海淀区-田村路43号",
				                    "parcelAddressId": 123123123,
				                    "parcelId": 125227047215107,
				                    "parcelWeight": 0.5
				                }
				            ],
				            "quotation": 0,						--报价金额
				            "quotationId": 125227049312256,		--报价表id
				            "senderAddressId": 11000205,		--发件地址id
				            "senderAddressName": "北京市-海淀区", --发件地址
				            "senderDetailAddressName": "",		--发件详细地址
				            "takeMemberId": 118557300277248,	--指定的取件员id
				            "takeMemberName": "负责人",			--指定的取件员姓名
				            "totalCount": 5,					--包裹总数量
				            "totalWeight": 1					--包裹总重量
				        }
				    ],
				    "success": true
				}
	 * @since v1.0
	 */
	public List<VO_RobInfo> doQueryRobInfo(Long compId,Short systemType,Long memberId);
	
	public List<RobQuotationInfo> queryRobQuotationInfoByCompId(Long compId);
	public List<RobQuotationInfo> queryRobQuotationInfoByMemberId(Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>批量添加广播报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:12:29</dd>
	 * @param list
	 * @since v1.0
	 */
	public  void addbatchRobQuotationInfo(List<RobQuotationInfo> list);
	
	public List<Map<String,Object>> queryParcelInfo(Long robId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:15:26</dd>
	 * @param userId			--广播推送给人的id
	 * @param broadcastType		--广播来源 1:个人端 2:电商管家
	 * @param loginMemberId		--发布抢单广播的客户id(													
	 * @param compId			--站点id
	 * @param broadcastId		--广播id
	 * @param quotationAmt		--报价金额
	 * @param takeMemberId		--指定的取件员
	 * @param quotationType		--报价类型 1站点报价、2收派员报价
	 * @since v1.0
	 */
	public void robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId, Long broadcastId, BigDecimal quotationAmt, Long takeMemberId, Short quotationType);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单广播发件省到收件地址最低报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 下午1:44:27</dd>
	 * @return
	 * @since v1.0
	 */
	public Long getLowestPrice(Long broadcastId);//广播id
	
	public DicAddressaid queryDicAddressaid(String addressName);

	public Short isRightConditions(Long quotationId,Long broadcastId, BigDecimal quotationAmt);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过站点id获取站点实体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午1:55:51</dd>
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	public BasCompInfo queryBasCompInfo(Long compId);//
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过网络id获取网络实体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午1:56:21</dd>
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	public BasNetInfo queryBasNetInfo(Long netId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单广播列表声音开关设置</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午5:59:53</dd>
	 * @param flag 0 关闭 1 开启
	 * @param memberId 当前登录人员id
	 * @since v1.0
	 */
	public void voiceSet(Long memberId,Short flag);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询站点当前登录人的声音开关设置信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午6:00:33</dd>
	 * @param memberId  当前登录人的memberId
	 * @return
	 * @since v1.0
	 */
	public VoiceSet queryVoiceSet(Long memberId);
	
	
	public Map<String,Object> createRob(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,short broadcastSource,
			String parcelsJsonStr,Long createUser,String appointTime);
	public void cancelRob(Long broadcastId,Long memberId);
	public List<Map<String,Object>> queryRob(Long memberId,double longitude,double latitude) throws ParseException;
	public Map<String,Object> queryRobByBroadcastId(Long broadcastId);
	public Map<String,Object> rob(Long broadcastId,Long memberId)throws NumberFormatException, Exception;
}
