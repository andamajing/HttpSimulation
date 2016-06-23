package com.tools.simulation.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author majing
 * @date 2016年6月10日 下午11:52:24
 * 组件配置
 */
@Root(name="component")
public class ComponentConfig {

	@Attribute
	private String id;
	
	@ElementList(inline=true,entry="interface")
	private List<InterfaceConfig> interfaces;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<InterfaceConfig> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceConfig> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public String toString() {
		return "ComponentConfig [id=" + id + ", interfaces=" + interfaces + "]";
	}

	
	
}
