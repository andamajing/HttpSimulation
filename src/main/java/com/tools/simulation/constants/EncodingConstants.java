
package com.tools.simulation.constants;


/**
 * 编码类型常量枚举
 * @author majing
 * @date 2016年6月21日 下午1:51:54
 */
public enum EncodingConstants {
	/** UTF-8 */
	UTF8("UTF-8"),

	/** GB2312 */
	GB2312("GB2312"),

	/** GBK */
	GBK("GBK");

	/** value */
	private String value;

	/**
	 * constructor
	 * 
	 * @param value
	 *            value
	 */
	private EncodingConstants(String value) {
		this.value = value;
	}

	/**
	 * getter method
	 * 
	 * @see EncodingConstants#value
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
