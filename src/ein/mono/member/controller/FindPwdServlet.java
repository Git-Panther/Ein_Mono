package ein.mono.member.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.Base64Decoder;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

/**
 * Servlet implementation class FindPwdServlet
 */
@WebServlet("/findpwd.do")
public class FindPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		
		MemberVo memberfindpwd = new MemberService().findpwd(email, id);
		
String message = "";



		

	
		
		if(memberfindpwd != null) {
			message = "정보일치";

		}else {
			message = "정보불일치";
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message);
	}

}
