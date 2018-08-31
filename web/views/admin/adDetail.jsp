<%@page import="ein.mono.event.model.vo.EventVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% EventVo event = (EventVo) request.getAttribute("event"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 상세 화면</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
    #jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto; margin-top:auto;}
    #jiSec1{width:1100px;  height:auto; margin-top:30px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    .jiCenter{
		float:left;
    }
    #jiCenter{
    	width:1100px;
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

	 .cancelBtn{
    	margin-top:20px;
    }
    .cancelBtn{
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
			$(".cancelBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".cancelBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
		});	
		
		function cancel(){
			location.href = "/mono/selectADList.do";
		}
	</script>
</head>
<body>
	<header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
		    		<%-- <% if(null != member && null != list) { %> --%>
					<input type="hidden" id="post_code" name="post_code" value="<%=event.getPost_code()%>"/>
					<input type="hidden" id="post_type" name="post_type" value="<%=event.getPost_type()%>"/>
					<table align="center" style="width:800px;">
						<tr>
							<th width="100">제목</th>
							<td width="300" style="border-top:1px solid black;"><%=event.getTitle() %></td>
							<th width="100">기간</th>
							<td width="300" style="border-top:1px solid black;"><%=event.getAdvStartDate() %> - <%=event.getAdvEndDate() %></td>
						</tr>
						<tr>
							<td colspan="4">
								<img src="/mono/upload/ad/<%=event.getAdvPhoto() %>" />
							</td>
						</tr>
					</table>
					<%-- <% } %> --%>
				</div>	
		        <!-- <article id="jiArticle1">article</article> -->
		    </section>
			<div class="pageArea" align="center">
				<div class="cancelBtn" id="cancelBtn" onclick="cancel();">목록으로</div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>