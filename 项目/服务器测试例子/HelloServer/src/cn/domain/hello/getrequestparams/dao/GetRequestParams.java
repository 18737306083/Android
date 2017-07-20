/**
 * 
 */
package cn.domain.hello.getrequestparams.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * @author 秋放
 *功能：处理请求得到请求的参数
 */
public class GetRequestParams {
	
	public static JSONArray getRequestParams(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String reqMessage, respMessage;
		JSONArray reqObject = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			reqMessage = sb.toString();
			System.out.println("请求报文:" + reqMessage);
			reqObject = new JSONArray(reqMessage);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return reqObject;
	}
}
