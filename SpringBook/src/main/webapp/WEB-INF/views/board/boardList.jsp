<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<div class="board_list_wrap">
<div style="margin-bottom:5px; font-size:14px;">총 건수: ${total}</div>
<input type="hidden" value="${principal}" id="principal">
<table class="board_list" style="border-collapse:collapse; width:100%;">	
	<thead>
		<tr>
			<th width="8%">번호</th>
			<th>제목</th>
			<th width="20%">글쓴이</th>
			<th width="10%">작성일</th>
			<th width="10%">조회</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="list" items="${list.content}">
		<c:choose>
		<c:when test="${list.user.userId eq 'admin'}">
			<tr style="background:#eeeeee">
				<td>
					<span style="background:red;color:white;border-radius:20%;font-size:10px;padding:5px;">공지</span>
				</td>
				<td class="tit"><a href="/board/${list.id}"  style="font-weight:bold">${list.title}</a></td>
				<td>${list.user.userId}</td>
				<td><fmt:formatDate value="${list.createDate}" pattern="yyyy-MM-dd" /></td>
				<td>${list.count}</td>
			</tr>
		</c:when>		
		<c:otherwise>
		<tr>
			<td class="num">${list.num}</td>
			<td class="tit"><a href="/board/${list.id}" >${list.title}</a></td>
			<td>${list.user.userId}</td>
			<td><fmt:formatDate value="${list.createDate}" pattern="yyyy-MM-dd" /></td>
			<td>${list.count}</td>
		</tr>
		</c:otherwise>
		</c:choose>
	</c:forEach>	
	</tbody>
</table>
<div class="clearfix" style="margin-top:10px;">
	<button type="button" class="btn btn-secondary"  id="bwrite" style="font-size:0.7em;float:right;">
		글쓰기
	</button>
</div>
</div>

<nav aria-label="Page navigation example">
<ul class="pagination justify-content-center" style="font-size:10px;">
	<!-- 이전 -->
	<c:choose>
		<c:when test="${list.first}"></c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="/board/boardList?page=0">&lt;&lt;</a></li>
			<li class="page-item"><a class="page-link" href="/board/boardList?page=${list.number-1}">&lt;</a></li>
		</c:otherwise>
	</c:choose>
	
	<!-- 페이지 그룹 -->
	<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
		<c:choose>
			<c:when test="${list.pageable.pageNumber+1 == i}">
				<li class="page-item disabled"><a class="page-link" href="/board/boardList?page=${i-1}">${i}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/board/boardList?page=${i-1}">${i}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
    
	<!-- 다음 -->
	<c:choose>
		<c:when test="${list.last}"></c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="/board/boardList?page=${list.number+1}">&gt;</a></li>
			<li class="page-item"><a class="page-link" href="/board/boardList?page=${list.totalPages-1}">&gt;&gt;</a></li>
		</c:otherwise>
	</c:choose>
</ul>
</nav>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
<script src="/js/board.js"></script>
</div>