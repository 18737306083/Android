package com.order.service;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.order.dao.AlreadyGoodsDAO;
import com.order.dao.CookDAO;
import com.order.dao.OrderDAO;
import com.order.dao.impl.AlreadyGoodsDAOImp;
import com.order.dao.impl.CookDAOImp;
import com.order.dao.impl.OrderDAOImp;
import com.order.domain.Already;
import com.order.domain.Order;
import com.order.domain.client.AlreadyGoods;
import com.order.domain.cook.Cook;

public class CookService {
	
	public static JSONObject getCook(JSONObject jsonObject){
		String name,password;
		Cook cook=null;
		JSONObject resJsonObject = new JSONObject();
		try {
			name = jsonObject.getString("name");
			password = jsonObject.getString("passWord");
			CookDAO cookDAO = new CookDAOImp();
			cook = cookDAO.getCook(name, password);
			if (cook!=null) {
				resJsonObject.put("login", "succuss");
			}else {
				resJsonObject.put("login", "falier");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resJsonObject;
	}
	
	/**
	 * @param jsonObject
	 * @return
	 * @ 根据相应的注册信息实现注册业务逻辑的方法
	 */
	public static JSONObject registerCook(JSONObject jsonObject){
		String name,password;
		Cook cook=null;
		JSONObject resJsonObject = new JSONObject();
		try {
			name = jsonObject.getString("name");
			password = jsonObject.getString("passWord");
			CookDAO cookDAO = new CookDAOImp();
			cook = cookDAO.getCookByName(name);
			//判断数据库中是否存在要注册的用户名
			if (cook!=null) {
				resJsonObject.put("regist", "same");
			}else {	//如果数据库中不存在
				//将客户端传过来的注册信息插入数据库中，然后返回相应的信息
				cookDAO.saveCook(name, password);
				resJsonObject.put("regist", "qq");
			}
		} catch (Exception e) {
			try {
				resJsonObject.put("regist", "exception");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return resJsonObject;
	}
	
	//返回所有的菜的信息
	public static JSONArray getData(){
		JSONArray jsonArray= new JSONArray();
		JSONArray itemsJSONArray =  null;
		JSONArray vegetableJSONArray = null;
		JSONArray numJSONArray = null;
		Order order = null;
		OrderDAO orderDAO = new OrderDAOImp();	//操作订单的事务实例
		AlreadyGoodsDAO alreadyGoodsDAO = new AlreadyGoodsDAOImp(); //操作订单的事务实例
		List<Order> orders = orderDAO.getAll();		//获取所有的订单
		for(int i = 0;i<orders.size();i++){
			
			try {
				itemsJSONArray = new JSONArray();
				order = orders.get(i);		//获取当前的order对象
				
				JSONArray jsonArrayGET = getVegetable(order.getTablenum(),alreadyGoodsDAO);
				vegetableJSONArray = jsonArrayGET.getJSONArray(0);	
				numJSONArray = jsonArrayGET.getJSONArray(1);
		 
				itemsJSONArray.put(0,order.getTablenum());	//放入桌号
				itemsJSONArray.put(1,order.getOrderprogress());	//放入进度
				itemsJSONArray.put(2,vegetableJSONArray);	//放入桌号对应所有菜的名字
				itemsJSONArray.put(3,numJSONArray);		//放入桌号对应所有菜的数量
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonArray.put(itemsJSONArray);
			// itemsJSONArray;
		}
		
		return null;
	}
	
	//根据相应的桌号查询相对应的菜，然后封装在jsonArray中
	public static JSONArray getVegetable(int tablenum,AlreadyGoodsDAO alreadyGoodsDAOImp){
		JSONArray jsonArray = new JSONArray();
		JSONArray jsonArray1 = new JSONArray();		//放对应桌子的所有的菜
		JSONArray jsonArray2 = new JSONArray();		//放对应的桌子的所有菜的数量
		List<AlreadyGoods> alreadies = alreadyGoodsDAOImp.getAll();
		for(AlreadyGoods alreadyGoods :alreadies){
			jsonArray1.put(alreadyGoods.getGoodsname());
			jsonArray2.put(alreadyGoods.getNum());
			
		}
		jsonArray.put(jsonArray1);
		jsonArray.put(jsonArray2);
		return jsonArray;
	}
	
	
}
