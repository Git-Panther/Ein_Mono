<%@page import="ein.mono.request.model.vo.RequestVo"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ptnCode = request.getParameter("ptnCode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>경매</title>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
	.basic_info,.elec,.inner,.photo_outer,.selected,.price_outer,.requestMsg,.requestDate{
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
		margin-top:10px;
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
		font-size:20px;
	}
	.veranda{
		color:black;
		margin-top:40px;
		margin-left:70px;
		font-size:21px;
	}
	.elec_inner,.photo_inner,.selected_inner{
		border:1px solid black;
		height:90px;
		padding:10px;
		font-size:21px;
	}
	.inner_inner{
		border:1px solid black;
		height:530px;
		padding:10px;
		font-size:21px;
	}
	.floor,.paper,.coating{
		margin-left:10px;
		margin-top:10px;
		font-size:20px;
		display:inline-block;
	}
	.inner_checkbox{
		margin-left:10px;
		margin-top:18px;
		font-size:20px;
		display:inline-block;
	}
	select{
		width:500px;
		height:40px;
		font-size:20px;
	}
	#searchAddressBtn{
		cursor:pointer;
		margin-left:170px;
		font-weight:bold;
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
	.photo{
		font-size:20px;
		padding:6px;
	}
	.price{
		margin-left:10px;
	}
	.message{
		color:white;
		font-weight:bold;
		font-size:22px;
		margin-top:70px;
		text-align:center;
	}
	.requestBtn{
		margin-left:auto;
		margin-right:auto;
		margin-top:10px;
		background:rgb(48, 49, 56);
		color:white;
		font-weight:bold;
		font-size:50px;
		text-align:center;
		width:500px;
		padding:20px;
		cursor:pointer;
		font-family:'Gothic A1', sans-serif;
	}
	textarea{
		font-size:20px;
	}
	#innerConst{
		color:white;
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
		height:145px;
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
</style>
<script>
function openAddressPopup(){
    new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;
                	console.log(fullAddr);

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                	console.log(fullAddr);
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== '' && /[동|로|가|]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }
                
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
               //data.zonecode; //5자리 새우편번호 사용
                  //$("#zipcode").val(data.zonecode);
               		document.getElementById("zipcode").value = data.zonecode;
               //$("#address1").val(fullAddr);
               		document.getElementById("address1").value = fullAddr;
                // 커서를 상세주소 필드로 이동한다.
                $("#address1").focus();
            }
        }).open();
}
$(function(){
	$("#verandaBtn1").click(setText);
	$("#verandaBtn2").click(setText);
	$("#elec1").click(setText);
	$("#elec2").click(setText);
	$("#elec3").click(setText);
	$("#elec4").click(setText);
	$("#elec5").click(setText);
	$("#elec6").click(setText);
	
	$("#floor").change(setText);
	$("#paper").change(setText);
	$("#coating").change(setText);
	
	$("#ceiling").click(setText);
	$("#middleDoor").click(setText);
	$("#window").click(setText);
	$("#tile1").click(setText);
	$("#tile2").click(setText);
	$("#tile3").click(setText);
	$("#bathroom").click(setText);
	$("#kitchen").click(setText);
	$("#cleaning").click(setText);
});
function setText(){

	var array = new Array();
	var array2 = new Array();
	if($("#verandaBtn1").prop("checked")){
		array.push("베란다O");
	}else{
		array.push("베란다X");
	}
	
	if($("#elec1").prop("checked")){
		array.push($("#elec1").val());
		array2.push($("#elec1").val());
	}
	if($("#elec2").prop("checked")){
		array.push($("#elec2").val());
	}
	if($("#elec3").prop("checked")){
		array.push($("#elec3").val());
	}
	if($("#elec4").prop("checked")){
		array.push($("#elec4").val());
	}
	if($("#elec5").prop("checked")){
		array.push($("#elec5").val());
	}
	if($("#elec6").prop("checked")){
		array.push($("#elec6").val());
	}
	
	if($("#floor").val() != ""){
	array.push("바닥재 : " +($("#floor").val()));
	}
	
	if($("#paper").val() != ""){
	array.push("도배지 : "+($("#paper").val()));
	}
	
	if($("#coating").val() != ""){
	array.push("도장재 : "+($("#coating").val()));
	}
	
	if($("#ceiling").prop("checked")){
		array.push($("#ceiling").val());
	}
	
	if($("#middleDoor").prop("checked")){
		array.push($("#middleDoor").val());
	}
	
	if($("#window").prop("checked")){
		array.push($("#window").val());
	}
	
	if($("#tile1").prop("checked")){
		array.push(($("#tile1").val())+" 타일");
	}
	if($("#tile2").prop("checked")){
		array.push(($("#tile2").val())+" 타일");
	}
	if($("#tile3").prop("checked")){
		array.push(($("#tile3").val())+" 타일");
	}
	
	if($("#bathroom").prop("checked")){
		array.push($("#bathroom").val());
	}
	
	if($("#kitchen").prop("checked")){
		array.push($("#kitchen").val());
	}
	
	if($("#cleaning").prop("checked")){
		array.push($("#cleaning").val());
	}

	$("#selected_inner").text(array.join(", "));
}

