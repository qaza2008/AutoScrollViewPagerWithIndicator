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


### 
