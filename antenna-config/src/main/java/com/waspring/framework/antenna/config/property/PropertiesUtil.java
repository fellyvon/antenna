package com.waspring.framework.antenna.config.property;

import java.util.Properties;

import com.waspring.framework.antenna.config.io.DefaultURL;
import com.waspring.framework.antenna.config.io.ITypeInputStream;
import com.waspring.framework.antenna.config.io.IURL;
import com.waspring.framework.antenna.config.io.ResourceFactory;

/**
 * 属性获取工具类
 * 
 * @author felly
 *
 */
public abstract class PropertiesUtil {
	public static final String DEBUG = "debug";
	public static final String KEY = "key";

	public static final String SECRET = "secret";

	public static final String LOGDIR = "logdir";
	private static Properties p;
	static {
		p = new Properties();
	}

	/**
	 * 通过url加载配置。url 可以是 classpath，file,http
	 * 
	 * @param url
	 */
	public static void loadConfig(String url) {
		IURL configURL = new DefaultURL(url);
		ITypeInputStream in = null;
		try {
			in = ResourceFactory.factoryResource().getInputStream(configURL);
			p.load(in.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载配置文件失败：" + url);
			return;
		}
		System.out.println("加载配置文件成功：" + url);
	}

	public static String getProperty(String key) {
		return p.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	public static String getKey() {
		return getProperty(PropertiesUtil.KEY);
	}

	public static String getSecret() {
		return getProperty(PropertiesUtil.SECRET);
	}

	public static String getLogdir() {
		return getProperty(PropertiesUtil.LOGDIR);
	}

	public static boolean isDebug() {
		String debug = getProperty(PropertiesUtil.DEBUG);
		return "true".equals(debug);
	}
}
