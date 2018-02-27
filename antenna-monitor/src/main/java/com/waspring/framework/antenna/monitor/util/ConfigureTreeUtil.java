package com.waspring.framework.antenna.monitor.util;

import java.util.ArrayList;
import java.util.List;

import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureInvokers;
import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.config.parse.IConfigureProviders;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.monitor.model.TreeNode;

/**
 * 将配置树转化为前端可识别的树
 * 
 * @author felly
 *
 */
public class ConfigureTreeUtil {

	public static List changeTree(IConfigure root) {
		IConfigureApplication application = (IConfigureApplication) root;
		List<TreeNode> rootTree = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootTree.add(rootNode);
		rootNode.setName("容器组");
		IConfigureContainer configureContainers[] = application.getConfigureContainer();
		List<TreeNode> containerTree = new ArrayList<TreeNode>();
		rootNode.setChildren(containerTree);//// 设置子节点
		for (IConfigureContainer cc : configureContainers) {
			parseContainer(cc, containerTree);
		}
		
		return rootTree;
	}

	/**
	 * 解析每个容器
	 */

	private static void parseContainer(IConfigureContainer cc, List<TreeNode> containerTree) {
		if(cc==null) {
			return;
		}
		TreeNode containerNode = new TreeNode();
		containerNode.setName("容器:" + cc.getId());
		containerNode.setData(cc.getPropertyMap());

		TreeNode invokers = new TreeNode();
		invokers.setName("调用者组");
		if(cc.getConfigureInvokers()!=null) {
		invokers.setData(cc.getConfigureInvokers().getPropertyMap());
		}
		TreeNode providers = new TreeNode();
		providers.setName("受理者组");
		if(cc.getConfigureProviders()!=null) {
		providers.setData(cc.getConfigureProviders().getPropertyMap());
		}
		List<TreeNode> containerSubs = new ArrayList<TreeNode>();
		containerSubs.add(invokers);
		containerSubs.add(providers);

		List<TreeNode> providersSub = new ArrayList<TreeNode>();
		providers.setChildren(providersSub);
		parseProviders(cc.getConfigureProviders(), providersSub);

		List<TreeNode> invokersSub = new ArrayList<TreeNode>();
		invokers.setChildren(invokersSub);
		parseInvokers(cc.getConfigureInvokers(), invokersSub);

		containerNode.setChildren(containerSubs);
		containerTree.add(containerNode);
	}

	/**
	 * 解析受理者
	 */

	private static void parseProviders(IConfigureProviders cp, List<TreeNode> providersList) {
                     if(cp==null) {
                    	return; 
                     }
                     IConfigureProvider ps[]=cp.getConfigureProviders();
                     if(ps==null||ps.length==0) {
                    	 return;
                     }
                     for(IConfigureProvider pp:ps) {
                    	 TreeNode item=new TreeNode();
                    	 item.setName("提供者:"+pp.getId());
                    	 item.setData(pp.getPropertyMap());
                    	 providersList.add(item);
                    	 
                     }
                     
	}

	/**
	 * 解析调用者
	 */

	private static void parseInvokers(IConfigureInvokers cp, List<TreeNode> invokesList) {
	     if(cp==null) {
         	return; 
          }
	     
	     IConfigureInvoker ps[]=cp.getConfigureInvokers();
         if(ps==null||ps.length==0) {
        	 return;
         }
         for(IConfigureInvoker pp:ps) {
        	 TreeNode item=new TreeNode();
        	 item.setName("调用者:"+pp.getId());
        	 item.setData(pp.getPropertyMap());
        	 invokesList.add(item);
        	 
         }
	}
}
