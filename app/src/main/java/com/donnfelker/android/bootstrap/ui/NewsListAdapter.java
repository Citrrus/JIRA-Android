package com.donnfelker.android.bootstrap.ui;

import android.view.LayoutInflater;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.News;
import com.donnfelker.android.bootstrap.ui.AlternatingColorListAdapter;

import java.util.List;

public class NewsListAdapter extends AlternatingColorListAdapter<News> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public NewsListAdapter(LayoutInflater inflater, List<News> items,
                               boolean selectable) {
        super(R.layout.news_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public NewsListAdapter(LayoutInflater inflater, List<News> items) {
        super(R.layout.news_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_summary,
                R.id.tv_date };
    }

    @Override
    protected void update(int position, News item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getContent());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
