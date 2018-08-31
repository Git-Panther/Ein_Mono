<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 작성 화면</title>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

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
    	padding:3px;
    	border-bottom:1px solid black;
    }

	 .cancelInsertBtn{
    	margin-top:80px;
    }
    .cancelInsertBtn{
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
			$(".cancelInsertBtn").mouseenter(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "white");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
			}).mouseout(function(){
				$(this).css("border", "1px solid white");
				$(this).css("background", "rgba(48, 49, 56,0.8)");
				$(this).css("color", "white");
			});
			$(".cancelInsertBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
			
			$("#startDate, #endDate").datepicker({
		         changeMonth: true, 
		         changeYear: true,
		         dateFormat:'yymmdd',
		         dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		         dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
		         monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		         monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		  	});
			
			
		});	
		
		function cancel(){
			location.href = "/mono/selectADListAdmin.do";
		}
		function insertEvent(){
			$("#eventForm").submit();
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
					<form id="eventForm" method="post" action="/mono/insertEvent.do" enctype="multipart/form-data">
			    		<%-- <% if(null != member && null != list) { %> --%>
						<table align="center" style="width:800px;">
							<tr>
								<th width="100">게시글분류</th>
								<td width="50" style="border-top:1px solid black;">ADV</td>
								<th width="100">업체명</th>
								<td width="100" style="border-top:1px solid black;">
									<input type="text" id="ptn_name" name="ptn_name"/>
								</td>
								<th width="100">기간</th>
								<td width="300" style="border-top:1px solid black;">
									<input type="text" id="startDate" name="startDate" style="width:70px; height:15px;">
									 - <input type="text" id="endDate" name="endDate" style="width:70px; height:15px;">
								</td>
								<th width="100">작성자</th>
								<td width="100" style="border-top:1px solid black;"><%=member.getMemberName() %></td>
							</tr>
							<tr>
								<th width="100">제목</th>
								<td colspan="7">
									<input type="text"  id="title" name="title"  size="95" />
								</td>
							</tr>
							<tr>
								<th colspan="8">내용</th>
							</tr>
							<tr>
								<td colspan="8">
									<textarea id="content" name="content" cols="110" rows="15"></textarea>
									<%-- <input type="text"  id="content" name="content" value="<%= post.getContent() %>"/> --%>
								</td>
							</tr>
							<tr>
								<th width="100">배너이미지</th>
								<td colspan="3">
									<input type="file" id="eventImg1" name="eventImg1"/>
								</td>	
								<th width="150">상세이미지</th>
								<td colspan="3">
									<input type="file" id="eventImg2" name="eventImg2"/>
								</td>
							</tr>
						</table>
					</form>
					<%-- <% } %> --%>
				</div>	
		        <!-- <article id="jiArticle1">article</article> -->
		    </section>
			<div class="pageArea" align="center">
				<div class="cancelInsertBtn" id="cancelBtn" onclick="cancel();">취소</div>
				<div class="cancelInsertBtn" id="insertBtn" onclick="insertEvent();">작성</div>
			</div> 
	    </div>
    </div>
<!-- <aside>aside</aside> -->
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
</body>
</html>