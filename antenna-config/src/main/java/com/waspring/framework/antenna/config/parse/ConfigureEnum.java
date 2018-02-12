package com.waspring.framework.antenna.config.parse;

public enum ConfigureEnum {
	 
	  Application("application","容器集合"),
	  Container("container","容器定义"),
	  Providers("providers","服务受理集合"),
	  Provider("provider","服务受理定义"),
	  Invokers("invokers","服务调用集合"),
	  Invoker("invoker","服务调用定义"),
	  Service("service","服务配置"),
	  Scan("scan","扫描注解的配置"),
	  Import("import","导入"),
	  RetryStrategys("retry-strategys","重试策略组"),
	  RetryStrategy("retry-strategy","重试策略"),
	  RetryListener("retry-listener","重试监听器"),
	  AttemptTimeLimiter("attempt-time-limiter","单次任务执行时间限制"),
	  BlockStrategies("block-strategies","柱塞策略"),
	  RetryException("retry-exception","需要重试的异常类型"),
	  StopStrategy("stop-strategy","停止策略"),
	  WaitStrategy("wait_strategy","重试等待策略"),
	  Exceptions("exceptions","异常组"),
	  Exception("exception","异常定义"),
	  IDGenerators("id-generators","ID序列组"),
	  IDGenerator("id-generator","ID序列"),
	  Filters("filters","过滤器组"),
	  Filter("filter","过滤器"),
	  QueueManager("queue-manager","隊列管理器"),
	  Queue("queue","隊列申請"),
	  ;
     
	private  ConfigureEnum (String name,String description) {
		this.name=name;
		this.description=description;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String  getName() {
		return this.name;
		}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
 
	private String name;
	private String description;
}
