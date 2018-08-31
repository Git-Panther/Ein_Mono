package ein.mono.qna.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.common.PageInfo;
import ein.mono.partners.model.service.PartnersService;
import ein.mono.qna.model.service.QnAService;
import ein.mono.qna.model.vo.QnAVo;

/**
 * Servlet implementation class SearchQnAListServlet
 */
@WebServlet("/searchQnAList.do")
public class SearchQnAListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchQnAListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String partnerCode = request.getParameter("partnerCode");	
		String keyword = request.getParameter("keyword"); // 검색 키워드
		String condition = request.getParameter("condition"); // 검색 기준
		
		QnAService qnaService = new QnAService();
		
		// 페이징 처리
		int currentPage = 0; // 현재 페이지 번호
		int limitPage = 0; // 한 페이지에 출력할 페이지 개수
		
		int maxPage = 0; // 마지막 페이지
		int startPage = 0; // 시작 페이지
		int endPage = 0; // 마지막 페이지 변수
		int limit = 0; // 한 페이지에 출력할 게시글 개수
		
		limit = 10;
		limitPage = 10;
		
		if(null != request.getParameter("currentPage")){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else{
			currentPage = 1;
		}
		
		int totalCount = qnaService.selectQnAListTotalCount(partnerCode, condition, keyword); // 총 질문 개수
		String url = null;
		if(-1 != totalCount) {
			maxPage = (int)((double)totalCount / limit + 0.9); // 업체 개수가 145개면 13페이지
			
			startPage = (int)(currentPage / limitPage * limitPage) + 1;// 현재 페이지 번호
			endPage = startPage + limitPage - 1;
			if(maxPage < endPage) {
				endPage = maxPage;
			}
			PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, totalCount);
				
			ArrayList<QnAVo> list = qnaService.selectQnAListByKeyword(partnerCode, condition, keyword, currentPage, limit);
			// 검색 조건이 무엇이냐에 따라 불러오는 쿼리는 달라진다. 표시하는 방법과 페이징은 같다.
			if(null != list){ // 성공
				url = "/views/qna/qnaList.jsp?partnerCode="+partnerCode;
				request.setAttribute("list", list);
				request.setAttribute("pi", pi);
				request.setAttribute("listType", "Search_"+condition+":"+keyword);
				request.setAttribute("partnerCode", partnerCode);
				String ptnName =  new PartnersService().selectPartnerName(partnerCode);
				request.setAttribute("partnerName", ptnName);
			}else{ // 실패
				url = "/views/commom/errorPage.jsp";
			}
		} else { // 실패
			url = "/views/commom/errorPage.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);	
	}

}
