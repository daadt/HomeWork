package com.example.web;

import android.annotation.TargetApi;
import android.content.Context;

import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

public class NetWeb extends WebView {



    public NetWeb(Context context) {
        super(getFixedContext(context));
    }

    public NetWeb(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public NetWeb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetWeb(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(getFixedContext(context), attrs, defStyleAttr, defStyleRes);
    }

    public NetWeb(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(getFixedContext(context), attrs, defStyleAttr, privateBrowsing);
    }

    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23)
            return context.createConfigurationContext(new Configuration());
        return context;
    }


}