$(function() {
    $( "#requestDate" ).datepicker({
         changeMonth: true, 
         changeYear: true,
         dateFormat:'yymmdd',
         dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
         dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
         monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
         monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
  });
});

function auction(){
	var str = $("#selected_inner").text();
	var subStr = str.split(",");
	console.log(subStr);
	for(var i=0;i<subStr.length;i++){
		if(i < 3){
			document.getElementById("message").style.color="red";
		}else{
			document.getElementById("message").style.color="white";
			$("#requestForm").submit();
		}
	}	
}
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<form id="requestForm" method="post" action="/mono/insertRequest.do" enctype="multipart/form-data" >
	<div class="auction">
		<%if(request.getParameter("page").equals("auction")){ %>
		<label style="font-size:40px;">AUCTION : 경매</label>
		<input type="hidden" name="ptnCode" value="">
		<input type="hidden" name="reqType" value="경매">
		<%}else{ %>
		<label style="font-size:40px;">CHOICE : 업체지정</label>
		<input type="hidden" name="ptnCode" value="<%=ptnCode%>">
		<input type="hidden" name="reqType" value="업체지정">
		<%} %>
		<hr width="1080px">
		<label>고객님의 소중한 공간을 만들기 위한 견적서 작성 페이지입니다.<br></label>
		<label>최소 3개 이상의 항목을 선택해 주세요.</label>
	</div>
	<div class="basic_info">
	<input type="hidden" name="userCode" value="<%=member.getMemberCode()%>">
	<label class="title">1. 의뢰현장 기본정보 입력</label>
	<div class="basic_info_inner">
		<div class="wrap">
		<div class="title_inner">주<br><br>소</div>
		<div class="address">
			<table class="table">
				<tr>
	               <td>우편번호</td>
	               <td>
	               		<input type="text" name="zipcode" id="zipcode" readonly style="width:150px; height:22px;">
	               		<div id="searchAddressBtn" onclick="openAddressPopup();">주소검색</div>
	               	</td>
	               <td></td>
	            </tr>
	            <tr>
	               <td>주소</td>
	               <td><input type="text" name="address1" id="address1" style="width:500px; height:24px;"></td>
	               <td></td>
	            </tr>
	            <tr>
	               <td>상세 주소</td>
	               <td><input type="text" name="address2" id="address2" style="width:500px; height:24px;"></td>
	               <td></td>
	            </tr>
			</table>
		</div>
		</div>
		<div class="wrap">
		<div class="title_inner">평<br><br>수</div>
		<div class="acreage"><input type="text" id="acreage" name="acreage" style="width:100px; height:30px;"> 평</div>
		</div>
		<br>
		<div class="wrap">
		<div class="title_inner">베<br>란<br>다</div>
		<div class="veranda">
			<input type="radio" style="width:20px; height:20px;" name="veranda" value="Y" id="verandaBtn1"><label for="verandaBtn1">있음</label>  
			<input type="radio" style="width:20px; height:20px;" name="veranda" value="N" id="verandaBtn2" checked="checked"><label for="verandaBtn2">없음</label>
		</div>
		</div>
	</div>
</div>
<div class="elec">
	<label class="title">2. 전기조명</label>
	<div class="elec_inner">
		<input type="checkbox" style="width:20px; height:20px;" id="elec1" name="elec" value="배선공사"><label for="elec1">배선공사</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec2" name="elec" value="조명,콘센트 이동 및 추가"><label for="elec2">조명,콘센트 이동 및 추가</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec3" name="elec" value="벽걸이티비 배선 매립"><label for="elec3">벽걸이티비 배선 매립</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec4" name="elec" value="조명기구교체"><label for="elec4">조명기구교체</label><br><br>
		<input type="checkbox" style="width:20px; height:20px;" id="elec5" name="elec" value="콘센트/스위치 커버 교체"><label for="elec5">콘센트/스위치 커버교체</label>
		<input type="checkbox" style="width:20px; height:20px;" id="elec6" name="elec" value="비디오폰"><label for="elec6">비디오폰</label>
	</div>
