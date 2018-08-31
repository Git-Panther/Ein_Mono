package ein.mono.member.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;
import ein.mono.report.model.service.ReportService;
import ein.mono.report.model.vo.ReportVo;

@WebServlet("/loginMember.au")
public class LoginMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginMemberServlet() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String id = request.getParameter("memberId");
		String password = request.getParameter("memberPwd");

		MemberVo member = new MemberService().loginMember(id);
		RequestDispatcher view = null;

		String mCode = member.getMemberCode();

		ReportVo report = new ReportService().findDate(mCode);

		if (mCode == null) {
			view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "로그인정보를 확인해주세요");
			view.forward(request, response);
		} else {

			if (member.getDelflag().equals("Y")) {
				view = request.getRequestDispatcher("views/common/errorPage.jsp");
				request.setAttribute("msg", "탈퇴한 회원입니다");
				view.forward(request, response);
			} else {

				if (null != report) {
					Date date1 = report.getBanstartDate();
					Date date2 = report.getSysdate();
					Date date3 = report.getBanendDate();
					if ((date2.compareTo(date1) > 0 && date3.compareTo(date2) > 0)
							|| (date2.compareTo(date1) == 0 && date3.compareTo(date2) > 0)
							|| (date2.compareTo(date1) > 0 && date3.compareTo(date2) == 0)) {
						view = request.getRequestDispatcher("views/common/errorPage.jsp");
						request.setAttribute("msg", date1 + "부터" + date3 + "까지 이용정지된회원입니다");
						view.forward(request, response);
					}
				} else {

					if (password.equals(member.getMemberPwd())) {
						HttpSession session = request.getSession();
						session.setAttribute("LoginMember", member);
						response.sendRedirect("index.jsp");

					} else {
						view = request.getRequestDispatcher("views/common/errorPage.jsp");
						request.setAttribute("msg", "로그인정보를 확인하세요");
						view.forward(request, response);
					}
				}
			}
		}

	}

}