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
	//ʹ��SharedPreferences����¼�����ʹ�ô���
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
				 //��ȡSharedPreferences����Ҫ������
		        preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
		        int count = preferences.getInt("count", 0);
		        //�жϳ�����ڼ������У�����ǵ�һ����������ת������ҳ��
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
		        //��������
		        editor.putInt("count", ++count);
		        //�ύ�޸�
		        editor.commit();
			}
		});

		homeImage.setAnimation(alphaAnimation);
		homeImage.setVisibility(View.VISIBLE);
	}
}