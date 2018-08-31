<%@page import="ein.mono.partners.model.vo.PartnersVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% ArrayList<PartnersVo> list = (ArrayList<PartnersVo>) request.getAttribute("list"); %>
<!DOCTYPE html>
<html>
<head>
<title>MONO : 관리자 페이지</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style type="text/css">
   	#jiNav, #jiSec1, article,/*  aside, */ footer{
    	display:block; width:1200px; 
    	/* margin:4px; padding:4px; */
    	text-align:center;
    }
  	#jiNav{width:200px; height:auto;}
    #jiSec1{width:950px;  height:300px; margin-left:50px; margin-top:110px;}
    #jiArticle1{width:900px; background-color:#efefef;}
    /* aside{float:left; width:104px; height:60px;} */
    footer{overflow:hidden;}
    
    .jiCenter{
		/* float:center; */
		float:left;
    }
    #jiCenter{
    	width:1200px;
    	margin-left:auto;
    	margin-right:auto;
    }
    
</style>
<script>
	$(function(){
		// 관리자 마이페이지 들어왔을 때 가입신청 목록 바로 보여주기
		location.href = "/mono/selectJoinRequestPtnList.do";
	});
</script>
</head>
<body>
    <%-- <header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="adminPageMenu.jsp" %>
		    </nav>
		    <section id="jiSec1" class="jiCenter">
	    		<% if(null != member && null != list) { %>
					<% for(int i=0; i<list.size(); i++) { %>
						<%= list.get(i).getMember().getMemberCode() %>
		
					<% } %>
				<% } %>
		        <!-- <article id="jiArticle1">article</article> -->
		        
		    </section>
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer> --%>
</body>
</html>

