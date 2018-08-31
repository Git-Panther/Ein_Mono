<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberVo m = new MemberVo();
	if(null != (MemberVo) request.getSession().getAttribute("member")) {
		m = (MemberVo) request.getSession().getAttribute("member");
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>회원가입</title>
<script type="text/javascript" src = "/mono/js/jquery-3.3.1.min.js"></script>
 <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script> 
<style>
	#returnMain, #joinMenuBtn, #checkIdBtn, #searchAddressBtn, #emailCheckBtn{
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
		 height:500px;
		width:550px; 
		margin-left:auto;
		margin-right:auto;
		border-radius: 5px;
	}
	

	
	#returnMain:hover, #joinMenuBtn:hover,#checkIdBtn:hover, #searchAddressBtn:hover, #emailCheckBtn:hover{
		background:#0d0d26;
		color:white;
		transition:0.5s;
	}
	
	

</style>
<script>
	function returnMainPage(){
		location.href = "../../index.jsp"; 
	}
	
	function MemberJoin(){
		
		$("#memberJoinForm").submit();
		
	}

	function validate(){
		
		var  inputId = $("#memberId").val();
		var inputRadio = $('input:radio[name=memberType]').is(':checked');
		
		
		var check_id = /^[a-z0-9_-]{6,16}$/;
		var check_pwd = /^[a-zA-Z0-9]{6,20}$/;
		var check_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		var check_name = /^[가-힣]{2,4}$/;
		var check_nickname = /^[가-힣a-zA-Z]{2,20}$/;
		var check_tel =  /^[0-9]{2,4}$/;
		
		
		
		if(inputRadio == false){
			alert("회원타입을 체크하세요");
			
			return false;
		}
		
		 if(inputId == ""){
			 alert("아이디를 입력하세요");
			$("#memberId").focus();
			return false;
		}else if(!check_id.test($("#memberId").val())){
			alert("아이디는 소문자와숫자를 이용해 6~16글자로 작성해주세요");
			$("#memberId").focus();
			return false;
		}else 
		 
		  if($("#memberPwd").val() != $("#memberPwd2").val()){
			  alert("비밀번호가 일치하지 않습니다");
			$("#memberPwd2").focus();
			return false;
		}else if(!check_pwd.test($("#memberPwd").val())){
			alert("비밀번호는 영문자6~20글자로 작성해주세요");
			$("#memberPwd").focus();
			return false;
		 }else
			
		 if(!check_name.test($("#memberName").val())){
			 alert("이름은 한글로2~4글자로 작성해주세요");
			$("#memberName").focus();
			return false;
		 }else
		 
		 
		 if(!check_nickname.test($("#memberNickname").val())){
			 alert("닉네임은 한글과영어로2~16글자로 작성해주세요");
			$("#memberNickname").focus();
			return false;
		}else  if(($("#addressNum").val() == ""||$("#address1").val() == ""||$("#address2").val() == "")){
			 alert("주소를 확인해주세요");
				$("#address1").focus();
				return false;
		}else 
			
			
			if(!check_email.test($("#memberEmail").val())){
				alert("이메일양식에 맞게 작성해주세요");
				$("#memberEmail").focus();
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



	function checkOnly(chk){ //체크박스 1개만 선택하기
	    var obj = document.getElementsByName("memberType");
	    for(var i=0; i<obj.length; i++){
	        if(obj[i] != chk){
	            obj[i].checked = false;
	        }
	    }
	    
	    if($("#user1").is(":checked") == true){
	    	$("#licenceArea").hide();
	    }else{
	    	$("#licenceArea").show();
	    }
	    
	}
	

	
	
	function checkIdBtn(){
		var inputId = $("#memberId").val();
		var check_id = /^[a-z0-9_-]{6,16}$/;

		$.ajax({
			url:"/mono/idCheck.do",
			type:"post",
			data:{memberId:inputId},
			success:function(data){
				if(data == "사용가능합니다" && check_id.test($("#memberId").val())){
					
				alert("사용가능합니다");
				}else{
					alert("존재하거나 사용이불가능한 아이디입니다");
					$("#memberId").focus();
				}
			},error:function(e){
				console.log(e);
			}
			
		});
	}
	
	
	$(function(){
		var obj = $("input:radio");/* document.getElementsByName("memberType"); */
		console.log(obj);
		if(obj.eq(0).attr("checked")) {
			$("#licenceArea").hide();
		} else {
			$("#licenceArea").show();
		}
	   /*  for(var i=0; i<obj.length; i++){ */
	       /*  if(obj[0].prop("checked")){
		    	$("#licenceArea").hide();
	        	
	        } else {
		    	$("#licenceArea").show();
	        } */
	            /* obj[i].checked = false; */
	   /*  }
	     */
	    
	    
	   /*  if($("#user1").is(":checked") == true){
	    }else{
	    } */
		
		$("#emailCheckBtn").click(function(){
			var email = $("#memberEmail").val();
			var membertype = $("#memberType").val();
			var licence = $("#Licence").val();
			var memberid = $("#memberId").val();
			var memberpwd = $("#memberPwd").val();
			var membername = $("#memberName").val();
			var membernname = $("#memberNickname").val();
			var addressnum = $("#addressNum").val();
			var address1 = $("#address1").val();
			var address2 = $("#address2").val();
			/* var tel1 = $("#tel1").val();
			var tel2 = $("#tel2").val();
			var tel3 = $("#tel3").val(); */
			var tel = $("#tel1").val() + "-" + $("#tel2").val() + "-" + $("#tel3").val();
			location.href = "/mono/emailSendAction.do?email=" + email+"&memberType="+membertype+"&Licence="+licence+"&memberId="+memberid+
					"&memberPwd="+memberpwd+"&memberName="+membername+"&memberNickname="+membernname+"&addressNum="+addressnum+
					"&address1="+address1+"&address2="+address2+"&tel=" + tel;/* 1="+tel1+"&tel2="+tel2+"&tel3="+tel3; */
		});
		
	});
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
	<h2 align-left = "150px;">회원가입을 환영합니다</h2>
	<form id = "memberJoinForm" method = "post" action = "/mono/joinMember.au" onsubmit = "return validate();" enctype="multipart/form-data">
		<table>
			<tr>
				<td align = "center">회원구분</td>
				<td>&emsp;&emsp;
					<label for = "user1">일반회원</label>
					<input type = "radio" id = "user1"  name = "memberType"  checked value = "1" onclick = "checkOnly(this);"/>
					<label for = "user2">업체회원</label>
					<input type = "radio" id = "user2"  name = "memberType"  value = "2" onclick = "checkOnly(this);"/>
				</td>
			</tr>

			<tr id = "licenceArea">
					<td align = "center">사업자등록증</td>
					<td><input type = "file" name = "Licence" id = "Licence"/></td>
					<td></td>
				</tr>

			<tr>
				<td align = "center">아이디</td>
				<td align = "center"><input type = "text" name = "memberId" id = "memberId" required/></td>
				 <td><div id = "checkIdBtn" onclick = "checkIdBtn();">중복확인</div></td> 
				<td></td>
			</tr>
			<tr>
				<td align = "center">비밀번호</td>
				<td align = "center"><input type = "password" name = "memberPwd" id = "memberPwd" required/></td>
				<td></td>
			</tr>
			<tr>
				<td align = "center">비밀번호확인</td>
				<td align = "center"><input type = "password" name = "memberPwd2" id = "memberPwd2" required/></td>
				<td></td>
			</tr>
			<tr>
				<td align = "center">이름</td>
				<td align = "center"><input type = "text" name = "memberName" id = "memberName" required/></td>
				<td></td>
			</tr>
			<tr>
				<td>닉네임(업체이름)</td>
				<td align = "center"><input type = "text" name = "memberNickname" id = "memberNickname" /></td>
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
					<td align = "center"><input type = "email"name = "email" id = "memberEmail"/></td>
					<td><div id="emailCheckBtn">이메일 인증</div></td>
				</tr>
				<tr>
					<td align = "center">전화번호</td>
					<td align = "center">
						<input type = "text" name = "tel1" id = "tel1" size = "3"/>-
						<input type = "text" name = "tel2" id = "tel2" size = "3"/>-
						<input type = "text" name = "tel3" id = "tel3" size = "3"/>
					</td>
					<td></td>
				</tr>
		</table>
		<br>
		<br>
	<div class = "btns">
		<div id = "returnMain" onclick = "returnMainPage();">메인으로</div>&emsp;
		<div id = "joinMenuBtn" onclick = "MemberJoin();">회원가입</div>
	</div>
	</form>
	</div>
	
<%@ include file="../common/footer.jsp" %>	
</body>
</html>