package com.tools.simulation.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * DES加密工具类。
 * 
 */
public class DESUtils {

	private static final String ALGORITHM_STR = "DES/ECB/PKCS5Padding";

	public static byte[] encrypt(byte[] expresslyBytes, byte[] rawKey) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException {

		DESKeySpec dkSpec = new DESKeySpec(rawKey);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dkSpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM_STR);

		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(expresslyBytes);

	}

	public static byte[] dencrypt(byte[] ciphertextBytes, byte[] rawKey) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException {

		DESKeySpec dkSpec = new DESKeySpec(rawKey);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dkSpec);

		Cipher cipher = Cipher.getInstance(ALGORITHM_STR);

		cipher.init(Cipher.DECRYPT_MODE, key);

		return cipher.doFinal(ciphertextBytes);
	}

	
}
