package com.tools.simulation.exception;

/**
 * 不支持的加密类型异常
 * @author majing
 * @date 2016年6月22日 下午3:13:29
 */
public class NotSupportedEncryptModeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2659417172516470471L;
	
	public NotSupportedEncryptModeException(String message){
		super(message);
	}

}
