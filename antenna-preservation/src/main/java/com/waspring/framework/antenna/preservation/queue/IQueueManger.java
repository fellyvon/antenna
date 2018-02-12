package com.waspring.framework.antenna.preservation.queue;

import java.util.Queue;

import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutor;

/**
 * 队列管理器抽象
 * 
 * @author felly
 * @date 2018-02-01
 * 
 */
public interface IQueueManger {

	int MAX_QUEUE_NUM = 10;// /默認最大允许开启10个队列

	Queue applyQueue(String name);// /通过名称来申请一个队列

	Queue[] getQueues();//// 得到當前容器中的全部隊列對象

	int getMaxQueueNum();

	int MAX_DATA_NUM = 5000;

	IQueueManger run();//// 容器启动

	IQueueManger stop(boolean force);/// 容器停止,

	boolean isRunning();

}
