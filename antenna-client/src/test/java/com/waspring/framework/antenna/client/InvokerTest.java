
package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;

/**
 * 
 * @author felly
 *
 */
public class InvokerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PropertiesUtil.loadConfig("classpath://root.properties");
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath("classpath://config.xml");
		LifecycleUtils.init(application);
		IVisitorContainer container = ApplicationUtil.getApplication().applyContainer("antennaContainer");
		IRequest request = ApplicationUtil.instanceClientRequest(container.getId(), new TestBean());
		IVisitorAware visitor = null;
		visitor = container.establishVisitor(request);
		System.out.println(visitor.getResponse());
	 
		LifecycleUtils.destory(application);
	}

}
