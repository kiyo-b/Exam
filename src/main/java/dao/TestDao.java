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

//	☆入学年度、クラス、科目、回数を引数としてDBから検索するフィルター
	public List<Test> filter(School school, Integer entYear, String classNum, String subject, int no) throws Exception {

	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	    	System.out.println("sqlをセット");
	    	statement = connection.prepareStatement(
	    		    "select s.ent_year, s.class_num, s.no as student_no, s.name, t.point "
	    		  + "from student as s "
	    		  + "left join test as t "
	    		  + "on s.no = t.student_no "
//	    		  + "and s.class_num = t.class_num "
	    		  + "and s.school_cd = t.school_cd "
	    		  + "and t.no = ? "
	    		  + "and t.subject_cd = ? "
	    		  + "where s.school_cd = ? "
	    		  + "and s.ent_year = ? "
	    		  + "and s.class_num = ? "
	    		);

    		// パラメータ順
    		statement.setInt(1, no);
    		statement.setString(2, subject);
    		statement.setString(3, school.getCd());
    		statement.setInt(4, entYear); 
    		statement.setInt(5, Integer.parseInt(classNum));

    		System.out.println("no = " + no);
    		System.out.println("subject = " + subject);
    		System.out.println("school_cd = " + school.getCd());
    		System.out.println("class_num = " + classNum);
    		
	        System.out.println("SQL実行前");
	        resultSet = statement.executeQuery();
    		System.out.println(statement);
    		System.out.println(resultSet);
	        System.out.println("SQL実行後");

	        // ✅ ここでlistに詰める（TpostFilter使わないなら）
	        while (resultSet.next()) {
	            System.out.println("while文に入ってる");

	            Test test = new Test();
	            test.setEntYear(resultSet.getInt("ent_year"));
	            test.setClass_num(resultSet.getString("class_num"));
	            test.setStudent_no(resultSet.getString("student_no"));
	            test.setStudent_Name(resultSet.getString("name"));
	            test.setPoint((Integer) resultSet.getObject("point"));

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
    

	//	点数を更新
	public void update(School school, String student_no, String class_num, String subject, int no, int point) throws Exception {
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	
	    try {
	        System.out.println("sqlをセット");
		    statement = connection.prepareStatement(
	            "update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ? and class_num = ?"
	            );
	
	        // パラメータ順
	        statement.setInt(1, point);
	        statement.setString(2, student_no);
	        statement.setString(3, subject);
	        statement.setString(4, school.getCd());
	        statement.setInt(5, no); 
	        statement.setString(6, class_num);
	        
	        System.out.println("SQL実行前");
	        int count = statement.executeUpdate();
	        System.out.println("更新件数: " + count);
	        System.out.println(statement);
	        System.out.println("SQL実行後");
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

	}

	//	点数を登録
	public void insert(School school, String student_no, String class_num, String subject, int no, int point) throws Exception {
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    
	
	    try {
	        System.out.println("sqlをセット");
		    statement = connection.prepareStatement(
	            "insert into test "
	            + "values(?, ?, ? ,? ,?, ?) "
	            );

			    statement.setString(1, student_no);
		        statement.setString(2, subject);
		        statement.setString(3, school.getCd());
		        statement.setInt(4, no);
		        statement.setInt(5, point);
		        statement.setString(6, class_num);

		        System.out.println("SQL実行前");
		        statement.executeUpdate();
		        System.out.println(statement);
		        System.out.println("SQL実行後");

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

	}

}
