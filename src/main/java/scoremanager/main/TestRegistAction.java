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
 
public class TestRegistAction extends Action {
 
	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
 
		// HttpSession session = req.getSession(); // コメントアウト

		// Teacher teacher = (Teacher)session.getAttribute("user"); // コメントアウト
 
		// 【代わりの処理】ログイン情報の代わりに、仮の学校情報を設定

		// 本来はログインした先生の学校を使いますが、今回はテスト用に「oom」校として動かします

		School school = new School();

		school.setCd("tes"); // お使いのテストデータに合わせた学校コードを指定してください
 
		// ローカル変数の指定 1

		// 画面から送られてくる検索条件や、DBから取ってきたデータを入れるための「箱」を準備します

// 入力値が入る

		String entYearStr = ""; 

		String classNum = "";

		String subject = ""; 

		String countStr = "";

		String student_no = "";

		String student_name = "";

		String subjectSet = "";
 
		

// 型変換などで変数を変える時　String→int

		int entYear = 0; 

		int testcount = 0;
 
// 学生情報を格納するリスト

		List<Test> tests = null; 

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

//		入学年度

		entYearStr = req.getParameter("f1");

//		クラス

		classNum = req.getParameter("f2");

//		科目

		subject = req.getParameter("f3");

//		回数

		countStr = req.getParameter("f4");		

		// ビジネスロジック 4

		// 受け取った値を「プログラムで計算・判定しやすい形」に整えます

		if (entYearStr != null && !entYearStr.isEmpty()) { // 空文字チェックを追加

			entYear = Integer.parseInt(entYearStr); // 文字列を数字に変換

		}

		if (countStr !=null && !countStr.isEmpty()) {// 空文字チェックを追加

			testcount = Integer.parseInt(countStr); //文字列を数字に変換 

		}

//	☆入学年度を表示するために必要（変更なし）

		// 画面のプルダウン（入学年度の選択肢）を作るために、今年から10年前までの数字をリストにします

		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i <= year; i++) { // 10年前から今年まで

			entYearSet.add(i);

		}

//  ☆ここまで


		// DBからデータ取得 3

		// 【変更】teacher.getSchool() を school に書き換え

		// その学校に登録されているクラス番号（A組、B組など）の一覧をDBから取ってきます

		List<String> list = classNumDao.filter(school);

		List<Test> clist = testDao.filter(school);

		List<Subject> slist = subjectDao.filter(school);
 
		// ここで「どういう条件で検索するか」を判断し、DB（Dao）に命令を出します

		if (entYear != 0 
&& classNum != null && !classNum.equals("0") 
&& subject != null && !subject.equals("0")
&& testcount !=0

		) {

			// 入学年度とクラス番号を指定（例：2023年の1組）

			tests = testDao.filter(school, entYear, classNum, subject, testcount);
 
		} else {

			// クラスだけ選んで年度を忘れた場合、エラーメッセージを出して全表示にします

			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");

			req.setAttribute("errors", errors);

//			tests = studentDao.filter(school, isAttend);

		}
 
		// レスポンス値をセット 6

		// 次の画面（JSP）で表示するために、検索結果や選択肢などのデータを詰め込みます

//		検索結果を表示するための検索後結果

		req.setAttribute("tests", tests);

//		検索条件を表示するために必要なデータ

		req.setAttribute("f1", entYear);

		req.setAttribute("f2", classNum);

		req.setAttribute("student_no", student_no);      // 検索された学生名簿

		req.setAttribute("class_num_set", list);    // クラスの選択肢

		req.setAttribute("ent_year_set", entYearSet); // 入学年度の選択肢

		req.setAttribute("subject_set", slist); // 科目の選択肢

		req.setAttribute("testcount_set", clist); // 回数の選択肢
 
		// JSPへフォワード 7

		// 全てのデータを「student_list.jsp」というファイルに渡して、画面を表示させます

		req.getRequestDispatcher("student_list.jsp").forward(req, res);

	}

}

 