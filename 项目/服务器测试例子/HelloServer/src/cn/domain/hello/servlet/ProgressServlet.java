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

/**
 * @author 秋放
 *
 */
public class ProgressServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		JSONArray jsonArray = new JSONArray();
		PrintWriter pw = response.getWriter();
		
		try {
			jsonArray.put(new JSONObject().put("result", "已做好"));
			System.out.println("返回的值为"+jsonArray.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pw.write(jsonArray.toString());
		pw.flush();
		pw.close();
	}
}
