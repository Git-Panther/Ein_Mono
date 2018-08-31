package ein.mono.board.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import ein.mono.board.model.vo.PostVo;
import ein.mono.common.JDBCTemplate;

public class PostDao {
	Properties prop = new Properties();

	public PostDao(){
		String filename = PostDao.class.getResource("/post/post_sql.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PostVo> selectPostList(Connection con, String post_type, int currentPage, int limit) {
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = prop.getProperty("selectPostList");
		int startRow = (currentPage -1)*limit+1;
		int endRow = startRow + limit -1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, post_type);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rs = pstmt.executeQuery();			
			
			PostVo temp = null;
			while(rs.next()) {
				int num = rs.getInt("num");
				String pCode = rs.getString("post_code");
				int vCount = rs.getInt("views_count");
				String title = rs.getString("title");
				String clobString = readClobData(rs.getCharacterStream("CONT"));
				String nName = rs.getString("member_nname");
				Date wDate = rs.getDate("written_Date");
				
				temp = new PostVo();
				temp.setNum(num);
				temp.setPost_code(pCode);
				temp.setViews_count(vCount);
				temp.setTitle(title);
				temp.setContent(clobString);
				temp.setWriter_nickname(nName);
				temp.setWritten_date(wDate);
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}
	
	public ArrayList<PostVo> selectPostListNonPaging(Connection con, String post_type) {
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = prop.getProperty("selectPostListNonPaging");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, post_type);
			rs = pstmt.executeQuery();			
			
			PostVo temp = null;
			while(rs.next()) {
				String pCode = rs.getString("post_code");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("member_name");
				Date wDate = rs.getDate("written_Date");
				
				temp = new PostVo();
				temp.setPost_code(pCode);
				temp.setTitle(title);
				temp.setContent(content);
				temp.setMember_name(name);
				temp.setWritten_date(wDate);
				
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
	
	
	
	public String selectRecentlyPostCode(Connection con, String postType, String writerCode) { // order by written_date desc해서 그 맨 위의 것을 뽑아옴
		// TODO Auto-generated method stub
		String postCode = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			query = prop.getProperty("selectRecentlyPostCode");
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, writerCode);
			pstmt.setString(2, postType);		
			rs = pstmt.executeQuery();
			while(rs.next()){
				postCode = rs.getString("POST_CODE");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}		
		return postCode;
	}
	
	public ArrayList<PostVo> selectPostListAdmin(Connection con, String post_type, int currentPage, int limit) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		try {
			// post_type과 일치하는 게시글만 가져오기. 
			// 회원 테이블과 조인해서 작성자의 닉네임도 가져오기.
			
			query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, MEMBER_NAME "
					+ "FROM (SELECT ROWNUM AS RNUM, POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, DELFLAG, MEMBER_NAME "
						+ "FROM (SELECT P.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, M.MEMBER_NAME "
							+ "FROM MEMBER M, POST P "
							+ "WHERE M.MEMBER_CODE = P.WRITER_CODE "
							+ "AND P.POST_TYPE = ? "
							+ "AND P.DELFLAG = 'N' "
							+ "ORDER BY TO_NUMBER(SUBSTR(P.POST_CODE, 4)) DESC)) "
					+ "WHERE RNUM BETWEEN ? AND ?";

			pstmt = con.prepareStatement(query);
			
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			
			pstmt.setString(1, post_type);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			PostVo temp = null;
			while(rs.next()) {
				temp = new PostVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
	
	// 검색조건과 키워드로 해당되는 게시글 select
		public ArrayList<PostVo> searchPost(Connection con, int condition, String keyword) {
			ArrayList<PostVo> list = null;
			
	/*		switch(condition) {
			case 1:
				// 제목
				query += "WHERE NTITLE LIKE '%" + keyword + "%'";
				break;
			case 2:
				query += "WHERE NCONTENT LIKE '%" + keyword + "%'";
				break;
			case 3:
				query += "WHERE USERNAME LIKE '%" + keyword + "%'";
				break;
			case 0:
				query += "WHERE (USERNAME LIKE '%" + keyword + "%'";
				query += "OR NCONTENT LIKE '%" + keyword + "%'";
				query += "OR NTITLE LIKE '%" + keyword + "%')";
				break;
			}
	*/		
			
			return list;
		}
	
	public static String readClobData(Reader reader) throws IOException {
        StringBuffer data = new StringBuffer();
        char[] buf = new char[1024];
        int cnt = 0;
        if (null != reader) {
            while ( (cnt = reader.read(buf)) != -1) {
                data.append(buf, 0, cnt);
            }
        }
        return data.toString();
    }
	
	public PostVo selectPost(Connection con, String post_code, String post_type) {
		// post_code를 where조건에서 사용하여 게시글 하나 select해서 리턴
		PostVo post = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "SELECT POST_CODE, POST_TYPE, WRITER_CODE, TITLE, CONTENT, VIEWS_COUNT, WRITTEN_DATE, MEMBER_NAME, MEMBER_NNAME "
				+ "FROM POST P, MEMBER M "
				+ "WHERE P.WRITER_CODE = M.MEMBER_CODE "
				+ "AND POST_CODE = ? AND POST_TYPE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, post_code);
			pstmt.setString(2, post_type);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				post = new PostVo();
				
				post.setPost_code(rs.getString("post_code"));
				post.setPost_type(rs.getString("post_type"));
				post.setMember_name(rs.getString("member_name"));
				post.setWriter_nickname(rs.getString("member_nname"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setViews_count(rs.getInt("views_count"));
				post.setWritten_date(rs.getDate("written_date"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		try {
			pstmt = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return post;
	}
	
	public int updatePost(Connection con, PostVo post, String title, String content) {
		// post 내용 변경.
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE POST SET TITLE = ?, CONTENT = ? WHERE POST_CODE = ? AND POST_TYPE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, post.getPost_code());
			pstmt.setString(4, post.getPost_type());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int deletePost(Connection con, String post_code, String post_type) {
		// post 내용 변경.
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE POST SET DELFLAG = 'Y' WHERE POST_CODE = ? AND POST_TYPE = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, post_code);
			pstmt.setString(2, post_type);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	// 가장 최신 게시글의 코드 가져옴
		public String selectPostCode(Connection con) {
			PreparedStatement pstmt = null;
			String query = null;
			ResultSet rs = null;
			String postCode = null;
			
			try {
				// post_type과 일치하는 게시글만 가져오기. 
				// 회원 테이블과 조인해서 작성자의 닉네임도 가져오기.
				
				query = "SELECT POST_CODE "
						+ "FROM POST "
						+ "WHERE POST_TYPE = 'ADV' "
						+ "ORDER BY SUBSTR(POST_CODE, 4) DESC";

				pstmt = con.prepareStatement(query);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					postCode = rs.getString("post_code");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// close 처리
				JDBCTemplate.close(rs);
				JDBCTemplate.close(pstmt);
			}
			return postCode;
		}
		
		public ArrayList<PostVo> selectPostList(Connection con, String post_type) {
			ArrayList<PostVo> list = new ArrayList<PostVo>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "";
			
			query = prop.getProperty("selectPostList");
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, post_type);
				rs = pstmt.executeQuery();			
				
				PostVo temp = null;
				while(rs.next()) {
					int num = rs.getInt("num");
					String pCode = rs.getString("post_code");
					String title = rs.getString("title");
					String clobString = readClobData(rs.getCharacterStream("CONT"));
					String nName = rs.getString("member_nname");
					Date wDate = rs.getDate("written_Date");
					
					temp = new PostVo();
					temp.setNum(num);
					temp.setPost_code(pCode);
					temp.setTitle(title);
					temp.setContent(clobString);
					temp.setWriter_nickname(nName);
					temp.setWritten_date(wDate);
					
					list.add(temp);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rs);
				JDBCTemplate.close(pstmt);
			}
			
			return list;
		}

	public PostVo selectPost(Connection con, String post_code) {
		PostVo post = new PostVo();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		System.out.println(post_code);
		query = prop.getProperty("selectPost");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, post_code);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String pType = rs.getString("post_type");
				String nName = rs.getString("member_nname");
				String mCdoe = rs.getString("member_code");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int vCount = rs.getInt("views_count");
				Date wDate = rs.getDate("written_date");
				
				post.setPost_code(post_code);
				post.setPost_type(pType);
				post.setWriter_nickname(nName);
				post.setWriter_code(mCdoe);
				post.setTitle(title);
				post.setContent(content);
				post.setViews_count(vCount);
				post.setWritten_date(wDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return post;
	}
	public int updatePost(Connection con, PostVo post) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		String pCode = post.getPost_code();
		String title = post.getTitle();
		String content = post.getContent();
		
		query = prop.getProperty("updatePost");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, pCode);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int insertPost(Connection con, PostVo p) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		//System.out.println(p.toString());
		String pType = p.getPost_type();
		//System.out.println(pType);
		String mCode = p.getWriter_code();
		String title = p.getTitle();
		String content = p.getContent();
		
		query = prop.getProperty("insertPost");
		String sequence = "";
		switch(pType){
		case "SHO":
			sequence = "'"+pType+"'||POST_SHOW_SEQ.NEXTVAL";
			break;
		case "FRE":
			sequence = "'"+pType+"'||POST_FREE_SEQ.NEXTVAL";
			break;
		case "MAR":
			sequence = "'"+pType+"'||POST_TRADE_SEQ.NEXTVAL";
			break;
		case "REV":
			sequence = "'"+pType+"'||POST_REVIEW_SEQ.NEXTVAL";
			break;
		case "QNA":
			sequence = "'"+pType+"'||POST_QNA_SEQ.NEXTVAL";
			break;
		case "FAQ":
			sequence = "'"+pType+"'||POST_FAQ_SEQ.NEXTVAL";
			break;
		case "ADV":
			sequence = "'"+pType+"'||POST_ADV_SEQ.NEXTVAL";
			break;
		}
		
		query = query.replaceAll("SEQ", sequence);
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pType);
			pstmt.setString(2, mCode);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			//System.out.println(pstmt.toString());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}
//		if(result == 1){
//			query = "SELECT CONTENT FROM POST WHERE POST_CODE = ? FOR UPDATE";
//			try {
//				pstmt = con.prepareStatement(query);
//				pstmt.setString(1, mCode);
//				ResultSet rs = pstmt.executeQuery();
//				
//				String strClob = content;
//				if(rs.next()){
//					CLOB clob = ((OracleResultSet)rs).getCLOB("content");
//					Writer writer = clob.getCharacterOutputStream();
//					Reader reader = new CharArrayReader(strClob.toCharArray());
//					char[] buffer = new char[1024];
//					int read = 0;
//					
//					try {
//						while((read = reader.read(buffer, 0, 1024)) != -1){
//							writer.write(buffer,0,read);
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					} finally{
//						try {
//							reader.close();
//							writer.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
				
		return result;
	}

	public int deletePost(Connection con, String pCode) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = prop.getProperty("deletePost");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pCode);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateVCount(Connection con, String pCode) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		query = prop.getProperty("updateVCount");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pCode);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}	
	
