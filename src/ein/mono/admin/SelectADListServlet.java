package ein.mono.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.common.PageInfo;
import ein.mono.event.model.service.EventService;
import ein.mono.event.model.vo.EventVo;

@WebServlet("/selectADList.do")
public class SelectADListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectADListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EventService es = new EventService();
		PostService ps = new PostService();
		String[] eCodeList;
		if(null != request.getParameter("eCodeList")) {
			//System.out.println("asdf:" + request.getParameter("eCodeList"));
			eCodeList = request.getParameter("eCodeList").split(",");
			
			/*for(String s : eCodeList) {
				System.out.println(s);
			}*/
		}
		
		// 페이징 처리 변수
		int currentPage; // 현재 페이지의 번호
		// 1~10
		int limitPage; // 한 페이지에 출력할 페이지 개수
		int maxPage; // 가장 마지막 페이지
		int startPage; // 시작 페이지
		int endPage; // 마지막 페이지
		int limit; // 한 페이지에 출력할 글의 개수
		
		limit = 4;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} else {
			currentPage = 1;
		}
		
		// 게시글 총 개수
		int listCount = es.selectEventTotalCount();
		maxPage = (int) ((double)listCount / limit + 0.9);
		
		startPage = (int)(currentPage / limitPage * limitPage) + 1;
		
		endPage = startPage + limitPage - 1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, listCount);
		// ---------------------------페이징 처리 끝~

		ArrayList<EventVo> list = es.selectEventList();
		
		String url = "";
		if(null != list) {
				url = "views/admin/adList.jsp";
				request.setAttribute("list", list);
				request.setAttribute("pi", pi);
				//request.setAttribute("eCodeList", request.getParameter("eCodeList"));
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "광고 목록 조회 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
