package com.waspring.framework.antenna.preservation.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.preservation.ser.ObjectsTranscoder;
import com.waspring.framework.antenna.preservation.ser.SerializeTranscoder;

/**
 * 日志文件的搜索实现
 * 
 * @author felly
 *
 */
public class FileQueryLog<Detail extends LogDetail, Stat extends LogStatBean> implements IQueryLog<Detail, Stat> {
	
	
	
	private Logger log = Logger.getLogger(getClass());
	private SerializeTranscoder serializeTranscoder = new ObjectsTranscoder();
	private String logDir = PropertiesUtil.getLogdir();

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	@Override
	public Collection<Detail> queryLogDetail(String containerId, String action, Date startDate, Date endDate) {
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);

		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);

		int year = calendarStart.get(Calendar.YEAR);
		int month = calendarStart.get(Calendar.MONTH) + 1;
		int day = calendarStart.get(Calendar.DAY_OF_MONTH);

		int yearEnd = calendarEnd.get(Calendar.YEAR);
		int monthEnd = calendarEnd.get(Calendar.MONTH) + 1;
		int dayEnd = calendarEnd.get(Calendar.DAY_OF_MONTH);
		if (year != yearEnd && month != monthEnd && day != dayEnd) {/// 只能查一天數據
			return Collections.EMPTY_LIST;

		}

		File logDetailPath = FileStorageStrategy.createLogDetailFile(logDir, containerId, action, year, month, day); //// 写日志

		File logDetailIndex = FileStorageStrategy.createLogDetailIndexFile(logDir, containerId, action, year, month,
				day);
		if (!logDetailIndex.exists()) {
			return Collections.EMPTY_LIST;
		}
		if (!logDetailPath.exists()) {
			return Collections.EMPTY_LIST;
		}
		int startRow = -1;
		int endRow = -1;
		try {
			FileReader reader = new FileReader(logDetailIndex);
			BufferedReader bf = new BufferedReader(reader);
			String line = "";

			while ((line = bf.readLine()) != null) {
				String xx[] = line.split("-");
				long time = Long.parseLong(xx[0]);
				if (time <= calendarStart.getTime().getTime()) {
					startRow = Integer.parseInt(xx[1]);
				}
				if (time > calendarEnd.getTime().getTime()) {
					endRow = Integer.parseInt(xx[1]);
				}
				if (startRow != -1 && endRow != -1) {
					break;
				}
			}

			bf.close();
			reader.close();
		} catch (Exception e) {

		}

		if (endRow < startRow) {
			return Collections.EMPTY_LIST;
		}

		try {
			return (Collection<Detail>) readLines(logDetailPath, startRow, endRow - startRow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}

	private List<LogDetail> readLines(File file, int rowStart, int rows) throws IOException {
		List<LogDetail> rowsData = new ArrayList<LogDetail>(rows + 16);
		FileReader fileReader = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(fileReader);
		int number = rowStart;// 设置指定行数
		String txt = "";
		int lines = 0;
		int rowIndex = 0;
		while (txt != null) {
			lines++;
			txt = reader.readLine();
			if (lines >= number) {
				LogDetail detail = JSON.parseObject(txt, LogDetail.class);
				rowsData.add(detail);
				rowIndex++;
			}
			if (rowIndex >= rows) {
				break;
			}
		}
		reader.close();
		fileReader.close();

		return rowsData;
	}

	@Override
	public Collection<Stat> queryLogStatByAction(String containerId, String action, int year, int month) {

		//// 写统计日志-处理器
		File logStatActionPath = FileStorageStrategy.createLogStatActionFile(logDir, containerId, action, year, month);
		if (!logStatActionPath.exists()) {
			return Collections.EMPTY_LIST;
		}
		DaysLog daysLog = null;
		try {
			byte data[] = FileUtils.readFileToByteArray(logStatActionPath);
			daysLog = (DaysLog) serializeTranscoder.deserialize(data);
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.error(log, e);
		}
		if (daysLog == null) {
			return Collections.EMPTY_LIST;
		}
		return (Collection<Stat>) daysLog.getLogStatBeans();
	}

	@Override
	public Collection<Stat> queryLogStatByContainer(String containerId, int year, int month) {
		File logStatContainerPath = FileStorageStrategy.createLogStatContainerFile(logDir, containerId, year, month);
		if (!logStatContainerPath.exists()) {
			return Collections.EMPTY_LIST;
		}
		DaysLog daysLog = null;
		try {
			byte data[] = FileUtils.readFileToByteArray(logStatContainerPath);
			daysLog = (DaysLog) serializeTranscoder.deserialize(data);
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.error(log, e);
		}
		if (daysLog == null) {
			return Collections.EMPTY_LIST;
		}
		return (Collection<Stat>) daysLog.getLogStatBeans();
	}

}
