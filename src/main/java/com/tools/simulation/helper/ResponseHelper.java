package com.tools.simulation.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.tools.simulation.config.ConfigCacheManager;
import com.tools.simulation.config.ConfigManager;
import com.tools.simulation.config.Header;
import com.tools.simulation.config.InterfaceConfig;
import com.tools.simulation.config.ResponseConfig;
import com.tools.simulation.constants.CompressMode;
import com.tools.simulation.constants.EncryptMode;
import com.tools.simulation.exception.NotSupportedCompressModeException;
import com.tools.simulation.exception.NotSupportedEncryptModeException;
import com.tools.simulation.utils.DESUtils;
import com.tools.simulation.utils.GZipUtils;
import com.tools.simulation.utils.IOUtils;

/**
 * 返回包帮助类
 * 
 * @author majing
 * @date 2016年6月20日 下午10:21:36
 */
public class ResponseHelper {
	/**
	 * majing 2016年6月20日 下午10:25:54
	 * 
	 * @param componentId
	 *            组件名
	 * @param interfaceId
	 *            接口名
	 * @param headerMap
	 *            头部字段
	 * @return
	 * @throws NotSupportedEncryptModeException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws NotSupportedCompressModeException
	 * @throws IOException
	 */
	public static byte[] buildResponseBytes(String componentId, String interfaceId, Map<String, String> headerMap)
			throws NotSupportedEncryptModeException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
			NotSupportedCompressModeException, IOException {
		InterfaceConfig interfaceConfig = ConfigManager.getInterfaceConfig(componentId, interfaceId);
		if (interfaceConfig == null) {
			return new byte[0];
		}
		ResponseConfig respConfig = interfaceConfig.getResponse();
		if (respConfig == null || CollectionUtils.isEmpty(respConfig.getResponsetags())) {
			return new byte[0];
		}

		// 处理头部
		List<Header> headers = respConfig.getHeaders();
		if (headerMap != null && CollectionUtils.isNotEmpty(headers)) {
			for (Header header : headers) {
				if (header != null && StringUtils.isNotBlank(header.getName())
						&& StringUtils.isNotBlank(header.getValue())) {
					headerMap.put(StringUtils.trim(header.getName()), StringUtils.trim(header.getValue()));
				}
			}
		}
		// 处理返回报文
		List<String> responsetags = respConfig.getResponsetags();
		String rate = respConfig.getRate();
		boolean cachable = StringUtils.equalsIgnoreCase(respConfig.getCachable(), "TRUE");
		String[] strRateArray = StringUtils.split(rate, ",");
		Integer[] intRateArray = convertToIntRate(strRateArray);

		byte[] contentBytes = null;
		String contentString = null;
		if (responsetags.size() > 0) {
			if (intRateArray != null && intRateArray.length == 1 && intRateArray[0] == 100) {
				if (cachable) {
					contentString = ConfigCacheManager.get(responsetags.get(0));
				}
				if (contentString == null) {
					try {
						contentBytes = IOUtils.read(responsetags.get(0));
						contentString = new String(contentBytes, "UTF-8");
						if (cachable) {
							ConfigCacheManager.put(responsetags.get(0), contentString);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					contentBytes = contentString.getBytes("UTF-8");
				}
			} else {
				// 按照配置返回不同的报文
				Random random = ConfigManager.getInterfaceRandom(componentId, interfaceId);
				if (random == null) {
					random = new Random(System.currentTimeMillis());
					ConfigManager.putInterfaceRandom(componentId, interfaceId, random);
				}
				int randomNum = random.nextInt(100);
				int i = 0;
				for (; i < intRateArray.length; i++) {
					if (randomNum < intRateArray[i]) {
						break;
					}
				}
				if (cachable) {
					contentString = ConfigCacheManager.get(responsetags.get(i));
				}
				if (contentString == null) {
					try {
						contentBytes = IOUtils.read(responsetags.get(i));
						contentString = new String(contentBytes, "UTF-8");
						if (cachable) {
							ConfigCacheManager.put(responsetags.get(i), contentString);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					contentBytes = contentString.getBytes("UTF-8");
				}
			}
		}

		// 加密压缩处理
		boolean isNeedEncrypt = interfaceConfig.isEncrypt();// 是否加密
		String encryptMode = interfaceConfig.getEncryptmode();// 加密模式
		String encryptKey = interfaceConfig.getEncryptkey();// 加密秘钥
		boolean isNeedCompress = interfaceConfig.isCompress();// 是否压缩
		String compressMode = interfaceConfig.getCompressmode();// 压缩模式
		if (isNeedEncrypt) {
			if (StringUtils.equalsIgnoreCase(encryptMode, EncryptMode.DES)) {
				contentBytes = DESUtils.encrypt(contentBytes, encryptKey.getBytes("UTF-8"));
			} else {
				throw new NotSupportedEncryptModeException("不支持的加密模式");
			}
		}

		if (isNeedCompress) {
			if (StringUtils.equalsIgnoreCase(compressMode, CompressMode.GZIP)) {
				contentBytes = GZipUtils.compress(contentBytes);
			} else {
				throw new NotSupportedCompressModeException("不支持的压缩模式");
			}
		}

		return contentBytes;
	}

	private static Integer[] convertToIntRate(String[] strRateArray) {
		Integer[] intRateArray = null;
		if (strRateArray == null) {
			intRateArray = new Integer[1];
			intRateArray[0] = 100;
		}
		intRateArray = new Integer[strRateArray.length];
		int total = 0;
		try {
			for (int i = 0; i < strRateArray.length; i++) {
				int temp = Integer.parseInt(strRateArray[i]);
				if (i > 0) {
					intRateArray[i] = intRateArray[i - 1] + temp;
				} else {
					intRateArray[i] = temp;
				}
				total = total + temp;
			}
			if (total != 100) {
				intRateArray = new Integer[1];
				intRateArray[0] = 100;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			intRateArray = new Integer[1];
			intRateArray[0] = 100;
		}

		return intRateArray;
	}
}
