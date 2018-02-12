package com.waspring.framework.antenna.config.util;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.io.IURL;

/**
 * url比较，这里的url是经过了轻度封装的IURL。<br/>
 * @see IURL
 * @author felly
 *
 */
public class CompareUtil {

	public static boolean compareURL(IURL a, IURL b) {
		String pa = StringUtils.trimToEmpty(a.getProtocol());
		String pb = StringUtils.trimToEmpty(b.getProtocol());

		String fileFormata = StringUtils.trimToEmpty(a.getFileFormat());
		String fileFormatb = StringUtils.trimToEmpty(b.getFileFormat());

		return pa.equals(pb) && fileFormata.equals(fileFormatb);

	}

}
