package com.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.chat.model.ChatRepository;
import com.web.config.auth.PrincipalDetail;
import com.web.service.IndexService;

@Controller
public class HomeController {	
	@Autowired
	IndexService indexService;
	@Autowired
	ChatRepository chatRepository;	

	@GetMapping("/")
	public String bookList(Model m, HttpSession session,
			@AuthenticationPrincipal PrincipalDetail principal) throws Exception {
		int newChat=0;
		if(principal != null) {
			newChat=chatRepository.newChat(principal.getUser().getUserId());
			session.setAttribute("newChat", newChat);	
		}
		
		m.addAttribute("rReply", indexService.rReply());
//		m.addAttribute("bookRanking", indexService.bookRanking());
		return "user/index"; 		
	}
}
