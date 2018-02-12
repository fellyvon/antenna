package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 请求ID产生规则配置
 * 
 * @author felly
 * 
 *
 */
public interface IConfigureIDGenerator extends IConfigure {
	String GENERATOR_CLASS = "generatorclass";

	String getGeneratorClass();
}
