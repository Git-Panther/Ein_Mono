<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ 작성 화면</title>
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
		function insertFAQ(){
			$("#faqForm").submit();
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
					<form id="faqForm" method="post" action="/mono/insertFAQ.do">
						<input type="hidden" id="post_type" name="post_type" value="FAQ"/>
			    		<%-- <% if(null != member && null != list) { %> --%>
						<table align="center" style="width:800px;">
							<tr>
								<th width="200">게시글분류</th>
								<td width="200" style="border-top:1px solid black;">FAQ</td>
								<th width="200">작성자</th>
								<td width="200" style="border-top:1px solid black;"><%=member.getMemberName() %></td>
							</tr>
							<tr>
								<th width="100">제목</th>
								<td colspan="3">
									<input type="text"  id="title" name="title"  size="95" />
								</td>
							</tr>
							<tr>
								<th colspan="4">내용</th>
							</tr>
							<tr>
								<td colspan="4">
									<textarea id="content" name="content" cols="110" rows="15"></textarea>
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
				<div class="cancelUpdateBtn" id="updateBtn" onclick="insertFAQ();">작성</div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>