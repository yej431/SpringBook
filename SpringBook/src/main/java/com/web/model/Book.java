package com.web.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
	private String id;
	private String title;
	private String link;
	private String image;
	private String author;
	private String price;
	private String publisher;
	private String pubdate;
	private String isbn;
	private String description;
	private String userId;
	private int count;
	
	public Book(JSONObject bookJson) {	
		this.title=bookJson.getString("title");
		this.link=bookJson.getString("link");
		this.image=bookJson.getString("image");
		this.author=bookJson.getString("author");
		this.price=bookJson.getString("price");
		this.publisher=bookJson.getString("publisher");
		this.pubdate=bookJson.getString("pubdate");
		this.isbn=bookJson.getString("isbn");
		this.description=bookJson.getString("description");
	}
	
	public Book(JSONObject bookJson,String id) {		
		this.id=id;
		this.title=bookJson.getString("title");
		this.link=bookJson.getString("link");
		this.image=bookJson.getString("image");
		this.author=bookJson.getString("author");
		this.price=bookJson.getString("price");
		this.publisher=bookJson.getString("publisher");
		this.pubdate=bookJson.getString("pubdate");
		this.isbn=bookJson.getString("isbn");
		this.description=bookJson.getString("description");
	}
}
