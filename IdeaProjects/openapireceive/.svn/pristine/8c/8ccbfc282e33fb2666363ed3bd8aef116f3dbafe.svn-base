package net.okdi.apiV2.service.impl;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.WechatJsApiTicketService;
import net.okdi.apiV2.service.WechatMpService;
import net.okdi.apiV2.vo.WechatJsapiTicket;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.WechatMpHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 微信公众账号
 * @ClassName: WechatJsApiTicketServiceImpl
 * @Description: TODO
 * @author jianxin.ma
 * @date 2016年4月6日 下午5:35:01
 * @version V1.0
 */
@Service
public class WechatJsApiTicketServiceImpl implements WechatJsApiTicketService {

	
	@Autowired
	private WechatMpService WechatMpService;
	
	
	private @Autowired StringRedisTemplate redis;
	
	private @Autowired WechatMpHttpClient wechatMpHttpClient;
	
	private static final String KEY = "wechatJsApiTicket";
	//微信获取签名url
	@Value("${wechat_ecdsa_url}")
	private String wechatUrl;
	
	private static final Logger logger = LoggerFactory.getLogger(WechatJsApiTicketServiceImpl.class);
	
	public WechatJsapiTicket getTicket() {
		logger.info("读缓WechatJsapiTicket存开始====================================");
		WechatJsapiTicket ticket = getRedisTicket(KEY, WechatJsapiTicket.class);
		if(ticket == null){
			ticket = this.ticket();
			logger.info("没走缓存WechatJsapiTicket===========================ticket1:::::"+ticket.getTicket());
		}else{
			logger.info("从缓存中读取WechatJsapiTicket===========================ticket1:::::"+ticket.getTicket());
		}
		return ticket;
	}
	

	private WechatJsapiTicket ticket() {
		String accessToken=WechatMpService.getToken();
		WechatJsapiTicket ticket = JSON.parseObject(wechatMpHttpClient.getJsapiTicket(accessToken), WechatJsapiTicket.class);
		if (!"0".equals(ticket.getErrcode())) {
			throw new ServiceException(ticket.getErrmsg());
		}
		ticket.setCreateTime(System.currentTimeMillis());
		putRedisTicket(KEY, ticket, ticket.getExpires_in());
		return ticket;
	}
	
	private <T> T getRedisTicket(String key, final Class<T> clazz) {
		final byte[] fKey = key.getBytes();
		return redis.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] bs = conn.get(fKey);
				return bs == null ? null : JSON.parseObject(new String(bs), clazz);
			}
		});
	}
	
	private void putRedisTicket(String key, Object value, final Long expire) {
		final byte[] fKey = key.getBytes();
		String jsonString = JSON.toJSONString(value);
		final byte[] fValue = jsonString.getBytes();
		logger.info("ticket: " + jsonString);
		redis.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.set(fKey, fValue);
				conn.expire(fKey, expire);
				return null;
			}
		});
	}
	
	public Map<String,Object> getEcdsa(){
		logger.info(new Date()+"进入openapi/getEcdsa-------------------------------------");
		String noncestr="Wm3WZYTPz0wzccnW";
		WechatJsapiTicket ticket=this.getTicket();
		String jsapi_ticket=ticket.getTicket();
		Long timestamp=ticket.getCreateTime();
//		String url="http://lifev2.okdi.net/okdilife/weixin/query_weixin.jsp";
		logger.info("获得到了ticket==============================================="+jsapi_ticket);
		String str="jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+String.valueOf(timestamp)+"&url="+wechatUrl;
		logger.info(str+"=================================================微信加密的str");
		String signature="";
		 try
	        {
			 logger.info("--------------开始加密");
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(str.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	            logger.info("--------------加密成功");
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	        	logger.info("加密出错--------------------------NoSuchAlgorithmException");
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	        	logger.info("加密出错--------------------------UnsupportedEncodingException");
	            e.printStackTrace();
	        }
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("noncestr", noncestr);
		map.put("timestamp", String.valueOf(timestamp));
		map.put("signature", signature);
		logger.info("获得签名============================signature::::"+signature+"timestamp::"+timestamp+"-----noncestr::"+noncestr);
		
		return map;
	}
	
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	
}













