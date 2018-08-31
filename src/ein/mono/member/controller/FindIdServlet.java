package ein.mono.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

/**
 * Servlet implementation class FindIdServlet
 */
@WebServlet("/findid.do")
public class FindIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		
		MemberVo memberfindid = new MemberService().findId(email);
		/*String findedid = memberfindid.getMemberId();*/
		
		String message = "";
		
		if(memberfindid != null) {
			message = memberfindid.getMemberId();

		}else {
			message = "이메일없음";
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message);
	}

}
