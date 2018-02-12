package com.waspring.framework.antenna.preservation.queue;

import java.io.Serializable;

/**
 * 队列实体上层抽象,最小的队列单元
 * 
 * @author felly
 *
 */
public  interface  IQueueUnit extends  Serializable,IQueueBean {
	/////


	public   IQueueUnit  getQueueUnit(String mode)  ;
	/**
	 * 得到持久化的执行内容或者命令<br/>
	 * 1、jdbc模式那么这里是sql命令.<br/>
	 * 2、file模式那么这里就是具体的文件内容.<br/>
	 * 3、其他可以由客户端自己来设计！<br/>
	 * 
	 * @return
	 */

	
	IQueueUnit linkQueueUnit(IQueueUnit next);

}
