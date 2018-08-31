<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
</head>
<style>
	.outer{
		border:2px solid #0d0d26;
		width:400px;
		height:500px;
		margin-left:auto;
		margin-right:auto;
		
		border-radius: 5px;
	}
	

	#area2:hover{
	background:#0d0d26;
		color:white;
		transition:0.5s;
		cursor:pointer;
		border-radius: 5px;
	}
</style>
<script>
	function findpwd(){
	var memberemail = $("#findpwd").val();
	var memberid = $("#findpwd2").val();
		
		$.ajax({
			url:"/mono/findpwd.do?email="+memberemail+"&id="+memberid,
			type:"post",
			data:{memberEmail:memberemail},
			success:function(data){
				
				if(data == "정보일치"){
		
				 window.close();
					var url = "pwdwriteMember.jsp?memberid="+memberid;
					var name = "비밀번호입력페이지";
					
					window.open(url, name, 'left = 500, top = 100,width=500, height=520, toolbar=1, status = 1, scrollbar=1, resizable = 1, location = no'); 
					
				}else{
					alert("정보가 없습니다")
				}
			},error:function(e){
				console.log(e);
			}
		})
	}
</script>
</head>
<body>

<div class = "outer">
<br>
<br>
<h2 align = "center">가입시 입력한</h2>
<h2 align = "center">이메일과 아이디를 입력하세요</h2>
<br>
<div id = "findArea" align = "center">
<div id = "area1"><input type = "text"name ="findpwd" id = "findpwd" placeholder = "EMAIL" SIZE = "40px" /></div>
<br>
<div id = "area1-1"><input type = "text"name ="findpwd2" id = "findpwd2" placeholder = "ID" SIZE = "40px" /></div>
<br>
<div id = "area2" onclick = "findpwd();">찾기</div>
</div>

</body>
</html>