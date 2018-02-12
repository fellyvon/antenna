package com.waspring.framework.antenna.monitor.api;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.io.DefaultURL;
import com.waspring.framework.antenna.config.io.ITypeInputStream;
import com.waspring.framework.antenna.config.io.IURL;
import com.waspring.framework.antenna.config.io.ResourceFactory;
import com.waspring.framework.antenna.config.util.StreamUtil;
import com.waspring.framework.antenna.monitor.model.ApiModel;
import com.waspring.framework.antenna.monitor.service.ApiService;

/**
 * 这里来制作接口文档的展示
 * 
 * @author felly
 *
 */
public class MonitorApiServlet implements Servlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(html);
		response.getWriter().flush();

	}

	private String getHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append(head);
		//////// 文件头
       Map<String, ApiModel> map=apiMap;
       String headerTr="";
       StringBuilder api=new StringBuilder();
       for(Map.Entry<String, ApiModel> ma:map.entrySet()) {
    	   headerTr+=("<tr><td>"+ma.getKey()+"</td><td>"+ma.getValue().getDescription()+"</td><td>"+ma.getValue().getUrl()+"</td><tr/>") ;
    	   String inputtrs=parseObject(ma.getValue().getInputs());
    	   String outputtrs=parseObject(ma.getValue().getOutputs());
    	   api.append(apibody.replace("${inputtrs}", inputtrs)
    			   .replace("${outputtrs}", outputtrs)
    			   .replace("${api}", ma.getKey() )
    			   .replace("${description}", ma.getValue().getDescription() )
    			   .replace("${url}", ma.getValue().getUrl())
    			   .replace("${method}", ma.getValue().getMethod())
    			   .replace("${response}", ma.getValue().getResponse())
    			   );
       }
       
		sb.append(startBody.replace("${data}", headerTr));
		sb.append(api.toString());
 
		sb.append(foot);
		return sb.toString();

	}
	
	private String parseObject(Object obj) {
		String str="";
		if(obj==null) {
			return "";
		}
		if(obj instanceof List) {
			List xt=(List)obj;
			Iterator<Map<String,String>> it=xt.iterator();
			while(it.hasNext()) {
				Map<String,String> map=it.next();
				str+="<tr>"; 
			 
			     /**
			      * <th>参数名</th>
					<th>类型</th>
					<th>是否必填</th>
					<th>描述</th>
					
						public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String DATA_FORMART = "dataformart";
	public static final String REQUIRED = "required";
			      */
				String name=map.get(ApiService.NAME);
				if(!StringUtils.isEmpty(name)) {
					str+="<td>"+name+"</td>";
				}
				
				String dataformart=map.get(ApiService.DATA_FORMART );
				if(!StringUtils.isEmpty(dataformart)) {
					str+="<td>"+dataformart+"</td>";
				}
				  
				String required=map.get(ApiService.REQUIRED );
				if(!StringUtils.isEmpty(required)) {
					str+="<td>"+required+"</td>";
				}
				String description=map.get(ApiService.DESCRIPTION );
				if(!StringUtils.isEmpty(description)) {
					str+="<td>"+description+"</td>";
				}
			   
				str+="</tr>";
			}
		}
		
		return str;
		
	}
	
 
	

	private Map<String, ApiModel> apiMap = null;
	private ApiService apiService = new ApiService();
	private String head = "";
	private String apibody = "";
	private String startBody = "";
	private String foot = "";
	private String html="";

	@Override
	public void init(ServletConfig config) throws ServletException {
		apiMap = apiService.getApiModels();

		head = queryFromLocalFile("classpath://startTag.html");
		startBody = queryFromLocalFile("classpath://startBody.html");
		apibody = queryFromLocalFile("classpath://apiBody.html");
		foot = queryFromLocalFile("classpath://endTag.html");
		
		html=getHTML();
	}

	private String queryFromLocalFile(String url) {
		IURL configURL = new DefaultURL(url);
		ITypeInputStream in = null;
		try {
			in = ResourceFactory.factoryResource().getInputStream(configURL);
			return StreamUtil.convertStreamToString(in.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载配置文件失败：" + url);

		}

		return "";
	}

	@Override
	public void destroy() {
		apiMap.clear();

	}

	@Override
	public ServletConfig getServletConfig() {

		return null;
	}

	@Override
	public String getServletInfo() {

		return null;
	}

}
