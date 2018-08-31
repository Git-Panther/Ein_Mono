,<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>로그인폼</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<style>
#LoginBtn, #JoinBtn{
	display: inline-block;
	 margin : 25px; 
	
	
	cursor:pointer;
	
	border-radius: 5px;
	width:auto;
	height:auto;
	border:1px solid white;
	}
	
	.loginArea{
		width:300px;
		height:300px;
		border:2px solid #0d0d26;
		margin-left:auto;
		margin-right:auto;
		border-radius: 5px;
		
	}
	
	#LoginBtn:hover, #JoinBtn:hover,#idfind:hover, #pwdfind:hover{
		background:#0d0d26;
		color:white;
		transition:0.5s;
		cursor:pointer;
	}
	
	
	
	#idfind, #pwdfind{
	display: inline-block;
	font-size:10px;
	}
</style>
<script>
function join(){
	location.href = "joinMember.jsp"; 
}

function idfind(){
	var url = "idfind.jsp";
	var name = "아이디찾기";
	
	window.open(url, name, 'left = 500, top = 100,width=500, height=520, toolbar=1, status = 1, scrollbar=1, resizable = 1, location = no');
}

function pwdfind(){
	var url = "pwdfind.jsp";
	var name = "비밀번호찾기";
	
	window.open(url, name, 'left = 500, top = 100,width=500, height=520, toolbar=1, status = 1, scrollbar=1, resizable = 1, location = no');
}



function login(){
	var memberid = $("#memberId").val();
	
	$.ajax({
		url:"/mono/idCheck.do",
		type:"post",
		data:{memberId:memberid},
		success:function(data){
			if(data == "사용가능합니다"){
				 alert("존재하지않는 아이디입니다");
				$("#memberId").focus(); 

			}else{

						 $("#loginForm").submit(); 
				
			}
		},error:function(e){
			console.log(e);
		}
		
	});
}

/* $("#memberPwd").keyup(function(key){
    if(key.keyCode == 13){
    	$("#loginForm").submit();
    }
 }); */


</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class = "loginArea" align = "center">

<h2>MONO</h2>
		<br>
			<form id = "loginForm" action = "/mono/loginMember.au" method = "post">
				<table class = "login">
					<tr>
						<td align = "center">아이디</td>
						<td>&emsp;<input type = "text" name ="memberId" id = "memberId" placeholder = "ID"/><br></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td>&emsp;<input type = "password" name = "memberPwd" id = "memberPwd" placeholder = "PASSWORD"/>
					</tr>
										
					<tr>
						<td colspan = "2" name = "btn" >
							<div id = "JoinBtn" onclick = "join();">회원가입</div>&emsp;&emsp;
							<div id = "LoginBtn" onclick = "login();" >로그인</div>
						</td>
					</tr>
					 <tr>
						<td colspan = "2" >
						<div id = "findinfo" align = "center">
							<div id = "idfind" onclick = "idfind();">아이디찾기</div>&emsp;
							<div id = "pwdfind" onclick = "pwdfind();">비밀번호 찾기</div>
						</div>
						</td>
					
					</tr> 
				</table>
			</form>

		</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>