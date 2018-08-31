<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	RequestVo req = (RequestVo)request.getAttribute("req");
	int currentPage = (Integer)request.getAttribute("currentPage");
	String pageL = request.getParameter("page");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>견적 상세 페이지</title>
<script type="text/javascript" src="/mono/js/jquery-3.3.1.min.js"></script>
<style>
	.basic_info,.elec,.inner,.photo_outer,.selected,.price_outer,.requestMsg,.requestDate,.ptnPay{
		width:1100px;
		margin-top:11px;
		margin-left:auto;
		margin-right:auto;
	}
	.basic_info_inner{
		border:1px solid black;		
		height:332px;
	}
	.title{
		font-size:27px;
		font-family: 'Do Hyeon', sans-serif;
	}
	.title_inner{
		background:gray;
		color:white;
		width:30px;
		margin-left:10px;
		margin-top:11px;
		font-size:20px;
		padding:8px;
		text-align:center;
		float:left;
	}
	.title_inner2{
		background:gray;
		color:white;
		width:100px;
		margin-left:10px;
		margin-top:8px;
		font-size:20px;
		padding:8px;
		text-align:center;
		float:left;
	}
	.address{
		width:1000px;
		height:95px;
		margin-top:10px;
		margin-left:10px;
		display:inline-block;
		color:black;
	}
	.acreage{
		margin-top:40px;
		margin-left:70px;
		height:68px;
	}
	#acreage{
		font-size:20px;
		text-align:center;
	}
	.veranda{
		color:black;
		margin-top:40px;
		margin-left:70px;
		font-size:21px;
	}
	.elec_inner,.selected_inner{
		border:1px solid black;
		height:90px;
		padding:10px;
		font-size:21px;
	}
	.photo_inner{
		border:1px solid black;
		height:300px;
		padding:10px;	
	}
	.photo{
		display:inline-block;
		margin-left:8px;
	}
	.inner_inner{
		border:1px solid black;
		height:541px;
		padding:10px;
		font-size:21px;
	}
	.select{
		font-size:20px;
		padding:8px;
		margin-left:10px;
		margin-top:10px;
		display:inline-block;
	}
	#select{
		font-weight:bold;
	}
	.reqPrice{
		font-size:25px;
		font-weight:bold;
		color:orangered;
	}
	
	
	
	#zipcode,#address1,#address2{
		font-size:19px;
	}
	#zipcode{
		float:left;
	}
	.table{
		margin-left:4px;
		font-size:18px;
	}
	.wrap{
		display:inline-block;
	}
	.message{
		color:white;
		font-weight:bold;
		font-size:22px;
		margin-top:70px;
		text-align:center;
	}
	.Btns{
		width:1100px;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
		text-align:center;
	}
	.Btn{
		background:rgb(48, 49, 56);
		color:white;
		font-weight:bold;
		font-size:30px;
		text-align:center;
		width:300px;
		padding:15px;
		cursor:pointer;
		font-family:'Gothic A1', sans-serif;
		display:inline-block;
		
	}
	#requestDate{
		font-size:20px;
		margin-left:10px;
		text-align:center;
	}
	.auction{
		margin-top:90px;
		margin-left:auto;
		margin-right:auto;
		width:1100px;
		height:115px;
		background:rgba(48, 49, 56,0.8);
		color:white;
		padding:3px;
	}
	.auction label{
		font-family: 'Rubik', sans-serif;
		font-size:20px;
		margin-left:10px;
		padding:10px;
	}
	.ptnPay{
		background:rgba(48, 49, 56,0.8);
		color:white;
		padding-top:20px;
		padding-bottom:20px;
		text-align:center;
	}
	.requestDate_inner{
		display:inline-block;	
		font-size:25px;
		font-weight:bold;
	}
	#ptnPay{
		font-size:20px;
	}
