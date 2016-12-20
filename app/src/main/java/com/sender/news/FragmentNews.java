package com.sender.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by XieShengda on 2016/12/17.
 */

public class FragmentNews extends Fragment implements AdapterView.OnItemClickListener {
    private DataLoader dataLoader;
    private ListView newsList;
    private NewsAdapter adapter;
    private List<NewsBean> list;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataLoader = new DataLoader();
    }


    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_news, container, false);
        newsList = (ListView) view.findViewById(R.id.news_list);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                list = (List<NewsBean>) msg.obj;
                adapter = new NewsAdapter(getActivity(), list);
                newsList.setAdapter(adapter);
                newsList.setOnItemClickListener(FragmentNews.this);
            }
        };
        String news_url = getArguments().getString("news_url");
        dataLoader.loadData(news_url, handler);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        String url = list.get(i).getUrl();
        intent.putExtra("news_content", url);

        startActivity(intent);

    }
}
