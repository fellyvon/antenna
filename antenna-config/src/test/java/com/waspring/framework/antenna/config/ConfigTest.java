package com.waspring.framework.antenna.config;

import com.waspring.framework.antenna.config.io.DefaultURL;
import com.waspring.framework.antenna.config.io.IResource;
import com.waspring.framework.antenna.config.io.ResourceFactory;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IParser;
import com.waspring.framework.antenna.config.parse.impl.ConfigureApplication;
import com.waspring.framework.antenna.config.parse.impl.ParserFatory;
import com.waspring.framework.antenna.core.config.IConfigure;

public class ConfigTest {

	public static void main(String[] args) throws Exception {
		String path = "classpath://example.xml";
		IParser parser = ParserFatory.parserFactory();
		IResource res = ResourceFactory.factoryResource();/// 解析导入节点开始
		IConfigure config = parser.handle(res.getInputStream(new DefaultURL(path)));
		ConfigureApplication application = (ConfigureApplication) config;
		IConfigureContainer[] c = application.getConfigureContainer();
		System.out.println(c.length);
		for (int i = 0; i < c.length; i++) {
			IConfigureContainer ci = c[i];
			System.out.println(ci.getId() + ":" + ci.getAllowMaximum());
		}

	}

}
