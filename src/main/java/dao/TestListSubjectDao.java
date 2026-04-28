package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestListSubjectDao extends Dao {


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
   
}
