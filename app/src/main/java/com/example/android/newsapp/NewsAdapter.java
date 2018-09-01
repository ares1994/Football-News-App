package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_items, parent, false);
        }

        News currentNews = getItem(position);
        TextView sectionTextView = convertView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getSection());

        TextView titleTextView = convertView.findViewById(R.id.title);
        titleTextView.setText(currentNews.getTitle());

        TextView dateTextView = convertView.findViewById(R.id.date);
        dateTextView.setText(currentNews.getDate());

        TextView authorTextView = convertView.findViewById(R.id.author);
        authorTextView.setText(currentNews.getAuthor());

        LinearLayout mainLinearLayout = convertView.findViewById(R.id.main_layout);
        int color;
        if (position % 4 == 0) {
            color = ContextCompat.getColor(getContext(), R.color.first);
            mainLinearLayout.setBackgroundColor(color);
        } else if (position % 4 == 1) {
            color = ContextCompat.getColor(getContext(), R.color.second);
            mainLinearLayout.setBackgroundColor(color);
        } else if (position % 4 == 2) {
            color = ContextCompat.getColor(getContext(), R.color.third);
            mainLinearLayout.setBackgroundColor(color);
        } else {
            color = ContextCompat.getColor(getContext(), R.color.fourth);
            mainLinearLayout.setBackgroundColor(color);
        }

        return convertView;


    }
}
