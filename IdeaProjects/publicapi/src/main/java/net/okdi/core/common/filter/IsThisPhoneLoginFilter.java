/**  
 * @Project: mob
 * @Title: IsThisPhoneLogin.java
 * @Package net.okdi.core.common.filter
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-3 上午9:42:32
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.core.common.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;

import net.okdi.core.util.PubMethod;

/** 验证是否是本台设备登录
 * @ClassName IsThisPhoneLogin
 * @Description 通过调用手机登录服务器来验证是否该台手机已经登录
 * 注意：改功能根据最新的需求，删掉
*/
public class IsThisPhoneLoginFilter implements Filter{

	private static Properties allowProp = new Properties();
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {
		//目前需要加上这段，当前不做校验，后期打开
		if(true){
			HttpServletRequest myRequest= (HttpServletRequest)request;
			Map m = request.getParameterMap();
			String str = "{";
			Iterator it = m.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				str += key + "=";
				String[] strArr = (String[]) m.get(key);
				for (String s : strArr) {
					str +=s+" ";
				}
				if(it.hasNext())
					str +=  ",";
			
			}
			str+="}";
			logger.debug("当前访问的url: "+myRequest.getRequestURI());
			logger.debug("访问的参数："+str);
			filterChain.doFilter(request, response);
			return;
		}
		
		HttpServletRequest myReq = (HttpServletRequest) request;
		//如果不需要token验证，直接往后执行
		//System.out.println(myReq.getServletPath().trim());
		//if(allowProp.get(myReq.getServletPath().trim()).equals("true")){
		if("true".equals(allowProp.get(myReq.getServletPath().trim()))){
			filterChain.doFilter(request, response);
			return;
		}
		//如果需要token验证，开始执行
		String deviceToken = request.getParameter("validate_deviceToken")==null?"":request.getParameter("validate_deviceToken").toString();
		String memberId = request.getParameter("validate_memberId")==null?"":request.getParameter("validate_memberId").toString();
		String channelNo = request.getParameter("validate_channelNo")==null?"":request.getParameter("validate_channelNo").toString();
		Map<String, String> returnResult = new HashMap<String,String>();
		returnResult.put("success", "false");
		//传入参数deviceToken和memberId校验
		if(PubMethod.isEmpty(deviceToken)||PubMethod.isEmpty(memberId)||PubMethod.isEmpty(channelNo)){
			returnResult.put("msg", "validate_deviceToken或validate_memberId或validate_channelNo为空");
		}else{
			boolean isThisPhone = false;// mobMemberLoginService.valOnePhoneLogin(channelNo, Long.valueOf(memberId), deviceToken);
			if(isThisPhone){
					filterChain.doFilter(request, response);
					return;
			} else {//返回值为false,所以退出
				returnResult.put("msg", "设备在别处登录或者已经登出，请重新登录");
			}
		}
		//如果没有进行正确，则返回
		response.getWriter().write(JSON.toJSONString(returnResult));
		response.getWriter().flush();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			String fileName = getClass().getResource("/config_allow_url.properties").getFile();
			allowProp.load(new FileInputStream(new File(fileName)));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
