package com.waspring.framework.antenna.config.io;

import org.apache.commons.lang.StringUtils;

/**
 * 资源抽象
 * 
 * @author felly
 * 
 *
 */
public abstract class AbstractResource implements IResource {

	public AbstractResource() {
	}

	public abstract ITypeInputStream getInnerInputStream(IURL url) throws NoResourseException;

	/**
	 * 责任到最终执行的类
	 */
	@Override
	public ITypeInputStream getInputStream(IURL url) throws NoResourseException {
		if (StringUtils.stripToEmpty(url.getProtocol()).equals(supportProtocol())) {
			return getInnerInputStream(url);
		} else {
			if (next != null) {
				return next.getInputStream(url);
			} else {
				throw new NoResourseException("无法找到资源！");
			}
		}
	}

	private IResource next;

	public IResource link(IResource resourse) {/// 返回设置后的
		this.next = resourse;

		return resourse;
	}

}
