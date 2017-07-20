package com.caiqiufang.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by 秋放.
 */
public class ImageLoader {
    private  ImageView mImageView;
    private  String murl;
    private LruCache<String,Bitmap> mCache;
    private ListView mListView;
    private Set<NewsAsynTask> mTask;
    //创建cache
  
	public ImageLoader(ListView listView){
        mListView = listView;
        mTask = new HashSet<NewsAsynTask>();
        //获取最大可用内存
        int maxMemory =(int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory /4;
        mCache = new LruCache<String,Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
               //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
        
    }

    //增加到缓存
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromCache(url) ==null){
            mCache.put(url,bitmap);
        }
    }

    //从缓存中获取
    public Bitmap getBitmapFromCache(String url){
        return mCache.get(url);
    }
    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(murl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    public void showImageByThread(ImageView imageView, final String url){
        mImageView = imageView;
        murl = url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitMapFromUrl(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }
    public Bitmap getBitMapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is=null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is =new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection
                    .disconnect();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
            	if (is != null) {
            		is.close();
				}
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public  void showImageByAsynTask(ImageView imageView,String url){
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap ==null){
                //imageView.setImageResource(R.drawable.ic_launcher);
            }else {
                imageView.setImageBitmap(bitmap);
            }
            new NewsAsynTask(url).execute(url);
    }
    public void cancleAllTask(){
        if (mTask !=null){
            for (NewsAsynTask task :mTask){
                task.cancel(false);
            }
        }
    }
    public void loadImages(int start,int end){
        for (int i =start;i<end-1;i++){
            String url = VsAdapter.URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null){
               NewsAsynTask task = new NewsAsynTask(url);
                task.execute(url);
                mTask.add(task);
            }else {
                ImageView imageView =(ImageView) mListView.findViewWithTag(url);
                System.out.println("***************************"+imageView);
                imageView.setImageBitmap(bitmap);	//*************************
            }
        }
    }
    private  class  NewsAsynTask extends AsyncTask<String,Void,Bitmap> {
       // private ImageView mimageView;
        private  String mUrl;
        public NewsAsynTask(String url){

            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //从网络获取图片
            Bitmap bitmap = getBitMapFromUrl(params[0]);
            String url = params[0];
            if (bitmap!=null){
                //下载完毕之后不在缓存中的图片加入缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView)mListView.findViewWithTag(mUrl);
            if (imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
