package com.tools.simulation.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author majing
 * @date 2016年6月10日 下午11:39:49
 * 接口返回报文配置
 */
@Root(name="response")
public class ResponseConfig {
	@Attribute(required=false)
	private String rate = "100";
	@Attribute(required=false)
	private String cachable = "true";
	@ElementList
	private List<Header> headers;
	@ElementList(entry="responsetag")
	private List<String> responsetags;
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getCachable() {
		return cachable;
	}
	public void setCachable(String cachable) {
		this.cachable = cachable;
	}
	
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public List<String> getResponsetags() {
		return responsetags;
	}
	public void setResponsetags(List<String> responsetags) {
		this.responsetags = responsetags;
	}
	@Override
	public String toString() {
		return "ResponseConfig [rate=" + rate + ", cachable=" + cachable + ", headers=" + headers + ", responsetags="
				+ responsetags + "]";
	}
	
	
	
	
}
