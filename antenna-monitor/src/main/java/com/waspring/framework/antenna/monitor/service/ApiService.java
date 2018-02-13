package com.waspring.framework.antenna.monitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.waspring.framework.antenna.monitor.model.ApiModel;

/**
 * 获取api文档内容
 * 
 * @author felly
 *
 */
public class ApiService {

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String DATA_FORMART = "dataformart";
	public static final String REQUIRED = "required";
	public static final String SUB = "sub";
	
	private String processMonitorResponse() {
		return "{\n" +
				"	\"code\": 1,\n" +
				"	\"count\": 0,\n" +
				"	\"data\": [{\n" +
				"		\"cap\": 500,\n" +
				"		\"pid\": \"monitor\",\n" +
				"		\"visitorNum\": 7\n" +
				"	}],\n" +
				"	\"message\": \"处理成功！\",\n" +
				"	\"subcode\": 0,\n" +
				"	\"timestamp\": 1518416274602,\n" +
				"	\"traces\": []\n" +
				"}";
	}
	
	private String  processDetailResponse() {
	 return "{\n" +
			 "	\"code\": 1,\n" +
			 "	\"count\": 0,\n" +
			 "	\"data\": [{\n" +
			 "		\"requestId\": \"-2571997308885345023\",\n" +
			 "		\"stayTime\": 8278640,\n" +
			 "		\"visitorId\": \"Visitor-09ec3bf2-9027-4b1c-8b1f-ac9d2253cb25\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"-1245612163935577971\",\n" +
			 "		\"stayTime\": 8379710,\n" +
			 "		\"visitorId\": \"Visitor-1b395d97-a9bf-4fe6-aa22-a33b10a702d0\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"-6487667657561889487\",\n" +
			 "		\"stayTime\": 8280486,\n" +
			 "		\"visitorId\": \"Visitor-6270c895-05d1-4941-8c8b-8c1aa3b50583\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"8684371590969075635\",\n" +
			 "		\"stayTime\": 0,\n" +
			 "		\"visitorId\": \"Visitor-d2dd8c0c-3417-4722-80c1-d613c14e9f62\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"8370205660813777691\",\n" +
			 "		\"stayTime\": 8273465,\n" +
			 "		\"visitorId\": \"Visitor-3889cbac-cd08-456b-9146-0654621a76f0\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"2475746801108253101\",\n" +
			 "		\"stayTime\": 8381720,\n" +
			 "		\"visitorId\": \"Visitor-1894f842-acfc-4e1c-8b38-4754bf7a21df\"\n" +
			 "	}, {\n" +
			 "		\"requestId\": \"-5536307712314285466\",\n" +
			 "		\"stayTime\": 8269000,\n" +
			 "		\"visitorId\": \"Visitor-9a242f59-cb47-48b8-802a-9fc84cd750b1\"\n" +
			 "	}],\n" +
			 "	\"message\": \"处理成功！\",\n" +
			 "	\"subcode\": 0,\n" +
			 "	\"timestamp\": 1518416338782,\n" +
			 "	\"traces\": []\n" +
			 "}";
	 }
	
