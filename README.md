![Antenna-logo](https://raw.githubusercontent.com/fellyvon/antenna/master/doc/logo.png "antenna-logo图") <br>
Antenna-框架介绍
====
# 简介
Antenna——一款简单的点对点服务治理框架，同时它也可以作为一款灵活小巧的MVC开发框架。<br>
该框架总体逻辑架构如下：<br>
 ![Antenna-架构](https://raw.githubusercontent.com/fellyvon/antenna/master/doc/antenna.png "antenna架构图") <br>
 自底向上分别为：数据层、服务层、配置层、接入层。<br>
* 数据层：用于持久化配置、日志、分析数据、缓存等
* 服务层：提供业务逻辑等服务的实现，分为三大类：基础服务类、工具服务类、业务服务类。
* 配置层：本框架提供配置 对外暴露的接口、可调用的接口以及一些规则的配置，比如接口暴露策略、通讯协议、白名单、重试策略、加密策略、认证策略、日志框架、异常处理策略、接口路由配置。
* 接入层：本框架的暴露给外部的接口以及实现，包含接入和输出管理，同时也提供了容器的管理和支持，出口即接口调用，入口即接口受理。

# 功能介绍
* 统一的服务受理、服务调用抽象。
* 提供统一的传输认证和加密。
* 提供访问者并发控制。
* 统一的异步日志记录。
* 统一并封装了请求输入和输出。
* 对正在进行的访问可控。
* 针对调用者，提供重试机制。
* 提供统一的异常处理机制。
* 提供统一的请求过滤功能。
* 通过配置，提供灵活的功能扩展。
* 提供简单的监控功能，包括访问者进程、调用统计、日志跟踪等。

## 如果您的团队处在这种境况，那么本框架可能非常适合您！
* 线上存在多应用服务，同时多应用之间需要通过接口相互通讯，若引入dubbo、springcloud可能技术成本又太高，若想简单的进行服务治理，那么antenna可能是一个不错的选择。
* 线上存在多应用服务，同时多应用之间需要通过接口相互通讯，但通讯接口分布在不同的应用中，写法千差万别，处理方式层出不穷，质量得不到统一保障，也无法统一监控分析各个接口的运作成绩。

# 使用方法
## 通过maven依赖开发包
```xml
 <dependency>
	 <groupId>com.waspring.framework</groupId>
	 <artifactId>antenna-access</artifactId>
	 <version>1.0.0</version>
 </dependency>
```
# 提供者使用
## 若在j2ee容器中
### 那么在web.xml中添加监听器和服务受理servlet
#### 监听器
```xml
            <listener>
		<listener-class>com.waspring.framework.antenna.access.servlet.AntennaListener</listener-class>
	</listener>
	<context-param>
		<param-name>atennaConfigLocation</param-name>
		<param-value>
			classpath://config.xml
		</param-value>
	</context-param>
<context-param>
		<param-name>atennaPropertyLocation</param-name>
		<param-value>
			classpath://root.properties
		</param-value>
	</context-param>
```
* 参数atennaConfigLocation为为入口的配置文件，支持协议：classpath://,file://,http://
* 参数atennaPropertyLocation 为属性配置，主要是指定key，sercret，debug,logdir等参数。
* 若debug为true，那么传输认证和加密都不生效，即key，sercret配置无效。

### Servlet配置
```xml
   <servlet>
		<servlet-name>antennaServlet</servlet-name>
		<servlet-class>com.waspring.framework.antenna.access.servlet.AntennaServlet</servlet-class>
		<init-param>
			<param-name>containerId</param-name>
			<param-value>antennaContainer</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>antennaServlet</servlet-name>
		<url-pattern>/antenna</url-pattern>

	</servlet-mapping>
```
* containerId配置和配置文件中对应的容器ID
* 这里大家可能会问多个容器怎么办？ 嗯，配置多个servlet对应！
* 接口访问地址：http://ip:port/servletname?action=providerID

 
## 若不在j2ee容器环境
### 系统启动申请applicaiton，代码如下：
```java
   PropertiesUtil.loadConfig("classpath://root.properties");
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath("classpath://config.xml");
		LifecycleUtils.init(application);
```
### 系统关闭的时候调用：
```java
   LifecycleUtils.destory(application);
```
### 申请容器
```java
//注意：这里用到的容器名称即主配置文件配置的对应容器ID
IVisitorContainer container = ApplicationUtil.getApplication().applyContainer("antennaContainer");
```
### 随后获取提供者访问
```java
        
		IRequest request = ApplicationUtil.instanceServerRequest(container.getId(), request);//这里request也可以自己申请，设置参数isServer=true即可
		IVisitor visitor = null;
		visitor = container.establishVisitor(request);
		//获取结果
        System.out.println(visitor.getResponse());
```
# 调用者使用
## 在j2ee容器中使用 
```java
IVisitorContainer container = ApplicationUtil.getApplication().applyContainer("antennaContainer");
///通过bean创建request对象，同样action属性会去路由invoker。
//同时bean有要求，字段只有添加了RequestField注解，才会被映射到request。
		IRequest request = ApplicationUtil.instanceClientRequest(container.getId(), new TestBean());
		IVisitor visitor = null;
		visitor = container.establishVisitor(request);

//获取结果
System.out.println(visitor.getResponse());
```
## 不在j2ee容器环境
那么需要在系统启动的时候执行：
```java
   PropertiesUtil.loadConfig("classpath://root.properties");
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath("classpath://config.xml");
		LifecycleUtils.init(application);
```
系统关闭的时候调用：
```java
   LifecycleUtils.destory(application);
```
# 配置文件介绍
## antenna的必须配置文件两个，即上面我们看到的config.xml和root.properties,config.xml为容器配置；root.properties为系统初始化配置。
### 下面是一个config.xml的详细实例
```xml
<?xml version="1.0" encoding="UTF-8"?>
<containers><!-- 容器集合 -->
<!-- 配置扫码的包，用于消除本配置文件中container配置,目前并未实现//TODO -->
	<scan base-package="antenna" />
   <!-- 
   container通讯容器，为本框架的配置基础单元，包含属性有：id,allowmaxnum,id-generator等，各个属性的用途解释如下：
   1、id：非空，容器的身份标识，唯一，若重复，会取最后一个容器配置。
   2、allowmaxnum：非空，容器允许的最大并发存在的访问者数量，用于访问上限限制。
   3、containerclass：可空，用户可通过继承@see AbstractVisitorContainer对容器进行扩展，扩展后的类配置到这里。
   4、 id-generator：非空，id产生策略，需要配合id-generators来使用，这里主要用于自动产生请求ID用。
   5、transfer-timeout：非空，传输过程中时效性允许的最大等待时长，主要用于防止重放攻击，即客户端会传输过来请求发起的时间戳，服务受理端根据服务自己的时间戳减去请求时间的间隔来判定是否存在通讯异常，若存在通讯异常，会阻止当前访问者的请求。
   6、filter：可空，过滤器，在执行具体的业务逻辑之前发生，可用于过滤请求和日志记录等场景。
   7、log-queue：非空，日志队列，配合queue-manager来使用，主要用于异步通讯日志记录。
   8、log-mode：非空，日志模式，即日志的存储方式，有file，jdbc等方式，这个配置决定了队列中每个节点的具体处理方式。
    
    -->
	<container id="antennaContainer" allowmaxnum="2"
		containerclass="" id-generator="randomId" transfer-timeout="10000"
		filter="printFilter" log-queue="logqueue" log-mode="file" >
		<!-- 服务受理组-->
		<providers>
		  <!-- 提供者配置实现 ，主要包含配置：id,serviceclass等,各个属性的用途解释如下：
		   1、id：非空，服务提供的action名称，唯一，在客户端请求的时候，通过传递action字段来匹配本id，若匹配到，那么用本提供配置的服务逻辑。
		   2、serviceclass：非空，业务处理逻辑的实现，需扩展自IService,当然本框架也可供了很多默认Service容器的实现，比如ServiceList，ServiceCollection等，作用不一样。
		   3、providerclass：可空，用户可通过继承@see AbstractProviderHander对服务提供者进行扩展，扩展后的类配置到这里。
		   4、listener：可空，服务提供的监听器，在服务受理的初始化、开始，结束，异常，提交等环节会触发拦截事件，便于使用者扩展响应的处理。
		  -->
			<provider id="parseString"
				serviceclass="com.waspring.framework.antenna.client.ParseStringService"
				providerclass=""  listener="com.waspring.framework.antenna.client.ParseStringListener">
			</provider>
		</providers>
		<!-- 调用服务组，主要的属性为 post-url,retry-strategy-id等.
		    1、post-url， 非空，请求路径全局配置，若invoker没有配置psot-url，那么会默认使用本地址就行远程调用。
		    2、retry-strategy-id， 可空，重试机制全局配置，若invoker没有配置retry-strategy-id，那么会默认使用重试策略。
		    -->
		<invokers post-url="http://www.baidu.com"> 
		  <!-- 调用者配置实现 ，主要包含配置：id,serviceclass等,各个属性的用途解释如下：
		   1、id：非空，服务调用的action名称，唯一，在客户端请求远程的时候，通过传递action字段来匹配具体的业务处理方法。
		   2、serviceclass：非空，业务处理逻辑的实现，需扩展自IService,当然本框架也可供了很多默认Service容器的实现，比如ServiceList，ServiceCollection等，作用不一样。
		   3、invokerclass：可空，用户可通过继承@see AbstractInvokerHander对服务提供者进行扩展，扩展后的类配置到这里。
		   4、listener：可空，服务提供的监听器，在服务受理的初始化、开始，结束，异常，提交等环节会触发拦截事件，便于使用者扩展响应的处理。
		   5、post-url：可空，请求远程的地址，这里主要实现了http协议，所以实际上是一个http地址
		   6、retry-strategy-id：可空，配合retry-strategys来使用，配置了请求的重试机制。
		  -->
			<invoker id="invokeBaidu" post-url="http://www.baidu.com"
				invokerclass="" serviceclass="com.waspring.framework.antenna.client.ParseStringService"
				retry-strategy-id="defaultRetry">
			</invoker>
			<invoker id="invokewaspring" post-url="http://www.waspring2.com"
				serviceclass="com.waspring.framework.antenna.client.HttpInvokerService">
			</invoker>
		</invokers>
		<retry-strategys><!-- 重试策略全局定义 -->
			<retry-strategy id="defaultRetry">
				<!--<retry-listener listener="" /> -->
				<attempt-time-limiter limiter="12" />
				<block-strategies sleeptime="1000" />
				<retry-exception exception="demandException" />
				<stop-strategy stop="attempt" attemptnumber="3" />
				<wait_strategy wait="incrementing" sleeptime="1000"
					increment="500" />
				<!-- 如果返回结果满足如下，则进行重试 -->
			</retry-strategy>

		</retry-strategys>

		<!-- 定义request的requestid的产生策略，默认的有uuid和random，这里配置的为自定义 -->
		<id-generators>
		  <!-- 序列产生器，需扩展@see IdGenerator -->
			<id-generator id="randomId"
				generatorclass="com.waspring.framework.antenna.access.id.RandomGenerator" />
		</id-generators>

      <!-- 异常列表定义，在重试策略中用到 -->
		<exceptions>
			<exception id="ioException" exceptionclass="java.io.IOException" />
			<exception id="demandException"
				exceptionclass="com.waspring.framework.antenna.core.hander.DemandException" />
		</exceptions>
       <!-- 过滤器定义，在hander中用到 -->
		<filters>
		<!-- 过滤器 需扩展IFilter -->
			<filter id="printFilter" filterclass="com.waspring.framework.antenna.client.FirstFilter" />
		</filters>
	</container>

	<!-- 队列管理器，主要有属性max-num，managerclass，他们的用途如下：
	1、max-num：非空，队列最大数量，超过这个数量不允许再申请。
	2、managerclass：可空，用户可通过继承@see AbstractQueueManger对管理器进行扩展，扩展后的类配置到这里。
	 -->
	<queue-manager max-num="100" managerclass="">
	<!-- 队列配置，主要包含id，maxidle，executor等属性，他们的用途如下：
	 1、id：非空，队列的身份标记，再container中配置的就是这个id
	 2、maxidle：非空，用于控制队列的长度，超过长度不允许加入节点；
	 3、executor：可空，队列的消费执行器，可通过继承：AbstractQueueExecutor进行扩展，扩展后的classname配置到这里。
	 4、task-hander：非空，消费细节实现，即对出队的节点具体要做什么业务逻辑处理的实现，需扩展：@see ITaskHander
	 5、queue-type：队列类型，分为阻塞和非阻塞两类，若非阻塞情况下，消费线程会根据executor-frequency配置进行休眠。
	 6、executor-frequency：可空，若queue-type为noblock，那么非空。 即消费线程的休眠时长，单位毫秒！
	  -->
		<queue id="logqueue" maxidle="10000" executor=""
			executor-frequency="1000"
			task-hander="com.waspring.framework.antenna.preservation.log.FileTaskHander"
			queue-type="noblock" />
	</queue-manager>

  <!-- import节点，可用于包含子配置文件，子配置文件和本配置文件格式要求完全一致 。例如:<import path=""/> 
    需要注意的是：path的协议只支持：classpath://,file://,http:// 若需要支持更多协议，需要扩展本框架,详见 @See IResource-->

</containers>
```
#### 下面是一个root.properties配置的实例
```xml
#通讯认证用到key
key=123501
#通讯认证用到的sercret
secret=RTUDUDO0232KDJDU1Ie33IIU3139I
#调试模式，设置为true通讯认证不会开启
debug=true
#默认通讯日志存储目录
logdir=logs
```
 
# 开发第一个demo
 请移步 antenn-client 或者 antenn-monitor
 
# 框架待改进
* 性能与内存消耗<br/>
>>> 为了提高程序的并发处理能力，当前每次visitor访问都需要反射创建provider或者invoker来处理业务逻辑，执行效率和内存上都会打折扣。
>>>> 我们可以参考servlet的架构思路优化，即处理程序只创建一次，访问方法保证线程安全。
* 异常处理机制<br/>
>>>目前异常方面会被吃掉很多，没有逐级上浮，虽然可以通过log跟踪，我们希望还是能从用户端直接判断大部分异常场景！
* 配置管理<br/>
>>>目前通过xml配置受理者和调用者，同时要在应用启动的时候指定容器，配置起来其实挺繁琐，我们希望通过注解等方式来简化配置!
>>>> 另外当前也不支持动态配置，可以使用第三方全局一致性服务来实现。
* 传输认证与加密
>>> 本框架虽然支持认证与加密，但有以下两个缺陷，一方面在传输数据量较大场景效率很低下，另一方面认证机制依赖双方的本地化配置，安全性和可维护下大大折扣。
>>>> 当前可以通过扩展request对象解决，比如采用第三方认证中心。
* 通讯日志
>>> 本框架通讯日志默认采用异步队列本地文件存储，在真正的正式环境中，我们一般不会这么做，基本上会采用第三方的存储服务，可以通过扩展：IQueueTaskHander来实现。
* 重试机制
>>> 目标重试机制实现主要是通过第三方框架guava-retrying实现，进行了简单封装，使用变得简单，同时扩展能力缺被削弱了。
* 补偿机制
>>> 这里的补偿主要是指当一次调用失败后，然后刚好服务重启，是否要进行重试的机制，目前框架并未支持。可以考虑请求前持久化等方案！
 


