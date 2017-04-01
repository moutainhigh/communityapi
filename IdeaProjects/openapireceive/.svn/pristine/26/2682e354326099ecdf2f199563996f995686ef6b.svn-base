package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.ParcelSupplementService;
import net.okdi.api.vo.VO_ParcelList;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 包裹信息
 * @author xianxian.chang
 * @version V1.0
 */
@Controller
@RequestMapping("/parcelInfo")
public class ParcelInfoController extends BaseController {

	@Autowired
	ParcelInfoService parcelInfoService;
	@Autowired
	ParcelSupplementService parcelSupplementService;
	
	
	/**
	 * 根据运单号与快递网络id查询包裹详情
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午3:23:47</dd>
	 * @param wayBillNum 运单号
	 * @param netId　快递网络id
	 * @return
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParcelInfoController.findParcelDetailByWaybillNumAndNetId.001 - 运单号不能为空</dd>
     * <dd>openapi.ParcelInfoController.findParcelDetailByWaybillNumAndNetId.002 - 网络号不能为空</dd>
{
    "data": {
        "actualCodAmount": "1.00",       代收货款实际收到的货款金额
        "actualSendMember": "3435096",       实际派件人ID
        "actualTakeMember": "3373043",       实际取件人ID
        "addresseeAddress": "",       收件详细地址
        "addresseeAddressId": "",        收件乡镇ID
        "addresseeCasUserId": "",       收件人CASID
        "addresseeContactId": "",       收件联系人ID
        "addresseeCustomerId": "",       收件客户ID
        "addresseeLatitude": "",       收件人纬度
        "addresseeLongitude": "",       收件人经度
        "addresseeMobile": "",       收件人手机
        "addresseeName": "",       收件人姓名
        "addresseePhone": "",       收件人电话
        "addresseeZipcode": "",       收件地址邮编
        "agencySiteId": "",       包裹代收点ID
        "chareWeightForsender": "1.00",       计费重量（销售）
        "chareWeightFortransit": "1.00",       计费重量（采购）
        "codAmount": "1.00",       代收货款金额
        "codIsRecovery": "1",       代收货款是否收回 0：代收货款未收回 1：代收货款已收回
        "compId": "5981",       所属站点(所属公司)
        "compName": "广西北海公司",       公司名称（站点名称）
        "createTime": "2014-12-17 16:44:47",       包裹录入时间
        "createUserId": "3435096",       包裹创建人
        "expWaybillNum": "sadf001",       包裹运单号
        "freight": "1.00",       应收运费
        "freightPaymentMethod": "1",     应收运费支付方式 0：发件方现结,1：发件方月节,2：收件方到付  
        "freightPaymentStatus": "1",       费用结算状态 1：yes 运费已收 0:no 运费未收
        "freightPaymentTime": "2014-12-20 16:44:20",       /费用支付时间 费用支付时间；取件时以收派员完成取件时间，派件时以派件签收时间；
        "goodsDesc": "",      产品描述 
        "goodsNum": "1",       产品个数
        "goodsPaymentMethod": "1",    货款支付方式 0:不代收付货款,1:上门代收付(COD)   
        "goodsPaymentStatus": "1",       货款与发件人结算状态  1：yes 货款已结给发件人 0:no 货款未付给发件人
        "id": "10001",       包裹id或包裹地址信息id
        "insureAmount": "1.00",       保价金额
        "netId": "999",      快递网络id 
        "netName": "EMS速递",       快递网络名称
        "noFly": "1",       禁航件 1：yes 禁航 0:no 非禁航
        "ordOfSellerId": "",       发货商订单号
        "packingCharges": "1.00000",       包装费
        "parcelEndMark": "1",       包裹结束标志 0：未结束 1：结束
        "parcelRemark": "1",       包裹备注
        "parcelType": "1",       包裹类型，1：包裹，2：文件
        "parcelVolume": "1.00",       包裹初始体积
        "paymentMethod": "1",       费用付款方式 0：现金,1：POS机
        "pricePremium": "1.00000",       保价费
        "printFlag": "1",       打印标记 0：未打印，1：已打印
        "receiptId": "1",       付款收据ID
        "sendAddress": "",     发件详细地址
        "sendAddressId": "",     发件乡镇ID  
        "sendCasUserId": "",     发件人CASID  
        "sendContactId": "",       发件联系人ID
        "sendCustomerId": "",       发件客户ID
        "sendLatitude": "",       发件人纬度
        "sendLongitude": "",    发件人经度   
        "sendMobile": "15898785451",      发件人手机 
        "sendName": "ss",       发件人姓名
        "sendPhone": "010-99999999",       发件人电话
        "sendTaskId": "117095508287488",       派件任务ID
        "sendZipcode": "",       发件地址邮编
        "senderCompId": "5980",     发货方商家ID  
        "senderType": "1",       发件人类型 1:发货商家,2:好递个人
        "senderUserId": "3435096",       
        "serviceId": "2005",       快递公司服务产品ID 快递产品ID，结算用
        "serviceName": "当日达",       服务产品名称
        "signGoodsTotal": "1.00",       签收金额
        "signImgUrl": "1",       上传的图片路径
        "signMember": "1",       签收人
        "signResult": "1",       签收结果 0：未签收/拒签 1：签收
        "signTime": "2014-12-09 16:44:32",     签收时间  
        "tackingStatus": "1",       包裹当前状态 0:在途,未签收 1:已签收
        "takeTaskId": "117095539744768",       取件任务ID
        "tipAmount": "1.00",       抢单发快递的小费
        "totalGoodsAmount": "1.00"       商品货款合计
        "parcelStatus": "0"       包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
    },
    "success": true
}
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/findParcelDetailByWaybillNumAndNetId", method = { RequestMethod.POST })
	public String findParcelDetailByWaybillNumAndNetId(String wayBillNum, Long netId) {
		if(PubMethod.isEmpty(wayBillNum)){
			return paramsFailure("openapi.ParcelInfoController.findParcelDetailByWaybillNumAndNetId.001","运单号为必填项");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("openapi.ParcelInfoController.findParcelDetailByWaybillNumAndNetId.002","网络号为必填项");
		}
		try {
			Map<Object, Object> map = this.parcelInfoService.findParcelDetailByWaybillNumAndNetId(wayBillNum, netId);
			return jsonSuccess(map);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 保存包裹信息 (包裹id为空进行保存操作，否则进行更新操作)
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午3:29:33</dd>
	 * @param id 包裹id
	 * @param takeTaskId 取件任务id
	 * @param sendTaskId 派件任务id
	 * @param senderUserId 发货方客户ID 
	 * @param sendCustomerId 发件客户ID (与senderUserId是同一值，前者存在于包裹表里，后者存在于包裹地址信息表里)
	 * @param addresseeCustomerId  收件客户ID
	 * @param sendCasUserId  发件人CASID
	 * @param addresseeCasUserId 收件人人CASID
	 * @param expWaybillNum 运单号
	 * @param compId 公司id
	 * @param netId  网络id
	 * @param addresseeName 收件人姓名
	 * @param addresseeMobile 收件人手机号码
	 * @param addresseeAddressId 收件人乡镇id
	 * @param addresseeAddress 收件人详细地址
	 * @param sendName 发件人姓名
	 * @param sendMobile 发件人手机
	 * @param sendAddressId 发件人乡镇id
	 * @param sendAddress 发件人详细地址
	 * @param chareWeightForsender 包裹重量 
	 * @param freight 包裹应收运费
	 * @param goodsPaymentMethod 支付方式
	 * @param codAmount 代收货款金额
	 * @param insureAmount 保价金额
	 * @param pricePremium 保价费
	 * @param packingCharges 包装费
	 * @param freightPaymentMethod 运费支付方式
	 * @param sendLongitude 发件人地址经度
	 * @param sendLatitude 发件人地址纬度
	 * @param addresseeLongitude 收件人地址经度
	 * @param addresseeLatitude 收件人地址纬度
	 * @param goodsDesc 产品描述
	 * @param parcelRemark 包裹备注
	 * @param serviceId 服务产品ID
	 * @param signMember 签收人
	 * @param createUserId 创建人id
	 * @param parcelEndMark 包裹结束标志
	 * @param actualTakeMember 收件人id
	 * @param actualSendMember 派件人id
	 * @param receiptId 收据id	
	 * @param parcelStatus;//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
	 * @return 
		 {"success": true, "data":1}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST })	
	public String saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId,
			Long sendCustomerId, Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId,
			String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, 
			Long addresseeAddressId, String addresseeAddress, String sendName, String sendMobile, 
			Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod, 
			BigDecimal codAmount, BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges,
			Short freightPaymentMethod, BigDecimal sendLongitude, BigDecimal sendLatitude, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark, Long serviceId, String signMember,
			Long createUserId, String parcelEndMark, Long actualTakeMember, Long actualSendMember, Long receiptId,
			Short parcelStatus,BigDecimal actualCodAmount, String disposalDesc
			){
		
		try {
			return jsonSuccess(
					this.parcelInfoService.saveParcelInfo(
							id, takeTaskId, sendTaskId, senderUserId,
							sendCustomerId, addresseeCustomerId, sendCasUserId, addresseeCasUserId,
							expWaybillNum, compId, netId, addresseeName, addresseeMobile, 
							addresseeAddressId, addresseeAddress, sendName, sendMobile, 
							sendAddressId, sendAddress, chareWeightForsender, freight, goodsPaymentMethod, 
							codAmount, insureAmount, pricePremium, packingCharges,
							freightPaymentMethod, sendLongitude, sendLatitude, addresseeLongitude,
							addresseeLatitude, goodsDesc, parcelRemark, serviceId, signMember,
							createUserId, parcelEndMark, actualTakeMember, actualSendMember, receiptId,
							parcelStatus,actualCodAmount,  disposalDesc
														)
							);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id删除对应包裹内容信息与包裹地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午7:29:17</dd>
	 * @param id 包裹id
	 * @return {"success": true/false} true 操作成功 false 操作异常
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.001 -参数id不能为空！</dd>
	 *<dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.002 -参数expWayBillNum不能为空！</dd>
	 *<dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.003 -参数netId不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.001 -包裹id不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.002 -包裹expWayBillNum不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.003 -包裹netId不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.004 -通过id查询包裹内容信息异常！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.005 -参数不匹配！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.006 -删除包裹内容信息异常！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.007 -删除包裹地址信息异常！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteParcelInfoByParcelId", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteParcelInfoByParcelId(Long id,String expWayBillNum,Long netId){
		if(PubMethod.isEmpty(id)){
			return paramsFailure("openapi.ParcelInfoController.deleteParcelInfoByParcelId.001","参数id不能为空");
		}
		if(PubMethod.isEmpty(expWayBillNum)){
			return paramsFailure("openapi.ParcelInfoController.deleteParcelInfoByParcelId.002","参数expWayBillNum不能为空");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("openapi.ParcelInfoController.deleteParcelInfoByParcelId.003","参数netId不能为空");
		}
		try {
			this.parcelInfoService.deleteParcelInfoByParcelId(id,expWayBillNum,netId);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午6:16:12</dd>
	 * @param expWayBillNum 包裹运单号 
	 * @param netId 网络id
	 * @return {"parcelId":118025415647232,"success":true} 存在包裹    {"success":false}  不存在包裹 
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelInfoByExpWayBillNumAndNetId.001 -运单号expWayBillNum不能为空！</dd>
	 *<dd>openapi.ParcelInfoController.queryParcelInfoByExpWayBillNumAndNetId.002 -网络netId不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.001 -网络netId不能为空！</dd>
	 *<dd>openapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.002 -运单号expWayBillNum不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryParcelInfoByExpWayBillNumAndNetId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelInfoByExpWayBillNumAndNetId(Long netId,String expWayBillNum){
		if(PubMethod.isEmpty(expWayBillNum)){
			return paramsFailure("openapi.ParcelInfoController.queryParcelInfoByExpWayBillNumAndNetId.001", "运单号expWayBillNum不能为空");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("openapi.ParcelInfoController.queryParcelInfoByExpWayBillNumAndNetId.002", "网络netId不能为空");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Long parcelId = this.parcelInfoService.queryParcelInfoByExpWayBillNumAndNetId(netId,expWayBillNum);
			if(PubMethod.isEmpty(parcelId)){
				map.put("success", false);
				return JSON.toJSONString(map);
			}else{
				map.put("success", true);
				map.put("parcelId", parcelId);
				return JSON.toJSONString(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 新快递 第一个接口：--jinggq
	 * 根据登录人ID查询 待派包裹列表
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员ID
	 * @return
	 * {
	    "data": [
	        {
	            "createTime": 1418177768000, 包裹创建时间
	            "totalGoodsAmount": 20, 应收货款
	            "freight": 2, 应收运费
	            "parcelId": 118329761382400, 包裹id
	            "sendAddress": "北京市北京市区城区 海淀区玉泉路", 派件地址
	            "sendAddressId": 310167157, 派件地址id
	            "sendMobile": "18601124718", 派件人手机号
	            "sendName": "刘稥苇" 派件人姓名
	        }
	    ],
	    "success": true
	}
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryParcelListBySendMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("openapi.ParcelInfoController.queryParcelListBySendMemberId.001", "派件人memberId不能为空!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("openapi.ParcelInfoController.queryParcelListBySendMemberId.002", "currentPage不能为空!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("openapi.ParcelInfoController.queryParcelListBySendMemberId.003", "pageSize不能为空!");
		}
		try {
			Long count = this.parcelInfoService.getParcelListBySendMemberIdCount(memberId);  //1。查询数量--jinggq
			return this.jsonSuccess(this.parcelInfoService.queryParcelListBySendMemberId(memberId,currentPage,pageSize), count);//组装详情和数量  --jinggq
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务(只查询该收派员一天的取件任务)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryTakeTaskList", method = { RequestMethod.POST })
	public String  queryTakeTaskList(Long actualTakeMember){
		if(PubMethod.isEmpty(actualTakeMember)){
			return paramsFailure("openapi.ParcelInfoController.queryTakeTaskList.002", "取件人actualTakeMember不能为空");
		}
		try {
			return jsonSuccess(this.parcelInfoService.queryTakeTaskList(actualTakeMember));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个单号的包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @param receiptId 运单Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryTakeByWaybillNum", method = { RequestMethod.POST })
	public String  queryTakeByWaybillNum(Long actualTakeMember,Long receiptId){
		if(PubMethod.isEmpty(actualTakeMember)){
			return paramsFailure("openapi.ParcelInfoController.queryTakeByWaybillNum.002", "取件人actualTakeMember不能为空");
		}
		try {
			return jsonSuccess(this.parcelInfoService.queryTakeByWaybillNum(actualTakeMember,receiptId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>更改收件人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param parceId 包裹Id
	 * @param TaskId 任务Id
	 * @param AddressId 收件人地址Id
	 * @param takePersonName 收件人姓名
	 * @param takePersonMoble 收件人电话
	 * @param takePersonAddress 收件人地址
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/modyfyTaskInfo", method = { RequestMethod.POST })
	 public String modyfyTaskInfo(Long memberId, Long parceId,Long TaskId,Long AddressId, String takePersonName
			 					  , String takePersonMoble,String takePersonAddress) {
			try {
				if(PubMethod.isEmpty(memberId)){
					return paramsFailure("openapi.ParcelInfoController.modyfyTaskInfo.002", "取件人memberId不能为空");
				}
				if(PubMethod.isEmpty(parceId)){
					return paramsFailure("openapi.ParcelInfoController.modyfyTaskInfo.003", "parceId不能为空");
				}
				if(PubMethod.isEmpty(TaskId)){
					return paramsFailure("openapi.ParcelInfoController.modyfyTaskInfo.004", "TaskId不能为空");
				}
				parcelInfoService.modyfyTaskInfo(memberId, parceId, TaskId, AddressId, takePersonName, takePersonMoble, takePersonAddress);
				return jsonSuccess();
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
	 }
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询该取件任务下的包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param takeTaskId 取件任务Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryTakeTaskParcel", method = { RequestMethod.POST })
		public String queryTakeTaskParcel(Long takeTaskId){
		try {
			if(PubMethod.isEmpty(takeTaskId)){
				return paramsFailure("openapi.ParcelInfoController.querytaketaskbyparcel.003", "取件任务takeTaskId不能为空");
			}
			Map<String,Object> map =  parcelInfoService.queryTakeTaskParcel(takeTaskId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除一个取件任务下,没有运单号的包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param takeTaskId 取件任务Id
	 * @param netId 网络Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.deleteParcelByTakeTaskId.001 -takeTaskId 取件任务Id,不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/deleteParcelByTakeTaskId", method = { RequestMethod.POST })
	public String deleteParcelByTakeTaskId(Long takeTaskId , Long netId){
		try {
			if(PubMethod.isEmpty(takeTaskId)){
				return paramsFailure("openapi.ParcelInfoController.deleteParcelByTakeTaskId.001", "取件任务takeTaskId不能为空");
			}
			if(PubMethod.isEmpty(takeTaskId)){
				return paramsFailure("openapi.ParcelInfoController.deleteParcelByTakeTaskId.001", "取件任务takeTaskId不能为空");
			}
			parcelInfoService.deleteParcelByTakeTaskId(takeTaskId,netId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>解除取件任务和包裹的关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param parcelIds 包裹Id多个用,分隔
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/delTakeTaskRelationParcel", method = { RequestMethod.POST })
	public String delTakeTaskRelationParcel(String parcelIds,Long takeId){
		try {
			if(PubMethod.isEmpty(parcelIds)){
				return paramsFailure("openapi.ParcelInfoController.delTakeTaskRelationParcel.002", "parcelIds为null");
			}
			if(PubMethod.isEmpty(takeId)){
				return paramsFailure("openapi.ParcelInfoController.delTakeTaskRelationParcel.003", "takeId为null");
			}
			parcelInfoService.delTakeTaskRelationParcel(parcelIds,takeId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/querySendParcelList", method = { RequestMethod.POST })
	public String querySendParcelList(Long memberId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.ParcelInfoController.querySendParcelList.001", "登录人id为null");
			}
			return jsonSuccess(parcelInfoService.querySendParcelList(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断包裹状态</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param takeTaskId 取件任务Id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryByTakeTaskStatus", method = { RequestMethod.POST })
	public String queryByTakeTaskStatus(Long takeTaskId){
		try {
			if(PubMethod.isEmpty(takeTaskId)){
				return paramsFailure("openapi.ParcelInfoController.queryByTakeTaskStatus.001", "takeTaskId为null");
			}
			return JSON.toJSONString(parcelInfoService.queryByTakeTaskStatus(takeTaskId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 下午6:05:04</dd>
	 * @param createTime
	 * @param expWaybillNum
	 * @param memberId
	 * @param customerName
	 * @param senderPhone
	 * @param parcelStatus
	 * @param compId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryParcelList", method = { RequestMethod.POST })
	public String queryParcelList(String createTime, String expWaybillNum, Long memberId, String customerName, String senderPhone, Long parcelStatus, Long compId, Long departId, Integer currentPage, Integer pageSize){
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		try {
			return jsonSuccess(parcelSupplementService.queryParcelList(createTime, expWaybillNum, memberId, customerName, senderPhone, parcelStatus, compId, null, page));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-30 下午3:14:02</dd>
	 * @param compId
	 * @param parcelId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/queryParcelDetail", method = { RequestMethod.POST })
	public String queryParcelDetail(Long compId, Long parcelId, String status){
		try {
			return jsonSuccess(parcelSupplementService.queryParcelDetail(compId, parcelId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加或更新包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-30 下午3:25:08</dd>
	 * @param id
	 * @param takeTaskId
	 * @param sendTaskId
	 * @param senderUserId
	 * @param sendCustomerId
	 * @param addresseeCustomerId
	 * @param sendCasUserId
	 * @param addresseeCasUserId
	 * @param expWaybillNum
	 * @param compId
	 * @param netId
	 * @param addresseeName
	 * @param addresseeMobile
	 * @param addresseeAddressId
	 * @param addresseeAddress
	 * @param sendName
	 * @param sendMobile
	 * @param sendAddressId
	 * @param sendAddress
	 * @param chareWeightForsender
	 * @param freight
	 * @param goodsPaymentMethod
	 * @param codAmount
	 * @param insureAmount
	 * @param pricePremium
	 * @param packingCharges
	 * @param freightPaymentMethod
	 * @param sendLongitude
	 * @param sendLatitude
	 * @param addresseeLongitude
	 * @param addresseeLatitude
	 * @param goodsDesc
	 * @param parcelRemark
	 * @param serviceId
	 * @param signMember
	 * @param createUserId
	 * @param parcelEndMark
	 * @param actualTakeMember
	 * @param actualSendMember
	 * @param receiptId
	 * @param parcelStatus
	 * @param actualCodAmount
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value ="/addOrUpdateParcelInfo", method = { RequestMethod.POST })
	public String addOrUpdateParcelInfo(Long id,Long sendCustomerId, String expWaybillNum, Long compId, 
			Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId, String addresseeAddress, 
			String sendName, String sendMobile, Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, 
			BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges, Short freightPaymentMethod, 
			String parcelRemark, Long createUserId, Long actualTakeMember, BigDecimal actualCodAmount, Short noFly, BigDecimal signGoodsTotal){
		try {
			return jsonSuccess(parcelSupplementService.addOrUpdateParcelInfo(id, sendCustomerId, expWaybillNum, compId, netId,
					addresseeName, addresseeMobile, addresseeAddressId, addresseeAddress, sendName, sendMobile, sendAddressId, 
					sendAddress, chareWeightForsender, insureAmount, pricePremium, packingCharges, freightPaymentMethod, parcelRemark, 
					createUserId, actualTakeMember, actualCodAmount, noFly, signGoodsTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value ="/editAddresseeInfo", method = { RequestMethod.POST })
	public String editAddresseeInfo(Long customerId,String customerName,String customerMobile,String customerAddress,Long memberId,String parcelId) {
		parcelInfoService.editAddresseeInfo(customerId, customerName, customerMobile, customerAddress, memberId, parcelId);
		return jsonSuccess();
	}
			
	
	
	/**
	 * @Method: queryAlreadySignList 
	 * @Description: 已提包裹列表
	 * @param sendMemberId
	 * @return
	 * @author xiangwei.liu
	 * @date 2015-4-20 下午7:51:27
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value ="/queryAlreadySignList", method = { RequestMethod.POST })
	public String queryAlreadySignList(Long sendMemberId,Integer currentPage, Integer pageSize) {
		try {
			if(PubMethod.isEmpty(sendMemberId)){
				return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.001", "memberId不能为null");
			}
			 if(PubMethod.isEmpty(currentPage)){
				return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.002", "currentPage当前页数不能为空");
			 }
			 if(PubMethod.isEmpty(pageSize)){
				return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.003", "pageSize每页条数不能为空");
			 }
			 Long count =  parcelInfoService.queryAlreadySignCount(sendMemberId);   //1.查询提货的派件包裹数量  --jinggq
			 return this.jsonSuccess(parcelInfoService.queryAlreadySignList(sendMemberId, currentPage, pageSize),count); //2.查询提货的派件包裹HE地址  --jinggq
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	

	
	
	/**
	 * @Method: cancelParcelBatche 
	 * @Description: TODO
	 * @param taskId
	 * @param memberId
	 * @param parcelId
	 * @param Id
	 * @param compId
	 * @param compName
	 * @return
	 * @author xiangwei.liu
	 * @date 2015-4-21 上午11:26:48
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value ="/cancelParcelBatche", method = { RequestMethod.POST })
	public String  cancelParcelBatche(Long memberId,String parcelId,Long compId,String compName) {
		try {
			 if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.002", "memberId不能为空");
			 }
			 if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.003", "parcelId不能为空");
			 }
			 if(PubMethod.isEmpty(compId)){
				 return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.005", "compId不能为空");
			 }
			 if(PubMethod.isEmpty(compName)){
				 return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.006", "compName不能为空");
			 }
			 parcelInfoService.cancelParcelBatche(memberId, parcelId,  compId, compName);
			 return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>取件记录查询（包裹）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-11 下午18:39:28</dd>
	 * @param memberId 用户ID
	 * @param date 日期
	 * @return
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value="/takeRecordList", method = { RequestMethod.GET , RequestMethod.POST })
	public String takeRecordList(Long memberId,String date){
		
//		if(PubMethod.isEmpty(memberId)){
//			return JSON.toJSONString("memberId is not null");
//		}
//		if(PubMethod.isEmpty(date)){
//			return JSON.toJSONString("date is not null");
//		}
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		try {
//			List<Map<String,Object>> list=takeTaskService.takeTaskRecordList(memberId,date);
//			int cancelCount = 0;
//			int finishCount = 0;
//			if (list!=null&&list.size()>0) {
//				for (int i = 0; i < list.size(); i++) {
//					Map <String,Object>map = list.get(i);
//					if ("3".equals(map.get("taskStatus").toString())) {
//						cancelCount++;
//					}else{
//						finishCount++;
//					}
//				}
//			}
//			dataMap.put("record", list);
//			dataMap.put("cancelCount", cancelCount);
//			dataMap.put("finishCount", finishCount);
//			 return jsonSuccess(dataMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return jsonFailure();
//		}
		if(PubMethod.isEmpty(memberId)){
			return JSON.toJSONString("memberId is not null");
		}
		if(PubMethod.isEmpty(date)){
			return JSON.toJSONString("date is not null");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<VO_ParcelList> list=parcelInfoService.takeRecordList(memberId,date);
			dataMap.put("record", list);
			dataMap.put("total", list==null?0:list.size());
			 return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/sendRecordList", method = { RequestMethod.GET , RequestMethod.POST })
	public String sendRecordList(Long memberId,Long date){
		if(PubMethod.isEmpty(memberId)){
			return JSON.toJSONString("memberId is not null");
		}
		if(PubMethod.isEmpty(date)){
			return JSON.toJSONString("date is not null");
		}
		try {
			Date signDate = new Date(date);
			Map<String,Object>resultMap = parcelInfoService.sendRecordList(memberId, signDate);
			 return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	private  String jsonSuccess(Object mapObject , Long count){
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		if (!PubMethod.isEmpty(count)){
			allMap.put("count",count);
		}
		String result = JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
		return result;
	}
}