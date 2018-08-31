package ein.mono.qna.model.vo;

import ein.mono.board.model.vo.PostVo;

public class QnAVo extends PostVo{
	private String ptnCode; // QNA 업체 코드
	private String ptnName; // QNA 업체명
	public String getPtnCode() {
		return ptnCode;
	}
	public void setPtnCode(String ptnCode) {
		this.ptnCode = ptnCode;
	}
	public String getPtnName() {
		return ptnName;
	}
	public void setPtnName(String ptnName) {
		this.ptnName = ptnName;
	}
}
