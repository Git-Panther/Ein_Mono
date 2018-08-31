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

@WebServlet("/selectMainEventList.do")
public class SelectMainEventListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectMainEventListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<EventVo> mainEventList = new EventService().selectMainEventList();
		
		String url = "";
		if(null != mainEventList) {
				url = "index.jsp";
				request.setAttribute("mainEventList", mainEventList);
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "메인 이벤트 조회 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
