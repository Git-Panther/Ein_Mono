package ein.mono.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import ein.mono.member.model.service.MemberService;
import ein.mono.member.model.vo.MemberVo;


@WebServlet("/joinMember.au")
public class JoinMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public JoinMemberServlet() {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//파일 업로드/다운로드 >> cos.jar 라이브러리
				//엑셀파일 >> poi.jar 라이브러리

				//1. 파일사이즈 설정
				int maxSize = 1024*1024*10;
				//2. 파일 전송시 enc 정상적으로 확인되었는지 확인
				
				RequestDispatcher view = null;
				if(!ServletFileUpload.isMultipartContent(request)) {
					view = request.getRequestDispatcher("view/common/errorPage.jsp");
					request.setAttribute("msg", "첨부하는 파일에 문제가 있습니다");
					view.forward(request, response);
				}
				
				//3. 파일 저장 경로 설정
				
				String root = request.getServletContext().getRealPath("/");
				String path = root + "upload/licence";
				//4. request >> mulripartrequest
				MultipartRequest mRequest = new MultipartRequest(request, path, maxSize, "UTF-8");

		//객체저장    
		
		String mRank = mRequest.getParameter("memberType");
		
		String mId	= mRequest.getParameter("memberId");
		String mPwd = mRequest.getParameter("memberPwd");
		String mName = mRequest.getParameter("memberName");
		String mEmail = mRequest.getParameter("email");
		String mAddress = "("+mRequest.getParameter("addressNum")+")"+", "+mRequest.getParameter("address1")+", "+mRequest.getParameter("address2");
		String mTel = mRequest.getParameter("tel1")+"-"+mRequest.getParameter("tel2")+"-"+mRequest.getParameter("tel3");
		String mNickname = mRequest.getParameter("memberNickname");
		
		String licence = mRequest.getFilesystemName("Licence");

		MemberVo member = new MemberVo(mId, mPwd, mName, mEmail, mAddress, mTel, mNickname, licence);
		
		
		int result = new MemberService().joinMember(member, mRank);
		
		
		if(0<result) {
			//int pwdChange = new MemberService().pwdChangeMember(mId, mPwd);
			//if(0<pwdChange) {
			
			
			/*if(mRank == "2") {
				view = request.getRequestDispatcher("pntmember.do?mId="+mId+"&licence="+licence);
			}else {
				
			}*/
			
			view = request.getRequestDispatcher("enc.do?mId=" + mId + "&memberPwd=" + mPwd);
			
			}else {
				
				request.setAttribute("msg", "회원가입에 실패하였습니다");
				view = request.getRequestDispatcher("views/common/errorPage.jsp");
			}
		
		
		view.forward(request, response);
		
		
		
		
		
		
		
		
	}

}
