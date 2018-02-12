package com.waspring.framework.antenna.config.io;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 通过文件系统查找资源
 * 
 * @author felly
 *
 */
public class FileSystemResource extends AbstractResource {

	public FileSystemResource( ) {

	}

	@Override
	public String supportProtocol() {
		return ProtocolEnum.FILE.getName();
	}

	@Override
	public ITypeInputStream getInnerInputStream(IURL url) throws NoResourseException {
	 
		String path = url.getURL();

		File file = new File(path);
		try {
			return new DefaultTypeInputStream(new java.io.FileInputStream(file), url.getFileFormat());
		} catch (FileNotFoundException e) {
			throw new NoResourseException(e);
		}
	}

}
