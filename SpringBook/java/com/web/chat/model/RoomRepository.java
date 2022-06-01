package com.web.chat.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {
	@Modifying
	@Query(value="insert into chatroom(created,createdDate,invited,invitedCheck,roomId)"
			+ " values(?1,now(),?2,0,?3)", nativeQuery=true)
	void insertRoom1(String created,String invited,String roomId); 
	
	@Modifying
	@Query(value="insert into chatroom(created,createdDate,invited,invitedCheck,roomId)"
			+ " values(?1,now(),?2,1,?3)", nativeQuery=true)
	void insertRoom2(String created,String invited,String roomId); 
	
	@Query(value="select * from chatroom where created=?1 or invited=?1", nativeQuery=true)
	List<ChatRoom> roomInfo(String created); 
	
	@Modifying
	@Query(value="delete from chatroom where invited=?1 and roomId=?2 and invitedCheck=1", nativeQuery=true)
	void invitedRoomDel(String invited,String roomId); 
	
	@Modifying
	@Query(value="delete from chatroom where created=?1 and roomId=?2 and invitedCheck=0", nativeQuery=true)
	void createdRoomDel(String created,String roomId); 
}