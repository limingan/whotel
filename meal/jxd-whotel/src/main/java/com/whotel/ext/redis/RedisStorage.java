package com.whotel.ext.redis;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.whotel.common.serializer.KryoTranscoder;
import com.whotel.common.serializer.Transcoder;
import com.whotel.weixin.service.WeixinMessageService;

/**
 * Redis存储管理，提供了获取连接、释放连接、连接池管理等必需的操作
 * 
 * @author
 * 
 */
public class RedisStorage {
	private static final Logger logger = LoggerFactory.getLogger(RedisStorage.class);

	private JedisPool jedisPool; // 连接缓存池
	private int dbIndex; // 数据库索引号
	private Transcoder transcoder = new KryoTranscoder(); // 序列化编码器
	
	@Autowired
	private WeixinMessageService weixinMessageService;

	/**
	 * 获取连接
	 * 
	 * @return 连接
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		int times = 0;// 获取连接次数
		int repeats = 2;// 重复次数
		Exception exception = null;
		while (jedis == null) {
			try {
				times++;
				jedis = jedisPool.getResource();
			} catch (Exception e) { // 捕捉异常
				exception = e;
				if (times <= repeats) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					break;
				}
			}
		}
		if (jedis != null) {
			jedis.select(dbIndex);
		} else {
			logger.error("get jedis from jedis pool error.", exception);
		}
		return jedis;
	}

	/**
	 * 销毁连接缓冲池
	 */
	public void destroyJedisPool() {
		if (jedisPool != null) {
			logger.info("destroy jedis pool.");
			jedisPool.destroy();
			jedisPool = null;
		}
	}

	/**
	 * 释放连接
	 * 
	 * @param jedis
	 *            - 连接
	 */
	public void returnJedis(Jedis jedis) {
		if (jedis != null) {
			try {
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				logger.error("return jedist to jedis pool error.", e);
			}
		}
	}

	/**
	 * 获取连接池
	 * 
	 * @return 连接池
	 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	/**
	 * 设置连接池
	 * 
	 * @param jedisPool
	 *            - 连接池
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	/**
	 * 获取数据库索引号
	 * 
	 * @return 数据库索引号
	 */
	public int getDbIndex() {
		return dbIndex;
	}

	/**
	 * 设置数据库索引号
	 * 
	 * @param dbIndex
	 *            - 数据库索引号
	 */
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	/**
	 * 获取序列化编码器
	 * 
	 * @return 序列化编码器
	 */
	public Transcoder getTranscoder() {
		return transcoder;
	}

	/**
	 * 设置序列化编码器
	 * 
	 * @param transcoder
	 *            - 序列化编码器
	 */
	public void setTranscoder(Transcoder transcoder) {
		this.transcoder = transcoder;
	}

	/**
	 * 进行序列化
	 * 
	 * @param attributeValue
	 * @return
	 */
	protected byte[] serialize(Object attributeValue) {
		return transcoder.serialize(attributeValue);
	}

	/**
	 * 进行反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Serializable> T deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return (T) transcoder.deserialize(bytes);
	}

}
