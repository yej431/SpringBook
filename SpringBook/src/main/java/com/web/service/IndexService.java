package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.model.BookReply;
import com.web.model.Recommend;
import com.web.repository.BookReplyRepository;
import com.web.repository.RecommendRepository;

@Service
public class IndexService {	
	@Autowired
	BookReplyRepository bookReplyRepository;
	@Autowired
	RecommendRepository recommendRepository;

	public List<BookReply> rReply() throws Exception {
		return bookReplyRepository.rReply();
	}

	
	 public List<Recommend> bookRanking() throws Exception { return
	 recommendRepository.bookRanking(); }
	 
}
