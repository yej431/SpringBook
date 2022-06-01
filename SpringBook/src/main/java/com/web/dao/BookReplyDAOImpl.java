package com.web.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.config.paging.Criteria;
import com.web.model.BookReply;
import com.web.repository.BookReplyRepository;

@Repository
public class BookReplyDAOImpl implements BookReplyDAO{
	
	@Autowired
	BookReplyRepository repository;
	
	@Override
	public List<BookReply> list(String isbn) throws Exception {
		return repository.reList(isbn);
	}

	@Transactional
	@Override
	public void create(BookReply re) throws Exception {
		repository.reCreate(re.getContent(),re.getIsbn(),re.getUser().getId());
	}
	
	@Transactional
	@Override
    public void delete(Integer id) throws Exception {
		repository.reDelete(id);
    }
    
	@Override
	public List<BookReply> listPaging(String isbn, Criteria criteria) throws Exception {
        return repository.listPaging(isbn, criteria.getPageStart(), criteria.getPerPageNum());
	}

	@Override
	public int countReplies(String isbn) throws Exception {
		return repository.countReplies(isbn);
	}

	@Transactional
	@Override
	public int update(int id, String content) throws Exception {
		int re=repository.reUpdate(content,id);
		System.out.println(re);
		return re;
	}
}
