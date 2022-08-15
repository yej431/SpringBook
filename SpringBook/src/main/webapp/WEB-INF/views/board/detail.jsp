<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div>
작성자 : <span><i>${board.user.userId} </i></span>
</div>
<br/>

<h5>제목: ${board.title}</h5>
<hr/>
<div>${board.content}</div>
<hr/>

<div class="clearfix" style="margin:20px 0;">
	<div style="float:left">
		<a href="#"  onclick="history.back()" style="font-size:15px;color:grey">돌아가기</a>
	</div>
	<div style="float:right">
		<c:if test="${board.user.id == principal.user.id}">
			<a href="/board/${board.id}/updateForm"  style="font-size:15px; margin-right:10px; color:grey">수정</a>
			<a href="#" id="btn-delete"  style="font-size:15px; color:grey">삭제</a>
		</c:if>
	</div>
</div>

<!-- 댓글 -->
<br/>
<form role="form" method="post">
<div class="card">
	<div>
		<input type="hidden" id="boardId" value="${board.id}">
		<input type="hidden" id="userId" value="${principal.user.id}">
		<div class="card-body" style="padding:13px;"><textarea id="reply-content" name="reply_text" class="form-control" rows="2"></textarea></div>
		<div class="card-footer" style="padding:10px;">
			<button type="button"  id="btn-reply-save" class="btn btn-primary" style="font-size:0.7em;">등록</button>
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
$(document).ready(function () {	
	
	$.ajaxSetup({cache:false});	

	var boardId = "${board.id}";// 현재 게시글 번호
	var principal= "${principal.user.userId}";
    var replyPageNum = 1; // 댓글 페이지 번호 초기화

 	// 댓글 목록 함수 호출
   getRepliesPaging(replyPageNum);
	
	// 댓글 목록 출력 함수
	function getRepliesPaging(page) {
		$.getJSON("/api/board/"+boardId+"/" + page, function (data) {
			var str = "";
			
			$(data.replies).each(function () {
				str += "<div id='reply--box' class='clearfix'>"
				+ "<input type='hidden' id='replyId' value='"+this.id+"'/>"
				+ "<input type='hidden' id='replyUserId' value='"+this.user.userId+"'/>"
				+ "<div id='reply-content'  class='reply-content'>"+this.content+"</div>"
				+ "<div class='reply-etc'>"	
				+ "<div id='newReplyWriter' name='reply_writer' class='reply-userId'>작성자:&nbsp;"+this.user.userId+"</div>"
				+ "<button type='button' name='reDel'>삭제</button>"
				+ "</div>"
				+ "</div>"	
	 		});		
			
			$("#replies").html(str)
			
			//페이지 번호 출력 호출
			printPageNumbers(data.pageMaker);
		});
	}
	
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
	
	$(".pagination").on("click", "li a", function(event){
		event.preventDefault();
        replyPageNum = $(this).attr("href");
        getRepliesPaging(replyPageNum);
	});
	
	$(document).on("click", "button[name='mod']", function(){
		formObj.attr("action", "${path}/reply/modView");
		formObj.attr("method", "post");
		formObj.submit();
	});
	
	$(document).on("click", "button[name='reDel']", function(){
		var replyId =$("#replyId").val();
		var userId = $("#replyUserId").val();
		
		if(principal == ""){
			alert("로그인 해주세요");
		}else if(principal != userId){
			alert("작성자가 다릅니다");
		}else{
			$.ajax({
				type : "delete",
				url: "/api/board/reply/"+replyId,
				headers:{
					"Content-type" : "application/json",
					"X-HTTP-Method-Override" : "delete"
				},
				dataType : "text",
				success : function (result) {
	                console.log("result : " + result);
	                if (result == "delSuccess") {
	                    alert("삭제되었습니다");                    
	                }
	                getRepliesPaging(replyPageNum);
	            }
			});		
		}
	});
});

$('.summernote').summernote({
	tabsize: 2,
	height: 300
});
</script>
<script src="/js/board.js"></script>