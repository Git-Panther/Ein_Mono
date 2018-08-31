<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<RequestVo> list = (ArrayList<RequestVo>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 확인</title>

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

	 .firstLastPageBtn, .pageBtn{
    	margin-top:80px;
    }
    .firstLastPageBtn, #deleteBlockBtn{
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
</head>
<body>
<header>
	<%@include file="/views/common/header.jsp"%>
</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="maside.jsp" %>
		    </nav>
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
		    		<h3>주문 확인</h3>
						<table align="center" style="width:800px;">
							<tr>
								<th>no</th><th>작성일</th><th>견적명</th><th>평형</th><th>작성자</th>
							</tr>
							<%if (0 < list.size()) {%>
								<%for (int i = 0; i < list.size(); i++) {%>
								<tr>
									<td><%=i+1 %></td>
									<td><%=list.get(i).getStartDate() %></td>
									<td><a href="#"><%=list.get(i).getConstAddress()%></a></td>
									<td><%=list.get(i).getAcreage()%></td>
									<td><%=list.get(i).getUserName()%></td>
								</tr>
								<%}%>
							<%} else {%>
								<tr>
									<td colspan="5">조회 된 내역이 없습니다.</td>
								</tr>
							<%}%>
						</table>
		    	</div>
		    </section>
		    
		   </div>		
	</div>


</body>
</html>