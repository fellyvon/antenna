package com.waspring.framework.antenna.config.parse.impl;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.io.ITypeInputStream;
import com.waspring.framework.antenna.config.parse.INodeParse;
import com.waspring.framework.antenna.config.parse.IParser;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 抽象解析
 * 
 * @author felly
 *
 */
public abstract class AbstractParser implements IParser {
	private IParser next;

	@Override
	public IParser next(IParser parser) {
		this.next = parser;
		return parser;
	}

	@Override
	public abstract String supportFormat();

	@Override
	public IConfigure handle(ITypeInputStream resouese) throws NoParseException {
		if (StringUtils.stripToEmpty(supportFormat()).equals(resouese.getFileFormat())) {
			try {
				return innerhandle(resouese.getInputStream());
			} catch (Exception e) {
				throw new NoParseException(e);
			}
		} else {
			if (next != null) {
				return next.handle(resouese);
			} else {
				throw new NoParseException("不能发现parse！");
			}
		}

	}

	/**
	 * 实际的执行方法
	 * 
	 * @param resouese
	 * @return
	 */
	public abstract IConfigure innerhandle(InputStream in) throws Exception;
	
	public abstract INodeParse getINodeParse() throws Exception;

}
