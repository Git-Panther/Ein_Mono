package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;

@WebServlet("/deleteFAQ.do")
public class DeleteFAQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteFAQServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String post_code = request.getParameter("post_code");
		String post_type = request.getParameter("post_type");
		
		int result = new PostService().deletePost(post_code, post_type);
		
		String url = "";
		if(0 < result) {
			url = "selectFAQListAdmin.do?post_type=FAQ";
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "FAQ 삭제 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
