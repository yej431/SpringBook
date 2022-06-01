package com.web.service;

import java.util.List;

import com.web.config.paging.Criteria;
import com.web.model.BookReply;

public interface BookReplyService {
	
	List<BookReply> list(String isbn) throws Exception;
    void create(BookReply re) throws Exception;
    void delete(Integer id) throws Exception;    
	List<BookReply> getRepliesPaging(String isbn, Criteria criteria) throws Exception;
	int countReplies(String isbn) throws Exception;
	void update(int id, String content) throws Exception;
}