</style>
<script>
	function reqAuction(){
		$("#requestAuctionForm").submit();
	}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<form id="requestAuctionForm" method="post" action="/mono/insertRequestAuction.do" >
	<div class="auction">
		<%if(req.getReqType().equals("경매")){ %>
		<label style="font-size:40px;">AUCTION : 경매</label>
		<%}else{ %>
		<label style="font-size:40px;">CHOICE : 업체지정</label>
		<%} %>
		<hr width="1080px">
		<label><%=member.getMemberName()%>님의 견적페이지 입니다.</label>
	</div>
	<div class="basic_info">
	<input type="hidden" name="ptnCode" value="<%=member.getMemberCode()%>">
	<input type="hidden" name="reqCode" value="<%=req.getReqCode()%>">
	<label class="title">1. 의뢰현장 기본정보 입력</label>
	<div class="basic_info_inner">
		<div class="wrap">
		<div class="title_inner">주<br><br>소</div>
		<div class="address">
			<table class="table">
				<tr>
	               <td>우편번호</td>
	               <td>
	               		<input type="text" name="zipcode" id="zipcode" readonly style="width:150px; height:22px;" readonly>
	               	</td>
	               <td></td>
	            </tr>
	            <tr>
	               <td>주소</td>
	               <td><input type="text" name="address1" id="address1" style="width:500px; height:24px;" readonly></td>
	               <td></td>
	            </tr>
	            <tr>
	               <td>상세 주소</td>
	               <td><input type="text" name="address2" id="address2" style="width:500px; height:24px;" readonly></td>
	               <td></td>
	            </tr>
			</table>
		</div>
		</div>
		<div class="wrap">
		<div class="title_inner">평<br><br>수</div>
		<div class="acreage"><input type="text" id="acreage" name="acreage" value="<%=req.getAcreage()%>" style="width:100px; height:30px;" readonly> 평</div>
		</div>
		<br>
		<div class="wrap">
		<div class="title_inner">베<br>란<br>다</div>
		<div class="veranda">
			<input type="radio" style="width:20px; height:20px;" name="veranda" value="Y" id="verandaBtn1" disabled=false><label for="verandaBtn1">있음</label>  
			<input type="radio" style="width:20px; height:20px;" name="veranda" value="N" id="verandaBtn2" disabled=false><label for="verandaBtn2">없음</label>
		</div>
		</div>
	</div>
</div>
<div class="elec">
	<label class="title">2. 전기조명</label>
	<div class="elec_inner">
		<input type="checkbox" style="width:20px; height:20px;" id="elec1" name="elec" value="배선공사" disabled=false><label for="elec1">배선공사</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec2" name="elec" value="조명,콘센트 이동 및 추가" disabled=false><label for="elec2">조명,콘센트 이동 및 추가</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec3" name="elec" value="벽걸이티비 배선 매립" disabled=false><label for="elec3">벽걸이티비 배선 매립</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec4" name="elec" value="조명기구교체" disabled=false><label for="elec4">조명기구교체</label><br><br>
		<input type="checkbox" style="width:20px; height:20px;" id="elec5" name="elec" value="콘센트/스위치 커버 교체" disabled=false><label for="elec5">콘센트/스위치 커버교체</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec6" name="elec" value="비디오폰" disabled=false><label for="elec6">비디오폰</label>
	</div>
</div>
<div class="inner">
	<label class="title">3. 내부시공항목</label>
	<div class="inner_inner">
		<div class="wrap">
			<div class="title_inner2">바 닥</div>
			<div class="select" id="select">
				<%=req.getFlooring()%>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">도 배</div>
			<div class="select" id="select"><%=req.getPapering()%></div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">도 장</div>
			<div class="select" id="select"><%=req.getCoating()%></div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">천 장</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="ceiling" name="ceiling" value="천장 전체 공사" disabled=false><label for="ceiling">천장 전체 공사</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">중 문</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="middleDoor" name="middleDoor" value="현관 중문" disabled=false><label for="middleDoor">현관 중문</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">창 호</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="window" name="window" value="창호(샤시) 전체 교체" disabled=false><label for="window">창호(샤시) 전체 교체</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">타 일</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="tile1" name="tile" value="현관" disabled=false><label for="tile1">현관</label>
				<input type="checkbox" style="width:20px; height:20px;" id="tile2" name="tile" value="욕실" disabled=false><label for="tile2">욕실</label>
				<input type="checkbox" style="width:20px; height:20px;" id="tile3" name="tile" value="주방 벽면" disabled=false><label for="tile3">주방 벽면</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">욕 실</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="bathroom" name="bathroom" value="가구 및 도기 설치" disabled=false><label for="bathroom">가구 및 도기 설치</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">주 방</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="kitchen" name="kitchen" value="가구 및 싱크대 설치" disabled=false><label for="kitchen">가구 및 싱크대 설치</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">기 타</div>
			<div class="select">
				<input type="checkbox" style="width:20px; height:20px;" id="cleaning" name="cleaning" value="Y" disabled=false><label for="cleaning" >입주 청소</label>
			</div>
		</div>								
	</div>