	private String killProcessResponse() {
		return "{\"code\":1,\"count\":0,\"message\":\"处理成功！\",\"subcode\":0,\"timestamp\":1518416409087,\"traces\":[]}";
	}
	private String queueMonitorResponse() {
	 return "{\n" +
			 "	\"code\": 1,\n" +
			 "	\"count\": 0,\n" +
			 "	\"data\": [{\n" +
			 "		\"blockNum\": 0,\n" +
			 "		\"cap\": 10000,\n" +
			 "		\"executor\": \"内置\",\n" +
			 "		\"hander\": \"com.hu8hu.framework.antenna.preservation.log.FileTaskHander\",\n" +
			 "		\"queueId\": \"monitor.logqueue\"\n" +
			 "	}],\n" +
			 "	\"message\": \"处理成功！\",\n" +
			 "	\"subcode\": 0,\n" +
			 "	\"timestamp\": 1518416464702,\n" +
			 "	\"traces\": []\n" +
			 "}";
	 }
	private String containerTreeResponse() {
	 
	return "{\n" +
			"	\"code\": 1,\n" +
			"	\"count\": 0,\n" +
			"	\"data\": {\n" +
			"		\"configScan\": {\n" +
			"			\"elementName\": \"scan\",\n" +
			"			\"parent\": {\n" +
			"				\"$ref\": \"..\"\n" +
			"			},\n" +
			"			\"propertyMap\": {},\n" +
			"			\"scanPaths\": []\n" +
			"		},\n" +
			"		\"configureContainer\": [{\n" +
			"			\"allowMaximum\": 500,\n" +
			"			\"configureProviders\": {\n" +
			"				\"configureProviders\": [{\n" +
			"					\"elementName\": \"provider\",\n" +
			"					\"id\": \"monitorProvider\",\n" +
			"					\"parent\": {\n" +
			"						\"$ref\": \"$.data.configureContainer[0].configureProviders\"\n" +
			"					},\n" +
			"					\"propertyMap\": {\n" +
			"						\"id\": \"monitorProvider\",\n" +
			"						\"serviceclass\": \"com.hu8hu.framework.antenna.monitor.service.MonitorService\"\n" +
			"					},\n" +
			"					\"serviceClass\": \"com.hu8hu.framework.antenna.monitor.service.MonitorService\"\n" +
			"				}],\n" +
			"				\"elementName\": \"providers\",\n" +
			"				\"parent\": {\n" +
			"					\"$ref\": \"..\"\n" +
			"				},\n" +
			"				\"propertyMap\": {}\n" +
			"			},\n" +
			"			\"elementName\": \"container\",\n" +
			"			\"id\": \"monitor\",\n" +
			"			\"logMode\": \"file\",\n" +
			"			\"logQueue\": \"monitor.logqueue\",\n" +
			"			\"parent\": {\n" +
			"				\"$ref\": \"$.data\"\n" +
			"			},\n" +
			"			\"propertyMap\": {\n" +
			"				\"id\": \"monitor\",\n" +
			"				\"log-queue\": \"monitor.logqueue\",\n" +
			"				\"allowmaxnum\": \"500\",\n" +
			"				\"log-mode\": \"file\",\n" +
			"				\"transfer-timeout\": \"10000\"\n" +
			"			},\n" +
			"			\"transferTimeout\": 10000\n" +
			"		}],\n" +
			"		\"configureImport\": [],\n" +
			"		\"configureQueueManager\": {\n" +
			"			\"elementName\": \"queue-manager\",\n" +
			"			\"iConfigureQuques\": [{\n" +
			"				\"elementName\": \"queue\",\n" +
			"				\"executor\": \"\",\n" +
			"				\"executorFrequency\": 100,\n" +
			"				\"id\": \"monitor.logqueue\",\n" +
			"				\"maxIdle\": 10000,\n" +
			"				\"parent\": {\n" +
			"					\"$ref\": \"$.data.configureQueueManager\"\n" +
			"				},\n" +
			"				\"propertyMap\": {\n" +
			"					\"queue-type\": \"noblock\",\n" +
			"					\"id\": \"monitor.logqueue\",\n" +
			"					\"executor-frequency\": \"100\",\n" +
			"					\"task-hander\": \"com.hu8hu.framework.antenna.preservation.log.FileTaskHander\",\n" +
			"					\"maxidle\": \"10000\",\n" +
			"					\"executor\": \"\"\n" +
			"				},\n" +
			"				\"taskHander\": \"com.hu8hu.framework.antenna.preservation.log.FileTaskHander\"\n" +
			"			}],\n" +
			"			\"managerclass\": \"\",\n" +
			"			\"maxQueue\": 100,\n" +
			"			\"parent\": {\n" +
			"				\"$ref\": \"..\"\n" +
			"			},\n" +
			"			\"propertyMap\": {\n" +
			"				\"managerclass\": \"\",\n" +
			"				\"max-num\": \"100\"\n" +
			"			}\n" +
			"		},\n" +
			"		\"elementName\": \"application\",\n" +
			"		\"extendConfigure\": [],\n" +
			"		\"importConfigureApplications\": [],\n" +
			"		\"propertyMap\": {},\n" +
			"		\"scan\": {\n" +
			"			\"$ref\": \"$.data.configScan\"\n" +
			"		}\n" +
			"	},\n" +
			"	\"message\": \"处理成功！\",\n" +
			"	\"subcode\": 0,\n" +
			"	\"timestamp\": 1518416511744,\n" +
			"	\"traces\": []\n" +
			"}";
	}

