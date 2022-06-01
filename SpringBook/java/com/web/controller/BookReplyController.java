package com.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.config.auth.PrincipalDetail;
import com.web.config.paging.Criteria;
import com.web.config.paging.PageMaker;
import com.web.model.BookReply;
import com.web.service.BookReplyService;

@RestController
@RequestMapping("/reply")
public class BookReplyController {

	@Autowired
	BookReplyService replyService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody BookReply re
			,@AuthenticationPrincipal PrincipalDetail principal){
		ResponseEntity<String> entity = null;
		re.setUser(principal.getUser());
		
		try {
			replyService.create(re);
			entity = new ResponseEntity<>("regSuccess", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/all/{isbn}", method=RequestMethod.GET)
	public ResponseEntity<List<BookReply>> list(@PathVariable("isbn") String isbn){
		ResponseEntity<List<BookReply>> entity = null;
		try {
			entity=new ResponseEntity<>(replyService.list(isbn), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/del/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") int id){
		ResponseEntity<String> entity = null;
		try {
			replyService.delete(id);
			entity = new ResponseEntity<>("delSuccess", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	public ResponseEntity<String> update(@RequestParam HashMap<Object, Object> params){
		ResponseEntity<String> entity = null;
		int id = Integer.parseInt((String) params.get("id"));		
		String content = String.valueOf(params.get("content"));	
		try {
			replyService.update(id, content);
			entity = new ResponseEntity<>("delSuccess", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/{isbn}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPaging(@PathVariable("isbn") String isbn,
			@PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		String isbn2="<b>"+isbn+"</b>";
		try {
			Criteria criteria = new Criteria();
			criteria.setPage(page);
			
			List<BookReply> replies = replyService.getRepliesPaging(isbn2, criteria);
			int repliesCount = replyService.countReplies(isbn2);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCriteria(criteria);
			pageMaker.setTotalCount(repliesCount);
			
			Map<String, Object> map = new HashMap<>();
			map.put("replies", replies);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<>(map, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.OK);
		}
		return entity;
	}
}
