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
		
		console.log(data); //자바스크립트 오브젝트
		console.log(JSON.stringify(data)); //JSON 문자열

		//ajax호출시 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
		//버전이 상승하면서 ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환해줌.
		$.ajax({
			//회원가입 수행 요청
			type: "post",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
			dataType: "json" 
			//요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열,
			//생긴것이 json이라면 javascript오브젝트로 변경.
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