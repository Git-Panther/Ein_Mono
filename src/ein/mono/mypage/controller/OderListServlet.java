package ein.mono.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ein.mono.mypage.model.service.MypageService;
import ein.mono.request.model.vo.RequestVo;

/**
 * Servlet implementation class OderListServlet
 */
@WebServlet("/orderList.do")
public class OderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OderListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		String reqType = request.getParameter("reqType");
		
		ArrayList<RequestVo> list = new MypageService().getOderList(mCode,reqType);
		
		if(0 <= list.size()){
			response.setContentType("application/json; charset=utf-8");
			new Gson().toJson(list,response.getWriter());
		}else{
			System.out.println("error");
		}
	}

}
