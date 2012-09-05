

package com.donnfelker.android.bootstrap.ui;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.R.id;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

import roboguice.inject.InjectView;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends RoboSherlockFragmentActivity {

    @InjectView(id.tpi_header) private TitlePageIndicator indicator;
    @InjectView(id.vp_pages) private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(1);
    }


}
