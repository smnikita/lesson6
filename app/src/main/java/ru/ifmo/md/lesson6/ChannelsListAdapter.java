package ru.ifmo.md.lesson6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChannelsListAdapter extends BaseAdapter {
    List<RssChannel> channels;

    public ChannelsListAdapter(List<RssChannel> channels) {
        this.channels = channels;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Object getItem(int i) {
        return channels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_channel, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.channel_title)).setText(channels.get(i).getTitle());
        return view;
    }
}
