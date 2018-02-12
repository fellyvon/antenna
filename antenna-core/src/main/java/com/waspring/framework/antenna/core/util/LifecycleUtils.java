package com.waspring.framework.antenna.core.util;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.Destoryable;
import com.waspring.framework.antenna.core.Initializable;

/**
 * 对象生命周期管理对象,一般对象都被抽象为 初始化、执行、销毁。<br/>
 * 
 * @author felly
 *
 */
public class LifecycleUtils {

	private static final Logger log = Logger.getLogger(LifecycleUtils.class);

	public static void init(Object o) throws Exception {
		if (o == null) {
			return;
		}
		if (o instanceof Initializable) {
			init((Initializable) o);
		}
	}

	public static void init(Initializable initializable) throws Exception {
		if (initializable == null) {
			return;
		}
		initializable.init();
	}

	/**
	 * Calls {@link #init(Object) init} for each object in the collection. If the
	 * collection is {@code null} or empty, this method returns quietly.
	 * 
	 */
	public static void init(Collection c) throws Exception {
		if (c == null || c.isEmpty()) {
			return;
		}
		for (Object o : c) {
			init(o);
		}
	}

	public static void destory(Object o) {
		if (o instanceof Destoryable) {
			destory((Destoryable) o);
		} else if (o instanceof Collection) {
			destory((Collection) o);
		}
	}

	public static void destory(Destoryable d) {
		if (d != null) {
			try {
				d.destory();
			} catch (Throwable t) {
				if (log.isDebugEnabled()) {
					String msg = "Unable to cleanly destroy instance [" + d + "] of type [" + d.getClass().getName()
							+ "].";
					log.debug(msg, t);
				}
			}
		}
	}

	public static void destory(Collection c) {
		if (c == null || c.isEmpty()) {
			return;
		}

		for (Object o : c) {
			destory(o);
		}
	}
}
