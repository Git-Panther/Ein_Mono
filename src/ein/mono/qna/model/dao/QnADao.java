package ein.mono.qna.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import ein.mono.common.JDBCTemplate;
import ein.mono.qna.model.vo.QnAVo;
import ein.mono.request.model.dao.RequestDao;

public class QnADao {
	private Properties prop = new Properties();
	
	public QnADao() {
		String filename = RequestDao.class.getResource("/partners/qna_sql.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<QnAVo> selectQnaList(Connection con){
		return null;
	}
	public QnAVo selectQna(Connection con,String qnaCode){
		return null;
	}
	public int insertQuestion(Connection con,QnAVo question){
		return 0;
	}
	
	public int insertAnswer(Connection con,QnAVo answer){
		return 0;
	}
	public ArrayList<QnAVo> selectQnAList(Connection con, String partnerCode, int currentPage, int limit) {
		// TODO Auto-generated method stub
		ArrayList<QnAVo> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectQnAList");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			int startRow = (currentPage - 1) * limit + 1; 
			int endRow = startRow + limit - 1;	
			pstmt.setString(1, partnerCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			list = new ArrayList<QnAVo>();
			QnAVo temp = null;
			while(rs.next()){
				temp = new QnAVo();
				temp.setPost_code(rs.getString("POST_CODE"));
				temp.setTitle(rs.getString("TITLE"));
				temp.setWritten_date(rs.getDate("WRITTEN_DATE"));
				temp.setReply_count(rs.getInt("REPLYCOUNT"));
				temp.setWriter_code(rs.getString("WRITER_CODE"));
				temp.setWriter_name(rs.getString("WRITER_NAME"));
				temp.setWriter_nickname(rs.getString("WRITER_NNAME"));
				temp.setPtnCode(rs.getString("PTN_CODE"));
				temp.setPtnName(rs.getString("PTN_NAME"));
				temp.setViews_count(rs.getInt("VIEWS_COUNT"));
				temp.setPost_num(rs.getInt("RNUM"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return list;
	}
	public int selectQnAListTotalCount(Connection con, String partnerCode) {
		// TODO Auto-generated method stub
		int listCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectQnAListTotalCount");
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, partnerCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				listCount = rs.getInt("LISTCOUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return listCount;
	}

	public int selectQnAListTotalCount(Connection con, String partnerCode, String condition, String keyword) {
		// TODO Auto-generated method stub
		int listCount = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectQnAListTotalCountByKeyword");
			//System.out.println(condition);
			switch(condition) {
			case "writer_name,writer_nname":
				query = query.replaceAll("C1", "LOWER(WRITER_NNAME) LIKE ? OR LOWER(WRITER_NAME) LIKE ?");
				break;
			case "content":
				//query = query.replaceAll("C1", "CONTAINS(CONTENT, ?) > 0");
				query = query.replaceAll("C1", "LOWER(" + condition + ") LIKE LOWER(?)");
				break;
			default:
				query = query.replaceAll("C1", "LOWER(" + condition + ") LIKE LOWER(?)");
				break;
			}
			//System.out.println(query);
			pstmt = con.prepareStatement(query);
			
			int inputIndex = 0; // 넣을 자리
			pstmt.setString(++inputIndex, partnerCode); // 1
			pstmt.setString(++inputIndex, "%" + keyword.toLowerCase() + "%"); // 2
	
			if(condition.equals("writer_name,writer_nname")) {
				pstmt.setString(++inputIndex, "%" + keyword.toLowerCase() + "%"); // 3
			}	
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				listCount = rs.getInt("LISTCOUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return listCount;
	}

	public ArrayList<QnAVo> selectQnAListByKeyword(Connection con, String partnerCode, String condition, String keyword,
			int currentPage, int limit) {
		// TODO Auto-generated method stub
		ArrayList<QnAVo> list = null;
		// 쿼리는 우수 업체 TOP 3, 내 지역에 가까운 업체 이름 오름차순 TOP 3, 전체 리스트 중 이름 오름차순 TOP 3(스타일 TOP 3 할수도) 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectQnAListByKeyword");
			int startRow = (currentPage - 1) * limit + 1; 
			int endRow = startRow + limit - 1;
			//System.out.println(condition);
			switch(condition) {
			case "writer_name,writer_nname":
				query = query.replaceAll("C1", "LOWER(WRITER_NNAME) LIKE ? OR LOWER(WRITER_NAME) LIKE ?");
				break;
			case "content":
				//query = query.replaceAll("C1", "(CONTAINS(CONTENT, ?) > 0)");
				query = query.replaceAll("C1", "LOWER(" + condition + ") LIKE LOWER(?)");
				break;
			default:
				query = query.replaceAll("C1", "LOWER(" + condition + ") LIKE LOWER(?)");
				break;
			}
			//System.out.println(query);
			pstmt = con.prepareStatement(query);
			int inputIndex = 0; // ? 들어갈 번호
						
			//2. 쿼리 작성
			pstmt.setString(++inputIndex, partnerCode); // 1
			pstmt.setString(++inputIndex, "%" + keyword.toLowerCase() + "%"); // 2
			if(condition.equals("writer_name,writer_nname")) {
				pstmt.setString(++inputIndex, "%" + keyword.toLowerCase() + "%"); // 3
			}			
			pstmt.setInt(++inputIndex, startRow); // 3 또는 4
			pstmt.setInt(++inputIndex, endRow); // 4 또는 5
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			list = new ArrayList<QnAVo>();
			QnAVo temp = null;
			while(rs.next()){
				temp = new QnAVo();
				temp.setTitle(rs.getString("TITLE"));
				temp.setPost_code(rs.getString("POST_CODE"));
				temp.setWritten_date(rs.getDate("WRITTEN_DATE"));
				temp.setReply_count(rs.getInt("REPLYCOUNT"));
				temp.setWriter_code(rs.getString("WRITER_CODE"));
				temp.setWriter_name(rs.getString("WRITER_NAME"));
				temp.setWriter_nickname(rs.getString("WRITER_NNAME"));
				temp.setPtnCode(rs.getString("PTN_CODE"));
				temp.setPtnName(rs.getString("PTN_NAME"));
				temp.setViews_count(rs.getInt("VIEWS_COUNT"));	
				temp.setPost_num(rs.getInt("RNUM"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return list;
	}

	public int insertPtnQnA(Connection con, QnAVo qna) {
		// TODO Auto-generated method stub
		int result = 0;
		String query = null;
		PreparedStatement pstmt = null;
		try {
			query = prop.getProperty("insertPtnQnA");
			//System.out.println(query);
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, qna.getPtnCode());
			pstmt.setString(2, qna.getPost_code());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}		
		return result;
	}

}
