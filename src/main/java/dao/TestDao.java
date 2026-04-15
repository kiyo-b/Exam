package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test where school_cd = ?";

//	public Test get(String no) throws Exception {
//
//		// 学生インスタンスを初期化
//		Test test = new Test();
//		// データベースへのコネクションを確立
//		Connection connection = getConnection();
//		// プリペアードステートメント
//		PreparedStatement statement = null;
//
//		try {
//			// プリペアードステートメントにSQL文をセット
//			statement = connection.prepareStatement("select * from test where school_cd = ?");
//			// プリペアードステートメントに学生番号をバインド
//			statement.setString(1, no);
//			// プリペアードステートメントを実行
//			ResultSet resultSet = statement.executeQuery();
//
//			// 学校Daoを初期化
//			SchoolDao schoolDao = new SchoolDao();
//
//			if (resultSet.next()) {
//				// リザルトセットが存在する場合
//				// 学生インスタンスに検索結果をセット
//				test.setStudent_no(resultSet.getString("student_no"));
//				test.setSubject_cd(resultSet.getString("subject_cd"));
//				test.setSchool_cd(resultSet.getString("school_cd"));
//				test.setNo(resultSet.getInt("no"));
//				test.setPoint(resultSet.getInt("point"));
//				test.setClass_num(resultSet.getString("class_num"));
//				// 学生フィールドには学校コードで検索した学校インスタンスをセット
////				test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
//			} else {
//				// リザルトセットが存在しない場合
//				// 学生インスタンスにnullをセット
//				test= null;
//			}
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			// プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//			// コネクションを閉じる
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//		}
//
//		return test;
//	}

	private List<Test> TpostFilter(ResultSet resultSet, School school) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (resultSet.next()) {
				// 学生インスタンスを初期化
				Test test = new Test();
				// 学生インスタンスに検索結果をセット
				test.setStudent_no(resultSet.getString("student_no"));
				test.setSubject_cd(resultSet.getString("subject_cd"));
				test.setSchool_cd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				test.setClass_num(resultSet.getString("class_num"));
//				student.setSchool(school);
				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Test> filter(School school, Integer entYear, String classNum, String subject, Integer count ) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String condition = "and ent_year = ?";
		// SQL文のソート
		String order = " order by no asc";


		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			statement.setString(4, subject);
            statement.setInt(5, count);
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = TpostFilter(resultSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

//	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
//
//		// リストを初期化
//		List<Student> list = new ArrayList<>();
//		// コネクションを確立
//		Connection connection = getConnection();
//		// プリペアードステートメント
//		PreparedStatement statement = null;
//		// リザルトセット
//		ResultSet resultSet = null;
//		// SQL文の条件
//		String condition = "and ent_year = ?";
//		// SQL文のソート
//		String order = " order by no asc";
//
//		// SQL文の在学フラグ
//		String conditionIsAttend = "";
//		// 在学フラグガtrueだった場合
//		if (isAttend) {
//			conditionIsAttend = "and is_attend = true";
//		}
//
//		try {
//			// プリペアードステートメントにSQL文をセット
//			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
//			// プリペアードステートメントに学校コードをバインド
//			statement.setString(1, school.getCd());
//			// プリペアードステートメントに入学年度をバインド
//			statement.setInt(2, entYear);
//			// プリペアードステートメントを実行
//			resultSet = statement.executeQuery();
//			// リストへの格納処理を実行
//			list = postFilter(resultSet, school);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			// プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//			// コネクションを閉じる
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//		}
//
//		return list;
//	}
//	public List<Student> filter(School school, boolean isAttend) throws Exception {
//
//		// リストを初期化
//		List<Student> list = new ArrayList<>();
//		// コネクションを確立
//		Connection connection = getConnection();
//		// プリペアードステートメント
//		PreparedStatement statement = null;
//		// リザルトセット
//		ResultSet resultSet = null;
//		// SQL文の条件
//		String order = " order by no asc";
//
//		// SQL文を在学フラグ
//		String conditionIsAttend = "";
//		// 在学フラグがtrueの場合
//		if (isAttend) {
//			conditionIsAttend = "and is_attend = true";
//		}
//
//		try {
//			// プリペアードステートメントにSQL文をセット
//			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
//			// プリペアードステートメントに学校コードをバインド
//			statement.setString(1, school.getCd());
//			// プリペアードステートメントを実行
//			resultSet = statement.executeQuery();
//			// リストへの格納処理を実行
//			list = postFilter(resultSet, school);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			// プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//			// コネクションを閉じる
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//		}
//
//		return list;
//	}

	public boolean save(Test test) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// データベースから学生を取得
			Test old = get(test.getNo());
			if (old == null) {
				// 学生が存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent_no());
				statement.setString(2, test.getSubject_cd());
				statement.setString(3, test.getSchool_cd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClass_num());
			} else {
				// 学生が存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("update student set student_no = ?, subject_cd = ?, school_cd = ?, no = ?, point = ?, class_num = ? ");
				// プリペアードステートメントに値をバインド

				statement.setString(1, test.getStudent_no());
				statement.setString(2, test.getSubject_cd());
				statement.setString(3, test.getSchool_cd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClass_num());
			}

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}
