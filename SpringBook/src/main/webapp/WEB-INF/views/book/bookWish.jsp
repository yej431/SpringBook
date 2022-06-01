<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div style="margin:40px 0;">
<div style="width:100%;" class="top">
<c:forEach items="${list.content}"  var="list" >	
<div class="clearfix" style="margin-top:10px;">		
<input type="hidden" id="userId" value="${list.user.id}">
<input type="hidden" id="title" value="${list.title}">
<input type="hidden" id="author" value="${list.author}">

<div class="clearfix">
	<div style="float:left; width:12%"><img src="${list.image}" ></div>
	<div style="float:right; width:88%;">
		<div style="color:black; font-weight:bold;">${list.title}</div>
		<div>${list.author} 저&nbsp;|&nbsp;${list.publisher}&nbsp;|&nbsp;${list.pubdate}&nbsp;|&nbsp;${list.isbn}&nbsp;|&nbsp;<a href="${list.link}">링크</a></div>
		<div style="color:red">${list.price}원</div>
		<div>${list.description}</div>	
	</div>
</div>
<div class="bookList-btn" >			
	<button onClick="index.wishDelete(${list.id}, ${list.user.id})"  class="btn btn-danger"><span>삭제</span></button>
</div>
</div>
</c:forEach>		
</div>
</div>

<nav aria-label="Page navigation example">
<ul class="pagination justify-content-center">
	<!-- 이전 -->
	<c:choose>
		<c:when test="${list.first}"></c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="/book/bookWish?page=0">&lt;&lt;</a></li>
			<li class="page-item"><a class="page-link" href="/book/bookWish?page=${list.number-1}">&lt;</a></li>
		</c:otherwise>
	</c:choose>
	
	<!-- 페이지 그룹 -->
	<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
		<c:choose>
			<c:when test="${list.pageable.pageNumber+1 == i}">
				<li class="page-item disabled"><a class="page-link" href="/book/bookWish?page=${i-1}">${i}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/book/bookWish?page=${i-1}">${i}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
    
	<!-- 다음 -->
	<c:choose>
		<c:when test="${list.last}"></c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="/book/bookWish?page=${list.number+1}">&gt;</a></li>
			<li class="page-item"><a class="page-link" href="/book/bookWish?page=${list.totalPages-1}">&gt;&gt;</a></li>
		</c:otherwise>
	</c:choose>
</ul>
</nav>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script src="/js/book.js"></script>