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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.controller.mail.Gmail;
import ein.mono.member.controller.mail.SHA256;
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

@WebServlet("/emailSendAction.do")
public class EmailSendActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmailSendActionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String id = request.getSession().getAttribute("userID").toString();
		String id = request.getParameter("email");
		
		//MemberVo member = new MemberService().selectMember(id);
		
		//String emailChecked = member.getUserEmailChecked(id);

		/*if(emailChecked == true) {

			PrintWriter script = response.getWriter();

			script.println("<script>");

			script.println("alert('이미 인증 된 회원입니다.');");

			script.println("location.href = 'index.jsp'");

			script.println("</script>");

			script.close();		

			return;

		}*/
		 // else {

		String mRank = request.getParameter("memberType");
		
		String mId	= request.getParameter("memberId");
		String mPwd = request.getParameter("memberPwd");
		String mName = request.getParameter("memberName");
		String mEmail = request.getParameter("email");
		String mAddress = "("+request.getParameter("addressNum")+")"+", "+request.getParameter("address1")+", "+request.getParameter("address2");
		//String mTel = request.getParameter("tel1")+"-"+request.getParameter("tel2")+"-"+request.getParameter("tel3");
		String mTel = request.getParameter("tel");
		String mNickname = request.getParameter("memberNickname");
		
		//String licence = request.getFilesystemName("Licence");
		
		MemberVo member = new MemberVo(mId, mPwd, mName, mEmail, mAddress, mTel, mNickname);
		
				//SMTP에 접속하기 위한 정보를 기입합니다.
				Properties p = new Properties();
				
				String host = "http://localhost:8082/mono/";
		
				String from = "yujisang93@gmail.com";
		
				String to = id; //입력받은 이메일
				
				String content = "다음 링크에 접속하여 이메일 확인을 진행하세요." +
						
				"<a href='" + host + "views/member/emailCheckAction.jsp?&email=" + to + "&code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
				
				p.put("mail.smtp.user", from);
				
				p.put("mail.smtp.host", "smtp.googlemail.com");
				
				p.put("mail.smtp.port", "465");
				
				p.put("mail.smtp.starttls.enable", "true");
				
				p.put("mail.smtp.auth", "true");
				
				p.put("mail.smtp.debug", "true");
				
				p.put("mail.smtp.socketFactory.port", "465");
				
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				
				p.put("mail.smtp.socketFactory.fallback", "false");
				
				 
				
				try{
				
				    Authenticator auth = new Gmail();
				
				    Session ses = Session.getInstance(p, auth);
				
				    ses.setDebug(true);
				
				    MimeMessage msg = new MimeMessage(ses); 
				
				    msg.setSubject("MONO이메일인증");
				
				    Address fromAddr = new InternetAddress(from);
				
				    msg.setFrom(fromAddr);
				
				    Address toAddr = new InternetAddress(to);
				
				    msg.addRecipient(Message.RecipientType.TO, toAddr);
				
				    msg.setContent(content, "text/html;charset=UTF-8");
				
				    Transport.send(msg);
				
				} catch(Exception e){
				
				    e.printStackTrace();
				
					PrintWriter script = response.getWriter();
				
					script.println("<script>");
				
					script.println("alert('오류가 발생했습니다..');");
				
					script.println("history.back();");
				
					script.println("</script>");
				
					script.close();		
				
				    return;
				
				}
				
		// }
		String url = "";
		if(null != member) {
			/*String code = new SHA256().getSHA256(member.getMemberEmail());
			
			url = "emailCheckAction.do?code=" + code + "&email=" + member.getMemberEmail();*/
			request.setAttribute("member", member);
			
			PrintWriter script = response.getWriter();
			
			script.println("<script>");
		
			script.println("alert('이메일을 확인하세요..');");
		
			//script.println("location.href = '/mono/index.jsp';");
			
			script.println("history.back();");
		
			script.println("</script>");
		
			script.close();	
			
			//url = "index.jsp";
			
		} /*else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "멤버 조회 실패");
			RequestDispatcher view = request.getRequestDispatcher(url);
			view.forward(request, response);
		}*/
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
