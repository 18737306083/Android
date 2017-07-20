package com.example.caiqiufang;

import java.util.ArrayList;
import java.util.List;
import com.caiqiufang.scan.MipcaActivityCapture;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author 彩秋放
 *
 */
public class MainActivity extends FragmentActivity {
	//Fragment
	private ViewPager mviewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;
	
	private TextView mChatTextView;
	private TextView mFriendTextView;
	private TextView mContactTextView;
	
	private LinearLayout chatLinearLayout;
	private int mScreen1_3;
	private ImageView mTabline;
	private int mCurrentPagerIndex;
	//top1上方的扫一扫
	private LinearLayout linearlayout_scan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initTabline();
		initView();
		initViewListener();
	}


	private void initTabline() {
		mTabline = (ImageView) findViewById(R.id.tabline);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels/3;
		LayoutParams lp= mTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mTabline.setLayoutParams(lp);
	}

	private void initView() {
		mviewPager = (ViewPager)findViewById(R.id.id_viewpager);
		
		linearlayout_scan = (LinearLayout)findViewById(R.id.linearlayout_scan);
		
		mDatas = new ArrayList<Fragment>();
		
		mChatTextView =(TextView) findViewById(R.id.id_tvchat);
		mFriendTextView = (TextView) findViewById(R.id.id_tvfriend);
		mContactTextView = (TextView) findViewById(R.id.id_tvcontact);
		chatLinearLayout = (LinearLayout)findViewById(R.id.id_ll_chat);
		TabOneFragment tab01 = new TabOneFragment();
		TabTwoFragment tab02 = new TabTwoFragment();
		TabThreeFragment tab03 = new TabThreeFragment();
		
		mDatas.add(tab01);
		mDatas.add(tab02);
		mDatas.add(tab03);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return mDatas.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mDatas.get(arg0);
			}
		};
		
		mviewPager.setAdapter(mAdapter);
		//当ViewPager 滚动结束的时候，改变top2相对应字体的颜色
		mviewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resetTextView();	//此方法为当滚动到下一个页面的时候把上一个页面字体的颜色改变为黑色
				switch (position) {
				case 0:
				
					mChatTextView.setTextColor(Color.parseColor("#F7A517"));
					break;
				case 1:
					mFriendTextView.setTextColor(Color.parseColor("#F7A517"));
					break;
				case 2:
					mContactTextView.setTextColor(Color.parseColor("#F7A517"));
					break;
				default:
					break;
				}
				mCurrentPagerIndex = position;
			}
			
			private void resetTextView() {
				// TODO Auto-generated method stub
				mChatTextView.setTextColor(Color.BLACK);
				mFriendTextView.setTextColor(Color.BLACK);
				mContactTextView.setTextColor(Color.BLACK);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, 
					int positionOffsetPx) {
					
					android.util.Log.d("MainActivity","**position**"+position+"****positionOffset****"+positionOffset+"***positionOffsetPx*****"+positionOffsetPx);
					LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mTabline.getLayoutParams();
					if (mCurrentPagerIndex ==0&&position ==0) {	//0->1页
						/*
						 * position   0->1,最后变为1（突变）
						 * positionOffset 为0->1(逐渐变化)
						 * positionOffsetPx 从零变为屏幕的三分之一（突变）
						 *       
						 */
						//变化情况
						layoutParams.leftMargin = (int) (positionOffset*mScreen1_3+mCurrentPagerIndex*mScreen1_3);
					}else if(mCurrentPagerIndex==1&&position ==0){//1->0页
					
						//position 一直为0
						//positionOffset 1->0(逐渐变化)
						//positionOffsetPx  从屏幕1/3->0（渐变）
						layoutParams.leftMargin =(int)(mCurrentPagerIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
					}else if (mCurrentPagerIndex == 1&&position == 1) {	//1->2 页
						layoutParams.leftMargin = (int) (mCurrentPagerIndex*mScreen1_3+positionOffset*mScreen1_3);
						
					}else if (mCurrentPagerIndex ==2&&position==1) {	//2->1页
						layoutParams.leftMargin = (int) (mCurrentPagerIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
					}
					mTabline.setLayoutParams(layoutParams);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 功能：处理组件事件
	 */
	private void initViewListener() {
		//扫一扫事件监听
		linearlayout_scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "您单击了扫一扫按钮", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,MipcaActivityCapture.class);
				startActivity(intent);
			}
		});
	}
}
