package com.caiqiufang.config;

public class Config {
	
	//public static  String SERVER_IP = "http://10.1.24.17:8080"; //服务器的IP本地测试要用"http://10.0.2.2:8080";
	//public static String SERVER_IP;
	//http://102c695e.tunnel.qydev.com/OrderServer/client/Goods_getvs.action
	public static  String SERVER_IP="http://69235a86.tunnel.qydev.com";
	public static final String METHOD_LOGIN = "LoginServlet";//访问的方法名
	public static final String METHOD_REGISTER = "RegisterServlet";
	public static final String METHOD_SUBMIT = "Goods_submit";
	public static final String METHOD_PROGRESS = "Goods_getprogress";
	public static final String METHOD_FINDSOURCE ="Goods_findSource";
	
	
	public static String getSERVER_IP() {
		return SERVER_IP;
	}
	public static void setSERVER_IP(String sERVER_IP) {
		SERVER_IP = sERVER_IP;
	}
}
