package com.donnfelker.android.bootstrap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.view.MenuItem;
import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.core.News;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.google.inject.Inject;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.NEWS_ITEM;

public class NewsActivity extends BootstrapActivity {

    @InjectExtra(NEWS_ITEM) protected News newsItem;

    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.tv_content) protected TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(newsItem.getTitle());

        title.setText(newsItem.getTitle());
        content.setText(newsItem.getContent());

    }

}
