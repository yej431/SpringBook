package com.web.chat.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.chat.model.ChatRepository;
import com.web.chat.model.RoomRepository;
import com.web.config.auth.PrincipalDetail;

@Service 
public class ChatService {
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	ChatRepository chatRepository;
	
	@Transactional
	public int invitedRoomDel(String roomId, String invited, HttpSession session, PrincipalDetail principal) {
		String str = invited+"님께서 나가셨습니다.";
		String fspell="event";
		try {
			roomRepository.invitedRoomDel(invited, roomId);				
			chatRepository.exitMsg(fspell,str,roomId);
			chatRepository.setNewChat(roomId, invited);			
			int newChat=0;
			newChat=chatRepository.newChat(principal.getUser().getUserId());
			session.setAttribute("newChat", newChat);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}

	@Transactional
	public int createdRoomDel(String roomId, String created, HttpSession session, PrincipalDetail principal) {
		String str = created+"님께서 나가셨습니다.";
		String fspell="event";
		try {
			roomRepository.createdRoomDel(created, roomId);				
			chatRepository.exitMsg(fspell,str,roomId);
			chatRepository.setNewChat(roomId, created);			
			int newChat=0;
			newChat=chatRepository.newChat(principal.getUser().getUserId());
			session.setAttribute("newChat", newChat);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}	
}