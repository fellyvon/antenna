package com.waspring.framework.antenna.config;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.waspring.framework.antenna.config.io.DefaultURL;
import com.waspring.framework.antenna.config.io.IResource;
import com.waspring.framework.antenna.config.io.ResourceFactory;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IParser;
import com.waspring.framework.antenna.config.parse.impl.ConfigureApplication;
import com.waspring.framework.antenna.config.parse.impl.ParserFatory;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 全局配置获取入口
 * 
 * @author felly
 *
 */
public class Configuration implements IConfiguration {

	private IConfigureApplication application = null;
	private String configPath;// 配置文件的路径，外部输入

	@Override
	public void destory() {

	}

	@Override
	public void init() throws Exception {
		IParser parser = ParserFatory.parserFactory();
		IResource res = ResourceFactory.factoryResource();/// 解析导入节点开始
		IConfigure config = parser.handle(res.getInputStream(new DefaultURL(getConfigPath())));
		application = (ConfigureApplication) config;
	}

	private Lock lock = new ReentrantLock();

	public IConfigureApplication getConfigureApplication() {
		if (application == null) {
			try {
				lock.lock();
				init();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		return application;
	}

	public Configuration(String configPath) {
		setConfigPath(configPath);
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

}
