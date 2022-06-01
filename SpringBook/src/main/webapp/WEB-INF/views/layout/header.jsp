<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link rel="stylesheet" href="/css/style.css">	
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
<div class="container">

<a class="navbar-brand" href="/" style="font-size:23px;">Book Search</a>
<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
<c:choose>
<c:when test="${empty principal}">
	<ul class="navbar-nav">		
		<li class="nav-item"><a class="nav-link" href="/">홈</a></li>
		<li class="nav-item"><a class="nav-link" href="/board/boardList">게시판</a></li>
		<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
		<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>	      
	</ul>
</c:when>
<c:otherwise>
	<ul class="navbar-nav" style="font-size:0.9em">		
		<li class="nav-item"><a class="nav-link" href="/">홈</a></li>
		<li class="nav-item"><a class="nav-link" href="/board/boardList">게시판</a></li>
		<li class="nav-item crelative">
			<a class="nav-link" href="/chat/room">채팅
				<c:if test="${newChat != 0}">
					<span id="newChat">${newChat}</span>
				</c:if>
			</a>
		</li>
		<li class="nav-item"><a class="nav-link" href="/user/userpage">마이페이지</a></li>
		<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
	</ul>
</c:otherwise>
</c:choose>
</div> 
</div>  
</nav>
<br>