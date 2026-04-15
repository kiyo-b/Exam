package bean;

import java.io.Serializable;

public class Test implements Serializable {

	/**
	 * 学校コード:String
	 */
	private String student_no;

	/**
	 * 学校名:String
	 */
	private String subject_cd;
	
	private String school_cd;
	
	private int no;
	
	private int point;
	
	private String class_num;

	/**
	 * ゲッター・セッター
	 */
	public String getStudent_no() {
		return student_no;
	}

	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}

	public String getSubject_cd() {
		return subject_cd;
	}

	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	
	public String getschool_cd() {
		return school_cd;
	}

	public void setSchool_cd(String school_cd) {
		this.school_cd = school_cd;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getClass_num() {
		return class_num;
	}

	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}
}
