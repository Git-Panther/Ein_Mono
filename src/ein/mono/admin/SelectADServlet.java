package ein.mono.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.event.model.service.EventService;
import ein.mono.event.model.vo.EventVo;

@WebServlet("/selectAD.do")
public class SelectADServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectADServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String postCode = request.getParameter("postCode");
		EventVo event = new EventService().selectEvent(postCode);
		
		String url = "";
		if(null != event) {
			if(null != request.getParameter("requestPage")) {
				url = "views/admin/eventPopup.jsp";
				request.setAttribute("event", event);
			} else {
				url = "views/admin/adDetail.jsp";
				request.setAttribute("event", event);
			}
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "이벤트 상세 조회 오류");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
