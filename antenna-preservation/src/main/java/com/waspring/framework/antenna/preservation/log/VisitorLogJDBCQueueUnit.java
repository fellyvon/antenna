package com.waspring.framework.antenna.preservation.log;

import java.util.ArrayList;
import java.util.List;

import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.VisitorUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.core.visitor.IVisitor;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

/**
 * 访问者日志节点定义——SQL命令
 * 
 * @author felly
 *
 */
@SuppressWarnings("serial")
public class VisitorLogJDBCQueueUnit extends AbstractVisitorLogQueueUnit implements IQueueUnit, IJDBCPara {

	@Override
	public String getSQL() {
		return getPersistentCommand();
	}

	private IRequest request;
	private IResponse response;
	private IVisitor visitor;
	private List para = new ArrayList();
	private StringBuilder sb = new StringBuilder();

	public VisitorLogJDBCQueueUnit(IVisitor visitor, IRequest request, IResponse response) {
		this.request = request;
		this.response = response;
		this.visitor = visitor;
	}

	public IVisitor getVisitor() {
		return visitor;
	}

	@Override
	public String getMode() {

		return JDBC;
	}

	public String getTable() {
		return table;
	}

	private String[] fields = new String[] { "container_id", "is_server", "action", "request_id", "requet_time_stamp",
			"response_time_stamp", "request_content", "response_content" };

	@Override
	public IJDBCPara setFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	@Override
	public String[] getFields() {

		return fields;
	}

	public IJDBCPara setTable(String table) {
		this.table = table;
		return this;
	}

	private String table = "atn_log";

	private void init() {
		para.clear();
		sb.setLength(0);
		IVisitorAware visitor = VisitorUtil.getVisitor();
		if (visitor == null) {
			LogUtil.warn(log, "visior is null,fail to get Persistent Command!");
			return;
		}
		sb.append("insert into " + table + " ");
		sb.append(" (" + getSQLField() + ")");
		sb.append(" values ");
		sb.append("(" + makePix(getFields().length) + ")");
		para.add(visitor.getVisitorContainer().getId());
		para.add(request.isServer());
		para.add(request.getAction());
		para.add(request.getRequestId());
		para.add(request.getTimestamp());
		para.add(response.getTimestamp());
		para.add(String.valueOf(request));
		para.add(String.valueOf(response));
	}

	@Override
	public String getPersistentCommand() {
		if (sb.length() == 0) {
			init();
		}
		return sb.toString();
	}

	public List getPara() {
		if (para.isEmpty()) {
			init();
		}
		return para;
	}

	private String makePix(int len) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sb.append("?");
			} else {
				sb.append("?,");
			}
		}
		return sb.toString();
	}

	public IRequest getRequest() {
		return request;
	}

	public IResponse getResponse() {
		return response;
	}

	private String getSQLField() {
		StringBuilder sb = new StringBuilder();
		int len = getFields().length;
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sb.append(getFields()[i]);
			} else {
				sb.append(getFields()[i] + ",");
			}
		}
		return sb.toString();
	}

}
