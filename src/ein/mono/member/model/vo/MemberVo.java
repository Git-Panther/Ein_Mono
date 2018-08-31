package ein.mono.member.model.vo;

import java.util.Date;

public class MemberVo {
	
	private String memberCode;
	private String memberRank;
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberEmail;
	private String memberAddress;
	private String memberTel;
	private String memberNname;
	private Date memberJdate;
	private String delflag;
	private int reportCount;
	private Date ban_startdate;
	private Date ban_enddate;
	private String licence;
	private String emailCheck;
	
	public MemberVo() {}

	public MemberVo(String memberCode, String memberRank, String memberId, String memberPwd, String memberName,
			String memberEmail, String memberAddress, String memberTel, String memberNname, Date memberJdate,
			String delflag, int reportCount) {
		super();
		this.memberCode = memberCode;
		this.memberRank = memberRank;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberTel = memberTel;
		this.memberNname = memberNname;
		this.memberJdate = memberJdate;
		this.delflag = delflag;
		this.reportCount = reportCount;
	}
	
	

	public MemberVo(String memberId, String memberPwd, String memberName, String memberEmail, String memberAddress,
			String memberTel, String memberNname) {
		super();//회원가입
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberTel = memberTel;
		this.memberNname = memberNname;
	}
	
	public MemberVo(String memberId, String memberPwd, String memberName, String memberEmail, String memberAddress,
			String memberTel) {
		super();//회원가입
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberTel = memberTel;
	}


	public MemberVo(String mId, String mPwd, String mName, String mEmail, String mAddress, String mTel,
			String mNickname, String licence) {
		super();//회원가입
		this.memberId = mId;
		this.memberPwd = mPwd;
		this.memberName = mName;
		this.memberEmail = mEmail;
		this.memberAddress = mAddress;
		this.memberTel = mTel;
		this.memberNname = mNickname;
		this.licence = licence;
		
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	public String getMemberNname() {
		return memberNname;
	}

	public void setMemberNname(String memberNname) {
		this.memberNname = memberNname;
	}

	public Date getMemberJdate() {
		return memberJdate;
	}

	public void setMemberJdate(Date memberJdate) {
		this.memberJdate = memberJdate;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}
	
	public Date getBan_startdate() {
		return ban_startdate;
	}

	public void setBan_startdate(Date ban_startdate) {
		this.ban_startdate = ban_startdate;
	}

	public Date getBan_enddate() {
		return ban_enddate;
	}

	public void setBan_enddate(Date ban_enddate) {
		this.ban_enddate = ban_enddate;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getEmailCheck() {
		return emailCheck;
	}

	public void setEmailCheck(String emailCheck) {
		this.emailCheck = emailCheck;
	}

	@Override
	public String toString() {
		return "MemberVo [memberCode=" + memberCode + ", memberRank=" + memberRank + ", memberId=" + memberId
				+ ", memberPwd=" + memberPwd + ", memberName=" + memberName + ", memberEmail=" + memberEmail
				+ ", memberAddress=" + memberAddress + ", memberTel=" + memberTel + ", memberNname=" + memberNname
				+ ", memberJdate=" + memberJdate + ", delflag=" + delflag + "]";
	}
	
	
	
	
	
	
	
	
	
}
