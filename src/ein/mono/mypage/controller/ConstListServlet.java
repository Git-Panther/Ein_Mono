package ein.mono.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.mypage.model.service.MypageService;

/**
 * Servlet implementation class ConstListServlet
 */
@WebServlet("/constList.do")
public class ConstListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ConstListServlet() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		
		HashMap<Integer, String> list = new MypageService().getConstList(mCode);
		
		RequestDispatcher view = null;
		if(null != list){
			request.setAttribute("list", list);
			view = request.getRequestDispatcher("views/mypage/constPhotoList.jsp");
		}else{
			System.out.println("error");
		}
		view.forward(request, response);
	}

}
