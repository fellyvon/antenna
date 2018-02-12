package com.waspring.framework.antenna.preservation.log;

import java.util.List;

/**
 * 因为jdbc都采用注入方式处理，所以要返回参数集合
 * 
 * @author felly
 *
 */
public interface IJDBCPara {
	///// 参数列表，有序
	List getPara();

	//// 最终可注入值的sql语句
	String getSQL();

	/// 指定表名
	IJDBCPara setTable(String table);

	/// 指定字段名，有序
	IJDBCPara setFields(String fields[]);

	/// 获取表名
	String getTable();

	/// 获取字段名
	String[] getFields();

}
