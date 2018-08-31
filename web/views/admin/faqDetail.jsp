<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% PostVo post = (PostVo) request.getAttribute("post"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ 상세 화면</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:50px; margin-top:75px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    .jiCenter{
		float:left;
    }
    #jiCenter{
    	width:1200px;
    	margin-left:auto;
    	margin-right:auto;
    }
    .tableArea th{
    	border-top:1px solid black;
    	border-bottom:1px solid black;
    	background:rgba(48, 49, 56,0.8);
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
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:11px;
    }
    #title{
    	float:left;
    }
    </style>
    
    <script>
		$(function(){
			$(".cancelUpdateBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".cancelUpdateBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
		});	
		
		function cancel(){
			location.href = "/mono/selectFAQListAdmin.do?post_type=FAQ";
		}
		function update(){
			$("#faqForm").submit();
		}
		function deleteFAQ(post_code, post_type){
			location.href = "/mono/deleteFAQ.do?post_code=" + post_code + "&post_type=" + post_type;
		}
	</script>
</head>
<body>
	<header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="adminPageMenu.jsp" %>
		    </nav>
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
					<form id="faqForm" method="post" action="/mono/updateFAQ.do">
			    		<%-- <% if(null != member && null != list) { %> --%>
						<input type="hidden" id="post_code" name="post_code" value="<%=post.getPost_code() %>"/>
						<input type="hidden" id="post_type" name="post_type" value="<%=post.getPost_type() %>"/>
						<table align="center" style="width:800px;">
							<tr>
								<th width="100">게시글코드</th>
								<td width="100" style="border-top:1px solid black;"><%=post.getPost_code() %></td>
								<th width="100">게시글분류</th>
								<td width="100" style="border-top:1px solid black;"><%=post.getPost_type() %></td>
								<th width="100">작성자</th>
								<td width="100" style="border-top:1px solid black;"><%=post.getMember_name() %></td>
								<th width="100">작성일</th>
								<td width="100" style="border-top:1px solid black;"><%=post.getWritten_date() %></td>
							</tr>
							<tr>
								<th width="100">제목</th>
								<td colspan="7">
									<input type="text"  id="title" name="title" value="<%= post.getTitle() %>"  size="95" />
								</td>
							</tr>
							<tr>
								<th colspan="8">내용</th>
							</tr>
							<tr>
								<td colspan="8">
									<textarea id="content" name="content" cols="110" rows="15"><%= post.getContent() %></textarea>
									<%-- <input type="text"  id="content" name="content" value="<%= post.getContent() %>"/> --%>
								</td>
							</tr>
						</table>
					</form>
					<%-- <% } %> --%>
				</div>	
		        <!-- <article id="jiArticle1">article</article> -->
		    </section>
			<div class="pageArea" align="center">
				<div class="cancelUpdateBtn" id="cancelBtn" onclick="cancel();">취소</div>
				<div class="cancelUpdateBtn" id="updateBtn" onclick="update();">수정</div>
				<div class="cancelUpdateBtn" id="deleteBtn" onclick="deleteFAQ('<%=post.getPost_code()%>', '<%=post.getPost_type()%>');">삭제</div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>