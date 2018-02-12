package com.waspring.framework.antenna.access.invoke;

import com.github.rholder.retry.RetryListener;

/**
 * 重试的动作触发器定义，在具体的使用过程是这样的：<br/>
 * 1、实现本接口.<br/>
 * 2、在容器配置中配置本触发器.<br/>
 * 
 * 
 * 
 * @author felly
 *
 */
public interface InvokerRetryListener extends RetryListener {

}
