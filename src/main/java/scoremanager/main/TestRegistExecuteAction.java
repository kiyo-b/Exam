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
        String[] points = req.getParameterValues("point");
        String[] oldpoints = req.getParameterValues("oldPoint");
    //		クラス番号
        String[] no = req.getParameterValues("no");
    //		クラス番号
        String[] subject = req.getParameterValues("subject");
        
        boolean hasError = false;
        String errorMsg = "";
        
    //		配列の中身をコンソールに出力
        for (int i = 0; i < points.length; i++) {
            System.out.println("---- i = " + i + " ----");
            System.out.println("points = " + points[i]);
            System.out.println("student_no = " + student_no[i]);
            System.out.println("class_num = " + class_num[i]);
            System.out.println("oldpoints = " + oldpoints[i]);
            System.out.println("subject = " + subject[i]);
            System.out.println("no = " + no[i]);
        }
    //		配列の中身の数を見えるようにする
        System.out.println("points = " + points);
        System.out.println(points.length);
        System.out.println("points: " + points.length);
        System.out.println("student_no: " + student_no.length);
        System.out.println("class_num: " + class_num.length);
        System.out.println("oldpoints: " + oldpoints.length);
        System.out.println("subject: " + subject.length);
        System.out.println("no: " + no.length);
        
        // 入学年度
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // 回数
        List<Integer> countset = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            countset.add(i);
        }
        
    //		if (points == null) {
    //		    System.out.println("pointsがnullです");
    //		    req.setAttribute("errorMsg", "データが送信されていません");
    //		    req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    //		    return;
    //		}

        for (String p : points) {

            // ① null または 空チェック
            if (p == null || p.trim().isEmpty()) {
                hasError = true;
                errorMsg = "未入力の項目があります";
                break;
            }

            try {
                int point = Integer.parseInt(p);

                // ② 範囲チェック
                if (point < 0 || point > 100) {
                    hasError = true;
                    errorMsg = "0～100の範囲で入力してください";
                    break;
                }

            } catch (NumberFormatException e) {
                // 数値じゃない場合
                hasError = true;
                errorMsg = "数値で入力してください";
                break;
            }
        }

        // ③ 分岐
        if (hasError) {
            req.setAttribute("errorMsg", errorMsg);
            
    //		    検索結果を再表示させるため
            int entYear = Integer.parseInt(req.getParameter("f1"));
            String classNum = req.getParameter("f2");
            String subjectCd = req.getParameter("f3");
            int noVal = Integer.parseInt(req.getParameter("f4"));

            tests = testDao.filter(school, entYear, classNum, subjectCd, noVal);

            req.setAttribute("tests", tests);

            // ★検索条件保持
            req.setAttribute("f1", entYear);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("f4", noVal);

            // ★プルダウン再セット
            req.setAttribute("class_num_set", classNumDao.filter(school));
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("subject_set", subjectDao.filter(school));
            req.setAttribute("testcount_set", countset);

            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        
        } else {
            // 登録処理
            for (int i = 0; i < points.length; i++) {
                try {
                    
    //			    	いまどこの値を受け取ってるか確認
                    System.out.println("---- i = " + i + " ----");
                    System.out.println("student_no = " + student_no[i]);
                    System.out.println("class_num = " + class_num[i]);
                    System.out.println("subject = " + subject[i]);
                    System.out.println("no = " + no[i]);
                    System.out.println("point = " + points[i]);
                    System.out.println("oldValue = " + oldpoints[i]);
                    
                    String newValue = points[i];
                    String oldValue = oldpoints[i];

                    if (newValue == null || newValue.trim().isEmpty()) {
                        continue;
                    }

                    int point = Integer.parseInt(newValue);
                    
    //			        もしDBに点数がない、または-のとき
                    if (oldValue == null || oldValue.trim().isEmpty() || oldValue.equals("-")) {
    //			        	登録
                        System.out.println("INSERT実行");
                        testDao.insert(school, student_no[i], class_num[i], subject[i], Integer.parseInt(no[i]), point);
                        System.out.println("INSERT実行終了");
                    } else {
    //			        	更新
                        System.out.println("UPDATE実行");
                        testDao.update(school, student_no[i], class_num[i], subject[i], Integer.parseInt(no[i]), point);
                        System.out.println("UPDATE実行終了");
                    }

                } catch (Exception e) {
                    System.out.println("★エラー発生 i=" + i);
                    e.printStackTrace(); 
                }
            }
            
            res.sendRedirect("test_regist_done.jsp");
            return;
        }


    }

    }

