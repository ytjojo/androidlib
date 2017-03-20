/*
 * 文 件 名:  ViewUtil.java
 * 版    权:  VA Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  lijing
 * 修改时间:  2015-5-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.ytjojo.commonlib.design;

import android.annotation.TargetApi;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.ytjojo.commonlib.resource.ResourceHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  lijing
 * @version  [版本号, 2015-5-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ViewUtil {
    

    /**
    * get GridView columns number
    *
    * @param view
    * @return
    */
    public static int getGridViewNumColumns(GridView view) {
        if (view == null || view.getChildCount() <= 0) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return getNumColumnsCompat11(view);
            
        }
        else {
            int columns = 0;
            int children = view.getChildCount();
            if (children > 0) {
                int width = view.getChildAt(0).getMeasuredWidth();
                if (width > 0) {
                    columns = view.getWidth() / width;
                }
            }
            return columns;
        }
    }
    
    @TargetApi(11)
    private static int getNumColumnsCompat11(GridView view) {
        return view.getNumColumns();
    }
    
    private static final String CLASS_NAME_GRID_VIEW = "android.widget.GridView";
    
    private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";
    
    /**
     * get GridView vertical spacing
     * 
     * @param view
     * @return
     */
    public static int getGridViewVerticalSpacing(GridView view) {
        // get mVerticalSpacing by android.widget.GridView
        Class<?> demo = null;
        int verticalSpacing = 0;
        try {
            demo = Class.forName(CLASS_NAME_GRID_VIEW);
            Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
            field.setAccessible(true);
            verticalSpacing = (Integer) field.get(view);
            return verticalSpacing;
        }
        catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException, NoSuchFieldException, InstantiationException,
             * IllegalArgumentException, IllegalAccessException, NullPointException
             */
            e.printStackTrace();
        }
        return verticalSpacing;
    }
    

    // /**
    // * set GistView height which is calculated by {@link # getGridViewHeightBasedOnChildren(GridView)}
    // *
    // * @param view
    // * @return
    // */
    // public static void setGridViewHeightBasedOnChildren(GridView view) {
    // setViewHeight(view, getGridViewHeightBasedOnChildren(view));
    // }


    
    /**
     * get descended views from parent.
     * 
     * @param parent
     * @param filter Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of filter or not.
     * @return
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<T>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass)) || (!includeSubClass && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child, filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    public static Drawable changeDrawableColor(int resourceId, int newImageColor) {
        Drawable drawable = ResourceHelper.getDrawable(resourceId);
        drawable.setColorFilter(newImageColor, Mode.MULTIPLY);
        return drawable;
    }
    
    /**
     * 让一个UI控件永久获得焦点
     * @param view  
     */
    public void getFouces(View view) {
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusableInTouchMode(true);
    }
    
    /**
     * 让EditText中的英文字母自动大写   只是看起来大写了，但实际获取的字符需要toUppCase()转换一下大写
     * @param et
     */
    public void autoToUppCase(EditText et) {
        et.setTransformationMethod(new AllCapTransformationMethod());
    }
    
    private static class AllCapTransformationMethod extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z' };
            return aa;
        }
        
        @Override
        protected char[] getReplacement() {
            char[] cc = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                    'Y', 'Z' };
            return cc;
        }
    }
}
