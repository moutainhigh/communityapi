package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.service.ExpressPriceService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 网络价格
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/expressPrice")
public class ExpressPriceController extends BaseController {
	@Autowired
	private ExpressPriceService expressPriceService;
	/**
	 * @api {post} /expressPrice/getExpressPrice 计算包裹运费
	 * @apiPermission user
	 * @apiDescription 计算包裹运费
	 * @apiparam {Long} netId 网络ID
	 * @apiparam {String} sendAddressId 发件地址ID
	 * @apiparam {Long} receiveAddressId 收件地址ID
	 * @apiparam {String} weight  重量
	 * @apiGroup 取件
	 * @apiSampleRequest /expressPrice/getExpressPrice
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"data":{"price":"0.00"},"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.ExpressPriceServiceImpl.getExpressPrice.001", "计算运费异常,sendAddressId参数非空异常","success":false}
	 *   {"errCode":0,"errSubcode":"openapi.ExpressPriceServiceImpl.getExpressPrice.002", "计算运费异常,netId参数非空异常"","success":false}
	 *   {"errCode":0,"errSubcode":"openapi.ExpressPriceServiceImpl.getExpressPrice.003", "计算运费异常,receiveAddressId参数非空异常","success":false}
	 *   {"errCode":0,"errSubcode":"openapi.ExpressPriceServiceImpl.getExpressPrice.004", "计算运费异常,weight参数异常","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	
	@RequestMapping(value = "/getExpressPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String  getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight){
		try {
			return this.expressPriceService.getExpressPrice(netId, sendAddressId, receiveAddressId, weight);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
