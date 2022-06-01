<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    
<link rel="stylesheet" href="/css/chat.css" />

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<input type="hidden" id="userId"  value="${principal.user.userId}"/>
<input type="hidden" id="roomId"  value="${roomId}"/>
<input type="hidden" id="invited"  value="${invited}"/>

    <div id="chat-page" >
        <div class="chat-container">
            <div class="chat-header">
                <h4>채팅</h4>
            </div>
            <div class="connecting">
                Connecting...
            </div>           
           
	        <ul id="messageArea">
	        <c:forEach var="list" items="${chatList}">
			<c:choose>
			<c:when test="${list.sender eq principal.user.userId}">
				<li class="chat-message">
					<i style="background-color:#2196F3">${list.fspell}</i>
					<span>${list.sender}</span>
					<p>${list.message}</p>
				</li>
			</c:when>		
			<c:when test="${list.fspell eq 'event'}">
				<li class='event-message'><p>${list.message}</p></li>
			</c:when>
			<c:otherwise>
				<li class="chat-message">
					<i style="background-color:#FF9800">${list.fspell}</i>
					<span>${list.sender}</span>
					<p>${list.message}</p>
				</li>
			</c:otherwise>
			</c:choose>
			</c:forEach>	    
	        </ul>
	        
            <form id="messageForm" name="messageForm" nameForm="messageForm">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <input type="text" id="message" placeholder="메시지 입력..." autocomplete="off" class="form-control"/>
                        <button type="submit" class="primary">확인</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    
</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="/js/chat.js"></script>