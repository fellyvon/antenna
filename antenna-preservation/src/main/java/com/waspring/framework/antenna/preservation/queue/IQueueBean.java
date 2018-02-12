package com.waspring.framework.antenna.preservation.queue;

import java.io.Serializable;

public interface  IQueueBean extends Serializable {
	String JDBC = "jdbc";//// 数据库sql格式
	String FILE = "file";/// 文件模式，带有空格分割的格式
 

	abstract String getMode();
	abstract String getPersistentCommand() ;
 
	String TAB="	";
}
