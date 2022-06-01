<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<form>
  <div class="form-group">
    <label for="userid">아이디</label>
    <input type="text" class="form-control" placeholder="아이디 입력" id="userId">
  </div>
  
  <div class="form-group">
    <label for="email">이메일</label>
    <input type="email" class="form-control" placeholder="이메일 입력" id="email">
  </div>
  
  <div class="form-group">
    <label for="password">비밀번호</label>
    <input type="password" class="form-control" placeholder="비밀번호 입력" id="password">
  </div>
</form>
<button id="btn-save" class="btn btn-primary" style="margin-top:10px">회원가입완료</button>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %> 