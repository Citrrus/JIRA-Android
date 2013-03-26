package com.jondwillis.vapordex.ui.fragment;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.jondwillis.vapordex.BootstrapApplication;
import com.jondwillis.vapordex.BootstrapServiceProvider;
import com.jondwillis.vapordex.R;
import com.jondwillis.vapordex.authenticator.LogoutService;
import com.jondwillis.vapordex.core.News;
import com.jondwillis.vapordex.ui.activity.NewsActivity;
import com.jondwillis.vapordex.ui.view.NewsListAdapter;
import com.jondwillis.vapordex.ui.view.ThrowableLoader;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.jondwillis.vapordex.core.Constants.Extra.NEWS_ITEM;

public class NewsListFragment extends ItemListFragment<News> {

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

        setEmptyText(R.string.no_news);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.news_list_item_labels, null));
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
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        final List<News> initialItems = items;
        return new ThrowableLoader<List<News>>(getActivity(), items) {

            @Override
            public List<News> loadData() throws Exception {
                try {
                    if(getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getNews();
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

    @Override
    protected SingleTypeAdapter<News> createAdapter(List<News> items) {
        return new NewsListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        News news = ((News) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), NewsActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
