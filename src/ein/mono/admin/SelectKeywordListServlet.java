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

@WebServlet("/selectKeywordList.do")
public class SelectKeywordListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectKeywordListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<KeywordVo> list = new KeywordService().selectKeywordList();
		String requestPage = request.getParameter("requestPage");
		int keywordNo = 0;
		String url = "";
		if(null != list) {
			if(requestPage.equals("index")) {
				//url = "index.jsp";
				// 메인광고 불러오는 서블릿 연속 호출.
				url = "selectMainEventList.do";
			} else {
				if(requestPage.equals("adminPage")) {
					url = "views/admin/keywordList.jsp?keywordNo=" + keywordNo;
				} else if(requestPage.equals("adminPageUpdateKeyword")){
					keywordNo = Integer.parseInt(request.getParameter("keywordNo"));
					url = "views/admin/keywordList.jsp?keywordNo=" + keywordNo;
				}
			}
			request.setAttribute("list", list);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "키워드 목록 조회 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
