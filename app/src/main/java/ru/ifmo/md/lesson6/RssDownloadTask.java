package ru.ifmo.md.lesson6;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RssDownloadTask extends AsyncTask<String, Void, Void> {
    RssDatabaseHelper dbHelper;
    ListView listView;


    protected RssDownloadTask(RssDatabaseHelper dbHelper, ListView listView) {
        this.dbHelper = dbHelper;
        this.listView = listView;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            String channel_url = params[0];
            String channel_title = "";
            XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
            InputStream stream = new URL(channel_url).openStream();
            xpp.setInput(stream, null);

            boolean f = true;
            while (f && xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("channel")) {
                    while (f && !(xpp.getEventType() == XmlPullParser.END_TAG && xpp.getName().equals("channel"))) {
                        if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("title")) {
                            xpp.next();
                            channel_title = xpp.getText();
                            f = false;
                        }
                        if (f) xpp.next();
                    }
                }
                if (f) xpp.next();
            }

            dbHelper.insertChannel(new RssChannel(channel_title, channel_url));

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                    String title = "";
                    String description = "";
                    String date = "";
                    String url = "";
                    xpp.next();
                    while (!(xpp.getEventType() == XmlPullParser.END_TAG && xpp.getName().equals("item"))) {
                        if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("title")) {
                            xpp.next();
                            title = xpp.getText();
                        }
                        if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("description")) {
                            xpp.next();
                            description = xpp.getText();
                        }
                        if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("pubDate")) {
                            xpp.next();
                            date = xpp.getText();
                        }
                        if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("link")) {
                            xpp.next();
                            url = xpp.getText();
                        }
                        xpp.next();
                    }
                    dbHelper.insertItem(new RssItem(title, url, description, date, channel_title));
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        new DisplayChannelsTask(dbHelper, listView).execute();
    }
}

