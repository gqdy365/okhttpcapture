package com.jerome.okhttpcapture;

import android.content.Context;

import com.jerome.okhttpcapture.internal.utils.CacheUtils;

public class InitCapture {

    private static InitCapture sInstance = null;
    private static Context mContext = null;
    private CacheUtils mCacheUtils = null;

    private InitCapture() {
        mCacheUtils = new CacheUtils(mContext);
    }

    public static InitCapture init(Context context) {
        mContext = context.getApplicationContext();
        return get();
    }

    public static InitCapture get() {
        if (sInstance == null) {
            synchronized (InitCapture.class) {
                if (sInstance == null) {
                    sInstance = new InitCapture();
                }
            }
        }
        return sInstance;
    }

    public CacheUtils getCacheUtils(){
        return mCacheUtils;
    }
}
