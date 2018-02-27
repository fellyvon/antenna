package com.waspring.framework.antenna.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.monitor.dao.MonitorDao;
import com.waspring.framework.antenna.monitor.util.ConfigureTreeUtil;
import com.waspring.framework.antenna.preservation.log.IQueryLog;
import com.waspring.framework.antenna.preservation.log.LogDetail;
import com.waspring.framework.antenna.service.AbstractService;

/**
 * 监控页面接口
 * 
 * @author felly
 *
 */
public class MonitorService extends AbstractService {
	public static final String COMMAND = "command";
	private MonitorDao dao = new MonitorDao();

	@Override
	public String getServiceName() {
		return "monitorService";
	}

	/**
	 * 业务处理入口
	 */
	@Override
	public void service(IRequest request, IResponse response) throws Exception {
		/////
		String command = String.valueOf(request.getParameter(MonitorService.COMMAND));
		response.setCode(1);
		response.setMessage("处理成功！");
		if ("processMonitor".equals(command)) {
			response.setData(dao.queryProcessMonitor());
		
		} else if ("processDetail".equals(command)) {
			String pid = String.valueOf(request.getParameter("pid"));
			response.setData(dao.queryVisitorDetail(pid));
		} else if ("killProcess".equals(command)) {
			String pid = String.valueOf(request.getParameter("pid"));
			String visitorId = String.valueOf(request.getParameter("visitorId"));
			dao.killProcess(pid, visitorId);
		} else if ("queueMonitor".equals(command)) {
			response.setData(dao.queryQueueMonitor());
		} else if ("containerTree".equals(command)) {
            //containers
			  //container
			     //invokes
			        //invoke1
			     //providers
			        //provider2
			   List data=ConfigureTreeUtil.changeTree(ApplicationUtil.getApplication().getRootConfigure());
			response.setData(data);
		} else if ("containerList".equals(command)) {
			response.setData(dao.queryProcessMonitor());
		} else if ("runAnalysis".equals(command)) {
			String containerId = String.valueOf(request.getParameter("containerId"));
			String action = String.valueOf(request.getParameter("hander"));
			IQueryLog log = dao.getQueryLog(containerId);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (!StringUtils.isEmpty(action) && !StringUtils.isEmpty(containerId)) {
				response.setData(log.queryLogStatByAction(containerId, action, year, month));
			}

			if (StringUtils.isEmpty(action) && !StringUtils.isEmpty(containerId)) {
				response.setData(log.queryLogStatByContainer(containerId, year, month));
			}

		} else if ("queryLogDetail".equals(command)) {
			String containerId = String.valueOf(request.getParameter("containerId"));
			String action = String.valueOf(request.getParameter("hander"));
			String date = String.valueOf(request.getParameter("date"));
			String startDateString = date + " " + String.valueOf(request.getParameter("startTime"));
			String endDateString = date + " " + String.valueOf(request.getParameter("endTime"));
			Date startDate = null;
			Date endDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);

			IQueryLog log = dao.getQueryLog(containerId);

			Collection<LogDetail> details = log.queryLogDetail(containerId, action, startDate, endDate);
			response.setData(details);
		} else if ("getConfig".equals(command)) {
			response.setData(dao.getConfigure());
		}

	}

}
