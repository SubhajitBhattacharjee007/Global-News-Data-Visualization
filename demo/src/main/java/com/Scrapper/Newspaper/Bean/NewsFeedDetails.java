package com.Scrapper.Newspaper.Bean;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class NewsFeedDetails {
	
	@Id
	@GeneratedValue
	int uniqeId;
	@Lob
	String data;
	Date date;
	int newsPaperId;
	
	public NewsFeedDetails() {
	}

	public NewsFeedDetails(int uniqeId, String data, Date date, int newsPaperId) {
		this.uniqeId = uniqeId;
		this.data = data;
		this.date = date;
		this.newsPaperId = newsPaperId;
	}
	
	

	public int getNewsPaperId() {
		return newsPaperId;
	}

	public void setNewsPaperId(int newsPaperId) {
		this.newsPaperId = newsPaperId;
	}

	public int getUniqeId() {
		return uniqeId;
	}

	public void setUniqeId(int uniqeId) {
		this.uniqeId = uniqeId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	

}
