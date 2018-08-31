package ein.mono.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import ein.mono.common.JDBCTemplate;
import ein.mono.member.model.dao.MemberDao;
import ein.mono.member.model.vo.MemberVo;

public class MemberService {

	public int joinMember(MemberVo member, String mRank) {
		
		Connection con = JDBCTemplate.getConnection();
		int result = new MemberDao().joinMember(con, member, mRank);
		int ptnResult = 0;
		if(0 < result) {
			JDBCTemplate.commit(con);
			if(mRank.equals("2")) {
				MemberVo findcode = new MemberDao().findcode(con, member);
				//System.out.println(findcode);
				ptnResult = new MemberDao().insertPtn(con, findcode);
				
				if(0<ptnResult) {
					JDBCTemplate.commit(con);
				}else {
					JDBCTemplate.rollback(con);
				}
			}
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
	
		if(mRank.equals("2")) {
			if(0 < result && 0 < ptnResult)
				return 1;
			else
				return 0;
		}else {
			if(0 < result)
				return 1;
			else
				return 0;
		}
}
	public MemberVo loginMember(String id ) {
		
		Connection con = JDBCTemplate.getConnection(); 
		
		MemberVo result = new MemberDao().loginMember(con, id);
		
			JDBCTemplate.close(con);
		
			return result;
	}

	public int updateMember(MemberVo member) {
		
		Connection con = JDBCTemplate.getConnection();
		int result = new MemberDao().updateMember(con, member);
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int deleteMember(MemberVo member) {
		
		Connection con = JDBCTemplate.getConnection();
		int result = new MemberDao().deleteMember(con, member);
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public MemberVo selectMember(String member_code) {
		Connection con = JDBCTemplate.getConnection();

		MemberVo result = new MemberDao().selectMember(con, member_code);

		JDBCTemplate.close(con);

		return result;
	}

	public int selectMemberTotalCount(int option) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new MemberDao().selectMemberTotalCount(con, option);
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<MemberVo> selectMemberList(int currentPage, int limit, int selectOption) {
		Connection con = JDBCTemplate.getConnection();

		ArrayList<MemberVo> result = new MemberDao().selectMemberList(con, currentPage, limit, selectOption);

		JDBCTemplate.close(con);

		return result;
	}

	public int updateMemberRank(String member_code, String member_rank) {
		Connection con = JDBCTemplate.getConnection();
		int result = new MemberDao().updateMemberRank(con, member_code, member_rank);
		if(0 < result) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int selectMemberConTotalCount(int condition, String searchContent) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new MemberDao().selectMemberTotalCount(con, condition, searchContent);
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public ArrayList<MemberVo> selectMemberListCon(int currentPage, int limit, int condition, String searchContent) {
		Connection con = JDBCTemplate.getConnection();

		ArrayList<MemberVo> result = new MemberDao().selectMemberList(con, currentPage, limit, condition, searchContent);

		JDBCTemplate.close(con);

		return result;
	}

	public String selectMemberCode(String ptnName) {
		Connection con = JDBCTemplate.getConnection();

		String ptnCode = new MemberDao().selectMemberCode(con, ptnName);

		JDBCTemplate.close(con);

		return ptnCode;
	}

	public int pwdChangeMember(String id, String memberPwd) {
		Connection con = JDBCTemplate.getConnection();
		int pwdChange = new MemberDao().pwdChangeMember(con,id, memberPwd);
		if(0 < pwdChange) {
			JDBCTemplate.commit(con);
		}else {
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		return pwdChange;
	}

	

	
}
