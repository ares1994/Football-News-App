package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private QueryUtils() {
    }


    public static URL makeURL(String address) {
        URL url = null;

        if (TextUtils.isEmpty(address)) {
            return null;
        }

        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String createHttpConnection(URL url) throws IOException {
        String responseFromServer = "";

        if (url == null) {
            return responseFromServer;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                responseFromServer = readInputStream(inputStream);
            } else {
                Log.e("Create Http Connection", "Response Code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("Create Http Connection", "Unable to establish Http Connection", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return responseFromServer;

    }

    private static String readInputStream(InputStream in) throws IOException {
        StringBuilder output = new StringBuilder();
        if (in != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<News> retrieveNews(String responseFromServer) {
        ArrayList<News> newsInfo = new ArrayList<>();


        try {
            JSONObject totalData = new JSONObject(responseFromServer);
            JSONObject response = totalData.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject object = results.getJSONObject(i);
                String section = object.getString("sectionName");
                String title = object.getString("webTitle");
                String date = object.getString("webPublicationDate");
                String webUrl = object.getString("webUrl");
                JSONObject fields = object.getJSONObject("fields");
                String author = fields.getString("byline");

                newsInfo.add(new News(section, title, date, webUrl, author));
            }
        } catch (JSONException e) {
            Log.e("retrieve news", "Error parsing JSON data", e);
        }

        return newsInfo;
    }
}
