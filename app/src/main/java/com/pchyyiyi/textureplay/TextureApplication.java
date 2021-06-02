package com.pchyyiyi.textureplay;

import android.app.Application;
import android.content.Context;

import com.pchyyiyi.textureplay.util.FontsUtil;

/**
 * @ClassName TextureApplication
 * @Description TODO
 * @Author fayXxxx
 * @Date 2021/6/2 上午9:43
 * @Version 1.0
 */
public class TextureApplication extends Application {
    public final static String TAG = "ywsapp";
    private static TextureApplication instance = null;

    public static Context getAppContext() {
        return instance;
    }

    /**
     * 获取该应用唯一的实例
     * @return
     */
    public static TextureApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {// serif //MONOSPACE
            FontsUtil.setDefaultFont(this, "SERIF", "fonts/fineblack.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        instance = this;
    }
}
