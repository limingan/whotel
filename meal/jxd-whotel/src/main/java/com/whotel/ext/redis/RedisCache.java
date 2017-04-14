package com.whotel.ext.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisCache extends RedisStorage {
	private static final Logger logger = Logger.getLogger(RedisCache.class);

	private static final String cacheNamePre = "whotel_";
	
	public boolean exist(String key) {
		Jedis jedis = null;
		Boolean exist = false;
		try {
			jedis = getJedis();
			String key2 = cacheNamePre + key;
			exist = jedis.exists(serialize(key2));
			if (exist == null)
				exist = false;
			// return (value != null ? new SimpleValueWrapper(value) : null);
		} catch (Exception e) {
			logger.error("Can't get from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return exist;
	}

	public Object get(Object key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String key2 = cacheNamePre + key;
			byte[] value = jedis.get(serialize(key2));
			Object value2 = value == null ? null : deserialize(value);
			return value2;
			// return (value != null ? new SimpleValueWrapper(value) : null);
		} catch (Exception e) {
			logger.error("Can't get from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return null;
	}

	public void put(Object key, Object value) {
		if (value == null) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String key2 = cacheNamePre + key;
			byte[] value2 = serialize(value);
			jedis.set(serialize(key2), value2);
		} catch (Exception e) {
			logger.error("Can't put object to redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
	}

	public void put(Object key, Object value, int cacheTime) {
		if (value == null) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.setex(serialize(cacheNamePre + key), cacheTime,
					serialize(value));
		} catch (Exception e) {
			logger.error("Can't put object to redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
	}

	public void evict(Object key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(serialize(cacheNamePre + key));
		} catch (Exception e) {
			logger.error("Can't evict object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 从hash中删除指定的存储
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid 存储的名字
	 * @return 状态码，1成功，0失败
	 * */
	public long hdel(String key, String fieid) {
		Jedis jedis = null;
		long s = 0;
		try {
			jedis = getJedis();
			s = jedis.hdel(serialize(cacheNamePre + key), serialize(fieid));
		} catch (Exception e) {
			logger.error("Can't hdel object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return s;
	}

	/**
	 * 添加一个对应关系
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid
	 * @param String
	 *            value
	 * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
	 * **/
	public long hset(String key, String fieid, Object value, int cacheTime) {
		Jedis jedis = null;
		long s = 0;
		try {
			jedis = getJedis();
			s = jedis.hset(serialize(cacheNamePre + key), serialize(fieid),
					serialize(value));
			if (cacheTime > 0) {
				jedis.expire(serialize(cacheNamePre + key), cacheTime);
			}
		} catch (Exception e) {
			logger.error("Can't hset object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return s;
	}

	/**
	 * 返回hash中指定存储位置的值
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid 存储的名字
	 * @return 存储对应的值
	 * */
	public Object hget(String key, String fieid) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis jedis = null;
		Object s = null;
		try {
			jedis = getJedis();
			byte data[] = jedis.hget(serialize(cacheNamePre + key),
					serialize(fieid));
			s = deserialize(data);
		} catch (Exception e) {
			logger.error("Can't hget object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return s;
	}

	/**
	 * 返回hash中指定存储位置的值
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid 存储的名字
	 * @return 存储对应的值
	 * */
	public boolean hexist(String key, String fieid) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis jedis = null;
		Boolean exist = false;
		try {
			jedis = getJedis();
			exist = jedis.hexists(serialize(cacheNamePre + key),
					serialize(fieid));
			if (exist == null)
				exist = false;
		} catch (Exception e) {
			logger.error("Can't hexist object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return exist;
	}

	/**
	 * 以Map的形式返回hash中的存储和值
	 * 
	 * @param String
	 *            key
	 * @return Map<Strinig,String>
	 * */
	public Map<String, Object> hgetAll(String key) {
		Jedis jedis = null;
		Map<String, Object> data = null;
		try {
			jedis = getJedis();
			Map<byte[], byte[]> map = jedis.hgetAll(serialize(cacheNamePre
					+ key));
			if (map != null && map.size() != 0) {
				data = new HashMap<String, Object>();
				for (Entry<byte[], byte[]> entry : map.entrySet()) {
					Object obj = deserialize(entry.getValue());
					String mkey = deserialize(entry.getKey());
					data.put(mkey, obj);
				}
			}
		} catch (Exception e) {
			logger.error("Can't hgetAll object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return data;
	}

	/**
	 * 返回指定hash中的所有存储名字,类似Map中的keySet方法
	 * 
	 * @param String
	 *            key
	 * @return Set<String> 存储名称的集合
	 * */
	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		Set<String> keys = null;
		try {
			jedis = getJedis();
			Set<byte[]> data = jedis.hkeys(serialize(cacheNamePre + key));
			if (data != null && data.size() > 0) {
				keys = new HashSet<String>();
				for (byte[] arr : data) {
					String s = deserialize(arr);
					keys.add(s);
				}
			}
		} catch (Exception e) {
			logger.error("Can't hkeys object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return keys;
	}

	/**
	 * 获取hash中存储的个数，类似Map中size方法
	 * 
	 * @param String
	 *            key
	 * @return long 存储的个数
	 * */
	public long hlen(String key) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis jedis = null;
		long len = 0;
		try {
			jedis = getJedis();
			len = jedis.hlen(serialize(cacheNamePre + key));
		} catch (Exception e) {
			logger.error("Can't hlen object from redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
		return len;
	}

	public void clear() {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.flushDB();
		} catch (Exception e) {
			logger.error("Can't clear redis cache.", e);
		} finally {
			returnJedis(jedis);
		}
	}

}
