<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    
<meta http-equiv="Cache-Control" content="no-cache">

<meta http-equiv="Pragma" content="no-cache">

<meta http-equiv="Expires" content="now">

<meta http-equiv="Expires" content=0>

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<c:forEach items="${bookInfo}" var="list">
<div style="width:100%;" class="top">
<div class="clearfix" style="margin-top:40px;">	
	<input type="hidden" id="title" value="${list.title}"/>
	<input type="hidden" id="user" value="${principal.user.id}"/>
	<input type="hidden" id="link" value="${list.link}"/>
	<input type="hidden" id="image" value="${list.image}"/>
	<input type="hidden" id="author" value="${list.author}"/>
	<input type="hidden" id="price" value="${list.price}"/>
	<input type="hidden" id="publisher" value="${list.publisher}"/>
	<input type="hidden" id="pubdate" value="${list.pubdate}"/>
	<input type="hidden" id="isbn" value="${list.isbn}"/>
	<input type="hidden" id="description" value="${list.description}"/>

	<div class="clearfix">
		<div style="float:left; width:15%"><img src="${list.image}" ></div>
		<div style="float:right; width:85%;" class="clearfix" >
			<div style="font-weight:bold;color:black">${list.title}</div>
			<div>${list.author} 저&nbsp;|&nbsp;${list.publisher}&nbsp;|&nbsp;${list.pubdate}&nbsp;|&nbsp;${list.isbn}&nbsp;|&nbsp;<a href="${list.link}">링크</a></div>
			<div style="color:red">${list.price}원</div>
			<div>${list.description}</div>	
		</div>	
	</div>
	<div class="bookList-btn">			
		<button id="book-wish" class="btn btn-warning"><span>위시리스트에 담기</span></button>
		<button id="book-up" class="btn btn-warning">추천 
			<span id="count" style="color:red;font-weight:bold;">${list.count}</span>
		</button>
	</div>
</div>
</div>
</c:forEach>

<!-- 댓글 -->
<br/>
<form role="form" method="post">
<div class="card">
	<div>
		<div class="card-body" style="padding:13px;"><textarea id="newReplyText" name="reply_text" class="form-control" rows="2"></textarea></div>
		<div class="card-footer" style="padding:10px;">
			<button type="button"  id="replyAddBtn" class="btn btn-primary" style="font-size:0.7em;">등록</button>
		</div>
	</div>
</div>
<br/>

<div id="reply--info">댓글</div>		
<div id="replies" style="list-style:none;">

</div>
<div class="paging" style="margin-top:10px; display: flex; justify-content: center;">
<ul class="pagination pagination-sm" style="list-style:none;">
         	
</ul>
</div>
</form>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script>
$.ajaxSetup({cache:false});	

var isbn = "${isbn}";// 현재 게시글 번호
var principal= "${principal.user.id}";
var replyPageNum = 1; // 댓글 페이지 번호 초기화

// 댓글 목록 함수 호출
getRepliesPaging(replyPageNum);
	
// 댓글 목록 출력 함수
function getRepliesPaging(page) {
	$.getJSON("/reply/" + isbn + "/" + page, function (data) {
		var str = "";			
		$(data.replies).each(function () {
			str += "<div id='reply--box"+this.id+"' class='clearfix reply--box'>"
			+ "<input type='hidden' id='reId' value='"+this.id+"'/>"
			+ "<input type='hidden' id='reUser' value='"+this.user.id+"'/>"
			+ "<div id='reply-content'  class='reply-content'>"+this.content+"</div>"
			+ "<div class='reply-etc'>"	
			+ "<div class='reply-userId'>작성자:&nbsp;"+this.user.userId
			+ "<div class='addChat' onclick='goRoom("+this.user.id+")'>채팅 신청</div></div>"
			+ "<button type='button' onClick='index.bookReMod("+this.id+","+page+",\""+this.user.id+"\",\""+this.content+"\")' style='margin-right:5px'>수정</button>"
			+ "<button type='button' onClick='index.bookReDel("+this.id+","+this.user.id+","+page+")'>삭제</button>"
			+ "</div>"
			+ "</div>"	
		});		
		$("#replies").html(str)			
		//페이지 번호 출력 호출
		printPageNumbers(data.pageMaker);
	});
}
	
//댓글 목록 페이지 번호 출력 함수
function printPageNumbers(pageMaker) {
	var str = "";
	if (pageMaker.prev) {
		str += "<li><a href='"+(pageMaker.startPage-1)+"'>이전</a></li>";
	}
	for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
		var strCalss = pageMaker.criteria.page == i ? 'class=active' : '';
		str += "<li "+strCalss+"><a href='"+i+"'>"+i+"</a></li>";
	}
	if (pageMaker.next) {
		str += "<li><a href='"+(pageMaker.endPage + 1)+"'>다음</a></li>";
	}
	$(".pagination-sm").html(str);
}
	
//목록페이지 번호 클릭 이벤트
$(".pagination").on("click", "li a", function(event){
	event.preventDefault();
	 replyPageNum = $(this).attr("href");
	 getRepliesPaging(replyPageNum);
});
	
//댓글 저장 버튼 클릭 이벤트 발생시
$("#replyAddBtn").click(function(){
	let user = $('#user').val(); 
	let data={
		content: $('#newReplyText').val(),
		isbn: $("#isbn").val()
	};				
	if(user==""){
		alert('로그인 해주세요');
	}else{
		$.ajax({
			type: "post",
			url: "/reply/create",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "text" 
		}).done(function(result){
			getRepliesPaging(replyPageNum);
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
});		
</script>
<script src="/js/book.js"></script>