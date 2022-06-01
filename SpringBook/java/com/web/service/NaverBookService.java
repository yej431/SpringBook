package com.web.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.web.model.Book;
import com.web.model.Recommend;
import com.web.model.User;
import com.web.model.WishList;
import com.web.repository.BookReplyRepository;
import com.web.repository.RecommendRepository;
import com.web.repository.WishRepository;

@Service
public class NaverBookService {	
	@Autowired
    WishRepository wishRepository;    
    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    BookReplyRepository replyListRepository;
    
	private static String clientID = "DExmSylU57B0u2Aov7qQ";
    private static String clientSecret = "rzVycRVAaz";
    
    //API를 통해 검색된 목록 조회
	public String search(String keyword) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Naver-Client-Id", clientID); 
		headers.add("X-Naver-Client-Secret", clientSecret);
		String body="";
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		
		ResponseEntity<String> responseEntity
		=rest.exchange("https://openapi.naver.com/v1/search/book.json?query="+keyword
				+"&start=1&display=30&sort=count",
				HttpMethod.GET, requestEntity, String.class);
		
		String response =responseEntity.getBody();
		
		return response;
	}
	
	//검색된 목록 데이터를 DTO로 변환
	public List<Book> fromJSONtoItems(String result){
		JSONObject rjson = new JSONObject(result);	
		JSONArray items = rjson.getJSONArray("items");
		List<Book> list = new ArrayList<>();		
		for(int i=0; i<items.length(); i++) {
			JSONObject json = items.getJSONObject(i);				
			Book book = new Book(json);				
			list.add(book);
		}
		return list;
	}
	
	@Transactional
	public void 위시리스트저장(WishList wish, User user) {
		wish.setUser(user);
		wishRepository.save(wish);
	}
	
	public String bookInfoSearch(String isbn) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Naver-Client-Id", clientID); 
		headers.add("X-Naver-Client-Secret", clientSecret);
		String body="";
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> responseEntity
			=rest.exchange("https://openapi.naver.com/v1/search/book.json?query="+isbn
					+"&start=1&display=1",
					HttpMethod.GET, requestEntity, String.class);

		String response =responseEntity.getBody();
		
		return response;
	}

	public List<Book> bookInfoToItem(String result, String isbn){
		JSONObject rjson = new JSONObject(result);
		JSONArray items = rjson.getJSONArray("items");
		List<Book> list = new ArrayList<>();
		for(int i=0; i<items.length(); i++) {
			JSONObject json = items.getJSONObject(i);
			Book book = new Book(json);
			
			int up = recommendRepository.upCount(isbn);	
			book.setCount(up);
			list.add(book);
		}
		return list;
	}

	@Transactional
	public void wishDel(long id) {
		wishRepository.deleteById(id);
	}

	@Transactional
	public void bookUp(Recommend rec, User user) {
		String isbn2=rec.getIsbn().replaceAll("<b>", "");
		String isbn3=isbn2.replaceAll("</b>", "");
		String upCheck =recommendRepository.upCheck(isbn3, user.getId());
		
		if(upCheck == null) {
			int count =1;
			recommendRepository.insertUp(count,isbn3,user.getId(),rec.getAuthor(),rec.getDescription(),
					rec.getImage(),rec.getLink(),rec.getPrice(),rec.getPubdate(),rec.getPublisher(),rec.getTitle());
		}else if(upCheck != null) {
			recommendRepository.delUp(isbn3, user.getId());
		}
	}
}
