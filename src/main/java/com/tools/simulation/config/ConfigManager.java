package com.tools.simulation.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.tools.simulation.helper.XmlHelper;

/**
 * 配置管理器
 * 
 * @author majing
 * @date 2016年6月20日 下午8:03:31
 */
public class ConfigManager {
	private static ConfigContext configContext = null;

	private static Map<String, InterfaceConfig> interfaceConfigMap = new HashMap<String, InterfaceConfig>();
	
	private static Map<String, Random> interfaceRamdom = new HashMap<String, Random>();
	
	public static void main(String[] args) throws Exception {
		ConfigManager.init();
	}
	
	/**
	 * 根据组件名和接口名找到对应的返回包配置信息
	 * majing
	 * 2016年6月22日 下午4:50:33
	 * @param componentId
	 * @param interfaceId
	 * @return
	 */
	public static InterfaceConfig getInterfaceConfig(String componentId, String interfaceId){
		return interfaceConfigMap.get(StringUtils.trimToEmpty(componentId)+"_"+StringUtils.trimToEmpty(interfaceId));
	}
	
	/**
	 * 根据组件名和接口名找到对应的随机数生成器
	 * majing
	 * 2016年6月22日 下午4:49:53
	 * @param componentId
	 * @param interfaceId
	 * @return
	 */
	public static Random getInterfaceRandom(String componentId, String interfaceId){
		return interfaceRamdom.get(StringUtils.trimToEmpty(componentId)+"_"+StringUtils.trimToEmpty(interfaceId));
	}
	
	public static void putInterfaceRandom(String componentId, String interfaceId, Random random){
		interfaceRamdom.put(StringUtils.trimToEmpty(componentId)+"_"+StringUtils.trimToEmpty(interfaceId), random);
	}

	public static void init() throws Exception {
		String configText = getConfigStr();
		configContext = XmlHelper.getInstance().simpleFromXml(ConfigContext.class, configText);
		if (configContext != null && CollectionUtils.isNotEmpty(configContext.getComponents())) {
			for (ComponentConfig component : configContext.getComponents()) {
				String componentId = component.getId();
				if(StringUtils.isNotBlank(componentId)){
					List<InterfaceConfig> interfaces = component.getInterfaces();
					for(InterfaceConfig interfaceConfig: interfaces){
						if(StringUtils.isNotBlank(interfaceConfig.getId())){
							interfaceConfigMap.put(componentId+"_"+interfaceConfig.getId(), interfaceConfig);
						}
					}
				}
			}
		}

	}

	public static String getConfigStr() {
		InputStream is = ConfigManager.class.getResourceAsStream("/config.xml");
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(is);
			int length = 0;
			char[] buffer = new char[2048];
			StringBuilder result = new StringBuilder();
			while ((length = inputStreamReader.read(buffer, 0, 2048)) > -1) {
				result.append(buffer, 0, length);
			}
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
