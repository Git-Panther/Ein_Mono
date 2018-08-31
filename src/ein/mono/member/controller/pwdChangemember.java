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

/**
 * Servlet implementation class pwdChangemember
 */
@WebServlet("/enc.do")
public class pwdChangemember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pwdChangemember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("mId");
		String password = request.getParameter("memberPwd");
		
		
		int pwdChange = new MemberService().pwdChangeMember(id, password);
		
		
		RequestDispatcher view = null;
		
		if(0<pwdChange) {
				
			if(0<pwdChange) {
				HttpSession session = request.getSession();
				//session.setAttribute("userID", id);
				view = request.getRequestDispatcher("index.jsp");
			//request.setAttribute("msg", "환영합니다! 회원가입을 축하해요!");			
			//view = request.getRequestDispatcher("views/common/successPage.jsp");
			}else {
				
				request.setAttribute("msg", "회원가입에 실패하였습니다");
				view = request.getRequestDispatcher("views/common/errorPage.jsp");
			}
		
		
		view.forward(request, response);
		
		}
		
	}

}
