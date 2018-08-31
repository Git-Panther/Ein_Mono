<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
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
    	text-align: center;
    }
    #aucBtn,#reqBtn{
    	width:150px;
    	height:30px;
    	border:1px solid black;
    	display:inline-block;
    	background:rgba(48, 49, 56,0.8);
    	color:white;
    	font-size:13px;
    	padding:5px;
    }
    #aucBtn:hover,#reqBtn:hover{
    	cursor:pointer;
    }
    .chkBtn{
   	 	width:50%;
    	float:left;    	
    }
    .chkBtn:hover{
    	cursor:pointer;
    }
    </style>
     <script>
		$(function(){
			$("#aucBtn").click(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "lightgray");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
				$("#reqBtn").css("border", "1px solid white");
				$("#reqBtn").css("background", "rgba(48, 49, 56,0.8);");
				$("#reqBtn").css("color", "white");
			});
			$("#reqBtn").click(function(){
				$(this).css("border", "1px solid black");
				$(this).css("background", "lightgray");
				$(this).css("color", "black");
				$(this).css("cursor", "pointer");
				$("#aucBtn").css("border", "1px solid white");
				$("#aucBtn").css("background", "rgba(48, 49, 56,0.8);");
				$("#aucBtn").css("color", "white");
			});
			$(".pageBtn").mouseenter(function(){
				$(this).css("cursor", "pointer");
			});
		});	
	</script>
</head>
<body>
	<header>
		<%@ include file="../common/header.jsp" %>
	</header>
	<div id="jiCenter">
		<div class="jiCenter">
		    <nav id="jiNav" class="jiCenter">
		    	<%@ include file="maside.jsp" %>
		    </nav>
		    <section id="jiSec1" class="jiCenter">
		    	<div class="tableArea">
					<h1>주문 확인</h1>
					<br>
						<div id="aucBtn">경매 참가 주문</div><div id="reqBtn">신청 받은 주문</div>
						<br><br>
						<div id="contents"></div>		    	
		    	</div>
		    </section>
		</div>
	</div>
	<footer>
		<%@ include file="../common/footer.jsp" %>
	</footer>
<script>
	function clickAucBtn(){
		var mCode = "<%=member.getMemberCode()%>";
		$.ajax({
			url : "/mono/orderList.do",
			type : "get",
			data : {mCode : mCode ,reqType : "경매"},
			success : function(data){
				var str = "";
				str = "<table align='center' style='width:800px;'><tr><th>견적명</th><th>평형</th><th>작성자</th><th>낙찰여부</th></tr>"
				if(data.length > 0 ){
					for(i=0; i<data.length; i++){	
						str += "<tr>";
						str += "<td>"+data[i].constAddress+"</td>";
						str += "<td>"+data[i].acreage+"</td>"
						str += "<td>"+data[i].userName+"</td>";							
						if(data[i].reqCheck =="경매중"){
							str +="<td>경매중</td></tr>"
						}else if(data[i].reqCheck =="경매종료"){
							if(data[i].bidCheck=="Y"){
								str +="<td>낙찰</td></tr>"
							}else{
								str +="<td>탈락</td></tr>"
							}
						}
		             }						
				}else{
					str += "<tr><td colspan='4' align='center'>조회된 목록이 없습니다.</tr>";
				}
				 str += "</table>";
				 $("#contents").html(str);
			},error : function(e){
				console.log(e);
			}			
		});
	}

	function clickChkBtn(){
		var text = $(this).text();
		var rCode = $("#rCode").val();
		var mCode = $("#mCode").val();
		 $.ajax({
			 url : "/mono/modifyReqCheck.do",
			 type : "get",
			 data : {rCode : rCode , reqCheck : text ,mCode : mCode},
			 success : function(data){
				 var str = "";
					str = "<table align='center' style='width:800px;'> <tr><th>견적명</th><th>평형</th><th>작성자</th><th>주문현황</th></tr>";
					if(data.length > 0 ){
						for(i=0; i<data.length; i++){	
							str += "<tr><td>"+data[i].constAddress+"</td>";
							str += "<td>"+data[i].acreage+"</td>"
							str += "<td>"+data[i].userName+"</td>";
							str += "<td>"+data[i].reqCheck+"</td> </tr>";	
							console.log(data[i].reqCheck);
			             }						
					}else{
						str += "<tr><td colspan='4' align='center'>조회된 목록이 없습니다.</tr>";
					}
					 str += "</table>";
					 $("#contents").html(str);
			 },error : function(e){
				 console.log(e);
			 }
		 });
	}
	
	function clickReqBtn(){
		var mCode = "<%=member.getMemberCode()%>";
		$.ajax({
			url : "/mono/orderList.do",
			type : "get",
			data : {mCode : mCode ,reqType : "업체지정"},
			success : function(data){
				var str = "";
				str = "<table align='center' style='width:800px;'> <tr><th>견적명</th><th>평형</th><th>작성자</th><th>주문현황</th></tr>";
				if(data.length > 0 ){
					for(i=0; i<data.length; i++){	
						str += "<tr><td>"+data[i].constAddress+"</td>";
						str += "<td>"+data[i].acreage+"</td>"
						str += "<td>"+data[i].userName+"</td>";
						if( data[i].reqCheck == "승인대기"){
							str += "<input type = 'hidden' value='"+data[i].reqCode+"' id='rCode'>"
							str += "<input type = 'hidden' value='<%=member.getMemberCode()%>' id='mCode'>"
							str += "<td class='btnTd' ><div class='chkBtn'>수락</div><div class='chkBtn'>거절</div></td></tr>"
						}else{
							str += "<td>"+data[i].reqCheck+"</td></tr>";
							console.log(data[i].reqCheck);
						}
		             }						
				}else{
					str += "<tr><td colspan='4' align='center'>조회된 목록이 없습니다.</tr>";
				}
				 str += "</table>";
				 $("#contents").html(str);
				 $(".chkBtn").each(function(i){
					 $(".chkBtn").eq(i).click(clickChkBtn);
				 });					
			},error : function(e){
				console.log(e);
			}		
		});
	}
	
	$(function(){
		$("#aucBtn").click(clickAucBtn);
		$("#reqBtn").click(clickReqBtn);		
	});
</script>

</body>
</html>