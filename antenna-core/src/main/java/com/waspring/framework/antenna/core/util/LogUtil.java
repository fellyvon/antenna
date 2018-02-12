package com.waspring.framework.antenna.core.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <b>日志工具的封装，支持日志读取和打印的兼容</b><br/>
 * log4j是apache基金会的一个项目,日志记录器(Logger)是日志处理的核心组件,log4j具有7种级别(Level).<br/>
 * <ul>
 * <li>DEBUG Level: 指出细粒度信息事件对调试应用程序是非常有帮助的,就是输出debug的信息.</li>
 * <li>INFO level: 表明消息在粗粒度级别上突出强调应用程序的运行过程,就是输出提示信息.</li>
 * <li>WARN level: 表明会出现潜在错误的情形,就是显示警告信息.</li>
 * <li>ERROR level: 指出虽然发生错误事件,但仍然不影响系统的继续运行.就是显示错误信息.</li>
 * <li>FATAL level: 指出每个严重的错误事件将会导致应用程序的退出.</li>
 * <li>ALL level: 是最低等级的,用于打开所有日志记录.</li>
 * <li>OFF level: 是最高等级的,用于关闭所有日志记录.</li>
 * </ul>
 * 
 * @author felly
 *
 */
public class LogUtil {

	private static boolean sysoutable = false;//// 默认不直接输出到System.out

	/**
	 * 全局设置，需要注意
	 * @param sysoutable
	 */
	public static void setSysoutable(boolean sysoutable) {
		LogUtil.sysoutable = sysoutable;
	}

	/**
	 * 错误的输出
	 * 
	 * @param info
	 */
	public static void error(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.ERROR) || log.isEnabledFor(Level.ALL))) {
			log.error(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

	/**
	 * 警告的输出
	 * 
	 * @param info
	 */
	public static void warn(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.WARN) || log.isEnabledFor(Level.ALL))) {

			log.warn(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

	/**
	 * 普通消息的输出
	 * 
	 * @param info
	 */
	public static void info(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.INFO) || log.isEnabledFor(Level.ALL))) {

			log.info(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

	/**
	 * 调试消息的输出
	 * 
	 * @param info
	 */
	public static void debug(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.DEBUG) || log.isEnabledFor(Level.ALL))) {

			log.debug(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

	/**
	 * FATAL消息的输出
	 * 
	 * @param info
	 */
	public static void fatal(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.FATAL) || log.isEnabledFor(Level.ALL))) {

			log.fatal(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

	/**
	 * TRACE消息的输出
	 * 
	 * @param info
	 */
	public static void trace(Logger log, Object info) {

		if ((!log.isEnabledFor(Level.OFF)) && (log.isEnabledFor(Level.TRACE) || log.isEnabledFor(Level.ALL))) {

			log.trace(info);
		} else {
			if (sysoutable) {
				System.out.println(info);
			}
		}
	}

}
