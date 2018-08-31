package ein.mono.keyword.model.vo;

public class KeywordVo {
	int keyword_no;
	String keyword_content;
	
	public KeywordVo() {
		super();
	}
	public KeywordVo(int keyword_no, String keyword_content) {
		super();
		this.keyword_no = keyword_no;
		this.keyword_content = keyword_content;
	}

	public int getKeyword_no() {
		return keyword_no;
	}
	public void setKeyword_no(int keyword_no) {
		this.keyword_no = keyword_no;
	}
	public String getKeyword_content() {
		return keyword_content;
	}
	public void setKeyword_content(String keyword_content) {
		this.keyword_content = keyword_content;
	}
	
	@Override
	public String toString() {
		return "KeywordVo [keyword_no=" + keyword_no + ", keyword_content=" + keyword_content + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
