package com.waspring.framework.antenna.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	// 这里要获取到最内层的异常
	public static Throwable parseException(Throwable e) {
		Throwable tmp = e;
		int breakPoint = 0;
		while (tmp.getCause() != null) {
			if (tmp.equals(tmp.getCause())) {
				break;
			}
			tmp = tmp.getCause();
			breakPoint++;
			if (breakPoint > 1000) {
				break;
			}
		}
		return tmp;
	}

	/**
	 * 打印异常堆栈
	 * 
	 * @param e
	 * @return
	 */
	public static String getOutTrace(StackTraceElement e[]) {
		String msg = "";
		for (int i = 0; i < e.length; i++) {
			StackTraceElement el = e[i];
			msg += el.getLineNumber() + ":" + el.getClassName() + ":"
					+ el.getMethodName() + "\r\n";
		}

		return msg;

	}

	/**
	 * 异常打印出来到string
	 * 
	 * @param e
	 * @return
	 */
	public static String getErrorInfoFromException(Throwable e,int limit) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			String error = "\r\n" + sw.toString() + "\r\n";
			if (limit!=-1&&error.length() > limit) {
				error = error.substring(0, limit) + "...";
			}
			return error;
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}

	/**
	 * 打印异常堆栈
	 * 
	 * @param e
	 * @return
	 */
	public static String getOutTrace(Exception er) {
		String msg = "";
		StackTraceElement e[] = er.getStackTrace();
		for (int i = 0; i < e.length; i++) {
			StackTraceElement el = e[i];
			msg += el.getLineNumber() + ":" + el.getClassName() + ":"
					+ el.getMethodName() + "\r\n";
		}

		return msg;

	}

	/**
	 * 打印异常堆栈
	 * 
	 * @param e
	 * @return
	 */
	public static void outTrace(Exception er) {
		er.printStackTrace();
	}

}
