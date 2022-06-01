package com.web.chat.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
	Optional<ChatMessage> findById(Long id);    
	
	@Modifying
	@Query(value="insert into chatmessage(fspell,message,newChat,receiver,sender,roomId)"
			+ " values(?1,?2,1,?3,?4,?5)", nativeQuery=true)
	void insertChat(String fspell,String message,String receiver,String sender,String roomId);
	
	@Query(value="select * from chatmessage where roomId=?1", nativeQuery=true)
	List<ChatMessage> allChat(String roomId); 
	
	@Query(value="select ifnull(sum(newChat), 0) from chatmessage where receiver=?1", nativeQuery=true)
	int  newChat(String receiver); 
	
	@Modifying
	@Query(value="insert into chatmessage(fspell,message,roomId) values(?1,?2,?3)", nativeQuery=true)
	void exitMsg(String fspell,String str,String roomId);
	
	@Modifying
	@Query(value="update chatmessage set newChat=0 where roomId=?1 and receiver=?2", nativeQuery=true)
	void setNewChat(String roomId,String receiver);
}