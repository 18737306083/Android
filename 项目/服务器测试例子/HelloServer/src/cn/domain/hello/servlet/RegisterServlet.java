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
import org.json.JSONObject;

import cn.domain.hello.dao.UserDao;
import cn.domain.hello.getrequestparams.dao.GetRequestParams;

/**
 * @author 秋放
 *
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String  respMessage;
			JSONArray reqObject = null;
			JSONArray respObject = null;
			
			try {
				reqObject = new GetRequestParams().getRequestParams(request, response);
				//测试请求 报文的存在性
				//System.out.println("请求报文:" + reqMessage);
				UserDao userDao = new UserDao();
				int resultcode = userDao.registerAndTest(reqObject.getJSONObject(0).getString("username"), 
						reqObject.getJSONObject(0).getString("password"));
				respObject = new JSONArray().put(new JSONObject().put("id", resultcode));
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				respMessage = respObject == null ? "" : respObject.toString();
				System.out.println("返回报文:" + respMessage);
				PrintWriter pw = response.getWriter();
				pw.write(respMessage);	
				pw.flush();
				pw.close();
			}
	}
}
