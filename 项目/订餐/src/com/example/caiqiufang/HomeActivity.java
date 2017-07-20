package com.example.caiqiufang;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	ImageView homeImage;
	//使用SharedPreferences来记录程序的使用次数
    SharedPreferences preferences ;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		homeImage = (ImageView) findViewById(R.id.homeimg);
			
		AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
		alphaAnimation.setDuration(3000);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				 //读取SharedPreferences中需要的数据
		        preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
		        int count = preferences.getInt("count", 0);
		        //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		        if (count == 0) {
		            Intent intent = new Intent();
		            intent.setClass(getApplicationContext(),GuideViewActivity.class);
		            startActivity(intent);
		            finish();
		        }else{
		        	Intent intent = new Intent();
		            intent.setClass(getApplicationContext(),MainActivity.class);
		            startActivity(intent);
		            finish();
		        }
		        
		        Editor editor = preferences.edit();
		        //存入数据
		        editor.putInt("count", ++count);
		        //提交修改
		        editor.commit();
			}
		});

		homeImage.setAnimation(alphaAnimation);
		homeImage.setVisibility(View.VISIBLE);
	}
}