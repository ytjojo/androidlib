package com.ytjojo.commonlib.observer;

import com.ytjojo.commonlib.dto.SmsInfo;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;

/**
 * @author Administrator
 *
 */
public class SmsObserver extends ContentObserver {
    
    private ContentResolver mResolver;
    
    public SmsHandler smsHandler;
    
    public SmsObserver(ContentResolver mResolver, SmsHandler handler) {
        super(handler);
        this.mResolver = mResolver;
        this.smsHandler = handler;
    }
    
    @Override
    public void onChange(boolean selfChange) {
        Cursor mCursor = mResolver.query(Uri.parse("content://sms/inbox"),
                new String[] { "_id", "address", "read", "body", "thread_id" },
                "read=?",
                new String[] { "0" },
                "date desc");
        
        if (mCursor == null) {
            return;
        }
        else {
            while (mCursor.moveToNext()) {
                SmsInfo _smsInfo = new SmsInfo();
                
                int _inIndex = mCursor.getColumnIndex("_id");
                if (_inIndex != -1) {
                    _smsInfo._id = mCursor.getString(_inIndex);
                }
                
                int thread_idIndex = mCursor.getColumnIndex("thread_id");
                if (thread_idIndex != -1) {
                    _smsInfo.thread_id = mCursor.getString(thread_idIndex);
                }
                
                int addressIndex = mCursor.getColumnIndex("address");
                if (addressIndex != -1) {
                    _smsInfo.smsAddress = mCursor.getString(addressIndex);
                }
                
                int bodyIndex = mCursor.getColumnIndex("body");
                if (bodyIndex != -1) {
                    _smsInfo.smsBody = mCursor.getString(bodyIndex);
                }
                
                int readIndex = mCursor.getColumnIndex("read");
                if (readIndex != -1) {
                    _smsInfo.read = mCursor.getString(readIndex);
                }
                
                //根据你的拦截策略，判断是否不对短信进行操作;将短信设置为已读;将短信删除
                //TODO
                Message msg = smsHandler.obtainMessage();
                _smsInfo.action = 2;//0不对短信进行操作;1将短信设置为已读;2将短信删除
                msg.obj = _smsInfo;
                smsHandler.sendMessage(msg);
            }
        }
        
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }
}