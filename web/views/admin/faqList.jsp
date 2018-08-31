<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="ein.mono.common.PageInfo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<PostVo> list = (ArrayList<PostVo>) request.getAttribute("list"); %>
<%
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	int listCount = pi.getTotalCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	if(endPage == 0) {
		endPage = 1;
	}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ 관리</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:30px; margin-top:75px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    #btnDiv{
	    margin-right:80px;
	    margin-top:55px;
    	width:800px; 
    	float:right;
    }
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

	 .firstLastPageBtn, .pageBtn{
    	margin-top:80px;
    }
    .firstLastPageBtn, #writeBtn{
    	border:1px solid white;
    	padding:3px;
    	border-radius:5px;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:11px;
    }
    .pageBtn{
    	padding:3px;
    	display:inline-block;
    	color:black;
    	font-size:11px;
    }
    </style>
    
    <script>
		$(function(){
			$(".tableArea td").mouseenter(function(){
				$(this).parent().css("background", "rgba(48, 49, 56,0.8)");
				$(this).parent().css("color", "white");
				$(this).parent().css("cursor", "pointer");
			}).mouseout(function(){
				$(this).parent().css("background", "white");
				$(this).parent().css("color", "black");
			}).click(function(){
				var post_code = "FAQ" + $(this).parent().children().eq(0).text();
				var post_type = $(this).parent().children().eq(1).text();
				location.href = "/mono/selectFAQ.do?post_code=" + post_code + "&post_type=" + post_type;
				<%-- location.href = "/mwp/selectBoard.do?boardNo=" + boardNo + "&currentPage=<%=currentPage%>"; --%>
			});
			
			$(".firstLastPageBtn, #writeBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".pageBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
			
			$("#writeBtn").click(function(){
				location.href = "/mono/views/admin/insertFAQ.jsp";
			});
		});	
		
		function movePage(pageNum){
			location.href = "/mono/selectFAQListAdmin.do?currentPage=" + pageNum + "&post_type=FAQ";
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
		   
	   		<div align="right" class="jiCenter">
		    	<div id="btnDiv" align="right">
		    		<button id="writeBtn" style="width:70px;">FAQ 작성</button>
		    	</div>
			    <section id="jiSec1" >
			    	<div class="tableArea">
			    		<%-- <% if(null != member && null != list) { %> --%>
						<table align="center" style="width:800px;">
							<tr>
								<th width="50">번호</th>
								<th width="100">게시글분류</th>
								<th width="300">제목</th>
								<th width="100">작성자</th>
								<th width="100">작성일</th>
							</tr>
							<% if(list.size() == 0) { %>
								<tr>
									<td colspan="5">작성된 FAQ가 없습니다.</td>
								</tr>
								<% } else { %>
								<% for(PostVo p : list) { %>
								<% String post_code = (p.getPost_code()).substring(3); %>
									<tr>
										<td><%= post_code %></td>
										<td><%= p.getPost_type() %></td>
										<td>	<%= p.getTitle() %></td>
										<td>
											<% if(p.getWriter_code().equals("A1")) { %>
												관리자
											<% } %>
										</td>
										<td>
											<%= p.getWritten_date() %>
										</td>
									</tr>
								<% } %>	
							<% } %>
						</table>
						<%-- <% } %> --%>
					</div>	
			        <!-- <article id="jiArticle1">article</article> -->
			    </section>
			    <!-- 페이징 처리 부분 -->
			    <section id="jiSec2">
					<div class="pageArea" align="center">
						<div class="firstLastPageBtn" onclick="movePage(1);"> << </div>
						<% for(int i = startPage; i <= endPage; i++) { %>
							<%if(i == currentPage) { %>
								<div class="pageBtn" style="color:lightgray; border-bottom:1px solid black;"><%=i %></div>
							<% } else { %>
								<div class="pageBtn" onclick="movePage(<%=i %>);"><%=i %></div>
							<% } %>
						<% } %>
						<div class="firstLastPageBtn" onclick="movePage(1);"> >> </div>
					</div> 
				</section>
		    </div>
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>

</html>