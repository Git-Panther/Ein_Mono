package ein.mono.favblock.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.favblock.model.service.FBService;

@WebServlet("/deleteFBMyPage.do")
public class DeleteFBMyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteFBMyPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String member_code = request.getParameter("member_code");
		String target_code = request.getParameter("target_code");
		String fb_type = request.getParameter("fb_type");
		
		int result = new FBService().deleteFB(member_code, target_code);

		String url = "";
		if(0 < result) {
			url = "selectFBList.do?member_code=" + member_code + "&fb_type=" + fb_type;
		} else {
			url = "views/common/errorPage.jsp";
			request.setAttribute("msg", "즐겨찾기/차단 해제 오류");
		}
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
