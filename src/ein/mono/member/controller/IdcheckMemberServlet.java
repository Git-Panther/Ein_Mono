package ein.mono.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

/**
 * Servlet implementation class MemberIdCheckServlet
 */
@WebServlet("/idCheck.do")
public class IdcheckMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdcheckMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("memberId");
		
		MemberVo member = new MemberService().loginMember(id);
		
		String message = "";
		
		if(member != null) {
			message = "중복된 아이디 입니다";
		}else {
			message = "사용가능합니다";
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message);
		
	}

}
