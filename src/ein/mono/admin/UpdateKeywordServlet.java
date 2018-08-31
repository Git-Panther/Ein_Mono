package ein.mono.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.keyword.model.service.KeywordService;
import ein.mono.keyword.model.vo.KeywordVo;

@WebServlet("/updateKeyword.do")
public class UpdateKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateKeywordServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int keyword_no = Integer.parseInt(request.getParameter("keyword_no"));
		String keyword_content = request.getParameter("keyword_content");

		int result = new KeywordService().updateKeywordContent(keyword_no, keyword_content);
		
		String url = "";
		if(0 < result) {
			url = "selectKeywordList.do?requestPage=adminPage";
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "키워드 수정 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

}
