package com.jondwillis.vapordex.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.jondwillis.vapordex.R;
import com.jondwillis.vapordex.core.News;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static com.jondwillis.vapordex.core.Constants.Extra.NEWS_ITEM;

public class NewsActivity extends BootstrapActivity {

    @InjectExtra(NEWS_ITEM)
    protected News newsItem;

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;

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
