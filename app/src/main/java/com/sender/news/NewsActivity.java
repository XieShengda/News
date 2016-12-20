package com.sender.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private PagerTabStrip tab;
    private FragmentNews top, shehui, guonei, guoji, yule, tiyu, junshi, keji, caijing, shishang;
    private List<Fragment> fragmentList;
    private List<String> pagerTitles;
    private final String APPKEY = "2e5c3bfb989a94a194cc3201ad75ec7e";
    private StringBuilder api;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        mViewPager = (ViewPager) findViewById(R.id.news_pager);
        tab = (PagerTabStrip) findViewById(R.id.pager_title);
        makeFragmentList();
        makePagerTitles();
        tab.setTextColor(Color.WHITE);
        tab.setTabIndicatorColor(Color.WHITE);

        mViewPager.setAdapter(new MyFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList, pagerTitles));


    }

    @NonNull
    private Bundle getBundle(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("news_url", getNewsUrl(type));
        return bundle;
    }

    private void makePagerTitles() {
        pagerTitles = new ArrayList<>();
        pagerTitles.add("头条");
        pagerTitles.add("社会");
        pagerTitles.add("国内");
        pagerTitles.add("国际");
        pagerTitles.add("娱乐");
        pagerTitles.add("体育");
        pagerTitles.add("军事");
        pagerTitles.add("科技");
        pagerTitles.add("财经");
        pagerTitles.add("时尚");

    }

    private void makeFragmentList() {
        fragmentList = new ArrayList<>();
        top = new FragmentNews();
        top.setArguments(getBundle("top"));
        fragmentList.add(top);

        shehui = new FragmentNews();
        shehui.setArguments(getBundle("shehui"));
        fragmentList.add(shehui);

        guonei = new FragmentNews();
        guonei.setArguments(getBundle("guonei"));
        fragmentList.add(guonei);

        guoji = new FragmentNews();
        guoji.setArguments(getBundle("guoji"));
        fragmentList.add(guoji);

        yule = new FragmentNews();
        yule.setArguments(getBundle("yule"));
        fragmentList.add(yule);

        tiyu = new FragmentNews();
        tiyu.setArguments(getBundle("tiyu"));
        fragmentList.add(tiyu);

        junshi = new FragmentNews();
        junshi.setArguments(getBundle("junshi"));
        fragmentList.add(junshi);

        keji = new FragmentNews();
        keji.setArguments(getBundle("keji"));
        fragmentList.add(keji);

        caijing = new FragmentNews();
        caijing.setArguments(getBundle("caijing"));
        fragmentList.add(caijing);

        shishang = new FragmentNews();
        shishang.setArguments(getBundle("shishang"));
        fragmentList.add(shishang);


    }

    private String getNewsUrl(String type) {
        api = new StringBuilder("http://v.juhe.cn/toutiao/index?type=");
        api.append(type);
        api.append("&key=");
        api.append(APPKEY);
        return api.toString();
    }


}
