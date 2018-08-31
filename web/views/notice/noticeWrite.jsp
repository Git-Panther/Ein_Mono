<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int currentPage = Integer.parseInt(request.getParameter("currentPage")); 

%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>공지사항 작성</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<script>
function returnNoticeList(){
	
	location.href = "/mono/noticeList.do?currentPage=<%=currentPage%>";
}

function writeOk(){
	$("#writeNotice").submit();
}
</script>
<style>
/* 	.outer{
		border:1px solid #0d0d26;
		width:900px;
		height:420px;
		margin-left:auto;
		margin-right:auto;
		 border-radius: 5px; 
		
	}
	.inner{
	margin-left:auto;
	margin-right:auto;
	border:1px solid white;
	width:700px;
	height:300px;
	}
	
	#textarea{
	resize:none;
	}
	
	#btn1, #btn2{
	display: inline-block;
	}
	
	#btn1:hover, #btn2:hover{
	border-radius: 5px;
	 background:#0d0d26;
		color:white;
		transition:0.5s;
		cursor:pointer;
	} */
	
	#btn1, #btn2{
    	background:rgba(61, 96, 131, 0.8);
    	color:white;
    	display:inline-block;
    	border-radius: 5px;
    	cursor:pointer;
    }
	
			    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:auto; margin-right:auto; margin-top:75px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    /* .jiCenter{
		float:left;
    } */
    #jiCenter{
    	width:1200px;
    	margin-left:auto;
    	margin-right:auto;
    }
    .tableArea th{
    	border-top:1px solid black;
    	border-bottom:1px solid black;
    	background:rgba(61, 96, 131, 0.8);
    	color:white;
    	font-size:13px;
    	padding:5px;
    }
    .tableArea td{
    	font-size:12px;
    	padding:7px;
    	border-bottom:1px solid black;
    }

	 .cancelUpdateBtn{
    	margin-top:60px;
    }
    .cancelUpdateBtn{
    	border:1px solid white;
    	padding:3px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(61, 96, 131, 0.8);
    	color:white;
    	font-size:11px;
    }
    #title{
    	float:left;
    }
	#content{
	resize:none;

	}
</style>
<script>
function validate(){
	
	var title = $("#title").val();
	var content = $("#textarea").val();
	
	if("" == title || "" == content){
		alert("빈칸이 있는지 확인해주세요");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>
<br>
<br>
<%-- <div class = "outer">
<br>
<h3 align = "center">공지사항 작성</h3>
	<div class = "inner" align = "center">
	<br>
		<form id = "writeNotice" method = "post" action = "/mono/noticeWrite.do" onsubmit = "return validate();">
			<input type = "hidden" name = "memberCode" value = "<%=member.getMemberCode()%>"/>
			<input type = "hidden" name = "currentPage" value = "<%=currentPage%>"/>
				<table>
				<tr>
					<th>제목</th>
					<td colspan = "3"><input id = "title" type = "text" name = "title" size = "50"/></td>
				</tr>
				<tr>
					<th></th>
				</tr>
				<tr>
					<td colspan = "4">
						<textarea name = "content" id = "textarea" cols = "60" rows = "12" placeholder = "공지사항을 입력해주세요"></textarea>
					</td>
				</tr>

			</table>
			<br>
			<div align = "center" id = "writeBtn">
				<div id = "btn1" onclick = "writeOk();">작성완료</div>&emsp;
				<div id = "btn2" onclick = "returnNoticeList();">돌아가기</div>
			</div>
			</form>
	</div>
</div> --%>

<section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
					<form id="writeNotice" method="post" action="/mono/noticeWrite.do" onsubmit = "return validate();">
					<input type = "hidden" name = "memberCode" value = "<%=member.getMemberCode()%>"/>
					<input type = "hidden" name = "currentPage" value = "<%=currentPage%>"/>

						<table align="center" style="width:900px;">
						
							<tr>
								<th width="200">제목</th>
								<td colspan="7" "  size="105">								
									<input type="text"  id="title" name="title"  size="90" />
								</td>
							</tr>
							<tr>
								<th colspan="8">내용</th>
							</tr>
							<tr>
								<td colspan="8" style="word-break:break-all;">
								
								<textarea id="content" name="content" cols="90" rows="15"></textarea>
								</td>
							</tr>
						</table>
						<br>
						<div align = "center" id = "writeBtn">
				<div id = "btn1" onclick = "writeOk();">작성완료</div>&emsp;
				<div id = "btn2" onclick = "returnNoticeList();">돌아가기</div>
			</div>
			</form>

<%@ include file="../common/footer.jsp" %>
</body>
</html>