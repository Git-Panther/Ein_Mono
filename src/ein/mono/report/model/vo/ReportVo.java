package ein.mono.report.model.vo;

import java.util.Date;

import ein.mono.member.model.vo.MemberVo;

public class ReportVo extends MemberVo{

	private String reportCode; // 신고코드
	private String reporterCode; //신고자코드
	private String reportedCode; //신고대상자 코드
	private String postCode; //게시글코드
	private String replyCode; //덧글코드
	private String reportReason; //신고이유
	private String adminCheck; //관리자 확인
	private Date reportDate;//신고일
	private Date banstartDate; //정지시작일
	private Date banendDate; //정지종료일
	private Date sysdate;
	
	public ReportVo() {}

	public ReportVo(String reportCode, String reporterCode, String reportedCode, String postCode, String replyCode,
			String reportReason, String adminCheck, Date reportDate, Date banstartDate, Date banendDate) {
		super();
		this.reportCode = reportCode;
		this.reporterCode = reporterCode;
		this.reportedCode = reportedCode;
		this.postCode = postCode;
		this.replyCode = replyCode;
		this.reportReason = reportReason;
		this.adminCheck = adminCheck;
		this.reportDate = reportDate;
		this.banstartDate = banstartDate;
		this.banendDate = banendDate;
	}
	
	

	public ReportVo(String reporterCode, String reportedCode, String postCode, String replyCode, String reportReason) {
		super();
		this.reporterCode = reporterCode;
		this.reportedCode = reportedCode;
		this.postCode = postCode;
		this.replyCode = replyCode;
		this.reportReason = reportReason;
	}
	
	

	public ReportVo(Date banstartDate, Date banendDate, Date sysdate) {
		super();
		this.banstartDate = banstartDate;
		this.banendDate = banendDate;
		this.sysdate = sysdate;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getReporterCode() {
		return reporterCode;
	}

	public void setReporterCode(String reporterCode) {
		this.reporterCode = reporterCode;
	}

	public String getReportedCode() {
		return reportedCode;
	}

	public void setReportedCode(String reportedCode) {
		this.reportedCode = reportedCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public String getAdminCheck() {
		return adminCheck;
	}

	public void setAdminCheck(String adminCheck) {
		this.adminCheck = adminCheck;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getBanstartDate() {
		return banstartDate;
	}

	public void setBanstartDate(Date banstartDate) {
		this.banstartDate = banstartDate;
	}

	public Date getBanendDate() {
		return banendDate;
	}

	public void setBanendDate(Date banendDate) {
		this.banendDate = banendDate;
	}
		

	public Date getSysdate() {
		return sysdate;
	}

	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}

	@Override
	public String toString() {
		return "ReportVo [reportCode=" + reportCode + ", reporterCode=" + reporterCode + ", reportedCode="
				+ reportedCode + ", postCode=" + postCode + ", replyCode=" + replyCode + ", reportReason="
				+ reportReason + ", adminCheck=" + adminCheck + ", reportDate=" + reportDate + ", banstartDate="
				+ banstartDate + ", banendDate=" + banendDate + "]";
	}
	
	
}
