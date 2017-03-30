package com.ytjojo.commonlib.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * 剪贴板操作类，从剪贴板中获取/设置文本，Intent，Uri.
 * <p>
 * 因为剪贴板需要的SDK最小是11，所以设置了 minSdkVersion 为 11.
 * </p>
 * @author Ayvytr <a href="https://github.com/Ayvytr" target="_blank">'s GitHub</a>
 * @since 1.0.0
 */

public class ClipboardTool
{
    private static final String TEXT = "text";
    private static final String URI = "uri";
    private static final String Intent = "intent";

    private ClipboardTool()
    {
        throw new IllegalArgumentException();
    }

    /**
     * 设置文本到剪贴板
     *
     * @param text 文本
     */
    public static void setText(Context context,CharSequence text)
    {

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(TEXT, text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static String getText(Context context)
    {
        return getText(context,null);
    }

    /**
     * 获取剪贴板文本，当获取到是空时，返回默认字符串
     *
     * @param textIfNull 剪贴板文本如果为空，返回这个字符串
     * @return 剪贴板文本
     */
    public static String getText(Context context,String textIfNull)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if(clipData != null && clipData.getItemCount() > 0)
        {
            return clipData.getItemAt(0).coerceToText(context).toString();
        }

        return textIfNull;
    }

    /**
     * 设置uri到剪贴板
     *
     * @param uri uri
     */
    public static void setUri(Context context,Uri uri)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        clipboardManager.setPrimaryClip(
                ClipData.newUri(context.getContentResolver(), URI, uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri(Context context)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if(clipData != null && clipData.getItemCount() > 0)
        {
            return clipData.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 设置意图到剪贴板
     *
     * @param intent 意图
     */
    public static void setIntent(Context context,Intent intent)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        clipboardManager.setPrimaryClip(ClipData.newIntent(Intent, intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent(Context context)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if(clipData != null && clipData.getItemCount() > 0)
        {
            return clipData.getItemAt(0).getIntent();
        }
        return null;
    }
}