
package cn.domain.hello.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.domain.hello.dao.UserDao;
import cn.domain.hello.getrequestparams.dao.GetRequestParams;

/**
 * @author 秋放
 */
public class SubmitServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray reqJsonArray = GetRequestParams.getRequestParams(request, response);
		//System.out.println("接收的请求串"+reqJsonArray.toString());
		UserDao userDao = new UserDao();
		userDao.insertGoodsData(reqJsonArray);
		
		JSONArray jsonArray = new JSONArray();
		PrintWriter pw = response.getWriter();
		try {
			jsonArray.put(new JSONObject().put("key", 1));
			System.out.println("返回的值为"+jsonArray.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pw.write(jsonArray.toString());
		pw.flush();
		pw.close();
	}

}
