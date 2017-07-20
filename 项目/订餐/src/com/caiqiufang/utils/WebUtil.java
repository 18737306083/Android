package com.caiqiufang.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import com.caiqiufang.config.Config;

/**
 * @author 秋放
 * 功能：运用异步加载发送请求并接收请求
 * 判断返回的结果响应到用户的界面
 */
public class WebUtil {
	static HttpPost hp;	
	static String returnValue = "";
	static JSONArray result = null;
	//发送post请求，该方法并返回服务器响应的结果
	public static JSONArray getJSONArrayByWeb(String methodName,
			JSONArray params) {		//传进来一个方法名和JsonArray 对象
		
		HttpParams httpParams = new BasicHttpParams();	
		httpParams.setParameter("charset", "UTF-8");		//设置请求的参数
		HttpClient hc = new DefaultHttpClient(httpParams);
			
		hp = new HttpPost(Config.getSERVER_IP()+"/OrderServer/client/"+methodName);
			
		try {
			hp.setEntity(new StringEntity(params.toString(), "UTF-8"));		//调用HttPost的setEntity(HttpEntity entity)设置请求的饿参数
			HttpResponse hr = hc.execute(hp);	//发送请求，返回一个HttpResponse
			if (hr.getStatusLine().getStatusCode() == 200) {	//判断状态码是否等于200（请求成功的状态码）
				returnValue = EntityUtils.toString(hr.getEntity(), "UTF-8");
				result = new JSONArray(returnValue);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hc != null) {
			hc.getConnectionManager().shutdown();
		}
		return result;
	}
	
	//发送get请求,并返回服务器响应的结果
	public static JSONArray getJSONArrayByUrl(String url)
		throws Exception
	{	HttpClient httpClient = new DefaultHttpClient();
		//创建HttpGet对象
		HttpGet get = new HttpGet(url);
		//发送get请求
		HttpResponse httpResponse = httpClient.execute(get);
		//如果请求服务器成功返回响应
		if (httpResponse.getStatusLine()
				.getStatusCode() == 200) {
			returnValue = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			result = new JSONArray(returnValue);
		}
		return result;
	}
}
