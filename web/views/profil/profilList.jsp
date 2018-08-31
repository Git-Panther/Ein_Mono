<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>업체회원 전체리스트 조회</title>
<%@ include file="../common/header.jsp" %>
<style>
	.outer{
		border:1px solid red;
		width:800px;
		height:500px;
		margin-left:auto;
		margin-right:auto;
		margin-top:100px;
	}
	
	.thumnailArea{
	border:1px solid blue;
	width:150px;
	height:150px;
	margin-left:auto;
	margin-right:auto;
	margin-top:-20px;
	display:inline-block;
	}
</style>
<script type="text/javascript" src = "/mwp/js/jquery-3.3.1.min.js"></script>
</head>
<body>
	
	<div class = "outer">
		<h2 align = "center">업체리스트</h2>
		<div class = "thumnailArea">
			<div>
					<img src = "업체로고이미지경로" />
			</div>		
					<p>업체이름</p>	
							
					</div>		
					
							<div class = "thumnailArea">
			<div>
					<img src = "업체로고이미지경로" />
			</div>		
					<p>업체이름2</p>	
							
					</div>	
			
			
			<div class = "searchArea" heigth = "200px" height = "100px" border = "1px solid black">
			
				<div>
				</div>
			</div>
	</div>
	
	
	
<%@ include file="../common/footer.jsp" %>
</body>
</html>