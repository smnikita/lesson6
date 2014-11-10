package ru.ifmo.md.lesson6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class MyActivity extends Activity {
    public static RssDatabaseHelper dbHelper;
    EditText editText;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final Intent intent = new Intent(this, ChannelActivity.class);

        dbHelper = new RssDatabaseHelper(this);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long x) {
                RssChannel channel = (RssChannel) adapter.getItemAtPosition(position);

                intent.putExtra("channel_title", channel.getTitle());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long x) {
                RssChannel channel = (RssChannel) adapter.getItemAtPosition(position);

                dbHelper.deleteChannel(channel.getTitle());
                new DisplayChannelsTask(dbHelper, listView).execute();
                return false;
            }
        });


        new DisplayChannelsTask(dbHelper, listView).execute();
    }

    public void OnClickAddChannel(View view) {
        String url = editText.getText().toString();
        new RssDownloadTask(dbHelper, listView).execute(url);
    }
}
