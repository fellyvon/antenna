package com.waspring.framework.antenna.core.util;

import com.waspring.framework.antenna.core.visitor.IVisitorAware;

/**
 * 
 * @author felly
 *
 */
public class VisitorUtil {

	/**
	 * 在任意位置获取访问者
	 * @return
	 */
	public static IVisitorAware getVisitor() {
		return ThreadContext.getVisitor();
	}
}
