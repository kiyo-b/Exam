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
				test.setEntYear(resultSet.getInt("ent_year"));
				test.setStudent_Name(resultSet.getString("student_name"));
				test.setStudent_no(resultSet.getString("student_no"));
				test.setSubject_cd(resultSet.getString("subject_cd"));
				test.setSchool_cd(resultSet.getString("school_cd"));
				test.setPoint1((Integer) resultSet.getObject("point1"));
				test.setPoint2((Integer) resultSet.getObject("point2"));
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
	public List<Test> filter(School school, Integer entYear, String classNum, String subject ) throws Exception {
		
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
			statement = connection.prepareStatement(
//					入学年度、クラス、学生番号、名前、1回目、2回目のデータを抽出
					"select s.ent_year, s.class_num, s.no as student_no, "
					+ "s.name as student_name ,"
//					回数が1の時、科目が入力された値だったら
					+ "max(case when t.no = 1 and t.subject_cd = ? then t.point end) as point1,"
//					回数が2の時、科目が入力された値だったら
					+ "max(case when t.no = 2 and t.subject_cd = ? then t.point end) as point2 "
//					学生テーブルとテストテーブルから
					+ "from student s left join test t "
//					学生テーブルのとテストテーブルの学生番号と学校コードが一致
					+ "on s.no = t.student_no and s.school_cd = t.school_cd "
//					学校コードと入学年度とクラス番号が入力された値の時
					+ "where s.school_cd = ? and s.ent_year = ? and s.class_num = ? "
//					入学年度、クラス番号、学生番号、名前をグループにして
//					+ "group by s.ent_year, s.class_num, s.no, s.name "
//					+ "having "
////					グループの検索条件
////					1回目の点数がnullじゃないとき
//					+ "max(case when t.no = 1 and t.subject_cd = ? then t.point end) is not null "
////					または
//					+ "or "
////					2回目の点数がnullじゃないとき
//					+ "max(case when t.no = 2 and t.subject_cd = ? then t.point end) is not null "
////					並び替え順は学生番号
//					+ "order by s.no "
			);
			statement.setString(1, subject);   // point1
			statement.setString(2, subject);   // point2
			statement.setString(3, school.getCd());
			statement.setInt(4, entYear);
			statement.setString(5, classNum);
			statement.setString(6, subject);   // having
			statement.setString(7, subject);   // having
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			System.out.println("===== SQL START =====");
			System.out.println(statement);
			System.out.println("===== SQL END =====");


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
