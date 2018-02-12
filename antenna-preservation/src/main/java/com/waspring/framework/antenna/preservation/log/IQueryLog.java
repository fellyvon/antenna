package com.waspring.framework.antenna.preservation.log;

import java.util.Collection;
import java.util.Date;

/**
 * 查询抽象
 * 
 * @author felly
 *
 */
public interface IQueryLog<Detail, Stat> {

	/**
	 * 查询日志明细
	 * 
	 * @param containerId
	 * @param action
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Collection<Detail> queryLogDetail(String containerId, String action, Date startDate, Date endDate);

	/**
	 * 获取日志统计数据,观察具体的处理者的统计信息
	 */
	Collection<Stat> queryLogStatByAction(String containerId, String action, int year, int month);

	/**
	 * 获取日志统计数据，观察容器内的统计信息
	 */
	Collection<Stat> queryLogStatByContainer(String containerId, int year, int month);
 
	
}
