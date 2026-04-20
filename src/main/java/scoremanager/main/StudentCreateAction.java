package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        School school = new School();
        school.setCd("tes");

        LocalDate today = LocalDate.now();
        int year = today.getYear();

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        Map<String, String> errors = new HashMap<>();
        
        List<String> classList = classNumDao.filter(school);
        req.setAttribute("class_list", classList);

        // 入学年度セット（10年前〜今年）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        // GET（初回表示）
        if (req.getMethod().equals("GET")) {
            req.setAttribute("f1", "");
            req.setAttribute("f2", "");
            req.setAttribute("f3", "");
            req.setAttribute("f4", "");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // POST（登録処理）
        String entYearStr = req.getParameter("f1");
        String no = req.getParameter("f2");
        String name = req.getParameter("f3");
        String classNum = req.getParameter("f4");

        // 入力保持
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", no);
        req.setAttribute("f3", name);
        req.setAttribute("f4", classNum);

        // バリデーション
        int entYear = 0;

        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("f1", "入学年度を選択してください");
        } else {
            entYear = Integer.parseInt(entYearStr);
        }

        if (no == null || no.isEmpty()) {
            errors.put("f2", "学生番号を入力してください");
        }

        if (name == null || name.isEmpty()) {
            errors.put("f3", "氏名を入力してください");
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("f4", "クラスを選択してください");
        }

        // 重複チェック
        if (errors.isEmpty()) {
            Student old = studentDao.get(no);
            if (old != null) {
                errors.put("f2", "この学生番号は既に登録されています");
            }
        }
        
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // Student オブジェクト作成
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true); // 新規は在学中で登録
        student.setSchool(school);

        // 登録処理（INSERT）
        studentDao.save(student);

        // 完了画面へ
        req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
    }
}
