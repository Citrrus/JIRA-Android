package com.donnfelker.android.bootstrap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false
)
public class AndroidModule {

    @Provides
    SharedPreferences provideDefaultSharedPreferences(final Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    PackageInfo providePackageInfo(Context context)
    {
        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Provides
    TelephonyManager provideTelephonyManager(Context context)
    {
        return getSystemService(context, Context.TELEPHONY_SERVICE);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSystemService(Context context, String serviceConstant)
    {
        return (T) context.getSystemService(serviceConstant);
    }

    @Provides
    InputMethodManager provideInputMethodManager(final Context context)
    {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


}
