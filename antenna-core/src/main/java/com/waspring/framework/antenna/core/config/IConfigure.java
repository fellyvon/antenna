package com.waspring.framework.antenna.core.config;

import java.util.Map;

/**
 * 配置项的抽象<br/>
 * 注意这里的配置项不限于使用什么形式来配置，比如本次实现我们使用的就是XML。<br/>
 * 但实际上，主要满足本抽象实现的任何配置形式都可以,比如注解，properties文件，json等等.
 * 
 * @author felly
 *
 */
public interface IConfigure {
	/**
	 * ID属性定义，每个XML配置节点都应该有这个属性，但是有的节点可以不强制设置，比如scan
	 */
	String ID = "id";

	/**
	 * 用于获取 当前配置节点的id
	 * 
	 * @return
	 */
	String getId(); ///

	/**
	 * 获取节点名称
	 * 
	 * @return
	 */
	String getElementName();///

	/**
	 * 获取上级配置对象,整个配置数据实际上是树形结构
	 * 
	 * @return
	 */
	IConfigure getParent();///

	/**
	 * 判断是否还存在有下一级
	 * 
	 * @return
	 */
	boolean hasNext();/// 是否存在下一节点

	/**
	 * 返回当前节点的全部属性集合.属性我们都是通过K=V来表示
	 * 
	 * @return
	 */
	Map<String, String> getPropertyMap();/// 节点属性

	/**
	 * 获取属性值
	 * @param key
	 * @return
	 */
	String getProperty(String key);

	/**
	 * 设置属性，本方法只在初始化的时候实现，在读取配置的时候不应该再动态添加，一些比较合理的抽象是
	 * 将这行为抽象为对应的Aware。这里我们不必要这么做。因为对配置的使用
	 * @param key
	 * @param value
	 */
	void putProperty(String key, String value);

	/**
	 * 获取节点内文本
	 * @return
	 */
	String getContent();/// 节点文字内容

	/**
	 * 设置内容，和Property操作很类似
	 * @param content
	 */
	void setContent(String content);
	
	


}
