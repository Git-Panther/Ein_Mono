package ein.mono.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ein.mono.mypage.model.service.MypageService;
import ein.mono.request.model.vo.RequestVo;

/**
 * Servlet implementation class UpdateReqCheckServlet
 */
@WebServlet(name = "ModifyReqCheckServlet", urlPatterns = { "/modifyReqCheck.do" })
public class ModifyReqCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyReqCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rChk = request.getParameter("reqCheck");
		String rCode = request.getParameter("rCode");
		String reqType = "업체지정";
		String mCode = request.getParameter("mCode");
		
		int result = new MypageService().modifyReqCheck(rChk,rCode);
		
		if(0 < result){
			ArrayList<RequestVo> list = new MypageService().getOderList(mCode,reqType);
			
			if(0 <= list.size()){
				response.setContentType("application/json; charset=utf-8");
				new Gson().toJson(list,response.getWriter());
			}else{
				System.out.println("error");
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
