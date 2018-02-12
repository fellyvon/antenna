package com.waspring.framework.antenna.access.manager;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.IConfigBeanFactory;
import com.waspring.framework.antenna.access.id.RandomGenerator;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerator;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerators;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.util.ReflectUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IVisitorContainerFactory;
import com.waspring.framework.antenna.core.visitor.IdGenerator;

/**
 * 定义从配置获取对象的抽象
 * 
 * @author felly
 *
 */
@SuppressWarnings("rawtypes")
public class VisitorContainerConfigBeanFactory<T extends IVisitorContainer> implements IConfigBeanFactory {

	private IVisitorContainerFactory vistitorContainerFactory = null;
	private IApplication application = null;

	public VisitorContainerConfigBeanFactory(IApplication application,
			IVisitorContainerFactory vistitorContainerFactory) {
		this.application = application;
		this.vistitorContainerFactory = vistitorContainerFactory;
	}

	public  T create(java.io.Serializable id) {
		T container = null;
		String cotainerId = String.valueOf(id);
		if (StringUtils.isEmpty(cotainerId)) {
			return container;
		}
		IConfigureApplication configure = (IConfigureApplication) application.getRootConfigure();
		if (configure != null) {
			final IConfigureContainer configureContainer = configure.getConfigureContainer(cotainerId);
			if (configureContainer != null) {
				String className = configureContainer.getContainerClass();
				/// 有classname配置，那么直接反射这个类进行返回，
				if (!StringUtils.isEmpty(className)) {
					container = ReflectUtil.<T>constructorNewInstance(className, new Class[] {}, new Object[] {});
				}
				// 否则的话通过匿名实现
				else {
					container = (T) new AbstractVisitorContainer(configureContainer) {
						
					
						@Override
						public long getTimeout() {
							return configureContainer.getTransferTimeout();
						}

						@Override
						public IdGenerator getIdGenerator() {
							
							IConfigureIDGenerators configureIdgenerators = configureContainer.getConfigureIDGenerators();
							if(configureIdgenerators!=null) {
							
							IConfigureIDGenerator configureIdgenerator=	configureIdgenerators.getConfigureIDGenerator(configureContainer.getIDGenerator());
							
							if(configureIdgenerator!=null&&!StringUtils.isEmpty(configureIdgenerator.getGeneratorClass())) {
							return ReflectUtil.<IdGenerator>constructorNewInstance(
									configureIdgenerator.getGeneratorClass(), new Class[] {}, new Object[] {});
							}
							}
							
							return new    RandomGenerator();////默認使用隨機
						}

						@Override
						public String getKey() {
							return PropertiesUtil.getKey();
						}

						@Override
						public String getSercret() {

							return PropertiesUtil.getSecret();
						}

						@Override
						public String getId() {

							return configureContainer.getId();
						}

						@Override
						public IHander getHander(IRequest request) {

							if (request.isServer()) {

								return application.getProvider(this, request.getAction());
							}

							return application.getInvoker(this, request.getAction());
						}

					};
				}

				try {
					LifecycleUtils.init(container);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return container;
	}

}
