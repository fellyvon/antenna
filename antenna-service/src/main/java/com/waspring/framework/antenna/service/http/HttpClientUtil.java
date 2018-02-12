package com.waspring.framework.antenna.service.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.util.StreamUtil;
import com.waspring.framework.antenna.core.util.ExceptionUtil;
import com.waspring.framework.antenna.core.util.LogUtil;

public class HttpClientUtil {

	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	/**
	 * 遠程請求數據，get请求
	 * 
	 * @throws IOException
	 */
	public static RemoteEntry get(String remoteUrl, String charset) {
		RemoteEntry entry = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(remoteUrl);
		httpget.getParams().setParameter("http.protocol.allow-circular-redirects", true);
		HttpResponse response = null;
		int code = -1;
		String data = "";
		try {
			response = httpClient.execute(httpget);
			code = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			data = ExceptionUtil.getOutTrace(e);
			LogUtil.error(log, data);

			//// 因为这里错误，服务端估计也响应失败了，所以直接返回结果了
			return new RemoteEntry(code, "远程请求失败:" + ExceptionUtil.parseException(e));
		}
		HttpEntity entity = response.getEntity();
		try {
			if (entity != null) {
				InputStream in = entity.getContent();
				data = StreamUtil.convertStreamToString(in, charset);
			}
		} catch (Exception e) {
			data = ExceptionUtil.getOutTrace(e);//// 客户端处理失败的情况，直接返回
			LogUtil.error(log, data);
			return new RemoteEntry(code, "可能远程请求已经成功了，客户端处理失败：" + ExceptionUtil.parseException(e));

		}
		return new RemoteEntry(code, data);

	}

	/**
	 * 遠程請求提交，post请求
	 * 
	 * @throws IOException
	 */
	public static RemoteEntry post(String remoteUrl, Map data) throws IOException {

		return post(remoteUrl, data, "UTF-8");
	}

	/**
	 * 模拟页面提交
	 * 
	 * @param reqUrl
	 *            提交url
	 * @param parameters
	 *            提交参数
	 * @param charSet
	 *            字符集 比如 UTF-8
	 * @return 返回请求结果
	 */
	public static RemoteEntry post(String reqUrl, Map parameters, String charSet) {

		return post(reqUrl, parameters, "UTF-8", "application/x-www-form-urlencoded;charset=UTF-8");
	}

	/**
	 * <title>使用httpclient 4.4 实现模拟的post请求</title>
	 * 
	 * 1、创建CloseableHttpClient对象。<br/>
	 * 2、创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。<br/>
	 * 3、如果需要发送请求参数，可可调用setEntity(HttpEntity
	 * entity)方法来设置请求参数。setParams方法已过时（4.4.1版本）。<br/>
	 * 4、调用HttpGet、HttpPost对象的setHeader(String name, String
	 * value)方法设置header信息，或者调用setHeaders(Header[] headers)设置一组header信息。<br/>
	 * 5、调用CloseableHttpClient对象的execute(HttpUriRequest
	 * request)发送请求，该方法返回一个CloseableHttpResponse。<br/>
	 * 6、调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容；调用CloseableHttpResponse的getAllHeaders()、getHeaders(String
	 * name)等方法可获取服务器的响应头。<br/>
	 * 7、释放连接。无论执行方法是否成功，都必须释放连接
	 * 
	 * 
	 */
	public static RemoteEntry post(String url, Map parameters, String encoding, String contentType) {
		String data = "";
		RemoteEntry entry = null;
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (parameters != null) {
			for (Object key : parameters.keySet()) {
				nvps.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(parameters.get(key))));
			}
		}
		try {
			// 设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
		} catch (UnsupportedEncodingException e) {
			data = ExceptionUtil.getOutTrace(e);
			LogUtil.error(log, data);
			return new RemoteEntry(-1, "编码参数传入错误，请求失败！");
		}
		LogUtil.info(log, "请求地址：" + url + ",参数:" + nvps.toString());

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", contentType);
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = null;
		int code = -1;
		try {
			response = client.execute(httpPost);
			if (response != null) {
				code = response.getStatusLine().getStatusCode();
			}
		} catch (IOException e) {
			//// 这种情况可能服务端响应都没成功
			/// 先写日志
			data = ExceptionUtil.getOutTrace(e);
			LogUtil.error(log, data);
			// 判断code
			return new RemoteEntry(code, "远程请求失败：" + ExceptionUtil.parseException(e));

		}

		try {
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
				data = EntityUtils.toString(entity, encoding);
				EntityUtils.consume(entity);
			}

		} catch (IOException e) {
			/// 这种情况下说明服务端可能是响应成功了的，所以需要将参数记录下来，让人工去处理

			data = ExceptionUtil.getOutTrace(e);
			LogUtil.error(log, data);
			// 判断code
			return new RemoteEntry(code, "可能远程请求已经成功了，客户端处理失败：" + ExceptionUtil.parseException(e));
		} finally {
			if (response != null) {
				// 释放链接
				try {
					response.close();
				} catch (IOException e) {

					LogUtil.error(log, ExceptionUtil.getOutTrace(e));
				}
			}
		}

		return new RemoteEntry(code, data);
	}

}
