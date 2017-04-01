package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.RobInfoService;
import net.okdi.api.vo.VO_RobInfo;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抢单信息openapi接口
 * @author haifeng.he
 * @version V1.0
 */
@Controller
@RequestMapping("/robInfo")
public class RobInfoController extends BaseController{
	
	public static final Log logger = LogFactory.getLog(RobInfoController.class);
	@Autowired
	private RobInfoService robInfoService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午2:36:46</dd>
	 * @param compId											--站点id
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id （手机端必传）
	 * @param pagesize
	 * @param pageno
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
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.RobInfoController.queryRobInfo.001", "查询抢单信息时systemType不能为空"</dd>
     * <dd>"openapi.RobInfoController.queryRobInfo.002", "站点查询抢单信息时compId不能为空"</dd>
     * <dd>"openapi.RobInfoController.queryRobInfo.003", "快递哥查询抢单信息时memberId不能为空"</dd>				
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRobInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRobInfo(Long compId,Short systemType,Long memberId,Integer  pagesize,Integer  pageno){
		if(PubMethod.isEmpty(systemType)){
			return paramsFailure("openapi.RobInfoController.queryRobInfo.001", "查询抢单信息时systemType不能为空");
		}
		if(systemType == 1 && PubMethod.isEmpty(compId)){
			return paramsFailure("openapi.RobInfoController.queryRobInfo.002", "站点查询抢单信息时compId不能为空");
		}else if(systemType == 2  && PubMethod.isEmpty(memberId)){
			return paramsFailure("openapi.RobInfoController.queryRobInfo.003", "快递哥查询抢单信息时memberId不能为空");	
		}
		try {
			/**查询推送给本站点系统或者快递哥当前登陆人的抢单信息列表**/
			List<VO_RobInfo> list =  this.robInfoService.doQueryRobInfo(compId,systemType,memberId);
			if(!PubMethod.isEmpty(list)){
				if(systemType == 2){
					return jsonSuccess(list);//快递哥查询不需要分页
				}else if(systemType == 1){
					Page page = PageUtil.getPageData(pageno, pagesize, list);
					return jsonSuccess(page);//站点系统查询需要分页
				}
			}
			return jsonSuccess("");
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:08:53</dd>
	 * @param userId			--广播推送给人的id
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
		    "msg": "XXXX"
		}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.RobInfoController.robExpress.001", "报价抢单broadcastId不能为空"</dd>
     * <dd>"openapi.RobInfoController.robExpress.002", "报价抢单broadcastType不能为空"</dd>
     * <dd>"openapi.RobInfoController.robExpress.003", "报价抢单loginMemberId不能为空"</dd>	
     * <dd>"openapi.RobInfoController.robExpress.004", "报价抢单quotationAmt不能为空"</dd>	
     * <dd>"openapi.RobInfoController.robExpress.005", "报价抢单quotationType不能为空"</dd>	
     * <dd>"openapi.RobInfoController.robExpress.006", "报价抢单quotationId不能为空"</dd>	
     * <dd>"openapi.RobInfoController.robExpress.007", "报价抢单userId不能为空"</dd>	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/robExpress", method = { RequestMethod.POST, RequestMethod.GET })
	public String robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId,Long broadcastId,BigDecimal quotationAmt,Long takeMemberId,Short quotationType){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("openapi.RobInfoController.robExpress.001", "broadcastId不能为空");
		}
		if(PubMethod.isEmpty(broadcastType)){
			return paramsFailure("openapi.RobInfoController.robExpress.002", "broadcastType不能为空");
		}
		if(PubMethod.isEmpty(loginMemberId)){
			return paramsFailure("openapi.RobInfoController.robExpress.003", "loginMemberId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return paramsFailure("openapi.RobInfoController.robExpress.004", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationType)){
			return paramsFailure("openapi.RobInfoController.robExpress.005", "quotationType不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return paramsFailure("openapi.RobInfoController.robExpress.006", "quotationId不能为空");
		}
		if(PubMethod.isEmpty(userId)){
			return paramsFailure("openapi.RobInfoController.robExpress.007", "userId不能为空");
		}
		try {
			/**抢单之前需要再次校验指定的取件员是否为空，以及该份报价是否符合最低报价，防止恶意报价**/
			Short status = this.robInfoService.isRightConditions(quotationId,broadcastId,quotationAmt);
			if(status == 0){
				this.robInfoService.robExpress(userId,quotationId,broadcastType,loginMemberId,compId,broadcastId,quotationAmt,takeMemberId,quotationType);
				return jsonSuccess();
			}else{
				return jsonSuccess(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return
			{
			    "data": 0,												---0是符合抢单条件的  直接调用下一个抢单接口
			    "success": true
			}
						{
			    "data": 1,     											---1 代表已报价
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
			    "msg": "XXXX"
			}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.RobInfoController.isRightConditions.001", "robId不能为空"</dd>
     * <dd>"openapi.RobInfoController.isRightConditions.002", "quotationAmt不能为空"</dd>			
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/isRightConditions", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRightConditions(Long broadcastId,BigDecimal quotationAmt,Long quotationId){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("openapi.RobInfoController.isRightConditions.001", "robId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return paramsFailure("openapi.RobInfoController.isRightConditions.002", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return paramsFailure("openapi.RobInfoController.isRightConditions.003", "quotationId不能为空");
		}
		try {
			Short flag = this.robInfoService.isRightConditions(quotationId,broadcastId,quotationAmt);
			return jsonSuccess(flag);
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
				    "msg": "XXXX"
				}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.RobInfoController.getLowestPrice.001", "broadcastId不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getLowestPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String getLowestPrice(Long broadcastId){
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure("openapi.RobInfoController.getLowestPrice.001", "broadcastId不能为空");	
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
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单广播列表声音开关设置</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午5:47:20</dd>
	 * @param request
	 * @param flag 0 关闭 1 开启
	 * @param memberId 当前登录人员id
	 * @return
				{
				    "data": "",
				    "success": true
				}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/voiceSet", method = { RequestMethod.POST, RequestMethod.GET })
	public String voiceSet(Long memberId,Short flag){
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("openapi.RobInfoController.voiceSet.001", "flag不能为空");	
		}
		try {
			this.robInfoService.voiceSet(memberId,flag);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询站点当前登录人的声音开关设置信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午5:48:31</dd>
	 * @param memberId  当前登录人的memberId
	 * @return
			{
			    "data": {
			        "flag": 0, --0 开启 1 关闭
			        "memberId": 99999999999  -- 站点当前登录人的memberId
			    },
			    "success": true
			}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryVoiceSet", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryVoiceSet(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("openapi.RobInfoController.queryVoiceSet.001", "memberId不能为空");	
		}
		try {
			return jsonSuccess(this.robInfoService.queryVoiceSet(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/createRob", method = { RequestMethod.POST})
	public String createRob(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,short broadcastSource,
			String parcelsJsonStr,Long createUser,String appointTime){
		try {
			return jsonSuccess(this.robInfoService.createRob(senderName, senderMobile, senderAddressId, senderAddressName,
					senderDetailAddressName, senderLongitude, senderLatitude, parcelTotalCount, parcelTotalWeight, broadcastRemark,
					quotationAmount, broadcastSource, parcelsJsonStr, createUser,appointTime));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/cancelRob", method = { RequestMethod.POST})
	public String cancelRob(Long broadcastId,Long memberId){
		try {
			this.robInfoService.cancelRob(broadcastId,memberId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryRob", method = { RequestMethod.POST})
	public String queryRob(Long memberId,double longitude,double latitude){
		try {
			return jsonSuccess(this.robInfoService.queryRob(memberId, longitude, latitude));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryRobByBroadcastId", method = { RequestMethod.POST})
	public String queryRobByBroadcastId(Long broadcastId){
		try {
			return jsonSuccess(this.robInfoService.queryRobByBroadcastId(broadcastId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/rob", method = { RequestMethod.POST})
	public String rob(Long broadcastId,Long memberId){
		try {
			return jsonSuccess(this.robInfoService.rob(broadcastId, memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
