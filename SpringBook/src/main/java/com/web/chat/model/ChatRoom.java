package com.web.chat.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	private String roomId;
    private String created;
    private String invited;
    
    @ColumnDefault("0")
    private int invitedCheck;
    
    @CreationTimestamp 
    @Column(updatable = false)
    private Timestamp createdDate;
}
