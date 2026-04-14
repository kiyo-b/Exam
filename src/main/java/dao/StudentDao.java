package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

// DBに接続して Studentテーブルを操作するクラス
public class StudentDao extends Dao {
	// basesqlを定義
	private String baseSql = "select * from student where school_cd=?";
		// 学生番号で一人の学生を取得（1件だけ）
		public Student get(String no) throws Exception {
			// オブジェクト化
			Student student = new Student();
			// DBと接続
			Connection connection = getConnection();
			// sqlを実行するためのオブジェクトを入れる変数を宣言
			PreparedStatement statement = null;
			try{
				// DBと接続して実行するsqlを記述
				// 学生番号を条件式に当てはまる生徒を抽出
				statement = connection.prepareStatement("select * from student where no=?");
				// ?に受け取ったnoを当てはめる
				statement.setString(1, no);
				// 実行した結果をrSetに格納
				ResultSet rSet = statement.executeQuery();
				// SchoolDaoをオブジェクト化
				SchoolDao SchoolDao = new SchoolDao();
				
				// rSetに格納しているデータをそれぞれ取り出して当てはまる学生情報をセットする
				if (rSet.next()){
					student.setNo(rSet.getString("no"));
					student.setName(rSet.getString("name"));
					student.setEntYear(rSet.getInt("ent_year"));
					student.setClassNum(rSet.getString("class_num"));
					student.setAttend(rSet.getBoolean("is_attend"));
					student.setSchool(SchoolDao.get(rSet.getString("school_cd")));
				}else{
					// 当てはまる生徒がいなかった
					student = null;
				}
			}catch (Exception e){
				throw e;
			}finally{
				if (statement != null){
					try{
						statement.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
			}
			// sutudentのデータを戻す
			return student;
		}

		// sqlの結果をリストに変換するメソッド
		private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
			// リストを定義
			List<Student> list = new ArrayList<>();
			try{
				// 取り出したものの次がなくなるまでループ
				while(rSet.next()){
					Student student = new Student();
					student.setNo(rSet.getString("no"));
					student.setName(rSet.getString("name"));
					student.setEntYear(rSet.getInt("ent_year"));
					student.setClassNum(rSet.getString("class_num"));
					student.setAttend(rSet.getBoolean("is_attend"));
					student.setSchool(school);
					list.add(student);
				}
			}catch(SQLException | NullPointerException e){
//				e.printStackTrace();
				throw e;
			}
			return list;
		}

		// 学校、年、クラス、在学フラグで条件検索
		public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
			// リストを定義
			List<Student> list = new ArrayList<>();
//			DBと接続
			Connection connection = getConnection();
//			sqlを保存する変数を定義
			PreparedStatement statement = null;
//			実行結果を保存するクラスの定義
			ResultSet rSet = null;
//			sqlを記述
			String condition = "and  ent_year=? and class_num=?";
//			並び替えのsqlを記述
			String order = "order by no asc";
//			空文字で在学中かの変数を定義
			String conditionIsAttend = "";
//			在学中だったら
			if (isAttend){
				conditionIsAttend = "and is_attend=true";
			}
			try{
//				sqlを順に格納
				statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
//				basesqlの？にセット
				statement.setString(1, school.getCd());
//				conditionの？にセット
				statement.setInt(2, entYear);
				statement.setString(3, classNum);
//				sqlを実行
				rSet = statement.executeQuery();
//				リストには一つの学校でリストをつくる
				list = postFilter(rSet, school);
			}catch(Exception e){
				throw e;
			}finally{
				if (statement !=null){
					try{
						connection.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
			}
			return list;
		}


//		学校、年、在学フラグで検索するフィルター
		public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
//			リストを定義
			List<Student> list = new ArrayList<>();
//			DBを接続
			Connection connection = getConnection();
//			sqlを格納する変数を定義
			PreparedStatement statement = null;
//			実行した結果を格納する変数を定義
			ResultSet rSet = null;
//			条件の追加
			String condition = "and ent_year=?";
//			並び替え順を記述
			String order = "order by no asc";
//			在学フラグ
			String conditionIsAttend = "";
//			在学中だったら
			if (isAttend){
				conditionIsAttend ="and is_attend=true";
			}
			try{
//				sqlを保存
				statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
//				basesqlの？に記述
				statement.setString(1, school.getCd());
//				conditionに年を記述
				statement.setInt(2, entYear);
//				実行した結果を格納
				rSet = statement.executeQuery();
//				リストに一つの学校のみの結果を格納
				list = postFilter(rSet, school);
			}catch(Exception e){
				throw e;
			}finally{
				if (statement !=null){
					try{
						statement.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
			}
			return list;
		}

//		学校、在学フラグで検索するフィルター
		public List<Student> filter(School school, boolean isAttend) throws Exception {
//			リストを定義
			List<Student> list = new ArrayList<>();
//			DBを接続
			Connection connection = getConnection();
			// sqlを保存する変数を定義
			PreparedStatement statement = null;
			// 実行した結果を格納する変数を記述
			ResultSet rSet = null;
//			並び替え順の記述
			String order = "order by no asc";
//			在学フラグ
			String conditionIsAttend = "";
//			在学中
			if (isAttend){
				conditionIsAttend = "and is_attend=true";
			}
			try{
//				sqlを格納
				statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
//				basesqlの？に格納
				statement.setString(1, school.getCd());
//				sqlを実行
				rSet = statement.executeQuery();
//				一つの学校でリストに格納
				list = postFilter(rSet, school);
			}catch(Exception e){
				throw e;
			}finally{
				if (statement !=null){
					try{
						statement.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
				if (connection !=null){
					try{
						connection.close();	
					}catch(SQLException sqle) {
						throw sqle;
					}
				}
			}
			return list;
		}
		public boolean save(Student student) throws Exception {
			Connection connection = getConnection();
			PreparedStatement statement = null;
			int count = 0;
			try{
				Student old = get(student.getNo());
				if (old == null){
					// sqlを格納
					statement = connection.prepareStatement(
						"insert into student (no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)"
					);
					statement.setString(1, student.getNo());
					statement.setString(2, student.getName());
					statement.setInt(3, student.getEntYear());
					statement.setString(4, student.getClassNum());
					statement.setBoolean(5, student.isAttend());
					statement.setString(6, student.getSchool().getCd());
				}else{
					statement = connection.prepareStatement(
						"update student set name=?, ent_year=?, class_num = ?, is_attend=? where no=?"
					);
					statement.setString(1, student.getName());
					statement.setInt(2, student.getEntYear());
					statement.setString(3, student.getClassNum());
					statement.setBoolean(4, student.isAttend());
					statement.setString(5, student.getNo());
				}
				count = statement.executeUpdate();
			}catch(Exception e){
				throw e;
			}finally{
				if(statement !=null){
					try{
						connection.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
				if (connection !=null){
					try{
						connection.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
			}
			if (count > 0){
				return true;
			}else{
				return false;
			}
		}
	}