package com.waspring.framework.antenna.preservation.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.preservation.queue.IQueueBean;
import com.waspring.framework.antenna.preservation.queue.IQueueTaskHander;
import com.waspring.framework.antenna.preservation.ser.ObjectsTranscoder;
import com.waspring.framework.antenna.preservation.ser.SerializeTranscoder;

/**
 * 文件队列任务处理器,处理日志的写入
 * 
 * @author felly
 *
 */
public class FileTaskHander<T extends IQueueBean> implements IQueueTaskHander<T> {
	@Override
	public IQueryLog getQueryLog() {
	 return new   FileQueryLog();
	}

	private Logger log = Logger.getLogger(getClass());

	@Override
	public void handle(T bean) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		VisitorLogFileQueueUnit unit = (VisitorLogFileQueueUnit) bean;
		String containerId = unit.getVisitor().getVisitorContainer().getId();
		String action = unit.getRequest().getAction();
		File logDetailPath = FileStorageStrategy.createLogDetailFile(logDir, containerId, action, year, month, day); //// 写日志
		insertLog(containerId, action, year, month, day, logDetailPath, unit);
		//// 写统计日志-处理器
		File logStatActionPath = FileStorageStrategy.createLogStatActionFile(logDir, containerId, action, year, month);
		statLog(containerId, logStatActionPath, unit, year, month, day);
		//// 写统计日志-容器
		File logStatContainerPath = FileStorageStrategy.createLogStatContainerFile(logDir, containerId, year, month);
		statLog(containerId, logStatContainerPath, unit, year, month, day);
	}

	/**
	 * 日志写入
	 * 
	 * @param filePath
	 * @param unit
	 */
	private void insertLog(String containerId, String action, int year, int month, int day, File file,
			VisitorLogFileQueueUnit unit) {

		List<String> lines = new ArrayList<String>();
		String line = unit.getPersistentCommand();
		LogUtil.info(log, "insertLog=" + line);
		lines.add(line);
		/// 记录内容
		appendFile(file, lines);
		/// 记录索引 时间-当前行数,那么搜索就是先找到时间，然后读取范围内的行数即可
		/// TODO 推荐用全文索引lucece等方案来解决查询效率问题
		try {
			appendIndex(unit, file, containerId, action, year, month, day, unit.getResponse().getTimestamp());
		} catch (IOException e) {

			e.printStackTrace();
			LogUtil.error(log, e);
		}
	}

	private void appendIndex(VisitorLogFileQueueUnit unit, File file, String containerId, String action, int year,
			int month, int day, long timeStamp) throws IOException {
		String key = FILE_ROW_COUNT + containerId + action;
		ICache<String, Integer> cache = unit.getVisitor().getApplicaiton().getCache(FILE_ROW_COUNT);
		Integer curRow = cache.get(key);
		if (curRow == null) {
			curRow = readFileCount(file);

		} else {
			curRow = curRow + 1;
		}
		cache.put(key, curRow);

		File indexFile = FileStorageStrategy.createLogDetailIndexFile(logDir, containerId, action, year, month, day);

		String index = timeStamp + "-" + curRow;
		List<String> lines = new ArrayList<String>();
		lines.add(index);
		appendFile(indexFile, lines);//// 保证有序？

	}

	public static final String FILE_ROW_COUNT = "_FILE_ROW_COUNT_";

	//// 读取文件行数
	private int readFileCount(File file) {
		int cnt = 0;
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(file));
			@SuppressWarnings("unused")
			String lineRead = "";
			while ((lineRead = reader.readLine()) != null) {
			}
			cnt = reader.getLineNumber();
		} catch (Exception ex) {
			cnt = -1;
			ex.printStackTrace();
			LogUtil.error(log, ex);
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(log, ex);
			}
		}
		return cnt;
	}

	private void appendFile(File file, List<String> lines) {
		try {
			OutputStream out = new FileOutputStream(file, true);
			try {
				IOUtils.writeLines(lines, null, out, "UTF-8");
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.error(log, e);
		}
	}

	private SerializeTranscoder serializeTranscoder = new ObjectsTranscoder();

	private void statLog(String containerId, File file, VisitorLogFileQueueUnit unit, int year, int month, int day) {
		//// 读取文件
		DaysLog daysLog = null;

		if (!file.exists()) {
			daysLog = new DaysLog();

		} else {
			try {
				byte data[] = FileUtils.readFileToByteArray(file);
				daysLog = (DaysLog) serializeTranscoder.deserialize(data);
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.error(log, e);
			}
		}

		String ymd = year + "" + month + "" + day;
		LogStatBean bean = daysLog.getLogStatBean(ymd);
		if (bean == null) {
			bean = new LogStatBean();
		}
		bean.setAction(unit.getRequest().getAction());
		bean.setContainerId(containerId);
		bean.setDay(day);
		bean.setLastTimestamp(unit.getResponse().getTimestamp());
		long pix = (unit.getResponse().getTimestamp() - unit.getRequest().getTimestamp());
		bean.setMaxExecuteTime(pix > bean.getMaxExecuteTime() ? pix : bean.getMaxExecuteTime());
		bean.setMonth(month);
		bean.setTimes(bean.getTimes() + 1);
		bean.setYear(year);
		daysLog.put(ymd, bean);

		byte data[] = serializeTranscoder.serialize(daysLog);
		try {
			FileUtils.writeByteArrayToFile(file, data);
		} catch (IOException e) {

			e.printStackTrace();
			LogUtil.error(log, e);
		}

	}

	private String logDir = PropertiesUtil.getLogdir();

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

}
