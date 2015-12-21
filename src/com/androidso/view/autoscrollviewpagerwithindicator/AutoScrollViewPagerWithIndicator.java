package com.androidso.view.autoscrollviewpagerwithindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import com.androidso.utils.AndroidUtils;

/**
 * Created by qaza2008 on 15/7/31.
 * www.androidso.com
 */
public class AutoScrollViewPagerWithIndicator extends FrameLayout {


    public AutoScrollViewPagerWithIndicator(Context paramContext) {
        this(paramContext, null);
    }

    LinearLayout indicatorLayout;
    AutoScrollViewPager viewPager;

    public AutoScrollViewPagerWithIndicator(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);


    }

    public AutoScrollViewPagerWithIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setLongClickable(true);
        viewPager = new AutoScrollViewPager(context, attrs);
        addView(viewPager);

        indicatorLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, AndroidUtils.dip2px(context, 20));
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.setMargins(0, 0, 0, AndroidUtils.dip2px(context, 15));
        indicatorLayout.setLayoutParams(params);
        indicatorLayout.setBackgroundResource(R.drawable.viewpage_header_ad_container_shape);
        indicatorLayout.setVisibility(View.INVISIBLE);
        addView(indicatorLayout);
    }

    public AutoScrollViewPager getViewPager() {
        return viewPager;
    }

    public void setAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPager.setOnPageChangeListener(listener);
    }

    public LinearLayout getIndicatorLayout() {
        return indicatorLayout;
    }

    public void initIndicator(int count) {
        this.initIndicator(count, 0);

    }

    public void initIndicator(int count, int index) {
        if (count == 0) {
            return;
        }
        if (count <= index) {
            index = 0;
        }
        indicatorLayout.setVisibility(VISIBLE);
        indicatorLayout.removeAllViews();
        for (int i = 0; i < count; i++) {

            ImageView ivDot = new ImageView(getContext());
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(AndroidUtils.dip2px(getContext(), 9), AndroidUtils.dip2px(getContext(), 9));
            ivParams.setMargins(AndroidUtils.dip2px(getContext(), 2.5f), 0, AndroidUtils.dip2px(getContext(), 2.5f), 0);
            ivParams.gravity = Gravity.CENTER;
            ivDot.setLayoutParams(ivParams);
            if (index == i) {
                ivDot.setImageResource(R.drawable.dot_white);
            } else {
                ivDot.setImageResource(R.drawable.dot_gray_c6c6c6);
            }
            indicatorLayout.addView(ivDot);
        }
    }

    public void selectIndicator(int pos) {
        if (indicatorLayout != null && indicatorLayout.getChildCount() > pos) {
            clearIndicator();
            chooseIndicator(pos);

        }

    }

    private void clearIndicator() {
        for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
            View view = indicatorLayout.getChildAt(i);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(R.drawable.dot_gray_c6c6c6);
            }
        }
        invalidate();

    }

    private void chooseIndicator(int pos) {
        View view = indicatorLayout.getChildAt(pos);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(R.drawable.dot_white);
        }
        invalidate();
    }

    public int getScreenWidth() {
        Context context = getContext();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        return width;
    }


    private float mDownX, mDownY;

    /**
     * if ViewPager is added as header in Listview .this Method can keep ListView and ViewPager scroll by the finger oritation.
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDownX = ev.getX();
                mDownY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDownX) > (ev.getY() - mDownY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);

                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
