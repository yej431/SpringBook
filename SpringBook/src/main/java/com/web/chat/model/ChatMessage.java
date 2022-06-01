package com.web.chat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    
	    private String message;	   	 	    
	    private String roomId;	    	    
	    private String fspell;
	    
	    @ColumnDefault("0")
	    private int newChat;
	    
	    private String sender;
	    private String receiver;
	    
		public ChatMessage(String message,String roomId,String fspell,String sender,String receiver) {
			super();
			this.message = message;
			this.roomId = roomId;
			this.fspell = fspell;
			this.sender=sender;
			this.receiver=receiver;
		}
}