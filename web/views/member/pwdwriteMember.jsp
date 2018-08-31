<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%
    String memberid = request.getParameter("memberid"); 
    %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<title>Insert title here</title>
<style>
	.outer{
		border:2px solid #0d0d26;
		width:400px;
		height:500px;
		margin-left:auto;
		margin-right:auto;
		
		border-radius: 5px;
	}
	
	
	
	/* #area1, #area2{
	display:inline-block;
	
	} */
	
	#area2{
	width:auto;
	height:auto;
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
function rewritePwd(){
	$("#rewritePwdForm").submit();
}

function validate(){
	
	var pwd1 = $("#memberPwd").val();
	var pwd2 = $("#memberPwd1").val();
	var check_pwd = /^[a-zA-Z0-9]{6,20}$/;
	
	if(pwd1 == "" || pwd2 == ""){
		alert("비밀번호를 확인하세요");
		$("#memberPwd1").focus();
		return false;
	}else if($("#memberPwd").val() != $("#memberPwd1").val()){
		  alert("비밀번호가 일치하지 않습니다");
			$("#memberPwd1").focus();
			return false;
	}else if(!check_pwd.test($("#memberPwd").val())){
		alert("비밀번호는 영문자6~20글자로 작성해주세요");
		$("#memberPwd").focus();
		return false;
	}
	
	return true;
	
}

</script>
</head>
<body>
<div class = "outer">
<form id = "rewritePwdForm" method = "post" action = "/mono/updateNewpwd.au"  onsubmit = "return validate();">
<input type = "hidden" name = "memberid" value = "<%=memberid%>"/> 
<br>
<br>
<h2 align = "center">변경하실 비밀번호를 입력하세요</h2>

<br>
<div id = "findArea" align = "center">
<div id = "area1"><input type = "password" name ="memberPwd" id = "memberPwd" placeholder = "비밀번호" SIZE = "40px" /></div><br>
<div id = "area1-1"><input type = "password" name ="memberPwd1" id = "memberPwd1" placeholder = "비밀번호확인" SIZE = "40px" /></div><br>
<div id = "area2" onclick = "rewritePwd();">확인</div>
</div>

</form>
</div>
</body>
</html>