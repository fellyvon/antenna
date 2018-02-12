package com.waspring.framework.antenna.service.http;

/**
 * 远程请求返回的结果集封装
 * @author felly
 *
 */
public class RemoteEntry  implements  java.io.Serializable  {
   private  int code;//返回码，遵循http的返回码，但是-1 代表的是服务端请求失败，切忌！
   private String data;//返回结果
   
	public RemoteEntry(int code,String data) {
		this.code=code;
		this.data=data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String toString() {
		return "code="+code+"\r\ndata="+data;
	}
	
}
