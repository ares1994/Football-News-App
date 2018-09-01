package com.example.android.newsapp;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class NewsTaskLoader extends AsyncTaskLoader {
    private String address;

    NewsTaskLoader(Context context, String address) {
        super(context);
        this.address = address;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        String responseFromServer = "";

        URL url = QueryUtils.makeURL(address);

        try {
            responseFromServer = QueryUtils.createHttpConnection(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return QueryUtils.retrieveNews(responseFromServer);
    }
}
