package com.citrrus.jira.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.github.kevinsawicki.wishlist.ViewUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 1/31/13
 * Time: 10:28 AM
 */
public class BootstrapFragment extends SherlockDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
    }

    public BootstrapFragment fadeIn(final View view, final boolean animate) {
        if (view != null) {
            if (animate) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in);
                anim.setInterpolator(AnimationUtils.loadInterpolator(getActivity(),
                        android.R.anim.linear_interpolator));
                view.startAnimation(anim);
            } else {
                view.clearAnimation();
            }
        }
        return this;
    }

    public BootstrapFragment fadeOut(final View view, final boolean animate) {
        if (view != null) {
            if (animate) {
                Animation anim = AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out);
                anim.setInterpolator(AnimationUtils.loadInterpolator(getActivity(),
                        android.R.anim.linear_interpolator));
                view.startAnimation(anim);
            } else {
                view.clearAnimation();
            }
        }
        return this;
    }

    public BootstrapFragment show(final View view) {
        ViewUtils.setGone(view, false);
        return this;
    }

    public BootstrapFragment hide(final View view) {
        ViewUtils.setGone(view, true);
        return this;
    }
}
