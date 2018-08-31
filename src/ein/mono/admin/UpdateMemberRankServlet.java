package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

@WebServlet("/updateMemberRank.do")
public class UpdateMemberRankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMemberRankServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String member_code = request.getParameter("member_code");
		// 새로운 member_rank가 블랙리스트라면 정지시작일을 sysdate로 업데이트해줘야 함,
		String member_rank = request.getParameter("rank");
		
		MemberVo m = new MemberService().selectMember(member_code);
		
		int result = 0;
		String url = "";
		if(null != m) {
			result = new MemberService().updateMemberRank(member_code, member_rank);
			if(0 < result) {
				url = "selectMember.do?member_code=" + member_code + "&flag=0";
			} else {
				url = "views/common/errorPage.jsp";
				request.setAttribute("msg", "회원 등급 수정 오류");
			}
			RequestDispatcher view = request.getRequestDispatcher(url);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
