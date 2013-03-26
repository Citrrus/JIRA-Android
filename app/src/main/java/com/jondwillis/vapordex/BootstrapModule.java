package com.jondwillis.vapordex;

import android.accounts.AccountManager;
import android.content.Context;
import com.jondwillis.vapordex.authenticator.BootstrapAccountAuthenticator;
import com.jondwillis.vapordex.authenticator.LogoutService;
import com.jondwillis.vapordex.event.TimerService;
import com.jondwillis.vapordex.ui.activity.BootstrapAuthenticatorActivity;
import com.jondwillis.vapordex.ui.activity.BootstrapTimerActivity;
import com.jondwillis.vapordex.ui.activity.CarouselActivity;
import com.jondwillis.vapordex.ui.activity.NewsActivity;
import com.jondwillis.vapordex.ui.activity.UserActivity;
import com.jondwillis.vapordex.ui.fragment.CheckInsListFragment;
import com.jondwillis.vapordex.ui.fragment.NewsListFragment;
import com.jondwillis.vapordex.ui.fragment.UserListFragment;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        entryPoints = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                BootstrapAccountAuthenticator.class,
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