	private String runAnalysisResponse() {
	 return "{\n" +
			 "	\"code\": 1,\n" +
			 "	\"count\": 0,\n" +
			 "	\"data\": [{\n" +
			 "		\"action\": \"monitorProvider\",\n" +
			 "		\"containerId\": \"monitor\",\n" +
			 "		\"day\": 11,\n" +
			 "		\"lastTimestamp\": 1518346429263,\n" +
			 "		\"maxExecuteTime\": 6093,\n" +
			 "		\"month\": 2,\n" +
			 "		\"times\": 34155,\n" +
			 "		\"year\": 2018\n" +
			 "	}, {\n" +
			 "		\"action\": \"monitorProvider\",\n" +
			 "		\"containerId\": \"monitor\",\n" +
			 "		\"day\": 12,\n" +
			 "		\"lastTimestamp\": 1518416590161,\n" +
			 "		\"maxExecuteTime\": 1065,\n" +
			 "		\"month\": 2,\n" +
			 "		\"times\": 1082,\n" +
			 "		\"year\": 2018\n" +
			 "	}],\n" +
			 "	\"message\": \"处理成功！\",\n" +
			 "	\"subcode\": 0,\n" +
			 "	\"timestamp\": 1518416596726,\n" +
			 "	\"traces\": []\n" +
			 "}";
	
	}
	
