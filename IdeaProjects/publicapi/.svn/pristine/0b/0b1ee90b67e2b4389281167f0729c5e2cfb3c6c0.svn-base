package net.okdi.core.common.interceptor;

import com.alibaba.fastjson.JSON;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.GenMD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sun.util.logging.resources.logging;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private OpenApiHttpClient openApiHttpClient;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
//        LOGGER.info("after completion by loginInterceptor");
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView mView) throws Exception {
//        LOGGER.info("after method invoke by loginInterceptor");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//        LOGGER.info("pre method invoke by loginInterceptor");
        Enumeration myEnumeration = request.getHeaderNames();
        String key = "";
        String mobile = "";
        String version = "";
        while (myEnumeration.hasMoreElements()) {
            Object element = myEnumeration.nextElement();
            if ("key".equals(element)) {
                key = request.getHeader(element.toString());
            } else if ("mobile".equals(element)) {
                mobile = request.getHeader(element.toString());
            } else if ("version".equals(element)) {
                version = request.getHeader(element.toString());
            }
        }
        LOGGER.info("path: " + request.getServletPath() + ",key: " + key + ",mobile: " + mobile + ",version: " + version);
        if ("".equals(version)) {
            try {
                String ip = getRemoteIP(request);
                String path = request.getServletPath();
                Map<String, String> map = new HashMap<>();
                map.put("ip", ip);
                map.put("path", path);
                openApiHttpClient.doPassSendStr("interceptor/saveOldVersionMsg", map);
            } catch (ServiceException e) {
                LOGGER.error(e.toString());
            }
            return true;
        }
        if (request.getServletPath().equals("/okdiLogin/okdiLoginExpress")
                || request.getServletPath().equals("/newRegister/validate")
                || request.getServletPath().equals("/expressUser/isBoundWechat")
                || request.getServletPath().equals("/queryNearCompInfo/queryVerifyCodeIsRight")
                || request.getServletPath().equals("/newRegister/validateVerify")) {   //过滤不需要拦截的请求
            return true;
        }

        if (isLogin(mobile, version, key)) {
            return true;
        } else {
        	LOGGER.info("LoginInterceptor：：：：：：：：：：请求被拦截！mobile:"+mobile+", version"+version+", key" + key);
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("errCode", 1);
            map.put("errSubCode", "");
            map.put("message", "还没有登录，请先登录");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream ous = response.getOutputStream();
            try {
            	ous.write(JSON.toJSONString(map).getBytes());
            	ous.flush();
			} finally{
				ous.close();
			}
        }
        return false;
    }

    private String getRemoteIP(HttpServletRequest request) {
        String ip;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip;
    }

    /**
     * 是否处于登陆状态
     */
    private static boolean isLogin(String mobile, String version, String key) {
        //把手机号和版本号加密
        String mobileMD5 = GenMD5.generateMd5(mobile);
        DesEncrypt des = new DesEncrypt();
        des.getKey("okdi"); //生成密匙
        String encString = des.getEncString(mobileMD5 + version);
        return encString.equals(key);   //返回验证结果
    }

}
