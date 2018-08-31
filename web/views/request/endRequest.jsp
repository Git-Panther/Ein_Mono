<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String requestValue = (String)request.getAttribute("requestValue");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		width:1100px;
		height:300px;
		border:1px solid black;
		margin-left:auto;
		margin-right:auto;
		margin-top:100px;
		font-size:20px;
		text-align:center;
	}
	.Btn{
		background:rgb(48, 49, 56);
		color:white;
		font-weight:bold;
		font-size:30px;
		text-align:center;
		width:300px;
		padding:15px;
		cursor:pointer;
		font-family:'Gothic A1', sans-serif;
		display:inline-block;		
	}
	.Btns{
		width:1100px;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
		text-align:center;
	}
</style>
<script>
	function myRequestList(mCode){
		location.href="/mono/selectMyRequestList.do?mCode=" + mCode;
	}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
	<div class="outer">
		<h3><%=requestValue%></h3>
		<div class="Btns">
		<div class="Btn" onclick="myRequestList('<%=member.getMemberCode()%>');">견적신청확인</div>
		<div class="Btn" onclick="mainPage();">메인으로</div>
		</div>
	</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>