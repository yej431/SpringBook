package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.config.paging.Criteria;
import com.web.dao.BookReplyDAO;
import com.web.model.BookReply;

@Service
public class BookReplyServiceImpl implements BookReplyService {
	
	@Autowired
	BookReplyDAO replyDAO;

	@Override
	public List<BookReply> list(String isbn) throws Exception {
		return replyDAO.list(isbn);
	}

	@Override
	public void create(BookReply re) throws Exception {
		replyDAO.create(re);
	}

	@Override
	public void delete(Integer id) throws Exception {
		replyDAO.delete(id);
	}

	@Override
	public List<BookReply> getRepliesPaging(String isbn, Criteria criteria) throws Exception {
		return replyDAO.listPaging(isbn, criteria);
	}

	@Override
	public int countReplies(String isbn) throws Exception {
		return replyDAO.countReplies(isbn);
	}

	@Override
	public void update(int id, String content) throws Exception {
		replyDAO.update(id, content);
	}
}
