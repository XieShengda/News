package com.sender.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieShengda on 2016/12/17.
 */

public class DataLoader {

    public void loadData(final String newsUrl, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                InputStream is = null;
                BufferedReader reader = null;
                try {
                    url = new URL(newsUrl);
                    is = url.openStream();
                    reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder data = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }

                    decodeJson(data, handler);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }

    private void decodeJson(StringBuilder data, Handler handler) throws JSONException {
        JSONObject jsonObject = new JSONObject(data.toString());
        if (jsonObject.getString("reason").equals("成功的返回")) {
            JSONObject result = jsonObject.getJSONObject("result");
            String stat = result.getString("stat");
            if (stat.equals("1")) {
                JSONArray newsData = result.getJSONArray("data");
                List<NewsBean> beanList = new ArrayList<>();
                for (int i = 0; i < newsData.length(); i++) {
                    JSONObject dataItem = newsData.getJSONObject(i);

                    NewsBean bean = new NewsBean(
                            dataItem.getString("title"),
                            dataItem.getString("date"),
                            dataItem.getString("author_name"),
                            dataItem.getString("thumbnail_pic_s"),
                            dataItem.getString("url")

                    );

                    beanList.add(bean);

                }
                Message message = Message.obtain();
                message.obj = beanList;
                handler.sendMessage(message);
            }
        }
    }


    public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String imgUrl;
        private ImageView imageView;
        private LruCache<String, Bitmap> bitmapLruCache;

        public ImageAsyncTask(ImageView imageView, LruCache<String, Bitmap> bitmapLruCache) {
            this.bitmapLruCache = bitmapLruCache;
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null && imgUrl.equals(imageView.getTag())) {
                imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            imgUrl = strings[0];
            Bitmap bitmap = bitmapLruCache.get(imgUrl);
            if (bitmap == null) {
                bitmap = loadBitmap(imgUrl);
                bitmapLruCache.put(imgUrl, bitmap);
            }
            return bitmap;
        }
    }

    private Bitmap loadBitmap(String imageUrl) {
        URL url;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageUrl);
            is = url.openStream();
            bitmap = BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
