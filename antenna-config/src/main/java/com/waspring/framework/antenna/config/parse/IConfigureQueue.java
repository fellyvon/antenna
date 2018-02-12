package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 队列配置
 * 
 * @author felly
 *
 */
public interface IConfigureQueue extends IConfigure {
 
	int  getMaxIdle();///// 最大等待执行量

	String getExecutor();/// 执行线程

	String getQuqueType();/// 队列类型，有柱塞等类似选择///block,noblock

	String getTaskHander();/// 数据处理器

 
	
	long getExecutorFrequency();
	
	String EXECUTOR="executor";
	String QUQUE_TYPE="quque-type";
	String MAXIDLE="maxidle";
	String TASK_HANDER="task-hander";
 
	String  EXECUTOR_FREQUENCY="executor-frequency";
	
	String QUEUE_TYPE_BLOCK="block";
	String QUEUE_TYPE_NOBLOCK="noblock";
}
