package com.waspring.framework.antenna.preservation.log;

import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.core.visitor.IVisitor;
import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

/**
 * 访问者日志节点定义——文件内容
 * 
 * @author felly
 *
 */
@SuppressWarnings("serial")
public class VisitorLogFileQueueUnit extends AbstractVisitorLogQueueUnit implements IQueueUnit {

	public IRequest getRequest() {
		return request;
	}

	public IResponse getResponse() {
		return response;
	}

	/**
	 * 采用json格式来返回：<br/>
	 * 内容有： containerId=容器<br/>
	 * isServer=请求类型<br/>
	 * action=处理器ID<br/>
	 * requestId= 请求id<br/>
	 * requetTimestamp= 请求时间<br/>
	 * responseTimestamp=响应时间<br/>
	 * requestContent=请求内容<br/>
	 * reponseContent=响应内容<br/>
	 * code=是否成功调用<br/>
	 */
	@Override
	public String getPersistentCommand() {

		IVisitor visitor = getVisitor();
		if (visitor == null) {
			LogUtil.warn(log, "visior is null,fail to get Persistent Command!");
			return null;
		}

		LogDetail persistentData = new LogDetail();
		persistentData.setContainerId(visitor.getVisitorContainer().getId());
		persistentData.setIsServer(request.isServer());
		persistentData.setAction(request.getAction());
		persistentData.setRequestId(request.getRequestId());
		persistentData.setRequetTimestamp(request.getTimestamp());
		persistentData.setResponseTimestamp(response.getTimestamp());
		persistentData.setReponseContent(String.valueOf(response));
		persistentData.setRequestContent(String.valueOf(request));
		persistentData.setCode(response.getCode());

		return com.alibaba.fastjson.JSON.toJSONString(persistentData);

	}

 

	private IRequest request;

	public IVisitor getVisitor() {
		return visitor;
	}

	private IResponse response;
	private IVisitor visitor;

	public VisitorLogFileQueueUnit(IVisitor visitor, IRequest request, IResponse response) {
		this.request = request;
		this.response = response;
		this.visitor = visitor;
	}

	@Override
	public String getMode() {

		return FILE;
	}

}
