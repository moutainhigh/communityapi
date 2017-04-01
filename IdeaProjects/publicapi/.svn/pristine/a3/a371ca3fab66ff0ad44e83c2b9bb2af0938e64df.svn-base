package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;

public class PayHttpClient extends AbstractHttpClient {

	@Autowired
	private ConstPool constPool;

	public String addPayInfo(Long fromMemberId, Long toMemberId, String orderId, String orderName,
			Double totalFee) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fromMemberId", fromMemberId==null?"":String.valueOf(fromMemberId));
		map.put("toMemberId", toMemberId==null?"":String.valueOf(toMemberId));
		map.put("orderId", orderId);
		map.put("orderName", orderName);
		map.put("totalFee", String.valueOf(totalFee));
		String methodName = "addPayInfo";
		String url = constPool.getPayUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	public String modifyPayStatus(String orderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		String methodName = "modifyPayStatus";
		String url = constPool.getPayUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	private Map<String, String> buildParams(Map<String, String> map) {
		if (PubMethod.isEmpty(map)) {
			map = new HashMap<String, String>();
		}
		return map;
	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信支付购买短信异步回调调用财务接口</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>tiecheng.an</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-9-23</dd>
	 * @param tradeNum订单号
	 * @param payStatus支付状态
	 * @param attach额外参数
	 * @param sign签名
	 * @param mchId商户号
	 * @param transactionId微信订单号
	 * @return
	 * @since v1.0
	*/
	public String smspurcallback(String tradeNum,String payStatus,String attach,String sign,String mchId,String transactionId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tradeNum", tradeNum);
		map.put("payStatus", payStatus);
		map.put("attach", attach);
		map.put("sign", sign);
		map.put("mchId", mchId);
		map.put("transactionId", transactionId);
		String methodName = "weixinSmsPurchaseCallBack";
		String url = constPool.getFinancial() + methodName;
		this.buildParams(map);
		return Post(url, map);
	}
	
	public String callBackOrder(String tradeNum,String payStatus,String cashAmount,String thirdPayAmount,
			String couponsTotalAmount,String outcomeAccountPayAmount,String paymentType,String paymentAmount) {
		if(PubMethod.isEmpty(tradeNum)){
			return null;
		}
		if(PubMethod.isEmpty(payStatus)){
			return null;
		}
		if(PubMethod.isEmpty(paymentAmount)){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("tradeNum", tradeNum);
		map.put("payStatus", payStatus);
		map.put("cashAmount", cashAmount);
		map.put("paymentAmount", paymentAmount);
		map.put("thirdPayAmount", thirdPayAmount);
		map.put("couponsTotalAmount", couponsTotalAmount);
		map.put("outcomeAccountPayAmount", outcomeAccountPayAmount);
		map.put("paymentType", paymentType);
		String methodName = "tradeInfoController/receivablesWeixin";
		String url = constPool.getO2oApiUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

	public String invokePaymentCallback(String out_trade_no, String payStatus,
			String attach, String sign, String mch_id, String transaction_id,
			String trade_type,String openId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("out_trade_no", out_trade_no);
		map.put("payStatus", payStatus);
		map.put("attach", attach);
		map.put("sign", sign);
		map.put("mchId", mch_id);
		map.put("transactionId",transaction_id);
		map.put("tradeType", trade_type);
		map.put("buyerOpenId", openId);
		String methodName = "ws/recharge/invoke_payment_callback";
		String url = constPool.getNewPayUrl() + methodName;
		this.buildParams(map);
		String response = Post(url, map);
		return response;
	}

}
