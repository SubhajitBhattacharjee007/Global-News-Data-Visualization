
package com.Scrapper.Newspaper.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Scrapper.Newspaper.Bean.NewsDetails;
import com.Scrapper.Newspaper.Bean.User;
import com.Scrapper.Newspaper.Repository.NewsDetailsRepository;
import com.Scrapper.Newspaper.Repository.NewsFeedDetailsRepository;
import com.Scrapper.Newspaper.Repository.UserRepository;
import com.Scrapper.Newspaper.Service.DuplicateWordCount;
import com.Scrapper.Newspaper.Service.ScrapNewsFeeds;
import com.Scrapper.Newspaper.Util.NewsFeed;

@Controller
//@RequestMapping("/api")
public class NewsController {

	@Autowired
	NewsDetailsRepository NewsDetailsRepository;
	@Autowired
	NewsFeedDetailsRepository newsFeedDetailsRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ScrapNewsFeeds scrapNewsFeeds;
	@Autowired
	DuplicateWordCount DuplicateWordCount;
	
	User user = new User();
	@RequestMapping("/")
	public String welcome(Model model) {
		
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		//return "AddNewsLink.html";
		return "login.html";
	}
	@RequestMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		 user = userRepository.findUserIdentity(username, password);
		
		if(user!=null) {
			int newsPaperCount = NewsDetailsRepository.findTotalNewsPaperCount();
			int userCount=userRepository.getUserCount();
			model.addAttribute("newsPaperCount", newsPaperCount);
			model.addAttribute("userName", user.getUsername());
			model.addAttribute("userCount", userCount);
			return "index.html";
		}else {
			model.addAttribute("Status", "Authentication failed!");
			return "login.html";
		}
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return "logout.html";
	}
	@RequestMapping("/index")
	public String index(Model model) {
		
		int newsPaperCount = NewsDetailsRepository.findTotalNewsPaperCount();
		model.addAttribute("newsPaperCount", newsPaperCount);
		return "index.html";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register.html";
	}
	@RequestMapping("/userRegister")
	public String userRegister(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
		
		User user = new User(username, email, password);
		userRepository.save(user);
		model.addAttribute("Status", "User registered successfully!");
		return "login.html";
	}
	
	@RequestMapping("/todayHeadlines")
	public String todayHeadlines(Model model) {
		int newsPaperCount = NewsDetailsRepository.findTotalNewsPaperCount();
		int userCount=userRepository.getUserCount();
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		model.addAttribute("newsPaperCount", newsPaperCount);
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("userCount", userCount);
		return "todayHeadlines.html";
	}
	@RequestMapping("/addNewsLink")
	public String addNewsLink(Model model) {
		
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "addNewsLink.html";
	}
	@RequestMapping("/checkWordOccurance")
	public String checkWordOccurance(Model model) {
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "checkWordOccurance.html";
	}
	
	@RequestMapping("/Test")
	public String Test(Model model) {	
		/*
		 * int j; for(int i=0;i<10000000; i++) { for(j=0; j<1000000; j++) { } for(int
		 * k=j; k>1000000; k--) { } }
		 */		 
		scrapNewsFeeds.Scrapping();
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "addNewsLink.html";
	}
	@RequestMapping("/GlobalDataVisualization")
	public String GlobalDataVisualization() {
		
		//return "GlobalDataVisualization.html";
		return "Test2.html";
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
		return "addNewsLink.html";
		}
		
		model.addAttribute("Status", errStatus);
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		return "addNewsLink.html";
	}
	
	@RequestMapping(value="/GetHeadLineKEYWORDCount", method = RequestMethod.GET)
	public String GetNewsFeedUrl(@RequestParam int id, @RequestParam String keyword, @RequestParam("date") String date, Model model) throws Exception {
		String feed;
		DateTimeFormatter dfm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localdate = LocalDate.parse(date, dfm);
		java.sql.Date date1 = java.sql.Date.valueOf(localdate);
		System.out.println("DATE :  " + date1);
		NewsFeed newsFeed = newsFeedDetailsRepository.findNewsFeedByPaperId(date1, id);
		if(!newsFeed.getNews().isEmpty() && newsFeed.getNews()!=null ) {
			 feed=newsFeed.getNews();
		}else {
			 feed="";
		}
	    int c= DuplicateWordCount.getCount(feed, keyword);
		
	    String count = "The Number of Times the given keyword occurred is : "+c;
	    
	    List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
	    model.addAttribute("newsDetails", newsDetails);
	    model.addAttribute("Count", count);
		return "checkWordOccurance.html";
		
	}
	
	@RequestMapping(value="/getHeadlines", method = RequestMethod.GET)
	public String getHeadlines(@RequestParam int paperId, @RequestParam("date") String date, Model model) throws Exception {
	
		String feed;
		DateTimeFormatter dfm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localdate = LocalDate.parse(date, dfm);
		java.sql.Date date1 = java.sql.Date.valueOf(localdate);
		System.out.println("DATE :  " + date1);
		
		NewsFeed newsFeed = newsFeedDetailsRepository.findNewsFeedByPaperId(date1, paperId);
		String paperName = NewsDetailsRepository.getNewsOrgName(paperId); 
		if(!newsFeed.getNews().isEmpty() && newsFeed.getNews()!=null ) {
			 feed=newsFeed.getNews();
		}else {
			 feed="No content found";
		}
		System.out.println("Feed:"+feed);
		int newsPaperCount = NewsDetailsRepository.findTotalNewsPaperCount();
		int userCount=userRepository.getUserCount();
		List<NewsDetails> newsDetails = NewsDetailsRepository.findAll();
		model.addAttribute("newsDetails", newsDetails);
		model.addAttribute("newsPaperCount", newsPaperCount);
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("userCount", userCount);
		model.addAttribute("feed", feed);
		model.addAttribute("paperName", paperName);
		return "todayHeadlines.html";
	}
	
	
}
