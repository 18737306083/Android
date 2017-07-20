
package cn.domain.hello.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import cn.domain.hello.dao.UserDao;

/**
 * @author 秋放
 *
 */

public class WinServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respMessage = null;
		String sql = "select * from table_win";
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		UserDao userDao = new UserDao();
		JSONArray jsonArray = userDao.queryVsInfo(sql);
		PrintWriter pw = response.getWriter();
		respMessage = jsonArray.toString();
		System.out.println("返回的报文是"+respMessage+"*******************");
		pw.write(respMessage);	
		pw.flush();
		pw.close();
	}
}
