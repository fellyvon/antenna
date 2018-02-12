package com.waspring.framework.antenna.preservation.log;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请本结果存储每一天的访问量
 * @author felly
 *
 */
public class DaysLog implements Serializable {

	private Map<String, LogStatBean> data = new HashMap<String, LogStatBean>(32);

	public void put(String ymd,LogStatBean bean) {
		data.put(ymd, bean);
	}
	
	public    LogStatBean getLogStatBean(String ymd) {
		return    data.get(ymd);
	}
	
	public Collection<LogStatBean> getLogStatBeans(){
		return  data.values();
	}
	
}
