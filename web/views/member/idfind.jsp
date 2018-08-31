<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	MemberVo memberfindid = (MemberVo)request.getAttribute("memberfindid");
  
    
    %>
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
		<%--  var idid = "<%=memberfindid.getMemberId()%>";  --%>
	function findid(){
		var memberemail = $("#findid").val();
		
		$.ajax({
			url:"/mono/findid.do?email="+memberemail,
			type:"post",
			data:{memberEmail:memberemail},
			success:function(data){
				
				if(data != "이메일없음"){
					alert("회원님의아이디는 "+data+"입니다");
					
				}else{
					alert("입력하신 이메일은 없습니다")
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
<h2 align = "center">이메일을 입력하세요</h2>
<br>
<div id = "findArea" align = "center">
<div id = "area1"><input type = "text"name ="findId" id = "findid" placeholder = "EMAIL" SIZE = "40px" /></div><br>
<div id = "area2" onclick = "findid();">찾기</div>
</div>


</div>

</body>
</html>