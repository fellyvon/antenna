package com.waspring.framework.antenna.config.io;

import java.io.InputStream;

/**
 * 
 * @author felly
 *
 */
public interface ITypeInputStream {
	InputStream getInputStream();

	String getFileFormat();
}
