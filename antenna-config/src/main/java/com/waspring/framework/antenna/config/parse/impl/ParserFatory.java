package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.IParser;

/**
 * 解析器工厂
 * @author felly
 *
 */
public class ParserFatory {

	
	/**
	 * 
	 * @return
	 */
	public static    IParser   parserFactory() {
		IParser parser=new XMLParser();
		//TODO 
		//parser.next(other parser);
		return parser;
	}
}
