<%@page import="ein.mono.member.model.service.MemberService"%>
<%@page import="ein.mono.member.model.vo.MemberVo"%>
<%@page import="java.io.PrintWriter"%>

<%@page import="ein.mono.member.controller.mail.SHA256"%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	//MemberVo member = (MemberVo) session.getAttribute("member");
	
	String email = request.getParameter("email");
	
	PrintWriter script = response.getWriter();
	
	
	request.setCharacterEncoding("UTF-8");

	String code = request.getParameter("code");

	//UserDAO userDAO = new UserDAO();

	String userID = null;
 
	/* if(session.getAttribute("userID") != null) {

		userID = (String) session.getAttribute("userID");

	}
 */
	/* if(userID == null) {

		PrintWriter script = response.getWriter();

		script.println("<script>");

		script.println("alert('로그인을 해주세요.');");

		script.println("location.href = 'userLogin.jsp'");

		script.println("</script>");

		script.close();

		return;

	} */
 
	String userEmail = email;

	boolean rightCode = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;

	if(rightCode == true) {

		//userDAO.setUserEmailChecked(userID);
		 int result = new MemberService().updateEmailCheck(userEmail); 
		
		
		
		script = response.getWriter();

		script.println("<script>");

		script.println("alert('인증에 성공했습니다.(jsp)');");

		//script.println("location.href = '/mono/index.jsp'");
		
		
		
		script.println("window.close();");

		script.println("</script>");

		script.close();		

		return;

	} else {

		script = response.getWriter();

		script.println("<script>");

		script.println("alert('유효하지 않은 코드입니다.');");

		script.println("location.href = 'index.jsp'");

		script.println("</script>");

		script.close();		

		return;

	}
%>