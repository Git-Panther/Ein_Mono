package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.board.model.service.PostService;
import ein.mono.board.model.vo.PostVo;

@WebServlet("/insertFAQ.do")
public class InsertFAQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertFAQServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer_code = request.getParameter("writer_code");
		
		PostVo post = new PostVo();
		post.setTitle(title);
		post.setContent(content);
		post.setPost_type("FAQ");
		post.setWriter_code("A1");
		
		int result = new PostService().insertPost(post);
		
		String url = "";
		if(0 < result) {
			url = "selectFAQListAdmin.do?post_type=FAQ";
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
