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
import com.caiqiufang.utils.WebUtil;
import com.caiqiufang.vegetablesstyle.DetailedIntroduceActivity;
import com.example.caiqiufang.TabOneFragment.NewsAsyncTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 秋放
 *@功能 显示所有的酒水
 */
public class TabTwoFragment extends Fragment implements OnRefreshListener,
OnLoadListener{
	
	private ListView mListView;
	private VsAdapter adapter;
	private RefreshLayout swipeLayout;
	private View header;
	// private  static  String URL ="www.baidu.com";
	private static String mURL ="/OrderServer/client/Goods_getwin.action";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab02,container,false);
		initView(view);
		initViewListener();
		
		return view;
	}
	
	//初始化所有的组件 
	private void initView(View view) {
		mListView = (ListView)view.findViewById(R.id.lv_vegetables_style);
		//执行异步的加载
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
	//swiperefreshlayout监听
	private void setListener() {
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setOnLoadListener(this);
	}
	
	//重写swiperefreshLayout方法实现下拉时刷新
	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				new NewsAsyncTask().execute(Config.getSERVER_IP()+mURL);
				if (adapter!=null) {
					adapter.notifyDataSetChanged();
				}
				swipeLayout.setRefreshing(false);
			}
		}, 500);
	}
	
	//上啦加载更多
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
	public void initViewListener(){
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
				Bitmap bitmap = null;		//初次思想为用tag标记，但获得数据比较麻烦，所以直接try catch 然后给出消息提醒
				try {
					bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
				} catch (Exception e) {
					showMessage("请耐心等待加载！！");
				}
				//System.out.println(bitmap+"***********************************");
				DetailedIntroduceActivity.InstanceBitmap(bitmap);	//用当前的图片的bitmap实例化下个活动的bitmap
				//跳转Activity并发送数据包
				if (bitmap !=null) {
					Intent intent = new Intent(getActivity(),DetailedIntroduceActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("name", iv_vegetable_name.getText().toString());
					bundle.putString("desc", tv_vegetable_desc.getText().toString());
					bundle.putString("price",tv_vegetable_price.getText().toString());
					intent.putExtra("data", bundle);
					//imageView.getb
					startActivity(intent);
				}else{
					showMessage("当前的网速不好，请您加载完毕后继续选菜！！");
				}
				
			}
		});
	}
	
	//定义显示提醒信息的方法
	public void showMessage(String message){
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	//定义异步加载的类
	class NewsAsyncTask extends AsyncTask<String,Void,List<VegetableBean>>{
		
        @Override
        protected List<VegetableBean> doInBackground(String... params) {
            return getJsonData(params[0]);		
        }

        @Override
        protected void onPostExecute(List<VegetableBean> newsBeen) {
            super.onPostExecute(newsBeen);
            if (newsBeen!=null&&getActivity().getApplication()!=null) {
            	 if (null == TabTwoFragment.this || !TabTwoFragment.this.isAdded()) {
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
