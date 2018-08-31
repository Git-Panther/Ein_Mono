<%@page import="ein.mono.notice.model.vo.NoticeVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    NoticeVo notice = (NoticeVo)request.getAttribute("notice");
    %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>노티스리스트 디테일</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<script>
	function returnNoticeList(){
		location.href = "/mono/noticeList.do?currentPage=<%=request.getAttribute("currentPage")%>";
	}
	
	function rewriteNotice(){
		location.href = "/mono/noticeUpdateForm.do?noticeNo="+<%=notice.getPostCode()%>+"&currentPage="+<%=request.getAttribute("currentPage")%>; 
	}
	
	function deleteNotice(){
		 location.href = "/mono/deleteNotice.do?noticeNo="+<%=notice.getPostCode()%>; 
	}
</script>
<style>
/* 	.outer{
		border:1px solid black;
		width:800px;
		height:420px;
		margin-left:auto;
		margin-right:auto;
		 border-radius: 5px; 
	}
	.inner{
	margin-left:auto;
	margin-right:auto;
	border:1px solid black;
	width:700px;
	height:250px;
	}
	
	#name{
	float:right;
	}
	
	 #content{
	float:left;
	} 
	#btn1, #btn2, #btn3{
	float:right;
	display:inline-block;
	 margin-top:-13px; 
	 margin-right:20px;
	}
	
	#btn1:hover, #btn2:hover, #btn3:hover{
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
    
    #btn1, #btn2, #btn3{
    	background:rgba(61, 96, 131, 0.8);
    	color:white;
    	display:inline-block;
    	border-radius: 5px;
    	cursor:pointer;
    }
    
    .pageArea{
    	margin-bottom:100px;
    }
 </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>

<div class = "outer" align = "center">
<%-- <h4><%=notice.getTitle() %></h4>
	<div id = "name"><p>게시자 : <%=notice.getMemberName() %>&emsp;게시일 : <%=notice.getWrittenDate() %>&emsp;&emsp;</p></div>
<br>
<br>
<br>

	<div class = "inner"><p id = "content"><%=notice.getContent() %></p></div>
	<br>
	<div class = "noticeDetailBtn">
		<div id = "btn1" onclick = "returnNoticeList();">돌아가기</div>
		<%if(member != null && "vavava".equals(member.getMemberId())){ %>
		<div id = "btn2" onclick = "rewriteNotice();">수정하기</div>
		<div id = "btn3" onclick = "deleteNotice();">삭제하기</div>
		<%}; %>
	</div> --%>
	 <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
					<form id="faqForm" method="post" action="/mono/updateFAQ.do">

						<table align="center" style="width:900px;">
							<tr>
								
								<th width="100">게시글분류</th>
								<td width="100" style="border-top:1px solid black;"> 공지사항</td>
								<th width="100">작성자</th>
								<td width="100" style="border-top:1px solid black;"><%=notice.getMemberName() %></td>
								<th width="100">작성일</th>
								<td width="100" style="border-top:1px solid black;"><%=notice.getWrittenDate() %></td>
								<th width="100">조회수</th>
								<td width="100" style="border-top:1px solid black;"><%=notice.getViewsCount() %></td>
							</tr>
							<tr>
								<th width="100">제목</th>
								<td colspan="7" "  size="95">
									<%=notice.getTitle() %>
								</td>
							</tr>
							<tr>
								<th colspan="8">내용</th>
							</tr>
							<tr>
								<td colspan="8" style="word-break:break-all;">
								<%=notice.getContent() %>
								</td>
							</tr>
						</table>
					</form>
					<%-- <% } %> --%>
				</div>	
				
		        <!-- <article id="jiArticle1">article</article> -->
		        <br>
		        <br>
		    <div class = "pageArea" align="center">
		<div id = "btn1" onclick = "returnNoticeList();">돌아가기</div>&emsp;
		<%if(member != null && "admin".equals(member.getMemberId())){ %>
		<div id = "btn2" onclick = "rewriteNotice();">수정하기</div>&emsp;
		<div id = "btn3" onclick = "deleteNotice();">삭제하기</div>
		<%}; %>
	</div> 
	
	<!-- <div class="pageArea" align="center">
				<div class="cancelUpdateBtn" id="cancelBtn" onclick="cancel();">취소</div>
				<div class="cancelUpdateBtn" id="updateBtn" onclick="update();">수정</div>
			</div>  -->
		    </section>
		    

<%@ include file="../common/footer.jsp" %>
</body>
</html>