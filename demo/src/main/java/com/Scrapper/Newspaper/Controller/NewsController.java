
package com.Scrapper.Newspaper.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Scrapper.Newspaper.Bean.NewsDetails;
import com.Scrapper.Newspaper.Repository.NewsDetailsRepository;
import com.Scrapper.Newspaper.Service.DuplicateWordCount;
import com.Scrapper.Newspaper.Service.ScrapNewsFeeds;

@Controller
public class NewsController {

	@Autowired
	NewsDetailsRepository NewsDetailsRepository;
	@Autowired
	ScrapNewsFeeds scrapNewsFeeds;
	@Autowired
	DuplicateWordCount DuplicateWordCount;
	
	@RequestMapping("/")
	public String login(Model model) {
		
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		
		return "AddNewsLink.html";
	}
	@RequestMapping("/Test")
	public String Test() {
		
		scrapNewsFeeds.Scrapping();
		//return "GlobalDataVisualization.html";
		//return "Test.html";
		return "AddNewsLink.html";
	}
	@RequestMapping("/GlobalDataVisualization")
	public String GlobalDataVisualization() {
		
		//return "GlobalDataVisualization.html";
		return "Main.html";
	}
	
	@RequestMapping("/Main")
	public String Main() {
		
		//return "GlobalDataVisualization.html";
		return "Main.html";
	}
	
	@RequestMapping(value="/AddUrl", method = RequestMethod.GET)
	public String GetNewsFeedUrl(@RequestParam String addNewsUrl, @RequestParam String orgName, Model model) {
		
		System.out.println("URL : " + addNewsUrl);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	      
		NewsDetails news = new NewsDetails();
		news.setUrl(addNewsUrl);
		news.setDate(formatter.format(date));
		news.setOrganization(orgName);
		
		String succStatus = "Successfully added in the List!";
		String errStatus = "Unable to add, please check the URL.";
		
		if(addNewsUrl!=null && !addNewsUrl.isEmpty() && orgName!=null && !orgName.isEmpty()) {
		NewsDetailsRepository.save(news);
		model.addAttribute("Status", succStatus);
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "AddNewsLink.html";
		}
		
		model.addAttribute("Status", errStatus);
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "AddNewsLink.html";
	}
	
	@RequestMapping(value="/GetHeadLineKEYWORDCount", method = RequestMethod.GET)
	public String GetNewsFeedUrl(@RequestParam int id, @RequestParam String keyword, @RequestParam("date") String date, Model model) throws Exception {

		int tmp = date.length();
		char tmp1; 
		String tmpYear  = "";
		String tmpDay   = "";
		String tmpMonth = "";
		
		for(int i = 0; i<tmp; i++) {
			//Year
			if(i<4) { 
			tmp1 = date.charAt(i);
			tmpYear = tmpYear+tmp1;
			}
			//MONTH
			if(i>4 && i<7) {
			tmp1 = date.charAt(i);
			tmpMonth = tmpMonth+tmp1;	
			}
			//DAY
			if(i>7) {
			tmp1 = date.charAt(i);	
			tmpDay = tmpDay+tmp1;
			}		
		}
		
		String startDate = tmpDay+"/"+tmpMonth+"/"+tmpYear;
		String endDate = "0"+(Integer.valueOf(tmpDay)+1)+"/"+tmpMonth+"/"+tmpYear;
		
		System.out.println("STARTDATE : "  + startDate  +" ENDDATE : "+ endDate);

	    int c= DuplicateWordCount.getCount(startDate, endDate, id, keyword);
		
	    String count = "The Number of Times the given keyword occurred is : "+c;
	    
	    List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
	    model.addAttribute("newsDetails", newsDetails);
	    model.addAttribute("Count", count);
		return "AddNewsLink.html";
	}
	
	
}
