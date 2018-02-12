package com.waspring.framework.antenna.config.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流处理工具类
 * @author felly
 *
 */
public class StreamUtil {
	
	/**
	 * 流转化为字符串
	 * 
	 * @param is
	 *            输入流
	 * @return 转化后的字符
	 * @throws Exception
	 */
	public static String convertStreamToString(InputStream is) throws Exception {
		return convertStreamToString(is, "UTF-8");
	}

	/**
	 * 流转化为字符串
	 * 
	 * @param is
	 *            输入流
	 * @param charset
	 *            字符集 比如 UTF-8
	 * @return 转化后的字符
	 * @throws Exception
	 */
	public static String convertStreamToString(InputStream is, String charset) throws Exception {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means there's
		 * no more data to read. Each line will appended to a StringBuilder and returned
		 * as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	 
}
