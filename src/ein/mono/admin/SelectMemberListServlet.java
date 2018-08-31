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
import ein.mono.partners.model.service.PartnersService;

@WebServlet("/selectMemberList.do")
public class SelectMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectMemberListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 : 전체, 2 : 개인, 3 : 업체(ptn_check 컬럼 표시)
		int selectOption = 1;
		selectOption = Integer.parseInt(request.getParameter("selectOption"));
	
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
		if(selectOption > 0) {
			switch(selectOption) {
			case 1:
				// 1 : 개인, 2 : 업체
				listCount = ms.selectMemberTotalCount(1) + ms.selectMemberTotalCount(2);
				break;
			case 2:
				listCount = ms.selectMemberTotalCount(1);
				break;
			case 3:
				listCount = ms.selectMemberTotalCount(2);
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

		ArrayList<MemberVo> list = ms.selectMemberList(currentPage, limit, selectOption);
		
		String url = "";
		if(null != list){
			url = "views/admin/memberList.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.setAttribute("condition", 1);
			request.setAttribute("selectOption", selectOption);
			request.setAttribute("searchBtnPressed", 0);
			request.setAttribute("searchContent", "");
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원 목록 조회 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	
	}

}
