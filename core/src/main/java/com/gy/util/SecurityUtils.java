package com.gy.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {
	public static byte[] encryptWithRSA(byte[] key, byte[] plainText)
			throws GeneralSecurityException {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(1, publicKey);

		byte[] cipherText = cipher.doFinal(plainText);

		return cipherText;
	}

	public static byte[] decryptWithRSA(Key key, byte[] cipherText)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(2, key);
		byte[] decryptPlainText = cipher.doFinal(cipherText);

		return decryptPlainText;
	}

	public static final String encryptWithAES(byte[] key, String data)
			throws GeneralSecurityException {
		if (data == null)
			throw new IllegalArgumentException("data is null.");
		try {
			return Base64.encode(encryptWithAES(key, data.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}

	public static final byte[] encryptWithAES(byte[] key, byte[] data)
			throws GeneralSecurityException {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, skeySpec);
		byte[] cipherText = cipher.doFinal(data);

		return cipherText;
	}

	public static final byte[] decryptWithAES(byte[] key, byte[] data)
			throws GeneralSecurityException {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, skeySpec);
		byte[] decryptText = cipher.doFinal(data);

		return decryptText;
	}

	public static final String decryptWithAES(byte[] key, String data)
			throws GeneralSecurityException {
		try {
			return new String(decryptWithAES(key, Base64.decode(data)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new GeneralSecurityException(e.getMessage(), e);
		}
	}

	public static String md5(String text) {
		if (text == null)
			throw new IllegalArgumentException("text is null.");
		try {
			return Base64.encode(digest("MD5", text.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("UT-F not supported");
		}
	}

	public static byte[] digest(String algorithm, byte[] text) {
		if ((algorithm == null) || (algorithm.length() == 0)
				|| (algorithm.equalsIgnoreCase("plaintext")))
			return text;
		try {
			MessageDigest digist = MessageDigest.getInstance(algorithm);
			return digist.digest(text);
		} catch (NoSuchAlgorithmException nsae) {
			throw new InternalError(algorithm + " not supported");
		}
	}

	public static String digest2(String algorithm, byte[] text) {
		byte[] bytes = digest(algorithm, text);
		// Create Hex String
		StringBuffer hexString = new StringBuffer();
		// 字节数组转换为 十六进制 数
		for (int i = 0; i < bytes.length; i++) {
			String shaHex = Integer.toHexString(bytes[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexString.append(0);
			}
			hexString.append(shaHex);
		}
		return hexString.toString();
	}
}
