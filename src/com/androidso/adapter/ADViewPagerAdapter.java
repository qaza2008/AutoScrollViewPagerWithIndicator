package com.androidso.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import thirdpart.salvage.RecyclingPagerAdapter;

import java.util.List;


/**
 * Created by qaza2008  on 15/7/31.
 * www.androidso.com
 */
public class ADViewPagerAdapter extends RecyclingPagerAdapter {
    private Activity context;
    private List<Integer> adList;
    private boolean isInfiniteLoop;
    public static final int REQUEST_CODE = 1342;

    public ADViewPagerAdapter(Activity context, List<Integer> adList) {
        this.context = context;
        this.adList = adList;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : adList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % adList.size() : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view = holder.imageView;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Integer id = adList.get(getPosition(position));
        holder.imageView.setImageResource(id);
        return view;
    }


    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ADViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
