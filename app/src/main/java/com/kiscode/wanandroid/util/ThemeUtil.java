package com.kiscode.wanandroid.util;

import android.content.Context;
import android.util.TypedValue;

import com.kiscode.wanandroid.R;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2021/4/27 15:16
 **/
public class ThemeUtil {
    /**
     * 获取主题颜色
     * @return
     */
    public int getColorPrimary(Context context){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
} 