package com.pchyyiyi.textureplay.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * 通过反射设置字体样式
 */
public class FontsUtil {
    /**
     * 设置自定义字体
     *
     * @param context
     * @param staticTypefaceFieldName 需要替换的系统字体样式
     * @param fontAssetName           替换后的字体样式
     */
    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        // 根据路径得到Typeface
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        // 设置全局字体样式
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            //替换系统字体样式
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}