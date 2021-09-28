package com.Scrapper.Newspaper.Repository;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Scrapper.Newspaper.Bean.NewsFeedDetails;
import com.Scrapper.Newspaper.Util.NewsFeed;

public interface NewsFeedDetailsRepository extends JpaRepository<NewsFeedDetails, Integer>{

	@Query("SELECT MAX(e.date),e.data FROM  NewsFeedDetails As e WHERE e.date >=:startDate AND e.newsPaperId=:uid GROUP BY e.uniqeId")
	NewsFeed findNewsFeedByPaperId(@Param("startDate") Date startDate, @Param("uid") int uid );
	
	@Query("SELECT e.data FROM  NewsFeedDetails e WHERE e.date >=:startDate AND e.newsPaperId=:uid")
	String findNewsFeedByPaperIdTest(@Param("startDate") Date startDate, @Param("uid") int uid );
}
