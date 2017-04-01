package net.okdi.apiV4.controller;

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
 * 时间: 15/02/2017 8:49 PM
 * 描述 : 快递员发货
 */
@Controller
@RequestMapping("/deliveryPackage")
public class DeliveryController extends BaseController{

    @Autowired
    private DeliveryService deliveryService;

    private Logger logger=Logger.getLogger(DeliveryController.class);
    /**
     * 查询发货列表
     */
    @ResponseBody
    @RequestMapping(value = "/queryDeliveryList", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryDeliveryList(String memberId, Long netId, Long compId) {
    	 logger.info("进入取件项目中=======查询发货记录=====参数是:memberId:"+memberId+",netId:"+netId+",compId:"+compId);
    	 String result = deliveryService.queryDeliveryList(memberId, netId, compId);
         logger.info("进入取件项目中=======查询返回的结果是:"+result);
         return result;
    }

    /**
     * 查询未打印列表
     */
    @ResponseBody
    @RequestMapping(value = "/queryNotPrintList", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryNotPrintList(String memberId, Long netId, Long compId) {
        logger.info("进入取件项目中=======queryNotPrintList=====参数是:memberId:"+memberId+",netId:"+netId+",compId:"+compId);
        String result = deliveryService.queryNotPrintList(memberId, netId, compId);
        logger.info("进入取件项目中=======queryNotPrintList()返回的结果是:"+result);
        return result;
    }

    /**
     * 查询发货详情
     */
    @ResponseBody
    @RequestMapping(value = "/queryDeliveryDetail", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryDeliveryDetail(String uid) {
    	logger.info("进入取件项目中=======查询发货详情=====uid:"+uid);
    	String result = deliveryService.queryDeliveryDetail(uid);
        logger.info("进入取件项目中=======查询发货详情返回的结果是:"+result);
        return result;
    }

    /**
     * 确认发运
     */
    @ResponseBody
    @RequestMapping(value = "/confirmDelivery", method = {RequestMethod.POST, RequestMethod.GET})
    public String confirmDelivery(String uids, String deliveryAddress) {
    	 logger.info("进入取件项目中=======确认发货=====uids:"+uids+",deliveryAddress:"+deliveryAddress);
         String result = deliveryService.confirmDelivery(uids,deliveryAddress);
         logger.info("进入取件项目中=======确认发货返回的结果是:"+result);
         return result;
    }

    /**
     * 根据二维码获取包裹列表
     */
    @ResponseBody
    @RequestMapping(value = "/getParListByQRCode", method = {RequestMethod.POST, RequestMethod.GET})
    public String getParListByQRCode(String uids) {
    	logger.info("进入取件项目中=======根据二维码获取包裹列表=====uids:"+uids);
    	String result = deliveryService.getParListByQRCode(uids);
    	logger.info("进入取件项目中=======根据二维码获取包裹列表返回的结果是:"+result);
    	return result;
    }

    /**
     * 确认收货
     */
    @ResponseBody
    @RequestMapping(value = "/confirmTakeDelivery", method = {RequestMethod.POST, RequestMethod.GET})
    public String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index) { // "0"快递员 "1"代收点
    	logger.info("进入取件项目中=======确认收货=====uids:"+uids);
    	String result = deliveryService.confirmTakeDelivery(memberId, uids, deliveryAddress, index);
    	logger.info("进入取件项目中=======确认收货返回的结果是:"+jsonSuccess(result));
    	return jsonSuccess(result);
    }

}
