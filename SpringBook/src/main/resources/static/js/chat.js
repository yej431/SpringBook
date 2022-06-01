'use strict';

var chatPage = document.querySelector('#chat-page');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var roomId = $('#roomId').val();
var userId = $('#userId').val();
var invited = $('#invited').val();

var stompClient = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

$(document).ready(function(){
	
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, onConnected, onError);

	function onConnected() {
	    stompClient.subscribe('/topic/public', onMessageReceived);
	    stompClient.send("/app/chat.addUser",
	        {},
	        JSON.stringify({sender: userId, type: 'JOIN'})
	    )
	    connectingElement.classList.add('hidden');
	}

	function onError(error) {
	    connectingElement.textContent = '웹소켓 접속에 오류가 있습니다.';
	    connectingElement.style.color = 'red';
	}
});

function sendMessage(event) {
	var messageContent = messageInput.value.trim();
	 if(messageContent && stompClient) {
		 var chatMessage = {			
			content: messageInput.value,
			receiver: invited,
			sender: userId,			
			roomId: roomId,			
			type: 'CHAT'
		};
		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	 event.preventDefault();
}

function onMessageReceived(payload) {
	var message = JSON.parse(payload.body);
	var messageElement = document.createElement('li');	    

	if(message.type === 'JOIN') {
		messageElement.classList.add('event-message');
		message.content = message.sender + '님이 입장하셨습니다.';
	} else if (message.type === 'LEAVE') {
		messageElement.classList.add('event-message');
		message.content = message.sender + '님이 퇴장하셨습니다.';
	} else {
		messageElement.classList.add('chat-message');
	        
		var avatarElement = document.createElement('i');
		var avatarText = document.createTextNode(message.sender[0]);
		avatarElement.appendChild(avatarText);
		if(userId === message.sender){
			avatarElement.style['background-color'] = "#2196F3";
		}else{
			avatarElement.style['background-color'] = "#FF9800";		
		}
		
		messageElement.appendChild(avatarElement);
	
		var usernameElement = document.createElement('span');	        
		var usernameText = document.createTextNode(message.sender);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(message.content);

	textElement.appendChild(messageText);		
	messageElement.appendChild(textElement);		
	messageArea.appendChild(messageElement);
			
	messageArea.scrollTop = messageArea.scrollHeight;		
}
	
function getAvatarColor(messageSender) {
	 var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}	
	var index = Math.abs(hash % colors.length);
	return colors[index];
}
	
messageForm.addEventListener('submit', sendMessage, true)