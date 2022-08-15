package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.model.Board;
import com.web.repository.BoardReplyRepository;
import com.web.repository.BoardRepository;
import com.web.service.BoardService;

@Controller
public class BoardController {	
	@Autowired
	BoardService boardService;
	@Autowired
	BoardRepository repository;
	@Autowired
	BoardReplyRepository reply;
	
	@GetMapping("/board/boardList")
	public String index(Model m, @PageableDefault(size=15,sort="id",direction=Sort.Direction.DESC)Pageable pageable) {
		Page<Board> list = repository.findList(pageable);
		int pageNumber = list.getPageable().getPageNumber(); //현재 페이지
		int totalPages = list.getTotalPages(); //총 페이지 수, 검색에 따라 10개면 10개
		int total = (int) list.getTotalElements();
		int pageBlock = 5; //블럭의 수 1,2,3,4,5
		int startBlockPage = ((pageNumber)/pageBlock) * pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10
		endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;
		
		m.addAttribute("startBlockPage", startBlockPage);
		m.addAttribute("endBlockPage", endBlockPage);
		m.addAttribute("total", total);
		m.addAttribute("list",list);
		return "board/boardList"; 
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model m) {
		m.addAttribute("board", boardService.글상세보기(id));
		boardService.countUp(id);
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model m) {
		m.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
}
