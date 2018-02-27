package com.waspring.framework.antenna.monitor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 树节点定义
 * @author felly
 *
 */
public class TreeNode {
	private String name;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private Map data = new HashMap();

	public void putProperty(Object key, Object value) {
		data.put(key, value);
	}

	public void addChild(TreeNode node) {
		children.add(node);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
}
