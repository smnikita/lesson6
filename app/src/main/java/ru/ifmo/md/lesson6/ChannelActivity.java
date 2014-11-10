package ru.ifmo.md.lesson6;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ChannelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        ListView listView = (ListView) findViewById(R.id.listView2);
        String channel_title = getIntent().getStringExtra("channel_title");
        setTitle(channel_title);
        new DisplayItemsTask(MyActivity.dbHelper, listView).execute(channel_title);
    }
}
