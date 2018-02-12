package com.waspring.framework.antenna.config.io;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 通过classpath查找资源
 * 
 * @author felly
 *
 */
public class ClasspathResource extends AbstractResource {

	public ClasspathResource() {

	}

	@Override
	public String supportProtocol() {
		return ProtocolEnum.CLASS_PATH.getName();
	}

	@Override
	public ITypeInputStream getInnerInputStream(IURL url) throws NoResourseException {
	 
		String path = getClass().getClassLoader().getResource(url.getURL()).getPath();
	//	path = path + url.getURL();
		File file = new File(path);
		try {
			return new DefaultTypeInputStream(new java.io.FileInputStream(file), url.getFileFormat());
		} catch (FileNotFoundException e) {
			throw new NoResourseException(e);
		}
	}

}