</div>
<div class="inner">
	<label class="title">3. 내부시공항목</label>
	<div class="inner_inner">
		<div class="wrap">
			<div class="title_inner2">바 닥</div>
			<div class="floor">
				<select name="floor"  id="floor">
					<option value='선택안함'>바닥재 선택</option>
					<option value="강마루" id="floor1">강마루</option>
					<option value="헤링본" id="floor2">헤링본</option>
					<option value="강마루+헤링본" id="floor3">강마루+헤링본</option>
					<option value="장판" id="floor4">장판</option>
				</select>
			</div>
		</div>
		<div class="wrap">
			<div class="title_inner2">도 배</div>
			<div class="paper">
				<select name="paper" id="paper">
					<option value='선택안함' selected>도배지 선택</option>
					<option value="합지" id="paper1">합지</option>
					<option value="실크" id="paper2">실크</option>
					<option value="천연벽지" id="paper3">천연벽지</option>
				</select>
			</div>
		</div>
		<div class="wrap">
			<div class="title_inner2">도 장</div>
			<div class="coating">
				<select name="coating" id="coating">
					<option value='선택안함'>도장재 선택</option>
					<option value="탄성코트" id="coating1">탄성코트</option>
					<option value="수성코트" id="coating2">수성코트</option>
				</select>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">천 장</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="ceiling" name="ceiling" value="천장 전체 공사"><label for="ceiling">천장 전체 공사</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">중 문</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="middleDoor" name="middleDoor" value="현관 중문"><label for="middleDoor">현관 중문</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">창 호</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="window" name="window" value="창호(샤시) 전체 교체"><label for="window">창호(샤시) 전체 교체</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">타 일</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="tile1" name="tile" value="현관"><label for="tile1">현관</label>
				<input type="checkbox" style="width:20px; height:20px;" id="tile2" name="tile" value="욕실"><label for="tile2">욕실</label>
				<input type="checkbox" style="width:20px; height:20px;" id="tile3" name="tile" value="주방 벽면"><label for="tile3">주방 벽면</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">욕 실</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="bathroom" name="bathroom" value="가구 및 도기 설치"><label for="bathroom">가구 및 도기 설치</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">주 방</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="kitchen" name="kitchen" value="가구 및 싱크대 설치"><label for="kitchen">가구 및 싱크대 설치</label>
			</div>
		</div>
		<br>
		<div class="wrap">
			<div class="title_inner2">기 타</div>
			<div class="inner_checkbox">
				<input type="checkbox" style="width:20px; height:20px;" id="cleaning" name="cleaning" value="Y"><label for="cleaning">입주 청소</label>
			</div>
		</div>								
	</div>
</div>
<div class="photo_outer">
	<label class="title">4. 희망 인테리어 사진</label>
	<div class="photo_inner">
		<input type="file" class="photo" name="photo1"><br>
		<input type="file" class="photo" name="photo2">
	</div>
</div>
<div class="selected">
	<label class="title">5. 선택한 시공 항목</label>
	<div class="selected_inner" id="selected_inner"></div>
</div>
<input type="hidden" id="innerConst" name="innerConst" >
<div class="price_outer">
	<label class="title">6. 희망 견적가</label>
		<select name="price" class="price">
			<option value='50만원 이하'>100만원 이하</option>
			<option value="50만원 이상~100만원 이하">50만원 이상~100만원 이하</option>
			<option value="100만원 이상~200만원 이하">100만원 이상~200만원 이하</option>
			<option value='200만원 이상'>200만원 이상</option>
		</select>
</div>
<div class="requestMsg">
	<label class="title">7. 요청사항</label><br>
	<textarea cols="100" rows="5" style="resize:none;" name="requestMsg" placeholder="500자이내로 적어주세요."></textarea>
</div>
<%if(!"request".equals(request.getParameter("page"))){%>
<div class="requestDate">
	<label class="title">8. 시공희망날짜</label>
	<input type="text" id="requestDate" name="requestDate"style="width:110px; height:30px;">
</div>
<%}%>
<div class="message" id="message">3개 이상의 항목을 선택해 주세요.</div>
<div class="requestBtn" onclick="auction();">경매 신청하기</div>
</form>
<%@ include file="../common/footer.jsp" %>
</body>
</html>