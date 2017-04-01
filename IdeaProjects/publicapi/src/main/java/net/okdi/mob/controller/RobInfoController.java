package net.okdi.mob.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.RobInfoService;

/**
 * 抢单报价的相关接口 
 * @author haifeng.he
 * @version V1.0
 */
@Controller
@RequestMapping("/robInfo")
public class RobInfoController extends BaseController{

	@Autowired
	private RobInfoService robInfoService;
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:05:01</dd>
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id 
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
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"publicapi.RobInfoController.queryRobInfo.001", "查询抢单信息时systemType不能为空"</dd>
     * <dd>"publicapi.RobInfoController.queryRobInfo.002", "快递哥查询抢单信息时memberId不能为空"</dd>	
     * <dd>"publicapi.RobInfoHttpClient.queryRobInfo.001", "查询抢单信息时systemType不能为空"</dd>
     * <dd>"publicapi.RobInfoHttpClient.queryRobInfo.002", "快递哥查询抢单信息时memberId不能为空"</dd>	
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRobInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRobInfo(Short systemType,Long memberId){
		if(PubMethod.isEmpty(systemType)){
			return paramsFailure("publicapi.RobInfoController.queryRobInfo.001", "快递哥查询抢单信息时systemType不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("publicapi.RobInfoController.queryRobInfo.002", "快递哥查询抢单信息时memberId不能为空");
		}
		try {
			return this.robInfoService.doQueryRobInfo(systemType,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单之前校验该条广播记录的状态以及报价是否符合条件<</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:35:59</dd>
	 * @param broadcastId  --广播id
	 * @param quotationAmt --报价金额
	 * @param quotationId --报价表的id
	 * @return
			{
			    "data": 0,												---0是符合抢单条件的  直接调用下一个抢单接口
			    "success": true
			}
						{
			    "data": 1,     											---1 您已报价
			    "success": true
			}
			{
			    "data": 3,     											---3 代表输入的价格低于后台系统的最低报价
			    "success": true
			}
			{
			    "data": 2,     											---2 代表该广播已经取消
			    "success": true
			}
			{
			    "data": 4,     											---4 代表该广播已被抢
			    "success": true
			}
			{
			    "data": 5,     											---5 代表该广播已超时
			    "success": true
			}
			
			失败：
			{
			    "success": false,
			    "errCode": "err.001",
			    "msg": "XXX"
			}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"publicapi.RobInfoController.isRightConditions.001", "broadcastId不能为空"</dd>
     * <dd>"publicapi.RobInfoController.isRightConditions.002", "quotationAmt不能为空"</dd>	
     * <dd>"publicapi.RobInfoController.isRightConditions.003", "quotationId不能为空"</dd>	
     * <dd>"publicapi.RobInfoHttpClient.isRightConditions.001", "broadcastId不能为空"</dd>
     * <dd>"publicapi.RobInfoHttpClient.isRightConditions.002", "quotationAmt不能为空"</dd>	
     * <dd>"publicapi.RobInfoHttpClient.isRightConditions.003", "quotationId不能为空"</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/isRightConditions", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRightConditions(Long broadcastId,BigDecimal quotationAmt,Long quotationId){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("publicapi.RobInfoController.isRightConditions.001", "broadcastId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return paramsFailure("publicapi.RobInfoController.isRightConditions.002", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return paramsFailure("publicapi.RobInfoController.isRightConditions.003", "quotationId不能为空");
		}
		try {
			return this.robInfoService.isRightConditions(broadcastId,quotationAmt,quotationId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据广播id计算最低报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午6:23:46</dd>
	 * @param broadcastId  --广播id
	 * @return
				{
				    "data": 11,
				    "success": true
				}
				失败：
				{
				    "success": false,
				    "errCode": "err.001",
				    "msg": "XXX"
				}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"publicapi.RobInfoController.getLowestPrice.001", "broadcastId不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getLowestPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String getLowestPrice(Long broadcastId){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("publicapi.RobInfoController.getLowestPrice.001", "broadcastId不能为空");
		}
		try {
			return jsonSuccess(this.robInfoService.getLowestPrice(broadcastId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:08:53</dd>
	 * @param userId			--广播推送给的人员id
	 * @param broadcastType		--广播来源 1:个人端 2:电商管家
	 * @param loginMemberId		--发布抢单广播的客户id(													
	 * @param compId			--站点id
	 * @param broadcastId		--广播id
	 * @param quotationAmt		--报价金额
	 * @param takeMemberId		--指定的取件员
	 * @param quotationType		--报价类型 1站点报价、2收派员报价
	 * @return
		{
		      											
		    "success": true
		}
		失败：
		{
		    "success": false,
		    "errCode": "err.001",
		    "msg": "XXX"
		}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"publiapi.RobInfoController.robExpress.001", "报价抢单broadcastId不能为空"</dd>
     * <dd>"publiapi.RobInfoController.robExpress.002", "报价抢单broadcastType不能为空"</dd>
     * <dd>"publiapi.RobInfoController.robExpress.003", "报价抢单loginMemberId不能为空"</dd>	
     * <dd>"publiapi.RobInfoController.robExpress.004", "报价抢单quotationAmt不能为空"</dd>	
     * <dd>"publiapi.RobInfoController.robExpress.005", "报价抢单quotationType不能为空"</dd>	
     * <dd>"publiapi.RobInfoController.robExpress.006", "报价抢单quotationId不能为空"</dd>
     * <dd>"publiapi.RobInfoController.robExpress.007", "报价抢单userId不能为空"</dd>	
     * <dd>"publiapi.RobInfoHttpClient.robExpress.001", "报价抢单broadcastId不能为空"</dd>
     * <dd>"publiapi.RobInfoHttpClient.robExpress.002", "报价抢单broadcastType不能为空"</dd>
     * <dd>"publiapi.RobInfoHttpClient.robExpress.003", "报价抢单loginMemberId不能为空"</dd>	
     * <dd>"publiapi.RobInfoHttpClient.robExpress.004", "报价抢单quotationAmt不能为空"</dd>	
     * <dd>"publiapi.RobInfoHttpClient.robExpress.005", "报价抢单quotationType不能为空"</dd>	
     * <dd>"publiapi.RobInfoHttpClient.robExpress.006", "报价抢单quotationId不能为空"</dd>
     * <dd>"publiapi.RobInfoHttpClient.robExpress.007", "报价抢单userId不能为空"</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/robExpress", method = { RequestMethod.POST, RequestMethod.GET })
	public String robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId,Long broadcastId,BigDecimal quotationAmt,Long takeMemberId,Short quotationType){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("publiapi.RobInfoController.robExpress.001", "broadcastId不能为空");
		}
		if(PubMethod.isEmpty(broadcastType)){
			return paramsFailure("publiapi.RobInfoController.robExpress.002", "broadcastType不能为空");
		}
		if(PubMethod.isEmpty(loginMemberId)){
			return paramsFailure("publiapi.RobInfoController.robExpress.003", "loginMemberId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return paramsFailure("publiapi.RobInfoController.robExpress.004", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationType)){
			return paramsFailure("publiapi.RobInfoController.robExpress.005", "quotationType不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return paramsFailure("publiapi.RobInfoController.robExpress.006", "quotationId不能为空");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("publiapi.RobInfoController.robExpress.007", "userId不能为空");
		}
		try {
			return this.robInfoService.robExpress(userId,quotationId,broadcastType,loginMemberId,compId,broadcastId,quotationAmt,takeMemberId,quotationType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/createRobAmssy", method = { RequestMethod.POST})
	public String createRobAmssy(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,
			String parcelsJsonStr,String appointTime){
		try {
			return this.robInfoService.createRobAmssy(senderName, senderMobile, senderAddressId, senderAddressName,
					senderDetailAddressName, parcelTotalCount, parcelTotalWeight, broadcastRemark,
					quotationAmount, parcelsJsonStr,appointTime);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/cancelRobAmssy", method = { RequestMethod.POST})
	public String cancelRobAmssy(Long broadcastId){
		try {
			return this.robInfoService.cancelRobAmssy(broadcastId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryRob", method = { RequestMethod.POST})
	public String queryRob(Long memberId,double longitude,double latitude){
		try {
			return this.robInfoService.queryRob(memberId, longitude, latitude);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryRobByBroadcastId", method = { RequestMethod.POST})
	public String queryRobByBroadcastId(Long broadcastId){
		try {
			return this.robInfoService.queryRobByBroadcastId(broadcastId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/rob", method = { RequestMethod.POST})
	public String rob(Long broadcastId,Long memberId){
		try {
			return this.robInfoService.rob(broadcastId, memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
