package com.waspring.framework.antenna.access.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.AbstractRequest;
import com.waspring.framework.antenna.access.Application;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.ann.RequestField;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IdGenerator;

/**
 *应用工具<br/>
 *包含了 request产生和application获取等功能
 * 
 * @author felly
 *
 */
public abstract class ApplicationUtil {

	public static final boolean devloperMode = Boolean.parseBoolean(PropertiesUtil.getProperty(PropertiesUtil.DEBUG));

	/**
	 * 
	 * @return
	 */
	public static IApplication getApplication() {
		return Application.getApplication();
	}

	/**
	 * 针对j2ee服务端，通过ServletRequest 产生request
	 * 
	 * @param request
	 * @return
	 */
	public static IRequest instanceServerRequest(final String containerId, final javax.servlet.ServletRequest request) {
		final HttpServletRequest httpServletRequst = (HttpServletRequest) request;
		IRequest antennaRequest = new AbstractRequest() {

			@Override
			public IdGenerator getIdGenerator() {

				return getApplication().applyContainer(containerId).getIdGenerator();
			}

			@Override
			public String getIP() {

				return String.valueOf(getParameter("ip"));
			}

			@Override
			public String getKey() {
				return getApplication().applyContainer(containerId).getKey();
			}

			@Override
			public String getSecret() {
				return getApplication().applyContainer(containerId).getSercret();
			}

			@Override
			public boolean isServer() {
				return true;
			}

		};

		//// 初始化参数
		antennaRequest.addAllHeader(ApplicationUtil.getHeadersInfo(httpServletRequst));
		Map<String, String[]> map = httpServletRequst.getParameterMap();

		for (Map.Entry<String, String[]> me : map.entrySet()) {
			String[] v = me.getValue();
			String vdata = "";
			if (v.length > 1) {
				vdata = StringUtils.join(v, ",");
			} else {
				vdata = v[0];
			}
			antennaRequest.addParameter(me.getKey(), vdata);
		}

		// antennaRequest.addAllParameter(httpServletRequst.getParameterMap());

		return antennaRequest;
	}

	/**
	 * 针对客户端 ，产生request
	 * 
	 * @param request
	 * @return
	 */
	public static IRequest instanceClientRequest(final String containerId, java.io.Serializable bean) {
		IVisitorContainer container = getApplication().applyContainer(containerId);
		IRequest antennaRequest = new AbstractRequest() {
			@Override
			public IdGenerator getIdGenerator() {
				return getApplication().applyContainer(containerId).getIdGenerator();
			}

			@Override
			public String getIP() {
				try {
					return getIp();
				} catch (SocketException e) {

					e.printStackTrace();
				}
				return null;
			}

			@Override
			public String getKey() {
				return getApplication().applyContainer(containerId).getKey();
			}

			@Override
			public String getSecret() {
				return getApplication().applyContainer(containerId).getSercret();
			}

			@Override
			public boolean isServer() {
				return false;
			}

		};

		//// 通过反射获取参数并设置给request
		try {
			ApplicationUtil.handleBeanTransfer(antennaRequest,bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return antennaRequest;
	}

	private static void handleBeanTransfer(IRequest antennaRequest,java.io.Serializable bean)
	 throws Exception{
		Class<?> classz = bean.getClass();

		///RequestField beanAnnotaiton = classz.getAnnotation(RequestField.class);
		// 得到类中的所有定义的属性
		for (Field filed : classz.getDeclaredFields()) {
			String columnName = null;
			// 得到属性的注解，对一个目标可以使用多个注解
			Annotation[] anns = filed.getAnnotations();
			if (anns.length < 1) {
				continue;
			}
			
			for(Annotation ann:anns) {
				RequestField rf=null;
				if(ann instanceof  RequestField) {
					rf=(RequestField)ann;
				  
					String name=rf.name();
					if(StringUtils.isEmpty(name)) {
						name=filed.getName();
					}
				 
						Object value= BeanUtils.getProperty(bean, name);
				 
				 antennaRequest.addParameter(name, value);
				}

			}

		}
	}

	private static String getIp() throws SocketException {
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			// System.out.println(netInterface.getName());
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					return ip.getHostAddress();
				}
			}
		}
		return null;
	}

	/**
	 * 获取头信息
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

}
