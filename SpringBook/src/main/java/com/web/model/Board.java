package com.web.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob //대용량 데이터
	private String content;
	
	private int count; 
	
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy="board", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE) 
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<BoardReply> replys; 
	
	private int num;
	
	private int notice;
	
	@CreationTimestamp 
	private Timestamp createDate;
}
