package com.Scrapper.Newspaper.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Scrapper.Newspaper.Bean.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT e FROM  User e where e.username=:username AND e.password=:password")
	User findUserIdentity(@Param("username") String username, @Param("password") String password);
	
	@Query("SELECT count(e.username) FROM  User e")
	int getUserCount();

	
}
