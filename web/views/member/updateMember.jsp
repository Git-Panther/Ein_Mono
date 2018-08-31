<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>개인정보수정</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
 <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<style>
	#memberOutBtn, #saveBtn, #returnPage, #searchAddressBtn{
			display:inline-block;

			cursor:pointer;
			border:none;
		margin-left:auto;
		margin-right:auto;
		
		border-radius: 5px;
	width:auto;
	height:auto;
	border:1px solid white;
		}
		
		.outer{

		 border:2px solid #0d0d26; 
		 height:470px;
		width:500px; 
		margin-left:auto;
		margin-right:auto;
		border-radius: 5px;
	}
	
	#memberOutBtn:hover, #saveBtn:hover, #returnPage:hover, #searchAddressBtn:hover{
	background:#0d0d26;
		color:white;
		transition:0.5s;
	}
	

</style>

<script>
function save(){
	
	$("#memberUpdateForm").submit();
}

function memberOut(){
	
	var check = confirm("정말로 탈퇴하시겠습니까???");
	if(check == true){
	 location.href = "/mono/deleteMember.do"; 
	}

}

function returnPage(){

	location.href = "../../index.jsp"; 
}

function validate(){
	
	var check_pwd = /^[a-zA-Z0-9]{6,20}$/;
	var check_tel =  /^[0-9]{2,4}$/;
	var check_nickname = /^[가-힣a-zA-Z]{2,20}$/;
	
	if($("#memberPwd").val() != $("#memberPwd2").val()){
		  alert("비밀번호가 일치하지 않습니다");
		$("#memberPwd2").focus();
		return false;
	}else if(!check_pwd.test($("#memberPwd").val())){
		alert("비밀번호는 영문자6~20글자로 작성해주세요");
		$("#memberPwd").focus();
		return false;
		
	}else if(!check_nickname.test($("#memberNickname").val())){
		 alert("닉네임은 한글과영어로2~16글자로 작성해주세요");
			$("#memberNickname").focus();
			return false;
			
	}else
		
		if(!check_tel.test($("#tel1").val())){
			alert("전화번호를 확인하세요");		
		return false;
	}else if(!check_tel.test($("#tel2").val())){
		alert("전화번호를 확인하세요");		
		return false;
	}else if(!check_tel.test($("#tel3").val())){
		alert("전화번호를 확인하세요");		
		return false;
	}
	
	
	
	return true;
}

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

              } else { // 사용자가 지번 주소를 선택했을 경우(J)
                  fullAddr = data.jibunAddress;
              }

              // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
              if(data.userSelectedType === 'R'){
                  //법정동명이 있을 경우 추가한다.
                  if(data.bname !== ''){
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
              //document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
               $("#addressNum").val(data.zonecode); 
             // document.getElementById('sample6_address').value = fullAddr;
             	 $("#address1").val(fullAddr); 

              // 커서를 상세주소 필드로 이동한다.
             // document.getElementById('sample6_address2').focus();
             	 $("#address2").focus();
          }
      }).open();
} 


</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<br>
<br>
<br>
<br>
<br>
<div class = "outer" align = "center">
<h2 align = "center">회원정보수정</h2>
	<form id = "memberUpdateForm" method = "post" action = "/mono/updateMember.au" onsubmit = "return validate();">
		<table>

			<tr>
				<td align = "center">회원구분</td>
				<td>
					
					 <label for = "CUS">일반회원</label>
					 <input type = "radio" id = "user1"  name = "memberType"  value = "1"  disabled = "true" />
					<label for = "PTN">업체회원</label> 
					<input type = "radio" id = "user2"  name = "memberType"  value = "2"  disabled = "true"/> 
					
				</td>
			</tr>
			<tr>
				<td align = "center">아이디</td>
				<td align = "center"><input type = "text" name = "memberId" id = "memberId" value = "<%=member.getMemberId() %>" readonly/></td>
				<!-- <td><button>중복확인</button></td> -->
			</tr>
			<tr>
				<td align = "center">비밀번호</td>
				<td align = "center"><input type = "password" name = "memberPwd" id = "memberPwd" required/></td>
				<td></td>
			</tr>
			<tr>
				<td align = "center">비밀번호확인</td>
				<td align = "center"><input type = "password" name = "memberPwd2" id = "memberPwd2" required/></td>
				<td><span id = "pwdCheckSpan"></span></td>
			</tr>
			<tr>
				<td align = "center">이름</td>
				<td align = "center"><input type = "text" name = "memberName" id = "memberName" value = "<%=member.getMemberName() %>" readonly/></td>
				<td></td>
			</tr>
			<tr>
				<td>닉네임(업체이름)</td>
				<td align = "center"><input type = "text" name = "memberNickname" id = "memberNickname" value = "<%=member.getMemberNname() %>" required/></td>
				<td></td>
			</tr>
			<tr>
					<td align = "center">우편번호</td>
					<td align = "center"><input type = "text" name = "addressNum" id = "addressNum"/></td>
					<td><div id = "searchAddressBtn" onclick = "openAddressPopup();">주소 검색</div></td>
				</tr>
				<tr>
					<td align = "center">주소</td>
					<td align = "center"><input type = "text" name = "address1" id = "address1"/></td>
					<td></td>
				</tr>
				<tr>
					<td align = "center">상세주소</td>
					<td align = "center"><input type = "text" name = "address2" id = "address2"/></td>
					<td></td>
				</tr>
				<tr>
					<td align = "center">이메일</td>
					<td align = "center"><input type = "email"name = "email" value = <%=member.getMemberEmail() %> readonly/></td>
					<td></td>
				</tr>
				<tr>
					<td align = "center">전화번호</td>
					<td>
						<input type = "text" name = "tel1" id = "tel1" size = "3" />-
						<input type = "text" name = "tel2" id = "tel2" size = "3"/>-
						<input type = "text" name = "tel3" id = "tel3" size = "3"/>
					</td>
					<td></td>
				</tr>
		</table>
		<br>
		<br>
	<div class = "btns" align = "center">
		<div id = "saveBtn" onclick = "save();">저장하기</div>&emsp;
		<div id = "memberOutBtn" onclick = "memberOut();">탈퇴하기</div>&emsp;
		<div id = "returnPage" onclick = "returnPage();">돌아가기</div>
	</div>
		</form>
		<br>
	</div>
	
		<script>
	
	
	<%-- $(function(){

	 	var addrArr = '<%=member.getMemberAddress()%>'.split(', ');
		$("#addressNum").val(addrArr[0]);			
		$("#address1").val(addrArr[1]);			
		$("#address2").val(addrArr[2]);	
		
		var tels = '<%=member.getMemberTel()%>'.split('-');
		$("#tel1").val(tels[0]);
		$("#tel2").val(tels[1]);
		$("#tel3").val(tels[2]);  
	});   --%>
	
	$(function(){
		
		
		
		 var mType = "<%=member.getMemberCode()%>".substr(0,1);
			if(mType == "B"){
				$("#user1").prop("checked", true);
			}else{
				$("#user2").prop("checked", true);
			}
		
			
		
		var addrArr = '<%=member.getMemberAddress()%>'.split(', ');
		$("#addressNum").val(addrArr[0]);			
		$("#address1").val(addrArr[1]);			
		$("#address2").val(addrArr[2]);	
		
		var tels = '<%=member.getMemberTel()%>'.split('-');
		$("#tel1").val(tels[0]);
		$("#tel2").val(tels[1]);
		$("#tel3").val(tels[2]);  
		
		
	});
	
	
	
	
	</script>

<%@ include file="../common/footer.jsp" %>
</body>
</html>