package net.okdi.core.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.alibaba.fastjson.JSON;

public class RedisRepository {

	private @Autowired StringRedisTemplate redisTemplate;
	
	private static final long EXPIRE_TIME = 24;
	
	public void set(final String key, final String value) {
		redisSet(key, value);
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public void set(final String key, final Object value) {
		redisSet(key, JSON.toJSONString(value));
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public void setNoTime(final String key, final String value) {
		redisSet(key, value);
	}
	
	public void setNoTime(final String key, final Object value) {
		redisSet(key, JSON.toJSONString(value));
	}

	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] bs = conn.get(key.getBytes());
				return bs == null ? "" : new String(bs);
			}
		});
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	public <T> T get(final String key, final Class<T> clazz) {
		return redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] bs = conn.get(key.getBytes());
				if (bs == null) {
					return null;
				}
				return JSON.parseObject(new String(bs), clazz);
			}
		});
	}
	
	/*********************hash**********************/
	
	public void hset(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, JSON.toJSONString(value));
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public void hsetNoTime(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, JSON.toJSONString(value));
	}
	
	public void hMset(String key, Map<String, String> map) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public <T> T hget(String key, String hashKey, Class<T> clazz) {
		Object obj = redisTemplate.opsForHash().get(key, hashKey);
		if (obj == null) {
			return null;
		}
		return JSON.parseObject(obj.toString(), clazz);
	}
	
	public <T> List<T> hMget(String key, Collection<Object> hashKeys, Class<T> clazz) {
		List<Object> objs = redisTemplate.opsForHash().multiGet(key, hashKeys);
		if (objs == null) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (Object obj : objs) {
			result.add(JSON.parseObject(obj.toString(), clazz));
		}
		return result;
	}
	
	public <T> List<T> hGetAll(String key, Class<T> clazz) {
		Set<Object> keys = redisTemplate.opsForHash().keys(key);
		if (keys == null) {
			return null;
		}
		List<T> result = new ArrayList<>();
		Iterator<Object> iter = keys.iterator();
		while (iter.hasNext()) {
			String hashKey = iter.next() + "";
			result.add(hget(key, hashKey, clazz));
		}
		return result;
	}
	
	
	/***********************zset*****************************/
	
	public void zset(String key, String value, double score) {
		redisTemplate.opsForZSet().add(key, value, score);
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}

	public Double zget(String key, String value) {
		Double score = redisTemplate.opsForZSet().score(key, value);
		if (null == score) {
			return null;
		}
		return score;
	}
	
	public void zMset(String key, Map<String, Double> valueScorePair) {
		Iterator<String> iter = valueScorePair.keySet().iterator();
		Set<TypedTuple<String>> tuples = new HashSet<>();
		while (iter.hasNext()) {
			String value = iter.next();
			TypedTuple<String> tuple = new DefaultTypedTuple<String>(value, valueScorePair.get(value));
			tuples.add(tuple);
		}
		redisTemplate.opsForZSet().add(key, tuples);
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public void zMset(String key, Set<TypedTuple<String>> tuples) {
		redisTemplate.opsForZSet().add(key, tuples);
		redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.HOURS);
	}
	
	public List<String> zgetReverse(String key, int start, int end) {
		Set<TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
		Iterator<TypedTuple<String>> iter = tuples.iterator();
		List<String> values = new ArrayList<>();
		while (iter.hasNext()) {
			TypedTuple<String> tuple = iter.next();
			values.add(tuple.getValue());
		}
		return values;
	}
	
	public List<String> zget(String key, int start, int end) {
		Set<TypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
		Iterator<TypedTuple<String>> iter = tuples.iterator();
		List<String> values = new ArrayList<>();
		while (iter.hasNext()) {
			TypedTuple<String> tuple = iter.next();
			values.add(tuple.getValue());
		}
		return values;
	}
	
	public List<Object> zgetObjectReverse(String key, int start, int end) {
		Set<TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
		Iterator<TypedTuple<String>> iter = tuples.iterator();
		List<Object> values = new ArrayList<>();
		while (iter.hasNext()) {
			TypedTuple<String> tuple = iter.next();
			values.add(tuple.getValue());
		}
		return values;
	}
	
	public List<Object> zgetObject(String key, int start, int end) {
		Set<TypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
		Iterator<TypedTuple<String>> iter = tuples.iterator();
		List<Object> values = new ArrayList<>();
		while (iter.hasNext()) {
			TypedTuple<String> tuple = iter.next();
			values.add(tuple.getValue());
		}
		return values;
	}
	
	
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}
	
	private void redisSet(final String key, final String value) {
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection conn) throws DataAccessException {
				conn.set(key.getBytes(), value.getBytes());
				return null;
			}
		});
	}
	
}






