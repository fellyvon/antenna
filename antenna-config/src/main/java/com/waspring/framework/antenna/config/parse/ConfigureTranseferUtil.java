package com.waspring.framework.antenna.config.parse;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.waspring.framework.antenna.config.io.DefaultURL;
import com.waspring.framework.antenna.config.io.IResource;
import com.waspring.framework.antenna.config.io.ResourceFactory;
import com.waspring.framework.antenna.config.parse.impl.ConfigureApplication;
import com.waspring.framework.antenna.config.parse.impl.ConfigureContainerImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureExceptionImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureExceptionsImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureFilterImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureFiltersImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureIDGeneratorImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureIDGeneratorsImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureImportImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureInvokerImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureInvokesImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureProviderImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureProvidersImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureQueueImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureQueueMangerImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureRetryStrategyImpl;
import com.waspring.framework.antenna.config.parse.impl.ConfigureRetryStrategysImpl;
import com.waspring.framework.antenna.config.parse.impl.XMLParser;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 配置转化工具
 * 
 * @author felly
 *
 */
public class ConfigureTranseferUtil {

	/**
	 * 
	 * @param config
	 * @param element
	 */
	private static void putsProperty(IConfigure config, Element element) {
		NamedNodeMap paras = element.getAttributes();
		int len = paras.getLength();
		for (int i = 0; i < len; i++) {
			Node node = paras.item(i);
			config.putProperty(node.getNodeName(), node.getNodeValue());
		}

	}
	
