package com.waspring.framework.antenna.preservation.log;

import java.io.File;

/**
 * 文件存储路径策略工具类
 * 
 * @author felly
 *
 */
public class FileStorageStrategy {
	public static File createLogDetailIndexFile(String root, String containerId, String action, int year, int month,
			int day) {
		String dir = root + File.separator + containerId + File.separator + action + File.separator + year
				+ File.separator + month + File.separator + File.separator;
		File temp = new File(dir);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		return new File(dir + File.separator + day + ".idx");
	}
	
	

	public static File createLogDetailFile(String root, String containerId, String action, int year, int month,
			int day) {
		String dir = root + File.separator + containerId + File.separator + action + File.separator + year
				+ File.separator + month + File.separator + File.separator;
		File temp = new File(dir);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		return new File(dir + File.separator + day + ".atn");
	}

	public static File createLogStatActionFile(String root, String containerId, String action, int year, int month) {
		String dir = root + File.separator + containerId + File.separator + action + File.separator + year
				+ File.separator;
		File temp = new File(dir);
		if (!temp.exists()) {
			temp.mkdirs();
		}

		return new File(dir + File.separator + month + ".st");

	}

	public static File createLogStatContainerFile(String root, String containerId, int year, int month) {
		String dir = root + File.separator + containerId + File.separator + year + File.separator;
		File temp = new File(dir);
		if (!temp.exists()) {
			temp.mkdirs();
		}

		return new File(dir + File.separator + month + ".st");

	}

}
