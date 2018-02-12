package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 * 回滚结束后的返回定义
 * 
 * @author felly
 *
 */
public class RollBackResponse extends SimpleResponse {

	public RollBackResponse() {
		init(ResponseCode.ROLLBACK);
	}

}
