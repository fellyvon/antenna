package com.waspring.framework.antenna.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.service.http.HttpClientUtil;
import com.waspring.framework.antenna.service.http.RemoteEntry;

/**
 * http客户端的调用服务。基本上客户端service都可以集成本service后实现逻辑
 * 
 * @author felly
 *
 */
public abstract class HttpClientInvokingService extends AbstractService {
	private Invoker invoker = null;

	public HttpClientInvokingService(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public String getServiceName() {
		return "HttpClientInvokingService";
	}

	@Override
	public void service(IRequest request, IResponse response) throws Exception {
		Map<String, Object> requestData = parseMap(request);
		RemoteEntry result = request(requestData, request.getMethod());
		parseResponse(request,response, result);
	}

	/**
	 * 请求参数的转化，客户端自己来实现
	 */
	public abstract Map<String, Object> parseMap(IRequest request) throws Exception;

	/**
	 * 结果处理，需要客户端自己来实现
	 */

	public abstract IResponse parseResponse(IRequest request,IResponse response, RemoteEntry re) throws Exception;

	/**
	 * 请求参数拼接
	 * 
	 * @param map
	 * @return
	 */
	private String maptoString(String url, Map<String, Object> map) {
		url = StringUtils.trimToEmpty(url);
		String para = "&";
		if (url.indexOf("?") == -1) {
			para = "?";
		}
		if (map == null || map.isEmpty()) {
			return para;
		}

		for (Map.Entry<String, Object> me : map.entrySet()) {
			para += me.getKey() + "=" + me.getValue() + "&";
		}
		return para;
	}

	/*
	 * 执行请求
	 */
	public RemoteEntry request(Map<String, Object> para, String method) throws Exception {
		RemoteEntry re = null;
		if (POST.equals(method)) {
			re = HttpClientUtil.post(invoker.getPostURL(), para);
		} else {
			String getUrl = maptoString(invoker.getPostURL(), para);

			re = HttpClientUtil.get(invoker.getPostURL(), "UTF-8");
		}

		return re;
	}

	public static final String POST = "post";
	public static final String GET = "get";

}
