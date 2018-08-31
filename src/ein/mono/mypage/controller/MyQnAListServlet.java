package ein.mono.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.vo.PostVo;
import ein.mono.common.PageInfo;
import ein.mono.mypage.model.service.MypageService;
import ein.mono.qna.model.vo.QnAVo;

/**
 * Servlet implementation class MyQnAListServlet
 */
@WebServlet("/myQnaList.do")
public class MyQnAListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyQnAListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		
		int currentPage; //현재 페이지 번호
		int limit; //한 페이지에 출력할 글의 갯수
		int limitPage; //한 페이지에 출력할 페이지 갯수
		int maxPage; //가장 마지막 페이지
		int startPage; //시작 페이지
		int endPage; //마지막 페이지
		
		limit = 10;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else{
			currentPage = 1;
		}
		
		//게시글의 총 갯수
		int listCount = new MypageService().selectBoardTotalCount();
		maxPage = (int)((double)listCount/limit + 0.9);
		
		startPage = (int)(currentPage /limitPage * limitPage) +1;
		
		endPage = startPage + limitPage -1;
		if(maxPage < endPage){
			endPage = maxPage;
		}
		PageInfo pi = new PageInfo(currentPage, limitPage, maxPage, startPage, endPage, listCount);
		
		ArrayList<PostVo> list = new MypageService().getQnaList(mCode,currentPage,limit);
		

		RequestDispatcher view = null;
		if(0 <= list.size()){
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			view = request.getRequestDispatcher("views/mypage/myQnAList.jsp");
		}else{
			System.out.println("error");
		}
		view.forward(request, response);
	}

}
