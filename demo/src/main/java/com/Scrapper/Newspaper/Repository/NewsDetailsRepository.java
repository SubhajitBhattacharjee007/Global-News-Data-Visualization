package com.Scrapper.Newspaper.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Scrapper.Newspaper.Bean.NewsDetails;

public interface NewsDetailsRepository extends JpaRepository<NewsDetails, Integer>{
	
	@Query("SELECT count(e.id) FROM  NewsDetails e")
	int findTotalNewsPaperCount();
	
	@Query("SELECT e.organization FROM  NewsDetails e where e.id=:id")
	String getNewsOrgName(@Param("id") int id);
}
