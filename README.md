

###  一.概述

项目需求在首页加入活动视图,每隔数秒钟自动切换到下一帧,支持手动滑动切换活动视图,在活动视图底部有圆形的指示点,整体的活动视图随着列表上拉而隐藏.
典型的ViewPager,加入自动滑动,加入指示器.并且和listview手势不冲突.


在 github搜索了一下,发现了[android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager),fork and clone.
发现demo还是很简洁的.

* 支持自动滑动
* 支持手势滑动
* 支持自动回收

但是:

* 没有指示器
* 与listview手势冲突

基于以上,对 [android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager) 进行二次封装.解决以上2个问题.

![demo](https://raw.githubusercontent.com/qaza2008/AutoScrollViewPagerWithIndicator/master/ezgif.com-crop.gif)

![image](https://raw.githubusercontent.com/qaza2008/AutoScrollViewPagerWithIndicator/master/ezgif.com-crop2.gif)


#### 引用:
环境: idea  ant编译

源码地址: [https://github.com/qaza2008/AutoScrollViewPagerWithIndicator](https://github.com/qaza2008/AutoScrollViewPagerWithIndicator)

### 二.代码示例

1.AutoScrollViewPagerWithIndicator 展示


```
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


    private float mDownX, mDownY;

    /**
     * if ViewPager is added as header in Listview .this Method can keep ListView and ViewPager scroll by the finger oritation.
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:


                Log.d("ACTION_MOVE" , "X:"+Math.abs(ev.getX() - mDownX)+ " : Y :"+  (ev.getY() - mDownY));
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    Log.d("ACTION_MOVE" , "X>Y");
                    getParent().requestDisallowInterceptTouchEvent(true);

                } else {
                    Log.d("ACTION_MOVE" , "X<Y");
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

}
   
```


```
调用:
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
```
* 将AutoScrollViewPager 封装到FrameLayout中.
* 新增LinearLayout用于镶嵌indicator,并保存到距离底部15dp的位置处.添加到FrameLayout.
* initIndicator()方法用于初始化indicatoer数量和初始位置.
* 通过ViewPager.OnPageChangeListener中的onPageSelected 方法回调处理indicator的位置.
* dispatchTouchEvent 方法的重写是避免作为ListView 的Header 时手势冲突.//我就遇到了
* 以上很到理解.
  

2.单独的viewPager 展示


``` 
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
```


*  从布局文件中获取autoScrollViewPager对象,初始化数据及Adapter.
*  通过判断当初始化的数据来进行展示.当只有一条的时候停止自动滑动展示.
*  具体的AutoScrollViewPager的设置参数可以参考 [android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager).

3.作为ListView的Header调用的方式.

```
rivate List<String> dataList;

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
```

* 和直接调用大同小异,此次直接作为header.
* 由于有了手势的判断处理,不会有多余的判断了.









