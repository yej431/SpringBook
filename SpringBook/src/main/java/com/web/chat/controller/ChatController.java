package com.web.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.chat.model.Chat;
import com.web.chat.model.ChatMessage;
import com.web.chat.model.ChatRepository;
import com.web.chat.model.ChatRoom;
import com.web.chat.model.RoomRepository;
import com.web.chat.service.ChatService;
import com.web.config.auth.PrincipalDetail;
import com.web.repository.UserRepository;

@Controller
@Transactional
public class ChatController {
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ChatService chatService;
	
	List<ChatRoom> roomList = new ArrayList<ChatRoom>();
	static int roomNumber = 0;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chat) {
    	System.out.println(chat.getRoomId());
    	try {
    		String sender = chat.getSender();
    		String sub = sender.substring(0,1);
    		String upper = sub.toUpperCase();
    		chatRepository.insertChat(upper,chat.getContent(),chat.getReceiver(),chat.getSender(),chat.getRoomId());			
		} catch (Exception e) {
			e.printStackTrace();
		}    
        return chat;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(@Payload Chat chat,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }
    
    @RequestMapping("/chat/room")
	public ModelAndView room(@AuthenticationPrincipal PrincipalDetail principal) {
		ModelAndView mv = new ModelAndView();		
		List<ChatRoom> roomList=roomRepository.roomInfo(principal.getUser().getUserId());
		
		mv.addObject("roomList", roomList);
		mv.setViewName("chat/room");
		return mv;
	}
	
	//채팅방
	@RequestMapping("/moveChating")
	public ModelAndView moveChating(@RequestParam HashMap<Object, Object> params,
			@AuthenticationPrincipal PrincipalDetail principal) {
		ModelAndView mv = new ModelAndView();
		
		String roomId = UUID.randomUUID().toString();
		String created = principal.getUser().getUserId();		
		int id2 = Integer.parseInt(String.valueOf(params.get("id")));
		String invited = userRepository.invited(id2);
			
		roomRepository.insertRoom1(created,invited,roomId);
		roomRepository.insertRoom2(created,invited,roomId);
		
		List<ChatMessage> chatList = chatRepository.allChat(roomId);
		
		if(params.isEmpty()) {
			mv.setViewName("chat/room");			
		}else {
			mv.addObject("chatList", chatList);
			mv.addObject("roomId", roomId);
			mv.addObject("invited", invited);
			mv.setViewName("chat/index");
		}
		return mv;
	}
	
	//채팅방2
	@RequestMapping("/goChat")
	public ModelAndView goChat(@RequestParam HashMap<Object, Object> params,
			@AuthenticationPrincipal PrincipalDetail principal) {
		ModelAndView mv = new ModelAndView();
			
		String roomId = String.valueOf(params.get("roomId"));		
		String invited = String.valueOf(params.get("invited"));		
			
		List<ChatMessage> chatList = chatRepository.allChat(roomId);
			
		if(params.isEmpty()) {
			mv.setViewName("chat/room");			
		}else {
			mv.addObject("chatList", chatList);							
			mv.addObject("roomId", roomId);								
			mv.addObject("invited", invited);								
			mv.setViewName("chat/index");
		}
		return mv;
	}
	
	@RequestMapping("/chat/invitedRoomDel")
	public int invitedRoomDel(@RequestParam HashMap<Object, Object> params,
			HttpSession session, @AuthenticationPrincipal PrincipalDetail principal) {
		String roomId = String.valueOf(params.get("roomId"));		
		String invited = String.valueOf(params.get("invited"));		
		
		return  chatService.invitedRoomDel(roomId, invited, session, principal);
	}
	
	@RequestMapping("/chat/createdRoomDel")
	public int createdRoomDel(@RequestParam HashMap<Object, Object> params,
			HttpSession session, @AuthenticationPrincipal PrincipalDetail principal) {
		String roomId = String.valueOf(params.get("roomId"));		
		String created = String.valueOf(params.get("created"));		
		
		return chatService.createdRoomDel(roomId, created, session, principal);
	}
}