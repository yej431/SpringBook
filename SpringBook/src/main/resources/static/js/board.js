$('#bwrite').click(function(){
		var principal=$('#principal').val();
		if(principal===""){
			alert('로그인 해주세요');
		}else{
			location.href = "/board/saveForm";
		}
});
	
let index={
	init: function(){
		$('#btn-save').on("click", ()=>{ 
			this.save();
		});
		$('#btn-delete').on("click", ()=>{ 
			this.deleteById();
		});
		$('#btn-update').on("click", ()=>{ 
			this.update();
		});	
		$('#btn-reply-save').on("click", ()=>{ 
			this.replySave();
		});	
	},
	
	save: function(){
		let data={
			title: $('#title').val(),
			content: $('#content').val()
		};
			$.ajax({
				type: "post",
				url: "/api/board",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8", 
				dataType: "json" 
			}).done(function(){
				alert("글쓰기가 완료되었습니다.");
				location.href = "/board/boardList";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
	},
	
	deleteById: function(){
		let id=$("#boardId").val();
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,
			dataType: "json" 
		}).done(function(){
			alert("삭제되었습니다");
			location.href = "/board/boardList";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	update: function(){
		let id=$("#id").val();
		
		let data={
			title: $('#title').val(),
			content: $('#content').val()
		};

		$.ajax({
			type: "put",
			url: "/api/board/"+id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			location.href = "/board/boardList";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	replySave: function(){
		let user = $("#userId").val();
		let data={
			userId: $("#userId").val(),
			boardId:$('#boardId').val(),
			content: $('#reply-content').val()
		};

		if(user==""){
			alert('로그인 해주세요.');
		}else{
			$.ajax({
				type: "post",
				url: `/api/board/${data.boardId}/reply`, //``(backtick): 자바스크립트 변수가 string에 들어옴.
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8", 
				dataType: "json" 
			}).done(function(){
				location.href = `/board/${data.boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
	}
}

index.init();