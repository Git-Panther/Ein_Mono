package ein.mono.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.controller.mail.Gmail;
import ein.mono.member.controller.mail.SHA256;
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

@WebServlet("/emailCheckAction.do")
public class EmailCheckActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmailCheckActionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String code = request.getParameter("code");

		//UserDAO userDAO = new UserDAO();

		String userID = null;
	
		String userEmail = request.getParameter("email");

		boolean rightCode = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;
		
		String url = "";

		if(rightCode == true) {
			//userDAO.setUserEmailChecked(userID);
			
			int result = new MemberService().updateEmailCheck(userEmail);
			
			if(0 < result) {
				PrintWriter script = response.getWriter();
	
				script.println("<script>");
	
				script.println("alert('인증에 성공했습니다.(servlet)');");
	
				script.println("location.href = '/mono/index.jsp'");
	
				script.println("</script>");
	
				script.close();		
	
				return;
			} else {

				PrintWriter script = response.getWriter();
	
				script.println("<script>");
	
				script.println("alert('유효하지 않은 코드입니다.');");
	
				script.println("location.href = 'index.jsp'");
	
				script.println("</script>");
	
				script.close();		
	
				return;
	
			}
		}
	
	}

	private Object MemberService() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
