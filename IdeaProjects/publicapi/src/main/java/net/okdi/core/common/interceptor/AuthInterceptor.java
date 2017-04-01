package net.okdi.core.common.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.okdi.api.entity.OauthAccessKey;
import net.okdi.api.service.OauthAccessService;
import net.okdi.core.util.GenMD5;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author feng.wang
 * @version V1.0
 */
@Repository
public class AuthInterceptor implements HandlerInterceptor {

	private final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Autowired
	private OauthAccessService oauthAccessService;

	/*
	 * (non-Javadoc) 拦截mvc.xml配置的/**路径的请求
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		// 请求的公钥
		String publickey = request.getParameter("publickey");
		// 获取签名
		String secret = request.getParameter("secret");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		
		logger.debug("请求的公钥：" + publickey + ",请求的签名：" + secret + ",时间戳：" + timestamp);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			if (PubMethod.isEmpty(publickey)) {
				out.print(PubMethod.paramError("err.001","publickey is not null!"));
				return false;
			}
			if (PubMethod.isEmpty(secret)) {
				out.print(PubMethod.paramError("err.002","secret is not null!"));
				return false;
			}
			if (PubMethod.isEmpty(timestamp)) {
				out.print(PubMethod.paramError("err.003","timestamp is not null!"));
				return false;
			}
			//每一个时间戳连接有效时间为1分钟
			long times = Long.valueOf(timestamp) + 60 * 1000;
			long nowTime = System.currentTimeMillis();
			if (times < nowTime) {
				out.print(PubMethod.paramError("err.004", "request is overdue!"));
				return false;
			}
			// 短信参数字符串(除了secret外)
			String params = this.getParamsStr(request.getParameterMap());
			OauthAccessKey entity = this.oauthAccessService.getEntityByKey(publickey);
			if (PubMethod.isEmpty(entity)) {
				out.print(PubMethod.paramError("err.005", "publickey is error!"));
				return false;
			}
			// 参数加私钥MD5加密返回字符串
			String result = GenMD5.generateMd5(params, entity.getPrivateKey());
//			logger.debug("参数加私钥MD5加密返回字符串：" + result);
			if (!result.equals(secret)) {
				out.print(PubMethod.paramError("err.006", "secret is error!"));
				return false;
			}
			// 存放用户ID
			request.setAttribute("auth_memberId", entity.getMemberId());
		} catch (Exception e) {
			e.printStackTrace();
			out.print(PubMethod.sysErrorUS());
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			ModelAndView arg3) {
	}

	private String getParamsStr(Map m) {
		String str = "";
		try {
			List<String> keyList = new ArrayList<String>(m.keySet());
			Collections.sort(keyList);
			for (String key : keyList) {
				if (!"secret".equals(key)) {
					str += key + "=";
					String[] strArr = (String[]) m.get(key);
					for (String s : strArr) {
						str += s;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		logger.debug("接口参数：" + str);
		return str;
	}
	

}
