package ein.mono.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;


import ein.mono.board.model.dao.PostDao;
import ein.mono.board.model.vo.PostVo;
import ein.mono.board.model.vo.ReplyVo;
import ein.mono.common.JDBCTemplate;

public class PostService {
	public ArrayList<PostVo> selectPostList(String post_type, int currentPage, int limit){
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PostVo> list = new PostDao().selectPostList(con, post_type,currentPage,limit);
		
		JDBCTemplate.close(con);
		
		return list;
	}

	public PostVo selectPost(String post_code) {
		Connection con = JDBCTemplate.getConnection();
		
		PostVo post = new PostDao().selectPost(con, post_code);
		
		JDBCTemplate.close(con);
		
		return post;
	}

	public int insertPost(PostVo post) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().insertPost(con, post);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		JDBCTemplate.close(con);
		
		return result;
	}
	public int updatePost(PostVo post) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().updatePost(con, post);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		return result;
	}
	public int selectPostTotalCount(String post_type) {
		
		//1. Ŀ�ؼ� ����
				Connection con = JDBCTemplate.getConnection();
				//2. dao �޼ҵ� ȣ��
				int listCount = new PostDao().selectBoardTotalCount(con,post_type);
				//3. �ڿ� �ݳ�(close)
				JDBCTemplate.close(con);
				//4. �ش� ��� ����
				return listCount;
		
		
		
	}

	public PostVo getPost(String pCode) {
		Connection con = JDBCTemplate.getConnection();
		
		PostVo result = new PostDao().selectPost(con, pCode);
		
		if(null != result){
			int up = new PostDao().updateVCount(con,pCode);
			if(0 < up){
				JDBCTemplate.commit(con);
			}else{
				JDBCTemplate.rollback(con);
			}
		}
		
		JDBCTemplate.close(con);
		
		return result;
	}

	public int removePost(String pCode) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().deletePost(con,pCode);
		
		if(0 < result){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		return result;
	}

	public int updateVCount(String pCode) {
		Connection con = JDBCTemplate.getConnection();
		
		int up = new PostDao().updateVCount(con,pCode);
		
		if(0 < up){
			JDBCTemplate.commit(con);
		}else{
			JDBCTemplate.rollback(con);
		}
		
		JDBCTemplate.close(con);
		
		return up;
	}

	public PostVo updatePost(String pCode) {
		Connection con = JDBCTemplate.getConnection();
		
		PostVo post = new PostDao().selectPost(con, pCode);
		
		JDBCTemplate.close(con);
		return post;
	}
	
	public ArrayList<PostVo> selectPostListAdmin(String post_type, int currentPage, int limit){
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PostVo> boardList = new PostDao().selectPostListAdmin(con, post_type, currentPage, limit);
		
		JDBCTemplate.close(con);
		
		return boardList;
	}
	public ArrayList<PostVo> selectPostListNonPaging(String post_type){
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PostVo> boardList = new PostDao().selectPostListNonPaging(con, post_type);
		
		JDBCTemplate.close(con);
		
		return boardList;
	}
	public PostVo selectPost(String post_code, String post_type) {
		Connection con = JDBCTemplate.getConnection();
		
		PostVo post = new PostDao().selectPost(con, post_code, post_type);
		
		JDBCTemplate.close(con);
		
		return post;
	}

	public int deletePost(String post_code) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().deletePost(con, post_code);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		return result;

	}

	public int updatePost(PostVo post, String title, String content) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().updatePost(con, post, title, content);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		return result;
	}

	public ArrayList<PostVo> searchPost(int condition, String keyword) {
		Connection con = JDBCTemplate.getConnection();

		ArrayList<PostVo> postList = new PostDao().searchPost(con, condition, keyword);
		
		JDBCTemplate.close(con);
		
		return postList;
	}

	public int deletePost(String post_code, String post_type) {
		Connection con = JDBCTemplate.getConnection();
		
		int result = new PostDao().deletePost(con, post_code, post_type);
		
		if(0 < result) {
			JDBCTemplate.commit(con);
		} else {
			JDBCTemplate.rollback(con);
		}
		
		return result;
	}

	// 메인의 faq 메뉴버튼 눌렀을 때 (페이징x)
	public ArrayList<PostVo> selectPostListAdmin(String post_type) {
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PostVo> boardList = new PostDao().selectPostListAdmin(con, post_type);
		
		JDBCTemplate.close(con);
		
		return boardList;
	}

	public String selectPostCode() {
		Connection con = JDBCTemplate.getConnection();
		
		String postCode = new PostDao().selectPostCode(con);
		
		JDBCTemplate.close(con);
		
		return postCode;
	}

private PostDao pd = new PostDao();
	
	public ArrayList<PostVo> selectPostList(String post_type){
		Connection con = JDBCTemplate.getConnection();
		
		ArrayList<PostVo> list = new PostDao().selectPostList(con, post_type);
		
		JDBCTemplate.close(con);
		
		return list;
	}
	
	public ArrayList<ReplyVo> selectReplyList(String postCode) {
		// TODO Auto-generated method stub
		return null;
	}



	

	
}
