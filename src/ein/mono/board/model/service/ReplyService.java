package ein.mono.board.model.service;
import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.board.model.dao.ReplyDao;
import ein.mono.board.model.vo.ReplyVo;
import ein.mono.common.JDBCTemplate; 
 
public class ReplyService {
	public ArrayList<ReplyVo> selectReplyList(String post_code) {
		// Ŀ�ؼ� ����
		Connection con = JDBCTemplate.getConnection();
		// ��� ȣ��
		ArrayList<ReplyVo> list = new ReplyDao().selectReplyList(con, post_code);

		JDBCTemplate.close(con);
		
		// ��� ��ȯ
		return list;
	}

	public int insertReply(ReplyVo reply) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new ReplyDao().insertReply(con, reply);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int updateReply(ReplyVo reply) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new ReplyDao().updateReply(con, reply);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int deleteReply(String reply_code) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new ReplyDao().deleteReply(con, reply_code);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}
}
