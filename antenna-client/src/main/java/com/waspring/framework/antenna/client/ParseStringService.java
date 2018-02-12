package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.service.AbstractService;

/**
 * demo处理
 * @author felly
 *
 */
public class ParseStringService extends AbstractService {

	public ParseStringService() {

	}

	@Override
	public String getServiceName() {

		return "ParseStringService";
	}

	@Override
	public void service(IRequest request, IResponse response) throws Exception {
 
		response.setCode(100);
		response.setCount(1);
		response.setData( "请求:"+request.getRequestId()+" 被接收！");
		response.setMessage("处理成功");
		response.setSubcode(200);

	}

}
