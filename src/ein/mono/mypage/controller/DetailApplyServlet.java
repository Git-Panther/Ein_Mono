package ein.mono.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.mypage.model.service.MypageService;
import ein.mono.request.model.vo.RequestVo;

/**
 * Servlet implementation class ReportDetailServlet
 */
@WebServlet("/myDetailApply.do")
public class DetailApplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DetailApplyServlet() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		
		ArrayList<RequestVo> result = new MypageService().getRequestList(mCode);
		
		RequestDispatcher view = null;
		if(0 <= result.size()){
			request.setAttribute("list", result);
			view = request.getRequestDispatcher("views/mypage/requestInterior.jsp");
			view.forward(request, response);
		}else{
			System.out.println("error");
		}
	}
	
}
