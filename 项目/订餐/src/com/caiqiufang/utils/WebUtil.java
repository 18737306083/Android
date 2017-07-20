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
 * @author ���
 * ���ܣ������첽���ط������󲢽�������
 * �жϷ��صĽ����Ӧ���û��Ľ���
 */
public class WebUtil {
	static HttpPost hp;	
	static String returnValue = "";
	static JSONArray result = null;
	//����post���󣬸÷��������ط�������Ӧ�Ľ��
	public static JSONArray getJSONArrayByWeb(String methodName,
			JSONArray params) {		//������һ����������JsonArray ����
		
		HttpParams httpParams = new BasicHttpParams();	
		httpParams.setParameter("charset", "UTF-8");		//��������Ĳ���
		HttpClient hc = new DefaultHttpClient(httpParams);
			
		hp = new HttpPost(Config.getSERVER_IP()+"/OrderServer/client/"+methodName);
			
		try {
			hp.setEntity(new StringEntity(params.toString(), "UTF-8"));		//����HttPost��setEntity(HttpEntity entity)��������Ķ�����
			HttpResponse hr = hc.execute(hp);	//�������󣬷���һ��HttpResponse
			if (hr.getStatusLine().getStatusCode() == 200) {	//�ж�״̬���Ƿ����200������ɹ���״̬�룩
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
	
	//����get����,�����ط�������Ӧ�Ľ��
	public static JSONArray getJSONArrayByUrl(String url)
		throws Exception
	{	HttpClient httpClient = new DefaultHttpClient();
		//����HttpGet����
		HttpGet get = new HttpGet(url);
		//����get����
		HttpResponse httpResponse = httpClient.execute(get);
		//�������������ɹ�������Ӧ
		if (httpResponse.getStatusLine()
				.getStatusCode() == 200) {
			returnValue = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			result = new JSONArray(returnValue);
		}
		return result;
	}
}
