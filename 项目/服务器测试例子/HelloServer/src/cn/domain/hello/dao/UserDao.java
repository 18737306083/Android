package cn.domain.hello.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.NewsAddress;
import javax.print.attribute.standard.PresentationDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.domain.hello.bean.UserBean;


public class UserDao extends BaseDao{
	public PreparedStatement prepStmt = null;
	public ResultSet rs = null;
	
	public UserBean getUserByName(String src){
		UserBean userBean = null;
		try {
			conn = super.openDB();	
			if(conn!=null){
				String sql = "select * from tab_user where username = ?";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setString(1,src);
				rs = prepStmt.executeQuery();
				if(rs.next()){
					userBean = new UserBean(rs.getInt(1),rs.getString(2),rs.getString(3));
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally{
			try {
				closedb();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userBean;			//返回一个userBean
	}
	
	public int registerAndTest(String name,String psw){
		try {
			conn = super.openDB();	
			if(conn!=null){
				String sql = "select * from tab_user where username = ?";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setString(1,name);
				rs = prepStmt.executeQuery();
				if(rs.next()){
					return 0;		//如果数据库中存在此用户名，则返回0
				}else{
					String insertsql = "insert into tab_user(username,password) values(?,?)";
					prepStmt = conn.prepareStatement(insertsql);
					prepStmt.setString(1,name);
					prepStmt.setString(2, psw);
					prepStmt.executeUpdate();	//执行插入语句
					return 1;		//插入成功
				}
			}
		} catch (Exception e) {
			return -1;		//数据库操作异常
		}finally{
			try {
				closedb();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	//查询数据库内容并返回JSONArrary
	public JSONArray queryVsInfo(String sql){
		JSONArray jsorArray = new JSONArray();
		JSONObject jsonObject ;
		Statement stmt ;
		ResultSet rs;
		try {
			conn = super.openDB();	//**********************************
			if(conn!=null){
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					jsonObject = new JSONObject();
					jsonObject.put("vsname", rs.getString("name"));
					jsonObject.put("vsdesc", rs.getString("desc"));
					jsonObject.put("vsprice", rs.getString("price"));
					jsonObject.put("vspath", rs.getString("path"));
					jsorArray.put(jsonObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				closedb();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsorArray;
	}
	
	public void insertGoodsData(JSONArray jsonArray){
		JSONObject jsonObject;
		Statement stmt;
		try {
			jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
			int count = Integer.parseInt(jsonObject.getString("count"));
			int placenum = Integer.parseInt(jsonObject.getString("placenum"));
			int num=0;
			conn = super.openDB();	
			if(conn!=null){		
				String insertsqlalready = "insert into alreadygoods(name,placenum,num) values(?,?,?)";
				String insertsqlorder = "insert into ordergoods (placenum,count,progress,date) "
						+"values(?,?,?,?)";
				for (int i = 0; i <jsonArray.length()-1; i++) {
					jsonObject = jsonArray.getJSONObject(i);
					num = Integer.parseInt(jsonObject.getString("num"));
					prepStmt = conn.prepareStatement(insertsqlalready);
					prepStmt.setString(1,jsonObject.getString("name"));
					prepStmt.setInt(2, placenum);
					prepStmt.setInt(3,num );
					prepStmt.executeUpdate();	//执行插入语句
				}
				prepStmt = conn.prepareStatement(insertsqlorder);
				prepStmt.setInt(1, placenum);
				prepStmt.setInt(2,count);
				prepStmt.setString(3,"未接收");
				
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(sdf.format(date)+"*******************");
				prepStmt.setString(4, sdf.format(date));
				prepStmt.executeUpdate();
			}
			System.out.println(count+"******"+placenum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @throws SQLException
	 * @throws Exception
	 */
	private void closedb() throws SQLException, Exception {
		if(rs!=null)
			rs.close();
		if(prepStmt!=null)
			prepStmt.close();
		if(conn!=null)
			conn.close();
		super.closeDB();
	}
}
