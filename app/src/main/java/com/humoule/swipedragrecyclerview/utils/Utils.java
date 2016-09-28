package com.humoule.swipedragrecyclerview.utils;

import android.content.res.Resources;

public class Utils {


    /**
     * Convert dp value to px
     *
     * @param dp
     * @return
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}

