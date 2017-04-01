package net.okdi.core.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.net.httpserver.Authenticator.Success;

import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.ShortLinkHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.httpClient.SmsHttpClient;

public class NewBaseService_jdw extends AbstractHttpClient {
	final String SMSSTATUS = "001";
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ConstPool constPool; 
	
	Logger logger = Logger.getLogger(NewBaseService_jdw.class);
	  SerializerFeature [] s = {SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteDateUseDateFormat};
	public Map<String, Object> SetCompPersmap(Long memberId ,Long compId,String memberName,short roleId,String memberPhone,String applicationDesc){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("memberName",memberName);
		paraMeterMap.put("roleId",roleId);
		paraMeterMap.put("memberPhone",memberPhone);
		paraMeterMap.put("applicationDesc",applicationDesc);
		return paraMeterMap;
	}
	
	public Map<String, Object>  SetMemberTaskMap(String senderName, String startTime, String endTime, String senderPhone,
			String spacetime, Long operatorCompId, Page page){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("senderName",senderName);
		paraMeterMap.put("startTime",startTime);
		paraMeterMap.put("endTime",endTime);
		paraMeterMap.put("senderPhone",senderPhone);
		paraMeterMap.put("spacetime",spacetime);
		paraMeterMap.put("operatorCompId",operatorCompId);
		paraMeterMap.put("page",page);
		return paraMeterMap;
	}
	
	public Map<String,Object> SetsaveCompMap(Long memberId, Long netId, String compTypeNum,
			String compName, String compTelephone, Long addressId,
			String address){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("compTypeNum",compTypeNum);
		paraMeterMap.put("compName",compName);
		paraMeterMap.put("compTelephone",compTelephone);
		paraMeterMap.put("addressId",addressId);
		paraMeterMap.put("address",address);
		return paraMeterMap;
	}
	
	public Map<String,Object> saveOrUpdateCompMap(	Long loginMemberId,
			Long loginCompId, Long netId, String compTypeNum, Long useCompId,
			String compName, String compTelephone, Long addressId,
			String address, Double longitude, Double latitude,
			Short compRegistWay){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("loginMemberId",loginMemberId);
		paraMeterMap.put("loginCompId",loginCompId);
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("compTypeNum",compTypeNum);
		paraMeterMap.put("useCompId",useCompId);
		paraMeterMap.put("compName",compName);
		paraMeterMap.put("compTelephone",compTelephone);
		paraMeterMap.put("addressId",addressId);
		paraMeterMap.put("address",address);
		paraMeterMap.put("longitude",longitude);
		paraMeterMap.put("latitude",latitude);
		return paraMeterMap;
	}
	
	public Map<String,Object> doParTaskrecordMap(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
				String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("fromCompId",fromCompId);
		paraMeterMap.put("fromMemberId",fromMemberId);
		paraMeterMap.put("toCompId",toCompId);
		paraMeterMap.put("toMemberId",toMemberId);
		paraMeterMap.put("coopNetId",coopNetId);
		paraMeterMap.put("appointTime",appointTime);
		paraMeterMap.put("appointDesc",appointDesc);
		paraMeterMap.put("actorMemberId",actorMemberId);
		paraMeterMap.put("contactName",contactName);
		paraMeterMap.put("contactMobile",contactMobile);
		paraMeterMap.put("contactTel",contactTel);
		paraMeterMap.put("contactAddressId",contactAddressId);
		paraMeterMap.put("contactAddress",contactAddress);
		paraMeterMap.put("customerId",customerId);
		paraMeterMap.put("createUserId",createUserId);
		paraMeterMap.put("contactAddrLongitude",contactAddrLongitude);
		paraMeterMap.put("contactAddrLatitude",contactAddrLatitude);
		return paraMeterMap;
	}
	
	//查询收派员自取件任务
	public Map<String,Object> SetQueryMyTask(String senderName, String startTime,String endTime, String senderPhone, String spacetime,
			Byte taskStatus, Long memberId, Long compId, Long operatorCompId,
			Integer currentPage, Integer pageSize){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("senderName",senderName);
		paraMeterMap.put("startTime",startTime);
		paraMeterMap.put("endTime",endTime);
		paraMeterMap.put("senderPhone",senderPhone);
		paraMeterMap.put("spacetime",spacetime);
		paraMeterMap.put("taskStatus",taskStatus);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("operatorCompId",operatorCompId);
		paraMeterMap.put("currentPage",currentPage);
		paraMeterMap.put("pageSize",pageSize);
		return paraMeterMap;
	}
	
	
	
	public Map<String,Object> saveOnLineMemberMap(Long id, Long netId, String netName,
			Long compId, String compName, Long memberId, String memberName,
			String memberMobile, Double lng, Double lat, String memo){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("id",id);
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("netName",netName);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("compName",compName);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("memberName",memberName);
		paraMeterMap.put("memberMobile",memberMobile);
		paraMeterMap.put("lng",lng);
		paraMeterMap.put("lat",lat);
		paraMeterMap.put("memo",memo);
		return paraMeterMap;
	}
	
	
	public Map<String,Object> addMemberInfoMap(Long compId, String associatedNumber,
			String memberName, Short roleId, String areaColor, Long userId,
			Short memberSource){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("associatedNumber",associatedNumber);
		paraMeterMap.put("memberName",memberName);
		paraMeterMap.put("roleId",roleId);
		paraMeterMap.put("userId",userId);
		paraMeterMap.put("memberSource",memberSource);
		return paraMeterMap;
	}
	
	
	public  Map<String,Object> SetUserUpdateMap(Long memberId, Long telephone, String idNum,
			String username, String adderssName, String address,
			Long countryId, Long addressId, String zip){
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("telephone",telephone);
		paraMeterMap.put("idNum",idNum);
		paraMeterMap.put("username",username);
		paraMeterMap.put("adderssName",adderssName);
		paraMeterMap.put("address",address);
		paraMeterMap.put("countryId",countryId);
		paraMeterMap.put("addressId",addressId);
		paraMeterMap.put("zip",zip);
		return paraMeterMap;
	}
	
