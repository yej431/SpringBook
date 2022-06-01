<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div style="margin:20px 0;">
<form class="form-inline">
	<input class="form-control mr-sm-2 search" type="search" aria-label="Search" id="keyword" style="width:93%"
		value="${keyword}">
	<button type="button"  class="btn btn-outline-success my-2 my-sm-0"  id="book-search" style="width:6%">검색</button>
</form>
</div>

<div class="clearfix" style="margin-top:40px;">
<div style="width:35%; float:left;">
	<div class="clearfix"  style="border-bottom:1px solid #ccc; padding-bottom:5px;">
		<span style="font-size:1em; font-weight:600">최근 댓글</span>
	</div>
	<div style="margin-top:10px;">
		<c:forEach items="${rReply}"  var="r" >		
			<a href="javascript:void(0)" onclick="searchRe('${r.isbn}')" id="searchRe" class="rReply">				
				${r.content}
			</a>
		</c:forEach>
	</div>
</div>
<div style="width:60%; float:right;">
	<div style="border-bottom:1px solid #ccc;padding-bottom:5px;font-weight:600">
		<span style="font-size:1em; font-weight:600">추천 순위 BEST5</span>
	</div>
	<c:forEach items="${bookRanking}"  var="list" >	
	<div class="clearfix" style="margin-top:20px;">		
		<input type="hidden" id="isbn" value="${list.isbn}">
		<div style="float:left; width:10%">
			<a href="/book/info/${list.isbn}"><img src="${list.image}"  style="width:70px"></a>
		</div>
		<div style="float:right; width:84%;">
			<a href="/book/info/${list.isbn}"  style="color:black; font-weight:bold;font-size:14px;">${list.title}</a>
			&nbsp;
			<span style="font-size:12px;">${list.author} 저&nbsp;&nbsp;|&nbsp;&nbsp${list.publisher}</span>			
			<div style="font-size:12px; margin-top:3px;">${list.description}</div>
		</div>
	</div>
</c:forEach>	
</div>
</div>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script>
function searchRe(isbn) {
	var regExp=/[<b></b>]/gi;
	var isbn2=isbn.replace(regExp, "");
	location.href="/book/info/"+isbn2; 
}
</script>
<script src="/js/book.js"></script>