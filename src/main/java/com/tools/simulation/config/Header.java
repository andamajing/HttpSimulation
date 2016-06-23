package com.tools.simulation.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 返回的头部
 * @author majing
 * @date 2016年6月20日 下午6:46:19
 */
@Root(name="header")
public class Header {
	/**
	 * header名称
	 */
	@Element
	private String name;
	/**
	 * header名称对应的值
	 */
	@Element
	private String value;
	
	public Header(){}
	
	public Header(String name, String value){
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Header [name=" + name + ", value=" + value + "]";
	}
	
	
}
