package com.ytjojo.commonlib.design;

import java.lang.reflect.Field;

import com.ytjojo.commonlib.app.BaseApplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

/**
 * dp与px互转辅助类
 *
 * @author hyr
 * @version [版本号, 2013-8-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DensityUtil {
    
    private static int width, height;
    
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    /**
     * sp转px
     * 
     * @param context
     * @param val
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
    
    
    /**
     * px转sp
     * 
     * @param fontScale
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }   
    
    public static DisplayMetrics getDeviceDisplay(Activity c) {
        DisplayMetrics metric = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric; // 屏幕宽度（像素）
        //        int height = metric.heightPixels; // 屏幕高度（像素）
        //        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        //        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }
    
    //Context 获取屏幕尺寸
    public static DisplayMetrics getDeviceDisplay(Context c) {
        Resources r;
        
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        return r.getDisplayMetrics();
    }
    
    public static int getDeviceWidth(Activity c) {
        if (width > 0) {
            return width;
        }
        DisplayMetrics metrics = getDeviceDisplay(c);
        return width = metrics.widthPixels;
        //        int height = metric.heightPixels; // 屏幕高度（像素）
        //        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        //        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }
    
    public static int getDeviceHeight(Activity c) {
        if (height > 0) {
            return height;
        }
        DisplayMetrics metrics = getDeviceDisplay(c);
        return height = metrics.heightPixels;
        //        int height = metric.heightPixels; // 屏幕高度（像素）
        //        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        //        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }
    
    public static int getWidth() {
        if (width > 0) {
            return width;
        }
        return BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getHeight() {
        
        if (height > 0) {
            return height;
        }
        return BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }
    
    //获取状态栏高度 反射
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    //获得状态栏高度
    public static int getStatusBarHeight(View  decorView){
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        final int height = rect.top;
        return height;
    }
    
    public static int getTitleHeight(View decorView){
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        final int height = rect.height() - decorView.getHeight();
        return height;
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static int getActionbarHeight() {
        TypedValue tv = new TypedValue();
        if (BaseApplication.getInstance().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, BaseApplication.getInstance().getResources().getDisplayMetrics());
        }

        //	    TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] {
        //	            android.R.attr.actionBarSize
        //	    });
        //
        //	    float h = actionbarSizeTypedArray.getDimension(0, 0);
        return 0;
    }

    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen height
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return display.getHeight();
    }

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }
}
