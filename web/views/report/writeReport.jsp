<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>신고작성</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
<script>
	function okBtn(){
		
		$("#reportForm").submit();
	}
	
	function returnPage(){
		window.close();
	}
	
		/* function validate(){
			var content = $("#textarea").val();
			if(null == content){
				alert("신고내용을 확인해주세요");
				console.log("dd");
				return false;
			}
			return true;
		} */
	
		function validate(){
			
			var content = $("#textarea").val();
			
			if("" == content){
				alert("신고내용을 확인해주세요");
				return false;
			}
			return true;
		}
	
	
	
</script>
<style>
	.outer{
		border:2px solid #0d0d26;
		width:400px;
		height:500px;
		margin-left:auto;
		margin-right:auto;
		
		border-radius: 5px;
	}
	
	.inner{
	
	width:400px;
	height:400px;
	margin-left:auto;
	margin-right:auto;
	margin-top:-20px;
	
	
	}
	#textarea{
	resize:none;
	}
	
	.btn{
	display:inline-block;
	margin:8px;
	border-radius: 5px;
	width:auto;
	height:auto;
	border:1px solid white;
	cursor:pointer;
	}
	
	.btn:hover{
	background:#0d0d26;
		color:white;
		transition:0.5s;
	}
</style>
</head>
<body>

<div class = "outer">
<form id = "reportForm" method = "post" action = "/mono/insertReport.do" onsubmit = "return validate();">
	<h2 align = "center">신고하기</h2>
	<div class = "inner" align = "center">
	<br>
	신고사유
		<select name="reportReason1" >
  			<option value="(사기)">사기</option>
  			<option value="(광고)">광고</option>
  			<option value="(욕설)">욕설</option>
  			<option value="(저작권침해)">저작권침해</option>
  			<option value="(게시판에목적에맞지않음)" >게시판의 목적에 맞지않음</option>
</select>
  		<p>신고내용</p>
  		<textarea id = "textarea" name = "reportReason2" rows="18" cols="50" ></textarea><br>
  		
  		<div class = "btn" onclick = "okBtn();">확인</div>
  		<div class = "btn" onclick = "returnPage();">닫기</div>
  		
	</div>
	</form>
</div>

</body>
</html>