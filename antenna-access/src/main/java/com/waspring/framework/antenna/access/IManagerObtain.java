package com.waspring.framework.antenna.access;

import com.waspring.framework.antenna.core.hander.IFilterFactory;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.visitor.IVisitorContainerFactory;

/**
 * 管理者获取
 * 
 * @author felly
 *
 */
public interface IManagerObtain {

	IHanderFactory getInvokerFactory();

	IHanderFactory getProviderFactory();

	IVisitorContainerFactory getVisitorFactory();

	IQueueManagerFactory getQueueManagerFactory();

	IFilterFactory getFilterFactory();
}
