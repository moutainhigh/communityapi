package net.okdi.apiV2.service.impl;

import java.util.Map;

import net.okdi.api.dao.ParTaskInfoMapper;
import net.okdi.apiV2.service.WechatMpService;
import net.okdi.apiV2.vo.WechatMpAccessToken;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.WechatMpHttpClient;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信公众账号
 * @ClassName: WechatMpServiceImpl
 * @Description: TODO
 * @author hang.yu
 * @date 2016年3月24日 下午2:35:01
 * @version V1.0
 */
@Service
public class WechatMpServiceImpl implements WechatMpService {

	@SuppressWarnings("unused")
	private @Autowired ParTaskInfoMapper parTaskInfoMapper;
	
	private @Autowired StringRedisTemplate redis;
	
	private @Autowired MongoTemplate mongoTemplate;
	
	
	private @Autowired ReceivePackageService receivePackageService;
	
	private @Autowired WechatMpHttpClient wechatMpHttpClient;
	
	private @Value("${mp.wechat.appid}") String appid;
	
	private @Value("${mp.wechat.secret}") String secret;
	
	private @Value("${mp.wechat.templateid}") String TEMPLATE_ID;
	
	private @Value("${mp.wechat.dumpurl}") String DUMP_URL;
	
	private static final String KEY = "wechatAccessToken";
	
	private static final Logger logger = LoggerFactory.getLogger(WechatMpServiceImpl.class);
	
	public String getToken() {
		WechatMpAccessToken token = getRedisToken(KEY, WechatMpAccessToken.class);
		logger.info("获取accessToken缓存中的========================"+token);
		return token == null ? token().getAccess_token() : token.getAccess_token();
	}
	
	/**
	 * 微信公众号发送模板消息
	 * 
	 * @param openId
	 *            接收人(关注公众号的微信用户)id
	 * @param site
	 *            站点
	 * @param courierName
	 *            快递员
	 * @param phone
	 *            快递员电话
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String send(String openId, String site, String courierName, String phone, Integer status, String reason,Long taskId) {
		logger.info("weixin 取件任务ID为："+ taskId+"进行  取消  或 同意操作   操作值为："+status);
		
		//查询任务信息
		Query query = Query.query(Criteria.where("uid").is(taskId));
		Map taskInfo = mongoTemplate.findOne(query, Map.class, "parTaskInfo");

		String result = "";
		//对任务进行取消操作
		if(status==0){ //前端操作  取消
			result = "000";
			receivePackageService.calledCourierToMemberWeiXin(taskId.toString(),"1");//1 拒绝

		}else{//对任务进行同意操作
			if(taskInfo.get("taskStatus") == null || "0".equals((String.valueOf(taskInfo.get("taskStatus"))))){
				result = "000";
				receivePackageService.memberConfirmOrderWeiXin(taskId.toString(), null, phone,(short)0);
			}else{
				result = String.valueOf(taskInfo.get("taskStatus"));
			}
		}

		
		//如果是从微信端发过来的任务，需要往微信端发模板消息
		if (!PubMethod.isEmpty(openId) && "000".equals(result)) {
			String token = getToken();
			JSONObject sendResult = wechatMpHttpClient.sendTempleteMessage(token, openId, TEMPLATE_ID, DUMP_URL, site,
					courierName, phone, status, reason);

			logger.info("发送模板消息返回值: " + sendResult == null ? null : sendResult.toJSONString());
		
			return sendResult == null ? "" : sendResult.getString("errmsg");
		}else{
			logger.info("weixin 取件任务ID为："+ taskId+"没有发送模板消息。因为opendId为:"+openId+" taskStatus状态为："+result);
		}
		
		return result;
	}

    @Override
	public void offlineSend(String openId, String courierName, String phone, Integer status, String reason) {
		String token = getToken();
		wechatMpHttpClient.sendTempleteMessage(token, openId, TEMPLATE_ID, DUMP_URL, "", courierName, phone, status, reason);
	}

	private WechatMpAccessToken token() {
		WechatMpAccessToken token = JSON.parseObject(wechatMpHttpClient.token(appid, secret), WechatMpAccessToken.class);
		if (!PubMethod.isEmpty(token.getErrcode())) {
			throw new ServiceException(token.getErrmsg());
		}
		token.setCreateTime(System.currentTimeMillis());
		putRedisToken(KEY, token, token.getExpires_in());
		return token;
	}
	
	private <T> T getRedisToken(String key, final Class<T> clazz) {
		final byte[] fKey = key.getBytes();
		return redis.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] bs = conn.get(fKey);
				return bs == null ? null : JSON.parseObject(new String(bs), clazz);
			}
		});
	}
	
	private void putRedisToken(String key, Object value, final Long expire) {
		final byte[] fKey = key.getBytes();
		String jsonString = JSON.toJSONString(value);
		final byte[] fValue = jsonString.getBytes();
		logger.info("token: " + jsonString);
		redis.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.set(fKey, fValue);
				conn.expire(fKey, expire);
				return null;
			}
		});
	}
	
}













