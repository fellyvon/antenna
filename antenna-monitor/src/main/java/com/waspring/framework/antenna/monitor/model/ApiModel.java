package com.waspring.framework.antenna.monitor.model;

/**
 * APi模型
 * @author felly
 *
 */
public class ApiModel {

	private String api="";
	private String code="";
	private String description="";
	private String  response="";
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private String url;
	private String method;
	private Object inputs;
	private Object outputs;
 
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Object getInputs() {
		return inputs;
	}
	public void setInputs(Object inputs) {
		this.inputs = inputs;
	}
	public Object getOutputs() {
		return outputs;
	}
	public void setOutputs(Object outputs) {
		this.outputs = outputs;
	}
 
	
	
}
