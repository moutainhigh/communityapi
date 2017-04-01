package net.okdi.apiV3.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.okdi.apiV3.entity.ReceiveMsg;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV3.service.WechatPayBackService;
import net.okdi.apiV4.service.CustomerInfoNewService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.httpClient.PayHttpClient;
import net.okdi.httpClient.WechatHttpClient;
import net.okdi.mob.service.CustomerInfoService;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.ReceiveAndReplyMessageCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("weChatPayBack")
public class WechatPayBackController extends AbstractHttpClient {

	private static Logger logger = Logger.getLogger(WechatPayBackController.class);
	
	@Autowired
	private WechatHttpClient WechatHttpClient;
	@Autowired
	private WechatPayBackService wechatPayBackService;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private PayHttpClient payHttpClient;
	
	@Autowired
	CustomerInfoService customerInfoService;
	@Autowired
	CustomerInfoNewService customerInfoNewService;
	@Autowired
	private ReceivePackageService receivePackageService;
	@Autowired
	private WalletNewService walletNewService;//钱包service
	
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<>();
		map.put("3", "1");
		map.put("2", "0");
		
		String jsonStr = JSON.toJSONString(map);
		
		JSONObject obj = JSON.parseObject(jsonStr, JSONObject.class);
		System.out.println(obj.getString("3"));
		System.out.println(obj.getString("2"));
		System.out.println(obj.getString("5"));
		
		
		
		
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/paycallback", method = { RequestMethod.POST, RequestMethod.GET })
	public void paycallback(HttpServletRequest request, HttpServletResponse response, InputStream is)
			throws IOException {
		
		PrintWriter pw = response.getWriter();
		logger.info("微信支付异步回调 /weChatPayBack/paycallback publicapi--net.okdi.apiV3.controller.WechatPayBackController");
		String return_code = "FAIL";
		String return_msg = "ERROR";
		
		try {
			/** 微信异步回调返回数据是xml **/
			ReceiveMsg receiveMsg = parseWechatCallbackMsg(is);
			logger.info("微信充值回调方法paycallback--微信支付异步回调返回的参数/weChatPayBack/paycallback:" + JSON.toJSONString(receiveMsg));
			// 只要return_code为SUCCESS时候 微信才会返回业务数据
			if (!"SUCCESS".equals(receiveMsg.getReturn_code())) {
				logger.error("微信回调方法返回ERROR, 不进行业务处理");
				pw.write(getReturnStr(return_code, return_msg));
				return;
			}
			
			String payStatus = "";
			String attach = "";
			// 根据业务结果判断支付状态 SUCCESS为支付成功 其他为支付失败
			if ("SUCCESS".equals(receiveMsg.getResult_code())) {
				payStatus = "1";
				attach = receiveMsg.getAttach();
			} else {
				payStatus = "2";
			}
			
			// 调用财务系统修改订单支付状态
			String result = WechatHttpClient.paycallback(receiveMsg.getOut_trade_no(), payStatus,
					receiveMsg.getAttach(), receiveMsg.getSign(), receiveMsg.getMch_id(),
					receiveMsg.getTransaction_id(), receiveMsg.getOut_trade_no());

			logger.info("微信充值回调方法paycallback--调用财务系统返回结果:" + JSON.toJSONString(result));
			// 微信支付成功后，返利
			// -------返利开始---------//
			Map map = (Map) JSON.parseObject(result).get("data");
			String accountId = map.get("accountId").toString();
			if (JSON.parseObject(result).get("success").equals(true)) {
				if ("1".equals(map.get("state").toString())) {
					// 查询财务系统通讯账户余额
					String telecomBook = WechatHttpClient.queryTelecomBook();
					// 微信支付成功后，返利
					String total_fee = receiveMsg.getTotal_fee();// 微信返回金额为分
					double parseDouble = Double.parseDouble(total_fee);
					double money = parseDouble / 100;
					HashMap<String, Object> rewardMap = wechatPayBackService.getActivityRule(money);
					String memberId = WechatHttpClient.getMemberIdByAccountId(accountId);
					
					// 微信交易号, 根据这个交易号来确保一次交易, 不管回调几次只返利一次, 保证幂等 2016-12-22 by yuhang
					String tradeNum = map.get("tradeNum").toString();
					JSONObject rebateLog = walletNewService.oneTradeRebateLog(tradeNum, memberId);
					logger.info("微信交易号: " + tradeNum + " 查询到的返利记录: " + rebateLog);
					// 财务系统通讯账户余额不足的时候不返利
					boolean hasRebateToRecharger = rebateLog.getString("2") == null || "0".equals(rebateLog.getString("2")) ? false : true;
					if (!PubMethod.isEmpty(rewardMap.get("rewardMoney"))) {
						if (!hasRebateToRecharger) {
							rebateToRecharger(accountId, tradeNum, telecomBook, money, rewardMap, memberId);
						} else {
							logger.error("已经给充值者返利了, 不再返利");
						}
					}
					// B.返利接口---返利给邀请者（好友充值奖励）
					// --满足余额够就返利，不受被邀请者充值多少钱影响--2016年8月4日14:58:22
					boolean hasRebateToInviter = rebateLog.getString("3") == null || "0".equals(rebateLog.getString("3")) ? false : true;
					if (!hasRebateToInviter) {
						logger.info("微信充值返利--返利给邀请者");
						String rechargeMoneyToInvite = walletNewService.rechargeMoneyToInvite(accountId, memberId,
								String.valueOf(money), "2", tradeNum);
					} else {
						logger.warn("已经给邀请者返利了, 不再返利");
					}
				}
			}
			// -------返利结束---------//
			
			String type = String.valueOf(JSON.parseObject(attach).get("type"));// 支付类型0充值1购物
			if (type.equals("0")) {// 充值业务
				logger.info("充值业务");
				if (result != null && !"".equals(result)) {// 充值业务
					if (JSON.parseObject(result).get("success").equals(true)) {
						return_code = "SUCCESS";
						return_msg = "OK";
					}
				}
			}
			
			pw.write(getReturnStr(return_code, return_msg));
		} catch (Exception e) {
			e.printStackTrace();
			pw.write(getReturnStr(return_code, return_msg));
		} finally {
			pw.close();
		}
	}

