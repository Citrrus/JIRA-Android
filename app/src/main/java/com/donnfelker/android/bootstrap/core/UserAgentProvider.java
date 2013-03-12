package com.donnfelker.android.bootstrap.core;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.donnfelker.android.bootstrap.BootstrapApplication;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.Strings;

import javax.inject.Inject;
import javax.inject.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;


public class UserAgentProvider implements Provider<String> {
    @Inject protected ApplicationInfo appInfo;
    @Inject protected PackageInfo info;
    @Inject protected TelephonyManager telephonyManager;
    @Inject protected ClassLoader classLoader;

    protected String userAgent;

    private static final String APP_NAME = "Android Bootstrap";

    @Override
    public String get() {
        if( userAgent==null ) {
            synchronized (UserAgentProvider.class) {
                if( userAgent==null ) {
                    userAgent = String.format("%s/%s (Android %s; %s %s / %s %s; %s)",
                            APP_NAME,
                            info.versionName,
                            Build.VERSION.RELEASE,
                            Strings.capitalize(Build.MANUFACTURER),
                            Strings.capitalize(Build.DEVICE),
                            Strings.capitalize(Build.BRAND),
                            Strings.capitalize(Build.MODEL),
                            Strings.capitalize( telephonyManager == null ? "not-found" : telephonyManager.getSimOperatorName())
                    );

                    final ArrayList<String> params = new ArrayList<String>();
                    params.add( "preload=" + ((appInfo.flags& ApplicationInfo.FLAG_SYSTEM)==1) ); // Determine if this app was a preloaded app
                    params.add( "locale=" + Locale.getDefault() );


                    // http://stackoverflow.com/questions/2641111/where-is-android-os-systemproperties
                    try{
                        final Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                        final Method get = SystemProperties.getMethod("get", String.class);
                        params.add( "clientidbase=" + get.invoke(SystemProperties, "ro.com.google.clientidbase"));
                    }catch( Exception ignored ){
                        Ln.d(ignored);
                    }


                    if( params.size()>0 )
                        userAgent += "["+ Strings.join(";", params) +"]";

                }
            }
        }

        return userAgent;
    }
}
