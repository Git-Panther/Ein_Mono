package ein.mono.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import ein.mono.board.model.service.ReplyService;
import ein.mono.board.model.vo.ReplyVo;

/**
 * Servlet implementation class InsertReply
 */
@WebServlet("/insertReply.do")
public class InsertReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		String content = request.getParameter("content");
		String pCode = request.getParameter("pCode");
		
		
		ReplyVo reply = new ReplyVo();
		reply.setWriter_code(mCode);
		reply.setReply_content(content);
		reply.setPost_code(pCode);
		
		
		int result = new ReplyService().insertReply(reply);
		if(0 < result){
			ArrayList<ReplyVo> list = new ReplyService().selectReplyList(pCode);
//			JSONArray json = new JSONArray();
//			for(int i = 0; i<list.size();i++){
//				JSONObject hm = new JSONObject();
//				hm.put("nName", list.get(i).getMember_nName());
//				hm.put("rDate", list.get(i).getReply_date());
//				hm.put("content", list.get(i).getReply_content());
//				json.add(hm);
//			}
			response.setContentType("application/json; charset=utf-8");
			new Gson().toJson(list,response.getWriter());
		}

	}

}
