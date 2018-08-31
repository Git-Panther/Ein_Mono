package ein.mono.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ein.mono.member.model.service.MemberService;

/**
 * Servlet implementation class UpdateNewpwdMember
 */
@WebServlet("/updateNewpwd.au")
public class UpdateNewpwdMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNewpwdMember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("memberid");
		String memberPwd = request.getParameter("memberPwd");
		
		int pwdChange = new MemberService().pwdChangeMember(id, memberPwd);
		
		RequestDispatcher view = null;
		
		
			
		PrintWriter out = response.getWriter(); 
			if(0<pwdChange) {
				 out.println ("<script>"); 
			        out.println("alert('비밀번호가 정상적으로 바뀌었습니다');");
			        out.println ("window.close()"); 			      
			        out.println ("</script>"); 
			}else {
				
				out.println ("<script>"); 
		        out.println("alert('비밀번호가 바꾸기에 실패했습니다');");
		        out.println ("window.close()"); 			      
		        out.println ("</script>"); 
			}
	
	}
}
