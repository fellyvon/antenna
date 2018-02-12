package com.waspring.framework.antenna.config.io;

import java.io.InputStream;

/**
 * 
 * @author felly
 *
 */
public class DefaultTypeInputStream implements ITypeInputStream {
	private InputStream in;
	private String fileFormart;

	public DefaultTypeInputStream(InputStream in, String fileFormart) {
		this.in = in;
		this.fileFormart = fileFormart;
	}

	@Override
	public InputStream getInputStream() {
		return in;
	}

	@Override
	public String getFileFormat() {
		return fileFormart;
	}

}
