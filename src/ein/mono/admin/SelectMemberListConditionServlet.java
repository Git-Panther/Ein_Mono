package ein.mono.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.common.PageInfo;
import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;

@WebServlet("/selectMemberListCon.do")
public class SelectMemberListConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectMemberListConditionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 : 아이디, 2 : 이름
		int condition = 1;
		condition = Integer.parseInt(request.getParameter("condition"));
		String searchContent = request.getParameter("searchContent");
		
		MemberService ms = new MemberService();
		
		// 페이징 처리 변수
		int currentPage; // 현재 페이지의 번호
		// 1~10
		int limitPage; // 한 페이지에 출력할 페이지 개수
		int maxPage; // 가장 마지막 페이지
		int startPage; // 시작 페이지
		int endPage; // 마지막 페이지
		int limit; // 한 페이지에 출력할 글의 개수
		
		limit = 10;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} else {
			currentPage = 1;
		}
		
		// 게시글 총 개수
		int listCount = -1;
		if(condition > 0) {
			switch(condition) {
			case 1:
				// 1 : 아이디, 2 : 이름
				listCount = ms.selectMemberConTotalCount(1, searchContent);
				break;
			case 2:
				listCount = ms.selectMemberConTotalCount(2, searchContent);
				break;
			}
		}
		maxPage = (int) ((double)listCount / limit + 0.9);
		
		startPage = (int)(currentPage / limitPage * limitPage) + 1;
		
		endPage = startPage + limitPage - 1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, listCount);
		// ---------------------------페이징 처리 끝~

		ArrayList<MemberVo> list = ms.selectMemberListCon(currentPage, limit, condition, searchContent);
		
		String url = "";
		if(null != list){
			url = "views/admin/memberList.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.setAttribute("condition", condition);
			request.setAttribute("selectOption", 1);
			request.setAttribute("searchBtnPressed", 1);
			request.setAttribute("searchContent", searchContent);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원 목록 조회 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
		
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
