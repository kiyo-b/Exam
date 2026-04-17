package bean;

import java.io.Serializable;

public class Subject implements Serializable {
	private String school_cd;

	/**
	 * 科目コード：String
	 */
	private String cd;

	/**
	 * 科目名：String
	 */
	private String name;



	/**
	 * ゲッタ・セッタ
	 */
	public String getSchool_cd() {
		return school_cd;
	}

	public void setSchool_cd(String shool_cd) {
		this.school_cd = school_cd;
	}
	
	
	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
