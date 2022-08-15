package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.config.auth.PrincipalDetail;
import com.web.model.WishList;
import com.web.repository.WishRepository;
import com.web.service.NaverBookService;

@Controller 
public class BookController {		
	@Autowired
	NaverBookService naverBookService;	
	
	@Autowired
    WishRepository wishRepository;
	
	@GetMapping("/book/bookList")
	public String bookSearch(){
		return "book/bookList";
	}	
	
	@GetMapping("/book/bookInfo")
	public String bookInfo(){
		return "book/bookInfo";
	}
	
	@GetMapping("/book/bookWish")
	public String index(Model m, @AuthenticationPrincipal PrincipalDetail principal, 
			@PageableDefault(size=5,sort="id",direction=Sort.Direction.DESC) Pageable pageable) {
		
		Page<WishList> list = wishRepository.findByUserId(principal.getUser().getId(), pageable);
		
		int pageNumber = list.getPageable().getPageNumber(); //현재 페이지
		int totalPages = list.getTotalPages(); //총 페이지 수, 검색에 따라 10개면 10개
		int pageBlock = 5; //블럭의 수 1,2,3,4,5
		int startBlockPage = ((pageNumber)/pageBlock) * pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10
		endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;
		
		m.addAttribute("startBlockPage", startBlockPage);
		m.addAttribute("endBlockPage", endBlockPage);
		m.addAttribute("list",list);
		
		return "/book/bookWish"; 
	}
	
	@DeleteMapping("/book/wishDel/{id}")
	public String wishDel(@PathVariable  int id, @AuthenticationPrincipal PrincipalDetail principal) {
		if(principal != null) {
			naverBookService.wishDel(id);
		}		
		return "/book/bookWish";
	}
}