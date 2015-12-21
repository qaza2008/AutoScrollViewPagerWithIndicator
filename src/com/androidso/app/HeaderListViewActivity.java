package com.androidso.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import com.androidso.adapter.ADViewPagerAdapter;
import com.androidso.view.autoscrollviewpagerwithindicator.AutoScrollViewPagerWithIndicator;
import com.androidso.view.autoscrollviewpagerwithindicator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qaza2008 on 15/12/21.
 * this demo showes the viewpager as listview's header
 */
public class HeaderListViewActivity extends Activity {
    private AutoScrollViewPagerWithIndicator autoScrollViewPager;
    private ADViewPagerAdapter adViewPagerAdapter;
    private List<Integer> idList;

    private ListView lv;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headerlistview);
        init();
    }

    private void init() {

        lv = (ListView) findViewById(R.id.lv);
        initViewPager();


        idList = new ArrayList<Integer>();
        idList.add(R.drawable.a);
        idList.add(R.drawable.b);
        idList.add(R.drawable.c);

        dataList = new ArrayList<String>();
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");
        dataList.add("a");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dataList);
        lv.setAdapter(adapter);


    }

    private void initViewPager() {

        View headerView = LayoutInflater.from(this).inflate(R.layout.autoscroll_viewpager, null);
        autoScrollViewPager = (AutoScrollViewPagerWithIndicator) headerView.findViewById(R.id.header_view_pager);
        idList = new ArrayList<Integer>();
        idList.add(R.drawable.a);
        idList.add(R.drawable.b);
        idList.add(R.drawable.c);
        adViewPagerAdapter = new ADViewPagerAdapter(this, idList);
        lv.addHeaderView(headerView);
        assemData();

    }

    private void assemData() {
        if (idList != null && !idList.isEmpty()) {

            if (idList.size() <= 1) {
                adViewPagerAdapter.setInfiniteLoop(false);
            } else {
                adViewPagerAdapter.setInfiniteLoop(true);
            }
            autoScrollViewPager.setVisibility(View.VISIBLE);
            AutoScrollViewPager viewPager = autoScrollViewPager.getViewPager();
            autoScrollViewPager.initIndicator(idList.size());
            autoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    autoScrollViewPager.selectIndicator(position % (idList.size()));

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            viewPager.setInterval(2000);
            viewPager.setAutoScrollDurationFactor(3);
            viewPager.startAutoScroll();
            viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % idList.size());
            autoScrollViewPager.setAdapter(adViewPagerAdapter);

        } else {
            autoScrollViewPager.setVisibility(View.GONE);
        }

    }
}
