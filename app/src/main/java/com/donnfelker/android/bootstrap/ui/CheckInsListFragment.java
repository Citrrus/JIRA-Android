package com.donnfelker.android.bootstrap.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.BootstrapApplication;
import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.CheckIn;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import javax.inject.Inject;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class CheckInsListFragment extends ItemListFragment<CheckIn> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.getInstance().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.checkins_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<CheckIn>> onCreateLoader(int id, Bundle args) {
        final List<CheckIn> initialItems = items;
        return new ThrowableLoader<List<CheckIn>>(getActivity(), items) {

            @Override
            public List<CheckIn> loadData() throws Exception {
                try {
                    if(getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getCheckIns();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<CheckIn> createAdapter(List<CheckIn> items) {
        return new CheckInsListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        CheckIn checkIn = ((CheckIn) l.getItemAtPosition(position));

        String uri = String.format("geo:%s,%s?q=%s",
                checkIn.getLocation().getLatitude(),
                checkIn.getLocation().getLongitude(),
                checkIn.getName());

        // Show a chooser that allows the user to decide how to display this data, in this case, map data.
        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), getString(R.string.choose)));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_checkins;
    }
}
