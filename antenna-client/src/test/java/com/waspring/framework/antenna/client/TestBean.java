package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.core.ann.RequestField;

public class TestBean implements java.io.Serializable {

	@RequestField(name="action")
	 private String action="invokeHu8hu";

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
