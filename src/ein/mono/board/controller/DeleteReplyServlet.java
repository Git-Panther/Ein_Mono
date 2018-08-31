package ein.mono.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ein.mono.board.model.service.ReplyService;
import ein.mono.board.model.vo.ReplyVo;
/**
 * Servlet implementation class DeleteReplyServlet
 */
@WebServlet("/deleteReply.do")
public class DeleteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DeleteReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String rCode = request.getParameter("rCode");
         String pCode = request.getParameter("pCode");
		

		int result = new ReplyService().deleteReply(rCode);
		
		if(0 < result){
			ArrayList<ReplyVo> list = new ReplyService().selectReplyList(pCode);
			
			response.setContentType("application/json; charset=utf-8");
			new Gson().toJson(list,response.getWriter());	
		}	

	}
	
}
