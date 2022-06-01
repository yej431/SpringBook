<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>    

<div class="body-wrapper" >
<div class="container">
<div class="body-content">

<form>
<div class="form-group">
	<input type="text" class="form-control" placeholder="제목 입력" id="title">
</div>  
<div class="form-group">
  <textarea class="form-control summernote" rows="5" id="content"></textarea>
</div>
</form>
<button id="btn-save" class="btn btn-primary">글쓰기 완료</button>

</div>
</div>
<%@ include file="../layout/footer.jsp" %>    
</div>

<script src="/js/board.js"></script>
<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
</script>