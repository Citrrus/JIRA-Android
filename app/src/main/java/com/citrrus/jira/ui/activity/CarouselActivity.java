package com.citrrus.jira.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import butterknife.InjectView;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.citrrus.jira.R;
import com.citrrus.jira.ui.view.BootstrapPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends BootstrapActivity {

    @InjectView(R.id.tpi_header) TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages) ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timer:
                final Intent i = new Intent(this, BootstrapTimerActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
