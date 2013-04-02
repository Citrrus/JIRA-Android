package com.citrrus.jira.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.widget.SpinnerAdapter;
import butterknife.InjectView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.citrrus.jira.BootstrapServiceProvider;
import com.citrrus.jira.R;
import com.citrrus.jira.model.Project;
import com.citrrus.jira.ui.view.BootstrapPagerAdapter;
import com.citrrus.jira.ui.view.ThrowableLoader;
import com.viewpagerindicator.TitlePageIndicator;
import org.holoeverywhere.ArrayAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends BootstrapActivity implements LoaderManager.LoaderCallbacks<List<Project>> {

    @InjectView(R.id.tpi_header) TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages) ViewPager pager;

    @Inject BootstrapServiceProvider serviceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(1);

        getSupportLoaderManager().initLoader(0, new Bundle(), this);
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

    @Override
    public Loader<List<Project>> onCreateLoader(int id, Bundle args) {
        return new ThrowableLoader<List<Project>>(this, new ArrayList<Project>()) {
            @Override
            public List<Project> loadData() throws Exception {
                return serviceProvider.getService(CarouselActivity.this).getProjects();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Project>> loader, List<Project> data) {
        List<String> projectNames = new ArrayList<String>(data.size());
        for(Project project : data) {
            projectNames.add(project.name);
        }
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this, org.holoeverywhere.R.layout.simple_dropdown_item_1line, projectNames);
        getSupportActionBar().setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override public boolean onNavigationItemSelected(int i, long l) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<Project>> loader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
