<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div style="margin:20px 0;">
<form class="form-inline">
	<input class="form-control mr-sm-2 search" type="search" aria-label="Search" id="keyword" style="width:90%"
		value="${keyword}">
	<button type="button"  class="btn btn-outline-success my-2 my-sm-0"  id="book-search"  >검색</button>
</form>
</div>

<div style="width:100%; margin-top:30px;">
<c:forEach items="${bookList}"  var="list" >	
<div class="clearfix" style="margin-top:40px;">		
	<div style="float:left;width:3%;">${list.id}</div>
	<div style="float:left; width:13%"><img src="${list.image}" ></div>
	<div style="float:right; width:84%;">
		<a href="/book/info/${list.isbn}"  id="homeToinfo"style="color:black; font-weight:bold;">${list.title}</a>
		<div>${list.author} 저&nbsp;|&nbsp;${list.publisher}&nbsp;|&nbsp;${list.pubdate}&nbsp;|&nbsp;${list.isbn}&nbsp;|&nbsp;<a href="${list.link}">링크</a></div>
		<div style="color:red">${list.price}원</div>
		<div>${list.description}</div>	
	</div>
</div>
</c:forEach>		
</div>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>
<script src="/js/book.js"></script>