<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    
<link rel="stylesheet" href="/css/chat.css">	
	
<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div>
<table id="roomList" class="roomList">
	<tr>
		<th style='width:15%;text-align:center'>생성일</th>
		<th style='width:72%;text-align:center'>방 이름</th>
		<th></th>	
		<th></th>
	</tr>	
	<c:forEach var="list"  items="${roomList}">
	<c:choose>
	<c:when test="${list.created eq principal.user.userId and list.invitedCheck==0}">
		<tr>
			<td style="text-align:center">
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.createdDate}"/>
			</td>
			<td style="text-align:center">${list.invited}님과의 대화방</td>
			<td>
				<button type='button' onclick="goChat('${list.roomId}','${list.invited}')" class='join'>참여하기</button>			
			</td>
			<td>
				<button type='button' onclick="createdRoomDel('${list.roomId}','${list.created}')" class='join'>삭제</button>			
			</td>
		</tr>
	</c:when>
	<c:when test="${list.created != principal.user.userId and list.invitedCheck==1}">
		<tr>
			<td style="text-align:center">
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.createdDate}"/>
			</td>
			<td style="text-align:center">${list.created}님과의 대화방</td>
			<td>
				<button type='button' onclick="goChat('${list.roomId}','${list.created}')" class='join'>참여하기</button>			
			</td>
			<td>
				<button type='button' onclick="invitedRoomDel('${list.roomId}','${list.invited}')" class='join'>삭제</button>			
			</td>
		</tr>
		</c:when>
	</c:choose>
	</c:forEach>
</table>
</div>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script type="text/javascript">
	 function goChat(roomId,invited){
		location.href="/goChat?roomId="+roomId+"&invited="+invited;
	}
	 
	function invitedRoomDel(roomId, invited){		
		$.ajax({
			type:"post",
			url: "/chat/invitedRoomDel?roomId="+roomId+"&invited="+invited
		}).done(function(result){
			console.log(result);
			alert("삭제하였습니다.");
			location.href="/chat/room";						
		})
	}
	
	function createdRoomDel(roomId, created){		
		$.ajax({
			type: "post",
			url: "/chat/createdRoomDel?roomId="+roomId+"&created="+created
		}).done(function(result){
			console.log(result);
			alert("삭제하였습니다.");
			location.href="/chat/room";						
		})
	}
</script>