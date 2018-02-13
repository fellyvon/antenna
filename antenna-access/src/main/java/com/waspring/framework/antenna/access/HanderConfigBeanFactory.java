package com.waspring.framework.antenna.access;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.hander.IHanderListener;
import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.ReflectUtil;
import com.waspring.framework.antenna.core.util.VisitorUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 从配置数据中创建hander
 * 
 * @author felly
 *
 */
@SuppressWarnings("rawtypes")
public class HanderConfigBeanFactory<T extends IHander> implements IConfigBeanFactory {
	private Logger log = Logger.getLogger(HanderConfigBeanFactory.class);
	private IApplication application = null;
	private IHanderFactory handerFactory = null;

	public HanderConfigBeanFactory(IApplication application, IHanderFactory handerFactory) {
		this.application = application;
		this.handerFactory = handerFactory;
	}

	/**
	 * TODO缓存Hander,那么要考虑并发
	 */
	public T create(java.io.Serializable id) {
		T hander = null;

		if (id instanceof HanderKey) {
			HanderKey key = (HanderKey) id;
			String containerId = key.getContainerId();
			String action = key.getAction();
			IConfigureApplication configure = (IConfigureApplication) application.getRootConfigure();
			if (configure != null) {
				try {

					if (VisitorUtil.getVisitor().getRequest().isServer()) {/// 服务端请求的情况下
						IConfigureProvider configureProvider = configure.getIConfigProvider(containerId, action);
						if (configureProvider == null) {
							LogUtil.warn(log, "action=" + action + "对应的受理者处理程序配置不存在");
							return null;
						}
						String providerClass = configureProvider.getProviderClass();
						if (!StringUtils.isEmpty(providerClass)) {/// 存在class配置就反射建立
							hander = ReflectUtil.<T>constructorNewInstance(providerClass,
									new Class[] { IConfigureProvider.class }, new Object[] { configureProvider });
						} else {//// 否则采用匿名实现
							hander = (T) new AbstractProviderHander(configureProvider) {
								@Override
								public void rollback(IRequest request) {

								}

							};
						}
						servieHandle(hander, configureProvider.getServiceClass());
						listenerHandle(hander,configureProvider.getListener());
						handerFactory.callback(hander, configureProvider);
						////// service的处理

					} else {/// 客户端请求
						IConfigureInvoker configureInvoker = configure.getIConfigInvoker(containerId, action);
						if(configureInvoker==null) {
							LogUtil.warn(log, "action=" + action + "对应的调用者处理程序配置不存在");
							return null;
						}
						String invokerClass = configureInvoker.getInvokerClass();
						if (!StringUtils.isEmpty(invokerClass)) {/// 存在class配置就反射建立
							hander = ReflectUtil.<T>constructorNewInstance(invokerClass,
									new Class[] { IConfigureInvoker.class }, new Object[] { configureInvoker });

						} else {//// 否则采用匿名实现
							hander = (T) new AbstractInvokerHander(configureInvoker) {
								@Override
								public void rollback(IRequest request) {
								}

								@Override
								public boolean callResultHandle(IResponse reponse) throws Exception {
									return true;
								}

							};
						}
						invokerHandle(hander, configureInvoker.getServiceClass());
						listenerHandle(hander,configureInvoker.getListener());
						handerFactory.callback(hander, configureInvoker);
						////// service的处理
					}

				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				} finally {
					try {
						LifecycleUtils.init(hander);
					} catch (Exception e) {

					}
				}
			}
		}

		return hander;

	}

	private void listenerHandle(T hander, String listenerClass) {
		if (hander == null) {
			return;
		}

		if (StringUtils.isEmpty(listenerClass)) {/// 如果没有配置listener 那么直接返回
			return;
		}
		IHanderListener listener = Application.getApplication().<String, IHanderListener>getCache(LISTENER_CACHE_NAME)
				.get(listenerClass);
		if (listener == null) {
			listener = ReflectUtil.<IHanderListener>constructorNewInstance(listenerClass, new Class[] {},
					new Object[] {});
			Application.getApplication().<String, IHanderListener>getCache(LISTENER_CACHE_NAME).put(listenerClass,
					listener);

		}
		if (listener != null) {
			hander.registerHanderListener(listener);
		}
	}

	private void servieHandle(T hander, String serviceClass) {
		if (hander == null) {
			return;
		}

		if (StringUtils.isEmpty(serviceClass)) {/// 如果没有配置service 那么直接返回
			return;
		}
		IService service = Application.getApplication().<String, IService>getCache(SERVICE_CACHE_NAME)
				.get(serviceClass);
		if (service == null) {
			service = ReflectUtil.<IService>constructorNewInstance(serviceClass, new Class[] {}, new Object[] {});
			Application.getApplication().<String, IService>getCache(SERVICE_CACHE_NAME).put(serviceClass, service);

		}
		if (service != null) {
			hander.registerService(service);
		}
	}

	/**
	 * 考虑到性能，这里采用了cache来缓存反射的对象
	 * 
	 * @param hander
	 * @param serviceClass
	 */
	private void invokerHandle(T hander, String serviceClass) {
		if (hander == null) {
			return;
		}

		if (StringUtils.isEmpty(serviceClass)) {/// 如果没有配置service 那么直接返回
			return;
		}

		IService service = Application.getApplication().<String, IService>getCache(SERVICE_CACHE_NAME)
				.get(serviceClass);
		if (service == null) {
			service = ReflectUtil.<IService>constructorNewInstance(serviceClass, new Class[] { Invoker.class },
					new Object[] { hander });
			Application.getApplication().<String, IService>getCache(SERVICE_CACHE_NAME).put(serviceClass, service);

		}
		if (service != null) {
			hander.registerService(service);
		}
	}

	/**
	 * 
	 */
	public static final String SERVICE_CACHE_NAME = "_SERVICE_CACHE_NAME_";

	public static final String LISTENER_CACHE_NAME = "_LISTENER_CACHE_NAME_";

}
