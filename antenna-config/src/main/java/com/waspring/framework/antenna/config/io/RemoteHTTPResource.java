package com.waspring.framework.antenna.config.io;

import com.waspring.framework.antenna.config.util.HTTPUtil;

/**
 * 通过http远程查找资源
 * 
 * @author felly
 *
 */
public class RemoteHTTPResource extends AbstractResource {

	public RemoteHTTPResource() {

	}

	@Override
	public String supportProtocol() {
		return ProtocolEnum.HTTP.getName();
	}

	@Override
	public ITypeInputStream getInnerInputStream(IURL url)
			throws NoResourseException {

		try {
			return new DefaultTypeInputStream(HTTPUtil.loadFileFromURL(url.getURL()), url.getFileFormat());
		} catch (Exception e) {
			throw new NoResourseException(e);
		}
	}

}
