package ein.mono.qna.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.board.model.dao.PostDao;
import ein.mono.qna.model.dao.QnADao;
import ein.mono.qna.model.vo.QnAVo;

public class QnAService {
	private QnADao qnaDao = new QnADao();

	public ArrayList<QnAVo> selectQnAList(String partnerCode, int currentPage, int limit) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		ArrayList<QnAVo> list = qnaDao.selectQnAList(con, partnerCode, currentPage, limit);
		JDBCTemplate.close(con); // 자원 반납
		return list;
	}
	public int selectQnAListTotalCount(String partnerCode) {
		// TODO Auto-generated method stub
		//커넥션 생성
		Connection con = JDBCTemplate.getConnection();
		//비지니스 로직 호출
		int listCount = qnaDao.selectQnAListTotalCount(con, partnerCode);
		//자원 반납(close)
		JDBCTemplate.close(con);
		//결과 반환
		return listCount;
	}
	public int selectQnAListTotalCount(String partnerCode, String condition, String keyword) {
		// TODO Auto-generated method stub
		//커넥션 생성
		Connection con = JDBCTemplate.getConnection();
		//비지니스 로직 호출
		int listCount = qnaDao.selectQnAListTotalCount(con, partnerCode, condition, keyword);
		//자원 반납(close)
		JDBCTemplate.close(con);
		//결과 반환
		return listCount;
	}
	public ArrayList<QnAVo> selectQnAListByKeyword(String partnerCode, String condition, String keyword,
			int currentPage, int limit) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		ArrayList<QnAVo> list = qnaDao.selectQnAListByKeyword(con, partnerCode, condition, keyword, currentPage, limit);
		JDBCTemplate.close(con); // 자원 반납
		return list;
	}
	public String insertPtnQnA(QnAVo qna) { // 왜 String이냐면 postCode를 받아서 그걸 출력할 것이기 때문
		// TODO Auto-generated method stub	
		Connection con = JDBCTemplate.getConnection();
		int result1, result2;
		result1 = result2 = 0;
		String postCode = null;
		
		PostDao pd = new PostDao();		
		result1 = pd.insertPost(con, qna);
		if(0 < result1) {
			postCode = pd.selectRecentlyPostCode(con, qna.getPost_type(), qna.getWriter_code());
			if(postCode == null || "".equals(postCode)) { // 혹시 모를 실패에 대비한 대비책
				JDBCTemplate.rollback(con);
				JDBCTemplate.close(con);
				return null;
			}
			
			qna.setPost_code(postCode);
			result2 = qnaDao.insertPtnQnA(con, qna);
			if(0 < result2) {
				JDBCTemplate.commit(con);
			}else {
				JDBCTemplate.rollback(con);
				JDBCTemplate.close(con);
				return null;
			}
		}else{
			JDBCTemplate.rollback(con);
			JDBCTemplate.close(con);
			return null;
		}			
		JDBCTemplate.close(con);		
		return postCode;	
	}
	

}
