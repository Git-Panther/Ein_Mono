package ein.mono.board.model.vo;

import java.util.Date;

public class ReplyVo {
	private String reply_code;
	private String writer_code;
	private String post_code;
	private String reply_content;
	private Date reply_date;
	private String member_nName;
	private String reply_dateSte;
	
	
	public String getMember_nName() {
		return member_nName;
	}

	public void setMember_nName(String member_nName) {
		this.member_nName = member_nName;
	}

	public ReplyVo() {
		super();
	}

	public ReplyVo(String writer_code, String post_code, String reply_content) {
		super();
		this.writer_code = writer_code;
		this.post_code = post_code;
		this.reply_content = reply_content;
	}

	public String getReply_code() {
		return reply_code;
	}

	public void setReply_code(String reply_code) {
		this.reply_code = reply_code;
	}

	public String getWriter_code() {
		return writer_code;
	}

	public void setWriter_code(String writer_code) {
		this.writer_code = writer_code;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public Date getReply_date() {
		return reply_date;
	}

	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	
	

	public String getReply_dateSte() {
		return reply_dateSte;
	}

	public void setReply_dateSte(String reply_dateSte) {
		this.reply_dateSte = reply_dateSte;
	}

	@Override
	public String toString() {
		return "ReplyVo [reply_code=" + reply_code + ", writer_code=" + writer_code + ", post_code=" + post_code
				+ ", reply_content=" + reply_content + ", reply_date=" + reply_date + ", member_nName=" + member_nName
				+ "]";
	}

	public void setWriteDate(java.sql.Date date) {
		// TODO Auto-generated method stub
		
	}

	public void setDelFlag(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
