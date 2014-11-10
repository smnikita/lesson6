package ru.ifmo.md.lesson6;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayChannelsTask extends AsyncTask<Void, Void, List<RssChannel>> {
    RssDatabaseHelper dbHelper;
    ListView listView;

    protected DisplayChannelsTask(RssDatabaseHelper dbHelper, ListView listView) {
        this.dbHelper = dbHelper;
        this.listView = listView;
    }

    @Override
    protected List<RssChannel> doInBackground(Void... voids) {
        ArrayList<RssChannel> channels = new ArrayList<RssChannel>();
        Cursor c = dbHelper.getAllChannels();
        c.moveToFirst();
        while (!c.isBeforeFirst() && !c.isAfterLast()) {
            channels.add(new RssChannel(c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("url"))));
            c.moveToNext();
        }
        c.close();
        return channels;
    }

    @Override
    protected void onPostExecute(List<RssChannel> channels) {
        ChannelsListAdapter adapter = new ChannelsListAdapter(channels);
        listView.setAdapter(adapter);
    }

}
