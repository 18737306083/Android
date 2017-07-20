package com.dachu.activity;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import com.dachu.constant.Constant;
import com.dachu.http.Asynck;
import com.future.dachu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//点击某项菜单后进入的界面
public class AddItems extends Activity implements OnClickListener {
	ImageView img;
	ListView lvContent;
	Button submit;
	String table;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		img = (ImageView) findViewById(R.id.im_back_item);
		lvContent = (ListView) findViewById(R.id.list_content_item);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		img.setOnClickListener(this);
		Intent intent = getIntent();
		// 获取上一级内容
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, String>> bd = (ArrayList<Map<String, String>>) intent
				.getExtras().getSerializable("key");
		 
		table=(String) intent
				.getExtras().getSerializable("table");
		SimpleAdapter simblesr = new SimpleAdapter(AddItems.this, bd,
				R.layout.list_item_content, new String[] { "names", "number" },
				new int[] { R.id.tv_item_menu, R.id.tv_item_number });
		lvContent.setAdapter(simblesr);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_back_item:
			AddItems.this.finish();
			// 回到上一级
			break;
		case R.id.submit:
			// 提交完成
			try {
				JSONObject ob = new JSONObject();
				 
				ob.put("progress", "2");
				ob.put("table", table);
				String sucuss = new Asynck(AddItems.this, ob).execute(
						"http://10306.5avpn.com/OrderServer/cook/Cook_modifyProgress").get();

				 
					Toast.makeText(AddItems.this, "提交成功!", Toast.LENGTH_SHORT)
							.show();
					AddItems.this.finish();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

	}

}
