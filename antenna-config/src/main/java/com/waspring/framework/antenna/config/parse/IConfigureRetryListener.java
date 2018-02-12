package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

public interface IConfigureRetryListener extends IConfigure {
   
	String getListener();
	String LISTENER="listener";
}
