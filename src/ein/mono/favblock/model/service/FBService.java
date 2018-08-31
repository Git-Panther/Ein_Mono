package ein.mono.favblock.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ein.mono.common.JDBCTemplate;
import ein.mono.favblock.model.dao.FBDao;
import ein.mono.favblock.model.vo.FBVo;
import ein.mono.member.model.vo.MemberVo;


public class FBService {

	public ArrayList<MemberVo> selectFBList(String member_code, String fb_type) {
		
		
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<MemberVo> list = new FBDao().selectFBList(con, member_code, fb_type);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int deleteFB(String member_code, String target_code) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new FBDao().deleteFB(con, member_code, target_code);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}
	
	public int updateLike(FBVo LB) {
		Connection con = JDBCTemplate.getConnection();
		int result = new FBDao().insertFav(con, LB);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int updateBlock(FBVo LB) {
		Connection con = JDBCTemplate.getConnection();
		int result = new FBDao().updateBlock(con, LB);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<FBVo> selectLBList(String userCode) {
		
		
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<FBVo> list = new FBDao().selectLBList(con, userCode);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public int removeBoard(String id) {
		Connection con = JDBCTemplate.getConnection();
		int result = new FBDao().deleteFB(con, id);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int insertFavorBlock(FBVo fb) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		int result = new FBDao().insertFavorBlock(con, fb);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int deleteFavorBlock(FBVo fb) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();
		int result = new FBDao().deleteFavorBlock(con, fb);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int selectFavorCount(String partnerCode) {
		// TODO Auto-generated method stub
		Connection con = JDBCTemplate.getConnection();		
		int favorCount = new FBDao().selectFavorCount(con, partnerCode);		
		JDBCTemplate.close(con);	
		return favorCount;
	}

	public List<FBVo> selectLBList(String mCode, String fb_type) {
		Connection con = JDBCTemplate.getConnection();
		ArrayList<FBVo> list = new FBDao().selectFBList2(con, mCode, fb_type);
		
		return list;
	}
	
}
