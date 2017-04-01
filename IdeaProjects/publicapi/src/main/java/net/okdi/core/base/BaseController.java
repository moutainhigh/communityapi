package net.okdi.core.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseController {

	@Autowired
	ExpGatewayService expGatewayService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private ConstPool constPool;
	public static SerializerFeature[] s = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteNullStringAsEmpty};
	//在进行传递参数异常的时进行补获转型异常,为前台输出错误信息
	@ExceptionHandler(Exception.class)  
    public String exp(HttpServletResponse response, Exception ex) {  
		Map<String,Object> ExcepResultMap = new HashMap<String,Object>();
		if(!PubMethod.isEmpty(ex)){
			ExcepResultMap.put("success","false");
			ExcepResultMap.put("msg",ex.getMessage().replaceAll("\"","'"));
		  try {  
              PrintWriter writer = response.getWriter();  
              writer.write(JSON.toJSONString(ExcepResultMap));  
              writer.flush();  
          } catch (IOException e) {  
              e.printStackTrace();  
          } 
		}
		return JSON.toJSONString(ExcepResultMap);
    }
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"), true));
	}

	public String longResult(Map<String,Object> map) {
		Map<String,Object> ResultMap = new HashMap<String,Object>();
		if(map.get("success").toString().equals("false")){
			return JSON.toJSONString(map);
		} 
		Long memberId =  Long.parseLong(map.get("memberId").toString());
		ResultMap.put("memberId",memberId);
		setauThentiCate(ResultMap,memberId);
		ResultMap.put("head_phone_Url",constPool.getReadPath()+constPool.getHead()+memberId+".jpg");
		ResultMap.put("success",true);
		return JSON.toJSONString(ResultMap);
	}
	//登录时获取compId,rowId
 	public Map<String,Object> setComp(Map<String,Object> ResultMap,Long memberId){
 		String MemberInfoStr =  expGatewayService.getAuditInfo(memberId);//唯一memberId
		if(isempty(MemberInfoStr)){
			Map<String,Object> memberInfoMap  = JSONObject.parseObject(MemberInfoStr);
			if(memberInfoMap.get("success").toString().equals("true")){
				if(null == memberInfoMap.get("data"))
				return null;
				String memberInfoStr =  memberInfoMap.get("data").toString();
			    if(isempty(memberInfoStr) && !"[]".equals(memberInfoStr)){
			       String value = JSONArray.parseArray(memberInfoStr).get(0).toString();
				   Map<String,Object> CompInfoMap =  JSONObject.parseObject(value);
				   ResultMap.put("adminFlag",CompInfoMap.get("roleId"));
				   ResultMap.put("compId",CompInfoMap.get("compId"));
			    } 
			} 
		}
 		return ResultMap;
	}
