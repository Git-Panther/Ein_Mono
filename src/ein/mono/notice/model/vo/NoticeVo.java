package ein.mono.notice.model.vo;

import java.util.Date;

import ein.mono.member.model.vo.MemberVo;

public class NoticeVo extends MemberVo{

	private String postCode;
	private String postType;
	private String writerCode;
	private String title;
	private String content;
	private int viewsCount;
	private Date writtenDate;
	private String delflag;

	
	public NoticeVo(){}


	public NoticeVo(String postCode, String postType, String writerCode, String title, String content, int viewsCount,
			Date writtenDate, String delflag) {
		super();
		this.postCode = postCode;
		this.postType = postType;
		this.writerCode = writerCode;
		this.title = title;
		this.content = content;
		this.viewsCount = viewsCount;
		this.writtenDate = writtenDate;
		this.delflag = delflag;
	}
	
	
	
	


	public NoticeVo(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}


	public NoticeVo(String writerCode, String title, String content) {
		super();
		this.writerCode = writerCode;
		this.title = title;
		this.content = content;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public String getPostType() {
		return postType;
	}


	public void setPostType(String postType) {
		this.postType = postType;
	}


	public String getWriterCode() {
		return writerCode;
	}


	public void setWriterCode(String writerCode) {
		this.writerCode = writerCode;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getViewsCount() {
		return viewsCount;
	}


	public void setViewsCount(int viewsCount) {
		this.viewsCount = viewsCount;
	}


	public Date getWrittenDate() {
		return writtenDate;
	}


	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}


	public String getDelflag() {
		return delflag;
	}


	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}


	@Override
	public String toString() {
		return "NoticeVo [postCode=" + postCode + ", postType=" + postType + ", writerCode=" + writerCode + ", title="
				+ title + ", content=" + content + ", viewsCount=" + viewsCount + ", writtenDate=" + writtenDate
				+ ", delflag=" + delflag + "]";
	}
	
	
}

