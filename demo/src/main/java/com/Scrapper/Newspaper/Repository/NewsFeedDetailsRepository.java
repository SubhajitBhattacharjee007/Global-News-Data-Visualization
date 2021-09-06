package com.Scrapper.Newspaper.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Scrapper.Newspaper.Bean.NewsFeedDetails;

public interface NewsFeedDetailsRepository extends JpaRepository<NewsFeedDetails, Integer>{
	

	@Query("SELECT e.data FROM  NewsFeedDetails e WHERE e.date >=:startDate AND e.date <=:endDate AND e.newsPaperId=:uid")
	String findNewsFeedByPaperId(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("uid") int uid );

}
