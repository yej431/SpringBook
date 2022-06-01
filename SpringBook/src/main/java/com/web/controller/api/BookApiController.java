package com.web.controller.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.config.auth.PrincipalDetail;
import com.web.dao.ResponseDto;
import com.web.model.BookReply;
import com.web.model.Recommend;
import com.web.model.WishList;
import com.web.repository.BookReplyRepository;
import com.web.repository.RecommendRepository;
import com.web.service.NaverBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //꼭 필요한 요소(final) 자동 생성
@RestController //Json으로 응답하기 위한 Controller
public class BookApiController {

	private final RecommendRepository recommendRepository;	
	private final NaverBookService naverBookService;	
	private final BookReplyRepository replyListRepository;
	
	//Naver 검색 API를 사용하여 검색된 목록 조회
	@RequestMapping("/book/bookSearch/{keyword}")
	public ModelAndView bookSearch(@PathVariable String keyword){
		ModelAndView mav = new ModelAndView();
		String resultString = naverBookService.search(keyword);
		if(keyword !=null)
        {			
			mav.addObject("bookList",naverBookService.fromJSONtoItems(resultString));
			mav.addObject("keyword",keyword);
        }
		mav.setViewName("book/bookList");
		return mav;
	}
	
	@RequestMapping("/book/info/{isbn}")
	public ModelAndView bookInfo(@PathVariable String isbn) {
		ModelAndView mav = new ModelAndView();
		String resultString = naverBookService.bookInfoSearch(isbn);

		List<BookReply> replyList = replyListRepository.findByIsbn(isbn);
	
		if(isbn !=null)
        {			
			mav.addObject("bookInfo",naverBookService.bookInfoToItem(resultString, isbn));
			mav.addObject("isbn",isbn);
			mav.addObject("replyList",replyList);
        }
		
		mav.setViewName("book/bookInfo");
		return mav;
	}
	
	@PostMapping("/book/wish")
	public ResponseDto<Integer> wishBook(@RequestBody WishList wish, @AuthenticationPrincipal PrincipalDetail principal) {	
		naverBookService.위시리스트저장(wish, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/book/up")
	public HashMap<String, Integer> bookUp(@RequestBody Recommend rec, @AuthenticationPrincipal PrincipalDetail principal) {	
		HashMap<String, Integer> result = new HashMap <String, Integer>();
		naverBookService.bookUp(rec, principal.getUser());			
		String isbn2=rec.getIsbn().replaceAll("<b>", "");
		String isbn3=isbn2.replaceAll("</b>", "");
		
		int upCount = recommendRepository.upCount(isbn3);	
		
		result.put("count", upCount);
		return result;
	}
	
	@DeleteMapping("/api/wish/{id}/{userId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int id, @PathVariable int userId,
			@AuthenticationPrincipal PrincipalDetail principal){
		if(principal.getUser().getId() == userId) {
			naverBookService.wishDel(id);
		}		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
