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
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算包裹运费</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 上午11:02:59</dd>
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param receiveAddressId 收件地址ID
	 * @param weight 重量
	 * @return {"data":{"price":"0.00"},"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.001", "计算运费异常,sendAddressId参数非空异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.002", "计算运费异常,netId参数非空异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.003", "计算运费异常,receiveAddressId参数非空异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.004", "计算运费异常,weight参数异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.005", "计算运费异常,获取发件地址异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.006", "计算运费异常,获取收件地址异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.007", "计算运费异常,获取网络信息异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getExpressPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String  getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight){
		try {
			Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
			map.put("price", this.expressPriceService.getExpressPrice(netId, sendAddressId, receiveAddressId, weight));
			return jsonSuccess(map);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算包裹总运费</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午7:51:30</dd>
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param weightAndreceiveAddressIds 重量、收件地址ID      weight-addressId,weight-addressId
	 * @return {"data":{"1.00-1101201":"10.00","2.00-1101201":"15.00","totalPrice":"25.00"},"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.ExpressPriceServiceImpl.getExpressTotalPrice.001", "计算运费异常,weightAndreceiveAddressIds参数非空异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.getExpressTotalPrice.002", "计算运费异常,weightAndreceiveAddressIds参数格式异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.getExpressTotalPrice.003", "计算运费异常,weight类型异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.getExpressTotalPrice.004", "计算运费异常,receiveAddressId类型异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.001", "计算运费异常,sendAddressId参数非空异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.002", "计算运费异常,netId参数非空异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.004", "计算运费异常,weight参数异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.005", "计算运费异常,获取发件地址异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.006", "计算运费异常,获取收件地址异常"</dd>
     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.007", "计算运费异常,获取网络信息异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getExpressTotalPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String  getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds){
		try {
			return jsonSuccess(this.expressPriceService.getExpressTotalPrice(netId, sendAddressId, weightAndreceiveAddressIds));
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>包裹收款/结算</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午4:42:40</dd>
	 * @param parcelIds 包裹ID
	 * @param totalCodAmount 代收货款金额
	 * @param totalFreight 实收运费
	 * @param memberId 创建人 
	 * @param type 类型 0取件 1派件
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.ExpressPriceServiceImpl.settleAccounts.001", "包裹收款结算异常,parcelIds参数非空异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.settleAccounts.002", "包裹收款结算异常,memberId参数非空异常""</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.settleAccounts.003", "包裹收款结算异常,type参数异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.settleAccounts.004", "包裹收款结算异常,费用异常"</dd>
	 * <dd>"openapi.ExpressPriceServiceImpl.settleAccounts.005", "包裹收款结算异常,parcelId异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/settleAccounts", method = { RequestMethod.POST, RequestMethod.GET })
	public String  settleAccounts(String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,short type){
		try {
			this.expressPriceService.settleAccounts(parcelIds, totalCodAmount, totalFreight, memberId, type);
			return jsonSuccess();
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
