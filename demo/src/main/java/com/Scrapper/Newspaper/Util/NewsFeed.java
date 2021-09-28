package com.Scrapper.Newspaper.Util;

import java.util.Date;

public class NewsFeed {
	
	Date date;
	String news;
	
	public NewsFeed(Date date, String news) {
		this.date = date;
		this.news = news;
	}
	public NewsFeed() {
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
}
