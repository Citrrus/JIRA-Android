package com.donnfelker.android.bootstrap.ui;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.donnfelker.android.bootstrap.BootstrapApplication;

import butterknife.Views;

/**
 * Base class for all Bootstrap Activities that need fragments.
 */
public class BootstrapFragmentActivity extends SherlockFragmentActivity {

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        // Perform view injection via butter knife
        // Doesnt seem like you can inject via a super class. Throws method not found.
        //Views.inject(this);
        BootstrapApplication.getInstance().inject(this);
    }
}
