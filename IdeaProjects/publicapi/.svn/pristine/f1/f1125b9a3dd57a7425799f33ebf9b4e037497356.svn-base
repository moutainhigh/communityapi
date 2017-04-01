package net.okdi.apiV3.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.okdi.apiV3.service.BillService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DateUtils;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.AbstractHttpClient;

@Service
public class BillServiceImpl extends AbstractHttpClient implements BillService{
	
	public static final Log logger = LogFactory.getLog(BillServiceImpl.class);
	
	@Value("${pay_url}")
	private String payUrl; //新版财务url
	@Value("${bank.picture.readPath}")
	private String logourl; //新版财务url
	@Autowired
	private WalletNewService walletNewService;//注入service
	
	@SuppressWarnings("unchecked")
	@Override
	public String querySmsBill(String accountId,String startTime,String endTime,String tradeCat,
			String platformId,String outerTradeType,String page,String rows,Short flag) throws ParseException{
		DecimalFormat  df   = new DecimalFormat("0.00");
		String url = payUrl;
		String methodName="ws/trade/query_trade_page";
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		String reMap = "";
		if(!PubMethod.isEmpty(tradeCat)){
			map.put("tradeCat",tradeCat);
		}
		if(!PubMethod.isEmpty(outerTradeType)){
			map.put("outerTradeType",outerTradeType);
		}
		if(flag==1){ //查询某一天的
			map.put("accountId", accountId);
			map.put("startTime", startTime);
			map.put("endTime", startTime);
			map.put("page", page);
			map.put("rows", rows);
			map.put("platformId","100002"); //平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
			String result = this.Post(url+methodName, map);
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String inCome="";
			String outCome="";
			List<String> listMonth=new ArrayList<String>();
			if(DateUtils.isToday(sim.parse(startTime+" 12:00:00"))){ //是当天
				map1.put("accountId", accountId);
				map1.put("startTime", startTime+" 00:00:00");
				map1.put("endTime", endTime+" 23:59:59");
				map1.put("tradeType", "0");
				String json = this.Post(url+"ws/account/getAccountTotal", map1);
				JSONObject json1=JSONObject.parseObject(json);
				if(json1.getBooleanValue("success")){
					inCome=JSONObject.parseObject(json1.getString("data")).getString("inCome");
				}else{
					throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.001", "查询总收入有误！");
				}
//				Date end=new Date(); //当天的话要查总支出只能查三个小时之前的，因为三个小时之内的是处于冻结状态的
//				Calendar c=Calendar.getInstance();
//				c.setTime(end);
//				c.add(Calendar.HOUR, -3);
//				end=c.getTime();
//				map1.put("endTime",sim.format(end));
				map1.put("tradeType","1");
				json = this.Post(url+"ws/account/getAccountTotal", map1);
				JSONObject jsons=JSONObject.parseObject(json);
				if(jsons.getBooleanValue("success")){
					outCome=JSONObject.parseObject(jsons.getString("data")).getString("outCome");
				}else{
					throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.002", "查询总支出有误！");
				}
			}else{ //不是当天
				map1.put("accountId", accountId);
				map1.put("startTime", startTime+" 00:00:00");
				map1.put("endTime", endTime+" 23:59:59");
				map1.put("tradeType", "0");
				String json = this.Post(url+"ws/account/getAccountTotal", map1);
				JSONObject json1=JSONObject.parseObject(json);
				if(json1.getBooleanValue("success")){
					inCome=JSONObject.parseObject(json1.getString("data")).getString("inCome");
				}else{
					throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.003", "查询总收入有误！");
				}
				map1.put("tradeType", "1");
				String jsons = this.Post(url+"ws/account/getAccountTotal", map1);
				JSONObject json2=JSONObject.parseObject(jsons);
				if(json2.getBooleanValue("success")){
					outCome=JSONObject.parseObject(json2.getString("data")).getString("outCome");
				}else{
					throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.004", "查询总支出有误！");
				}
			}
			String items="";
			JSONObject jsonResult=JSONObject.parseObject(result);
			if(jsonResult.getBooleanValue("success")){
				items=JSONObject.parseObject(jsonResult.getString("data")).getString("items");
			}else{
				throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.005", "查询账单列表有误！");
			}
			JSONArray jsona=JSONArray.parseArray(items);
			List<Map> list = JSON.parseArray(jsona.toString(),Map.class);
			Map<String, Object> map2 = new HashMap<String, Object>();
			List<Map> ls=new ArrayList<Map>();
			if(list.size()>0){
				for(Map mp:list){
					String outer_tid=mp.get("outer_tid").toString();
					String createTime=mp.get("created").toString();
					String outer_trade_type=mp.get("outer_trade_type").toString();
					String trade_status=mp.get("trade_status").toString();
					String seller_account_id = mp.get("seller_account_id").toString();
					String buyer_account_id = mp.get("buyer_account_id").toString();
					//status 1交易成功 2交易失败 3进行中
					String status=this.updateStatus(outer_trade_type, trade_status);
					String type=this.updateType(outer_trade_type);
					mp.put("type", type);
					if(outer_trade_type.equals("1002")){
						String bankName=mp.get("channel_name").toString();
						type="提现-"+bankName;
						String channelId=mp.get("channel_id").toString();
						String logo = logourl+"bank/"+channelId+".png";
						mp.put("logo", logo);
					}else{
						mp.put("logo", "");
					}
					mp.put("trade_status", status);
					String totalMoney = mp.get("total_amount").toString();
					String money = this.getMoney(outer_trade_type, totalMoney,accountId,seller_account_id,buyer_account_id);
					mp.put("tradeMoney", money);
					if("10010".equals(outer_trade_type) || "10012".equals(outer_trade_type) || "10025".equals(outer_trade_type)){
						HashMap<String, Object> queryCount = this.walletNewService.queryCount(outer_tid);
						mp.put("smsOrPhoneCount", queryCount.get("smsOrPhoneCount"));
						mp.put("weChatCount", queryCount.get("weChatCount"));
					}else if("10011".equals(outer_trade_type)){
						HashMap<String, Object> queryCountAll = this.walletNewService.queryCountAll(outer_tid);
						mp.put("phoneCount", queryCountAll.get("phoneCount"));
						mp.put("noticeCount", queryCountAll.get("noticeCount"));
						mp.put("weChatCount", queryCountAll.get("weChatCount"));
					}
					ls.add(mp);
				}
			}
			Map maps=new HashMap();
			maps.put("inCome",df.format(Double.parseDouble(inCome)));
			maps.put("outCome",df.format(Double.parseDouble(outCome)));
			maps.put("listName",startTime);
			listMonth.add(JSON.toJSONString(maps).toString());
			reMap="{\"items\":{\""+startTime+"\":"+JSON.toJSONString(ls).toString()+"},\"total\":"+ls.size()+",\"itemsName\":"+listMonth.toString()+",\"success\":true}";
//			reMap="{\"items\":"+JSON.toJSONString(map2).toString()+",\"itemsName\":"+listMonth.toString()+",\"success\":true}";
			//			reMap="{\"items\":"+items+",\"inCome\":"+inCome+",\"outCome\":"+outCome+",\"success\":true}";
			return reMap;
		}else{ //查询全部的
			Map<String, Object> map2 = new HashMap<String, Object>();
			map.put("accountId", accountId);
			if(!PubMethod.isEmpty(startTime)){
				map.put("startTime", startTime);
			}
			if(!PubMethod.isEmpty(endTime)){
				map.put("endTime", endTime);
			}	
			map.put("page", page);
			map.put("rows", rows);
			map.put("platformId","100002"); //平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
			String result = this.Post(url+methodName, map);
			String items="";
			String inCome="";
			String outCome="";
			JSONObject jsonResult=JSONObject.parseObject(result);
			if(jsonResult.getBooleanValue("success")){
				items=JSONObject.parseObject(jsonResult.getString("data")).getString("items");
			}else{
				throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.006", "查询账单列表有误！");
			}
			JSONArray jsona=JSONArray.parseArray(items);
			List<Map> list = JSON.parseArray(jsona.toString(),Map.class);
			List<Map> list1 = new ArrayList<Map>();
			int i=0;  // 定义一个常量 来那个什么 分情况
			int j=0;  // 定义一个常量 来那个什么 分情况
			String time1="";
			String time2="";
			List<String> listMonth=new ArrayList<String>();
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
			List<Map> ls=new ArrayList<Map>();
			if(list.size()>0){
				for(Map mp:list){
					j=j+1;
					String outer_tid=mp.get("outer_tid").toString();
					String createTime=mp.get("created").toString();
					String outer_trade_type=mp.get("outer_trade_type").toString();
					String trade_status=mp.get("trade_status").toString();
					String seller_account_id = mp.get("seller_account_id").toString();
					String buyer_account_id = mp.get("buyer_account_id").toString();
					String status=this.updateStatus(outer_trade_type, trade_status);
					String type=this.updateType(outer_trade_type);
					mp.put("type", type);
					if(outer_trade_type.equals("1002")){
						String bankName=mp.get("channel_name").toString();
						type="提现-"+bankName;
						String channelId=mp.get("channel_id").toString();
						String logo = logourl+"bank/"+channelId+".png";
						mp.put("logo", logo);
					}else{
						mp.put("logo", "");
					}
					mp.put("trade_status", status);
					String totalMoney = mp.get("total_amount").toString();
					String money = this.getMoney(outer_trade_type, totalMoney,accountId,seller_account_id,buyer_account_id);
					mp.put("tradeMoney", money);
					if("10010".equals(outer_trade_type) || "10012".equals(outer_trade_type) || "10025".equals(outer_trade_type)){
						HashMap<String, Object> queryCount = this.walletNewService.queryCount(outer_tid);
						mp.put("smsOrPhoneCount", queryCount.get("smsOrPhoneCount"));
						mp.put("weChatCount", queryCount.get("weChatCount"));
					}else if("10011".equals(outer_trade_type)){
						HashMap<String, Object> queryCountAll = this.walletNewService.queryCountAll(outer_tid);
						mp.put("phoneCount", queryCountAll.get("phoneCount"));
						mp.put("noticeCount", queryCountAll.get("noticeCount"));
						mp.put("weChatCount", queryCountAll.get("weChatCount"));
					}
					if(i==0){
						time1=createTime.substring(0,7);
						time2=createTime.substring(0,7);
						Date now=new Date();
						String nows=sim.format(now);
						if(time1.equals(nows.substring(0,7))){	
							map1.put("accountId", accountId);
							map1.put("startTime", createTime.substring(0,8)+"01 00:00:00");
							map1.put("endTime", nows);
							map1.put("tradeType", "0");
							String json = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject json1=JSONObject.parseObject(json);
							if(json1.getBooleanValue("success")){
								inCome=JSONObject.parseObject(json1.getString("data")).getString("inCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.007", "查询总收入有误！");
							}
//							Date end=sim.parse(nows); //当天的话要查总支出只能查三个小时之前的，因为三个小时之内的是处于冻结状态的
//							Calendar c=Calendar.getInstance();
//							c.setTime(end);
//							c.add(Calendar.HOUR, -3);
//							end=c.getTime();
//							map1.put("endTime",sim.format(end));
							map1.put("tradeType", "1");
							json = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject jsons=JSONObject.parseObject(json);
							if(jsons.getBooleanValue("success")){
								outCome=JSONObject.parseObject(jsons.getString("data")).getString("outCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.008", "查询总支出有误！");
							}
						}else{
							map1.put("accountId", accountId);
							map1.put("startTime", createTime.substring(0,8)+"01 00:00:00");
							String end=DateUtils.getLastDayOfMonth(sim.parse(createTime));
							map1.put("endTime",end.substring(0,11)+"23:59:59");
							map1.put("tradeType", "0");
							String json = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject json1=JSONObject.parseObject(json);
							if(json1.getBooleanValue("success")){
								inCome=JSONObject.parseObject(json1.getString("data")).getString("inCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.009", "查询总收入有误！");
							}
							map1.put("tradeType", "1");
							String jsons = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject json2=JSONObject.parseObject(json);
							if(json2.getBooleanValue("success")){
								outCome=JSONObject.parseObject(json2.getString("data")).getString("outCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.010", "查询总支出有误！");
							}
						}
						Map maps=new HashMap();
						maps.put("listName",time1);
						maps.put("inCome",df.format(Double.parseDouble(PubMethod.isEmpty(inCome)?"0.00":inCome )));
						maps.put("outCome",df.format(Double.parseDouble(PubMethod.isEmpty(outCome)?"0.00":outCome)));
						listMonth.add(JSON.toJSONString(maps).toString());
						ls.add(mp);
						i++;
						if (list.size() == 1) {
							map2.put(time1, ls);
						}
					}else{
						time2=createTime.substring(0,7);
						if(!time2.equals(time1)){
							map1.put("accountId", accountId);
							map1.put("startTime", createTime.substring(0,8)+"01 00:00:00");
							String end=DateUtils.getLastDayOfMonth(sim.parse(createTime));
							map1.put("endTime",end.substring(0,11)+"23:59:59");
							map1.put("tradeType", "0");
							String json = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject json1=JSONObject.parseObject(json);
							if(json1.getBooleanValue("success")){
								inCome=JSONObject.parseObject(json1.getString("data")).getString("inCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.011", "查询总收入有误！");
							}
							map1.put("tradeType", "1");
							String jsons = this.Post(url+"ws/account/getAccountTotal", map1);
							JSONObject json2=JSONObject.parseObject(jsons);
							if(json2.getBooleanValue("success")){
								outCome=JSONObject.parseObject(json2.getString("data")).getString("outCome");
							}else{
								throw new ServiceException("net.okdi.apiV3.service.impl.BillServiceImpl.querySmsBill.012", "查询总收入有误！");
							}
							Map maps=new HashMap();
							maps.put("listName",time2);
							maps.put("inCome",df.format(Double.parseDouble(inCome)));
							maps.put("outCome",df.format(Double.parseDouble(outCome)));
							System.out.println(time1+"==="+time2);
							listMonth.add(JSON.toJSONString(maps).toString());
							i++;
							List<Map> lss=new ArrayList<Map>();
							lss=ls;
							map2.put(time1+"",lss);
							time1=time2;
							ls=new ArrayList<Map>();
							ls.add(mp);
							map2.put(time2+"",ls);
						}else if(list.size()==j){
							List<Map> lss=new ArrayList<Map>();
							ls.add(mp);
							lss=ls;
							map2.put(time1,lss);
						}else{
							ls.add(mp);
						}
					}
				}
			}
			reMap="{\"items\":"+JSON.toJSONString(map2).toString()+",\"itemsName\":"+listMonth.toString()+",\"success\":true}";
			return reMap;
		}
	}
//	10010 群发通知-仅短信
//	10011 群发通知-电话优先
//	10012 群发通知-仅电话
//	10013 拨打电话
//	10014 充值-余额充值到通讯费(通信账户充值-余额支付)
//	10015 充值-微信充值到通讯费(通信账户充值-微信支付)
//	10016 平台赠送-注册(财务通讯账户-->个人现金账户)
//	10017 平台赠送-充值(财务通讯账户-->个人通讯账户)
//	10018 平台赠送-邀请(财务通讯账户-->个人现金账户)
//	10019 平台赠送-新手体验金(财务通讯账户-->个人通讯账户)
//	10020 平台赠送-好友充值返利
//	10021 平台赠送－关注好递用户（通信费账户）
//	10023 平台赠送－关注好递用户（现金余额账户）
//	10022 群发通知-短信+群呼
//	1002  提现
//	1006  平台赠送-邀请爱购猫用户
//	10025 扫单通知-短信
	private String updateStatus(String outer_trade_type,String tradeStatus){
		//1交易成功 2交易失败 3进行中4取消支付
		String statu="";
		//交易说明  交易状态
		if("10010".equals(outer_trade_type)||"10011".equals(outer_trade_type)||"10012".equals(outer_trade_type)||"10013".equals(outer_trade_type)
				||"10022".equals(outer_trade_type) || "10025".equals(outer_trade_type)){//群发通知-……
			if(tradeStatus.equals("700")){
				statu= "3";
			}else if(tradeStatus.equals("710")){
				statu= "1";
			}
		}else if ("10014".equals(outer_trade_type)||"10015".equals(outer_trade_type)) {
			if(tradeStatus.equals("200")||tradeStatus.equals("210")){
				statu= "3";
			}else if(tradeStatus.equals("220")){
				statu= "1";
			}else if(tradeStatus.equals("230")) {
				statu= "2";
			}else if(tradeStatus.equals("240")) {
				statu= "4";
			}else if(tradeStatus.equals("250")) {
				statu= "5";
			}
		}else if ("10016".equals(outer_trade_type)||"10017".equals(outer_trade_type)||"10018".equals(outer_trade_type)||"10019".equals(outer_trade_type)||"1006".equals(outer_trade_type)) {
			if(tradeStatus.equals("410")){
				statu= "1";
			}else {
				statu= "2";
			}
		}else if ("10020".equals(outer_trade_type) || "10021".equals(outer_trade_type) || "10023".equals(outer_trade_type)) {
			if(tradeStatus.equals("410")){
				statu= "1";
			}else {
				statu= "2";
			}
		}else if ("1002".equals(outer_trade_type)) {
			if(tradeStatus.equals("180")){
				statu= "1";
			}else if(tradeStatus.equals("120") || tradeStatus.equals("150")|| tradeStatus.equals("190")){
				statu= "2";
			}else {
				statu= "3";
			}
			//2016年5月12日17:07:03 by hu.zhao   收派员收款-寄件----快递费
		}else if("200001".equals(outer_trade_type)){  //购物支付交易状态（0--99） 0 待支付 10支付中 20买家支付成功待签收 30签收成功 40 支付失败 50 取消支付（未付款成功）60退款 前端页面展示 交易失败（支付成功退款未签收）
			if(tradeStatus.equals("10")||tradeStatus.equals("20")||tradeStatus.equals("0")){
				statu= "3";
			}else if(tradeStatus.equals("30")){
				statu= "1";
			}else if(tradeStatus.equals("40")) {
				statu= "2";
			}else if(tradeStatus.equals("50")) {
				statu= "4";
			}else if(tradeStatus.equals("51")) {
				statu= "5";
			}//1交易成功 2交易失败 3进行中4取消支付5交易关闭
		}
		return statu;
}
	private String updateType(String outer_trade_type){
		String type="";
		//交易说明  交易状态
		if("10010".equals(outer_trade_type)){//群发通知-仅短信
			type="群发通知-仅短信";
		}else if ("10011".equals(outer_trade_type)) {
			type="群发通知-群呼优先";
		}else if ("10012".equals(outer_trade_type)) {
			type="群发通知-仅群呼";
		}else if ("10013".equals(outer_trade_type)) {
			type="拨打电话";
		}else if ("10014".equals(outer_trade_type)) {
			type="通信账户充值-余额支付";
		}else if ("10015".equals(outer_trade_type)) {
			type="通信账户充值-微信支付";
		}else if ("10016".equals(outer_trade_type)) {
			type="平台赠送-注册";
		}else if ("10017".equals(outer_trade_type)) {
			type="平台赠送-充值";
		}else if ("10018".equals(outer_trade_type)) {
			type="平台赠送-邀请";
		}else if ("10019".equals(outer_trade_type)) {
			type="平台赠送-新手体验金";
		}else if ("10020".equals(outer_trade_type)) {
			type="平台赠送-好友充值返利";
		}else if ("10021".equals(outer_trade_type)) {
			type="平台赠送-关注好递（通信费）";
		}else if ("10023".equals(outer_trade_type)) {
			type="平台赠送-关注好递（现金）";
		}else if ("10022".equals(outer_trade_type)) {
			type="群发通知-短信+群呼";
		}else if ("1002".equals(outer_trade_type)) {
			type="提现";
		}else if ("200001".equals(outer_trade_type)) {
			type="快递费";
		}else if ("1006".equals(outer_trade_type)) {
			type="平台赠送-邀请爱购猫用户";
		}else if ("10025".equals(outer_trade_type)) {
			type="扫单通知-短信";
		}
		return type;
	}
	
	public String getMoney(String tradeType,String money,String accountId,String seller_account_id,String buyer_account_id) {
		DecimalFormat df = new DecimalFormat("0.00");
		money = df.format(Double.parseDouble(money));
		String newMoney = "";
		if ("10014".equals(tradeType) || "10015".equals(tradeType) || "10016".equals(tradeType) ||"10017".equals(tradeType) || "10018".equals(tradeType) 
				|| "10019".equals(tradeType) || "10020".equals(tradeType) || "10021".equals(tradeType) || "10023".equals(tradeType)|| "1006".equals(tradeType)) {
			newMoney = "+"+money;
		}else if("10010".equals(tradeType) ||"10011".equals(tradeType) ||"10012".equals(tradeType) ||"10013".equals(tradeType) || "1002".equals(tradeType)
				||"10022".equals(tradeType) || "10025".equals(tradeType)){
			newMoney = "-"+money;
		}else if ("200001".equals(tradeType)) {
			if(seller_account_id.equals(accountId)){
				newMoney= "+"+money;
			}else if (buyer_account_id.equals(accountId)) {
				newMoney= "-"+money;
			}
		}
		return newMoney;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
