package com.caiqiufang.loader;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.caiqiufang.bean.VegetableBean;
import com.example.caiqiufang.R;

/**
 * Created by ��� on 2016/8/12.
 */
public class VsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<VegetableBean> mList;
    private LayoutInflater minflater;
    private ImageLoader mImageLoader;
    private int mStart;
    private  int mEnd;
    public static  String [] URLS;
    private  boolean mFirstIn;
    
    public VsAdapter(Context context, List<VegetableBean> data, ListView listView){
        mList = data;
        minflater = LayoutInflater.from(context);//**********************�õ�����������ʱΪ��
        mImageLoader = new ImageLoader(listView);
        URLS = new String [data.size()];
        mFirstIn = true;
        for (int i=0;i<data.size();i++){
            URLS[i] = data.get(i).vsUrl;
        }
        listView.setOnScrollListener(this); //��listviewע����������¼�
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
          //iv_vegetable_img,tv_vegetable_name,tv_vegetable_desc,tv_vegetable_price
            convertView = minflater.inflate(R.layout.tab01_items_activity,null);
            viewHolder.ivIcon =(ImageView) convertView.findViewById(R.id.iv_vegetable_img);
            viewHolder.tvname =(TextView) convertView.findViewById(R.id.iv_vegetable_name);
            viewHolder.tvdesc = (TextView)convertView.findViewById(R.id.tv_vegetable_desc);
            viewHolder.tvprice = (TextView)convertView.findViewById(R.id.tv_vegetable_price);
//            final AnimationDrawable anim = (AnimationDrawable)viewHolder.
//            		ivIcon.getBackground();
//            anim.start();
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.ivIcon.setImageResource(R.drawable.ic_launcher);
        String url = mList.get(position).vsUrl;   //��utl��ͼƬ������ݵİ󶨣��Է���ȷ��listview���ز�����ȷ��urlͼƬ
        viewHolder.ivIcon.setTag(url);
        //new ImageLoader().showImageByThread(viewHolder.ivIcon,url);//�ö��̵߳ķ�������ͼƬ
        mImageLoader.showImageByAsynTask(viewHolder.ivIcon,url);
        viewHolder.tvname.setText(mList.get(position).vsName);
        viewHolder.tvdesc.setText(mList.get(position).vsDesc);
        viewHolder.tvprice.setText(mList.get(position).vsPrice);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //�˷����жϹ���״̬,�����ֹͣ״̬������ؿɼ���
        if (scrollState ==SCROLL_STATE_IDLE){
            //���ؿɼ���
           // mImageLoader.loadImages(mStart,mEnd);			
        }else{			//���������״̬����ֹͣһ�еļ���
        	
            mImageLoader.cancleAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem+visibleItemCount;
        //��һ����ʾ��ʱ�����
        if (mFirstIn&&visibleItemCount>0){
            mImageLoader.loadImages(mStart,mEnd);
            mFirstIn = false;
        }
    }

    class  ViewHolder{
    	
        public TextView tvname,tvdesc,tvprice;
        public ImageView ivIcon;
    }

}