	public String queryTaskDetailHandle(Map<String,Object> ResultMeterMap){
		if(ResultMeterMap.get("success").toString().equals("true")){
			if(!PubMethod.isEmpty(ResultMeterMap) && !PubMethod.isEmpty(ResultMeterMap.get("data"))){
				Map<String,Object> dataMap = JSONObject.parseObject(ResultMeterMap.get("data").toString());
				dataMap.remove("resultlist");
				String taskinfo =  dataMap.get("taskinfo").toString();
				Map<String,Object> map =  castFunction(taskinfo);
				String [] StrArr =  map.get("contactAddress").toString().replace("-","").split(" ");
				map.put("contactAddress", StrArr[0]);
				map.put("contactAddressMsg", StrArr[1]);
				return jsonSuccess(map);
			}
		}
		return JSON.toJSONString(ResultMeterMap);
	}
	
	public String changeValue(Map<String,Object> ResultMeterMap){
		//在查询所有公司列表时
//		String jsonString = JSON.toJSONString(reSlutMap);
//		String regex = "\"[A-Z]\":\\[";
//		String regex2="\"OTHER\":\\[";
//		String regex3="\"HOT\":\\[";      
//				jsonString
//				.replaceAll(regex, "")
//				.replaceAll(regex2, "")
//				.replaceAll(regex3,"")
//				.replaceAll("\\]", "")
//				.replace("\"data\":{","\"data\":[").replace("}}", "}]");
		Map<String,Object> returnJsonMap = new HashMap<String,Object>();
		List<Object> returnJsonList = new ArrayList<Object>();
		if(null != ResultMeterMap){
 		Set<Entry<String, Object>> set = ResultMeterMap.entrySet();
        for (Iterator<Entry<String, Object>> it = set.iterator(); it.hasNext();) {
          Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
          if(!entry.getKey().equals("success")){
        	  Map result = JSONObject.parseObject(entry.getValue().toString());
        		Set<Entry<String, Object>> reSet = result.entrySet();
        		 for (Iterator<Entry<String, Object>> iter = reSet.iterator(); iter.hasNext();) {
        			 Map.Entry<String, Object> entryMap = (Map.Entry<String, Object>) iter.next();
        			 List<Map> json2list = openApiHttpClient.json2list(entryMap.getValue().toString());
        			 if(null != json2list){
        				 for (Map SouceMap : json2list) {
        					 SouceMap.put("url",constPool.getReadPath()+constPool.getNet()+SouceMap.get("netId")+".png");
        					 returnJsonList.add(SouceMap);
        				 }
        			 }
        		 }
        		 returnJsonMap.put("data",returnJsonList);
	         } else {
	        	 returnJsonMap.put("success",entry.getValue());
	         }
        }
		} else {
			return JSON.toJSONString(ResultMeterMap);
		}
//        		System.out.println(JSON.toJSONString(returnJsonMap));
				return JSON.toJSONString(returnJsonMap);
	} 
	//获取短连接                                                                        
//	public String getOkdiShortLink(String sendAddress,String netNames,String sendLon,String sendLat,String waybillNum,String prcUrl){
//		     PubMethod pubMethod = new PubMethod();
//		    ShortLinkHttpClient linkClient = new ShortLinkHttpClient();	
//			String okdiUrl = constPool.getOkdiHttpUrl();//访问网站页面
//				String shortLinkUrl = constPool.getShortlinkHttpUrl();//获取短链接KEY
//				String longLinkUrl = constPool.getLonglinkHttpUrl();//短网址调转到长网址
//		 String params = "";		 
//		 pubMethod.escape(sendAddress);
//		 params = "sendAddress="+ pubMethod.escape(sendAddress)+"" +
//			 		"&sendLon="+sendLon+"" +
//			 		"&sendLat="+sendLat+"" +
//			 		"&netName="+pubMethod.escape(netNames)+"" +
//			 		"&waybillNum="+waybillNum+"" +
//			 		"&prcUrl="+prcUrl+"";
//        Map mapLink = new HashMap();
//        mapLink.put("sys", "1");
//        mapLink.put("text", okdiUrl+"?"+params);
//        String shortKey = linkClient.doPost(shortLinkUrl, mapLink);
//		return longLinkUrl+shortKey;
//	}
private String getOkdiShortLink(String expLon,String expLat,String expMob){
		/*if(PubMethod.isEmpty(expLon)||PubMethod.isEmpty(expLat)){
			return null;
		}*/
		ShortLinkHttpClient linkClient = new ShortLinkHttpClient();	
		String okdiUrl = constPool.getOkdiHttpUrl();//访问网站页面
		String shortLinkUrl = constPool.getShortlinkHttpUrl();//获取短链接KEY
		String longLinkUrl = constPool.getLonglinkHttpUrl();//短网址调转到长网址
//		String params = "sendLon=" + expLon + "" + "&sendLat=" + expLat + "" + "&sendMob=" + expMob;
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
//		mapLink.put("text", okdiUrl + "?" + params);
		mapLink.put("text", okdiUrl);
		String shortKey = linkClient.doPost(shortLinkUrl, mapLink);
		
		return longLinkUrl + shortKey;
	}
//	public String SetSmsContent(String  usePhones,String  BasicContent,String sendAddress,String netNames,
//								String sendLon,String sendLat,String waybillNum,String prcUrl ,String memberPhone,String taskId) {
//		List  paramList = new ArrayList();
//		StringBuffer SB = null;
//		 Map<String,String> contentMap = null;
//		if(isempty(usePhones) && isempty(waybillNum) && isempty(taskId)){
//			String usePhoneArr [] = usePhones.split(",");
//			String waybillNumArr [] = waybillNum.split(",");
//			String taskIdArray [] =taskId.split(",");
////			模板为：自定义信息后跟[XX快递公司][单号：890723409][联系电话:13584565242]，请查看包裹位置详情链接；		
//			int i = 0;
//			if(iscount(waybillNumArr,usePhoneArr) && iscount(taskIdArray,usePhoneArr)){
//				for (String phone : usePhoneArr) {
//					SB =  new StringBuffer();
//					contentMap = new HashMap();
//					contentMap.put("id",taskIdArray[i]);
//					contentMap.put("usePhone",phone);
//					SB.append(BasicContent);
//					SB.append(" [");
//					SB.append(netNames);
//					SB.append("快递公司][单号:");
//					SB.append(waybillNumArr[i]);
//					SB.append("][联系电话:");
//					SB.append(memberPhone);
//					SB.append("],请查看包裹位置详情链接:");
//					SB.append(getOkdiShortLink(sendAddress, netNames, sendLon, sendLat, waybillNum, prcUrl));
//					contentMap.put("content",SB.toString());
//					paramList.add(contentMap);
//					i++;
//				  }
//			}
//		} 
//		System.out.println(JSON.toJSONString(paramList));
//		return JSON.toJSONString(paramList);
//	}
public String SetSmsContent(String  usePhones,String  BasicContent,String netNames,
		String waybillNum,String memberPhone,String expLat,String expLon) {
			StringBuffer SB = new StringBuffer();
			List<String> parList = new ArrayList<String>(); 
			if(isempty(usePhones)){
			String usePhoneArr [] = usePhones.split(",");
			//每一个用户的一串单号用|分隔
			String waybillNumArr [] = waybillNum.split("[|]");
//			自定义信息后跟[XX快递公司][单号：890723409][联系电话:13584565242]， 请查看包裹位置详情 链接 ；
				int i = 0;
				for (String phone : usePhoneArr) {
					SB.append(phone);//13161449048|lllnnn^13164
					SB.append("|");
					SB.append(BasicContent);
					//edit by zmn 2015/2/28 12:52
//					SB.append(" [");
//					SB.append(netNames);
//					SB.append("快递公司] ");
////					String [] waybillNumArr  = waybillNum.split("[|]");
//					SB.append("[单号:");
//					if(i<waybillNumArr.length){
//						SB.append(waybillNumArr[i].toString());
//					}
//					SB.append("] ");
//					SB.append("[联系电话:");
//					SB.append(memberPhone);
					//edit by zmn 2015/2/28 12:52
					//SB.append(" 包裹当前位置：");
					SB.append("发快递用好递:");
					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));
					if(usePhoneArr.length-1 != i){
						SB.append("^&");
					}
					i++;
				}
				String [] stringResult =  SB.toString().split("&");
				Collections.addAll(parList, stringResult);
				HashSet<String> h = new HashSet<String>(parList);  
				parList.clear();
				parList.addAll(h);
	} 
			StringBuilder sb = new StringBuilder();
			for (String string : parList) {
				sb.append(string);
			}
			return JSON.toJSONString(sb.toString());
}
	public String SetSmsContent(String smsTemplate,String phoneAndWaybillNum,String memberPhone,String expLon,String expLat) {
			StringBuffer SB = new StringBuffer();
			Map<String,String> map = new HashMap<String,String>();
//			Pattern p = Pattern.compile("^((1[3,8]\\d)|(15[^4,\\D])|(17[0,5-8])|(14[5,7,9]))\\d{8}$");
			Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //取消严格校验，与手机端校验保持一致
			if(isempty(phoneAndWaybillNum)){
				System.out.println("**************phoneAndWaybillNum:"+phoneAndWaybillNum);
			String phoneAndWaybillNumArr [] = phoneAndWaybillNum.split("[|]");
				int i = 0;
				for (String temp : phoneAndWaybillNumArr) {
					if(PubMethod.isEmpty(temp)){
						continue;
					}
					System.out.println("***********temp执行:"+temp);
					Matcher m = p.matcher(temp.split("-")[0].trim()); 
					if(!m.matches()){
						return null; 
					}
					
					if(map.containsKey(temp)){
						if(phoneAndWaybillNumArr.length-1 == i){
							SB.substring(0, SB.length()-1);
						}
						continue;
					}
					String content = smsTemplate;
					content = content.replaceAll("[^\\u0000-\\uFFFF]","");
					content = temp.split("-").length>1 && !"".equals(temp.split("-")[1].trim())? content.replace("#运单号#", "运单号:"+temp.split("-")[1].trim()) : content.replace("#运单号#", "");
					content = temp.split("-").length>2 && !"".equals(temp.split("-")[2].trim())? content.replace("#编号#", "编号:"+temp.split("-")[2].trim()) : content.replace("#编号#", "");
					map.put(temp, null);
					SB.append(temp.split("-")[0].trim());
					SB.append("|");
					/*SB.append("比价发快递请下载：");
					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));
					SB.append("， ");*/
					SB.append(content);
//					SB.append(" 发快递用好递:");
//					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));
					//SB.append(" 超市商品,快捷上门：");
					
					
					
					/*SB.append("电话:"+memberPhone+" 便利店上门送货:");
					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));*/
					
					/*SB.append("电话:"+memberPhone+" 查快递，发快递，超市购物找便宜，就用好递生活，送您10元超市大红包，点击 ");
					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));
					SB.append(" 马上领取！");*/
					
					/*SB.append("电话:"+memberPhone+" 还想接着买买买？送你10元超市红包继续购，点此链接直接领取 ");
					SB.append(getOkdiShortLink( expLon, expLat, memberPhone));*/
					
					
//					SB.append(" 电话:"+memberPhone);
					/*if("北京".equals(openApiHttpClient.getPhoneCity(temp.split("-")[0].trim()))){
						SB.append("，10元超市红包送你啦，窝家也能逛超市！点击领用  ");
						SB.append(getOkdiShortLink( expLon, expLat, memberPhone));
					}*/
					
					/******加上  回TD退订********/
//					SB.append(" 短信可回.关注好递");  //切换新通道暂时去掉
					/******加上  回TD退订********/
					
					if(phoneAndWaybillNumArr.length-1 != i){
						SB.append("^");
					}
					i++;
				}
			} 
			//System.out.println(SB.toString());
			if(PubMethod.isEmpty(SB.toString())){
				return null; 
			}
			return JSON.toJSONString(SB.toString());
	}
	
	public String SetSmsContentNew(String smsTemplate,String phoneAndWaybillNum,String memberPhone) {
		Map<String,String> map = new HashMap<String,String>();
		List<Map> resultList = new ArrayList<Map>();
		Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //取消严格校验，与手机端校验保持一致
		if(isempty(phoneAndWaybillNum)){
			//电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|
			System.out.println("**************phoneAndWaybillNum:"+phoneAndWaybillNum);
		String phoneAndWaybillNumArr [] = phoneAndWaybillNum.split("[|]");
		logger.info("切割后的数组为phoneAndWaybillNumArr: "+phoneAndWaybillNumArr[0]);
		Long i=0l;
		Map<String,Object> errorMap = new HashMap<String,Object>();
		List<Map> list = new ArrayList<Map>();
		for (String temp : phoneAndWaybillNumArr) {
				if(PubMethod.isEmpty(temp)){
					continue;
				}
				System.out.println("***********temp执行:"+temp);
				Matcher m = p.matcher(temp.split("-")[0].trim()); 
				if(!m.matches()){
					logger.info("你录入的该 "+temp.split("-")[0]+" 号码有误.....................");
					errorMap.put("success",false);
					errorMap.put("cause", "你录入的此 "+temp.split("-")[0]+" 的号码有误,请修改此号码!!!");
					errorMap.put("isCharge","2");
					list.add(errorMap);
					String jsonString = JSON.toJSONString(list);
					return jsonString; 
				}
				//phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";
				String content = smsTemplate;
				content = content.replaceAll("[^\\u0000-\\uFFFF]","");
				content = temp.split("-").length>2 && !"".equals(temp.split("-")[2].trim())? content.replace("#编号#", "取件码"+temp.split("-")[2].trim()) : content.replace("#编号#", "");
				map.put(temp, null);
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("realContent", content);
				resultMap.put("mobile", temp.split("-")[0].trim()+"-"+(temp.split("-").length>2?temp.split("-")[2]:""));
				resultMap.put("content", content);
				resultMap.put("isWx", temp.split("-")[1].trim()); //是否关注微信
				
				resultMap.put("name", ""); //姓名
				resultMap.put("parcelId", ""); //包裹id
				resultMap.put("firstMsgId", ""); //firstMsgId
				//15810885211-0-2-1-12121121212121212-不晓得1-zj--
				resultMap.put("wayBill", temp.split("-")[4].trim()); //运单号
				resultMap.put("netName", temp.split("-")[5].trim()); //网络名称
				if((temp.split("-").length)>4){
					resultMap.put("wayBill", temp.split("-")[4].trim()); //运单号
					if((temp.split("-").length)>5){
						resultMap.put("netName", temp.split("-")[5].trim()); //网络名称
					}
					logger.info("111111111: "+resultMap);
				}
				if((temp.split("-").length)>6){
					resultMap.put("name", temp.split("-")[6].trim()); //姓名
					if((temp.split("-").length)>7){
						resultMap.put("parcelId", temp.split("-")[7].trim());//包裹id
					}
					logger.info("2222222: "+resultMap);
				}
				if((temp.split("-").length)>8){
					resultMap.put("firstMsgId", temp.split("-")[8].trim());//firstMsgId
					logger.info("33333333: "+resultMap);
				}
				if((temp.split("-").length)>3){
					resultMap.put("isCharge",PubMethod.isEmpty(temp.split("-")[3])?"0":temp.split("-")[3]); // 1.走余额 0.先走免费
				}else{
					resultMap.put("isCharge", "1"); //聊天全部走余额
				}
				i++;
				logger.info("resultMap: "+resultMap.toString());
				resultList.add(resultMap);
			}	
		} 
		logger.info("JSON.toJSONString(resultList): "+JSON.toJSONString(resultList));
		return JSON.toJSONString(resultList);
}
	public String SetSmsContentThree(String smsTemplate,String phoneAndWaybillNum) {
		List<Map> resultList = new ArrayList<Map>();
		if(isempty(phoneAndWaybillNum)){
			System.out.println("***********阿里大于***phoneAndWaybillNum:"+phoneAndWaybillNum);
			String phoneAndWaybillNumArr [] = phoneAndWaybillNum.split("\\|");
			Map<String,Object> errorMap = new HashMap<String,Object>();
			List<Map> list = new ArrayList<Map>();
			for (String temp : phoneAndWaybillNumArr) {
				if(PubMethod.isEmpty(temp)){
					continue;
				}
				System.out.println("*********阿里大于**temp执行:"+temp);
				//waybill+"-"+num+"-"+arrs[j].split("-")[2]   运单号-编号-netId
				String content = smsTemplate;
				content = content.replaceAll("[^\\u0000-\\uFFFF]","");
				//content = temp.split("-").length>2 && !"".equals(temp.split("-")[2].trim())? content.replace("#编号#", "编号"+temp.split("-")[2].trim()) : content.replace("#编号#", "");
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("realContent", content);
				resultMap.put("waybill", temp.split("-")[0]+"-"+temp.split("-")[1]);
				//content = temp.split("-").length>2 && !"".equals(temp.split("-")[2].trim())? content.replace("#编号#", "编号"+temp.split("-")[2].trim()) : content.replace("#编号#", "");
				resultMap.put("content", temp.split("-").length>2 && !"".equals(temp.split("-")[1].trim())? content.replace("#编号#", "取件码"+temp.split("-")[1].trim()) : content.replace("#编号#", ""));
				resultMap.put("netId", temp.split("-")[2].trim());
				resultMap.put("netName", temp.split("-")[3].trim());
				resultMap.put("isCharge", PubMethod.isEmpty(temp.split("-")[4].trim()) ? "0":temp.split("-")[4].trim());//如果是空免费
				logger.info("阿里大于resultMap: "+resultMap.toString());
				resultList.add(resultMap);
			}	
		} 
		logger.info("阿里大于 JSON.toJSONString(resultList): "+JSON.toJSONString(resultList));
		return JSON.toJSONString(resultList);
	}
	
	public String SetSmsContentNewTwo(String smsTemplate,String phoneAndWaybillNum,String memberPhone,String expLon,String expLat) {
		Map<String,String> map = new HashMap<String,String>();
		List<Map> resultList = new ArrayList<Map>();
		Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //取消严格校验，与手机端校验保持一致
		if(isempty(phoneAndWaybillNum)){
			System.out.println("**************phoneAndWaybillNum:"+phoneAndWaybillNum);
		String phoneAndWaybillNumArr [] = phoneAndWaybillNum.split("[|]");
			int i = 0;
			for (String temp : phoneAndWaybillNumArr) {
				if(PubMethod.isEmpty(temp)){
					continue;
				}
				System.out.println("***********temp执行:"+temp);
				Matcher m = p.matcher(temp.split("-")[0].trim()); 
				if(!m.matches()){
					return null; 
				}
				
				/*if(map.containsKey(temp)){
					continue;
				}*/
				String content = smsTemplate;
				content = content.replaceAll("[^\\u0000-\\uFFFF]","");
				content = temp.split("-").length>1 && !"".equals(temp.split("-")[1].trim())? content.replace("#运单号#", "运单号:"+temp.split("-")[1].trim()) : content.replace("#运单号#", "");
				content = temp.split("-").length>2 && !"".equals(temp.split("-")[2].trim())? content.replace("#编号#", "编号"+temp.split("-")[2].trim()) : content.replace("#编号#", "");
				map.put(temp, null);
				/*content+="电话:"+memberPhone+" 还想接着买买买？送你10元超市红包继续购，点此链接直接领取 ";
				content+=getOkdiShortLink( expLon, expLat, memberPhone);*/
//				content+=" 电话:"+memberPhone;
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("realContent", content);
				/*if("北京".equals(openApiHttpClient.getPhoneCity(temp.split("-")[0].trim()))){
					content+="，10元超市红包送你啦，窝家也能逛超市！点击领用 ";
					content+=getOkdiShortLink( expLon, expLat, memberPhone);
				}*/
//				content+=" 可回，屏蔽短信回1";    //切换新通道暂时去掉
				resultMap.put("mobile", temp.split("-")[0].trim());
				resultMap.put("content", content);
				resultList.add(resultMap);
			}
		} 
		return JSON.toJSONString(resultList);
}
/** 
 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
 * <dt><span class="strong">时间:</span></dt><dd>2015-2-28 下午12:53:54</dd>
 * @since v1.0
 */