	private ReceiveMsg parseWechatCallbackMsg(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
	
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
		return receiveMsg;
	}
	
	private String getReturnStr(String returnCode, String returnMsg) {
		String xml = "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA["
				+ returnMsg + "]]></return_msg></xml>";
		
		logger.info("微信充值回调方法paycallback--同步告诉微信结果:" + xml);
		return xml;
	}
	
	/**
	 * 微信充值  返利给充值者
	 * @Method: rebateToRecharger
	 * @param accountId
	 * @param tradeNum
	 * @param telecomBook
	 * @param money
	 * @param rewardMap
	 * @param memberId
	 */
	private void rebateToRecharger(String accountId, String tradeNum, String telecomBook, double money,
			HashMap<String, Object> rewardMap, String memberId) {
		
		if(Double.parseDouble(telecomBook) >= Double.parseDouble(rewardMap.get("rewardMoney").toString())){
			//有活动规则 返利
			if ("1".equals(rewardMap.get("isHaveActivity").toString())) {
				//A.返利接口---返利给充值者
				String rechargeResult = WechatHttpClient.rechargeMoney(accountId,Double.parseDouble(rewardMap.get("rewardMoney").toString()),rewardMap.get("activityId").toString());
				if (!"true".equals(JSON.parseObject(rechargeResult).getString("success"))) {
					//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
					this.walletNewService.insertRechargeRebateInfo("2", memberId, accountId, String.valueOf(money), 
							PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "0","微信充值到通讯账户返利失败","0", 
									tradeNum);
					logger.info("微信充值回调方法paycallback，请求财务返利接口数据异常--本人充值奖励失败！");
				}else {
					//赠送奖励成功推送
					if(!PubMethod.isEmpty(memberId)){
						Map<String, String> smsMap = new HashMap<String, String>();
						smsMap.put("money",rewardMap.get("rewardMoney").toString());
						smsMap.put("memberId",memberId);
						openApiHttpClient.doPassSendStr("expressRegister/sendSmsByRecharge/", smsMap);
					}else {
						logger.info("微信充值回调方法paycallback--没有查到此人的memberId");
					}
					logger.info("微信充值回调方法paycallback--返利成功！");
					//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
					this.walletNewService.insertRechargeRebateInfo("2", memberId, accountId, String.valueOf(money), 
							PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "1","微信充值到通讯账户返利成功","0", 
									tradeNum);
				}
			}
		}else {
			logger.info("微信充值回调方法paycallback--财务系统通信账户余额不足，不返利！充值返利金额为："+rewardMap.get("rewardMoney").toString()+"，通信账户余额为telecomBook："+telecomBook+"");
			//插入充值返利信息-2016年7月9日13:45:18 by zhaohu
			this.walletNewService.insertRechargeRebateInfo("2", memberId, accountId, String.valueOf(money), 
					PubMethod.isEmpty(rewardMap.get("rewardMoney"))?"0.00":rewardMap.get("rewardMoney").toString(), "0","微信充值到通讯账户返利失败！原因：好递账户余额不足未返利！","1", 
							tradeNum);
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.controller.WechatPayBackController.java.setReceiveMsg 
	 * @Description: TODO(封装数据) 
	 * @param @param msg
	 * @param @param name
	 * @param @param value   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-2-29
	 * @auth zhaohu
	 */
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
//			msg.setOut_trade_no(value.substring(0, value.indexOf("D")));
			msg.setOut_trade_no(value);
		if (name.equals("time_end"))
			msg.setTime_end(value);
		if (name.equals("attach"))
			msg.setAttach(value);

	}
	/**
	 * 
	 * @MethodName: net.okdi.apiV3.controller.WechatPayBackController.java.financeCallBack 
	 * @Description: TODO(微信相关-财务系统回调publicapi) 
	 * @param @param type 1.微信充值 2.扫码支付
	 * @param @param money 订单金额（充值金额/支付金额）
	 * @param @param accountId 财务账号id
	 * @param @param state 微信返回的交易状态 0失败1成功
	 * @param @param tradeNo 外部交易号（不带D）
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-6-15
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/financeCallBack", method = { RequestMethod.POST, RequestMethod.GET })
	public void financeCallBack(String type,Double money,String accountId,String state,String tradeNo) {
		logger.info("微信相关financeCallBack--财务回调publicapi参数：type="+type+",money="+money+",accountId="+accountId+",state="+state+"，tradeNo="+tradeNo+" ");
		if(!PubMethod.isEmpty(type)){
			//1.微信充值
			if("1".equals(type)){
				this.wxRechargeBack(money, accountId, state);
			//2.扫码支付
			}else if("2".equals(type)) {
				if("1".equals(state)){
					state="SUCCESS";
				}else{
					state="FAIL";
				}
				try {
					paycallback2(tradeNo,state,state,String.valueOf(money));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public void paycallback2( String tradeNo,String returnCode,String result,String totalFee)
		    throws IOException
		  {
		    logger.info("微信支付异步回调okdilifeapi.weixin.paycallback2");
		    try {
		      
		      if ("SUCCESS".equals(returnCode)) {

		        if (JSON.parseObject(result).get("SUCCESS").equals(Boolean.valueOf(true))) {
		          logger.info("publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 20 ");

		          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "20");
		        } else {
		          logger.info("publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 21 ");
		          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "21");
		        }

		        logger.info("payNum>>>>>>>>>>>: " + tradeNo);
		        String result1 = this.receivePackageService.getTaskIdByOrderNum(tradeNo);
		        logger.info("根据orderNum查询到的result 数据 任务的 >>>>>>>>>>>: " + result1);
		        JSONObject parseObject = JSONObject.parseObject(result1);
		        JSONObject jsonObject = parseObject.getJSONObject("data");

		        String taskId = jsonObject.getString("uid");
		        String memberId = jsonObject.getString("actorMemberId");
		        String packageNum = jsonObject.getString("parEstimateCount");
		        String orderNum = jsonObject.getString("orderNum");//查询代收点取件微信支付的 id
		        String sign = "25";
		        if(!PubMethod.isEmpty(orderNum)){//不是空代表代收点微信支付
		        	sign = "21";
		        }
		        logger.info("根据微信订单号查询到的任务taskId 和 memberId和 phone ||||||||||||||||||||||| taskId" + taskId + " memberId: " + memberId);
		        this.receivePackageService.takeSendPackage(taskId, memberId, packageNum, totalFee, sign);
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
		 
	/**
	 * @MethodName: net.okdi.apiV3.controller.WechatPayBackController.java.wxRechargeBack 
	 * @Description: TODO(微信充值-财务系统回调publicapi) 
	 * @param @param money
	 * @param @param accountId
	 * @param @param flag   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-6-15
	 * @auth zhaohu
	 */
	public void wxRechargeBack(Double money,String accountId,String state) {
		logger.info("财务回调publicapi-wxRechargeBack，---进入wxRechargeBack方法");
		if(!PubMethod.isEmpty(state) && "1".equals(state)){
			//查询财务系统通讯账户余额
			String telecomBook = WechatHttpClient.queryTelecomBook();
			HashMap<String, Object> rewardMap = wechatPayBackService.getActivityRule(money);
			//有活动规则 返利
			if ("1".equals(rewardMap.get("isHaveActivity").toString())) {
				//财务系统通讯账户余额不足的时候不返利
				if(Double.parseDouble(telecomBook) >= Double.parseDouble(rewardMap.get("rewardMoney").toString())){
					//返利接口
					String rechargeResult = WechatHttpClient.rechargeMoney(accountId,Double.parseDouble(rewardMap.get("rewardMoney").toString()),rewardMap.get("activityId").toString());
					if (!"true".equals(JSON.parseObject(rechargeResult).getString("success"))) {
						throw new ServiceException("net.okdi.apiV3.controller.WechatPayBackController.wxRechargeBack","财务回调publicapi-wxRechargeBack，请求财务返利接口数据异常");
					}else { 
						//赠送奖励成功推送
						String memberId = WechatHttpClient.getMemberIdByAccountId(accountId);
						if(!PubMethod.isEmpty(memberId)){
							Map<String, String> smsMap = new HashMap<String, String>();
							smsMap.put("money",rewardMap.get("rewardMoney").toString());
							smsMap.put("memberId",memberId);
							openApiHttpClient.doPassSendStr("expressRegister/sendSmsByRecharge/", smsMap);
						}else {
							logger.info("没有查到此人的memberId");
						}
						logger.info("财务回调publicapi-wxRechargeBack--返利成功！");
					}
				}else {
					logger.info("财务回调publicapi-wxRechargeBack，财务系统通信账户余额不足，不返利！充值返利金额为："+rewardMap.get("rewardMoney").toString()+"，通信账户余额为telecomBook："+telecomBook);
				}
			}else {
				logger.info("财务回调publicapi-wxRechargeBack---运营平台没有符合条件的活动规则，不返利！");
			}
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getActivityRule", method = { RequestMethod.POST, RequestMethod.GET })
	public String getActivityRule() {
		HashMap<String,Object> activityRule = this.wechatPayBackService.getActivityRule(15D);
		return activityRule.toString();
	}
}