	public ArrayList<PostVo> selectPostListAdmin(Connection con, String post_type) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		try {
			// post_type과 일치하는 게시글만 가져오기. 
			// 회원 테이블과 조인해서 작성자의 닉네임도 가져오기.
			
			query = "SELECT P.POST_CODE, P.POST_TYPE, P.WRITER_CODE, P.TITLE, P.CONTENT, P.VIEWS_COUNT, P.WRITTEN_DATE, P.DELFLAG, M.MEMBER_NAME "
							+ "FROM MEMBER M, POST P "
							+ "WHERE M.MEMBER_CODE = P.WRITER_CODE "
							+ "AND P.POST_TYPE = ? "
							+ "AND P.DELFLAG = 'N' "
							+ "ORDER BY TO_NUMBER(SUBSTR(P.POST_CODE, 4)) DESC";

			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, post_type);
			
			rs = pstmt.executeQuery();
			
			// 결과 처리(select -> resultSet)
			PostVo temp = null;
			while(rs.next()) {
				temp = new PostVo();
				
				temp.setPost_code(rs.getString("post_code"));
				temp.setPost_type(rs.getString("post_type"));
				temp.setWriter_code(rs.getString("writer_code"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setViews_count(rs.getInt("views_count"));
				temp.setWritten_date(rs.getDate("written_date"));
				temp.setDel_flag(rs.getString("delflag"));
				temp.setMember_name(rs.getString("member_name"));
				
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close 처리
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
	
	public int selectPostTotalCount(Connection con, String post_type) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) AS COUNT FROM POST WHERE POST_TYPE = ? AND DELFLAG = 'N'";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, post_type);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int selectBoardTotalCount(Connection con, String post_type) {
		int result = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		query = prop.getProperty("selectBoardTotalCount");
		try {
			pstmt= con.prepareStatement(query);
			pstmt.setString(1, post_type);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				result = rs.getInt("listcount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}


}
