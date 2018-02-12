package com.waspring.framework.antenna.client;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.alibaba.fastjson.JSON;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.preservation.log.FileQueryLog;
import com.waspring.framework.antenna.preservation.log.IQueryLog;
import com.waspring.framework.antenna.preservation.log.LogDetail;
import com.waspring.framework.antenna.preservation.log.LogStatBean;

public class LogQueryTest {

	public static void main(String arfs[]) throws Exception {
		PropertiesUtil.loadConfig("classpath://root.properties");
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath("classpath://config.xml");
		LifecycleUtils.init(application);
		IQueryLog query = new FileQueryLog();
		Date startDate = new Date();
		startDate.setTime(1518084900910L);

		Date endDate = new Date();
		endDate.setTime(1518084927861L);

		Collection list = query.queryLogDetail("antennaContainer", "parseString", startDate, endDate);
		System.out.println(list.size());

		Iterator it = list.iterator();
		while (it.hasNext()) {
			LogDetail detail = (LogDetail) it.next();
			if (detail != null) {

				System.out.println(detail.getRequestId() + ":" + detail.getAction() + ":" + detail.getRequestContent()
						+ ":" + detail.getReponseContent());
			}
		}
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);

		int year = calendarStart.get(Calendar.YEAR);
		int month = calendarStart.get(Calendar.MONTH) + 1;

		Collection ContainerStat = query.queryLogStatByContainer("antennaContainer", year, month);

		it = ContainerStat.iterator();
		while (it.hasNext()) {
			LogStatBean stat = (LogStatBean) it.next();
			System.out.println("container=" + JSON.toJSONString(stat));
		}

		ContainerStat = query.queryLogStatByAction("antennaContainer", "parseString", year, month);

		it = ContainerStat.iterator();
		while (it.hasNext()) {
			LogStatBean stat = (LogStatBean) it.next();
			System.out.println("action=" + JSON.toJSONString(stat));
		}

		LifecycleUtils.destory(application);
	}
}
