package com.waspring.framework.antenna.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.service.IServiceContainer;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 
 * @author felly
 *
 */
public abstract class AbstractServiceContainer extends AbstractService implements IServiceContainer {

	@Override
	public IService[] getChildServices() {
		List<IService> list = new ArrayList<IService>();
		expandChildren(list, null);
		return list.toArray(new IService[list.size()]);
	}

	@Override
	public IService[] getChildServicesByClass(Class<?> byclass) {
		List<IService> list = new ArrayList<IService>();
		expandChildren(list, byclass);
		return list.toArray(new IService[list.size()]);
	}

	@Override
	public <T extends IService> T getChildServiceByClass(Class<T> byclass) {
		List<IService> list = new ArrayList<IService>();
		expandChildren(list, byclass);
		if (list.isEmpty())
			return null;
		return (T) list.get(0);
	}

	@Override
	public String getServiceName() {
		return getClass().getName();
	}

	@Override
	public abstract void service(IRequest request, IResponse reponse) throws  Exception;

	protected void expandHandler(IService handler, List<IService> list, Class<?> byClass) {
		if (handler == null)
			return;

		if (byClass == null || byClass.isAssignableFrom(handler.getClass()))
			list.add(handler);

		if (handler instanceof AbstractServiceContainer)
			((AbstractServiceContainer) handler).expandChildren(list, byClass);
		else if (handler instanceof IServiceContainer) {
			IServiceContainer container = (IServiceContainer) handler;
			IService[] handlers = byClass == null ? container.getChildServices()
					: container.getChildServicesByClass(byClass);
			list.addAll(Arrays.asList(handlers));
		}
	}

	/* ------------------------------------------------------------ */
	protected void expandChildren(List<IService> list, Class<?> byClass) {
	}
}
