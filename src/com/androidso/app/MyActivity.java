package com.androidso.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import com.androidso.adapter.ADViewPagerAdapter;
import com.androidso.view.autoscrollviewpagerwithindicator.AutoScrollViewPagerWithIndicator;
import com.androidso.view.autoscrollviewpagerwithindicator.R;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViewPager();
    }

    private AutoScrollViewPagerWithIndicator autoScrollViewPager;
    private ADViewPagerAdapter adViewPagerAdapter;
    private List<Integer> idList;

    private void initViewPager() {

        View headerView = this.findViewById(R.id.view_pager_layout);
        autoScrollViewPager = (AutoScrollViewPagerWithIndicator) headerView.findViewById(R.id.header_view_pager);
        idList = new ArrayList<Integer>();
        idList.add(R.drawable.a);
        idList.add(R.drawable.b);
        idList.add(R.drawable.c);
        adViewPagerAdapter = new ADViewPagerAdapter(this, idList);
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

    public void ChangeDemo(View view) {
        Intent intent = new Intent();
        intent.setClass(this, HeaderListViewActivity.class);
        startActivity(intent);


    }
}