// 	获取人员认证信息
//	 "relationFlag":0, --归属验证标识 0 待审核 1通过 2拒绝
//	 "veriFlag":0}, --身份验证标识 0 待审核 1通过 2拒绝
	public Map<String,Object> setauThentiCate(Map<String,Object> ResultMap,Long memberId){
		
		//查看审核通过或者是失败(拒绝) 还有等待
		Map<String, Object> validationStatusMap = expGatewayService.getValidationStatus(memberId);
		if(null != validationStatusMap){
		if(validationStatusMap.get("success").toString().equals("true")){
			  if(null == validationStatusMap.get("data"))
			  return null;
			  String memberflagStr =  validationStatusMap.get("data").toString();
			  if(isempty(memberflagStr)){
				  Map<String,Object> CompInfoMap  = JSONObject.parseObject(memberflagStr);
				  ResultMap.put("roleId", CompInfoMap.get("roleId"));
				  ResultMap.put("compId", CompInfoMap.get("compId"));
				  ResultMap.put("compStatus", CompInfoMap.get("compStatus")); //-1创建 0提交待审核 1审核通过 2审核失败
				  if(null == CompInfoMap.get("relationFlag") || "".equals(CompInfoMap.get("relationFlag")) || "null".equals(CompInfoMap.get("relationFlag"))){
					  ResultMap.put("relationFlag","-1");
				  } else {
					  ResultMap.put("relationFlag",CompInfoMap.get("relationFlag"));
				  }
				  if(null == CompInfoMap.get("veriFlag") || "".equals(CompInfoMap.get("veriFlag")) ||  "null".equals(CompInfoMap.get("veriFlag"))){
					  ResultMap.put("veriFlag","-1");
				  } else {
					  ResultMap.put("veriFlag",CompInfoMap.get("veriFlag"));
				  }
			  } 
		  }
		}                  
		  return ResultMap;
	}
	//获取人员基本信息
	public Map<String,Object> setBasicInfo(Map<String,Object> ResultMap,Long memberId){
		String memberMsg = registerService.getMemberMsg(memberId);
			Map<String,Object> BasicMap = JSONObject.parseObject(memberMsg.replace("[","").replace("]",""));
			if(BasicMap.get("success").toString().equals("true")){
				if(null == BasicMap.get("data"))
				return null;
				ResultMap.put("realname",BasicMap.get("realname"));
				ResultMap.put("idCard",BasicMap.get("idNum"));
				ResultMap.put("email",BasicMap.get("email"));
				ResultMap.put("memberMobile",BasicMap.get("mobile"));
				ResultMap.put("telephone",BasicMap.get("telephone"));
				ResultMap.put("memberName",BasicMap.get("username"));
			}
		return ResultMap;
	}
	//获取人员网络信息
	public Map<String,Object> setNetInfo(Map<String,Object> ResultMap,Long compId){
		String  CompResultStr = expGatewayService.getCompInfo(compId);
	 	if(null != CompResultStr && !"".equals(CompResultStr)){
	 		Map<String,Object> CompResultMap =  JSONObject.parseObject(CompResultStr);
	 		if(CompResultMap.get("success").toString().equals("true")){
	 			String compInfoDataStr =  CompResultMap.get("data").toString();
	 			if(isempty(compInfoDataStr)){
					 Map<String,Object> CompInfoDataMap = JSONObject.parseObject(compInfoDataStr);
					 ResultMap.put("netId",CompInfoDataMap.get("netId"));
					 ResultMap.put("compId",CompInfoDataMap.get("compId"));
					 ResultMap.put("compName",CompInfoDataMap.get("compName"));
					 ResultMap.put("netName",CompInfoDataMap.get("netName"));
				} 
	 		}
	 	}
		return ResultMap;
	}
	 public String queryStationByPhone(Map<String,Object> ResultMap){
		 Map<String,Object> handlMap = new HashMap<String,Object>();
		 if(null !=ResultMap){
			 if(null != ResultMap.get("data")){
				  String str = ResultMap.get("data").toString(); 
				   String strvalue = cast(str,"list");
				  List<Object> dataList = JSONArray.parseArray(strvalue);
				  for (Object object : dataList) {
					  if(null != object){
						  String ListStr = object.toString();
						  Map<String,Object> ifMap = JSONObject.parseObject(ListStr);
						  if(null != ifMap.get("roleId") && ifMap.get("roleId").toString().equals("1")){
							  handlMap.put("msg",ifMap.get("memberPhone"));
							  handlMap.put("success",true);
							  return JSON.toJSONString(handlMap);
						  }
					 }
				}
				  handlMap.put("msg","对不起不存在站长电话");
				  handlMap.put("success",false);
			 }
		 } else {
			 handlMap.put("msg","站点下没有人员信息");
			 handlMap.put("success",false);
		 }
		 return JSON.toJSONString(handlMap);
}
	 
	public boolean isempty(String value){
		if(null != value && !"".equals(value)){
			return true;
		}
		return false;
	}
	
    protected String paramsFailure() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("message", "Parameter is not correct or Parameter format error");
		return JSON.toJSONString(map);
	}
    
    public String cast(String str,String type){
    	Map<String,Object>  mm =  JSON.parseObject(str);
    	String string = "";
    	if(!PubMethod.isEmpty(mm)){
    		string = mm.get(type).toString();
    	}
    	return string;
    }
	protected String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		return JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
	}
	protected String jsonFailure(Exception e) {
		e.printStackTrace();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		if(e instanceof ServiceException ){
			ServiceException temp=(ServiceException)e;
			map.put("errCode", temp.getErrCode());
			map.put("errSubCode", temp.getErrSubCode());
			map.put("message", temp.getMessage());
		}else{
			map.put("errCode", 1);
			map.put("errSubCode", "");
			map.put("message", e.initCause(e.getCause()).toString());
		}
		return JSON.toJSONString(map);
	}
	protected String paramsFailure(String errSubcode,String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", 0);
		map.put("errSubcode",errSubcode);
		map.put("message",message);
		return JSON.toJSONString(map);
	}
	
	protected String getMemberId(HttpServletRequest request) {
		String memberId = null;
		if(!PubMethod.isEmpty(request.getAttribute("auth_memberId"))){
			 memberId = String.valueOf(request.getAttribute("auth_memberId"));
		}
		return memberId==null?"":memberId;
	}

}