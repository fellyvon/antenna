package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.core.hander.HanderEvent;
import com.waspring.framework.antenna.core.hander.IHanderListener;
import com.waspring.framework.antenna.core.visitor.IVisitor;

/**
 * 触发器
 * @author felly
 *
 */
public class ParseStringListener implements IHanderListener {

	@Override
	public void onInit(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("init："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onExecuteBefore(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("before："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onExecuteAfter(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("after："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onExecuteException(HanderEvent event, Exception e) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("exception："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onCommit(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("commit："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onRollback(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("rollback："+v.getId()+":"+v.getRequest().getRequestId());
		
	}

	@Override
	public void onDestory(HanderEvent event) {
		 IVisitor v=(IVisitor)event.getSource();
		 System.out.println("destory："+v.getId()+":"+v.getRequest().getRequestId());
		
	}
 

}
