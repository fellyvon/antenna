package com.waspring.framework.antenna.config.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http远程加载数据工具类
 * @author felly
 *
 */
public class HTTPUtil {

	
	/**
     *  实现网络访问文件，将获取到数据储存在文件流中
     * 
     * @param url
     *            ：访问网络的url地址
     * @return inputstream
     */
    public static InputStream loadFileFromURL(String urlString) 
    throws Exception{
        BufferedInputStream bis = null;
        HttpURLConnection httpConn = null;
        try {
            // 创建url对象
            URL urlObj = new URL(urlString);
            // 创建HttpURLConnection对象，通过这个对象打开跟远程服务器之间的连接
            httpConn = (HttpURLConnection) urlObj.openConnection();

            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(5000);
            httpConn.connect();

            // 判断跟服务器的连接状态。如果是200，则说明连接正常，服务器有响应
            if (httpConn.getResponseCode() == 200) {
                // 服务器有响应后，会将访问的url页面中的内容放进inputStream中，使用httpConn就可以获取到这个字节流
                bis = new BufferedInputStream(httpConn.getInputStream());
                return bis;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                // 对流对象进行关闭，对Http连接对象进行关闭。以便释放资源。
                bis.close();
                httpConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
