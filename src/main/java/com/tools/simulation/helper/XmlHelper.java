package com.tools.simulation.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.simpleframework.xml.core.Persister;

public class XmlHelper {
	
	private static final Persister PERSISTER = new Persister();
	
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
	
	private static XmlHelper instance;
	
	private XmlHelper(){}
	
	public static XmlHelper getInstance(){
		if(instance==null){
			instance =new XmlHelper();
		}
		return instance;
	}
	
	/*********************************************************/
	
	public String simpleToXml(Object object){
		return simpleToXml(object,"utf-8",false);
	}
	
	public String simpleToXml(Object object, String charset){
		return simpleToXml(object,charset,false);
	}
	
	public String simpleToXml(Object object, String charset, boolean adddecleration){
		ByteArrayOutputStream baos = null;
		try{
			baos = new ByteArrayOutputStream();
			PERSISTER.write(object, baos, charset);
			byte[] bytes = baos.toByteArray();
			String content = new String(bytes, charset);
			return adddecleration?(XML_HEADER+content):content;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(baos!=null){
				try{
					baos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	
	public <T> T simpleFromXml(Class<T> clazz, String xml) throws Exception{
		return simpleFromXml(clazz, xml, "utf-8", false);
	}
	
	public <T> T simpleFromXml(Class<T> clazz, String xml, String charset) throws Exception{
		return simpleFromXml(clazz, xml, charset, false);
	}
	
	public <T> T simpleFromXml(Class<T> clazz, String xml, String charset, boolean strict) throws Exception{
		InputStream is = new ByteArrayInputStream(xml.getBytes(charset));
		return (T)PERSISTER.read(clazz,  is, strict);
	}
	
	
}
