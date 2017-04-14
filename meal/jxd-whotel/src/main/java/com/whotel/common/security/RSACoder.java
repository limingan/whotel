package com.whotel.common.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;

import com.whotel.common.util.EncodeUtil;

/**
 * RSA非对称加密内容长度有限制，1024位key的最多只能加密127位数据，
 * 否则就会报错(javax.crypto.IllegalBlockSizeException: Data must not be longer than 117 bytes)
 * 解决办法是用对称加密(AES/DES etc)加密数据，然后用RSA公钥加密对称加密的密钥，用RSA的私钥解密得到对称加密的密钥，然后完成反向操纵得到明文。
 * 
 */
public abstract class RSACoder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data - 加密数据
	 * @param privateKey - 私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = EncodeUtil.decodeBase64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return EncodeUtil.encodeBase64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data - 加密数据
	 * @param publicKey - 公钥
	 * @param sign - 数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		// 解密由base64编码的公钥
		byte[] keyBytes = EncodeUtil.decodeBase64(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(EncodeUtil.decodeBase64(sign));
	}

	// 私钥处理
	private static byte[] procByPrivateKey(byte[] data, String key, int mode) throws Exception {
		// 对密钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(mode, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		return procByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		return procByPrivateKey(data, key, Cipher.DECRYPT_MODE);
	}

	// 公钥处理
	private static byte[] procByPublicKey(byte[] data, String key, int mode) throws Exception {
		// 对公钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(mode, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		return procByPublicKey(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		return procByPublicKey(data, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return EncodeUtil.encodeBase64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return EncodeUtil.encodeBase64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 公钥加密超过长度限制的数据(采用分段处理方式)
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptLongByPublicKey(byte[] data, String key) throws Exception {
		byte[] dataReturn = new byte[] {};
		// 对公钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		for (int i = 0; i < data.length; i += 117) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 117));
			dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}

		return dataReturn;
	}

	/**
	 * 私钥解密超过长度限制的数据(采用分段处理方式)
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptLongByPrivateKey(byte[] data, String key) throws Exception {
		byte[] dataReturn = new byte[] {};
		// 对密钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		for (int i = 0; i < data.length; i += 128) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
			dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}
		return dataReturn;
	}

	/**
	 * 私钥加密超过长度限制的数据(采用分段处理方式)
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptLongByPrivateKey(byte[] data, String key) throws Exception {
		byte[] dataReturn = new byte[] {};
		// 对密钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		for (int i = 0; i < data.length; i += 128) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
			dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}
		return dataReturn;
	}

	/**
	 * 公钥解密超过长度限制的数据(采用分段处理方式)
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptLongByPublicKey(byte[] data, String key) throws Exception {
		byte[] dataReturn = new byte[] {};
		// 对公钥解密
		byte[] keyBytes = EncodeUtil.decodeBase64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		for (int i = 0; i < data.length; i += 117) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 117));
			dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}

		return dataReturn;
	}

}