<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<form>
	<input type="hidden" id="id" value="${principal.user.id}"/>
	<c:if test="${empty principal.user.oauth}">
		<div class="form-group">
			<label for="userid">아이디</label>
			<input type="text" value="${principal.user.userId}" class="form-control" placeholder="아이디 입력" id="userid" readonly>
		</div>
		<div class="form-group">
			<label for="password">비밀번호</label>
			<input type="password"  class="form-control" placeholder="비밀번호 입력" id="password">
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input type="email" value="${principal.user.email}"  class="form-control" placeholder="이메일 입력" id="email">
		</div>
		<button id="btn-update" class="btn btn-primary" style="margin-top:10px">회원수정완료</button>
	</c:if>
	
	<c:if test="${not empty principal.user.oauth}">
		<div class="form-group">
			<label for="userid">아이디</label>
			<input type="text" value="${principal.user.userId}" class="form-control" placeholder="아이디 입력" id="userid" readonly>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input type="email" value="${principal.user.email}"  class="form-control" placeholder="이메일 입력" id="email" readonly>
		</div>
	</c:if>	
</form>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script src="/js/user.js"></script>