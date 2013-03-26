package com.citrrus.jira.core;
/*
 * Copyright 2012 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Intent;
import com.citrrus.jira.ui.activity.NewsActivity;

/**
 * Utilities for an {@link android.app.Activity}
 */
public class ActivityUtils {

    /**
     * Finish the given activity and start a home activity class.
     * <p/>
     * This mirror the behavior of the home action bar button that clears the
     * current activity and starts or brings another activity to the top.
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void goHome(Activity activity, Class<?> homeActivityClass) {
        activity.finish();
        Intent intent = new Intent(activity, homeActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    public static void newClearTask(Activity activity, Class<?> homeActivityClass) {
        activity.finish();
        Intent intent = new Intent(activity, homeActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent
                .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void goHomeAfterLogin(Activity loginActivity) {
        loginActivity.finish();
        Intent intent = new Intent(loginActivity, NewsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent
                .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fromLogin", true);
        loginActivity.startActivity(intent);
    }
}
