package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.model.BookReply;

public interface BookReplyRepository extends JpaRepository<BookReply,Integer> {

	@Modifying
	@Query(value="insert into bookreply(content, isbn, userId) values(?1,?2,?3)", nativeQuery=true)
	void reCreate(String content, String isbn,  int userId);
	
	@Modifying
	@Query(value="update bookreply set content=?1 where id=?2", nativeQuery=true)
	int reUpdate(String content, int id);
	
	@Modifying
	@Query(value="delete from bookreply where id=?1", nativeQuery=true)
	void reDelete(int id);

	@Query(value="select content,isbn,timestamp,userId from bookreply where isbn=?1 order by id desc", nativeQuery=true)
	List<BookReply> findByIsbn(String isbn);
	
	@Query(value="select id,content,isbn,timestamp,userId from bookreply"
			+ " where isbn = ?1 order by id desc", nativeQuery=true)
	List<BookReply> reList(String isbn);	
	
	@Query(value="select id,content,isbn,timestamp,userId from bookreply"
			+ " where isbn=?1 order by id desc limit ?2, ?3", nativeQuery=true)
	List<BookReply> listPaging(String isbn, int getPageStart, int perPageNum);
	
	@Query(value="select count(isbn) from bookreply where isbn=?1", nativeQuery=true)
	int countReplies(String isbn);

	@Query(value="select id,content,isbn,timestamp,userId from bookreply order by id desc limit 10"
			, nativeQuery=true)
	List<BookReply> rReply();
}
