/**
 * 
 */
package cn.domain.hello.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.domain.hello.getrequestparams.dao.GetRequestParams;

/**
 * @author 秋放
 *
 */
public class FindSourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray reqJsonArray = GetRequestParams.getRequestParams(request, response);
		//System.out.println("接收的请求串"+reqJsonArray.toString());
		
		JSONArray jsonArray = new JSONArray();
		PrintWriter pw = response.getWriter();
		
		try {
			JSONObject jsonObject = new JSONObject(); 
			jsonObject.put("name", "葱爆羊肉");
			jsonObject.put("source","河南省新乡市红旗区华联超市");
			jsonObject.put("buydate", "2016-5-16");
			jsonObject.put("singleprice", "12/kg");
			jsonObject.put("num", "2");
			
			JSONObject jsonObject2 = new JSONObject(); 
			jsonObject2.put("name", "葱爆羊肉");
			jsonObject2.put("source","河南省新乡市红旗区华联超市");
			jsonObject2.put("buydate", "2016-5-16");
			jsonObject2.put("singleprice", "12/kg");
			jsonObject2.put("num", "2");
			jsonArray.put(jsonObject);
			jsonArray.put(jsonObject2);
			//System.out.println("返回的值为"+jsonArray.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		pw.write(jsonArray.toString());
		pw.flush();
		pw.close();
	}
}
