package com.whotel.ext.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.whotel.common.serializer.KryoTranscoder;
import com.whotel.common.serializer.Transcoder;
import com.whotel.ext.redis.RedisStorage;

/**
 * 基于Redis存储机制的Session管理器
 * 
 * @author 冯勇
 * 
 */
public class RedisSessionManager extends RedisStorage implements SessionManager {
	private Transcoder transcoder = new KryoTranscoder();

	private RedisSessionManager() {
	}

	@Override
	public Map<String, Serializable> getSession(String sessionId) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Map<byte[], byte[]> attributesByte = jedis.hgetAll(serializeSessionId(sessionId));
			jedis.expire(serializeSessionId(sessionId), HttpSessionWrapper.SESSION_EXPIRED_SECEND);
			if (attributesByte != null) {
				Map<String, Serializable> sessionAttributes = new HashMap<String, Serializable>(attributesByte.size());
				for (Map.Entry<byte[], byte[]> entry : attributesByte.entrySet()) {
					sessionAttributes.put(deserializeAttributeName(entry.getKey()),
							(Serializable) deserializeAttributeValue(entry.getValue()));
				}
				return sessionAttributes;
			}
		} finally {
			returnJedis(jedis);
		}
		return new HashMap<String, Serializable>();
	}

	@Override
	public void setAttribute(final String sessionId, final String attributeName, final Object attributeValue) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hset(serializeSessionId(sessionId), serializeAttributeName(attributeName),
					serializeAttributeValue(attributeValue));
			jedis.expire(serializeSessionId(sessionId), HttpSessionWrapper.SESSION_EXPIRED_SECEND);
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public Object getAttribute(final String sessionId, final String attributeName) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] value = jedis.hget(serializeSessionId(sessionId), serializeAttributeName(attributeName));
			jedis.expire(serializeSessionId(sessionId), HttpSessionWrapper.SESSION_EXPIRED_SECEND);
			return deserializeAttributeValue(value);
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public Enumeration<String> getAttributeNames(String sessionId) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<byte[]> keyByteArray = jedis.hkeys(serializeSessionId(sessionId));
			jedis.expire(serializeSessionId(sessionId), HttpSessionWrapper.SESSION_EXPIRED_SECEND);
			Set<String> names = new HashSet<String>(keyByteArray.size());
			for (byte[] keyByte : keyByteArray) {
				names.add(deserializeAttributeName(keyByte));
			}
			return new Enumerator<String>(names);
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public void removeAttribute(final String sessionId, final String attributeName) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hdel(serializeSessionId(sessionId), serializeAttributeName(attributeName));
			jedis.expire(serializeSessionId(sessionId), HttpSessionWrapper.SESSION_EXPIRED_SECEND);
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public void removeSession(String sessionId) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(serializeSessionId(sessionId));
		} finally {
			returnJedis(jedis);
		}
	}

	protected byte[] serializeSessionId(String sessionId) {
		return sessionId.getBytes();
	}

	protected byte[] serializeAttributeName(String attributeName) {
		return attributeName.getBytes();
	}

	protected String deserializeAttributeName(byte[] bytes) {
		return new String(bytes);
	}

	protected byte[] serializeAttributeValue(Object attributeValue) {
		return transcoder.serialize(attributeValue);
	}

	protected Object deserializeAttributeValue(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return transcoder.deserialize(bytes);
	}

}
