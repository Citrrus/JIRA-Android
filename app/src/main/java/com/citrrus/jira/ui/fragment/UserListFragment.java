package com.citrrus.jira.ui.fragment;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.citrrus.jira.BootstrapServiceProvider;
import com.citrrus.jira.authenticator.LogoutService;
import com.citrrus.jira.core.Constants;
import com.citrrus.jira.core.User;
import com.citrrus.jira.ui.view.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.citrrus.jira.BootstrapApplication;
import com.citrrus.jira.R;
import com.citrrus.jira.core.AvatarLoader;
import com.citrrus.jira.ui.activity.UserActivity;
import com.citrrus.jira.ui.view.UserListAdapter;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class UserListFragment extends ItemListFragment<User> {

    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject AvatarLoader avatars;
    @Inject
    LogoutService logoutService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.getInstance().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_users);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.user_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    List<User> latest = null;

                    if(getActivity() != null) {
                        latest = serviceProvider.getService(getActivity()).getUsers();
                    }

                    if (latest != null) {
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                    return initialItems;
                }
            }
        };
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = ((User) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), UserActivity.class).putExtra(Constants.Extra.USER, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_users;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new UserListAdapter(getActivity().getLayoutInflater(), items, avatars);
    }
}
