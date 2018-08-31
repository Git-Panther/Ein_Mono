package ein.mono.keyword.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.keyword.model.vo.KeywordVo;

public class KeywordDao {

	public ArrayList<KeywordVo> selectKeywordList(Connection con) {
		ArrayList<KeywordVo> list = new ArrayList<KeywordVo>();
		ResultSet rs = null;
		Statement stmt = null;
		String query = "";
		
		try {
			stmt = con.createStatement();
			
			query = "SELECT KEYWORD_NO, KEYWORD_CONTENT FROM KEYWORD";
			
			rs = stmt.executeQuery(query);
			KeywordVo temp = null;
			while(rs.next()) {
				temp = new KeywordVo();
				
				temp.setKeyword_no(rs.getInt("keyword_no"));
				temp.setKeyword_content(rs.getString("keyword_content"));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}

	public int updateKeywordContent(Connection con, int keyword_no, String keyword_content) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE KEYWORD SET KEYWORD_CONTENT = ? WHERE KEYWORD_NO = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, keyword_content);
			pstmt.setInt(2, keyword_no);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

}
