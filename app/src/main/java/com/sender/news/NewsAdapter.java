package com.sender.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XieShengda on 2016/12/18.
 */

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsBean> mList;
    private LruCache<String, Bitmap> bitmapLruCache;
    private static String[] urlArray;

    public NewsAdapter(Context mContext, List<NewsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int lruCache = maxMemory / 3;
        bitmapLruCache = new LruCache<String, Bitmap>(lruCache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        urlArray = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            urlArray[i] = mList.get(i).getThumbnail_pic_s();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        NewsBean bean = mList.get(i);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.list_image = (ImageView) view.findViewById(R.id.list_image);
            viewHolder.list_title = (TextView) view.findViewById(R.id.list_title);
            viewHolder.list_time = (TextView) view.findViewById(R.id.list_time);
            viewHolder.list_author = (TextView) view.findViewById(R.id.list_author);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageView imageView = viewHolder.list_image;
        viewHolder.list_title.setText(bean.getTitle());
        viewHolder.list_time.setText(bean.getDate());
        viewHolder.list_author.setText(bean.getAuthor_name());
        imageView.setImageResource(R.mipmap.app_icon);
        imageView.setBackgroundColor(Color.parseColor("#4169E1"));
        String imgUrl = urlArray[i];
        imageView.setTag(imgUrl);
        new DataLoader().new ImageAsyncTask(imageView, bitmapLruCache).execute(imgUrl);

        return view;
    }


    class ViewHolder {
        ImageView list_image;
        TextView list_title;
        TextView list_time;
        TextView list_author;
    }
}