</div>
<div class="photo_outer">
	<label class="title">4. 희망 인테리어 사진</label>
	<div class="photo_inner">
		<div class="photo">
			<%if(req.getSamplePhotoUrl1() != null){ %>
			<img src="/mono/upload/request_photo/<%=req.getSamplePhotoUrl1()%>" style="width:520px; height:300px;">
			<%} %>
		</div>
		<div class="photo">
			<%if(req.getSamplePhotoUrl2() != null){ %>
			<img src="/mono/upload/request_photo/<%=req.getSamplePhotoUrl2()%>" style="width:520px; height:300px;">
			<%} %>
		</div>
	</div>
</div>
<div class="price_outer">
	<label class="title">6. 희망 견적가</label>
	<div class="reqPrice"><%=req.getReqPrice()%></div>
</div>
<div class="requestMsg">
	<label class="title">7. 요청사항</label><br>
	<div><%=req.getReqContent()%></div>
</div>
<div class="requestDate">
	<label class="title">8. 시공희망날짜 : </label>
	<div class="requestDate_inner"><%=req.getReqDate() != null ? req.getReqDate().substring(0,10) : ""%></div>
</div>
<br>
<%if(member.getMemberRank().equals("일반업체") || member.getMemberRank().equals("우수업체")){%>
<hr width="1100px">
<br>
<div class="ptnPay">
	<label class="title">희망 낙찰 금액 : </label>
	<input type="text" id="ptnPay" name="ptnPay" style="width:200px; height:30px;" placeholder="숫자로만 적어주세요"> 원
</div>
<%} %>
<div class="Btns">
	<div class="Btn" onclick="reqList('<%=currentPage%>','<%=member.getMemberCode()%>');">목록으로</div>
<%if(member.getMemberRank().equals("일반업체") || member.getMemberRank().equals("우수업체")){%>
	<div class="Btn" onclick="reqAuction();">경매 신청하기</div>
	<%} %>
</div>
</form>
<script>

function reqList(pageNum,mCode){
	<%if(pageL != null){%>
		location.href="/mono/selectMyRequestList.do?currentPage=" + pageNum +"&mCode=" + mCode;
	<%}else{ %>		
		location.href="/mono/selectRequestList.do?currentPage=" + pageNum;
	<%}%>	
}

$(function(){	
	var addrArr = '<%=req.getConstAddress()%>'.split('/');
	$("#zipcode").val(addrArr[0]);
	$("#address1").val(addrArr[1]);
	$("#address2").val(addrArr[2]);
	
	$("input:radio").each(function(){
		if($(this).val() == '<%=req.getVeranda()%>'){
			$(this).prop("checked",true);
		}
	});
	
	var elecArr = '<%=req.getElectrics()%>'.split(', ');
	console.log(elecArr);
	$(".elec_inner input[type=checkbox]").each(function(index){
		//if(-1 < $.inArray($(this).val(), elecArr)){
		//	$(this).prop("checked", true);
		//}
		for(var i in elecArr){
			if($(this).val() == elecArr[i]){
				$(this).prop("checked", true);				
				break;
			} 
		}
	});
	
	$("#floor").each(function(){
		if($("#floor").val() == '<%=req.getFlooring()%>'){
			$("#floor").prop("selected",true);
		}
	});
	
	$("#paper").each(function(){
		if($(this).val() == '<%=req.getPapering()%>'){
			$(this).prop("selected",true);
		}
	});
	
	$("#coating").each(function(){
		if($(this).val() == '<%=req.getCoating()%>'){
			$(this).prop("selected",true);
		}
	});
	
	if($("#ceiling").val() == '<%=req.getCeiling()%>'){
		$("#ceiling").prop("checked",true);
	}
	
	if($("#middleDoor").val() == '<%=req.getMiddleDoor()%>'){
		$("#middleDoor").prop("checked",true);
	}
	
	if($("#window").val() == '<%=req.getWindow()%>'){
		$("#window").prop("checked",true);
	}

	var tileArr = '<%=req.getTile()%>'.split(', ');
	$("input[name*=tile]").each(function(index){
		for(var i in tileArr){
			if($(this).val() == tileArr[i]){
				$(this).prop("checked", true);				
				break;
			} 
		}
	});
	
	if($("#bathroom").val() == '<%=req.getBathroom()%>'){
		$("#bathroom").prop("checked",true);
	}
	
	if($("#kitchen").val() == '<%=req.getKitchen()%>'){
		$("#kitchen").prop("checked",true);
	}
	
	if($("#cleaning").val() == '<%=req.getCleaning()%>'){
		$("#cleaning").prop("checked",true);
	}	
});
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>