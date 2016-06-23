package com.tools.simulation.exception;

/**
 * 不支持的压缩模式
 * @author majing
 * @date 2016年6月22日 下午3:16:17
 */
public class NotSupportedCompressModeException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4429216642164185158L;
	
	public NotSupportedCompressModeException(String message){
		super(message);
	}

}
