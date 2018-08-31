package ein.mono.mypage.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import ein.mono.board.model.vo.PostVo;
import ein.mono.common.JDBCTemplate;
import ein.mono.qna.model.vo.QnAVo;
import ein.mono.report.model.vo.ReportVo;
import ein.mono.request.model.vo.RequestVo;

public class MypageDao {
	Properties prop = new Properties();
	public MypageDao(){
		String filename = MypageDao.class.getResource("/mypage/mypage_sql.properties").getPath();
		try {
			prop.load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList selectFBList(Connection con,String mCode,String fbType){
		return null;
	}
	public int deleteFB(Connection con,String mCode,String tCode, String fbType){
		return 0;
	}
	public ArrayList<PostVo> selectPostList(Connection con,String mCode, int currentPage, int limit){
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		query = prop.getProperty("selectPostList");
		int startRow = (currentPage -1)*limit+1;
		int endRow = startRow + limit -1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs = pstmt.executeQuery();
			PostVo temp = null;
			while(rs.next()){
				String title = rs.getString("TITLE");
				String pCode = rs.getString("post_code");
				String type = rs.getString("POST_TYPE");
				int vCount = rs.getInt("VIEWS_COUNT");
				Date wDate = rs.getDate("WRITTEN_DATE");
				int num = rs.getInt("num");
				temp = new PostVo(title,vCount,wDate);
				temp.setPost_code(pCode);
				temp.setPost_type(type);
				temp.setNum(num);
				
				list.add(temp);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		System.out.println(list.size());
		return list;
	}
	
	public ArrayList<PostVo> selectQnaList(Connection con,String mCode, int currentPage, int limit){
		ArrayList<PostVo> list = new ArrayList<PostVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		
		int startRow = (currentPage -1)*limit+1;
		int endRow = startRow + limit -1;
		
		query = prop.getProperty("selectQnaList");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rs = pstmt.executeQuery();
			PostVo temp = null;
			while(rs.next()){
				String qCode = rs.getString("post_code");
				String title = rs.getString("title");
				String nName = rs.getString("member_nname");
				int vCount = rs.getInt("views_count");
				Date wDate = rs.getDate("written_date");
				
				temp = new PostVo();
				temp.setPost_code(qCode);
				temp.setTitle(title);
				temp.setWriter_nickname(nName);
				temp.setViews_count(vCount);
				temp.setWritten_date(wDate);
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public ArrayList<RequestVo> selectOderList(Connection con, String mCode, String reqType) {
		ArrayList<RequestVo> list = new ArrayList<RequestVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		String cAddress;
		String Name;
		String rCode;
		int acreage;
		String bCheck="";
		String reqCheck;
		
		if("경매".equals(reqType)){
			query = prop.getProperty("selectActList");			
		}else{
			query = prop.getProperty("selectReqList");
		}
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			pstmt.setString(2, reqType);
			
			rs = pstmt.executeQuery();
			RequestVo temp = null;
			while(rs.next()){
				if("경매".equals(reqType)){
					cAddress = rs.getString("const_address");
					Name = rs.getString("member_name");
					rCode = rs.getString("req_code");
					acreage = rs.getInt("acreage");
					bCheck = rs.getString("bid_check");
					reqCheck = rs.getString("REQ_CHECK");					
				}else{
					cAddress = rs.getString("const_address");
					Name = rs.getString("member_name");
					rCode = rs.getString("req_code");
					acreage = rs.getInt("acreage");
					reqCheck = rs.getString("REQ_CHECK");
				}
				
				temp = new RequestVo();
				temp.setConstAddress(cAddress);
				temp.setReqCode(rCode);
				temp.setUserName(Name);
				temp.setAcreage(acreage);
				temp.setBidCheck(bCheck);
				temp.setReqCheck(reqCheck);
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}	
		
		return list;
	}

	public int insertPhoto(Connection con, String mCode,String newName, String oldName) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		
		query = prop.getProperty("insertPhoto");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			pstmt.setString(2, oldName);
			pstmt.setString(3, newName);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deletePhoto(Connection con, String mCode, String chk) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "";
		String[] chkList = chk.split(",");
		int num=0;
		
		try {
			for(int i=0;i<chkList.length;i++){
				query = prop.getProperty("deletePhoto");
				num = Integer.parseInt(chkList[i]);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, mCode);
				pstmt.setInt(2, num);
				result += pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public HashMap<Integer, String> selectConstPhoto(Connection con, String ptnCode) {
		HashMap<Integer, String> ptnPhoto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			//1. 쿼리 전송 객체 생성
			query = prop.getProperty("selectConstPhoto");
			pstmt = con.prepareStatement(query);
			//2. 쿼리 작성
			pstmt.setString(1, ptnCode);
			//3. 쿼리 실행
			rs = pstmt.executeQuery();
			//4. 결과 처리(resultSet-list parsing)
			ptnPhoto = new HashMap<Integer, String>();
			while(rs.next()){
				ptnPhoto.put(rs.getInt("PHOTO_NUM"), rs.getString("PHOTO_NEWNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//5. 자원 반납(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		//6. 결과 리턴	
		return ptnPhoto;
	}

	public ArrayList<RequestVo> selectRequestList(Connection con, String mCode) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RequestVo> list = new ArrayList<RequestVo>();
		String query = "";
		
		query = prop.getProperty("selectRequestList");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mCode);
			
			rs = pstmt.executeQuery();
			RequestVo temp = null;
			while(rs.next()){
				String rCode = rs.getString("req_code");
				String rType = rs.getString("req_type"); 
				String address = rs.getString("const_address");
				String Name = rs.getString("member_name");
				
				temp = new RequestVo();
				temp.setReqCode(rCode);
				temp.setReqType(rType);
				temp.setConstAddress(address);
				temp.setUserName(Name);
				
				list.add(temp);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int selectBoardTotalCount(Connection con) {
		int result = -1;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		try {
			stmt = con.createStatement();
			query = "SELECT COUNT(*) AS LISTCOUNT FROM POST WHERE DELFLAG !='Y'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				result = rs.getInt("listcount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		return result;
	}

	public int modifyReqCheck(Connection con, String rChk, String rCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "";
		
		query = prop.getProperty("modifyReqCheck");
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, rChk);
			pstmt.setString(2, rCode);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCTemplate.close(pstmt);			
		}
		
		return result;
	}
}
