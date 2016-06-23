package com.tools.simulation.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

/**
 * 用于缓存被访问过的配置文件
 * @author majing
 * @date 2016年6月21日 下午7:55:52
 */
public class ConfigCacheManager {
	/**
	 * 以文件名为key，文件中的内容为value
	 */
	private static Map<String,String> configCache  = new ConcurrentHashMap<String,String>();
	
	public static void put(String configFileName, String content){
		if(StringUtils.isNotBlank(configFileName)&&StringUtils.isNotBlank(content)){
			configCache.put(configFileName.trim(), content.trim());
		}
	}
	
	public static String get(String configFileName){
		return configCache.get(configFileName);
	}
}
