package com.waspring.framework.antenna.access.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.util.LogUtil;

/**
 * 接口受理如果为servlet，那么配置这个类，并指定配置文件.<br/>
 * web.xml配置参数：<br/>
 * <context-param> <br/>
 * <param-name>contextConfigLocation</param-name><br/>
 * <param-value><br/>
 * /WEB-INF/config/applicationContext.xml<br/>
 * </param-value><br/>
 * </context-param><br/>
 * 
 * <listener><br/>
 * <listener-class><br/>
 * com.hu8hu.framework.antenna.access.servlet.AntennaListener
 * </listener-class><br/>
 * </listener><br/>
 * 
 * @author felly
 *
 */
public class AntennaListener implements ServletContextListener {
	protected Logger log = Logger.getLogger(getClass());
	public static final String CONFIG_LOCATION_PARAM = "atennaConfigLocation";
	public static final String CONFIG_LOCATION_PROPERTY = "atennaPropertyLocation";

	public AntennaListener() {

	}

	private String getConfigLocation(ServletContext context) {
		String localPath = context.getInitParameter(CONFIG_LOCATION_PARAM);
		if (StringUtils.isEmpty(localPath)) {
			return "classpath://config.xml";
		}
		return localPath;
	}

	private String getPropertyLocation(ServletContext context) {
		String localPath = context.getInitParameter(CONFIG_LOCATION_PROPERTY);
		if (StringUtils.isEmpty(localPath)) {
			return "classpath://root.properties";
		}
		return localPath;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LogUtil.debug(log, "初始化应用开始！");
		ServletContext context = sce.getServletContext();
		PropertiesUtil.loadConfig(getPropertyLocation(context));
		String localPath = getConfigLocation(context);
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath(localPath);
		// 初始化application
		initApplication(application);
		LogUtil.debug(log, "初始化应用成功！");
	}

	/**
	 * //初始化application
	 * 
	 * @param application
	 */
	private void initApplication(IApplication application) {
		try {
			LifecycleUtils.init(application);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			LifecycleUtils.destory(ApplicationUtil.getApplication());
		} catch (Exception e) {
			e.printStackTrace();
			///
		}
		LogUtil.debug(log, "销毁应用成功！");
	}

}
