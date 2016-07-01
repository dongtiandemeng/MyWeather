package com.chen.chenbin.data;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by chenbin on 2016/6/11.
 */
public class MyApplication extends Application {
    private static String key="575e4737fa3ffc29c3d79eaec3bfba21";
    private static Context mContext;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        // TODO 您的其他初始化流程
        ApiStoreSDK.init(this, key);
        super.onCreate();
    }
    public static Context getContext(){
        return mContext;
    }

}
