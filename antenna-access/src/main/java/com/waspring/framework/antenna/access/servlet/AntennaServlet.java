package com.waspring.framework.antenna.access.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.access.util.ResponseUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;

/***
 * 接口受理如果为servlet，那么配置这个类，并指定访问路径
 * 
 * @author felly
 *
 */
public class AntennaServlet implements Servlet {

	public static final String CONTAINER_ID = "containerId";
	private ServletConfig config;

	public AntennaServlet() {

	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		this.config = config;

	}

	@Override
	public ServletConfig getServletConfig() {
		return config;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String containerId = config.getInitParameter(CONTAINER_ID);
		if (StringUtils.isEmpty(containerId)) {
			throw new ServletException("期望containeId配置！");
		}
		IVisitorContainer container = ApplicationUtil.getApplication().applyContainer(containerId);
		IRequest request = ApplicationUtil.instanceServerRequest(containerId, req);
		IVisitorAware visitor = null;
		visitor = container.establishVisitor(request);
		IResponse response = visitor.getResponse();
		ResponseUtil.writeJson(res, response);
	}

	@Override
	public String getServletInfo() {
		return "antenna v1.0.0 servlet,This is a unified service admission access!";
	}

	@Override
	public void destroy() {

	}

}
