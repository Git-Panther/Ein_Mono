<%@page import="ein.mono.notice.model.vo.NoticeVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    NoticeVo notice = (NoticeVo)request.getAttribute("notice");
     int currentPage = (int)request.getAttribute("currentPage"); 
    %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>수정하기정보조회</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<script>
function returnNoticeList(){
	
	location.href = "/mono/noticeList.do?currentPage=<%=request.getAttribute("currentPage")%>";
}

function rewriteOk(){
	$("#rewriteNotice").submit();
}
</script>
<style>
/* 	.outer{
		border:2px solid black;
		width:800px;
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
	
	#btn1, #btn2{
    	background:rgba(61, 96, 131, 0.8);
    	color:white;
    	display:inline-block;
    	border-radius: 5px;
    	cursor:pointer;
    }
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>

<%-- <div class = "outer">
<h3 align = "center">공지사항 수정</h3>
	<div class = "inner" align = "center">
	<br>
		<form id = "rewriteNotice" method = "post" action = "/mono/noticeUpdate.do">
			<input type = "hidden" name = "memberCode" value = "<%=member.getMemberCode()%>"/>
			<input type = "hidden" name = "currentPage" value = "<%=request.getAttribute("currentPage")%>"/>
			<input type = "hidden" name = "noticeNo" value = "<%=notice.getPostCode()%>"/>
			
				<table>
				<tr>
					<th>제목</th>
					<td colspan = "3"><input type = "text" name = "title" size = "50" value = "<%=notice.getTitle() %>"/><td>				
				</tr>
				<tr>
					<th></th>
				</tr>
				<tr>
					<td colspan = "4">
						<textarea name = "content" id = "textarea" cols = "60" rows = "12"><%=notice.getContent() %></textarea>
					</td>
				</tr>

			</table>
			<br>
			<div align = "center" id = "writeBtn">
				<div id = "btn1" onclick = "rewriteOk();">작성완료</div>&emsp;
				<div id = "btn2" onclick = "returnNoticeList();">돌아가기</div>
			</div>
			</form>
	</div>
</div> --%>
<section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
					<form id="rewriteNotice" method="post" action="/mono/noticeUpdate.do">
					<input type = "hidden" name = "memberCode" value = "<%=member.getMemberCode()%>"/>
			<input type = "hidden" name = "currentPage" value = "<%=request.getAttribute("currentPage")%>"/>
			<input type = "hidden" name = "noticeNo" value = "<%=notice.getPostCode()%>"/>

						<table align="center" style="width:900px;">
							<tr>
								
								<th width="100">게시글분류</th>
								<td width="100" style="border-top:1px solid black;">게시판 > 공지사항수정</td>
								<th width="100">작성자</th>
								<td width="100" style="border-top:1px solid black;"><%=notice.getMemberName() %></td>
								<th width="100">작성일</th>
								<td width="100" style="border-top:1px solid black;"><%=notice.getWrittenDate() %></td>
							</tr>
							<tr>
								<th width="100">제목</th>
								<td colspan="5" "  size="95">								
									<input type="text"  id="title" name="title" value="<%=notice.getTitle() %>"  size="95" />
								</td>
							</tr>
							<tr>
								<th colspan="6">내용</th>
							</tr>
							<tr>
								<td colspan="8" style="word-break:break-all;">
								
								<textarea id="content" name="content" cols="110" rows="15"><%=notice.getContent() %></textarea>
								</td>
							</tr>
						</table>
						<br>
						<div align = "center" id = "writeBtn">
				<div id = "btn1" onclick = "rewriteOk();">작성완료</div>&emsp;
				<div id = "btn2" onclick = "returnNoticeList();">돌아가기</div>
			</div>
					</form>
					<%-- <% } %> --%>
				</div>
				</section>	
<%@ include file="../common/footer.jsp" %>
</body>
</html>