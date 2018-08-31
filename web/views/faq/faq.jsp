<%@page import="ein.mono.board.model.vo.PostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	ArrayList<PostVo> list = (ArrayList<PostVo>) request.getAttribute("list");
%>    
    
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css?family=Rubik" rel="stylesheet">
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
	.faqMenu{
		font-family: 'Rubik', sans-serif;
		font-size:60px;
		margin-top:90px;
		margin-left:auto;
		margin-right:auto;
		width:1100px;
		padding-left:55px;
	}
	.accordionMenu{
		background:#fff;
		padding:10px;
		width:60%;
		margin-left:auto;
		margin-right:auto;	
	}
	.accordionMenu h2{
		text-align:center;
		margin:5px 0;
		padding:0;
	}
	.accordionMenu h2 a{
		font-size:25px;
		display:block;
		font-weight:normal;
		text-decoration:none;
		margin:0;
		padding:10px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		border-radius:25px;
	}

	.accordionMenu :target h2 a,
	.accordionMenu h2 a:focus,
	.accordionMenu h2 a:hover,
	.accordionMenu h2 :active {
		background:rgb(61, 96, 131);
		color:white;
		text-weight:bold;
	}
	a{
		text-decoration:none;
		color:gray;
	}
	.accordionMenu p {
		font-size:20px;
		padding:0 10px;
		margin:0;
		height:0;
		overflow:hidden;
		transition:height 0.5s ease-in;
		text-align:center;
	}

	.accordionMenu :target p{
		overflow: auto;
		height:50px;
		/*box-shadow:2px 2px 1px gray;*/
	}


  </style>

<body>
<%@ include file="../common/header.jsp" %>
	<div class="faqMenu">
	<label>  FAQ</label>
	</div>
	<hr width="1100px;">
	<div class="accordionMenu">
		<% for(int i=0; i<list.size(); i++) { %>
			<div id="a<%=i %>" class="menuSection">
				<h2><a href="#a<%=i %>"><%=list.get(i).getTitle() %></a></h2>
				<p><%=list.get(i).getContent() %></p>
			</div>
		<% } %>
	</div>
 <%@ include file="../common/footer.jsp" %>
</body>
</html>