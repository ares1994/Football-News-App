package com.example.android.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private NewsAdapter newsAdapter;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    static String BASE_GUARDIAN_API = "https://content.guardianapis.com/search";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list);
        emptyTextView = findViewById(R.id.no_news);
        progressBar = findViewById(R.id.prgress_bar);
        newsAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = newsAdapter.getItem(position);
                assert currentNews != null;
                openWebPage(currentNews.getWebUrl());
            }
        });

        listView.setEmptyView(emptyTextView);
        if (isConnected()) {
            getSupportLoaderManager().initLoader(0, null, MainActivity.this);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            emptyTextView.setText(R.string.no_internet);
        }


    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(BASE_GUARDIAN_API);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("api-key", "83c5a839-f2b3-43b2-94ba-9d47b2e4bb41");
        builder.appendQueryParameter("section", "football");
        builder.appendQueryParameter("order-by", "newest");
        builder.appendQueryParameter("page-size", "20");
        builder.appendQueryParameter("show-fields", "byline");

        return new NewsTaskLoader(MainActivity.this, builder.toString());


    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> newsInfo) {
        newsAdapter.clear();

        if (newsInfo != null || !newsInfo.isEmpty()) {
            newsAdapter.addAll(newsInfo);
        }
        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setText(R.string.no_news);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        newsAdapter.addAll(new ArrayList<News>());
    }


    public void openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
