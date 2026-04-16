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

	private String baseSql = "select * from test where school_cd = ? ";
	private List<Test> TpostFilter(ResultSet resultSet) throws Exception {

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

				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

//	☆入学年度、クラス、科目、回数を引数としてDBから検索するフィルター
	public List<Test> filter(School school, Integer entYear, String classNum, String subject, Integer testcount ) throws Exception {
		
		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String condition = "and ent_year = ? and class_num = ? and subject = ? and testcount = ? ";
		// SQL文のソート
		String order = " order by ent_year asc";


		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			statement.setString(4, subject);
            statement.setInt(5, testcount);
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = TpostFilter(resultSet);
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
    
//	☆学生番号を引数としてDBから検索するフィルター
	public List<Test> filter(School school, Integer no ) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String condition = "and no = ? ";
		// SQL文のソート
		String order = " order by ent_year asc";


		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, no);

			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = TpostFilter(resultSet);
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

	
//	☆学校コードを引数としてDBから回数を取り出すフィルター
	public List<Test> filter(School school) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select distinct no from test");


			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			while (resultSet.next()) {
			    int no = resultSet.getInt("no");
			}
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

	
	// 	public boolean save(Test test) throws Exception {

	// 	// コネクションを確立
	// 	Connection connection = getConnection();
	// 	// プリペアードステートメント
	// 	PreparedStatement statement = null;
	// 	// 実行件数
	// 	int count = 0;

	// 	try {
	// 		// データベースから学生を取得
	// 		Test old = get(test.getNo());
	// 		if (old == null) {
	// 			// 学生が存在しなかった場合
	// 			// プリペアードステートメントにINSERT文をセット
	// 			statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
	// 			// プリペアードステートメントに値をバインド
	// 			statement.setString(1, test.getStudent_no());
	// 			statement.setString(2, test.getSubject_cd());
	// 			statement.setString(3, test.getSchool_cd());
	// 			statement.setInt(4, test.getNo());
	// 			statement.setInt(5, test.getPoint());
	// 			statement.setString(6, test.getClass_num());
	// 		} else {
	// 			// 学生が存在した場合
	// 			// プリペアードステートメントにUPDATE文をセット
	// 			statement = connection.prepareStatement("update student set student_no = ?, subject_cd = ?, school_cd = ?, no = ?, point = ?, class_num = ? ");
	// 			// プリペアードステートメントに値をバインド

	// 			statement.setString(1, test.getStudent_no());
	// 			statement.setString(2, test.getSubject_cd());
	// 			statement.setString(3, test.getSchool_cd());
	// 			statement.setInt(4, test.getNo());
	// 			statement.setInt(5, test.getPoint());
	// 			statement.setString(6, test.getClass_num());
	// 		}

	// 		// プリペアードステートメントを実行
	// 		count = statement.executeUpdate();

	// 	} catch (Exception e) {
	// 		throw e;
	// 	} finally {
	// 		// プリペアードステートメントを閉じる
	// 		if (statement != null) {
	// 			try {
	// 				statement.close();
	// 			} catch (SQLException sqle) {
	// 				throw sqle;
	// 			}
	// 		}
	// 		// コネクションを閉じる
	// 		if (connection != null) {
	// 			try {
	// 				connection.close();
	// 			} catch (SQLException sqle) {
	// 				throw sqle;
	// 			}
	// 		}
	// 	}

	// 	if (count > 0) {
	// 		// 実行件数が1件以上ある場合
	// 		return true;
	// 	} else {
	// 		// 実行件数が0件の場合
	// 		return false;
	// 	}
	// }
}
