package bean;

import java.io.Serializable;

public class Test implements Serializable {

	/**
	 * 学校コード:String
	 */
	private String student_no;
	
	private int ent_year;
	
	private String student_name;

	/**
	 * 学校名:String
	 */
	private String subject_cd;
	
	private String subject_name;
	
	private String school_cd;
	
	private int no;
	
	private String point1;
	
	private String point2;
	
	private Integer point;
	
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
	
	public int getEntYear() {
		return ent_year;
	}

	public void setEntYear(int ent_year) {
		this.ent_year = ent_year;
	}
	
	public String getStudent_Name() {
		return student_name;
	}

	public void setStudent_Name(String student_name) {
		this.student_name = student_name;
	}

	public String getSubjectCd() {
		return subject_cd;
	}

	public void setSubjectCd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	
	public String getSubjectName() {
		return subject_name;
	}

	public void setSubjectName(String subject_name) {
		this.subject_name = subject_name;
	}
	
	public String getSchool_cd() {
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
	
	public String getPoint1() {
		return point1;
	}

	public void setPoint1(String point1) {
		this.point1 = point1;
	}
	
	public String getPoint2() {
		return point2;
	}

	public void setPoint2(String point2) {
		this.point2 = point2;
	}
	
	public String getClass_num() {
		return class_num;
	}

	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}
	
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
}
