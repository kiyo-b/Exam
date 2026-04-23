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
//				test.setSubject_cd(resultSet.getString("subject_cd"));
//				test.setSchool_cd(resultSet.getString("school_cd"));
				Integer p1 = (Integer) resultSet.getObject("point1");
				if (p1 != null) {
					String str = String.valueOf(p1);
				    test.setPoint1(str);
				} else {
				    test.setPoint1("-");
				    
				}

				Integer p2 = (Integer) resultSet.getObject("point2");
				if (p2 != null) {
				    String str = String.valueOf(p2);
				    test.setPoint2(str);
				} else {
				    test.setPoint2("-");
				}
				test.setClass_num(resultSet.getString("class_num"));

				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			throw e;
//			e.printStackTrace();
		}

		return list;
	}

//	☆入学年度、クラス、科目を引数としてDBから検索するフィルター
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
					+ "group by s.ent_year, s.class_num, s.no, s.name "
					+ "having "
//					グループの検索条件
//					1回目の点数がnullじゃないとき
					+ "max(case when t.no = 1 and t.subject_cd = ? then t.point end) is not null "
//					または
					+ "or "
//					2回目の点数がnullじゃないとき
					+ "max(case when t.no = 2 and t.subject_cd = ? then t.point end) is not null "
//					並び替え順は学生番号
					+ "order by s.no "
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
	public List<Test> filter(School school, String no ) throws Exception {

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
				    "select "
				  + "s.no as student_no, "
				  + "s.name as student_name, "
				  + "sub.name as subject_name, "
				  + "t.subject_cd, "
				  + "t.no, "
				  + "t.point "
				  + "from test t "
				  + "inner join student s "
				  + "on t.student_no = s.no and t.school_cd = s.school_cd "
				  + "inner join subject sub "
				  + "on t.subject_cd = sub.cd "
				  + "where t.school_cd = ? "
				  + "and t.student_no = ? "
				  + "order by t.subject_cd, t.no"
				);

//					並び替え順は学生番号
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setString(2, no);

			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行

			while (resultSet.next()) {
			    Test t = new Test();
			    t.setStudent_no(resultSet.getString("student_no"));
			    t.setStudent_Name(resultSet.getString("student_name"));
			    t.setSubjectName(resultSet.getString("subject_name"));
			    t.setSubjectCd(resultSet.getString("subject_cd"));
			    t.setNo(resultSet.getInt("no"));
			    t.setPoint((Integer) resultSet.getObject("point"));
			    list.add(t);
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
			statement = connection.prepareStatement(
				    "select s.ent_year, s.class_num, s.no as student_no, "
				  + "s.name as student_name, "
				  + "max(case when t.no = 1 then t.point end) as point1, "
				  + "max(case when t.no = 2 then t.point end) as point2 "
				  + "from student s left join test t "
				  + "on s.no = t.student_no and s.school_cd = t.school_cd "
				  + "where s.school_cd = ? "
				  + "group by s.ent_year, s.class_num, s.no, s.name "
				  + "having "
				  + "max(case when t.no = 1 then t.point end) is not null "
				  + "or "
				  + "max(case when t.no = 2 then t.point end) is not null "
				);

//					並び替え順は学生番号
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド

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

//	☆入学年度、クラス、科目を引数としてDBから検索するフィルター
	public List<Test> filter(School school, Integer entYear, String classNum, String subject, int no) throws Exception {

	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        statement = connection.prepareStatement(
	            "SELECT "
	          + "s.ent_year, "
	          + "s.class_num, "
	          + "s.no AS student_no, "
	          + "s.name, "
	          + "t.point "
	          + "FROM student s "
	          + "LEFT JOIN test t "
	          + "ON s.no = t.student_no "
	          + "AND s.school_cd = t.school_cd "
	          + "AND s.class_num = t.class_num "
	          + "AND t.subject_cd = ? "
	          + "AND t.no = ? "
	          + "WHERE s.school_cd = ? "
	          + "AND s.ent_year = ? "
	          + "AND s.class_num = ? "
	          + "AND s.is_attend = true "
	          + "ORDER BY s.no"
	        );

	        // ✅ 正しい順番
	        statement.setString(1, subject);          // 科目
	        statement.setInt(2, no);                  // 回数
	        statement.setString(3, school.getCd());   // 学校コード
	        statement.setInt(4, entYear);             // 入学年度
	        statement.setString(5, classNum);         // クラス

	        resultSet = statement.executeQuery();

	        // ✅ ここでlistに詰める（TpostFilter使わないなら）
	        while (resultSet.next()) {
	            Test test = new Test();

	            test.setEntYear(resultSet.getInt("ent_year"));
	            test.setClass_num(resultSet.getString("class_num"));
	            test.setStudent_no(resultSet.getString("student_no"));
	            test.setStudent_Name(resultSet.getString("name"));
	            test.setPoint((Integer) resultSet.getObject("point"));// NULL対応

	            list.add(test);
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
