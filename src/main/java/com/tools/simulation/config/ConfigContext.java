package com.tools.simulation.config;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author majing
 * @date 2016年6月10日 下午11:36:39
 * 配置上下文
 */
@Root(name="config")
public class ConfigContext {
	
	@ElementList(inline=true,entry="component")
	private List<ComponentConfig> components;

	public List<ComponentConfig> getComponents() {
		return components;
	}

	public void setComponents(List<ComponentConfig> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "ConfigContext [components=" + components + "]";
	}
	
}