	 /*  
     * 把dom文件转换为xml字符串  
     */  
    public static String toStringFromDoc(Document document) {  
        String result = null;  
  
        if (document != null) {  
            StringWriter strWtr = new StringWriter();  
            StreamResult strResult = new StreamResult(strWtr);  
            TransformerFactory tfac = TransformerFactory.newInstance();  
            try {  
                javax.xml.transform.Transformer t = tfac.newTransformer();  
               
                t.transform(new DOMSource(document.getDocumentElement()),  
                        strResult);  
            } catch (Exception e) {  
                System.err.println("XML.toString(Document): " + e);  
            }  
            result = strResult.getWriter().toString();  
            try {  
                strWtr.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return result;  
    }  

	/**
	 * XML的关键解析动作
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static IConfigure parsebyXML(Document doc, INodeParse extendNodeParse) throws Exception {
		IConfigureApplication application = new ConfigureApplication();
		( (ConfigureApplication)application).setRootDocument(toStringFromDoc(doc));
		Element first = doc.getDocumentElement();
		IParser parser = new XMLParser();
		IResource res = ResourceFactory.factoryResource();/// 解析导入节点开始
		NodeList imports = doc.getElementsByTagName(ConfigureEnum.Import.getName());
		if (imports != null) {
			int len = imports.getLength();
			IConfigureApplication[] subs = new IConfigureApplication[len];
			for (int i = 0; i < len; ++i) {
				Element element = (Element) imports.item(i);
				IConfigureImport ii = new ConfigureImportImpl(application);
				ConfigureTranseferUtil.putsProperty(ii, element);
				application.addConfigureImport(ii);
				subs[i] = (IConfigureApplication) parser.handle(res.getInputStream(new DefaultURL(ii.path())));

			}
			/// 同时把导入的配置文件解析出来并配置给总配置文件
			application.setConfigureApplication(subs);

		}
		/// 解析扫描节点开始
		NodeList scans = doc.getElementsByTagName(ConfigureEnum.Scan.getName());
		if (scans != null) {
			int len = scans.getLength();
			for (int i = 0; i < len; ++i) {
				Element element = (Element) scans.item(i);
				application.getConfigScan().addScanPath(element.getAttribute(IConfigureScan.BASE_PACKAGE));
			}
		}

		NodeList ququeMangerConfigures = doc.getElementsByTagName(ConfigureEnum.QueueManager.getName());

		explainConfigueQuque(ququeMangerConfigures, application);
		/// 解析容器开始
		NodeList containers = doc.getElementsByTagName(ConfigureEnum.Container.getName());

		if (containers != null) {
			int len = containers.getLength();
			for (int i = 0; i < len; ++i) {
				Element element = (Element) containers.item(i);//// container
				IConfigureContainer cc = new ConfigureContainerImpl(application);
				ConfigureTranseferUtil.putsProperty(cc, element);
				application.addConfigureContainer(cc);
				/// 解析providers
				ConfigureTranseferUtil.explainProvider(cc, element);
				/// 解析Invokers
				ConfigureTranseferUtil.explainInvokers(cc, element);
				/// 解析ID规则列表
				ConfigureTranseferUtil.explainConfigureIDGenerators(cc, element);
				//// 解析异常列表
				ConfigureTranseferUtil.explainConfigureExceptions(cc, element);
				///// 解析策略列表
				ConfigureTranseferUtil.explainConfigureRetryStrategy(cc, element);
				///// 解析过滤器列表
				ConfigureTranseferUtil.explainConfigureFilter(cc, element);

			}
		}

		/// 自定义扩展配置
		if (extendNodeParse != null) {
			INodeParse node = extendNodeParse;
			while (node != null) {
				IConfigure configure = node.nodeParse(doc);
				application.addExtendConfigure(configure);
				node = node.next();
				if (node == extendNodeParse) {
					break;/// 防止死循环
				}
			}
		}

		return application;

	}

	/**
	 * 队列配置解析
	 * 
	 * @param ququeMangerConfigures
	 * @param application
	 */
	private static void explainConfigueQuque(NodeList ququeMangerConfigures, IConfigureApplication application) {

		if (ququeMangerConfigures != null) {
			int len = ququeMangerConfigures.getLength();
			for (int i = 0; i < len; i++) {///// 获取manmager
				Element ququeManagerElement = (Element) ququeMangerConfigures.item(i);//// ququemanager
				if (ququeManagerElement != null) {
					IConfigureQueueManager ququeManager = new ConfigureQueueMangerImpl(application);
					application.setConfigureQueueManager(ququeManager);
					ConfigureTranseferUtil.putsProperty(ququeManager, ququeManagerElement);// 赋值
					NodeList ququeConfigures = ququeManagerElement.getElementsByTagName(ConfigureEnum.Queue.getName());
					if (ququeConfigures != null) {
						int ququeConfiguresLen = ququeConfigures.getLength();
						for (int j = 0; j < ququeConfiguresLen; j++) {
							Element ququeElement = (Element) ququeConfigures.item(j);//// quque
							if (ququeElement != null) {
								IConfigureQueue quque = new ConfigureQueueImpl(ququeManager);
								if (quque != null) {
									ConfigureTranseferUtil.putsProperty(quque, ququeElement);// 赋值
									ququeManager.addConfigureQuque(quque);
								}

							}
						}

					}
				}

				break;/// 只读取第一个，配置多个也没有意义

			}

		}
	}

	private static void explainConfigureFilter(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.Filters.getName());//// exceptions
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; j++) {
			Node filters = pis.item(j);
			IConfigureFilters configureFilters = new ConfigureFiltersImpl(cc);
			cc.setConfigureFilters(configureFilters);
			ConfigureTranseferUtil.putsProperty(configureFilters, (Element) filters);////
			/// 处理RetryStrategy
			NodeList configureFilterArray = ((Element) filters).getElementsByTagName(ConfigureEnum.Filter.getName());
			int configureFilterArrayLen = configureFilterArray.getLength();

			for (int k = 0; k < configureFilterArrayLen; k++) {
				Node configureFilterNode = configureFilterArray.item(k);//// provider
				IConfigureFilter configureFilter = new ConfigureFilterImpl(configureFilters);
				ConfigureTranseferUtil.putsProperty(configureFilter, (Element) configureFilterNode);////
				configureFilters.addConfigureFilter(configureFilter);
			}
			break;
		}

	}

