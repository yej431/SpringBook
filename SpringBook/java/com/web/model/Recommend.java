package com.web.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity 
public class Recommend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name="userId")
	private User user;

	@ColumnDefault("0")
	private int count;
	
	private String author;
	private String description;
	private String image;
	private String isbn;
	private String link;
	private String price;
	private String pubdate;
	private String publisher;
	private String title;
}
