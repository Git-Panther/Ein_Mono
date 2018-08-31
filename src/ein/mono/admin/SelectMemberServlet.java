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
import ein.mono.partners.model.service.PartnersService;
import ein.mono.partners.model.vo.PartnersVo;

@WebServlet("/selectMember.do")
public class SelectMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectMemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String member_code = request.getParameter("member_code");
		// 등급변경 버튼 클릭 여부
		int flag = 0;
		String flag2 = request.getParameter("flag");
		if(null != flag2) {
			flag = Integer.parseInt(flag2);
		}
		PartnersVo m = new PartnersVo();
		// 정지시작일이랑 정지종료일도 불러와야함. REPORT 테이블과 조인해서 가져오기
		// 업체일 경우 평점도 가져오기
		if(member_code.charAt(0) != 'B') {
			m = new PartnersService().selectPartner(member_code);
		}
		MemberVo mem = new MemberService().selectMember(member_code);
		
		String url = "";
		if(null != mem) {
			url = "views/admin/memberDetail.jsp";
			if(null != m) {
				request.setAttribute("m", m);
			}
			request.setAttribute("mem", mem);
			request.setAttribute("flag", flag);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원 상세 조회 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

}
