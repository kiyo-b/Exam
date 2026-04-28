package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestListStudentDao extends Dao {

	private List<Test> TpostFilter(ResultSet resultSet) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
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
		} catch (SQLException | NullPointerException e) {
			throw e;
//			e.printStackTrace();
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
