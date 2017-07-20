package cn.domain.hello.dao;

import java.sql.Connection;
import cn.domain.hello.config.Config;

public abstract class BaseDao {
	
	public Connection conn = null;

	public Connection openDB() throws Exception {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = java.sql.DriverManager.getConnection("jdbc:mysql://"		//****************
						+ Config.dbServerIp + ":"+Config.dbPort + "/" + Config.dbName
						+ "?useUnicode=true&characterEncoding="+Config.dbEncoding,
						Config.dbUser, Config.dbPwd);
			} else if (conn.isClosed() == true) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = java.sql.DriverManager.getConnection("jdbc:mysql://"
						+ Config.dbServerIp + ":"+Config.dbPort + "/" + Config.dbName
						+ "?useUnicode=true&characterEncoding="+Config.dbEncoding,
						Config.dbUser, Config.dbPwd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public void closeDB() throws Exception {
		if (conn != null) {
			conn.close();
		}
	}
}
