package ein.mono.keyword.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.keyword.model.dao.KeywordDao;
import ein.mono.keyword.model.vo.KeywordVo;

public class KeywordService {

	public ArrayList<KeywordVo> selectKeywordList() {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<KeywordVo> list = new KeywordDao().selectKeywordList(con);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int updateKeywordContent(int keyword_no, String keyword_content) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new KeywordDao().updateKeywordContent(con, keyword_no, keyword_content);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

}
