package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public interface IConfigureScan extends IConfigure {
	String BASE_PACKAGE = "base-package";

	String[] getScanPaths();
	
	void addScanPath(String path);
	
	void removeScanPath(String path);

}
