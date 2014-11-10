package ru.ifmo.md.lesson6;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayItemsTask extends AsyncTask<String, Void, List<RssItem>> {
    RssDatabaseHelper dbHelper;
    ListView listView;

    protected DisplayItemsTask(RssDatabaseHelper dbHelper, ListView listView) {
        this.dbHelper = dbHelper;
        this.listView = listView;
    }

    @Override
    protected List<RssItem> doInBackground(String... params) {
        ArrayList<RssItem> items = new ArrayList<RssItem>();
        Cursor c = dbHelper.getAllItems(params[0]);
        c.moveToFirst();
        while (!c.isBeforeFirst() && !c.isAfterLast()) {
            items.add(new RssItem(
                c.getString(c.getColumnIndex("title")),
                c.getString(c.getColumnIndex("url")),
                c.getString(c.getColumnIndex("description")),
                c.getString(c.getColumnIndex("date")),
                c.getString(c.getColumnIndex("channel_title"))
            ));
            c.moveToNext();
        }
        c.close();
        return items;
    }

    @Override
    protected void onPostExecute(List<RssItem> items) {
        ItemsListAdapter adapter = new ItemsListAdapter(items);
        listView.setAdapter(adapter);
    }

}
