package com.citrrus.jira.ui.activity;

import android.R;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import butterknife.Views;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.citrrus.jira.core.ActivityUtils;
import com.github.kevinsawicki.wishlist.ViewUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.citrrus.jira.BootstrapApplication;

/**
 * Base activity for a Bootstrap activity which does not use fragments.
 */
public abstract class BootstrapActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BootstrapApplication.getInstance().inject(this);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        Views.inject(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:  // This is the home button in the top left corner of the screen.
                ActivityUtils.goHome(this, NewsActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected BootstrapActivity fadeIn(final View view, final boolean animate) {
        if (view != null) {
            if (animate) {
                Animation anim = AnimationUtils.loadAnimation(this,
                        R.anim.fade_in);
                anim.setInterpolator(AnimationUtils.loadInterpolator(this, R.anim.linear_interpolator));
                view.startAnimation(anim);
            } else {
                view.clearAnimation();
            }
        }
        return this;
    }

    protected BootstrapActivity fadeOut(final View view, final boolean animate) {
        if (view != null) {
            if (animate) {
                Animation anim = AnimationUtils.loadAnimation(this,
                        R.anim.fade_out);
                anim.setInterpolator(AnimationUtils.loadInterpolator(this, R.anim.linear_interpolator));
                view.startAnimation(anim);
            } else {
                view.clearAnimation();
            }
        }
        return this;
    }

    protected BootstrapActivity show(final View view) {
        ViewUtils.setGone(view, false);
        return this;
    }

    protected BootstrapActivity hide(final View view) {
        ViewUtils.setGone(view, true);
        return this;
    }
}
