package ein.mono.board.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import ein.mono.board.model.vo.ReplyVo;
import ein.mono.common.JDBCTemplate;

public class ReplyDao {
	Properties prop = new Properties();

	public ReplyDao(){
		String filename = ReplyDao.class.getResource("/relpy/reply_sql.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ReplyVo> selectReplyList(Connection con, String post_code) {
		ArrayList<ReplyVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			// post_code瑜� 鍮꾧탳�빐�꽌 �빐�떦 寃뚯떆湲��쓽 �뙎湲�留� 媛��졇���빞�븿
			query = prop.getProperty("selectReplyList");  

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, post_code);
				
			rs = pstmt.executeQuery();
			
			list = new ArrayList<ReplyVo>();
			ReplyVo temp = null;
			while(rs.next()) {
				String rCode = rs.getString("Reply_code");
				String rContent = rs.getString("reply_content");
				String mCode = rs.getString("member_code");
				String rDate = rs.getString("reply_Date");
				String nName = rs.getString("member_nname");
				temp = new ReplyVo();
				temp.setReply_code(rCode);
				temp.setPost_code(post_code);
				temp.setWriter_code(mCode);
				temp.setReply_content(rContent);
				temp.setReply_dateSte(rDate);
				temp.setMember_nName(nName);
				
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public int insertReply(Connection con, ReplyVo reply) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		String mCode =reply.getWriter_code();
		String content = reply.getReply_content();
		String pCode = reply.getPost_code();
		
		query = prop.getProperty("insertReply");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			pstmt.setString(2, pCode);
			pstmt.setString(3, content);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateReply(Connection con, ReplyVo reply) {
		int result = 0;

		return result;
	}

	public int deleteReply(Connection con, String reply_code) {
		int result = 0;		
		PreparedStatement pstmt = null;				
		String query = "";
		
		query = prop.getProperty("deleteReply");
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, reply_code);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	
}
