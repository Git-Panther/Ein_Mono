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
 * Servlet implementation class QnAListServlet
 */
@WebServlet("/selectQnAList.do")
public class QnAListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnAListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		QnAService service = new QnAService();
		String partnerCode = request.getParameter("partnerCode");
		
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
		
		String url = null;
		int totalCount = service.selectQnAListTotalCount(partnerCode); // 한 업체의 qna 총 개수
		if(-1 != totalCount) {
			maxPage = (int)((double)totalCount / limit + 0.9); // qna 개수가 63개면 7페이지
			
			startPage = (int)(currentPage / limitPage * limitPage) + 1;// 현재 페이지 번호
			endPage = startPage + limitPage - 1;
			if(maxPage < endPage) {
				endPage = maxPage;
			}
			PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, totalCount);
			
			ArrayList<QnAVo> list = service.selectQnAList(partnerCode, currentPage, limit);
			if(null != list) {
				url = "/views/qna/qnaList.jsp";
				request.setAttribute("list", list);
				request.setAttribute("pi", pi);
				request.setAttribute("listType", "Common");
				request.setAttribute("partnerCode", partnerCode);
				String ptnName =  new PartnersService().selectPartnerName(partnerCode);
				request.setAttribute("partnerName", ptnName);
			}else {
				url = "/views/common/errorPage.jsp";
				request.setAttribute("msg", "failed");
			}
		} else { // 실패
			url = "/views/common/errorPage.jsp";
			request.setAttribute("msg", "failed");
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}
}
