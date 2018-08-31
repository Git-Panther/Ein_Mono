package ein.mono.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

/**
 * Servlet implementation class memberDeleteServlet
 */
@WebServlet("/deleteMember.do")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		/*int result = new MemberService().deleteMember(id);
		
		RequestDispatcher view = null;
		if(0<result) {
			HttpSession session = request.getSession();
			session.setAttribute("LoginMember", member);
			
		}else {
		
			view.forward(request, response);
		}*/
		HttpSession session = request.getSession();
		
		
		MemberVo member = (MemberVo)session.getAttribute("LoginMember");
		
		
		int result = new MemberService().deleteMember(member);
		
		RequestDispatcher view = null;
		
		if(0<result) {
			session.setAttribute("LoginMember", member);
			//세션값삭제
			
			session.invalidate();
			

			request.setAttribute("msg", "회원탈퇴가 정상적으로 처리되었습니다");
			view = request.getRequestDispatcher("views/common/successPage.jsp");
			
		}else {
			
			
			request.setAttribute("msg", "회원탈퇴가 실패했습니다!");
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
		}
		view.forward(request, response);
		
	}

}
