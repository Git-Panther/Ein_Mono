package ein.mono.request.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import ein.mono.member.model.vo.MemberVo;
import ein.mono.request.model.service.RequestService;
import ein.mono.request.model.vo.RequestVo;

@WebServlet("/selectChoicePtnList.do")
public class SelectChoicePtnListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SelectChoicePtnListServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String userCode = request.getParameter("userCode");
		String reqCode = request.getParameter("reqCode");
		
		
		List<RequestVo> list = new RequestService().selectChoicePtnList(reqCode);
//		HttpSession session = request.getSession();
		RequestDispatcher view = null;
		if(list != null){
			request.setAttribute("list", list);
			view = request.getRequestDispatcher("views/request/choicePtn.jsp");
		}else{
			request.setAttribute("requestValue", "경매에 신청한 없체가 없습니다.");
			view = request.getRequestDispatcher("views/request/endRequest.jsp");			
		}
		view.forward(request, response);
	}

}
