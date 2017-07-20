package com.example.caiqiufang;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.aiweite.swiperefreshlayout.RefreshLayout;
import com.aiweite.swiperefreshlayout.RefreshLayout.OnLoadListener;
import com.caiqiufang.bean.VegetableBean;
import com.caiqiufang.config.Config;
import com.caiqiufang.loader.VsAdapter;
import com.caiqiufang.mine.LoginActivity;
import com.caiqiufang.mine.MyMenuActivity;
import com.caiqiufang.mine.MyOrderFormActivity;
import com.caiqiufang.scan.MipcaActivityCapture;
import com.caiqiufang.utils.WebUtil;
import com.caiqiufang.vegetablesstyle.CriticismActivity;
import com.caiqiufang.vegetablesstyle.DetailedIntroduceActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.example.caiqiufang.SatelliteMenu.SateliteClickedListener;

/**
 * @author 秋放
 *介绍：首页模块菜系的处理类
 */

public class TabOneFragment extends Fragment implements OnRefreshListener,
OnLoadListener {
	
	private Button bt_collect;
	private Button bt_critisim;
	private  SatelliteMenu menu;
	private ListView mListView;
	VsAdapter adapter;
	private RefreshLayout swipeLayout;
	private View header;
	// private  static  String URL ="www.baidu.com";
	private  String mURL ="/OrderServer/client/Goods_getvs.action";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab01,container,false);
		initView(view);
		initViewListener();
		return view;
	}
	
	/**
	 *功能：初始化所有界面中的view组件
	 * @param view
	 */
	private void initView(View view) {
		//初始化卫星菜单参数
		menu = (SatelliteMenu)view. findViewById(R.id.menu);
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
	       items.add(new SatelliteMenuItem(6, R.drawable.ic_1));
	       items.add(new SatelliteMenuItem(5, R.drawable.ic_3));
	       items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
	       items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
	       items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
	       items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
	       menu.addItems(items);    
	    //初始化收藏和评价两个Button
		bt_collect = (Button)view.findViewById(R.id.bt_vegetables_style_collect);
		bt_critisim = (Button)view.findViewById(R.id.bt_vegetables_style_criticism);
		mListView = (ListView)view.findViewById(R.id.lv_vegetables_style);
		
		new NewsAsyncTask().execute(Config.getSERVER_IP()+mURL);
		
		swipeLayout = (RefreshLayout)view. findViewById(R.id.swipe_container);
		swipeLayout.setColorSchemeResources(R.color.color_bule2,
				R.color.color_bule,
				R.color.color_bule2,
				R.color.color_bule3);
			setListener();
			onRefresh();
			
			swipeLayout.post(new Thread(new Runnable() {
				
				@Override
				public void run() {
					swipeLayout.setRefreshing(true);
				}
			}));
	}
	
	private void setListener() {
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setOnLoadListener(this);
	}
	
	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				new NewsAsyncTask().execute(Config.getSERVER_IP()+mURL);
				Toast.makeText(getActivity(), "*****"+Config.getSERVER_IP()+"****", Toast.LENGTH_SHORT).show();
				if (adapter!=null) {
					adapter.notifyDataSetChanged();
				}
				swipeLayout.setRefreshing(false);
			}
		}, 500);
	}
	
	@Override
	public void onLoad() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				//new NewsAsyncTask().execute(URL);
				adapter.notifyDataSetChanged();
				swipeLayout.setRefreshing(false);
			}
		}, 1000);

	}
	
	/**
	 * 功能：为所有该解析界面中的组件添加监听
	 */
	private void initViewListener() {
		bt_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "您已经收藏了该店铺", Toast.LENGTH_LONG).show();
			}
		});
		bt_critisim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),CriticismActivity.class);
				startActivity(intent);
			}
		});
		//listview中的item监听
		mListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				ImageView imageView = (ImageView) view.findViewById(R.id.iv_vegetable_img);
				//iv_vegetable_name
				TextView iv_vegetable_name = (TextView)view.findViewById(R.id.iv_vegetable_name);
				//tv_vegetable_desc
				TextView tv_vegetable_desc = (TextView)view.findViewById(R.id.tv_vegetable_desc);
				//tv_vegetable_price
				TextView tv_vegetable_price = (TextView)view.findViewById(R.id.tv_vegetable_price);
				Bitmap bitmap = null;
				try {
					bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
				} catch (Exception e) {
					showMessage("请耐心等待加载！！");
				}
				//System.out.println(bitmap+"***********************************");
				DetailedIntroduceActivity.InstanceBitmap(bitmap);	//用当前的图片的bitmap实例化下个活动的bitmap
				//跳转Activity并发送数据包
				if (bitmap != null) {
					Intent intent = new Intent(getActivity(),DetailedIntroduceActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("name", iv_vegetable_name.getText().toString());
					bundle.putString("desc", tv_vegetable_desc.getText().toString());
					bundle.putString("price",tv_vegetable_price.getText().toString());
					intent.putExtra("data", bundle);
					//imageView.getb
					startActivity(intent);
				}
			}
		});
		
		//卫星菜单监听
		 menu.setOnItemClickedListener(new SateliteClickedListener(){

				@Override
				public void eventOccured(int id) {
					switch (id) {
					case 1:		//单击了收藏菜单按钮
						showMessage("恭喜您收藏店铺成功！！！");
						//下面做出逻辑处理
						break;
					case 2:		//单击了扫一扫菜单按钮
						Intent intent2 = new Intent(getActivity(),MipcaActivityCapture.class);
						startActivity(intent2);
						break;
					case 3:		//单击了 查看订单 菜单按钮
						Intent intent3 = new Intent(getActivity(),MyOrderFormActivity.class);
						startActivity(intent3);
						break;
					case 4:		//单击了  菜单  菜单按钮
						Intent intent4 = new Intent(getActivity(),MyMenuActivity.class);
						startActivity(intent4);
						break;
					case 5:		//单击了  登录  菜单按钮
						Intent intent5 = new Intent(getActivity(),LoginActivity.class);
						startActivity(intent5);
						break;
					case 6:		//单击了  评价   菜单按钮
						Intent intent6 = new Intent(getActivity(),CriticismActivity.class);
						startActivity(intent6);
						break;
					default:
						break;
					}
					
					
					Log.i("sat", "Clicked on " + id);
				}
		  });
	}
	public void showMessage(String message){
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	class NewsAsyncTask extends AsyncTask<String,Void,List<VegetableBean>>{
	
	        @Override
	        protected List<VegetableBean> doInBackground(String... params) {
	            return getJsonData(params[0]);		
	        }
	
	        @Override
	        protected void onPostExecute(List<VegetableBean> newsBeen) {
	            super.onPostExecute(newsBeen);
	            if (newsBeen!=null&&getActivity().getApplication()!=null) {
	            	 if (null == TabOneFragment.this || !TabOneFragment.this.isAdded()) {
	                     return;
	                 }
	            	adapter = new VsAdapter(getActivity().getApplicationContext(),newsBeen,mListView);	//*********
	 	            mListView.setAdapter(adapter);
				}
	        }
	
	        /**
	         * @param url
	         * @return
	         */
	        private List<VegetableBean> getJsonData(String url) {
	            List<VegetableBean> vsBeanList = new ArrayList<VegetableBean>();
	            
	                JSONObject jsonObject;
	                VegetableBean vsBean;
	                JSONArray jsonArray = null;
	                try {
	                   
						try {
							jsonArray = WebUtil.getJSONArrayByUrl(url);
							System.out.println("(**************"+jsonArray.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (jsonArray!=null) {
							if (jsonArray.length()>0) {
								 for (int i=0;i<jsonArray.length();i++){	//****************************
				                        jsonObject = jsonArray.getJSONObject(i);
				                        vsBean = new VegetableBean();
				                        vsBean.vsName = jsonObject.getString("vsname");
				                        vsBean.vsDesc = jsonObject.getString("vsdesc");
				                        vsBean.vsPrice = "￥"+jsonObject.getString("vsprice");
				                        vsBean.vsUrl = Config.SERVER_IP+"/OrderServer/goodsimages/"+jsonObject.getString("vspath");
				                        vsBeanList.add(vsBean);
				                    }
							}
						}
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                } finally {
	                }
	               // Log.d("MainActivity", "getJsonData: "+jsonString+"***************************");//�д�
	            
	            return vsBeanList;
	        }
	   }
}
