let index={
	init: function(){
		$('#book-search').on("click", ()=>{ 
			this.bookSearch();
		});
		$('#book-wish').on("click", ()=>{ 
			this.bookWish();
		});
		$('#book-up').on("click", ()=>{ 
			this.bookUp();
		});
		$('#wish-del').on("click", ()=>{ 
			this.wishDel();
		});
		$('#btn-reply-save').on("click", ()=>{ 
			this.replySave();
		});
	},

	bookSearch: function(){
		let data={
			keyword: $('#keyword').val()
		};

		$.ajax({
			type: "post",
			url: `/book/bookSearch/${data.keyword}`, 
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
		}).done(function(){
			location.href = `/book/bookSearch/${data.keyword}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},

	bookWish: function(){
		let user = $('#user').val();
		let data = {
				link: $('#link').val(),
				image: $('#image').val(),
				author: $('#author').val(),
				price: $('#price').val(),
				publisher: $('#publisher').val(),
				pubdate: $('#pubdate').val(),
				title: $('#title').val(),
				isbn: $('#isbn').val(),
				description: $('#description').val()
		};
		if(user==""){
			alert('로그인 해주세요');
		}else{
			$.ajax({
				type: "post",
				url: "/book/wish", 
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8"
			}).done(function(){
				alert('위시리스트에 추가되었습니다');
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
	},
	
	bookUp: function(){
		let user = $('#user').val();
		let data = {
			link: $('#link').val(),
			image: $('#image').val(),
			author: $('#author').val(),
			price: $('#price').val(),
			publisher: $('#publisher').val(),
			pubdate: $('#pubdate').val(),
			title: $('#title').val(),
			isbn: $('#isbn').val(),
			description: $('#description').val()
		};
		
		if(user==""){
			alert('로그인 해주세요');
		}else{
			$.ajax({
				type: "post",
				url: "/book/up", 
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8"
			}).done(function(data){
				$("#count").text(data.count);
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		} 
	},
	
	wishDelete: function(id, userId){
		$.ajax({
			type: "DELETE",
			url: `/api/wish/${id}/${userId}`, //``(backtick): 자바스크립트 변수가 string에 들어온다.
			dataType: "json" 
		}).done(function(){
			alert("삭제되었습니다");
			location.href = "/book/bookWish";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},	
	
	replySave: function(){
		let user = $('#user').val();
		let isbn = $('#isbn').val();
		let data={
			isbn: $('#isbn').val(),
			content: $('#content').val()
		};		
		var regExp=/[<b></b>]/gi;
		var isbn2=isbn.replace(regExp, "");
		
		if(user==""){
			alert('로그인 해주세요.');
		}else{
			$.ajax({
				type: "post",
				url: "/book/reply",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8", 
				dataType: "json" 
			}).done(function(){
				location.href = "/book/info/"+isbn2;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
	},
	
	replyDel: function(id){
		let user = $('#user').val();
		let isbn = $('#isbn').val();		
		let regExp=/[<b></b>]/gi;
		let isbn2=isbn.replace(regExp, "");
		
		if(user==""){
			alert('로그인 해주세요.');
		}else{
			$.ajax({
				type: "DELETE",
				url: `/api/replyDel/${id}`,
				dataType: "json" 
			}).done(function(){
				location.href = "/book/info/"+isbn2;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
	},
	
	bookReDel: function(id, userId,page){
		var user = $('#user').val();		
		if(user == ""){
			alert('로그인 해주세요.');
		}else if(user != userId){
			alert('사용자가 다릅니다.');
		}else{
			$.ajax({
				type:"delete",
				url:"/reply/del/"+id,
				contentType:"application/json; charset=utf-8"
			}).done(function(){
				alert("삭제되었습니다");
				getRepliesPaging(page);
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
	},
	
	bookReMod: function(id,page,userId,content){
		var user = $('#user').val();
		var str="";			
		if(user == ""){
			alert('로그인 해주세요.');
		}else if(user != userId){
			alert('사용자가 다릅니다.');
		}else{
			str += "<div id='reply--box"+id+"' class='clearfix reply--box'>"
			+ "<div class='card-body' style='padding:13px;'>"
			+ "<textarea id='editContent' class='form-control' rows='3' style='font-size:0.9em'>"
			+ content
			+ "</textarea>"
			+ "<div class='clearfix' style='float:right;margin-top:3px;'>"
			+ "<a href='javascript:void(0)' style='font-size:0.8em;padding-right:7px;' onClick='index.bookReUpdate("+id+","+page+")'>저장</a>"
			+ "<a href='javascript:void(0)' style='font-size:0.8em;' onClick='getRepliesPaging("+page+")'>취소</a>"
			+ "</div>"
			+ "</div>"	
			+ "</div>"						
			$("#reply--box"+id).replaceWith(str)
			$("#reply--box"+id+' #editContent').focus(); 
		}				
	},
	
	bookReUpdate: function(id,page){
		var content = $('#editContent').val();
		$.ajax({
			type:"put",
			url:"/reply/update?id="+id+"&content="+content,
			contentType:"application/json; charset=utf-8"
		}).done(function(){
			getRepliesPaging(page);
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
}

index.init();

$(document).on('click', '.reply-userId', function(e){
	e.preventDefault();
	if($(this).children('div').css("display") == "none"){
		$(this).children('div').css('display','block');
	}else{
		$(this).children('div').css('display','none');
	}
});

function goRoom(id){
	location.href="/moveChating?id="+id;
}