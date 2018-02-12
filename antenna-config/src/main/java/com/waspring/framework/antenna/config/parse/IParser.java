package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.config.io.ITypeInputStream;
import com.waspring.framework.antenna.config.parse.impl.NoParseException;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 解析器抽象
 * 
 * @author felly
 *
 */
public interface IParser {
	/***
	 * 通过资源解析出来具体的配置对象
	 * 
	 * @param resouese
	 * @return
	 */
	IConfigure handle(ITypeInputStream resouese) throws NoParseException;

	
	String supportFormat();////

	IParser next(IParser parser);
	
	
}
