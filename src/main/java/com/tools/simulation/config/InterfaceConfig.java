package com.tools.simulation.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author majing
 * @date 2016年6月10日 下午11:47:18
 * 接口配置
 */
@Root(name="interface")
public class InterfaceConfig {
	@Attribute
	private String id;
	@Element
	private boolean encrypt;
	@Element(required=false)
	private String encryptmode;
	@Element(required=false)
	private String encryptkey;
	@Element
	private boolean compress;
	@Element(required=false)
	private String compressmode;
	@Element
	private ResponseConfig response;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isEncrypt() {
		return encrypt;
	}
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	public String getEncryptmode() {
		return encryptmode;
	}
	public void setEncryptmode(String encryptmode) {
		this.encryptmode = encryptmode;
	}
	public String getEncryptkey() {
		return encryptkey;
	}
	public void setEncryptkey(String encryptkey) {
		this.encryptkey = encryptkey;
	}
	public boolean isCompress() {
		return compress;
	}
	public void setCompress(boolean compress) {
		this.compress = compress;
	}
	public String getCompressmode() {
		return compressmode;
	}
	public void setCompressmode(String compressmode) {
		this.compressmode = compressmode;
	}
	public ResponseConfig getResponse() {
		return response;
	}
	public void setResponse(ResponseConfig response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "InterfaceConfig [id=" + id + ", encrypt=" + encrypt + ", encryptmode=" + encryptmode + ", encryptkey="
				+ encryptkey + ", compress=" + compress + ", compressmode=" + compressmode + ", response=" + response
				+ "]";
	}
	
	
}
