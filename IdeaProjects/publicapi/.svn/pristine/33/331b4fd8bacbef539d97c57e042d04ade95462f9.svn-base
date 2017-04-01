package net.okdi.apiV4.controller;

import java.util.Map;

import net.okdi.apiV4.service.ReceivePackageReportService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receiveReport")
public class ReceivePackageReportController extends BaseController{

	@Autowired
	private ReceivePackageReportService receivePackageReportService;
	
	
	/**
	 * @author song
	 * @api {post} /receiveReport/queryPackage 揽收运单号查询电商件
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收运单号查询电商件（新版揽收）
	 * @apiGroup 新版-揽收V5
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} expWaybillNum 运单号    (不能为空)	 
	 * @apiParam {String} netId   网络id  (不能为空)
	 * @apiParam {String} netName   网络名称 
	 * @apiSampleRequest /receiveReport/queryPackage
	 * @apiSuccess {Long} parcelId      包裹id
	 * @apiSuccess {String} netName   网络名称
	 * @apiSuccess {String} netId  网络id 
	 * @apiSuccess {String} sendProv  发件人省份
	 * @apiSuccess {String} sendCity  发件人市区
	 * @apiSuccess {String} sendDetailed  详细地址
	 * @apiSuccess {String} sendAddress  发件人地址
	 * @apiSuccess {String} sendMobile  发件人电话
	 * @apiSuccess {String} sendName  发件人名称
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} addresseePhone  收件人电话
	 * @apiSuccess {String} addresseeAddress  收件人地址	 
	 * @apiSuccess {String} addresseeProv  收件人省份
	 * @apiSuccess {String} addresseeCity  收件人市区
	 * @apiSuccess {String} addresseeDetailed  详细地址	
	 * @apiSuccess {String} expWaybillNum  运单号
	 * @apiSuccess {String} comment   标记备注
	 * @apiSuccess {String} pacelWeight  包裹重量
	 * @apiSuccess {String} parcelType  物品类型
	 * @apiSuccess {String} serviceName  服务产品
	 * @apiSuccess {String} flag  （去重标示）0 ：未揽收，1：已揽收  ,2：授权国通
	 * @apiSuccessExample Success-Response:																		               
	 {
	    "data": {
	     	"flag":  0
	        "listMap": [
	            {
	               "addresseeAddress": "北京朝阳区",
	                "addresseeMobile": "13588899999",
	                "addresseeName": "哈哈哈",
					"expWaybillNum": "70567200043203",
					"netName": "AAE全球专递",
					"sendAddress": "北京市海淀区花园北路",
					"sendMobile": "13888899999",
					"sendName": "艾玛",
					"comment": "易燃",
					"parcelId": 253922753454081
	            },
	        ]
	    },
	    "success": true
	 }
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryPackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackage(String memberId, String expWaybillNum, String netId,String netName){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}						
		return receivePackageReportService.queryPackage(memberId,expWaybillNum,netId,netName);
	}
		
	/**
	 * @author song
	 * @api {post} /receiveReport/queryPackageCode 揽收码查询
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收-揽收码查询（新版揽散件）
	 * @apiGroup 新版-揽收V5
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} code  揽收码（扫码判断）  (不能为空)	 
	 * @apiParam {String} netId   网络id  (不能为空)
	 * @apiSampleRequest /receiveReport/queryPackageCode
	 * @apiSuccess {Long} parcelId      包裹id
	 * @apiSuccess {String} data: 返回的是 001:新的, 002:已揽收	
	 * @apiSuccessExample Success-Response:																		               
	 {
	    "data": "001",
	    "success": true
	 }
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryPackageCode", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackageCode(String memberId, String code, String netId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}						
		return receivePackageReportService.queryPackageCode(memberId,code,netId);
	}
	/**
	 * @author song
	 * @api {post} /receiveReport/queryTakeforms 揽收报表--取件 2016-12-28
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收报表（new）
	 * @apiGroup 新版-报表V5
	 * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员 提供}
	 * @apiParam {String} date 选择的日期格式yyyy-mm(不能为空)	
	 * @apiParam {String} compId     站点id    全部包裹	 
	 * @apiSampleRequest /receiveReport/queryTakeforms	
	 * @apiSuccessExample Success-Response:	
     {
	   "data": {
	        "mapList": [
	            {
	                "date": "6月9日",
	                "count": 1
	            },
	            {
	                "date": "6月4日",
	                "count": 17
	            },
	            {
	                "date": "6月3日",
	                "count": 4
	            },
	            {
	                "date": "6月1日",
	                "count": 3
	            }
	          ],
	          "sum": 25
	      },
         "success": true
      }
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTakeforms", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeforms(String memberId, String date,String compId){
		
		String result = receivePackageReportService.queryTakeforms(memberId, date, compId);
		return result;
	}
	
	/**
	 * @author song
	 * @api {post} /receiveReport/takeReceivePackageByMember  添加取件（或添加订单中包裹）--新取件-揽收
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员或者代收点添加取件(揽收  扫码)
	 * @apiGroup 新版-报表V5
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiParam {String} roleId   角色id
	 * @apiParam {String} compId   站点或代理点id
	 * @apiParam {String} sendName 发件人name(不能为空)
	 * @apiParam {String} sendPhone 发件人手机号(不能为空)
	 * @apiParam {String} sendProv 发件人省份(不能为空)
	 * @apiParam {String} sendCity 发件人市县（区）(不能为空)  "北京市,海淀区"逗号分隔
	 * @apiParam {String} sendDetailed 发件人详细地址(不能为空)
	 * @apiParam {String} sendAddress 发件人地址(不能为空)
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} netName 网络名称
	 * @apiParam {String} addresseeName  收件人姓名(不能为空)
	 * @apiParam {String} addresseePhone  收件人电话(不能为空)
	 * @apiParam {String} addresseeProv 收件人省份(不能为空)
	 * @apiParam {String} addresseeCity 收件人市县（区）(不能为空)
	 * @apiParam {String} addresseeDetailed 收件人详细地址(不能为空)
	 * @apiParam {String} addresseeAddress 收件人地址(不能为空)
	 * @apiParam {String} expWaybillNum   运单号  (不能为空)
	 * @apiParam {String} code     取件码     
	 * @apiParam {String} deliveryAddress    当前交付地址     
	 * @apiParam {String} comment   标记备注
	 * @apiParam {String} pacelWeight   包裹重量
	 * @apiParam {String} parcelType   物品类型
	 * @apiParam {String} serviceName   服务产品
	 * @apiParam {Long} parcelId  包裹parcelId（标示是否订单包裹）  
	 * @apiSampleRequest /receiveReport/takeReceivePackageByMember
	 * @apiSuccess {String} data: 返回的是 001:代表成功, 002:代表失败
	 * @apiSuccessExample Success-Response:
	   {
		    "data": "001",
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageByMember", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName,
			 String sendProv,String sendCity,String addresseeProv,String addresseeCity,String sendDetailed,String addresseeDetailed){
		
		String result = receivePackageReportService.takeReceivePackageByMember(memberId, sendName, sendPhone,  sendAddress, deliveryAddress,
				 netId,  addresseeName,  addresseeAddress, addresseePhone, netName, roleId,
				 expWaybillNum , code, comment, parcelId, compId,pacelWeight,parcelType,serviceName, sendProv, sendCity, addresseeProv,
				 addresseeCity, sendDetailed, addresseeDetailed);
		
		return result;		

	}
	/**
	 * @author song
	 * @api {post} /receiveReport/takeReceivePackage  揽散件
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员或者代收点添加取件(揽散件  扫码)
	 * @apiGroup 新版-揽收V5
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiParam {String} roleId   角色id
	 * @apiParam {String} compId   站点或代理点id
	 * @apiParam {String} sendName 发件人name(不能为空)
	 * @apiParam {String} sendPhone 发件人手机号(不能为空)
	 * @apiParam {String} sendProv 发件人省份(不能为空)
	 * @apiParam {String} sendCity 发件人市县（区）(不能为空)  "北京市,海淀区"逗号分隔
	 * @apiParam {String} sendDetailed 发件人详细地址(不能为空)
	 * @apiParam {String} sendAddress 发件人地址(不能为空)
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} netName 网络名称
	 * @apiParam {String} addresseeName  收件人姓名(不能为空)
	 * @apiParam {String} addresseePhone  收件人电话(不能为空)
	 * @apiParam {String} addresseeProv 收件人省份(不能为空)
	 * @apiParam {String} addresseeCity 收件人市县（区）(不能为空)
	 * @apiParam {String} addresseeDetailed 收件人详细地址(不能为空)
	 * @apiParam {String} addresseeAddress 收件人地址(不能为空)
	 * @apiParam {String} expWaybillNum   运单号  
	 * @apiParam {String} code     取件码    (不能为空) 
	 * @apiParam {String} deliveryAddress    当前交付地址     
	 * @apiParam {String} comment   标记备注
	 * @apiParam {String} pacelWeight   包裹重量
	 * @apiParam {String} parcelType   物品类型
	 * @apiParam {String} serviceName   服务产品
	 * @apiParam {String} versionId   客户端（apk）版本号 
	 * @apiParam {String} terminalId    设备号（IMEI号）
	 * @apiSampleRequest /receiveReport/takeReceivePackage
	 * @apiSuccess {String} data: 返回的是 001:代表成功, 002:代表失败
	 * @apiSuccessExample Success-Response:
	   {
	    "data": {
	        "code": "14567",
	        "expWaybillNum": "",
	        "netId": "1536",
	        "netName": "百世汇通",
	        "parcelId": 269317310849024,
	        "reason": "",
	        "success": "true"
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackage(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,String compId,String pacelWeight,String parcelType,String serviceName,
			 String sendProv,String sendCity,String addresseeProv,String addresseeCity,String sendDetailed,String addresseeDetailed,
			 String versionId ,String terminalId){
		
		String result = receivePackageReportService.takeReceivePackage(memberId, sendName, sendPhone,  sendAddress, deliveryAddress,
				 netId,  addresseeName,  addresseeAddress, addresseePhone, netName, roleId,
				 expWaybillNum , code, comment, compId,pacelWeight,parcelType,serviceName, sendProv, sendCity, addresseeProv,
				 addresseeCity, sendDetailed, addresseeDetailed, versionId , terminalId);
		
		return result;		

	}
	/**
	 * @author song
	 * @api {post} /receiveReport/finishTakeReceivePackage 揽收--确认揽收
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收报表（new）
	 * @apiGroup 新版-报表V5
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} jsonData  {"items":[{"netId":"1526","netName":"国通","expWay":"1453"}]}
	 * @apiParam {String} codes  揽收码，拼接字符串  ","
	 * @apiParam {String} compId     站点id  
	 * @apiParam {String} deliveryAddress    交付地址 
	 * @apiParam {String} versionId        客户端（apk）版本号 
	 * @apiParam {String} terminalId     设备号（IMEI号）
	 * @apiSampleRequest /receiveReport/finishTakeReceivePackage	
	 * @apiSuccessExample Success-Response:	
     {}      
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/finishTakeReceivePackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String finishTakeReceivePackage(String memberId ,String jsonData,String codes,String compId,String deliveryAddress,
			String versionId ,String terminalId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		//{"items":[{"netId":"1526","netName":"国通","expWay":"1453","sendName":"张三","sendAddress":"北京市海淀区环星大厦","sendMobile":"13535353000"}]}
		String result = receivePackageReportService.finishTakeReceivePackage(memberId,jsonData, codes,compId,deliveryAddress,versionId ,terminalId);		
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receiveReport/againUpload 揽收失败--重新上传
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收上传订单失败重新上传（new）
	 * @apiGroup 新版-揽收V5
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} expWaybillNums  运单号	
	 * @apiParam {String} code  揽收码
	 * @apiParam {String} netId    快递公司id
	 * @apiParam {String} netName     快递公司名称   
	 * @apiParam {Long} parcelId     包裹id 
	 * @apiSampleRequest /receiveReport/againUpload	
	 * @apiSuccessExample Success-Response:	   
	 {
	    "data": {
	        "code": "21043",
	        "expWaybillNum": "",
	        "netId": "1536",
	        "netName": "国通",
	        "parcelId": 269287030071296,
	        "reason": "已存在该订单号或取件码",
	        "success": "false"
	    },
	    "success": true
	}       
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/againUpload", method = {RequestMethod.POST, RequestMethod.GET })
	public String againUpload(String memberId, String netId, String netName,String expWaybillNum ,String code,Long parcelId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
	
		String result = receivePackageReportService.againUpload(memberId, netId, netName, expWaybillNum,code,parcelId);
		return result;		

	}
	
}
