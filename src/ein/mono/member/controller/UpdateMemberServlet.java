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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/updateMember.au")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		//객체저장    
		
		
		String mId	= request.getParameter("memberId");
		String mPwd = request.getParameter("memberPwd");
		String mName = request.getParameter("memberName");
		String mEmail = request.getParameter("email");
		String mAddress = request.getParameter("addressNum")+", "+request.getParameter("address1")+", "+request.getParameter("address2");
		String mTel = request.getParameter("tel1")+"-"+request.getParameter("tel2")+"-"+request.getParameter("tel3");
		String mNickname = request.getParameter("memberNickname");
		
		MemberVo member = new MemberVo(mId, mPwd, mName, mEmail, mAddress, mTel, mNickname);
		
		int result = new MemberService().updateMember(member);
		RequestDispatcher view = null;
		
		if(0<result) {
			//현재 member정보로 session 값 수정
			HttpSession session =  request.getSession();
			session.setAttribute("LoginMember", member);
		//회원정보수정완료 >> 인텍스
			
			request.setAttribute("msg", "회원정보수정완료에 성공하였습니다");
			view = request.getRequestDispatcher("views/common/successPage.jsp");
			
		}else {
			request.setAttribute("msg", "회원정보수정에 실패하였습니다");
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
		}
		view.forward(request, response);
		
	}

}
