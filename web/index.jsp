<%@page import="ein.mono.event.model.vo.EventVo"%>
<%@page import="ein.mono.keyword.model.vo.KeywordVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<KeywordVo> list = (ArrayList<KeywordVo>) request.getAttribute("list");
%>    
<% ArrayList<EventVo> mainEventList = (ArrayList<EventVo>) request.getAttribute("mainEventList"); %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모노</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<style>
	.request{
		background:url("/mono/images/request.jpg") no-repeat;
		width:1100px;
		height:400px;
		margin-left:auto;
		margin-right:auto;
		margin-top:10px;
		text-align:center;
	}
	.requestBtn{
		display:inline-block;
		width:300px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		cursor:pointer;
		border-radius:20px;
		text-align:center;
		padding:10px;
		font-size:20px;
		margin-top:180px;
		font-family: 'Nanum Gothic', sans-serif;
	}
	.keyword{
		width:1100px;
		height:50px;
		margin-left:auto;
		margin-right:auto;
		margin-top:10px;
		font-size:22px;
		text-aling:center;
		vertical-align:middle;
	}
	.key{
		width:150px;
		display:inline-block;
		background:rgba(48, 49, 56,0.8);
		color:white;
		text-align:center;
		cursor:pointer;
		border-radius:20px;
		font-size:19px;
		padding-top:5px;
		padding-bottom:6px;
		font-family: 'Nanum Gothic', sans-serif;
	}
	.board{
		width:1100px;
		height:300px;
		margin-left:auto;
		margin-right:auto;
		margin-top:45px;
		cursor:pointer;
	}
	.board div{
		width:256px;
		height:300px;
		border:1px solid black;
		margin-left:10px;
		margin-right:auto;
		margin-top:10px;
		display:inline-block;
	}
	.img{padding:0;}
	ul,li{list-style:none;}
	.slide{z-index:1; width:1100px;height:400px; margin-left:auto; margin-right:auto; position:relative;overflow:hidden;}
	.slide ul{width:400%;height:100%;transition:1s;}
	.slide ul:after{content:"";display:block;clear:both;}
	.slide li{float:left;width:25%;height:100%;}
	.slide input{display:none;}
	.slide label{display:inline-block;vertical-align:middle;width:10px;height:10px;border:2px solid #666;background:#fff;transition:0.3s;border-radius:50%;cursor:pointer;}
	.slide .pos{text-align:center;position:absolute;bottom:0px;left:0;width:100%;text-align:center;}
	#pos1:checked~ul{margin-left:0%;}
	#pos2:checked~ul{margin-left:-100%;}
	#pos3:checked~ul{margin-left:-200%;}
	#pos4:checked~ul{margin-left:-300%;}
	#pos1:checked~.pos>label:nth-child(1){background:#666;}
	#pos2:checked~.pos>label:nth-child(2){background:#666;}
	#pos3:checked~.pos>label:nth-child(3){background:#666;}
	#pos4:checked~.pos>label:nth-child(4){background:#666;}
</style>
<script>
	$(function(){
		<% if(null != mainEventList) { %>
					var mainEventImg = new Array();
					var idx = 0;
					<% for(int i=0; i<mainEventList.size(); i++) { %>
						mainEventImg[idx] = "<%=mainEventList.get(i).getAdvBanner()%>";
						idx++;
					<% } %>
				var mainEventArea = $("#mainEventImgs li").children("img");
				mainEventArea.each(function(idx){
						$(this).attr("src", "/mono/upload/ad/" + mainEventImg[idx]);
				});
		<% } %>
	});
	var test = 0;
	$(function(){
		setInterval(function(){
			$(".pos label").eq(test%4).click();
			test++;
		}, 3000);
		
	})
</script>
</head>
<body>
<%@ include file="views/common/header.jsp" %>
<div class="slide">
	<input type="radio" name="pos" id="pos1" checked>
	<input type="radio" name="pos" id="pos2">
	<input type="radio" name="pos" id="pos3">
	<input type="radio" name="pos" id="pos4">
	<ul id="mainEventImgs" class="img">
		<li><img src="/mono/upload/ad/사진없음.png" width="1100px" height="350px"></li>
		<li><img src="/mono/upload/ad/사진없음.png" width="1100px" height="350px"></li>
		<li><img src="/mono/upload/ad/사진없음.png" width="1100px" height="350px"></li>
		<li><img src="/mono/upload/ad/사진없음.png" width="1100px" height="350px" ></li>
	</ul>
	<p class="pos">
		<label for="pos1"></label>
		<label for="pos2"></label>
		<label for="pos3"></label>
		<label for="pos4"></label>
	</p>
</div>
<div class="request">
	<div class="requestBtn" onclick="auctionPage('auction');">무료견적 신청하기</div>
</div>
<div class="keyword">
	<label style="font-weight:bold">BEST 키워드</label>
	<hr>
	<% if(null == list) { %>
		<script>
			location.href = "/mono/selectKeywordList.do?requestPage=index";
		</script>
	<% } else { %>
		<% for(KeywordVo keyword : list) { %>
				<div class="key" onclick="searchPartner('<%=keyword.getKeyword_content() %>');"><%=keyword.getKeyword_content() %></div>
		<% } %>
	<% } %>
</div>
<div class="board">
	<div class="board1" onclick="boardPage('FRE');"><img src="/mono/images/자유게시판.png"></div>
	<div class="board2" onclick="boardPage('SHO');"><img src="/mono/images/마이룸.png"></div>
	<div class="board3" onclick="boardPage('MAR');"><img src="/mono/images/오픈마켓.png"></div>
	<div class="board4" onclick="boardPage('REV');"><img src="/mono/images/후기게시판.png"></div>
</div>
<%@ include file="views/common/footer.jsp" %>
<script type="text/javascript">
	function searchPartner(keyword){
		location.href = "/mono/searchPartnersList.do?condition=ptn_styles&keyword="+keyword;
	}
</script>
</body>
</html>