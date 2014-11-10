package ru.ifmo.md.lesson6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemsListAdapter extends BaseAdapter {
    List<RssItem> items;

    public ItemsListAdapter(List<RssItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_item, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.title)).setText(items.get(i).getTitle());
        ((TextView) view.findViewById(R.id.description)).setText(items.get(i).getDescription());
        ((TextView) view.findViewById(R.id.date)).setText(items.get(i).getDate());
        return view;
    }
}