	private   String queryLogDetailResponse() {
		return "";
	}
	/**
	 * 获取接口内容
	 * 
	 * @return Map<String, ApiModel>
	 */
	public Map<String, ApiModel> getApiModels() {

		Map<String, ApiModel> apiModels = new HashMap<String, ApiModel>();

		ApiModel api = new ApiModel();
		api.setApi("进程监控");
		api.setMethod("post/get");
		api.setCode("processMonitor");
		api.setUrl("monitor?action=monitorProvider&command=processMonitor");
		api.setOutputs(getProcessMonitorOutput());
		api.setDescription("进程监控列表，展示当前系统容器负荷情况！");
		api.setResponse(processMonitorResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("访问者详情");
		api.setMethod("post/get");
		api.setCode("processDetail");
		api.setUrl("monitor?action=monitorProvider&command=processDetail");
		api.setInputs(getProcessDetailInput());
		api.setOutputs(getProcessDetailOutput());
		api.setDescription("访问者列表，展示当前访问者详细情况！");
		api.setResponse(processDetailResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("杀掉进程");
		api.setMethod("post");
		api.setCode("killProcess");
		api.setUrl("monitor?action=monitorProvider&command=killProcess");
		api.setInputs(getKillProcessInput());
		api.setDescription("终止访问者的访问");
		api.setResponse(killProcessResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("队列监控");
		api.setMethod("post/get");
		api.setCode("queueMonitor");
		api.setUrl("monitor?action=monitorProvider&command=queueMonitor");
		api.setOutputs(getQueueMonitorOutput());
		api.setDescription("队列监控列表，展示当前系统队列负荷情况！");
		api.setResponse(queueMonitorResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("容器树形");
		api.setMethod("post/get");
		api.setCode("containerTree");
		api.setUrl("monitor?action=monitorProvider&command=containerTree");
		api.setOutputs(getContainerTreeOutput());
		api.setDescription("返回整个容器组的树形结构，包含容器，处理器等");
		api.setResponse(containerTreeResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("容器列表");
		api.setMethod("post/get");
		api.setCode("containerList");
		api.setUrl("monitor?action=monitorProvider&command=containerList");
		api.setOutputs(getProcessMonitorOutput());
		api.setDescription("容器列表列表，展示当前容器！");
		api.setResponse(processMonitorResponse());
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("运行分析");
		api.setMethod("post/get");
		api.setCode("runAnalysis");
		api.setUrl("monitor?action=monitorProvider&command=runAnalysis");
		api.setInputs(getRunAnalysisInput());
		api.setOutputs(getRunAnalysisOutput());
		api.setResponse(runAnalysisResponse());
		api.setDescription("运行分析，用户图表展示，分为处理器的分析和容器的分析数据，目前只能分析本月的数据");
		apiModels.put(api.getApi(), api);

		api = new ApiModel();
		api.setApi("日志跟踪");
		api.setMethod("post/get");
		api.setCode("queryLogDetail");
		api.setUrl("monitor?action=monitorProvider&command=queryLogDetail");
		api.setInputs(getLogDetailInput());
		api.setOutputs(getLogDetailOutput());
		api.setDescription("日志查询！");
		api.setResponse(queryLogDetailResponse());
		apiModels.put(api.getApi(), api);
		
		
		api = new ApiModel();
		api.setApi("配置文件");
		api.setMethod("post/get");
		api.setCode("getConfig");
		api.setUrl("monitor?action=monitorProvider&command=getConfig");
		api.setOutputs(getConfigOutput());
		api.setDescription("查看配置文件内容！");
		apiModels.put(api.getApi(), api);
		
		return apiModels;
	}
	private List getConfigOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "data");
		para.put(DESCRIPTION, "配置文件内容！");
		para.put(DATA_FORMART, "String");
		paras.add(para);
		return paras;
		
	}
	private List getContainerTreeOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "id");
		para.put(DESCRIPTION, "节点ID");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "propertyMap");
		para.put(DESCRIPTION, "数据集合，k，V结构");
		para.put(DATA_FORMART, "Map");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "elementName");
		para.put(DESCRIPTION, "节点分类");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "parent");
		para.put(DESCRIPTION, "上级对象路径");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		return paras;
	}

	private List getLogDetailOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "containerId");
		para.put(DESCRIPTION, "所属容器");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "isServer");
		para.put(DESCRIPTION, "调用方向");
		para.put(DATA_FORMART, "boolean");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "requestId");
		para.put(DESCRIPTION, "请求ID");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "requetTimestamp");
		para.put(DESCRIPTION, "请求时间");
		para.put(DATA_FORMART, "Long");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "responseTimestamp");
		para.put(DESCRIPTION, "返回时间");
		para.put(DATA_FORMART, "Long");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "executeTime");
		para.put(DESCRIPTION, "执行时长,用返回时间减去请求时间可以得到！");
		para.put(DATA_FORMART, "Long");
		paras.add(para);

		return paras;

	}

	private List getLogDetailInput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "containerId");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "YES");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "hander");
		para.put(DESCRIPTION, "处理器ID");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "YES");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "startTime");
		para.put(DESCRIPTION, "开始时间,格式YYYY-MM-DD hh:mm:ss");
		para.put(DATA_FORMART, "Date");
		para.put(REQUIRED, "YES");
		paras.add(para);
		para = new HashMap();
		para.put(NAME, "endTime");
		para.put(DESCRIPTION, "截止时间,格式YYYY-MM-DD hh:mm:ss");
		para.put(DATA_FORMART, "Date");
		para.put(REQUIRED, "YES");
		paras.add(para);

		return paras;
	}

	private List getRunAnalysisOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "containerId");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");

		paras.add(para);

		para = new HashMap();
		para.put(NAME, "action");
		para.put(DESCRIPTION, "处理器ID");
		para.put(DATA_FORMART, "String");

		paras.add(para);

		para = new HashMap();
		para.put(NAME, "year");
		para.put(DESCRIPTION, "年");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "month");
		para.put(DESCRIPTION, "月");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "day");
		para.put(DESCRIPTION, "日");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "times");
		para.put(DESCRIPTION, "访问次数");
		para.put(DATA_FORMART, "Integer");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "maxExecuteTime");
		para.put(DESCRIPTION, "最大执行时长(ms)");
		para.put(DATA_FORMART, "Integer");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "lastTimestamp");
		para.put(DESCRIPTION, "最后调用时间戳");
		para.put(DATA_FORMART, "Long");
		paras.add(para);

		return paras;

	}

	private List getRunAnalysisInput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "containerId");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "YES");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "hander");
		para.put(DESCRIPTION, "处理器ID，若不传该值，那么就是分析容器的数据！");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "NO");
		paras.add(para);

		return paras;
	}

	private List getQueueMonitorOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "queueId");
		para.put(DESCRIPTION, "队列名称");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "cap");
		para.put(DESCRIPTION, "容量");
		para.put(DATA_FORMART, "integer");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "executor");
		para.put(DESCRIPTION, "执行器");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "hander");
		para.put(DESCRIPTION, "处理器");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "blockNum");
		para.put(DESCRIPTION, "等待量");
		para.put(DATA_FORMART, "Integer");
		paras.add(para);

		return paras;
	}

	private List getKillProcessInput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "pid");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "YES");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "visitorId");
		para.put(DESCRIPTION, "访问者Id");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		return paras;
	}

	private List getProcessDetailOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "visitorId");
		para.put(DESCRIPTION, "访问者Id");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "requestId");
		para.put(DESCRIPTION, "请求ID");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "stayTime");
		para.put(DESCRIPTION, "停留时长(ms)");
		para.put(DATA_FORMART, "Long");
		paras.add(para);

		return paras;
	}

	private List getProcessDetailInput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "pid");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");
		para.put(REQUIRED, "YES");
		paras.add(para);
		return paras;
	}

	private List getProcessMonitorOutput() {
		List paras = new ArrayList();
		Map para = new HashMap();
		para.put(NAME, "cap");
		para.put(DESCRIPTION, "容量");
		para.put(DATA_FORMART, "integer");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "pid");
		para.put(DESCRIPTION, "容器ID");
		para.put(DATA_FORMART, "String");
		paras.add(para);

		para = new HashMap();
		para.put(NAME, "visitorNum");
		para.put(DESCRIPTION, "访问者量");
		para.put(DATA_FORMART, "integer");
		paras.add(para);

		return paras;
	}

}
