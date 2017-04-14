package com.whotel.common.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES Coder<br/>
 * secret key length: 128bit, default: 128 bit<br/>
 * mode: ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 * @author
 * 
 */
public class AESCoder {

	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static Key key;

	public AESCoder() {
		key = toKey(initSecretKey());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	private byte[] initSecretKey() {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		// 初始化此密钥生成器，使其具有确定的密钥大小
		// AES 要求密钥长度为 128
		kg.init(128);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key - 二进制密钥
	 * @return 密钥
	 */
	private Key toKey(byte[] key) {
		// 生成密钥
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data - 待加密数据
	 * @param key - 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public String encrypt(String data) throws Exception {
		return Base64.encodeBase64String(encrypt(data.getBytes()));
	}

	/**
	 * 加密
	 * 
	 * @param data - 待加密数据
	 * @param key - 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data - 待解密数据
	 * @return 解密数据
	 * @throws Exception
	 */
	public String decrypt(String data) throws Exception {
		return new String(decrypt(Base64.decodeBase64(data)));
	}

	/**
	 * 解密
	 * 
	 * @param data - 待解密数据
	 * @param key - 二进制密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	public static Key getKey() {
		return key;
	}

	public static void setKey(Key key) {
		AESCoder.key = key;
	}

}