	private static void explainConfigureRetryStrategy(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.RetryStrategys.getName());//// exceptions
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; j++) {
			Node retryStrategys = pis.item(j);
			IConfigureRetryStrategys configureRetryStrategys = new ConfigureRetryStrategysImpl(cc);
			cc.setConfigureRetryStrategys(configureRetryStrategys);
			ConfigureTranseferUtil.putsProperty(configureRetryStrategys, (Element) retryStrategys);////
			/// 处理RetryStrategy
			NodeList configureRetryStrategyArray = ((Element) retryStrategys)
					.getElementsByTagName(ConfigureEnum.RetryStrategy.getName());
			int configureRetryStrategyArrayLen = configureRetryStrategyArray.getLength();

			for (int k = 0; k < configureRetryStrategyArrayLen; k++) {
				Node configureRetryStrategyNode = configureRetryStrategyArray.item(k);//// provider
				IConfigureRetryStrategy configureRetryStrategy = new ConfigureRetryStrategyImpl(
						configureRetryStrategys);
				ConfigureTranseferUtil.putsProperty(configureRetryStrategy, (Element) configureRetryStrategyNode);////
				configureRetryStrategys.addConfigureRetryStrategy(configureRetryStrategy);
			}
			break;
		}

	}

	private static void explainConfigureExceptions(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.Exceptions.getName());//// exceptions
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; j++) {
			Node exceptions = pis.item(j);
			IConfigureExceptions configureExceptions = new ConfigureExceptionsImpl(cc);
			cc.setConfigureExceptions(configureExceptions);
			ConfigureTranseferUtil.putsProperty(configureExceptions, (Element) exceptions);////
			/// 处理exception
			NodeList configureExceptionArray = ((Element) exceptions)
					.getElementsByTagName(ConfigureEnum.Exception.getName());
			int configureExceptionArrayLen = configureExceptionArray.getLength();

			for (int k = 0; k < configureExceptionArrayLen; k++) {
				Node configureExceptionNode = configureExceptionArray.item(k);//// provider
				IConfigureException configureException = new ConfigureExceptionImpl(configureExceptions);
				ConfigureTranseferUtil.putsProperty(configureException, (Element) configureExceptionNode);////
				configureExceptions.addConfigureException(configureException);
			}
			break;
		}

	}

	private static void explainConfigureIDGenerators(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.IDGenerators.getName());//// IDGenerators
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; j++) {
			Node idGenerators = pis.item(j);
			IConfigureIDGenerators configureIDGenerators = new ConfigureIDGeneratorsImpl(cc);
			cc.setConfigureIDGenerators(configureIDGenerators);
			ConfigureTranseferUtil.putsProperty(configureIDGenerators, (Element) idGenerators);////
			/// 处理IDGenerators
			NodeList idGeneratorsArray = ((Element) idGenerators)
					.getElementsByTagName(ConfigureEnum.IDGenerator.getName());
			int invokerArrayLen = idGeneratorsArray.getLength();

			for (int k = 0; k < invokerArrayLen; k++) {
				Node idGenerator = idGeneratorsArray.item(k);//// provider
				IConfigureIDGenerator configureIDGenerator = new ConfigureIDGeneratorImpl(configureIDGenerators);
				ConfigureTranseferUtil.putsProperty(configureIDGenerator, (Element) idGenerator);////
				configureIDGenerators.addConfigureIDGenerators(configureIDGenerator);
			}
			break;
		}

	}

	private static void explainInvokers(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.Invokers.getName());//// providers
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; ++j) {
			Node invokers = pis.item(j);
			IConfigureInvokers configureInvokers = new ConfigureInvokesImpl(cc);
			cc.setConfigureInvokers(configureInvokers);
			ConfigureTranseferUtil.putsProperty(configureInvokers, (Element) invokers);////
			/// 处理invoker
			NodeList invokerArray = ((Element) invokers).getElementsByTagName(ConfigureEnum.Invoker.getName());
			int invokerArrayLen = invokerArray.getLength();

			for (int k = 0; k < invokerArrayLen; k++) {
				Node invokerNode = invokerArray.item(k);//// provider
				IConfigureInvoker invoker = new ConfigureInvokerImpl(configureInvokers);
				ConfigureTranseferUtil.putsProperty(invoker, (Element) invokerNode);////
				configureInvokers.addConfigureInvoker(invoker);
			}
			break;
		}
	}

	private static void explainProvider(IConfigureContainer cc, Element container) {
		NodeList pis = container.getElementsByTagName(ConfigureEnum.Providers.getName());//// providers
		int pisLen = pis.getLength();
		for (int j = 0; j < pisLen; ++j) {
			Node providers = pis.item(j);
			IConfigureProviders configureProviders = new ConfigureProvidersImpl(cc);
			cc.setConfigureProviders(configureProviders);
			ConfigureTranseferUtil.putsProperty(configureProviders, (Element) providers);////
			/// 处理provider
			NodeList providerArray = ((Element) providers).getElementsByTagName(ConfigureEnum.Provider.getName());//// providers
			int providerArrayLen = providerArray.getLength();

			for (int k = 0; k < providerArrayLen; k++) {
				Node providerNode = providerArray.item(k);//// provider
				IConfigureProvider provider = new ConfigureProviderImpl(configureProviders);
				ConfigureTranseferUtil.putsProperty(provider, (Element) providerNode);////
				configureProviders.addConfigureProvider(provider);
			}
			break;

		}
	}

}
