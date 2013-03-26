package com.citrrus.jira.core;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 2/5/13
 * Time: 9:16 PM
 */
public class ViewUtils {
    public static int calculatePxFromDp(Context context, int sizeInDp) {
        final float px = (int) (sizeInDp * context.getResources().getDisplayMetrics().density + 0.5f);
        return Math.round(px);
    }
}
