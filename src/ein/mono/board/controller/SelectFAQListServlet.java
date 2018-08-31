package ein.mono.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

@WebServlet("/selectFAQList.do")
public class SelectFAQListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectFAQListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String post_type = request.getParameter("post_type");
		ArrayList<PostVo> list = new PostService().selectPostListNonPaging(post_type);
		
		String url = "";
		if(null != list) {
			url = "views/faq/faq.jsp";
			request.setAttribute("list", list);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "FAQ 목록 조회 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
