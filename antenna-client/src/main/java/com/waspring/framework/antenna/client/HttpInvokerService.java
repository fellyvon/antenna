package com.waspring.framework.antenna.client;

import java.util.Map;

import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.service.HttpClientInvokingService;
import com.waspring.framework.antenna.service.http.RemoteEntry;

public class HttpInvokerService extends HttpClientInvokingService {

	public HttpInvokerService(Invoker invoker) {
		super(invoker);
	}

	@Override
	public String getServiceName() {

		return "HttpInvokerService";
	}

	@Override
	public Map<String, Object> parseMap(IRequest request) throws Exception {
		return request.getParameterMap();
	}

	@Override
	public IResponse parseResponse(IRequest request,IResponse res, RemoteEntry re) throws Exception {
		  res.setCode(1);
		  res.setData(re.getData());
		  res.setSubcode(re.getCode());
		return res;
	}

}
