<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HashMap<Integer, String> list = (HashMap<Integer, String>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체 시공 사례</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
	#aside{
		width:200px;
		height:100px;
	}
	.outer {
		width: 500px;
		height: 500px;
		margin-left: auto;
		margin-right: auto;
		padding: 20px;
	}
	.imgFile{
		width:150px;
		height:100px;
	}
</style>

</head>
<body>
<%@include file="/views/common/header.jsp" %>
	<div id="aside">
		<%@include file="/views/mypage/maside.jsp" %>
	</div>
	<div class="outer">
		<%for(int key : list.keySet()){ %>
			<div class="photoDiv">
				<input type="checkbox" name="chk" value="<%=key%>" id="<%=key%>">
				<img class="imgFile" src="/mono/upload/const_photo/<%=list.get(key)%>" onclick="clickImg(<%=key%>);">
			</div>
			
		<%} %>
		<%if(8 > list.size()){ %>
			<form action="/mono/photoInsert.do?mCode=<%=member.getMemberCode() %>" method="post" enctype="Multipart/form-data">
				<input type="file" name="fileName">
				<input type="submit" value="사진 업로드">
			</form>
		<%}else{ %>
			<h3>사진은 8개 까지만 가능합니다.</h3>
		<%} %>
			<button id="deleteBtn">사진 삭제</button>		
		
	</div>
	<script>
	$(function(){
		$("#deleteBtn").click(function(){
			var list = [];
			$("input[name='chk']").each(function(){
				if($(this).prop("checked")){
					list.push($(this).val());
				}
			});
			var str = list.join(",");
			location.href = "/mono/deletePhoto.do?chk="+str+"&mCode=<%=member.getMemberCode()%>";
		});
	});
	function clickImg(key){
		var chk = $("input:checkbox[id='"+key+"']");
		if(!chk.prop("checked")){
			chk.attr("checked",true);			
		}else{
			chk.attr("checked",false);
		}
	}
	
	</script>
	
</body>
</html>
