public static void main(String[] args) {
	NewBaseService_jdw bs = new NewBaseService_jdw();
	bs.SetSmsContent("13161340623,13161340623,13161340623", "张梦楠测试内容", "", "", "", "0.0", "0.0");
}
	public String reviseResult(Map<String,Object> ResultMap){
		List<Object> ResultList = new ArrayList<Object>();
		Map<String,Object> m = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(ResultMap)){
			 String str = ResultMap.get("data").toString().replace("=",":");
			 Map<String,Object> map =  JSON.parseObject(str);
			 if(!PubMethod.isEmpty(map) &&!PubMethod.isEmpty(map.get("resultlist"))){
				  String valueStr =  map.get("resultlist").toString();
				   List valueList =   JSONArray.parseArray(valueStr);
				 if(!PubMethod.isEmpty(valueList) && valueList.size() > 0){
					 for(int i = 0 ; i < valueList.size() ; i++){
						 StringBuffer SB = new StringBuffer();
						 String  StrValue = valueList.get(i).toString();
						 Map<String,Object> mapvalue =  JSON.parseObject(StrValue);
						 if(!PubMethod.isEmpty(mapvalue.get("contactAddress")) && !PubMethod.isEmpty(String.valueOf(mapvalue.get("contactAddress")).split(" "))){
							 String address =  String.valueOf(mapvalue.get("contactAddress")).replace("-","");
							 String [] addressArr = address.split(" ");
							 mapvalue.put("contactAddress",addressArr[0]);
							 for(int j = 1; j < addressArr.length;j++ ){
								 SB.append(addressArr[j]);
							 }
							 mapvalue.put("AddressMsg",SB.toString());
							 if(!PubMethod.isEmpty(address) && addressArr.length <= 1){
								 SB = new StringBuffer();
								 String [] addressArre =  address.split("[|]");
								 mapvalue.put("contactAddress",addressArre[0]);
								 for(int j = 1; j < addressArre.length;j++ ){
									 SB.append(addressArre[j]);
								 }
								 mapvalue.put("AddressMsg",SB.toString());
							 }
						 }   else {
							 mapvalue.put("contactAddress","");
							 mapvalue.put("AddressMsg","");
						 }
						 ResultList.add(mapvalue);
					 }
					 m.put("resultlist", ResultList);
					 ResultMap.put("data",m);
				 } 
			 }
		}
		ResultMap.put("success",true);
		return JSON.toJSONString((ResultMap));
	}
	
	public String reviseResultSend(Map<String,Object> ResultMap){
		List<Object> ResultList = new ArrayList<Object>();
		Map<String,Object> m = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(ResultMap)){
			 String str = ResultMap.get("data").toString();
			 		JSON.parseObject(str.replace("=",":"));
				   List valueList =   JSONArray.parseArray(str);
				 if(!PubMethod.isEmpty(valueList) && valueList.size() > 0){
					 for(int i = 0 ; i < valueList.size() ; i++){
						 StringBuffer SB = new StringBuffer();
						 String  StrValue = valueList.get(i).toString();
						 Map<String,Object> mapvalue =  JSON.parseObject(StrValue);
						 String [] addressArr = mapvalue.get("contactAddress").toString().replace("-","").split(" ");
						 mapvalue.put("contactAddress",addressArr[0]);
						 for(int j = 1; j < addressArr.length;j++ ){
							 SB.append(addressArr[j]);
						 }
						 mapvalue.put("AddressMsg",SB.toString());
						 ResultList.add(mapvalue);
					 }
					 m.put("resultlist", ResultList);
					 ResultMap.put("data",m);
				 } 
		}
		return JSON.toJSONString(ResultMap);
	}
	
	public String saveParcelMap(HttpServletResponse response ,short ParceTypeFlag,String parmJSON,Long actualAcount,
								Long memberId){
		BaseController baseController = new BaseController();
		StringBuffer parcelIds = new StringBuffer();	
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(parmJSON)) {
				List valueList = new ArrayList();
				Map<String,Object> Map = castFunction(parmJSON);
				String valueStr = castFunction(Map,"data");
				if(PubMethod.isEmpty(valueStr)){
					 return  baseController.exp(response,new ServiceException("Json不能为空"));
				}
				JSONArray JSONArray = castFunctionByList(valueStr);
					for (Object object : JSONArray) {
						Map<String,Object> valueMap = castFunction(object.toString());
						if(PubMethod.isEmpty(valueMap.get("id")) || "0".equals(valueMap.get("id")+"")){
							valueMap.put("id", "");
						}
						if(PubMethod.isEmpty(valueMap.get("takeTaskId")) || "0".equals(valueMap.get("takeTaskId")+"")){
							valueMap.put("takeTaskId", "");
						}
						if(PubMethod.isEmpty(valueMap.get("addresseeAddressId")) || "0".equals(valueMap.get("addresseeAddressId")+"")){
							valueMap.put("addresseeAddressId", "");
						}
						if(PubMethod.isEmpty(valueMap.get("sendAddressId")) || "0".equals(valueMap.get("sendAddressId")+"")){
							valueMap.put("sendAddressId", "");
						}
						if(PubMethod.isEmpty(valueMap.get("compId")) || "0".equals(valueMap.get("compId")+"")){
							valueMap.put("compId", "");
						}
						//1为取件,支付方式在前台显示,以不用判断
						//2为提货,提供的方式不全,如果有钱就说明要给收件人要支付方式就是未收
						if(2 == ParceTypeFlag) {
							if(PubMethod.isEmpty(valueMap.get("freight")) || "0".equals(valueMap.get("freight").toString())){
								// freight_payment_status '费用结算状态 1 运费已收 0 运费未收',
								valueMap.put("freightPaymentStatus", "1");
							} else {
								valueMap.put("freightPaymentStatus", "0");
							}
//							代收货款为空或者是0,那么就就是在派件的时候在收取费用
//							0:不代收付货款,1:上门代收付(COD)'
							if(PubMethod.isEmpty(valueMap.get("codAmount")) || "0".equals(valueMap.get("codAmount"))){
								valueMap.put("goodsPaymentMethod", "0");
							} else {
								valueMap.put("goodsPaymentMethod", "1");
							}
						}
						//包裹未结束
						valueMap.put("parcelEndMark", "0");
//						发件客户ID
//						if(userSend == 0){
//							valueMap.put("sendCustomerId",valueMap.get("senderUserId"));
//						}
						String  ResultStr = openApiHttpClient.doPassSendStr("parcelInfo/saveParcelInfo",valueMap);
						if(!PubMethod.isEmpty(ResultStr)){
							Map<String, Object> castFunction = castFunction(ResultStr);
							parcelIds.append(",");
							parcelIds.append(castFunction(castFunction.get("data").toString()).get("id"));
							String existBoolean  = castFunction.get("success").toString();
							if("".equals(existBoolean) || existBoolean.equals("false")){
							     return  baseController.exp(response,new ServiceException("保存包裹信息异常"));
							}
						} else {
							return  baseController.exp(response,new ServiceException("保存包裹信息异常"));
						}
					}
			} else {
				return  baseController.exp(response,new ServiceException("参数不能为空"));
			}
			String parcels =  parcelIds.toString().substring(1,parcelIds.length());    
			//ParceTypeFlag=1--取-- 取件结算
		    //在结算表中插入信息,该接口修改包裹的订单Id
		    if(ParceTypeFlag == 3 || (!PubMethod.isEmpty(actualAcount) && 0 != actualAcount && 1 == ParceTypeFlag )){
		    	 Map<String,Object> paraMeterMap = new HashMap<String,Object>();
				  paraMeterMap.put("parcelIds",parcels);
				  paraMeterMap.put("totalFreight",actualAcount);
				  paraMeterMap.put("memberId",memberId);
				  paraMeterMap.put("type",0);
				  String doPassSendStr = openApiHttpClient.doPassSendStr("expressPrice/settleAccounts",paraMeterMap);
		    }
		    resultMap.put("parceIds",parcels);
			return jsonSuccess(resultMap);
}
	
	public String findParcelDetailMap(Map<String,Object> map){
		Map<String,Object> datavalueMap = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(map) && !PubMethod.isEmpty(map)){
			String castFunction = castFunction(map,"data");
			if(!PubMethod.isEmpty(castFunction)){
			    datavalueMap =  castFunction(castFunction);
				if(!PubMethod.isEmpty(datavalueMap) && !PubMethod.isEmpty(datavalueMap.get("sendAddress"))){
					//发件人详细地址
					String [] valueArry =  datavalueMap.get("sendAddress").toString().split(" ");
					if(valueArry.length >= 2){
						datavalueMap.put("sendAddress",valueArry[0]);
						datavalueMap.put("sendAddressmsg",valueArry[1]);
					} else {
						datavalueMap.put("sendAddress",valueArry[0]);
						datavalueMap.put("sendAddressmsg","");
					}
				}
				if(!PubMethod.isEmpty(datavalueMap) && !PubMethod.isEmpty(datavalueMap.get("addresseeAddress"))){
					//收件人详细地址
					String [] valueArry =  datavalueMap.get("addresseeAddress").toString().split(" ");
					if(valueArry.length >= 2){
					datavalueMap.put("addresseeAddress",valueArry[0]);
					datavalueMap.put("addresseeAddressmsg",valueArry[1]);
					} else {
						datavalueMap.put("addresseeAddress",valueArry[0]);
						datavalueMap.put("addresseeAddressmsg","");
					}
					}
			}
		}
		return jsonSuccess(datavalueMap);
	}
	
	
	

	public Map<String,Object> createSendTaskBatch(String parcelIds,Long memberId,String memberPhone){
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("parcelId",parcelIds );
		map.put("memberId",memberId );
		map.put("memberPhone",memberPhone );
		return map;
	}
	
	public Map<String,Object> createSendTaskMap(String parcelId,Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc,Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Long createUserId,
			Integer taskFlag, Double contactAddrLongitude,
			Double contactAddrLatitude){
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("parcelId",parcelId );
		map.put("coopCompId",coopCompId );
		map.put("coopNetId",coopNetId );
		map.put("parEstimateCount", parEstimateCount);
		map.put("parEstimateWeight",parEstimateWeight );
		map.put("appointTime",appointTime );
		map.put("appointDesc",appointDesc );
		map.put("actorMemberId", actorMemberId);
		map.put("actorPhone", actorPhone);
		map.put("contactName",contactName );
		map.put("contactMobile", contactMobile);
		map.put("contactTel",contactTel );
		map.put("contactAddressId", contactAddressId);
		map.put("contactCompId", contactCompId);
		map.put("createUserId", createUserId);
		map.put("taskFlag", taskFlag);
		map.put("contactAddrLongitude", contactAddrLongitude);
		map.put("contactAddrLatitude", contactAddrLatitude);
		return map;
	}
	
	public String checkTelMap(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(PubMethod.isEmpty(map)){
			return jsonFailure(new ServiceException("判断手机号码异常"));
		}
	    if(!PubMethod.isEmpty(map.get("success")) && "true".equals(map.get("success").toString())){
	    	if(!PubMethod.isEmpty(map.get("data"))){
	    		String flag  =  castFunction(map.get("data").toString()).get("flag").toString().toString();
	    		return flag;
		    } else{
		    	return jsonFailure(new ServiceException("判断手机号码异常"));
		    }
	    } else {
	    	return jsonFailure(new ServiceException("判断手机号码异常"));
	    }
	}
	
	
	public String queryTakeTaskListMap(Map<String,Object> map){
		List resultList = new ArrayList();
		if(!PubMethod.isEmpty(map)){
			 JSONArray castFunctionByList = castFunctionByList(castFunction(map, "data"));
			 if(!PubMethod.isEmpty(castFunctionByList) && !"[]".equals(castFunctionByList)){
				 for (Object object : castFunctionByList) {
					 Map<String, Object> castFunction = new HashMap<String,Object>();
					 StringBuffer sb = new StringBuffer();
					   castFunction = castFunction(JSON.toJSONString(object));
					  String [] Strvalue =  castFunction.get("sendAddress").toString().split(" ");
					  castFunction.put("sendAddress", Strvalue[0]);
					  for(int i = 1 ; i < Strvalue.length ; i++){
						  	sb.append(Strvalue[i]);
					  }
					  castFunction.put("sendAddressMsg", sb.toString());
					  resultList.add(castFunction);
				 }
			 } else {
				 jsonSuccess(map);
			 }
		} else {
			jsonSuccess(map);
		}
		return jsonSuccess(resultList);
	}
	public String saveCompInfoMap(Map<String,Object> map){
	   Map<String,Object> mapValue =  castFunction( PubMethod.isEmpty(castFunction(map, "data"))?null:castFunction(map, "data"));
		if(PubMethod.isEmpty(mapValue)){
			return null;
		}
		return String.valueOf(mapValue.get("compId"));
	}
	
	public List SendMap(Map<String,Object> map){
		if(null != map){
			if(null != castFunction(map,"data") && !"[]".equals(castFunction(map,"data"))){
				return	castFunctionByList(castFunction(castFunction(map,"data")).get("resultlist").toString());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public boolean isempty(String value){
		if(null != value && !"".equals(value)){
			return true;
		}
		return false;
	}
	public boolean iscount(String [] valueArr,String [] usePhoneArr){
		if(valueArr.length>=usePhoneArr.length){
			return true;
		}
		return false;
	}
	public Map<String,Object> castFunction(String value){
		Map<String,Object> result = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(value)){
			 result = JSON.parseObject(value);
		}
		return result;
	}
	public String castFunction(Map<String,Object> resultMap,String value){
		String resultStr = "";
		if(!PubMethod.isEmpty(resultMap)){
			if(!PubMethod.isEmpty(resultMap.get(value))){
				resultStr = resultMap.get(value).toString();
			}
		}
		return resultStr;
	}
	public JSONArray castFunctionByList(String value){
		JSONArray parseArray =new JSONArray();
		if(!PubMethod.isEmpty(value)){
			  parseArray = JSONArray.parseArray(value);
		}
		return parseArray;
	}
	protected String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		return JSON.toJSONString(allMap,s);
	}
	protected String jsonFailur(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", false);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		return JSON.toJSONString(allMap,s);
	}
	protected String jsonFailure(Exception e)  {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("success", false);
		 map.put("message", e.getMessage());
         return JSON.toJSONString(map);
	}
	protected String jsonFailure(String Message)  {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("message", Message);
		return JSON.toJSONString(map);
	}
	
	public String SetSmsContentTask(String smsTemplate,String phoneNumMsgId,String memberPhone,String expLon,String expLat) {
		logger.info("SetSmsContentTask******************phoneNumMsgId: "+phoneNumMsgId);
		Map<String,String> map = new HashMap<String,String>();
		List<Map> resultList = new ArrayList<Map>();
		Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$"); //取消严格校验，与手机端校验保持一致
		if(isempty(phoneNumMsgId)){
		String phoneAndWaybillNumArr [] = phoneNumMsgId.split("[|]");
		int i = 0;
		Map<String,Object> errorMap = new HashMap<String,Object>();
		List<Map> list = new ArrayList<Map>();
		for (String temp : phoneAndWaybillNumArr) {
			if(PubMethod.isEmpty(temp)){
				continue;
			}
				Matcher m = p.matcher(temp.split("-")[0].trim()); 
				if(!m.matches()){
					logger.info("你录入的该 "+temp.split("-")[0]+" 号码有误.....................");
					errorMap.put("success",false);
					errorMap.put("cause", "你录入的此 "+temp.split("-")[0]+" 的号码有误,请修改此号码!!!");
					errorMap.put("isCharge","2");
					list.add(errorMap);
					String jsonString = JSON.toJSONString(list);
					return jsonString; 
				}
				//ephone+"-"+num+"-"+msgId+"-"+isSend+"-"+isWx+"-"+receiverMap.get(phone)+"-"+waybill+"-"+netName+"-"+name;
				//phoneAndNum: 13426465659-26-258406143557632-0-1-1-70456105009568-百世-|13426465758-25-258406143557633-0-1-1-50272350207319-百世-
				String content = smsTemplate;
				content = content.replaceAll("[^\\u0000-\\uFFFF]","");
				content = temp.split("-").length>1 && !"".equals(temp.split("-")[1].trim())? content.replace("#编号#", "取件码"+temp.split("-")[1].trim()) : content.replace("#编号#", "");
				map.put(temp, null);
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("realContent", content);
				resultMap.put("mobile", temp.split("-")[0].trim()+"-"+temp.split("-")[1]);
				resultMap.put("content", content);
				resultMap.put("msgId", temp.split("-")[2]);
				resultMap.put("isSendMsg", temp.split("-")[3]);
				resultMap.put("isWx", PubMethod.isEmpty(temp.split("-")[4]) ? "1" : temp.split("-")[4]); //1.没有关注微信 0.关注微信
				resultMap.put("isCharge", PubMethod.isEmpty(temp.split("-")[5]) ? "1" : temp.split("-")[5]); // 0.先走免费 1.仅走余额
				resultMap.put("wayBill", ""); // 运单号
				resultMap.put("netName", ""); // 网络名称
				resultMap.put("name", ""); // 姓名
				logger.info("到了第一步resultMap: "+resultMap);
				if(temp.split("-").length>6){
					resultMap.put("wayBill", temp.split("-")[6]); 
					resultMap.put("netName", temp.split("-")[7]);
					logger.info("到了第二步resultMap: "+resultMap);
					if(temp.split("-").length>8){
						resultMap.put("name", temp.split("-")[8]);
						logger.info("到了第三步resultMap: "+resultMap);
					}
				}
				resultList.add(resultMap);
			}
		} 
		logger.info("最后组装成的数据格式resultList***************:"+resultList);
		return JSON.toJSONString(resultList);
}
	public String SetAliSmsContentTask(String smsTemplate,String phoneNumMsgId,String memberPhone,String expLon,String expLat) {
		Map<String,String> map = new HashMap<String,String>();
		List<Map> resultList = new ArrayList<Map>();
		if(isempty(phoneNumMsgId)){
			String phoneAndWaybillNumArr [] = phoneNumMsgId.split("[|]");
			for (String temp : phoneAndWaybillNumArr) {
				if(PubMethod.isEmpty(temp)){
					continue;
				}
				logger.info("阿里temp***************************temp: "+temp);
				//wayBill+"-"+num+"-"+msgId+"-"+isSend+"-"+netName+"-"+netId;
				String content = smsTemplate;
				content = content.replaceAll("[^\\u0000-\\uFFFF]","");
				content = temp.split("-").length>1 && !"".equals(temp.split("-")[1].trim())? content.replace("#编号#", "取件码"+temp.split("-")[1].trim()) : content.replace("#编号#", "");
				map.put(temp, null);
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("realContent", content);
				resultMap.put("wayBill", temp.split("-")[0]);
				resultMap.put("mobile", ""+"-"+temp.split("-")[1]);
				resultMap.put("content", content);
				resultMap.put("msgId", temp.split("-")[2]);
				resultMap.put("isSendMsg", temp.split("-")[3]);
				resultMap.put("netName", temp.split("-")[4]);
				resultMap.put("netId", temp.split("-")[5]);
				resultMap.put("isCharge", PubMethod.isEmpty(temp.split("-")[6]) ? "0":temp.split("-")[6]);
				resultList.add(resultMap);
			}
		}
		logger.info("延时发送阿里大于最后拼装的串为resultList: "+resultList);
		return JSON.toJSONString(resultList);
	}
}