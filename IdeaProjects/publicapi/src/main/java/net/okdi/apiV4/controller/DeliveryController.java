package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.okdi.apiV4.service.DeliveryService;
import net.okdi.core.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 包名: net.okdi.apiV4.controller
 * 创建人 : Elong
 * 时间: 05/02/2017 5:26 PM
 * 描述 : 发货
 */
@Controller
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    private DeliveryService deliveryService;

    private Logger logger=Logger.getLogger(DeliveryController.class);
    /**
     * @author erlong.pei
     * @api {post} /delivery/queryDeliveryList 发货记录
     * @apiVersion 0.2.0
     * @apiDescription 发货记录
     * @apiGroup 发货
     * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员}
     * @apiParam {Long} netId 网络id
     * @apiParam {Long} compId 站点id (默认传0, 当站长查询本站所有人时, 传站点id)
     * @apiSampleRequest /delivery/queryDeliveryList
     * @apiSuccess {String} sendAddress : 发件人地址
     * @apiSuccess {String} sendPhone : 发件人电话
     * @apiSuccess {String} sendName : 发件人名称
     * @apiSuccess {String} expWaybillNum : 运单号
     * @apiSuccess {String} netName : 快递公司
     * @apiSuccess {String} code : 揽收码
     * @apiSuccess {Date} pickupTime : 取件时间
     * @apiSuccess {Long} uid  :     包裹id
     * @apiSuccess {Boolean} isSign : 0:未标记,1:已标记
     * @apiSuccessExample Success-Response:
     * {}
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "success":	false,
     * "errCode":	"err.001",
     * "message":"XXX"
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/queryDeliveryList", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryDeliveryList(String memberId, Long netId, Long compId) {
       try {
    	   logger.info("查询发货记录=====参数是:memberId:"+memberId+",netId:"+netId+",compId:"+compId);
    	   String result = deliveryService.queryDeliveryList(memberId, netId, compId);
    	   logger.info("查询返回的结果是:"+result);
           JSONObject resultObj = JSON.parseObject(result);
           return jsonSuccess(resultObj);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	    }


    /**
     * @author erlong.pei
     * @api {post} /delivery/queryNotPrintList 未打印记录列表
     * @apiVersion 0.2.0
     * @apiDescription 未打印记录列表
     * @apiGroup 发货
     * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员}
     * @apiParam {Long} netId 网络id
     * @apiParam {Long} compId 站点id (默认传0, 当站长查询本站所有人时, 传站点id)
     * @apiSampleRequest /delivery/queryNotPrintList
     * @apiSuccess {String} sendAddress : 发件人地址
     * @apiSuccess {String} sendPhone : 发件人电话
     * @apiSuccess {String} sendName : 发件人名称
     * @apiSuccess {Date} pickupTime : 取件时间
     * @apiSuccess {Long} uid  :     包裹id
     * @apiSuccess {Boolean} isSign : 0:未标记,1:已标记
     * @apiSuccessExample Success-Response:
     * [
     * {
     * "uid": 464644654,
     * "pickupTime": 1482833545837,
     * "sendAddress": "北京市海淀区花园北路",
     * "sendMobile": "13888899999",
     * "sendName": "艾玛",
     * "uid": 253922753454081,
     * "isSign":true
     * }
     * ]
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "success":	false,
     * "errCode":	"err.001",
     * "message":"XXX"
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/queryNotPrintList", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryNotPrintList(String memberId, Long netId, Long compId) {
        try {
            logger.info("queryNotPrintList=====参数是:memberId:"+memberId+",netId:"+netId+",compId:"+compId);
            String result = deliveryService.queryNotPrintList(memberId, netId, compId);
            logger.info("查询返回的结果是:"+result);
            JSONObject resultObj = JSON.parseObject(result);
            return jsonSuccess(resultObj);
        } catch (Exception e) {
            return jsonFailure(e);
        }
    }

    /**
     * @author erlong.pei
     * @api {post} /delivery/confirmDelivery 确认发运
     * @apiVersion 0.2.0
     * @apiDescription 确认发运
     * @apiGroup 发货
     * @apiParam {String} uids 包裹id（格式: "1111111-2222222-3333333"）
     * @apiParam {String} deliveryAddress 发运地址
     * @apiSampleRequest /delivery/confirmDelivery
     * @apiSuccessExample Success-Response:
     * 1
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * 
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/confirmDelivery", method = {RequestMethod.POST, RequestMethod.GET})
    public String confirmDelivery(String uids,String deliveryAddress) {
        try {
        	logger.info("确认发货=====uids:"+uids+",deliveryAddress:"+deliveryAddress);
        	String result = deliveryService.confirmDelivery(uids,deliveryAddress);
        	logger.info("确认发货返回的结果是:"+result);
     	   return jsonSuccess(result);
 			} catch (Exception e) {
 				return jsonFailure(e);
 			}
    }

    
    /**
     * @author erlong.pei
     * @api {post} /delivery/queryDeliveryDetail 查询发货详情
     * @apiVersion 0.2.0
     * @apiDescription查询发货详情
     * @apiGroup 发货
     * @apiParam {String} uid 包裹id
     * @apiSampleRequest /delivery/queryDeliveryDetail
     * @apiSuccessExample Success-Response:
     * {
		    "addresseeAddress": "北京市海淀区 ",
		    "addresseeName": "",
		    "content": [
		        {
		            "name": "格格",
		            "time": 1487247100476
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487410893001
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487411130070
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487411147088
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487411219861
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487411295762
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487412338532
		        },
		        {
		            "name": "孙盼柯",
		            "time": 1487412379303
		        },
		        {
		            "content": "",
		            "name": "孙盼柯",
		            "time": 1487415095563
		        }
		    ],
		    "parNumber": 1,
		    "uid": 216448081682434
		    "expWaybillNum": 216448081682434
		    "netName": 百世快递
		    "sendName": "ss",
		    "sendPhone": "aa",
		    "sendAddress": "aa",
		    "addresseeName": "aa",
		    "addresseePhone": "a",
		    "addresseeAddress": "a",
		    "parNumber": 216448081682434,
		    "shipmentRemark": 216448081682434,
		    "shipmentTime": 216448081682434
		}
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "success":	false,
     * "errCode":	"错误码",
     * "message":   "错误信息"
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/queryDeliveryDetail", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryDeliveryDetail(String uid) {
    	 try {
		 logger.info("查询发货详情=====uid:"+uid);
		 String result = deliveryService.queryDeliveryDetail(uid);
		 logger.info("查询发货详情返回的结果是:"+result);
		 return result;
		} catch (Exception e) {
			return jsonFailure(e);
		}
    }
   
    /**
     * @author erlong.pei
     * @api {post} /delivery/getParListByQRCode 根据二维码获取包裹列表
     * @apiVersion 0.2.0
     * @apiDescription查询发货详情
     * @apiGroup 发货
     * @apiParam {String} uids 包裹id（格式: "1111111-2222222-3333333"）
     * @apiSampleRequest /delivery/getParListByQRCode
     * @apiSuccessExample Success-Response:
     * {
    "data":{
    "parList":[
    {
    "expWaybillNum":"123456",
    "sendPhone":"13589999999",
    "uid":"264083081601025"
    }
    ],
    "parNumber":1
    },
    "success":true
    }
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "success":	false,
     * "errCode":	"错误码",
     * "message":   "错误信息"
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/getParListByQRCode", method = {RequestMethod.POST, RequestMethod.GET})
    public String getParListByQRCode(String uids) {
     try {
    	    	
    	 logger.info("根据二维码获取包裹列表=====uids:"+uids);
    	 String result = deliveryService.getParListByQRCode(uids);
    	 logger.info("根据二维码获取包裹列表返回的结果是:"+result);
         return jsonSuccess(JSON.parseObject(result));
      	} catch (Exception e) {
      				return jsonFailure(e);
      	}
    }
    
    /**
     * @author erlong.pei
     * @api {post} /delivery/confirmTakeDelivery 确认收货
     * @apiVersion 0.2.0
     * @apiDescription  确认收货
     * @apiGroup 发货
     * @apiParam {String} uids 包裹id（待发过来的二维码: "1111111-2222222-3333333,0"   转代收过来的二维码： "1111111-2222222-3333333,1"）
     * @apiParam {String} memberId "0"快递员 "1"代收点
     * @apiParam {String} deliveryAddress 发运地址
     * @apiParam {String} index 传二维码上边的 0 或者 1
     * @apiSampleRequest /delivery/confirmTakeDelivery
     * @apiSuccessExample Success-Response:
     * 
     * "1"
     */
    @ResponseBody
    @RequestMapping(value = "/confirmTakeDelivery", method = {RequestMethod.POST, RequestMethod.GET})
    public String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index) { // "0"快递员 "1"代收点
    	 try {
    		 logger.info("确认收货=====uids:"+uids+",memberId:"+memberId+",deliveryAddress:"+deliveryAddress+",index:"+index);
    		 String result = deliveryService.confirmTakeDelivery(memberId, uids, deliveryAddress, index);
    		 logger.info("确认收货返回的结果是:"+result);
    		 return result;
  			} catch (Exception e) {
  				return jsonFailure(e);
  			}
    }
    
    
    
}
