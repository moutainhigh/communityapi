package net.okdi.api.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.okdi.api.entity.ReceiveMsg;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.PayHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("weixin")
public class WechatController extends BaseController{
	private static Logger logger = Logger.getLogger(WechatController.class);
	@Autowired
	private PayHttpClient payHttpClient;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信支付购买短信异步回调(微信异步回调返回数据是xml)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>tiecheng.an</dd> <dt><span
	 * class="strong">时间:</span></dt><dd>2015-9-23 上午10:18:33</dd>
	 * @param request
	 * @param response
	 * @param is
	 * @throws IOException
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/smspurchasecallback", method = { RequestMethod.POST, RequestMethod.GET })
	public void smspurchasecallback(HttpServletRequest request, HttpServletResponse response, InputStream is)
			throws IOException {
		PrintWriter pw = response.getWriter();
		logger.info("微信支付异步回调okdilifeapi.weixin.smspurchasecallback");
		try {
			String return_code = "FAIL";
			String return_msg = "ERROR";
			/** 微信异步回调返回数据是xml **/
			ReceiveMsg receiveMsg = new ReceiveMsg();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();// 获得根节点
			NodeList msgs = root.getChildNodes();
			// 解析XML里面的数据并且封装成对象
			for (int i = 0; i < msgs.getLength(); i++) {// 遍历元素
				Node msg = msgs.item(i);
				if (msg.getNodeType() == Node.ELEMENT_NODE) {// 如果是元素节点
					String nodeName = msg.getNodeName().trim();// 获得元素的名
					String nodeValue = msg.getTextContent();
					setReceiveMsg(receiveMsg, nodeName, nodeValue);
				}
			}
			logger.info("微信支付异步回调返回的参数:" + JSON.toJSONString(receiveMsg));
			// 只要return_code为SUCCESS时候 微信才会返回业务数据
			if ("SUCCESS".equals(receiveMsg.getReturn_code())) {
				String payStatus = "";
				String o2oOderStatus = "";
				// 根据业务结果判断支付状态 SUCCESS为支付成功 其他为支付失败
				if ("SUCCESS".equals(receiveMsg.getResult_code())) {
					payStatus = "1";
					o2oOderStatus = "1";
				} else {
					payStatus = "2";
					o2oOderStatus = "0";
				}
				// 调用财务系统修改订单支付状态
				String result = payHttpClient.smspurcallback(receiveMsg.getOut_trade_no(), payStatus,
						receiveMsg.getAttach(),receiveMsg.getSign(),receiveMsg.getMch_id(),receiveMsg.getTransaction_id());
				logger.info("调用财务系统返回结果:" + JSON.toJSONString(result));
				if (result != null && !"".equals(result)) {
					if (JSON.parseObject(result).get("success").equals(true)) {
						// 调用O2OAPI系统修改订单支付状态
						String callBackOrderResult = callBackOrder(result, o2oOderStatus);
						logger.info("调用O2O2API系统返回结果:" + JSON.toJSONString(callBackOrderResult));
						if (callBackOrderResult != null && !"".equals(callBackOrderResult)) {
							if (JSON.parseObject(callBackOrderResult).get("success").equals(true)) {
								return_code = "SUCCESS";
								return_msg = "OK";
							}
						}
					}
				}
			}
			String xml = "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA["
					+ return_msg + "]]></return_msg></xml>";
			logger.info("同步告诉微信结果:" + xml);
			pw.write(xml);
		} catch (Exception e) {
			e.printStackTrace();
			String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg>ERROR</return_msg></xml>";
			logger.info("同步告诉微信结果:" + xml);
			pw.write(xml);
		} finally {
			pw.close();
		}
	}
	
	private String callBackOrder(String result, String payStatus) {
		// 运单号
		String tradeNum = String.valueOf(JSON.parseObject(result).get("tradeNum"));
		if (PubMethod.isEmpty(tradeNum) || "null".equals(tradeNum)) {
			return null;
		}
		// 线下支付额度
		String cashAmount = String.valueOf(JSON.parseObject(result).get("cashAmount"));
		if (PubMethod.isEmpty(cashAmount) || "null".equals(cashAmount)) {
			cashAmount = "";
		}
		// 第三方平台支付实际金额
		String thirdPayAmount = String.valueOf(JSON.parseObject(result).get("thirdPayAmount"));
		if (PubMethod.isEmpty(thirdPayAmount) || "null".equals(thirdPayAmount)) {
			thirdPayAmount = "";
		}
		// 实际使用消费券金额
		String couponsTotalAmount = String.valueOf(JSON.parseObject(result).get("couponsTotalAmount"));
		if (PubMethod.isEmpty(couponsTotalAmount) || "null".equals(couponsTotalAmount)) {
			couponsTotalAmount = "";
		}
		// 账户支付实际金额
		String outcomeAccountPayAmount = String.valueOf(JSON.parseObject(result).get("outcomeAccountPayAmount"));
		if (PubMethod.isEmpty(outcomeAccountPayAmount) || "null".equals(outcomeAccountPayAmount)) {
			outcomeAccountPayAmount = "";
		}
		// 1支付宝2微信3银联
		String paymentType = String.valueOf(JSON.parseObject(result).get("paymentType"));
		if (PubMethod.isEmpty(paymentType) || "null".equals(paymentType)) {
			return null;
		}
		// 支付总金额
		String paymentAmount = String.valueOf(JSON.parseObject(result).get("paymentAmount"));
		if (PubMethod.isEmpty(paymentAmount) || "null".equals(paymentAmount)) {
			return null;
		}
		return payHttpClient.callBackOrder(tradeNum, payStatus, cashAmount, thirdPayAmount, couponsTotalAmount,
				outcomeAccountPayAmount, paymentType, paymentAmount);
	}
	
	private void setReceiveMsg(ReceiveMsg msg, String name, String value) {
		if (name.equals("return_code"))
			msg.setReturn_code(value);
		if (name.equals("return_msg"))
			msg.setReturn_msg(value);
		if (name.equals("appid"))
			msg.setAppid(value);
		if (name.equals("mch_id"))
			msg.setMch_id(value);
		if (name.equals("nonce_str"))
			msg.setNonce_str(value);
		if (name.equals("sign"))
			msg.setSign(value);
		if (name.equals("result_code"))
			msg.setResult_code(value);
		if (name.equals("openid"))
			msg.setOpenid(value);
		if (name.equals("is_subscribe"))
			msg.setIs_subscribe(value);
		if (name.equals("trade_type"))
			msg.setTrade_type(value);
		if (name.equals("bank_type"))
			msg.setBank_type(value);
		if (name.equals("total_fee"))
			msg.setTotal_fee(value);
		if (name.equals("cash_fee"))
			msg.setCash_fee(value);
		if (name.equals("transaction_id"))
			msg.setTransaction_id(value);
		if (name.equals("out_trade_no"))
			msg.setOut_trade_no(value.substring(0, value.indexOf("D")));
		if (name.equals("time_end"))
			msg.setTime_end(value);
		if (name.equals("attach"))
			msg.setAttach(value);

	}
}
