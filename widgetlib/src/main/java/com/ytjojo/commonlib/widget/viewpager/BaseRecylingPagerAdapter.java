package com.ytjojo.commonlib.widget.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecylingPagerAdapter<T> extends RecyclingPagerAdapter {
    
    private List<T> mDataList;
    
    private Context mContext;
    
    private int mLayoutRes;
    
    private LayoutInflater mInflater;
    
    public BaseRecylingPagerAdapter(Context c, int layout, List<T> list) {
        this.mContext = c;
        this.mDataList = list;
        this.mLayoutRes = layout;
        this.mInflater = LayoutInflater.from(mContext);
    }
    
    /**
     * @param position
     * @param convertView
     * @param container
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutRes, container, false);
            
        }
        bindViewData(position, convertView, container);
        return convertView;
    }
    
    public abstract View bindViewData(int position, View convertView, ViewGroup parent);
    
    /**
     * @return
     */
    @Override
    public int getCount() {
        if (mDataList !=null && mDataList.size()>0) {
            return mDataList.size();
        }
        return 0;
    }
    
}