package com.donnfelker.android.bootstrap.ui;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import butterknife.Views;

/**
 * Base class for all Bootstrap Activities that need fragments.
 */
public class BootstrapFragmentActivity extends SherlockFragmentActivity {

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        // Perform view injection via butter knife
        Views.inject(this);
    }
}
