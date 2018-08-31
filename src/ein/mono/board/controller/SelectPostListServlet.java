package ein.mono.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import ein.mono.board.model.dao.PostDao;
import ein.mono.board.model.service.PostService;
import ein.mono.board.model.service.ReplyService;
import ein.mono.board.model.vo.PostVo;
import ein.mono.board.model.vo.ReplyVo;
import ein.mono.common.PageInfo;

@WebServlet("/selectPostList.do")
public class SelectPostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectPostListServlet() {
        super();
    } 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String post_type = request.getParameter("posttype");

		//�럹�씠吏� 泥섎━ 蹂��닔
		int currentPage;	//�쁽�옱 �럹�씠吏��쓽 踰덊샇
		int limitPage;		//�븳�럹�씠吏��뿉 異쒕젰�븷 �럹�씠吏� 媛��닔
		//1~10
		int maxPage;		//媛��옣 留덉�留� �럹�씠吏�
		int startPage;		//�떆�옉 �럹�씠吏� 蹂��닔
		int endPage;		//留덉�留� �럹�씠吏� 蹂��닔
		int limit;				//�븳�럹�씠吏��뿉 異쒕젰�븷 湲��뿉 媛��닔
		
		limit = 10;
		limitPage = 10;
		
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else{
			currentPage = 1;
		}
		
		//총 길이
		int listCount = new PostService().selectPostTotalCount(post_type);
		System.out.println("listCount : " + listCount);
		//134 -> 14
		maxPage = (int)((double)listCount / limit + 0.9);
		
		//�쁽�옱 �럹�씠吏� 踰덊샇
		//12 - 10
		startPage = (int)(currentPage / limitPage * limitPage) + 1;
		
		//11~20  -> 134 -> 14
		endPage = startPage + limitPage - 1;
		if(maxPage < endPage){
			endPage = maxPage;
		}
		
		System.out.println("start" +startPage);
		System.out.println(endPage);
		
		PageInfo pi = new PageInfo(currentPage, limitPage, maxPage,startPage, endPage, listCount);
	
		
		ArrayList<PostVo> postList = null;
		// �뜽�꽕�씪 �씠誘몄�濡� �궗�슜�븷 泥⑤��뙆�씪 由ъ뒪�듃 媛��졇���꽌 �솕硫댁뿉 �쟾�떖.
		
		String url="";
		
		postList = new PostService().selectPostList(post_type,currentPage,limit);
		
		System.out.println(postList.size());
		if(post_type.equals("SHO")) {
			if(0 <= postList.size()){
				url = "views/post/shoPostList.jsp";
				request.setAttribute("list", postList);
				request.setAttribute("pType", post_type);
				request.setAttribute("pi", pi);
				
			}else{
				url = "views/post/errorPage.jsp";
				request.setAttribute("list", postList);
			}
		}else if(post_type.equals("FRE")){
			if(0 <= postList.size()){
				url = "views/post/frePostList.jsp";
				request.setAttribute("list", postList);
				request.setAttribute("pType", post_type);
				request.setAttribute("pi", pi);
				
			}else{
				url = "views/post/errorPage.jsp";
				request.setAttribute("list", postList);
			}
		}else if(post_type.equals("REV")){
			if(0 <= postList.size()){
				url = "views/post/afterList.jsp";
				request.setAttribute("list", postList);
				request.setAttribute("pType", post_type);
				request.setAttribute("pi", pi);
				
			}else{
				url = "views/post/errorPage.jsp";
				request.setAttribute("list", postList);
			}
		}else if(post_type.equals("MAR")){
			if(0 <= postList.size()){
				url = "views/post/marketList.jsp";
				request.setAttribute("list", postList);
				request.setAttribute("pType", post_type);
				request.setAttribute("pi", pi);
				
			}else{
				url = "views/post/errorPage.jsp";
				request.setAttribute("list", postList);
			}
			
		}else if(post_type.equals("REV")){
			if(0 <= postList.size()){
				url = "views/post/afterList.jsp";
				request.setAttribute("list", postList);
				request.setAttribute("pType", post_type);
				request.setAttribute("pi", pi);
			}
			
		}else {
			// �뿉�윭 �럹�씠吏�濡� �씠�룞
			url = "views/post/errorPage.jsp";
			request.setAttribute("list", postList);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}


}
