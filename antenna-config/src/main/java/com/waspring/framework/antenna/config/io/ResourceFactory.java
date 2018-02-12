package com.waspring.framework.antenna.config.io;

/**
 * 
 * @author felly
 *
 */
public class ResourceFactory {

	public static IResource factoryResource() {
		IResource res = new ClasspathResource();
		res.link(new RemoteHTTPResource().link(new FileSystemResource()));
		return res;

	}
}
