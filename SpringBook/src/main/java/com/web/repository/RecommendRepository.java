package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.model.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

	@Query(value = "select ifnull(sum(count), 0) from recommend where isbn=?1", nativeQuery = true)
	int upCount(String isbn);

	@Query(value = "select count from recommend where isbn=?1 and userId=?2", nativeQuery = true)
	String upCheck(String isbn, int userId);
	
	@Modifying
	@Query(value = "insert into recommend(count,isbn,userId,author,description,image,link,price,"
			+ "pubdate,publisher,title) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)", nativeQuery = true)
	void insertUp(int count,String isbn,int userId,String author,String description,String image,String link,
			String price,String pubdate,String publisher,String title);

	@Modifying
	@Query(value = "delete from recommend where isbn=?1 and userId=?2", nativeQuery = true)
	void delUp(String isbn, int userId);

	
	 @Query(value="select count(isbn) as ranking,id,count,isbn,userId,author,description,image,link,price,pubdate,publisher,title"
	 		+ " from recommend group by id,count,isbn,userId,author,description,image,link,price,pubdate,publisher,title"
	 		+ " order by ranking desc limit 5;",nativeQuery=true)
	 List<Recommend> bookRanking();
}
