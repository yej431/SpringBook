let index={
	init: function(){
		$('#btn-save').on("click", ()=>{ 
			this.save();
		});
		$('#btn-update').on("click", ()=>{ 
			this.update();
		});
	},
	
	save: function(){
		let data={
			userId: $('#userId').val(),
			password: $('#password').val(),
			email: $('#email').val()
		};
		
		console.log(data);
		console.log(JSON.stringify(data)); 

		$.ajax({
			type: "post",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){
			if(resp.status===500){
				alert("회원가입에 실패하였습니다.");
			}else{
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	update: function(){
		let data={
			id: $('#id').val(),
			userId: $('#userId').val(),
			password: $('#password').val(),
			email: $('#email').val()
		};

		$.ajax({
			type: "put",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();
