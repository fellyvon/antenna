package com.waspring.framework.antenna.access.util;

import java.io.IOException;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * servlet响应处理工具
 * @author felly
 *
 */
public class ResponseUtil {

	/**
	 * 写入json格式到页面
	 * 
	 * @param response
	 * @param data
	 */
	public static void writeJson(ServletResponse response, Object data) throws IOException {

		writeJson(response, data, "UTF-8", "application/json;charset=utf-8");
	}

	/**
	 * 写入json格式到页面
	 * 
	 * @param response
	 * @param data
	 */
	public static void writeJson(ServletResponse response, Object data, String charset, String contentType)
			throws IOException {
		response.setCharacterEncoding(charset);
		response.setContentType(contentType);
		if (data instanceof String) {
			response.getWriter().write(String.valueOf(data));
		} else {
			response.getWriter().write(JSON.toJSONString(data));
		}
		response.getWriter().flush();
	}

	/**
	 * 写入字符到页面
	 */
	public static void writeJson(ServletResponse response, String data) throws IOException {
		writeJson(response, data, "UTF-8", "application/json;charset=utf-8");
	}
}
