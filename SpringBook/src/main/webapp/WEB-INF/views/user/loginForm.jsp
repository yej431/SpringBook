<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<form action="/auth/loginProc" method="post">
  <div class="form-group">
    <label for="userid">아이디</label>
    <input type="text" name="username" class="form-control" placeholder="아이디 입력" id="userid">
  </div>
  
  <div class="form-group">
    <label for="password">비밀번호</label>
    <input type="password" name="password" class="form-control" placeholder="비밀번호 입력" id="password">
  </div>
  
  <button id="btn-login" class="btn btn-primary">로그인</button>
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=a804841f47ca417eacc43348a0e4905c&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code">
		<img height="39px" src="/image/kakao-btn.png"/>
	</a>
</form>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>