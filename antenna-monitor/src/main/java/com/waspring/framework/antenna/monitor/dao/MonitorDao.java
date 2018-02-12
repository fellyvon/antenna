package com.waspring.framework.antenna.monitor.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.IManagerObtain;
import com.waspring.framework.antenna.access.IQueueManagerFactory;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.visitor.IVisitor;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.VisitException;
import com.waspring.framework.antenna.monitor.model.ProcessMonitor;
import com.waspring.framework.antenna.monitor.model.QueueMonitor;
import com.waspring.framework.antenna.monitor.model.VisitorDetail;
import com.waspring.framework.antenna.preservation.log.IQueryLog;
import com.waspring.framework.antenna.preservation.queue.IQueueManger;
import com.waspring.framework.antenna.preservation.queue.IQueueMangerObtain;
import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutor;
import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutorManager;

/**
 * 数据获取
 * 
 * @author felly
 *
 */
public class MonitorDao {

	/**
	 * 获取所有容器的所有进程
	 */

	public List<ProcessMonitor> queryProcessMonitor() {
		IVisitorContainer[] visitorContainers = ApplicationUtil.getApplication().getVisitorContainers();
		List<ProcessMonitor> pms = new ArrayList<ProcessMonitor>();
		if (visitorContainers == null) {
			return pms;
		}
		for (IVisitorContainer vc : visitorContainers) {
			String id = vc.getId();
			int cap = vc.getAllowMaximum();
			int visitorNum =vc.getVisitors().size();
			ProcessMonitor pm = new ProcessMonitor();
			pm.setCap(cap);
			pm.setPid(id);
			pm.setVisitorNum(visitorNum);
			pms.add(pm);
		}

		return pms;

	}

	/**
	 * 获取访问者信息
	 * 
	 * @param pid
	 * @return
	 */
	public List<VisitorDetail> queryVisitorDetail(String pid) {
		IVisitorContainer container = ApplicationUtil.getApplication().applyContainer(pid);
		List<VisitorDetail> vds = new ArrayList<VisitorDetail>();
		if (container == null) {
			return vds;
		}

		Collection<IVisitorAware> visitors = container.getVisitors();
		if (visitors == null) {
			return vds;
		}

		Iterator<IVisitorAware> visitorIt = visitors.iterator();
		while (visitorIt.hasNext()) {
			IVisitor visitor = visitorIt.next();
			VisitorDetail detail = new VisitorDetail();
			detail.setVisitorId(visitor.getId());
			detail.setRequestId(visitor.getRequest().getRequestId());
			detail.setStayTime(System.currentTimeMillis() - visitor.getRequest().getTimestamp());
			vds.add(detail);
		}
		return vds;

	}

	/**
	 * 结束进程
	 */
	public void killProcess(String pid, String visitorId) {
		IVisitorContainer container = ApplicationUtil.getApplication().applyContainer(pid);

		if (container == null) {
			return;
		}
		IVisitor visitor = container.getVisitor(visitorId);
		if (visitor == null) {
			return;
		}

		try {
			container.releaseVisitor(visitor);
		} catch (VisitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取当前队列信息
	 */
	public List<QueueMonitor> queryQueueMonitor() {
		IApplication application = ApplicationUtil.getApplication();
		IManagerObtain mo = (IManagerObtain) application;
		IQueueManagerFactory queueManager = mo.getQueueManagerFactory();
		List<QueueMonitor> qms = new ArrayList<QueueMonitor>();
		if (queueManager == null) {
			return qms;
		}

		IQueueManger queueManger = queueManager.getQueueManger();
		IQueueMangerObtain qmo = (IQueueMangerObtain) queueManger;
		IConfigureQueueManager cqm = qmo.getConfigureQueueManager();

		IConfigureQueue[] cqs = cqm.getIConfigureQuques();
		for (IConfigureQueue cq : cqs) {
			QueueMonitor qm = new QueueMonitor();
			String executor = cq.getExecutor();
			if (StringUtils.isEmpty(executor)) {
				executor = "内置";
			}
			qm.setExecutor(executor);
			qm.setHander(cq.getTaskHander());
			qm.setQueueId(cq.getId());
			qm.setCap(cq.getMaxIdle());
			qm.setBlockNum(queueManger.applyQueue(cq.getId()).size());

			qms.add(qm);
		}
		return qms;

	}

	/**
	 * 获取日志信息
	 */
	public IQueryLog getQueryLog(String containerId) {
		IApplication application = ApplicationUtil.getApplication();
		IConfigureContainer containerConfig = ((IConfigureApplication) application.getRootConfigure()).getConfigureContainer(containerId);
		if (containerConfig == null) {
			return null;
		}
		IManagerObtain mo = (IManagerObtain) application;
		IQueueManagerFactory queueManager = mo.getQueueManagerFactory();
		if (queueManager == null) {
			return null;
		}

		IQueueManger queueManger = queueManager.getQueueManger();
		IQueueMangerObtain qmo = (IQueueMangerObtain) queueManger;
		IQueueExecutorManager cqm = qmo.getQueueExecutorManager();
		IQueueExecutor executor = cqm.getQueueExecutor(containerConfig.getLogQueue());
		return executor.getTaskHander().getQueryLog();
	}

	/**
	 * 获取配置文件
	 * 
	 * @return
	 */
	public String getConfigure() {
		IApplication application = ApplicationUtil.getApplication();
		return ((IConfigureApplication) application.getRootConfigure()).inverse();
	}

}
