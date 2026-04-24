package scoremanager.main;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Test;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
 
public class TestRegistExecuteAction extends Action {
 
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("★★★ ExecuteAction開始 ★★★");
		// HttpSession session = req.getSession(); // コメントアウト

		// Teacher teacher = (Teacher)session.getAttribute("user"); // コメントアウト
 
		// 【代わりの処理】ログイン情報の代わりに、仮の学校情報を設定

		// 本来はログインした先生の学校を使い  ますが、今回はテスト用に「oom」校として動かします

		School school = new School();
		school.setCd("oom"); // お使いのテストデータに合わせた学校コードを指定してください
 
		// ローカル変数の指定 1

		// 画面から送られてくる検索条件や、DBから取ってきたデータを入れるための「箱」を準備します

// 入力値が入る


//		String[] classNum = new String[0];
//		String[] student_no = new String[0];
//		String[] no = new String[0];
//		String[] point = new String[0];
//		String[] subject = new String[0];

 
// 学生情報を格納するリスト

		List<Test> tests = new ArrayList<>(); 

// 入学年度を10年分にするためにいまの年度を取得

		LocalDate todaysDate = LocalDate.now(); 
		int year = todaysDate.getYear(); 

// インスタンス化

//		学校情報を取得するため
		SchoolDao schoolDao = new SchoolDao();

//		科目情報を取得するため
		SubjectDao subjectDao = new SubjectDao();

//		学生情報を取得するため
		StudentDao studentDao = new StudentDao();

//		クラス情報を取得するため
		ClassNumDao classNumDao = new ClassNumDao();

//		テスト情報を取得するため
		TestDao testDao = new TestDao();

//		エラー表示するため
		Map<String, String> errors = new HashMap<>(); 
		


 
		// リクエストパラメーターの取得 2

		// 画面の入力フォーム（f1=入学年度, f2=クラス, f3=科目, f4=回数）に書かれた値を受け取ります

//		クラス番号
		String[] class_num = req.getParameterValues("class_num");
//		クラス番号
		String[] student_no = req.getParameterValues("student_no");
//		クラス番号
		String[] point = req.getParameterValues("point");
//		クラス番号
		String[] no = req.getParameterValues("no");
//		クラス番号
		String[] subject = req.getParameterValues("subject");
		
		if ()


		// 全てのデータを「student_list.jsp」というファイルに渡して、画面を表示させます

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);

	}

}

 