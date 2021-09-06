package com.Scrapper.Newspaper.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Scrapper.Newspaper.Bean.NewsDetails;
import com.Scrapper.Newspaper.Bean.NewsFeedDetails;
import com.Scrapper.Newspaper.Repository.NewsDetailsRepository;
import com.Scrapper.Newspaper.Repository.NewsFeedDetailsRepository;

@Component
public class DuplicateWordCount {
	
	@Autowired
	NewsDetailsRepository newsDetailsRepository;
	
	@Autowired
	NewsFeedDetailsRepository newsFeedDetailsRepository;
	

    public int getCount(String startDate, String endDate, int paperId, String keyword) {
	
    	
	String feed = newsFeedDetailsRepository.findNewsFeedByPaperId(startDate, endDate, paperId);
    
	System.out.println("FEED : " + feed+ "Id : " + paperId);
	String news = "";
	int count;
    
	if(feed!=null) {
		news = feed.toLowerCase();   
	}else { 
		feed ="";
	}
    String containWord= keyword;
        
    count = countFreq(containWord.toLowerCase(), news);
    
                  
       System.out.println("No of time the given word occurred : " +count);    
 
    return count;
}
    
    static int countFreq(String pat, String txt) {       
        int M = pat.length();       
        int N = txt.length();       
        int res = 0;
 
        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) {
            /* For current index i, check for
        pattern match */
            int j;           
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
 
            // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
            if (j == M) {               
                res++;               
                j = 0;               
            }           
        }       
        return res;       
    }
	
}
