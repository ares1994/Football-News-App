package com.example.android.newsapp;


public class News {
    private String section;
    private String title;
    private String webUrl;
    private String date;
    private String author;

    News(String section, String title, String date, String webUrl, String author) {
        this.section = section;
        this.title = title;
        this.webUrl = webUrl;
        this.date = date;
        this.author = author;

    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getDate() {
        String[] parts = date.split("T");
        return parts[0];
    }

    public String getAuthor() {
        String[] parts;
        if (author.matches("(.*)at(.*)")) {
            parts = author.split("at");
            return parts[0];
        } else {
            return author;
        }
    }

//    public boolean noAuthorName(){
//        return TextUtils.isEmpty(author);
//    }
}
